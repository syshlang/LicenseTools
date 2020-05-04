/*
 * Copyright (c) 2019.
 * @File: ServerCreateLicenseTool.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/11/21 下午3:19
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
