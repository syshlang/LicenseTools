/*
 * Copyright (c) 2020.
 * @File: LicenseMessage.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.client.core;

import de.schlichtherle.util.ObfuscatedString;

/**
 * @author sunys
 */
public class LicenseMessage {
    public static final String EXC_NOT_MACHINE_INFO = "Invalid machine info!";
    public static final String EXC_MACHINE_NO_AUTHORIZATION = "The machine is not authorized!";

    public static final String EXC_LICENSE_IS_NOT_YET_VALID;

    static {
        EXC_LICENSE_IS_NOT_YET_VALID = (new ObfuscatedString(new long[]{5434633639502011825L, -3406117476263181371L, 6903673940810780388L, -6816911225052310716L})).toString();
    }
}
