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
    public static String getYesterdayDayStr(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis()-24*60*60*1000);
        String  yesterday = formatter.format(date);
        return  yesterday;
    }

    /**
     * 返回long形式的天的毫秒值
     * @return
     */
    public static long getCurrentDayMillis(String dayStr){
        if(null==dayStr){
            return 0L;
        }
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(dayStr);
            long time = date.getTime();
            return  time;
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }
}