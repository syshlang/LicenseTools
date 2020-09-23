/*
 * Copyright (c) 2020.
 * @File: WebContextListener.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/9/22 下午4:20
 * @since:
 */

package com.syshlang.license.client.core;

import com.syshlang.license.client.constant.LicenseConstant;
import com.syshlang.license.client.util.LicenseVerifyUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author sunys
 */

public class WebContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (LicenseConstant.USE_LICENSE){
            LicenseVerifyUtil.clientLicenseInstall();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
