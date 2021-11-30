package com.jm.demo.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {

    private static final ZoneOffset offset = OffsetDateTime.now().getOffset();

    public static Date nowToDate() {
        return Date.from(LocalDateTime.now().toInstant(offset));
    }

    public static Date nowAfterHoursToDate(Long hours) {
        return Date.from(LocalDateTime.now().plusHours(hours).toInstant(offset));
    }

    public static Date nowAfterDaysToDate(Long days) {
        return Date.from(LocalDateTime.now().plusDays(days).toInstant(offset));
    }

    private DateUtil() {
    }
}
