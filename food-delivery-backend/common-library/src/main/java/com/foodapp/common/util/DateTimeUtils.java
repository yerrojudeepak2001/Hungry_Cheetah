package com.foodapp.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String DEFAULT_TIMEZONE = "UTC";
    
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_FORMATTER);
    }
    
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }
    
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.of(DEFAULT_TIMEZONE))
                .toLocalDateTime();
    }
    
    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime
                .atZone(ZoneId.of(DEFAULT_TIMEZONE))
                .toInstant());
    }
    
    public static boolean isDateTimeInRange(LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }
    
    public static LocalDateTime getCurrentUtcDateTime() {
        return LocalDateTime.now(ZoneId.of(DEFAULT_TIMEZONE));
    }
    
    public static long getTimeDifferenceInMinutes(LocalDateTime start, LocalDateTime end) {
        return java.time.Duration.between(start, end).toMinutes();
    }
}