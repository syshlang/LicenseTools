/*
 * Copyright (c) 2020.
 * @File: LicenseConstant.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.client.constant;

import com.syshlang.license.common.util.SecurityUtils;

/**
 * @author sunys
 */
public class LicenseConstant {

    private LicenseConstant() {
    }

    public static final String KEY_LICENSELASTUPDATEDATE = "SYSHLANG_KEY_LICENSELASTUPDATEDATE";
    public static long CLIENT_LICENSE_LAST_UPDATE_DATE;


    public final static String LICENSE_SUBJECT = "SYSHLANG_LICENSE";
    public final static String LICENSE_PUBLICALIAS = "SYSHLANG";
    public final static String LICENSE_STOREPASS = SecurityUtils.encryptAES("12345A67",KEY_LICENSELASTUPDATEDATE);
    public final static String LICENSE_PUBLICCERTSPATH = "C:/publicCerts.keystore";
    public final static String LICENSE_LICENSEPATH = "C:/license.lic";

}
