package com.github.sulo.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sorata 2020-09-21:9:51
 */
public abstract class FastCreateUtils {

    private static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

    public static <K, V> Map<K, V> createHashMap(int initSize) {
        if (initSize < 0) {
            throw new IllegalArgumentException("initSize >= 0");
        }
        if (initSize < 3) {
            return new HashMap<>(initSize + 1);
        }
        if (initSize < MAX_POWER_OF_TWO){
            initSize = (int) (initSize / 0.75f + 1);
        }else {
            initSize = Integer.MAX_VALUE;
        }
        return new HashMap<>(initSize);
    }





}
