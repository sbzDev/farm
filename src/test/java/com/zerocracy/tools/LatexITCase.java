/*
 * Copyright (c) 2016-2019 Zerocracy
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
package com.zerocracy.tools;

import java.io.File;
import org.cactoos.Input;
import org.cactoos.io.LengthOf;
import org.cactoos.io.TeeInput;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test case for {@link Latex}.
 * @since 1.0
 */
public final class LatexITCase {

    /**
     * Latex can render PDF.
     * @throws Exception If some problem inside
     */
    @Test
    @Ignore
    public void renders() throws Exception {
        final Input pdf = new Latex(
            "\\documentclass{article}\\begin{document}test\\end{document}",
            "this is our secret data"
        ).pdf();
        final File temp = new File("/tmp/bill.pdf");
        new LengthOf(new TeeInput(pdf, temp)).intValue();
        MatcherAssert.assertThat(
            new LengthOf(pdf).intValue(),
            Matchers.greaterThan(0)
        );
    }

}
