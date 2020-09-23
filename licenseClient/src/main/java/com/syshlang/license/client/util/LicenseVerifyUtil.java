/*
 * Copyright (c) 2020.
 * @File: LicenseVerifyUtil.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
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

/**
 * @author sunys
 */
public class LicenseVerifyUtil {
    private static Logger logger = Logger.getLogger(LicenseVerifyUtil.class.getName());

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


    public static synchronized LicenseContent clientLicenseVerify() throws Exception {
        LicenseManager licenseManager = ClientLicenseManagerHolder.getInstance();
        LicenseContent licenseContent = licenseManager.verify();
        //更新时间, 可以将最后一次的更新时间保存，防止用户通过篡改服务器时间来绕过有效期校验
        LicenseConstant.CLIENT_LICENSE_LAST_UPDATE_DATE = System.currentTimeMillis();
        return licenseContent;
    }

}
