/*
 * Copyright (c) 2019.
 * @File: LicenseConstant.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/11/29 下午4:27
 * @since:
 */

package com.syshlang.license.client.constant;

import com.syshlang.license.common.util.SecurityUtils;

public class LicenseConstant {
    public static final String KEY_LICENSELASTUPDATEDATE = "SYSHLANG_KEY_LICENSELASTUPDATEDATE";
    public static long CLIENT_LICENSE_LAST_UPDATE_DATE;


    public final static String LICENSE_SUBJECT = "SYSHLANG_LICENSE";
    public final static String LICENSE_PUBLICALIAS = "SYSHLANG";
    public final static String LICENSE_STOREPASS = SecurityUtils.encryptAES("12345A67",KEY_LICENSELASTUPDATEDATE);
    public final static String LICENSE_PUBLICCERTSPATH = "C:/publicCerts.keystore";
    public final static String LICENSE_LICENSEPATH = "C:/license.lic";

}
