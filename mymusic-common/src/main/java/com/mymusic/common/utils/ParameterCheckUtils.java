package com.mymusic.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数检查的工具类
 */
public class ParameterCheckUtils {

    public static void checkParamIsBlank(Object... parameters) {
        for (Object parameter : parameters) {
            if (parameter == null) {
                throw new RuntimeException("参数不能为空");
            }
            if (parameter instanceof String && StringUtils.isBlank((String) parameter)) {
                throw new RuntimeException("参数不能为空");
            }
        }
    }
}
