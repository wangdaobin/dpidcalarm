package com.services.dpidcalarm.utils;
import com.services.dpidcalarm.collect.bean.ErrorResult;
import com.services.dpidcalarm.collect.bean.SvgUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
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
        //原系统，能取到每行，只有扣分数，没有问题点数
        //Pattern pattern = compile(".*<td class=\"td\"><a href=\"javascript:void\\(0\\);\" onclick=\"warnStationReport\\('[\\u4e00-\\u9fa5]+\\.[\\u4e00-\\u9fa5]+\\.([\\u4e00-\\u9fa5]+)'\\);\">(\\d+)</a></td>.*");
        //改进后可以去全部名称的
        //Pattern pattern = compile(".*<td class=\"td\"><a href=\"javascript:void\\(0\\);\" onclick=\"warnStationReport\\('(.*)'\\);\">(\\d+)</a></td>.*");
        //原系统，\s匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。 （） group用
        Pattern pattern = compile(".*<td class=\"td\"><a href=\"javascript:void\\(0\\);\" onclick=\"warnStationReport\\('[\\u4e00-\\u9fa5]+\\.[\\u4e00-\\u9fa5]+\\.([\\u4e00-\\u9fa5]+)'\\);\">(\\d+)</a></td>\\s*" +
                "<td class=\"td\".*>(.*)</td>.*");
        Matcher matcher = pattern.matcher(html);
        Map<String, String> map = new HashMap();
        while (matcher.find()) {
            String station = matcher.group(1);
            String problemCount = matcher.group(2);
            String deductPoints = matcher.group(3);
            if(null==problemCount || "".equals(problemCount) || "0".equals(problemCount)){
                continue;
            }
            map.put(station, problemCount + ";");
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
            // String table = json.getString("table");
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
        // Calendar calendar  = Calendar.getInstance();
        // int hous = calendar.get(Calendar.HOUR_OF_DAY);
        //
        // ErrorCountHtmlUtils.getCountMap("");
        //
        // String str = MyDateUtils.getYesterdayDayStr();
        //
        //
        // File file  = new File("E:\\31-重庆\\网站数据\\指标20191212\\01变电站刷新\\变电站刷新-详细--20191224.html");
        // try {
        //     String fileContent = FileUtils.readFileToString(file);
        //     Map<String,String> map = ErrorCountHtmlUtils.getCountMap(fileContent);
        //     // double currentScore = ErrorCountHtmlUtils.getCurrentScore(fileContent);
        //     // System.out.println(currentScore);
        // }catch (Exception e){
        //     e.printStackTrace();
        // }


        ErrorCountHtmlUtils.dealZTGJError();
        // ErrorCountHtmlUtils.dealcurrentScoreJSON(null);

        // return;
        String compony_jfdl = "南岸分公司";
        //String compony_jfdl = "%e5%8d%97%e5%b2%b8%e5%88%86%e5%85%ac%e5%8f%b8";
        String str2 = "%3Croot%3E%3CTableData+table_id%3D%272064%27+app_id%3D%27201100%27+HisDataType%3D%271081085952%27+col_name_list%3D%27dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase%27+sql%3D%22select+dv_name%2Cst_id%2Cdescr%2Cmeasure_type%2Cvalue_%2Cvalue_pre%2Cvalue_res%2Cvalue_r%2Cbase+from+pas_allmeasure+where+descr+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+finish_num%3D%270%27+record_num%3D%270%27+value%3D%27%27+m_SQL%3D%22+f3+LIKE+%27%25%E5%8D%97%E5%B2%B8%25%27+%22+m_WhereFieldList_string%3D%22f3%22+m_FieldInfoVec_string%3D%220%2Cf0%2C1%2C-1%2C-1%2C-1%2C-1%3B1%2Cf1%2C2%2C-1%2C-1%2C-1%2C-1%3B2%2Cf2%2C4%2C-1%2C-1%2C-1%2C-1%3B3%2Cf3%2C5%2C-1%2C-1%2C-1%2C-1%3B4%2Cf4%2C6%2C-1%2C-1%2C-1%2C-1%3B5%2Cf5%2C7%2C-1%2C-1%2C-1%2C-1%3B6%2Cf6%2C8%2C-1%2C-1%2C-1%2C-1%3B7%2Cf7%2C9%2C-1%2C-1%2C-1%2C-1%3B8%2Cf8%2C12%2C-1%2C-1%2C-1%2C-1%3B9%2Cf9%2C10%2C-1%2C-1%2C-1%2C-1%22+m_StatusQueryVec_string%3D%22%22%2F%3E%3C%2Froot%3E";
        try{
            String str = URLDecoder.decode(str2, "UTF-8");
            System.out.println(str);

            String reslut =  URLEncoder.encode(str, "utf-8");
            System.out.println(reslut);
            System.out.println(str2);

            //  // String reslut2 =  URLDecoder.decode(str.replaceAll("%","%25"), "utf-8");
            // System.out.println(reslut2);
            return;
        }catch (Exception e){
            e.printStackTrace();
        }


        //File file1  = new File("D:/a.xml");
        File file1  = new File("D:/b.xml");
        try {
            String fileContent = FileUtils.readFileToString(file1);
            SvgUtil.getQulifyRateFloat(fileContent,"市南");
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

    public static double dealcurrentScoreJSON(String jsonStrResult){
        //1、解析json
        //json样例{"com":"113715891329302534","countNo":null,"datas":[{"x":1575129600000,"y":100.0},{"x":1575216000000,"y":99.91},{"x":1575302400000,"y":99.99},{"x":1575388800000,"y":99.92},{"x":1575475200000,"y":99.94},{"x":1575561600000,"y":99.95},{"x":1575648000000,"y":99.96},{"x":1575734400000,"y":99.88},{"x":1575820800000,"y":99.99},{"x":1575907200000,"y":99.99},{"x":1575993600000,"y":99.99},{"x":1576080000000,"y":99.89}],"name":"正确率","startDay":"2019-12-12","table":"bk_con_cord_5"}
        jsonStrResult = "{\"com\":\"南岸分公司\",\"datas\":[{\"x\":1588348800000,\"y\":99.8},{\"x\":1588435200000,\"y\":99.86},{\"x\":1588521600000,\"y\":99.8},{\"x\":1588608000000,\"y\":99.8},{\"x\":1588694400000,\"y\":99.6},{\"x\":1588780800000,\"y\":99.53},{\"x\":1588867200000,\"y\":99.53},{\"x\":1588953600000,\"y\":99.53},{\"x\":1589040000000,\"y\":99.47},{\"x\":1589126400000,\"y\":99.53},{\"x\":1589212800000,\"y\":99.66},{\"x\":1589299200000,\"y\":99.66},{\"x\":1589385600000,\"y\":99.73},{\"x\":1589472000000,\"y\":99.86},{\"x\":1589558400000,\"y\":99.73},{\"x\":1589644800000,\"y\":99.6},{\"x\":1589731200000,\"y\":99.53},{\"x\":1589817600000,\"y\":99.73},{\"x\":1589904000000,\"y\":99.86},{\"x\":1589990400000,\"y\":99.86},{\"x\":1590076800000,\"y\":99.66},{\"x\":1590163200000,\"y\":99.66},{\"x\":1590249600000,\"y\":99.73},{\"x\":1590336000000,\"y\":99.73},{\"x\":1590422400000,\"y\":99.53},{\"x\":1590508800000,\"y\":99.47},{\"x\":1590595200000,\"y\":99.6},{\"x\":1590681600000,\"y\":99.47}],\"name\":\"正确率\",\"startDay\":\"2020-05-29\"}";
        if(null==jsonStrResult){
            return -1;
        }
        try {
            JSONObject json = new JSONObject(jsonStrResult);
            //公司
            String com = json.getString("com");
            //表
            // String table = json.getString("table");
            //开始时间
            String startDay = json.getString("startDay");
            long currentDayMillis = MyDateUtils.getCurrentDayMillis(startDay);

            //解析datas
            JSONArray datas = json.getJSONArray("datas");
            if( null!=datas && datas.length()>0){
                for(int i=0; i<datas.length(); i++){
                    JSONObject xyObj = datas.getJSONObject(i);
                    //x 值如果为当天，则取出值
                    long xValue = (long) xyObj.get("x");
                    if(currentDayMillis == xValue){
                        double currentScore = (double)xyObj.get("y");
                        // this.logger.info("当前分数为：" + currentScore);
                        System.out.println("当前分数为：" + currentScore);
                        return currentScore;
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            // this.logger.error("获取得分出错：",e);
        }
        return -1;
    }

    public static void dealZTGJError(){

        String errorResult = "<TableData  record_num='9' finish_num='9' value='检修公司@重庆.巴南站@重庆.市南/220kV.巴四南线@无功@-1.70@-11.15@3.10@9.44@305.00@,市南@重庆.市南.土桥站@重庆.市南/110kV.油土西线@有功@-14.61@0.00@12.81@14.61@114.00@,市南@重庆.市南.四公里站@重庆.市南/110kV.里海线@无功@-5.30@0.19@4.81@5.48@114.00@,市南@重庆.市南.柏子桥站@重庆.市南/10kV.子原一回线@无功@-0.22@0.83@6.55@1.05@16.00@,市南@重庆.市南.大佛寺站@重庆.市南/10kV.佛嘉线@无功@-0.99@-0.01@6.10@0.98@16.00@,市南@重庆.市南.炒油场站@重庆.市南/10kV.油南线@无功@-0.01@0.80@5.10@0.82@16.00@,市南@重庆.市南.南坪站@重庆.市南.南坪站/110kV.Ⅰ母@电压@114.48@116.27@1.35@1.79@132.00@,市南@重庆.市南.五布站@重庆.市南.五布站/35kV.Ⅱ母@电压@35.96@36.40@1.04@0.44@42.00@,市南@重庆.市南.五布站@重庆.市南.五布站/35kV.Ⅰ母@电压@35.96@36.40@1.04@0.44@42.00@'/>";
        List<ErrorResult> thisErrs = SvgUtil.getErrorResult(errorResult);
        StringBuilder sb = new StringBuilder();
        for (ErrorResult er : thisErrs) {
            sb.append(";  设备名称:").append(er.getSbName()).append(",估计值").append(er.getGjValue()).append(",量测值").append(er.getLcValue());
        }
        System.out.println("getNananTestError状态估计合格率--出错详情整理：" + sb.toString());

    }
}