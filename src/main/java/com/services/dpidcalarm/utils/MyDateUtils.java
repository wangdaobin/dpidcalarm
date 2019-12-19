package com.services.dpidcalarm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/12/17 0017 17:59:21
 */
public class MyDateUtils {
    /**
     * 返回字符串“2019-12-12”
     * @return
     */
    public static String getCurrentDayStr(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String  currentDay = formatter.format(date);
        return  currentDay;
    }

    /**
     * 返回字符串形式的天的毫秒值
     * @return
     */
    public static String getCurrentDayMillisStr(String dayStr){
        if(null==dayStr){
            return null;
        }
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(dayStr);
            long time = date.getTime();
            return  String.valueOf(time);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}