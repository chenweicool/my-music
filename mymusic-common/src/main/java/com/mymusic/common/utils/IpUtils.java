package com.mymusic.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 有关ip的工具类
 */
public class IpUtils {

    /**
     * 获得主机Ip地址
     * @return
     */
    public static String getHostIp()
    {
        try
        {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
        }
        return "127.0.0.1";
    }

    /**
     * 获取主机的内容信息
     * @return 返回主机的信息
     */
    public static String getHostName()
    {
        try
        {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e)
        {
        }
        return "未知";
    }
}
