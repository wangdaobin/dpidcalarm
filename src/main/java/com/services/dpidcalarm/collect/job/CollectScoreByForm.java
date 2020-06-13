package com.services.dpidcalarm.collect.job;

import com.services.dpidcalarm.utils.MyDateUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description：开关位置频繁变位得分、遥信遥测状态不一致得分、积分电量误差率得分情况
 * @Author：zhangtao
 * @Date：2019/11/16 0002 20:40:51
 */
public class CollectScoreByForm
{
    private Logger logger = LoggerFactory.getLogger(CollectScoreByForm.class);
    //客户端句柄
    private CloseableHttpClient client = null;

    /**
     * 构造函数
     */
    public CollectScoreByForm(){

    }

    /**
     * 登录方法，登录方法是固定的
     * @return  是否登录成功
     */
    public boolean login(String loginURL, String user, String pwd){
        try {
            client = HttpClients.createDefault();
            NameValuePair username = new BasicNameValuePair("user", "gtzh");
            NameValuePair password = new BasicNameValuePair("pass", "123456");
            List list = new ArrayList();
            list.add(username);
            list.add(password);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            HttpPost post = new HttpPost("http://10.55.6.114/Loginout.gc");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            this.logger.info("登录结果：" + EntityUtils.toString(response.getEntity()));
            return true;
        } catch (Exception e){
            this.logger.info("登录出错，错误内容: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 得分： 开关和遥测遥信只是表名不一样。 积分电量url和开始日期（昨天）和com（中文字符，不一样）
     * @param scoreURL   开关和遥测遥信的url一致都是http://10.55.6.114/analysis/Daycord_j.gc， 积分电量url：http://10.55.6.114/analysis/TmrJFMonthCord_j.gc
     * @param compony  开关和遥测遥信的是编号“113715891329302536”，积分电量的是“市区分公司”
     * @param table  开关“bk_con_cord_5”  遥测遥信“excDeviceCord” 积分电量没有table
     * @param startDay 开关和遥测是今天，积分电量是昨天
     * @return
     * @throws IOException
     */
    public String getCurrentScoreJSON(String scoreURL, String compony, String table, String startDay ){
        try{
            // //url
            // HttpPost post = new HttpPost("http://10.55.6.114/analysis/Daycord_j.gc");
            // //公司
            // NameValuePair com = new BasicNameValuePair("com", "113715891329302534");
            // //表名
            // NameValuePair table = new BasicNameValuePair("table", "bk_con_cord_5");
            // 开始日期为当前时期
            // String currentDay = MyDateUtils.getCurrentDayStr();

            HttpPost post = new HttpPost(scoreURL);
            //参数列表
            List paramlist = new ArrayList();
            //公司
            NameValuePair compony_nvp = new BasicNameValuePair("com", compony);
            paramlist.add(compony_nvp);
            //表名
            NameValuePair table_nvp = null;
            if(table!=null){
                //表名
                table_nvp = new BasicNameValuePair("table", table);
                paramlist.add(table_nvp);
            }
            //开始日期
            NameValuePair startDay_nvp = new BasicNameValuePair("startDay", startDay);
            paramlist.add(startDay_nvp);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramlist,Consts.UTF_8);
            //设置参数
            post.setEntity(entity);
            //执行post请求
            HttpResponse response = client.execute(post);
            String resultJSON = EntityUtils.toString(response.getEntity());
            this.logger.info("详情获取结果：" + resultJSON);
            return resultJSON;
        }catch (Exception e){
            this.logger.info("详情获取出错，错误内容: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public double dealcurrentScoreJSON(String jsonStrResult){
        //1、解析json
        //json样例{"com":"113715891329302534","countNo":null,"datas":[{"x":1575129600000,"y":100.0},{"x":1575216000000,"y":99.91},{"x":1575302400000,"y":99.99},{"x":1575388800000,"y":99.92},{"x":1575475200000,"y":99.94},{"x":1575561600000,"y":99.95},{"x":1575648000000,"y":99.96},{"x":1575734400000,"y":99.88},{"x":1575820800000,"y":99.99},{"x":1575907200000,"y":99.99},{"x":1575993600000,"y":99.99},{"x":1576080000000,"y":99.89}],"name":"正确率","startDay":"2019-12-12","table":"bk_con_cord_5"}
        if(null==jsonStrResult){
            return -1;
        }
        try {
            JSONObject json = new JSONObject(jsonStrResult);
            //公司
            String com = json.getString("com");
            //表(积分电量没有表的概念）
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
                        this.logger.info("当前分数为：" + currentScore);
                        // System.out.println("当前分数为：" + currentScore);
                        return currentScore;
                    }
                }

            }

        }catch (Exception e){
            this.logger.error("获取得分出错：",e);
        }
        return -1;
    }
}