/*
 * Copyright (c) 2020.
 * @File: LicenseKeyStoreParam.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.common.vo;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.*;

/**
 * @author sunys
 */
public class LicenseKeyStoreParam extends AbstractKeyStoreParam {

    private String storePath;
    private String alias;
    private String storePwd;
    private String keyPwd;

    protected LicenseKeyStoreParam(Class aClass, String storePath) {
        super(aClass, storePath);
    }

    public LicenseKeyStoreParam(Class aClass, String storePath, String alias, String storePwd, String keyPwd) {
        super(aClass,storePath);
        this.storePath = storePath;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    @Override
    public InputStream getStream() throws IOException {
        final InputStream in = new FileInputStream(new File(storePath));
        if (null == in){
            throw new FileNotFoundException(storePath);
        }
        return in;
    }
}
