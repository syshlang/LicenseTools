/*
 * Copyright (c) 2020.
 * @File: LicenseHandleFilter.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.core.filter;


import com.syshlang.license.client.util.LicenseVerifyUtil;
import com.syshlang.license.common.util.ServerInfoUtils;
import de.schlichtherle.license.LicenseContent;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sunys
 */
@WebFilter(filterName="licenseHandleFilter",urlPatterns="/*")
public class LicenseHandleFilter implements Filter {
    private final String[] urls = {".js",".css",".ico",".jpg",".png"};
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 下面这段是用于检查licence的，在试用版中这一段代码要放开
        if (true){
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String spath = httpServletRequest.getServletPath();
            boolean flag = false;
            for (String str : urls) {
                if (spath.endsWith(str)) {
                    flag = true;
                    break;
                }
            }
            if (flag){
                chain.doFilter(request, response);
            }else{
                LicenseContent licenseContent = null;
                try {
                    licenseContent = LicenseVerifyUtil.clientLicenseVerify();
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errormsg",e.getLocalizedMessage());
                }
                if (licenseContent == null){
                    String machineCode = ServerInfoUtils.getMachineCode();
                    request.setAttribute("machineCode",machineCode);
                    request.getRequestDispatcher("/web/core/license/license.jsp").forward(request,response);
                    return;
                }else{
                    chain.doFilter(request, response);
                }
            }
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
