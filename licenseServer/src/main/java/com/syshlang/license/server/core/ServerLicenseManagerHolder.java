/*
 * Copyright (c) 2019.
 * @File: LicenseManagerHolder.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/11/29 下午4:02
 * @since:
 */

package com.syshlang.license.server.core;

import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class ServerLicenseManagerHolder {

    private static volatile ServerLicenseManager  SERVER_LICENSE_MANAGER;

    public static LicenseManager getInstance(LicenseParam licenseParam) throws LicenseContentException {
        if(SERVER_LICENSE_MANAGER == null){
            synchronized (ServerLicenseManagerHolder.class){
                if(SERVER_LICENSE_MANAGER == null){
                    SERVER_LICENSE_MANAGER = new ServerLicenseManager(licenseParam);
                }
            }
        }
        return SERVER_LICENSE_MANAGER;
    }
}
