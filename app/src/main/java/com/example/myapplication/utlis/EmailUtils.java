package com.example.myapplication.utlis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {
    public static boolean isValid(String email){
        String regex = "^(?!\\d)[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)" +
                "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
