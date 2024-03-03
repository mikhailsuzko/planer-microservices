package com.sma.micro.planner.plannerutils.util;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.sma.micro.planner.plannerutils.model.Constants.USER_ID_NOT_FOUND;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static String prepareParam(String param) {
        return StringUtils.isBlank(param) ? null : "%" + param.toLowerCase() + "%";
    }

    public static String userIdNotFound(Long userId) {
        return String.format(USER_ID_NOT_FOUND, userId);
    }

}
