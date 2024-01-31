package com.example.springproject.utils;

import com.example.springproject.exception.InvalidDateOfBirthException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.example.springproject.constant.CommonConstants.AGE_THRESHOLD;
import static com.example.springproject.constant.CommonConstants.DATE_TIME_FORMAT;

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
     * This method to get Current Date Time String
     * @return currentDateTime
     */
    public static String getCurrentDateTimeString() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

        return currentDateTime.format(formatter);
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

    /**
     *
     * @param dateOfBirth
     */
    public static void checkDateOfBirth(Date dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Period period = Period.between(birthDate, currentDate);

        if (period.getYears() < AGE_THRESHOLD ||
            (period.getYears() == AGE_THRESHOLD && (period.getMonths() > 0 || period.getDays() > 0))) {
            throw new InvalidDateOfBirthException();
        }
    }
}