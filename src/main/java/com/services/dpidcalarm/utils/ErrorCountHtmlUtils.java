package com.services.dpidcalarm.utils;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/12/12 0012 18:51:27
 */
public class ErrorCountHtmlUtils {

    public static Map<String, String> getCountMap(String html)
    {
        //Pattern pattern = compile("*<td class=\"td\"><a href=\"javascript:void\\(0\\);\" onclick=\"warnStationReport*;\">(\d+)</a></td>*");
        // String html1 = "<td class=\"td\"><a href=\"javascript:void(0);\" onclick=\"warnStationReport('重庆.长寿.涪陵.蔺市站');\">0</a></td>" +
        //         "<td class=\"td\">0</td>" +
        //         "</tr>" +
        //         "<tr>" +
        //         "<td class=\"td\" style=\"text-align: left;\">重庆.长寿.涪陵.大桥站</td>" +
        //         "<td class=\"td\"><a href=\"javascript:void(0);\" onclick=\"warnStationReport('重庆.长寿.涪陵.大桥站');\">247</a></td>" +
        //         "<td class=\"td\" style=\"color: red;\">0.66</td>";
        //Pattern pattern = compile(".*<td class=\"td\"><a href=\"javascript:void\\(0\\);\" onclick=\"warnStationReport\\('[\\u4e00-\\u9fa5]+\\.[\\u4e00-\\u9fa5]+\\.([\\u4e00-\\u9fa5]+)'\\);\">(\\d+)</a></td>.*");
        //Pattern pattern = compile(".*<td class=\"td\"><a href=\"javascript:void\\(0\\);\" onclick=\"warnStationReport\\('[\\u4e00-\\u9fa5]+\\.[\\u4e00-\\u9fa5]+\\.[\\u4e00-\\u9fa5]+\\.([\\u4e00-\\u9fa5]+)'\\);\">(\\d+)</a></td>.*");
        Pattern pattern = compile(".*<td class=\"td\"><a href=\"javascript:void\\(0\\);\" onclick=\"warnStationReport\\('(.*)'\\);\">(\\d+)</a></td>\\W*" +
                "<td class=\"td\" style=\"color: red;\">(.*)</td>.*");
        // Pattern pattern = compile("onclick=\"warnStationReport\\('[\\u4e00-\\u9fa5]+\\.[\\u4e00-\\u9fa5]+\\.([\\u4e00-\\u9fa5]+\\.([\\u4e00-\\u9fa5]+)'\\);\">");

        Matcher matcher = pattern.matcher(html);
        Map<String, String> map = new HashMap();
        while (matcher.find()) {
            String station = matcher.group(1);
            String problemCount = matcher.group(2);
            String deductPoints = matcher.group(3);
            if(null==problemCount || "".equals(problemCount) || "0".equals(problemCount)){
                continue;
            }
            map.put(station, problemCount + ";" + deductPoints);
        }
        return map;
    }

    public static boolean dealcurrentScore(String jsonStrResult){
        //1、解析json
        jsonStrResult = "{\"com\":\"113715891329302534\",\"countNo\":null,\"datas\":[{\"x\":1575129600000,\"y\":100.0},{\"x\":1575216000000,\"y\":99.91},{\"x\":1575302400000,\"y\":99.99},{\"x\":1575388800000,\"y\":99.92},{\"x\":1575475200000,\"y\":99.94},{\"x\":1575561600000,\"y\":99.95},{\"x\":1575648000000,\"y\":99.96},{\"x\":1575734400000,\"y\":99.88},{\"x\":1575820800000,\"y\":99.99},{\"x\":1575907200000,\"y\":99.99},{\"x\":1575993600000,\"y\":99.99},{\"x\":1576080000000,\"y\":99.89}],\"name\":\"正确率\",\"startDay\":\"2019-12-12\",\"table\":\"bk_con_cord_5\"}";
        if(null==jsonStrResult){
            return false;
        }
        try {
            JSONObject json = new JSONObject(jsonStrResult);
            //公司
            String com = json.getString("com");
            //表
            String table = json.getString("table");
            //开始时间
            String startDay = json.getString("startDay");
            long currentDayStr = 1576080000000L;
            // MyDateUtils.getCurrentDayMillisStr(startDay);

            //解析datas
            JSONArray datas = json.getJSONArray("datas");
            if( null!=datas && datas.length()>0){
                for(int i=0; i<datas.length(); i++){
                    JSONObject xyObj = datas.getJSONObject(i);
                    //x 值如果为当天，则取出值
                    long xValue = (long) xyObj.get("x");

                    if(currentDayStr == xValue){
                        double currentScore = (double)xyObj.get("y");
                        System.out.println("开关位置情况--当前分数为：" + currentScore);
                        //todo
                        //写库
                        return true;
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();

        }




        //2、写库
        return false;
    }
    public static void main(String[] args) {

        String str = MyDateUtils.getYesterdayDayStr();


        File file  = new File("E:\\31-重庆\\网站数据\\指标20191212\\01变电站刷新\\变电站刷新-详细-.html");
        try {
            String fileContent = FileUtils.readFileToString(file);
            Map<String,String> map = ErrorCountHtmlUtils.getCountMap(fileContent);
            // double currentScore = ErrorCountHtmlUtils.getCurrentScore(fileContent);
            //System.out.println(currentScore);
        }catch (Exception e){
            e.printStackTrace();
        }


        ;
    }

    // public static  double getCurrentScore(String detailsHtml){
    //     //1、解析html，解析
    //     Map<String, String> resultMap =  ErrorCountHtmlUtils.getCountMap(detailsHtml);
    //     //key:重庆.长寿.涪陵.桥南站    value:34;0.09
    //     if(resultMap.size()==0){
    //         //没有问题，100分
    //         return 100;
    //     }
    //     Double currentScore = 100.0;
    //     for (String value : resultMap.values()){
    //         String scoreStr = value.split(";")[1];
    //         double tempScore = Double.parseDouble(scoreStr);
    //         currentScore = currentScore - tempScore;
    //
    //     }
    //     return currentScore ;
    // }
}