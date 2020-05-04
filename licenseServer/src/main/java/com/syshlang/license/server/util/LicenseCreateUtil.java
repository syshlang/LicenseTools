/*
 * Copyright (c) 2020.
 * @File: LicenseCreateUtil.java
 * @Description:
 * @Author: sunys
 * @Date: 2020/5/4 下午6:01
 * @since:
 */

package com.syshlang.license.server.util;



import com.syshlang.license.common.vo.LicenseKeyStoreParam;
import com.syshlang.license.server.constant.LicenseConstant;
import com.syshlang.license.server.core.ServerLicenseManagerHolder;
import com.syshlang.license.server.vo.LicenseCreateParam;
import de.schlichtherle.license.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class LicenseCreateUtil {
    private static Logger logger = Logger.getLogger(LicenseCreateUtil.class.getName());

    public static boolean createLicense(){
        try {
            LicenseCreateParam licenseCreateParam = initLicenseCreateParam();
            if (licenseCreateParam == null){
                return false;
            }
            LicenseParam licenseParam = initLicenseParam(licenseCreateParam);
            if (licenseParam == null){
                return false;
            }
            LicenseManager licenseManager = ServerLicenseManagerHolder.getInstance(licenseParam);
            LicenseContent licenseContent = initLicenseContent(licenseCreateParam);
            licenseManager.store(licenseContent,new File(licenseCreateParam.getLicensePath()));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            logger.info(MessageFormat.format("证书生成成功\n------------------" +
                            "\n生成日期:{0}" +
                            "\n证书有效期：{1} 至 {2}" +
                            "\n证书路径：{3}" +
                            "\n唯一识别码：{4}" +
                            "\n描述： {5}\n------------------",
                    format.format(licenseCreateParam.getIssuedTime()),
                    format.format(licenseContent.getNotBefore()),
                    format.format(licenseContent.getNotAfter()),
                    licenseCreateParam.getLicensePath(),
                    licenseCreateParam.getMachineCode(),
                    licenseCreateParam.getDescription()));
            return true;
        }catch (Exception e){
            logger.info(e.getLocalizedMessage());
        }
        return false;
    }

    private static LicenseContent initLicenseContent(LicenseCreateParam licenseCreateParam) {
        if (licenseCreateParam == null){
            return null;
        }
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(LicenseConstant.DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(LicenseConstant.DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setSubject(licenseCreateParam.getSubject());
        licenseContent.setIssued(licenseCreateParam.getIssuedTime());
        licenseContent.setNotBefore(licenseCreateParam.getIssuedTime());
        licenseContent.setNotAfter(licenseCreateParam.getExpiryTime());
        licenseContent.setConsumerType(licenseCreateParam.getConsumerType());
        licenseContent.setConsumerAmount(licenseCreateParam.getConsumerAmount());
        licenseContent.setInfo(licenseCreateParam.getDescription());
        licenseContent.setExtra(licenseCreateParam.getMachineCode());
        return licenseContent;
    }

    private static LicenseParam initLicenseParam(LicenseCreateParam licenseCreateParam) {
        if (licenseCreateParam == null){
            return null;
        }
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreateUtil.class);
        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(licenseCreateParam.getStorePass());
        KeyStoreParam privateStoreParam = new LicenseKeyStoreParam(LicenseCreateUtil.class
                ,licenseCreateParam.getPrivateKeysStorePath()
                ,licenseCreateParam.getPrivateAlias()
                ,licenseCreateParam.getStorePass()
                ,licenseCreateParam.getKeyPass());

        LicenseParam licenseParam = new DefaultLicenseParam(licenseCreateParam.getSubject()
                ,preferences
                ,privateStoreParam
                ,cipherParam);

        return licenseParam;
    }

    private static LicenseCreateParam initLicenseCreateParam() {
        Properties properties = new Properties();
        try {
            String realPath = getRealPath();
            realPath = realPath.substring(0, realPath.lastIndexOf(File.separator));
            System.out.println(realPath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(realPath+File.separator+"config.properties"));
            properties.load(bufferedReader);
        } catch (FileNotFoundException e) {
            logger.info("配置文件不存在！");
            return null;
        } catch (IOException e) {
            logger.info("读取配置文件发生错误！");
            return null;
        }

        String licenseSubject = properties.getProperty("license.subject");
        if (StringUtils.isEmpty(licenseSubject)){
            logger.info("请配置证书subject！");
            return null;
        }
        String licensePrivatealias =  properties.getProperty("license.privatealias");
        if (StringUtils.isEmpty(licensePrivatealias)){
            logger.info("请配置密钥别称！");
            return null;
        }
        String licenseKeypass =  properties.getProperty("license.keypass");
        if (StringUtils.isEmpty(licenseSubject)){
            logger.info("请配置密钥密码！");
            return null;
        }
        String licensePrivatekeysstorepath =  properties.getProperty("license.privatekeysstorepath");
        if (StringUtils.isEmpty(licenseSubject)){
            logger.info("请配置密钥库存储路径！");
            return null;
        }
        String licenseStorepass =  properties.getProperty("license.storepass");
        if (StringUtils.isEmpty(licenseStorepass)){
            logger.info("请配置访问秘钥库的密码！");
            return null;
        }
        String licenseIssuedtime =  properties.getProperty("license.issuedtime");
        if (StringUtils.isEmpty(licenseIssuedtime)){
            logger.info("请配置证书生效时间，格式： yyyy-MM-dd HH:mm:ss！");
            return null;
        }
        String licenseExpirytime =  properties.getProperty("license.expirytime");
        if (StringUtils.isEmpty(licenseExpirytime)){
            logger.info("请配置证书失效时间，格式： yyyy-MM-dd HH:mm:ss！");
            return null;
        }
        String licenseMachinecode =  properties.getProperty("license.machinecode");
        if (StringUtils.isBlank(licenseMachinecode)){
            logger.info("请配置证书使用的机器码！");
            return null;
        }
        String licenseDescription =  properties.getProperty("license.description");
        String licenseLicensepath =  properties.getProperty("license.licensepath");
        if (StringUtils.isEmpty(licenseLicensepath)){
            logger.info("请配置证书生成路径！");
            return null;
        }
        LicenseCreateParam licenseCreateParam = new LicenseCreateParam();
        licenseCreateParam.setSubject(licenseSubject);
        licenseCreateParam.setPrivateAlias(licensePrivatealias);
        licenseCreateParam.setKeyPass(licenseKeypass);
        licenseCreateParam.setStorePass(licenseStorepass);
        licenseCreateParam.setLicensePath(licenseLicensepath);
        licenseCreateParam.setPrivateKeysStorePath(licensePrivatekeysstorepath);
        SimpleDateFormat simpleDateFormat = null;
        try {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date issuedDate = simpleDateFormat.parse(licenseIssuedtime);
            licenseCreateParam.setIssuedTime(issuedDate);
        } catch (ParseException e) {
            logger.info("证书生效时间不合法！");
            return null;
        }
        try {
            Date expiryDate = simpleDateFormat.parse(licenseExpirytime);
            licenseCreateParam.setExpiryTime(expiryDate);
        } catch (ParseException e) {
            logger.info("证书失效时间不合法！");
            return null;
        }
        //licenseCreateParam.setConsumerType();
        //licenseCreateParam.setConsumerAmount();
        licenseCreateParam.setMachineCode(licenseMachinecode);
        licenseCreateParam.setDescription(licenseDescription);
        return licenseCreateParam;
    }

    private static String getRealPath(){
        String rootPath = LicenseCreateUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (StringUtils.isNotEmpty(rootPath)){
            if (SystemUtils.IS_OS_WINDOWS){
                if (rootPath.startsWith("/")){
                    rootPath = rootPath.length() >= 2 ? rootPath.substring(1) : "";
                }
                rootPath = rootPath.replaceAll("/", "\\\\");
                rootPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator))+File.separator;
            }
        }
        return rootPath;
    }
}
