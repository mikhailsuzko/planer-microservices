package com.sma.micro.planner.plannerutils.util;

import io.micrometer.common.util.StringUtils;

public class Utils {

    public static String prepareParam(String param) {
        return StringUtils.isBlank(param) ? null : "%" + param.toLowerCase() + "%";
    }
}
