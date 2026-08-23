/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.shared.jarsigner;

import java.io.File;
import java.util.Arrays;

import org.apache.maven.shared.utils.cli.Commandline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JarSignerCommandLineBuilderTest {

    private JarSignerCommandLineBuilder builder;
    private JarSignerSignRequest request;

    @BeforeEach
    void setUp() throws Exception {
        builder = new JarSignerCommandLineBuilder();
        builder.setJarSignerFile("jarsigner");

        request = new JarSignerSignRequest();
        request.setAlias("myalias");
        request.setArchive(new File("test.jar"));
    }

    private String buildArgsString() throws Exception {
        Commandline cli = builder.build(request);
        return Arrays.toString(cli.getArguments());
    }

    @Test
    void passwordWithAmpersandShouldBeEscaped() throws Exception {
        request.setStorepass("p&ssword");

        assertTrue(buildArgsString().contains("p^&ssword"), "Password with '&' should be escaped with '^'");
    }

    @Test
    void keypassWithAmpersandShouldBeEscaped() throws Exception {
        request.setKeypass("k&ypass");

        assertTrue(buildArgsString().contains("k^&ypass"), "Keypass with '&' should be escaped with '^'");
    }

    @Test
    void passwordWithOtherSpecialCharsShouldBeEscaped() throws Exception {
        request.setStorepass("p|ss<word>(1)@home^end");

        assertTrue(
                buildArgsString().contains("p^|ss^<word^>^(1^)^@home^^end"),
                "Password with special chars should be escaped");
    }

    @Test
    void passwordWithoutSpecialCharsShouldNotBeChanged() throws Exception {
        request.setStorepass("simplepassword");

        assertTrue(
                buildArgsString().contains("simplepassword"),
                "Password without special chars should pass through unchanged");
    }

    @Test
    void nullPasswordShouldNotCauseError() throws Exception {
        String argsString = buildArgsString();

        assertFalse(argsString.contains("-storepass"), "No -storepass should appear when password is null");
    }
}
