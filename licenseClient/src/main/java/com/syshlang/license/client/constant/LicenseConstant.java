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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author sunys
 */
public class LicenseConstant {

    public static final boolean USE_LICENSE = true;
    private static Properties properties = null;
    static {
        try {
            if (properties == null) {
                properties = new Properties();
            }
            InputStream in = LicenseConstant.class.getResourceAsStream("/config.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LicenseConstant() {
    }

    public static final String KEY_LICENSELASTUPDATEDATE = "SYSHLANG_KEY_LICENSELASTUPDATEDATE";
    public static long CLIENT_LICENSE_LAST_UPDATE_DATE;


    public final static String LICENSE_SUBJECT = properties.getProperty("license.subject");
    public final static String LICENSE_PUBLICALIAS = properties.getProperty("license.publicalias");
    public final static String LICENSE_STOREPASS = properties.getProperty("license.storepass");
    public final static String LICENSE_PUBLICCERTSPATH = Thread.currentThread().getContextClassLoader().getResource(
            "/").getPath()+"certs"+ File.separator+"public.keystore";
    public final static String LICENSE_LICENSEPATH = properties.getProperty("license.licensepath");

}
