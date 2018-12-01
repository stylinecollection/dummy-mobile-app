package com.avijitbabu.dummy_mobile_app.Utility;

import android.util.Patterns;


public class AppValidator {

    public static boolean empty(String value) {
        return value.equals("");
    }

    public static boolean validEmail(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    public static boolean validPassword(String value) {
        return value.length() >= 6;
    }

}
