package com.example.myapplication.utlis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {
    public static boolean isValid(String phone){
        String regex = "^0+\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
