package com.brennan.jake;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonProcessor {
    public static String getData(String JSONString) {
        Pattern p = Pattern.compile("\"data\":\\[.*?\\]");
        Matcher m = p.matcher(JSONString);

        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }

    public static ArrayList<Long> extractIds(String data) {
        ArrayList<Long> ids = new ArrayList<>();

        Pattern p = Pattern.compile("(?<=\"id\":)\"\\d*?\"");
        Matcher m = p.matcher(data);
        
        while (m.find()) {
            String toAdd = m.group();
            toAdd = toAdd.substring(1, toAdd.length() - 1);
            ids.add(Long.valueOf(toAdd));
        }

        return ids;
    }

    public static Boolean extractStatus(String JSONString) {
        Pattern p = Pattern.compile("(?<=\"success\":)(?>true|false)");
        Matcher m = p.matcher(JSONString);

        if (m.find()) {
            return Boolean.valueOf(m.group());
        } else {
            return false;
        }
    }

    public static String extractDescription(String data) {
        Pattern p = Pattern.compile("(?<=\"description\":\").*?(?=\",)");
        Matcher m = p.matcher(data);

        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }

    public static String extractUrl(String data) {
        Pattern p = Pattern.compile("(?<=\"url\":\")https:\\/\\/(?>www){0,1}\\.linkedin\\.com\\/jobs\\/.*?(?=\")");
        Matcher m = p.matcher(data);

        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }
}
