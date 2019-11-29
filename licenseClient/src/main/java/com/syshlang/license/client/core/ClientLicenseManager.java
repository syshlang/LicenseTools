/*
 * Copyright (c) 2019.
 * @File: ClientLicenseManager.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/11/20 下午8:14
 * @since:
 */

package com.syshlang.license.client.core;


import com.syshlang.license.client.constant.LicenseConstant;
import com.syshlang.license.client.util.LicenseVerifyUtil;
import com.syshlang.license.client.vo.LicenseVerifyParam;
import com.syshlang.license.common.util.SecurityUtils;
import com.syshlang.license.common.util.ServerInfoUtils;
import com.syshlang.license.common.vo.LicenseKeyStoreParam;
import de.schlichtherle.license.*;
import org.apache.commons.lang3.StringUtils;

import java.util.prefs.Preferences;

/**
 * @author sunys
 */
public class ClientLicenseManager extends LicenseManager {


    @Override
    protected synchronized LicenseContent verify(LicenseNotary licenseNotary) throws LicenseContentException {
        LicenseContent licenseContent;
        try {
            licenseContent = super.verify(licenseNotary);
            LicenseConstant.CLIENT_LICENSE_LAST_UPDATE_DATE = System.currentTimeMillis();
            LicenseConstant.CLIENT_LICENSE_VALID = true;
        } catch (Exception e) {
            LicenseConstant.CLIENT_LICENSE_VALID = false;
            throw new LicenseContentException(e.getLocalizedMessage());
        }
        return licenseContent;
    }

    @Override
    protected synchronized void validate(LicenseContent licenseContent) throws LicenseContentException {
        super.validate(licenseContent);
        if (LicenseConstant.CLIENT_LICENSE_LAST_UPDATE_DATE > System.currentTimeMillis()){
            throw new LicenseContentException(LicenseMessage.EXC_LICENSE_IS_NOT_YET_VALID);
        }
        String machineCodeReal = ServerInfoUtils.getMachineCode();
        if(StringUtils.isBlank(machineCodeReal)){
            throw new RuntimeException(LicenseMessage.EXC_NOT_MACHINE_INFO);
        }
        String  machineCode = (String) licenseContent.getExtra();
        if(StringUtils.isBlank(machineCode)){
            throw new LicenseContentException(LicenseMessage.EXC_LICENSE_IS_NOT_YET_VALID);
        }
        if (!machineCode.equals(machineCodeReal)){
            throw new RuntimeException(LicenseMessage.EXC_MACHINE_NO_AUTHORIZATION);
        }
    }




    private  LicenseVerifyParam getLicenseVerifyParam()  {
        LicenseVerifyParam licenseVerifyParam = new LicenseVerifyParam();
        licenseVerifyParam.setSubject(LicenseConstant.LICENSE_SUBJECT);
        licenseVerifyParam.setPublicAlias(LicenseConstant.LICENSE_PUBLICALIAS);
        if (StringUtils.isNotEmpty(LicenseConstant.LICENSE_STOREPASS)){
            String decryptAES = SecurityUtils.decryptAES(LicenseConstant.LICENSE_STOREPASS, LicenseConstant.KEY_LICENSELASTUPDATEDATE);
            licenseVerifyParam.setStorePass(decryptAES);
        }
        licenseVerifyParam.setLicensePath(LicenseConstant.LICENSE_LICENSEPATH);
        licenseVerifyParam.setPublicKeysStorePath(LicenseConstant.LICENSE_PUBLICCERTSPATH);
        return licenseVerifyParam;
    }

    public void initLicenseParam() {
        LicenseVerifyParam licenseVerifyParam = getLicenseVerifyParam();
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerifyUtil.class);
        CipherParam cipherParam = new DefaultCipherParam(licenseVerifyParam.getStorePass());
        KeyStoreParam publicStoreParam = new LicenseKeyStoreParam(LicenseVerifyUtil.class
                ,licenseVerifyParam.getPublicKeysStorePath()
                ,licenseVerifyParam.getPublicAlias()
                ,licenseVerifyParam.getStorePass()
                ,null);

        DefaultLicenseParam defaultLicenseParam = new DefaultLicenseParam(licenseVerifyParam.getSubject()
                , preferences
                , publicStoreParam
                , cipherParam);
        this.setLicenseParam(defaultLicenseParam);
    }
}
