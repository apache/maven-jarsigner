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

import org.apache.maven.shared.utils.cli.javatool.AbstractJavaToolRequest;

/**
 * Specifies the commons parameters used to control a jar signer invocation.
 *
 * @author Tony Chemit
 * @since 1.0
 */
public abstract class AbstractJarSignerRequest extends AbstractJavaToolRequest implements JarSignerRequest {
    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private boolean verbose;

    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private String keystore;

    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private String storetype;

    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private String storepass;

    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private String alias;

    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private String providerName;

    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private String providerClass;

    /**
     * See <a href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    private String providerArg;

    /**
     * The maximum memory available to the JAR signer, e.g. <code>256M</code>. See <a
     * href="http://docs.oracle.com/javase/6/docs/technotes/tools/windows/java.html#Xms">-Xmx</a> for more details.
     */
    private String maxMemory;

    /**
     * List of additional arguments to append to the jarsigner command line.
     */
    private String[] arguments;

    /**
     * Location of the working directory.
     */
    private File workingDirectory;

    /**
     * Archive to treat.
     */
    private File archive;

    /**
     * See <a href="http://java.sun.com/javase/6/docs/technotes/tools/windows/jarsigner.html#Options">options</a>.
     */
    protected boolean protectedAuthenticationPath;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKeystore() {
        return keystore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStoretype() {
        return storetype;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStorepass() {
        return storepass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProviderName() {
        return providerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProviderClass() {
        return providerClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProviderArg() {
        return providerArg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaxMemory() {
        return maxMemory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getArguments() {
        return arguments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getArchive() {
        return archive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProtectedAuthenticationPath() {
        return protectedAuthenticationPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStoretype(String storetype) {
        this.storetype = storetype;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStorepass(String storepass) {
        this.storepass = storepass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProviderClass(String providerClass) {
        this.providerClass = providerClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProviderArg(String providerArg) {
        this.providerArg = providerArg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxMemory(String maxMemory) {
        this.maxMemory = maxMemory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArguments(String... arguments) {
        this.arguments = arguments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArchive(File archive) {
        this.archive = archive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProtectedAuthenticationPath(boolean protect) {
        this.protectedAuthenticationPath = protect;
    }
}
