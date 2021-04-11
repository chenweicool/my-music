package com.mymusic.jwt.utils;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java字符串和日期的相互转换
 */
public class TimeUtils {

    /**
     * 字符串和日期之间的转换
     * @param dataStr 字符串
     * @return
     * @throws ParseException
     */
    @SneakyThrows
    public static Date stringConvertData(String dataStr)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (dataStr.isEmpty()) {
            return new Date();
        }
        return simpleDateFormat.parse(dataStr);
    }

    /**
     * 日期和转为字符
     * @param date
     * @return
     */
    public static String dateConvertStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
