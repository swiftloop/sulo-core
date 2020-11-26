package com.github.sulo.core.util;



/**
 * @author sorata 2020-10-09:10:39
 *
 * 将字符串的IP地址转化到Long类型的
 *
 */
public abstract class IpUtils {

    /**
     *  255L << 24 | 255 << 16 | 255 <<8 | 255
     */
    private static final long MAX_VALUE = 4294967295L;

    public static long toLong(String ip){
        if (ip == null || ip.isEmpty()){
            return 0L;
        }
        if (!Validator.isIpV4(ip)){
            return 0L;
        }
        final String[] split = ip.split("\\.");
       return Long.parseLong(split[0]) << 24 | Long.parseLong(split[1]) << 16 |
               Long.parseLong(split[2]) << 8 | Long.parseLong(split[3]);
    }

    public static String toStr(long ip){
        if (ip <=0 || ip > MAX_VALUE){
            return "0.0.0.0";
        }
        return (ip >> 24 )+ "." + (ip >> 16 & 0xff) +"." + (ip >> 8 & 0xff) +"." + (ip & 0xff);
    }





}
