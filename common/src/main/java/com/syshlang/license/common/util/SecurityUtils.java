/*
 * Copyright (c) 2020.
 * @File: SecurityUtils.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.common.util;



import com.syshlang.license.common.constant.Commononstant;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * 数据安全加密工具类
 */
public class SecurityUtils {

    private static final String DEFAULT_DES_CRYPT_KEY = "SYSHLANG";
    private static final String DEFAULT_AES_CRYPT_KEY = "SYSHLANG";
    
    private static final int  HEXADECIMAL = 16;
    private static final int  BYTE2HEXSTR = 0xFF;
    /**
     * 对输入的字符串进行MD5加密
     * 
     * @param str
     *            需要加密的字符串
     * @return MD5加密后的字符串
     */
    public static String getMD5(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(str.getBytes());
            return new String(Hex.encodeHex(digest));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 使用默认密钥进行 AES加密
     * 
     * @param content
     *            须加密内容
     * @return 字符串
     */
    public static String encryptAES(String content) {
        return encryptAES(content, DEFAULT_AES_CRYPT_KEY);
    }

    /**
     * AES加密
     * 
     * @param content
     *            须加密内容
     * @param key
     *            密钥
     * @return 字符串
     */
    public static String encryptAES(String content, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kgen.init(Commononstant.KEYGENERATOR_KEYSIZE, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 使用默认密钥进行 AES解密
     * 
     * @param content
     * @return 字符串
     */
    public static String decryptAES(String content) {
        return decryptAES(content, DEFAULT_AES_CRYPT_KEY);
    }

    /**
     * AES解密
     * 
     * @param content
     *            待解密内容
     * @param key
     *            解密密钥
     * @return 字符串
     */
    public static String decryptAES(String content, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kgen.init(Commononstant.KEYGENERATOR_KEYSIZE, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));
            return new String(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 使用默认密钥进行 AES加密
     * 
     * @param content
     *            须加密内容
     * @return 字符串
     */
    public static String encryptDES(String content) {
        return encryptDES(content, DEFAULT_DES_CRYPT_KEY);
    }

    /**
     * 加密
     * 
     * @param src
     *            数据源
     * @param key
     *            密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception
     */
    public static String encryptDES(String src, String key) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            return parseByte2HexStr(cipher.doFinal(src.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 使用默认密钥进行 DES解密
     * 
     * @param content
     *            待解密内容
     * @return 字符串
     */
    public static String decryptDES(String content) {
        return decryptDES(content, DEFAULT_DES_CRYPT_KEY);
    }

    /**
     * 解密
     * 
     * @param src
     *            数据源
     * @param key
     *            密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    public static String decryptDES(String src, String key) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            return new String(cipher.doFinal(parseHexStr2Byte(src)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     *            字节数组
     * @return 字符串
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & BYTE2HEXSTR);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     *            16进制字符串
     * @return 字节数组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), HEXADECIMAL);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), HEXADECIMAL);
            result[i] = (byte) (high * HEXADECIMAL + low);
        }
        return result;
    }

    /**
     * 对字符串进行MD5编码(清机系统用户密码加密)
     * 
     * @param str
     *            待编码字符串
     * @return 字符串
     */
    public static String encodeByMD5(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(str.getBytes());
            return new String(Hex.encodeHex(digest));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(encryptAES("123456"));
        System.out.println(decryptAES(encryptAES("123456")));
    }
}
