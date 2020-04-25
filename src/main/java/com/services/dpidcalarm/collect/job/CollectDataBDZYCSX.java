package com.services.dpidcalarm.collect.job;

import com.services.dpidcalarm.collect.bean.BDZYCSXDetails;
import com.services.dpidcalarm.collect.bean.BDZYCSXScore;
import com.services.dpidcalarm.utils.ErrorCountHtmlUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description：变电站遥测刷新率
 * @Author：zhangtao
 * @Date：2019/11/16 0002 20:40:51
 */
public class CollectDataBDZYCSX
{
    private Logger logger = LoggerFactory.getLogger(CollectDataBDZYCSX.class);
    //客户端句柄
    private CloseableHttpClient client = null;

    public CollectDataBDZYCSX(){

    }

    /**
     * 登录方法
     * @return  是否登录成功
     */
    public boolean login(String loginURL, String user, String pwd){
        try {
            this.client = HttpClients.createDefault();
            NameValuePair username = new BasicNameValuePair("user", "gtzh");
            NameValuePair password = new BasicNameValuePair("pass", "123456");
            List list = new ArrayList();
            list.add(username);
            list.add(password);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            HttpPost post = new HttpPost("http://10.55.6.114/Loginout.gc");
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            this.logger.info(EntityUtils.toString(response.getEntity()));
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取错误情况
     * @param url
     * @param param1
     * @param param2
     * @param param3
     * @return
     * @throws IOException
     */
    public String getDetailsHtml(String url, String param1, String param2, String param3 ) {
        try{
            //String url = "http://10.55.6.114/check/WarnReport_WarnReport.gc?tableType=city&field=南岸&dataType=day&dianshu=5";
            HttpGet get = new HttpGet("http://10.55.6.114/check/WarnReport_WarnReport.gc?tableType=city&field=" + param1 +"&dataType=day&dianshu=5");
            return EntityUtils.toString(this.client.execute(get).getEntity());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }catch (Throwable e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;

    }

    public BDZYCSXScore getCurrentBDZYCSXScore(String detailsHtml){
        BDZYCSXScore bdzycsxScore = new BDZYCSXScore();
        //1、解析html，解析
        Map<String, String> resultMap =  ErrorCountHtmlUtils.getCountMap(detailsHtml);
        //key:重庆.长寿.涪陵.桥南站    value:34;0.09
        if(resultMap.size()==0){
            //没有问题，100分
            bdzycsxScore.setScore(100);
            return bdzycsxScore;
        }
        double currentScore = 100.0;
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {

            //值
            String value = entry.getValue();
            //问题点数
            String problemCoun = value.split(";")[0];
            //扣分数
            String scoreStr = value.split(";")[1];
            double tempScore = Double.parseDouble(scoreStr);
            //当前得分
            currentScore = currentScore - tempScore;
            if(tempScore>0){
                //有扣分需要记录扣分情况
                BDZYCSXDetails details = new BDZYCSXDetails();
                details.setStationName( entry.getKey());
                details.setProblemCount(Integer.parseInt(problemCoun));
                details.setDeductPoint((float)tempScore);
                //加入详情队列
                bdzycsxScore.getDetailsList().add(details);
            }
        }

        //设置当前得分，并返回详情对象
        bdzycsxScore.setScore(currentScore);
        return bdzycsxScore ;
    }
}