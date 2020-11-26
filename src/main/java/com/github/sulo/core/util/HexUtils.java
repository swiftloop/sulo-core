package com.github.sulo.core.util;


/**
 * @author sorata 2020-09-21:11:00
 */
public abstract class HexUtils {

    private static final int[] DEC = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1,
            -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 10, 11, 12, 13, 14, 15,
    };

    private static final char[] CHARS = "0123456789abcdef".toCharArray();

    private static int toDEC(char c) {
        int index = c - '0';
        if (index < 0 || index > DEC.length - 1) {
            return -1;
        }
        return DEC[index];
    }

    /**
     * 将hex字符串转变成字节数组
     * 原理：一个字节 可以用 2个16进制的char（0-9 a-f）
     * @param str 偶数长度的hex字符串
     * @return 字节数组
     */
    public static byte[] toBytes(String str) {
        //首先  假设这个字符串是由字节数组转化来的十六进制的字符串
        if (str == null) {
            return null;
        }
        //判断奇数 偶数 这里一定会是偶数
        if ((str.length() & 1) == 1) {
            throw new IllegalArgumentException("HexUtils.toBytes str is oddDigits");
        }
        final byte[] bytes = new byte[str.length() >> 1];
        final char[] chars = str.toCharArray();
        for (int i = 0; i < bytes.length; i++) {
            //height bit
            final int h = toDEC(chars[2 * i]);
            // lower bit
            final int l = toDEC(chars[2 * i + 1]);
            if (h < 0 || l < 0) {
                throw new IllegalArgumentException("HexUtils.toBytes str is not hex string");
            }
            bytes[i] = (byte) (h << 4 | l);
        }
        return bytes;
    }


    public static String toHexString(byte[] bytes){
        if (bytes == null){
            return null;
        }
        final byte[] chars = new byte[bytes.length << 1];
        int j = 0;
        for (byte aByte : bytes) {
            chars[2 * j] = (byte) CHARS[(aByte & 0xf0) >>> 4];
            chars[2 * j + 1] = (byte) CHARS[aByte & 0x0f];
            ++j;
        }
        return new String(chars);
    }



}

