/*
 * Copyright (c) 2019. GRGBanking
 * @File: LicenseVerifyUtil.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/11/21 下午1:47
 * @since:
 */

package com.syshlang.license.client.util;


import com.syshlang.license.client.constant.LicenseConstant;
import com.syshlang.license.client.core.ClientLicenseManagerHolder;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class LicenseVerifyUtil {
    private static Logger logger = Logger.getLogger(LicenseVerifyUtil.class.getName());
    private static final String PARAM_CONFIG_KEY_LICENSELASTUPDATEDATE = "LICENSELASTUPDATEDATE";

    public static synchronized void clientLicenseInstall(){
        try{
            LicenseManager licenseManager = ClientLicenseManagerHolder.getInstance();
            licenseManager.uninstall();
            LicenseContent licenseContent = licenseManager.install(new File(LicenseConstant.LICENSE_LICENSEPATH));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logger.info(MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static synchronized LicenseContent clientLicenseVerify() {
        try {
            LicenseManager licenseManager = ClientLicenseManagerHolder.getInstance();
            LicenseContent licenseContent = licenseManager.verify();
            long timeMillis = System.currentTimeMillis();
            //更新时间
            return licenseContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
