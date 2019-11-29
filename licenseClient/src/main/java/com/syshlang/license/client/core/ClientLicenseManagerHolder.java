/*
 * Copyright (c) 2019.
 * @File: LicenseManagerHolder.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/11/29 下午4:09
 * @since:
 */

package com.syshlang.license.client.core;

import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;

public class ClientLicenseManagerHolder {

    private static volatile LicenseManager  CLIENT_LICENSE_MANAGER;

    public static LicenseManager getInstance() throws LicenseContentException {
        if(CLIENT_LICENSE_MANAGER == null){
            synchronized (ClientLicenseManagerHolder.class){
                if(CLIENT_LICENSE_MANAGER == null){
                    ClientLicenseManager clientLicenseManager = new ClientLicenseManager();
                    clientLicenseManager.initLicenseParam();
                    CLIENT_LICENSE_MANAGER = clientLicenseManager;
                }
            }
        }
        return CLIENT_LICENSE_MANAGER;
    }
}
