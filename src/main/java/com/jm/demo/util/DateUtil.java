package com.jm.demo.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {

    public static Date nowToDate() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(-6)));
    }

    public static Date nowAfterHoursToDate(Long hours) {
        return Date.from(LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.ofHours(-6)));
    }

    public static Date nowAfterDaysToDate(Long days) {
        return Date.from(LocalDateTime.now().plusDays(days).toInstant(ZoneOffset.ofHours(-6)));
    }

    private DateUtil() {
    }
}
