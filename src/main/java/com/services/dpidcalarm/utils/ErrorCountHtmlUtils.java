package com.services.dpidcalarm.utils;
import org.apache.commons.io.FileUtils;

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
        String html1 = "<td class=\"td\"><a href=\"javascript:void(0);\" onclick=\"warnStationReport('重庆.长寿.涪陵.蔺市站');\">0</a></td>" +
                "<td class=\"td\">0</td>" +
                "</tr>" +
                "<tr>" +
                "<td class=\"td\" style=\"text-align: left;\">重庆.长寿.涪陵.大桥站</td>" +
                "<td class=\"td\"><a href=\"javascript:void(0);\" onclick=\"warnStationReport('重庆.长寿.涪陵.大桥站');\">247</a></td>" +
                "<td class=\"td\" style=\"color: red;\">0.66</td>";
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
    public static void main(String[] args) {
        String aa = MyDateUtils.getCurrentDayMillisStr("2019-12-11");

        File file  = new File("E:\\31-重庆\\网站数据\\指标20191212\\01变电站刷新\\变电站刷新-详细-.html");
        try {
            String fileContent = FileUtils.readFileToString(file);
            Map<String,String> map = ErrorCountHtmlUtils.getCountMap(fileContent);
            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
        }


        ;
    }
}