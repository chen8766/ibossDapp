package com.chen.web.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chen
 * @date 2020-11-16-23:15
 */
public class DateUtils {

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS));
    }
}
