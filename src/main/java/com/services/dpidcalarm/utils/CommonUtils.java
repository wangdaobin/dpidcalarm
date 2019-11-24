package com.services.dpidcalarm.utils;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/2 0002 21:08:25
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils
{
    public static Date string2date(String dateStr)
    {
        if ((dateStr == null) || ("".equals(dateStr))) return null;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Integer getYear(String date)
    {
        if ((date == null) || ("".equals(date))) return null;
        String[] yam = null;
        try {
            yam = date.split("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.valueOf(Integer.parseInt(yam[0]));
    }

    public static Integer getMonth(String date)
    {
        if ((date == null) || ("".equals(date))) return null;
        String[] yam = null;
        try {
            yam = date.split("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.valueOf(Integer.parseInt(yam[1]));
    }

    public static Integer getDay(String date)
    {
        if ((date == null) || ("".equals(date))) return null;
        String[] yam = null;
        try {
            yam = date.split("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.valueOf(Integer.parseInt(yam[2]));
    }

    public static int getDaysOfMonth(Integer year, Integer month)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year.intValue());
        cal.set(2, month.intValue() - 1);
        return cal.getActualMaximum(5);
    }

    public static int getMaxInt(List<Integer> intArrays)
    {
        int result = ((Integer)intArrays.get(0)).intValue();
        for (Iterator localIterator = intArrays.iterator(); localIterator.hasNext(); ) { int i = ((Integer)localIterator.next()).intValue();
            if (result < i) {
                result = i;
            }
        }
        return result;
    }

    public static String list2string(Set list) {
        StringBuilder sb = new StringBuilder();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String ctn = iterator.next().toString();
            sb.append(ctn).append(";");
        }
        sb.substring(0, sb.length() - 1);
        return sb.toString();
    }

    /**
     * 取出list中的最小值
     * @param values
     * @return
     */
    public static Float getMin(List<Float> values)
    {
        if ((values == null) && (values.size() == 0)){
            return null;
        }
        Float minFloatValue = Float.MAX_VALUE ;
        for(Float tempValue: values){
            if(tempValue < minFloatValue){
                minFloatValue = tempValue;
            }
        }
        return minFloatValue;
    }

    public static int getCurrentMonthDay(Date date)
    {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.set(5, 1);
        a.roll(5, -1);
        int maxDate = a.get(5);
        return maxDate;
    }
}