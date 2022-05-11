package com.example.cogentcodebucketassignment.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cogentcodebucketassignment.model.UserDetails;

public class SessionManager {
    //Class Variables
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static final String PREF_NAME = "com.example.cogentcodebucketassignment";

    // All Shared Preferences Keys
    private static final String KEY_IS_LOGGED_IN = "101";
    private static final String KEY_DOC_ID = "102";
    private static final String KEY_USER_NAME = "103";
    private static final String KEY_USER_EMAIL = "104";
    private static final String KEY_USER_LANGUAGE_PREFERENCE = "105";
    private static final String KEY_USER_LANGUAGE_PREFERENCE_POSITION = "106";

    // Constructor
    private SessionManager() {
        //private empty constructor for singleton approach
    }

    public static void createSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void createUserSession(UserDetails userDetails) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_DOC_ID, userDetails.getDocumentId());
        editor.putString(KEY_USER_NAME, userDetails.getName());
        editor.putString(KEY_USER_EMAIL, userDetails.getEmail());

        // commit changes
        editor.apply();
    }


    public static void saveLanguagePreference(String languageName, int position) {
        editor.putString(KEY_USER_LANGUAGE_PREFERENCE, languageName);
        editor.putInt(KEY_USER_LANGUAGE_PREFERENCE_POSITION, position);

        // commit changes
        editor.apply();
    }

    public static String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "user");
    }

    public static String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "N/A");
    }

    public static void logoutUser(Context context) {
        clearSessionManager();
//        ApiClient.destroyAllRetrofitClients();
    }

    public static void clearSessionManager() {
        // Clearing all data from Shared Preferences
        try {
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            //
        }
    }
}
