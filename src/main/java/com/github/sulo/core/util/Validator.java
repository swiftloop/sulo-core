package com.github.sulo.core.util;

import java.util.regex.Pattern;

/**
 * @author sorata 2020-11-17:15:45
 */
public abstract class Validator {

    private static final Pattern IMAGE_PATTERN = Pattern.compile("(\\.([png]|[jpg]|[jpge]|[bpg]|[webp]))\\b");

    private static final Pattern IP_V4_PATTERN = Pattern.compile("^(([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4]\\d|25[0-5])\\.){1}" +
            "(([0-9]|[1-9]\\d|1[0-9]{2}|2[0-4]\\d|25[0-5])\\.){2}" +
            "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    public static boolean isImage(String fileName) {
        return IMAGE_PATTERN.matcher(fileName).find();
    }

    public static boolean isIpV4(String ipv4) {
        return IP_V4_PATTERN.matcher(ipv4).find();
    }


}
