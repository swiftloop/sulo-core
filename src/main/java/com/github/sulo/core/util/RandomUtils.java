package com.github.sulo.core.util;

import java.util.concurrent.ThreadLocalRandom;

import static com.github.sulo.core.util.StringUtils.CHARS;

/**
 * @author sorata 2020-11-19:11:34
 */
public abstract class RandomUtils {


    private static String rand(int len) {
        double rand = ThreadLocalRandom.current().nextDouble();
        while (rand < 0.1) {
            rand = ThreadLocalRandom.current().nextDouble();
        }
        return String.valueOf((int) (Math.pow(10, len) * rand));
    }

    private static String randStr(int len) {
        final char[] chars = new char[len];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = CHARS[ThreadLocalRandom.current().nextInt(CHARS.length)];
        }
        return new String(chars);
    }










}
