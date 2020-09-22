/*
 * Copyright (c) 2020.
 * @File: SecurityUtils.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.common.util;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.commons.lang3.StringUtils;


/**
 * 加密工具类
 * @author sunys
 */
public class SecurityUtils {
    //密钥
    private final static byte[] key = "0CoJUm6Qyw8W8jud".getBytes();
    //构建
    private final static SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES.getValue(),key);


    public static String encryptAES(String content) {
        if (StringUtils.isNotBlank(content)){
            return aes.encryptHex(content);
        }
        return null;
    }

    public static String decryptAES(String content) {
        if (StringUtils.isNotBlank(content)){
            return aes.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(encryptAES("12345678A"));
        System.out.println(decryptAES(encryptAES("12345678A")));
    }
}
