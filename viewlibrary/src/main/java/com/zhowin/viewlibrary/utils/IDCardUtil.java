package com.zhowin.viewlibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号验证
 */

public class IDCardUtil {

    // 身份证号码的严格校验
    public static boolean checkIdCardNum(String idNum) throws ParseException {
        idNum = idNum.toUpperCase(); // 将末尾可能存在的x转成X
        String regex = "";
        regex += "^[1-6]\\d{5}"; // 前6位地址码。后面仍需打表校验

        regex += "(18|19|20)\\d{2}"; // 年份。后面仍需校验
        regex += "((0[1-9])|(1[0-2]))"; // 月份。后面仍需校验
        regex += "(([0-2][1-9])|10|20|30|31)"; // 日期。后面仍需校验

        regex += "\\d{3}"; // 3位顺序码

        regex += "[0-9X]"; // 检验码。后面仍需验证

        if (!idNum.matches(regex)) {
            return false;
        }
        // 第1，2位(省)打表进一步校验
        int[] d = {11, 12, 13, 14, 15,
                21, 22, 23, 31, 32, 33, 34, 35, 36, 37,
                41, 42, 43,
                44, 45, 46,
                50, 51, 52, 53, 53,
                61, 62, 63, 64, 65,
                83, 81, 82};
        boolean flag = false;
        int prov = Integer.parseInt(idNum.substring(0, 2));
        for (int i = 0; i < d.length; i++) {
            if (d[i] == prov) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return false;
        }
        // 生日校验：生日的时间不能比当前时间（指程序检测用户输入的身份证号码的时候）晚
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date birthDate = sdf.parse(idNum.substring(6, 14));
        Date curDate = new Date();
        if (birthDate.getTime() > curDate.getTime()) {
            return false;
        }
        // 生日校验：每个月的天数不一样（有的月份没有31），还要注意闰年的二月
        int year = Integer.parseInt(idNum.substring(6, 10));
        int leap = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 1 : 0;
        final int[] month = {0, 31, 28 + leap, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int mon = Integer.parseInt(idNum.substring(10, 12));
        int day = Integer.parseInt(idNum.substring(12, 14));
        if (day > month[mon]) {
            return false;
        }
        // 检验码
        if (idNum.charAt(17) != getLastChar(idNum)) {
            return false;
        }
        return true;
    }

    // 根据身份证号码的前17位计算校验码
    private static char getLastChar(String idNum) {
        final int[] w = {0, 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        final char[] ch = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int res = 0;
        for (int i = 0; i < 17; i++) {
            int t = idNum.charAt(i) - '0';
            res += (t * w[i + 1]);
        }
        return ch[res % 11];
    }

    public static boolean checkName(String userName) {
        // 验证是否存在中文
        String regex0 = "[\\u4e00-\\u9fa5]";
        Pattern p0 = Pattern.compile(regex0);
        Matcher m0 = p0.matcher(userName);
        if (!m0.find()) {
            return false;
        }
        // 特殊符号验证
        String regex1 = "[ `~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）—"
                + "—+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p1 = Pattern.compile(regex1);
        Matcher m1 = p1.matcher(userName);
        if (m1.find()) {
            return false;
        }
        // 数字验证
        String regex2 = ".*[0-9].*";
        Pattern p2 = Pattern.compile(regex2);
        Matcher m2 = p2.matcher(userName);
        if (m2.find()) {
            return false;
        }
        // 字母验证
        String regex3 = ".*[a-zA-z].*";
        Pattern p3 = Pattern.compile(regex3);
        Matcher m3 = p3.matcher(userName);
        if (m3.find()) {
            return false;
        }
        // emoji
        return !containsEmoji(userName);
    }

    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }
}