package com.services.dpidcalarm.collect.bean;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/2 0002 15:50:14
 */

import com.services.dpidcalarm.utils.MultiMemberGZIPInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SvgUtil
{
    /**
     *  将文件内容返回为UTF-8的字符串
     * @param file
     * @return
     */
    private static String svgHandler(File file) {
        String result = null;
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String s = null;
            StringBuffer sb = new StringBuffer();
            while ((s = reader.readLine()) != null) {
                //追加换行
                sb.append(s).append("\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null){
                    reader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 解析指标数据
     * @param is
     * @param type
     * @return
     */
    public static String svgzHandler(InputStream is, String type) {
        if (type.equalsIgnoreCase("svgz")) {
            File file = new File("temp.svg");
            MultiMemberGZIPInputStream mmgzip = null;
            FileOutputStream fout = null;
            try {
                mmgzip = new MultiMemberGZIPInputStream(is);
                fout = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                //先读一次
                int nnumber = mmgzip.read(buf, 0, buf.length);
                while (nnumber != -1) {
                    fout.write(buf, 0, nnumber);
                    nnumber = mmgzip.read(buf, 0, buf.length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    mmgzip.close();
                    fout.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //转字符串
            return svgHandler(file);
        }
        if (type.equalsIgnoreCase("svg")) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String s = null;
            while ((s = reader.readLine()) != null){
                sb.append(s).append("\n");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null){
                    is.close();
                }
                if (reader != null){
                    reader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
        return null;
    }

    /**
     * 获取合格率
     * @param svg
     * @return
     */
    public static Map<String, Float> getQulifyRate(String svg) {
        Map stationMap = new HashMap();
        getCZQulifyRateByReg("南岸", svg, "(@南岸@\\d+@\\d+\\.?\\d+@(\\d+\\.?\\d+))", stationMap);
        return stationMap;
    }
    public static float getQulifyRateFloat(String svg,String comShortName) {
        try{
            String reg = ".*@" + comShortName + "@\\d+@(\\d+\\.?\\d+)@.*";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(svg);
            String temp = null;
            if (matcher.find()) {
                temp = matcher.group(1);
                return Float.valueOf(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  -1;
    }
    /**
     * 获得厂站合格率
     * @param cz
     * @param svg
     * @param reg
     * @param stationMap
     */
    private static void getCZQulifyRateByReg(String cz, String svg, String reg, Map<String, Float> stationMap) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(svg);
        String temp = null;
        if (matcher.find()) {
            temp = matcher.group(2);
        }
        stationMap.put(cz, temp == null ? null : Float.valueOf(temp));
    }



    public static List<ErrorResult> getErrorResult(String str)
    {
        Pattern pattern = Pattern.compile(".*<TableData  record_num='\\d+' finish_num='\\d+' value='(.+)'");
        Matcher matcher = pattern.matcher(str);
        String content = null;
        List erList = new ArrayList();
        if (matcher.find()) {
            content = matcher.group(1);
        }
        if (content == null) return erList;
        String[] datas = content.split(",");
        for (String s : datas) {
            String[] items = s.split("@");
            ErrorResult er = new ErrorResult();
            for (int i = 0; i < items.length; i++) {
                switch (i) {
                    case 0:
                        er.setArea(items[i]);
                        break;
                    case 1:
                        er.setCzName(items[i]);
                        break;
                    case 2:
                        er.setSbName(items[i]);
                        break;
                    case 3:
                        er.setSbType(items[i]);
                        break;
                    case 4:
                        try {
                            er.setGjValue(Float.valueOf(items[i]).floatValue());
                        } catch (NumberFormatException e) {
                            er.setGjValue(0.0F);
                        }
                    case 5:
                        try
                        {
                            er.setLcValue(Float.valueOf(items[i]).floatValue());
                        } catch (NumberFormatException e) {
                            er.setLcValue(0.0F);
                        }
                    case 6:
                        try
                        {
                            er.setWcPercent(Float.valueOf(items[i]).floatValue());
                        } catch (NumberFormatException e) {
                            er.setWcPercent(0.0F);
                        }
                    case 7:
                        try
                        {
                            er.setCcValue(Float.valueOf(items[i]).floatValue());
                        } catch (NumberFormatException e) {
                            er.setCcValue(0.0F);
                        }
                    case 8:
                        try
                        {
                            er.setJzValue(Float.valueOf(items[i]).floatValue());
                        } catch (NumberFormatException e) {
                            er.setJzValue(0.0F);
                        }
                }
            }

            erList.add(er);
        }
        return erList;
    }
}