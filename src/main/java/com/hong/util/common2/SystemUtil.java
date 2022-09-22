package com.hong.util.common2;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * 系统参数获取工具
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/09/22
 */
@Slf4j
public class SystemUtil {

    /**
     * 获取主机IP
     */
    public static String getHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 获取系统环境变量
     */
    public static Map<String, String> getSystemEnvMap() {
        return System.getenv();
    }

    /**
     * 获取 Java 环境变量
     */
    public static Properties getJavaEnvMap() {
        return System.getProperties();
    }

    /**
     * 获取主机名
     */
    public static String getComputeName() {
        return System.getenv().get("COMPUTERNAME");
    }

    /**
     * 获取 MAC 地址
     */
    public static String getLocalMac() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 利用 hutool 工具类中的封装方法获取本机mac地址
        return NetUtil.getMacAddress(inetAddress);
    }

    /**
     * 获取 MAC 地址2
     */
    private static String getLocalMac2() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.error("", e);
        }

        if (inetAddress == null) {
            return null;
        }
        //获取网卡，获取地址
        byte[] mac = new byte[0];
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            mac = networkInterface.getHardwareAddress();
        } catch (SocketException e) {
            log.error("", e);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append(":");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0").append(str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getHostAddress());
        getSystemEnvMap().forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
    }
}
