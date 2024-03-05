package com.sma.micro.planner.plannerutils.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.sma.micro.planner.plannerutils.model.Constants.USER_ID_NOT_FOUND;
import static org.apache.logging.log4j.util.Strings.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static String prepareParam(String param) {
        return isBlank(param) ? null : "%" + param.toLowerCase() + "%";
    }

    public static String userIdNotFound(String userId) {
        return String.format(USER_ID_NOT_FOUND, userId);
    }

}
