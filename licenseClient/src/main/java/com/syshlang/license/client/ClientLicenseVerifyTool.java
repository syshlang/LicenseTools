/*
 * Copyright (c) 2020.
 * @File: ClientLicenseVerifyTool.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.client;


import com.syshlang.license.client.util.LicenseVerifyUtil;

/**
 * @author sunys
 */
public class ClientLicenseVerifyTool {

    public static void main(String[] args) throws Exception {
        LicenseVerifyUtil.clientLicenseInstall();
        LicenseVerifyUtil.clientLicenseVerify();
    }
}
