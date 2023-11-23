package com.evhub.util;

import com.evhub.exception.AppsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static final String DEFAULT_DATE_TIME_FORMAT_SECONDS = "dd/MM/yyyy HH:mm:ss";

    public static String getDefaultFormattedDateTimeMaskNull(Date date) {
        if (date != null) {
            return getFormattedDate(date, DEFAULT_DATE_TIME_FORMAT_SECONDS);
        }
        return null;
    }

    public static String getDefaultFormattedDateMaskNull(Date date) {
        if (date != null) {
            return getFormattedDate(date, DEFAULT_DATE_FORMAT);
        }
        return null;
    }

    public static String getFormattedDate(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static Date getDefaultParsedDateTime(String date) throws AppsException {
        return getParsedDate(date, DEFAULT_DATE_TIME_FORMAT_SECONDS);
    }

    public static Date getParsedDate(String date, String dateFormat) throws AppsException {
        Date parsedDate;

        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            parsedDate = format.parse(date);
            return parsedDate;
        } catch (ParseException e) {
            throw new AppsException("Invalid date format");
        }
    }

    public static boolean isBefore(Date checkDate, Date checkAgainstDate) {
        boolean status = false;
        if (checkDate.before(checkAgainstDate)) {
            status = true;
        }
        return status;
    }

    public static Date getParsedStartDateTime(String date) throws AppsException {
        date = date.trim().concat(" 00:00:00");
        return getParsedDateTime(date);
    }

    public static Date getParsedEndDateTime(String date) throws AppsException {
        date = date.trim().concat(" 23:59:59");
        return getParsedDateTime(date);
    }

    public static Date getParsedDateTime(String date) throws AppsException {
        return getParsedDate(date, DEFAULT_DATE_TIME_FORMAT_SECONDS);
    }
}
