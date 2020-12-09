package com.github.sulo.core.util;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * @author sorata 2020-10-30:16:14
 */
public abstract class ParamsUtils {

    @SuppressWarnings("unchecked")
    public static Map<String, String> resolve(ServletRequest request) {
        final Map<String, String[]> parameterMap = request.getParameterMap();
        final Map<String, String> map = FastCreateUtils.createHashMap(parameterMap.keySet().size());
        parameterMap.forEach((k, v) -> {
            final StringBuilder builder = new StringBuilder(v.length);
            for (int i = 0; i < v.length; i++) {
                builder.append(v[i]);
                if (i != v.length - 1) {
                    builder.append(",");
                }
            }
            map.put(k, builder.toString());
        });
        return map;
    }






}
