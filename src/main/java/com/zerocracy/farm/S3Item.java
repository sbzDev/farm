/**
 * Copyright (c) 2016 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.farm;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jcabi.log.Logger;
import com.jcabi.s3.Ocket;
import com.zerocracy.jstk.Item;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Item in S3.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
final class S3Item implements Item {

    /**
     * S3 ocket.
     */
    private final Ocket ocket;

    /**
     * File.
     */
    private final Path temp;

    /**
     * Is it open/acquired?
     */
    private final AtomicBoolean open;

    /**
     * Ctor.
     * @param okt Ocket
     * @throws IOException If fails
     */
    S3Item(final Ocket okt) throws IOException {
        this(okt, Files.createTempDirectory("").resolve(okt.key()));
    }

    /**
     * Ctor.
     * @param okt Ocket
     * @param tmp Path
     */
    S3Item(final Ocket okt, final Path tmp) {
        this.ocket = okt;
        this.temp = tmp;
        this.open = new AtomicBoolean(false);
    }

    @Override
    public Path path() throws IOException {
        if (!this.open.get()) {
            if (this.temp.getParent().toFile().mkdirs()) {
                Logger.info(
                    this, "Directory created for %s",
                    this.temp.toFile().getAbsolutePath()
                );
            }
            if (this.ocket.exists() && (!Files.exists(this.temp)
                || this.ocket.meta().getLastModified().compareTo(
                    new Date(
                        Files.getLastModifiedTime(this.temp).toMillis()
                    )
                ) > 0)) {
                this.ocket.read(
                    Files.newOutputStream(
                        this.temp,
                        StandardOpenOption.CREATE
                    )
                );
                Files.setLastModifiedTime(
                    this.temp,
                    FileTime.fromMillis(
                        this.ocket.meta().getLastModified().getTime()
                    )
                );
                Logger.info(
                    this, "Loaded %d bytes from %s",
                    this.temp.toFile().length(),
                    this.ocket.key()
                );
            }
            this.open.set(true);
        }
        return this.temp;
    }

    @Override
    public void close() throws IOException {
        if (this.open.get()
            && (!this.ocket.exists()
            || this.ocket.meta().getLastModified().compareTo(
                new Date(Files.getLastModifiedTime(this.temp).toMillis())
            ) < 0)) {
            final ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(this.temp.toFile().length());
            this.ocket.write(Files.newInputStream(this.temp), meta);
            Logger.info(
                this, "Saved %d bytes to %s",
                this.temp.toFile().length(),
                this.ocket.key()
            );
        }
        this.open.set(false);
    }

}
