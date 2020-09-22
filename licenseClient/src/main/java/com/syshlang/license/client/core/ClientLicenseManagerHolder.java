/*
 * Copyright (c) 2020.
 * @File: ClientLicenseManagerHolder.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.client.core;

import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;

/**
 * @author sunys
 */
public class ClientLicenseManagerHolder {

    private static volatile LicenseManager  CLIENT_LICENSE_MANAGER;

    public static LicenseManager getInstance() {
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
