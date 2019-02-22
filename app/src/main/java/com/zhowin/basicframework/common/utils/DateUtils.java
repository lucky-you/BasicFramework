package com.zhowin.basicframework.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author      : Z_B
 * date       : 2018/12/8
 * function  : 时间戳的工具类
 */
public class DateUtils {

    public static String getStringDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(date);
    }


    public static String getResultStringDate(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

}
