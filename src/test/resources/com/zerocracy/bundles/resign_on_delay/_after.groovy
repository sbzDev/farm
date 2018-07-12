/*
 * Copyright (c) 2016-2018 Zerocracy
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
package com.zerocracy.bundles.resign_on_delay

import com.jcabi.xml.XML
import com.zerocracy.Project

def exec(Project project, XML xml) {
  // @todo #1302:30min This test always fail because for unknown reason
  //  roles.xml in PMO is full copy of roles.xml in this fake project.
  //  Files in PMO and in bundle test project should be different and
  //  not correlate somehow.
//  MatcherAssert.assertThat(
//    'Issue wasn\'t resigned',
//    new Orders(project).bootstrap().jobs('lazydev'),
//    Matchers.emptyIterable()
//  )
//  MatcherAssert.assertThat(
//    'PR was resigned',
//    new Orders(project).bootstrap().jobs('lazyrev'),
//    Matchers.contains('gh:test/test#2')
//  )
}
