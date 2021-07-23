package com.mymusic.common.utils;

import com.mymusic.common.domain.StatisticsVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现统计信息映射
 */
public class LocationUtils {

    public static final List<StatisticsVo> locationList = new ArrayList<>();
    public static final List<String> list = new ArrayList<>();

     public static List<StatisticsVo> getLocation(){
         List<String> areaNameList = getAreaName();
         for (String areaName : areaNameList) {
             StatisticsVo statisticsVo = new StatisticsVo();
             statisticsVo.setName(areaName);
             locationList.add(statisticsVo);
         }
         return locationList;
     }

    public static List<String> getAreaName(){
        list.add("北京");
        list.add("天津");
        list.add("河北");
        list.add("山西");
        list.add("内蒙古");
        list.add("辽宁" );
        list.add("吉林" );
        list.add("黑龙江");
        list.add("上海" );
        list.add("江苏" );
        list.add("浙江");
        list.add("安徽");
        list.add("福建" );
        list.add("江西" );
        list.add("山东");
        list.add("河南" );
        list.add("湖北" );
        list.add("湖南" );
        list.add("重庆" );
        list.add("四川" );
        list.add("贵州" );
        list.add("云南" );
        list.add("西藏" );
        list.add("陕西");
        list.add("甘肃" );
        list.add("青海" );
        list.add("宁夏" );
        list.add("新疆" );
        list.add("广东" );
        list.add("广西" );
        list.add("海南" );
        return list;
    }
}
