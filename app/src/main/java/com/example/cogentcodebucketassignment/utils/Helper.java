package com.example.cogentcodebucketassignment.utils;

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Helper {
    //PATTERNS
    public static final Pattern PHONE_NO_PATTERN = Pattern.compile("^[+]?[0-9]{10}$");
    public static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;
    public static final String RAW_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String STANDARD_DATE_FORMAT = "dd-MMM hh:mm a";

    public static boolean isThisToday(Date d) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d).equals(fmt.format(new java.util.Date()));
    }

    public static boolean isThisToday(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM");
        String currentDate = fmt.format(new java.util.Date());
        try {
            return fmt.format(fmt.parse(date)).equals(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getOnlyTime(String date) {
        return date.substring(7);
    }

    public static String changeDateFormat(String dateInString, String currentPattern, String newPattern) {
        String formattedDate = "";
        try {
            Date date = new SimpleDateFormat(currentPattern).parse(dateInString);
            formattedDate = (new SimpleDateFormat(newPattern)).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
