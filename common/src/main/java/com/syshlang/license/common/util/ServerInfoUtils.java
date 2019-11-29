/*
 * Copyright (c) 2019.
 * @File: ServerInfoUtils.java
 * @Description:
 * @Author: sunys
 * @Date: 2019/11/29 下午3:43
 * @since:
 */

package com.syshlang.license.common.util;


import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;


/**
 * @author sunys
 */
public class ServerInfoUtils {

    private static Logger logger = Logger.getLogger(ServerInfoUtils.class.getName());

    /**
     * 获取本地Mac地址
     * @return
     * @throws Exception
     */
    public static  String getLocalMacAddress() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String macByInetAddress = getMacByInetAddress(localHost);
        return macByInetAddress;
    }

    /**
     * 获取CPU序列号
     * @return
     * @throws Exception
     */
    public static String getCPUSerial() throws IOException {
        String serialNumber = "";
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("windows")) {
            String[] cmd = {"cmd","/C","wmic cpu get processorid"};
            serialNumber = commandExec(cmd);
        }else{
            String[] shell = {"/bin/bash","-c","dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
            serialNumber = commandExec(shell);
        }
        return serialNumber;
    }


    private  static String commandExec(String[] shell) throws IOException {
        if (shell == null || shell.length ==0){
            return null;
        }
        String result = "";
        String osName = System.getProperty("os.name").toLowerCase();
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();
        if (osName.startsWith("windows")) {
            Scanner scanner = new Scanner(process.getInputStream());
            if(scanner != null && scanner.hasNext()){
                scanner.next();
            }
            if(scanner.hasNext()){
                result = scanner.next().trim();
            }
            scanner.close();
        }else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine().trim();
            if(StringUtils.isNotBlank(line)){
                result = line;
            }
            reader.close();
        }
        return result;
    }



    /**
     * 获取某个网络接口的Mac地址
     * @param inetAddr
     * @return
     */
    private static  String getMacByInetAddress(InetAddress inetAddr){
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    stringBuffer.append("-");
                }
                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    stringBuffer.append("0" + temp);
                }else{
                    stringBuffer.append(temp);
                }
            }

            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据mac和cpu序列号生成机器码
     * @return
     * @throws Exception
     */
    public static  String getMachineCode()  {
        try {
            String localMacAddress = getLocalMacAddress();
            String cpuSerial = getCPUSerial();
            if (StringUtils.isNotBlank(localMacAddress) && StringUtils.isNotBlank(cpuSerial)){
                return SecurityUtils.encryptAES(localMacAddress+cpuSerial);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("获取LocalMac地址:"+getLocalMacAddress());
        System.out.println("获取CPU序列号:"+getCPUSerial());
        System.out.println("根据mac和cpu序列号生成机器码:"+getMachineCode());

    }
}
