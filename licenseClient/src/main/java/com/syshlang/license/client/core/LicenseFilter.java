/*
 * Copyright (c) 2020.
 * @File: LicenseFilter.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/9/21 下午4:00
 * @since:
 */

package com.syshlang.license.client.core;

import com.syshlang.license.client.constant.LicenseConstant;
import com.syshlang.license.client.util.LicenseVerifyUtil;
import com.syshlang.license.common.util.ServerInfoUtils;
import de.schlichtherle.license.LicenseContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sunys
 */
@WebFilter(urlPatterns = "/*",filterName="SimpleFilter")
@Order(1)
@Slf4j
public class LicenseFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
        if (LicenseConstant.USE_LICENSE){
            log.debug("Certificate verification is enabled.");
            LicenseContent licenseContent = null;
            try {
                licenseContent = LicenseVerifyUtil.clientLicenseVerify();
            } catch (Exception e) {
                e.printStackTrace();
                httpRequest.setAttribute("errormsg",e.getMessage());
                log.debug("Certificate verification failed："+e.getMessage());
            }
            if (licenseContent == null){
                String machineCode = ServerInfoUtils.getMachineCode();
                httpRequest.setAttribute("machineCode",machineCode);
                httpRequest.getRequestDispatcher("/web/core/license/license.jsp").forward(httpRequest, httpResponse);
                return;
            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
