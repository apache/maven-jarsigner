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

/**
 * Specifies the parameters used to control a jar signer sign operation invocation.
 *
 * @author Tony Chemit
 * @since 1.0
 */
public class JarSignerSignRequest extends AbstractJarSignerRequest {

    /**
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jarsigner.html#CCHIFIAD">options</a>.
     */
    private String keypass;

    /**
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jarsigner.html#CCHIFIAD">options</a>.
     */
    private String sigfile;

    /**
     * The {@code -tsa} parameter, the URL to the Time Stamping Authority (TSA) server.
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jarsigner.html#CCHIFIAD">options</a>.
     */
    private String tsaLocation;

    /**
     * The {@code -tsacert} parameter. Alias for a certificate in the active keystore. From the certificate the X509v3
     * extension "Subject Information Access" field is examined to find a TSA server URL.
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jarsigner.html#CCHIFIAD">options</a>.
     */
    private String tsaAlias;

    /**
     * The {@code -tsapolicyid} parameter, an OID to send to the TSA server to identify the policy ID the server should
     * use. See
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jarsigner.html#CCHIFIAD">options</a>.
     *
     * @since 3.1.0
     */
    private String tsapolicyid;

    /**
     * The {@code -tsadigestalg} parameter, the message digest algorithm for TSA server to use. Only available in Java
     * 11 and later. See
     * <a href="https://docs.oracle.com/en/java/javase/11/tools/jarsigner.html">options</a>.
     *
     * @since 3.1.0
     */
    private String tsadigestalg;

    /**
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jarsigner.html#CCHIFIAD">options</a>.
     */
    protected File signedjar;

    /**
     * Location of the extra certchain file to be used during signing.
     *
     * See <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/jarsigner.html#CCHIFIAD">options</a>.
     * @since 3.0.0
     */
    protected File certchain;

    public String getKeypass() {
        return keypass;
    }

    public String getSigfile() {
        return sigfile;
    }

    public String getTsaLocation() {
        return tsaLocation;
    }

    public String getTsaAlias() {
        return tsaAlias;
    }

    public String getTsapolicyid() {
        return tsapolicyid;
    }

    public String getTsadigestalg() {
        return tsadigestalg;
    }

    public void setKeypass(String keypass) {
        this.keypass = keypass;
    }

    public void setSigfile(String sigfile) {
        this.sigfile = sigfile;
    }

    public void setTsaLocation(String tsaLocation) {
        this.tsaLocation = tsaLocation;
    }

    public void setTsaAlias(String tsaAlias) {
        this.tsaAlias = tsaAlias;
    }

    public void setTsapolicyid(String tsapolicyid) {
        this.tsapolicyid = tsapolicyid;
    }

    public void setTsadigestalg(String tsadigestalg) {
        this.tsadigestalg = tsadigestalg;
    }

    public File getSignedjar() {
        return signedjar;
    }

    public void setSignedjar(File signedjar) {
        this.signedjar = signedjar;
    }

    /**
     * Sets certchain to be used.
     *
     * @param certchain Cert Chain file path or {@code null} to remove the option
     * @since 3.0.0
     */
    public void setCertchain(File certchain) {
        this.certchain = certchain;
    }

    /**
     * Get certificate chain.
     * @return Path to the certificate chain file or {@code null} if undefined
     */
    public File getCertchain() {
        return certchain;
    }
}
