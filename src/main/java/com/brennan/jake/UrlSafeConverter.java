package com.brennan.jake;

public class UrlSafeConverter {
    public static String convertToUrlSafe(String input) {
        StringBuilder sb = new StringBuilder();
        char[] charArr = input.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            switch (charArr[i]) {
                case ';' -> sb.append("%3B");
                case '?' -> sb.append("%3F");
                case '/' -> sb.append("%2F");
                case ':' -> sb.append("%3A");
                case '#' -> sb.append("%23");
                case '&' -> sb.append("%26");
                case '=' -> sb.append("%3D");
                case '+' -> sb.append("%2B");
                case '$' -> sb.append("%24");
                case ',' -> sb.append("%2C");
                case ' ' -> sb.append("%20");
                case '%' -> sb.append("%25");
                case '<' -> sb.append("%3C");
                case '>' -> sb.append("%3E");
                case '~' -> sb.append("%7E");
                default -> sb.append(charArr[i]);
            }
        }
        return sb.toString();
    }

    public static String longArrayParse(Long[] input) {
        StringBuilder sb = new StringBuilder();
        for (Long i : input) {
            sb.append(i).append(',');
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), "");
        return sb.toString();
    }
}
