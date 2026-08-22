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
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created on 11/8/13.
 *
 * @author Tony Chemit
 * @since 1.1
 */
class JarSignerUtilTest extends AbstractJarSignerTest {

    @TempDir
    File tempDir;

    @Test
    void unsignArchive() throws Exception {

        File target = prepareTestJar("javax.persistence_2.0.5.v201212031355.jar");

        assertTrue(JarSignerUtil.isArchiveSigned(target));

        // check that manifest contains some digest attributes
        Manifest originalManifest = readManifest(target);
        assertTrue(containsDigest(originalManifest));

        Manifest originalCleanManifest = JarSignerUtil.buildUnsignedManifest(originalManifest);
        assertFalse(containsDigest(originalCleanManifest));

        assertEquals(originalCleanManifest, JarSignerUtil.buildUnsignedManifest(originalCleanManifest));

        JarSignerUtil.unsignArchive(target);

        assertFalse(JarSignerUtil.isArchiveSigned(target));

        // check that manifest has no digest entry
        // see https://issues.apache.org/jira/browse/MSHARED-314
        Manifest manifest = readManifest(target);

        Manifest cleanManifest = JarSignerUtil.buildUnsignedManifest(manifest);
        assertFalse(containsDigest(cleanManifest));

        assertEquals(manifest, cleanManifest);
        assertEquals(manifest, originalCleanManifest);
    }

    @Test
    void isZipFileWithPrependedData() throws Exception {
        // Simulate a Spring Boot "fully executable" jar: shell script prepended before the real jar.
        File realJar = prepareTestJar("javax.persistence_2.0.5.v201212031355.jar");
        byte[] jarBytes = Files.readAllBytes(realJar.toPath());

        File executableJar = new File(tempDir, "executable.jar");
        try (OutputStream out = Files.newOutputStream(executableJar.toPath())) {
            out.write("#!/bin/bash\nexec java \"$@\" -jar \"$0\"\n".getBytes());
            out.write(jarBytes);
        }

        assertTrue(JarSignerUtil.isZipFile(executableJar));
    }

    @Test
    void isZipFileWithNull() {
        assertThrows(NullPointerException.class, () -> JarSignerUtil.isZipFile(null));
    }

    @Test
    void unsignArchiveConcurrent() throws Exception {
        File target = prepareTestJar("javax.persistence_2.0.5.v201212031355.jar");

        // Use a latch to make both threads start unsigning at the same moment,
        // maximizing the window for the race condition on the shared .unsigned path.
        CountDownLatch startLatch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Future<?> f1 = executor.submit(() -> {
                try {
                    startLatch.await();
                    JarSignerUtil.unsignArchive(target);
                } catch (Exception e) {
                    // One thread may fail with NoSuchFileException because the other
                    // already moved the shared .unsigned file. This is the race condition.
                }
            });

            Future<?> f2 = executor.submit(() -> {
                try {
                    startLatch.await();
                    JarSignerUtil.unsignArchive(target);
                } catch (Exception e) {
                    // Same as above.
                }
            });

            startLatch.countDown();

            f1.get();
            f2.get();
        } finally {
            executor.shutdownNow();
        }

        // The jar must still be openable and readable after concurrent unsigning.
        try (JarFile jar = new JarFile(target)) {
            assertNotNull(jar.getManifest(), "jar manifest must be readable after concurrent unsign");
        }

        // Verify no orphaned .unsigned files remain in the jar's parent directory.
        File jarDir = target.getAbsoluteFile().getParentFile();
        String[] leftover = jarDir.list((dir, name) -> name.endsWith(".unsigned"));
        assertEquals(0, leftover.length, "no .unsigned files should remain after concurrent unsign");
    }

    private Manifest readManifest(File file) throws IOException {
        JarFile jarFile = new JarFile(file);

        Manifest manifest = jarFile.getManifest();

        jarFile.close();

        return manifest;
    }

    private boolean containsDigest(Manifest manifest) {
        for (Map.Entry<String, Attributes> entry : manifest.getEntries().entrySet()) {
            Attributes attr = entry.getValue();

            for (Map.Entry<Object, Object> objectEntry : attr.entrySet()) {
                String attributeKey = String.valueOf(objectEntry.getKey());
                if (attributeKey.endsWith("-Digest")) {
                    return true;
                }
            }
        }
        return false;
    }
}
