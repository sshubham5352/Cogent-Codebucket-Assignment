package com.example.cogentcodebucketassignment.utils;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Patterns;

import java.util.regex.Pattern;

public class Helper {
    //PATTERNS
    public static final Pattern PHONE_NO_PATTERN = Pattern.compile("^[+]?[0-9]{10}$");
    public static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;
}
