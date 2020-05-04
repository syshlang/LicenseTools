/*
 * Copyright (c) 2020.
 * @File: ServerLicenseManagerHolder.java
 * @Description: 
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
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
