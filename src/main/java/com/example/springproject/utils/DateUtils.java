package com.example.springproject.utils;

import java.time.LocalDate;

/**
 * Utility class for handling date-related operations.
 */
public class DateUtils {

    /**
     * The getCurrentDateString method in the provided code snippet returns the current
     * date as a string in the format "yyyy-MM-dd" using the LocalDate.now() method
     *
     * @return String yyyy-MM-dd
     */
    public static String getCurrentDateString() {
        return LocalDate.now().toString();
    }

    /**
     * The getCurrentTimeMillis method in the provided code snippet
     * returns the current time in milliseconds since the epoch.
     *
     * @return milliseconds
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}