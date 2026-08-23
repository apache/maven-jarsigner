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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JarSignerCommandLineBuilderTest {

    @Test
    void passwordWithAmpersandShouldBeEscaped() throws Exception {
        JarSignerCommandLineBuilder builder = new JarSignerCommandLineBuilder();
        builder.setJarSignerFile("jarsigner");

        JarSignerSignRequest request = new JarSignerSignRequest();
        request.setStorepass("p&ssword");
        request.setAlias("myalias");
        request.setArchive(new File("test.jar"));

        Commandline cli = builder.build(request);
        String[] args = cli.getArguments();
        String argsString = Arrays.toString(args);

        assertTrue(
                argsString.contains("p^&ssword"), "Password with '&' should be escaped with '^', args: " + argsString);
    }

    @Test
    void keypassWithAmpersandShouldBeEscaped() throws Exception {
        JarSignerCommandLineBuilder builder = new JarSignerCommandLineBuilder();
        builder.setJarSignerFile("jarsigner");

        JarSignerSignRequest request = new JarSignerSignRequest();
        request.setKeypass("k&ypass");
        request.setAlias("myalias");
        request.setArchive(new File("test.jar"));

        Commandline cli = builder.build(request);
        String[] args = cli.getArguments();
        String argsString = Arrays.toString(args);

        assertTrue(argsString.contains("k^&ypass"), "Keypass with '&' should be escaped with '^', args: " + argsString);
    }

    @Test
    void passwordWithOtherSpecialCharsShouldBeEscaped() throws Exception {
        JarSignerCommandLineBuilder builder = new JarSignerCommandLineBuilder();
        builder.setJarSignerFile("jarsigner");

        JarSignerSignRequest request = new JarSignerSignRequest();
        request.setStorepass("p|ss<word>(1)@home^end");
        request.setAlias("myalias");
        request.setArchive(new File("test.jar"));

        Commandline cli = builder.build(request);
        String[] args = cli.getArguments();
        String argsString = Arrays.toString(args);

        assertTrue(
                argsString.contains("p^|ss^<word^>^(1^)^@home^^end"),
                "Password with special chars should be escaped, args: " + argsString);
    }

    @Test
    void passwordWithoutSpecialCharsShouldNotBeChanged() throws Exception {
        JarSignerCommandLineBuilder builder = new JarSignerCommandLineBuilder();
        builder.setJarSignerFile("jarsigner");

        JarSignerSignRequest request = new JarSignerSignRequest();
        request.setStorepass("simplepassword");
        request.setAlias("myalias");
        request.setArchive(new File("test.jar"));

        Commandline cli = builder.build(request);
        String[] args = cli.getArguments();
        String argsString = Arrays.toString(args);

        assertTrue(
                argsString.contains("simplepassword"),
                "Password without special chars should pass through unchanged, args: " + argsString);
    }

    @Test
    void nullPasswordShouldNotCauseError() throws Exception {
        JarSignerCommandLineBuilder builder = new JarSignerCommandLineBuilder();
        builder.setJarSignerFile("jarsigner");

        JarSignerSignRequest request = new JarSignerSignRequest();
        request.setAlias("myalias");
        request.setArchive(new File("test.jar"));

        Commandline cli = builder.build(request);
        String[] args = cli.getArguments();
        String argsString = Arrays.toString(args);

        assertTrue(
                !argsString.contains("-storepass"),
                "No -storepass should appear when password is null, args: " + argsString);
    }
}
