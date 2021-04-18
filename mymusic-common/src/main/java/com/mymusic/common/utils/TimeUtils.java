package com.mymusic.common.utils;

import java.time.LocalDate;

/**
 * 时间的工具类
 */

public class TimeUtils {

    /**
     * 算歌手出生时间的类
     * @param ageRange 时间的范围
     * @return
     */
    public static String getLocalDateRange(Integer ageRange){
        LocalDate basicDate = LocalDate.now().minusYears(121);
         return  basicDate.plusYears(ageRange).toString();
    }
}
