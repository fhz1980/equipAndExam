package com.sznikola.equipAndExam.util;

/**
 * @author yzh
 * @Description
 * @email yzhcherish@163.com
 * @data 2022-11-15  15:34
 */
public class UnicodeUtil {

    private static char[] low_alphabets = "0123456789abcdef".toCharArray();

    private static char[] up_alphabets =  "0123456789ABCDEF".toCharArray();

    // 是否是ASCII
    public static boolean isAsciiPrintable(char ch) {
        return ch >= ' ' && ch < 127;
    }

    // 转成Unicode 16进制字符串
    public static String toUnicodeHex(char ch, boolean lower) {
        char[] alphabets = lower?low_alphabets:up_alphabets;
        return "\\u" + alphabets[ch >> 12 & 15] + alphabets[ch >> 8 & 15] + alphabets[ch >> 4 & 15] + alphabets[ch & 15];
    }

    public static String toUnicode(String str) {
        return toUnicode(str, true, true);
    }

    public static String toUnicode(String str, boolean lower) {
        return toUnicode(str, lower, true);
    }

    // isSkipAscii 是否跳过ascii字符
    public static String toUnicode(String str, boolean lower, boolean isSkipAscii) {
        if (str == null || str.length() == 0) {
            return str;
        } else {
            int len = str.length();
            StringBuilder unicode = new StringBuilder(str.length() * 6);

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (isSkipAscii && isAsciiPrintable(c)) {
                    unicode.append(c);
                } else {
                    unicode.append(toUnicodeHex(c, lower));
                }
            }

            return unicode.toString();
        }
    }

}
