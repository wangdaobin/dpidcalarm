package com.services.dpidcalarm.collect.service;

import com.services.dpidcalarm.collect.job.CollectDataBDZYCSX;
import com.services.dpidcalarm.collect.job.CollectScoreByForm;
import com.services.dpidcalarm.sms.SmsService;
import com.services.dpidcalarm.utils.MyDateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/12/19 0019 16:16:34
 */
@Service
public class QuartzTask {
    private static Logger logger = LoggerFactory.getLogger(QuartzTask.class);
    @Value("${send.flag}")
    private int sendFlag;

    @Value("${phone}")
    private String phone;

    @Value("${limit.value}")
    private String limitValueStr;


    public void reptilian1(){
        //指标数据获取
        logger.info("指标数据获取："+new Date());


    }
    /**
     * 业务逻辑
     */
    public void reptilian(){
        SmsService smsService = new SmsService();



        logger.info("参数-是否发送：" + sendFlag);
        logger.info("参数-限值：" + limitValueStr);
        logger.info("参数-发送号码：" + phone);
        double limitValue = Double.parseDouble(limitValueStr);

        //指标数据获取
        logger.info("变电站遥测刷新指标数据获取开始：");
        CollectDataBDZYCSX collectDataBDZYCSX = new CollectDataBDZYCSX();
        collectDataBDZYCSX.login(null,null,null);
        String detailsHtml = collectDataBDZYCSX.getDetailsHtml(null,null,null,null);
        logger.info("变电站遥测刷新指标数据：resultHTML：" + detailsHtml);
        double scoreBDZYCSX =  collectDataBDZYCSX.getCurrentScore(detailsHtml);
        logger.info("变电站遥测刷新指标得分：" + scoreBDZYCSX);
        if(scoreBDZYCSX < limitValue && "1".equals(sendFlag)){
            smsService.sendSms(phone, "变电站遥测刷新指标得分" + scoreBDZYCSX + "，小于限值" + limitValueStr);
            logger.info("变电站遥测刷新指标得分" + scoreBDZYCSX + "，小于限值" + limitValueStr + "发送短息到" + phone);
        }



        logger.info("开关、遥测遥信、积分电量指标数据获取开始：");
        CollectScoreByForm collectScoreByForm = new CollectScoreByForm();
        //**************1、登录*****************//
        // 内部有写死的url，所有的指标登录都是一样的，本部分只做测试，后续从数据库读
        collectScoreByForm.login(null,null,null);

        /**
         * 得分： 开关和遥测遥信只是表名不一样。 积分电量url和开始日期（昨天）和com（中文字符，不一样）
         * @param scoreURL   开关和遥测遥信的url一致都是http://10.55.6.114/analysis/Daycord_j.gc， 积分电量url：http://10.55.6.114/analysis/TmrJFMonthCord_j.gc
         * @param compony  开关和遥测遥信的是编号“113715891329302536”，积分电量的是“市区分公司”
         * @param table  开关“bk_con_cord_5”  遥测遥信“excDeviceCord” 积分电量没有table
         * @param startDay 开关和遥测是今天，积分电量是昨天
         * @return
         * @throws IOException
         */

        //url
        String scoreURL = "http://10.55.6.114/analysis/Daycord_j.gc";
        //公司
        //
        // String compony = "113715891329302534";
        String compony = "113715891329302535";
        //*************2、开关指标得分*************//
        //开关表名
        String table_kg = "bk_con_cord_5";
        //开始日期为当前时期
        String currentDay = MyDateUtils.getCurrentDayStr();
        String resultJSON = collectScoreByForm.getCurrentScoreJSON(scoreURL,compony,table_kg,currentDay);
        // System.out.println("开关指标得分获取结果：resultJSON："+resultJSON);
        logger.info("开关指标得分获取结果：resultJSON：" + resultJSON);
        double scoreKGZB = collectScoreByForm.dealcurrentScoreJSON(resultJSON);
        logger.info("开关指标得分：" + scoreKGZB);
        if(scoreKGZB < limitValue && "1".equals(sendFlag)){
            smsService.sendSms(phone, "开关指标得分" + scoreBDZYCSX + "，小于限值" + limitValueStr);
            logger.info("开关指标得分" + scoreKGZB + "，小于限值" + limitValueStr + "发送短息到" + phone);
        }


        //*************3、遥测遥信得分*************
        //遥测遥信表名
        String table_ycyx = "excDeviceCord";
        //开始日期为当前时期
        String resultJSON_ycyx = collectScoreByForm.getCurrentScoreJSON(scoreURL,compony,table_ycyx,currentDay);
        // System.out.println("遥测遥信获取结果：resultJSON："+resultJSON_ycyx);
        logger.info("遥测遥信获取结果：resultJSON：" + resultJSON_ycyx);
        double scoreYCYX = collectScoreByForm.dealcurrentScoreJSON(resultJSON_ycyx);
        logger.info("遥测遥信得分：" + scoreYCYX);
        if(scoreYCYX < limitValue && "1".equals(sendFlag)){
            smsService.sendSms(phone, "遥测遥信得分" + scoreBDZYCSX + "，小于限值" + limitValueStr);
            logger.info("遥测遥信得分" + scoreYCYX + "，小于限值" + limitValueStr + "发送短息到" + phone);
        }

        //*************3、积分电量得分*************
        //开始日期为当前时期
        String scoreURL_jfdl = "http://10.55.6.114/analysis/TmrJFMonthCord_j.gc";
        String compony_jfdl = "南岸分公司";
        String resultJSON_jfdl = collectScoreByForm.getCurrentScoreJSON(scoreURL_jfdl,compony_jfdl,null,currentDay);
        // System.out.println("积分电量的获取结果：resultJSON："+resultJSON_jfdl);
        logger.info("积分电量的获取结果：resultJSON："+resultJSON_jfdl);
        double scoreJFDL = collectScoreByForm.dealcurrentScoreJSON(resultJSON_jfdl);
        logger.info("积分电量得分：" + scoreJFDL);
        if(scoreJFDL < limitValue && "1".equals(sendFlag)){
            smsService.sendSms(phone, "积分电量的获取结果" + scoreJFDL + "，小于限值" + limitValueStr);
            logger.info("积分电量的获取结果" + scoreYCYX + "，小于限值" + limitValueStr + "发送短息到" + phone);
        }


    }


    // public void reptilian(){
    //     //指标数据获取
    //     System.out.println("指标数据获取："+new Date());
    //     CollectScoreByForm collectScoreByForm = new CollectScoreByForm();
    //     //**************1、登录*****************//
    //     // 内部有写死的url，所有的指标登录都是一样的，本部分只做测试，后续从数据库读
    //     collectScoreByForm.login(null,null,null);
    //
    //     /**
    //      * 得分： 开关和遥测遥信只是表名不一样。 积分电量url和开始日期（昨天）和com（中文字符，不一样）
    //      * @param scoreURL   开关和遥测遥信的url一致都是http://10.55.6.114/analysis/Daycord_j.gc， 积分电量url：http://10.55.6.114/analysis/TmrJFMonthCord_j.gc
    //      * @param compony  开关和遥测遥信的是编号“113715891329302536”，积分电量的是“市区分公司”
    //      * @param table  开关“bk_con_cord_5”  遥测遥信“excDeviceCord” 积分电量没有table
    //      * @param startDay 开关和遥测是今天，积分电量是昨天
    //      * @return
    //      * @throws IOException
    //      */
    //
    //     //url
    //     String scoreURL = "http://10.55.6.114/analysis/Daycord_j.gc";
    //     //公司
    //     String compony = "113715891329302534";
    //     //*************2、开关指标得分*************//
    //     //开关表名
    //     String table_kg = "bk_con_cord_5";
    //     //开始日期为当前时期
    //     String currentDay = MyDateUtils.getCurrentDayStr();
    //     String resultJSON = collectScoreByForm.getCurrentScore(scoreURL,compony,table_kg,currentDay);
    //     System.out.println("获取结果：resultJSON："+resultJSON);
    //     collectScoreByForm.dealcurrentScore(resultJSON);
    //
    //     /*
    //
    //     //*************3、遥测遥信得分*************
    //     //遥测遥信表名
    //     String table_ycyx = "bk_con_cord_5";
    //     //开始日期为当前时期
    //     String resultJSON_ycyx = collectScoreByForm.getCurrentScore(scoreURL,compony,table_ycyx,currentDay);
    //     System.out.println("获取结果：resultJSON："+resultJSON_ycyx);
    //     collectScoreByForm.dealcurrentScore(resultJSON_ycyx);
    //
    //     //*************3、积分电量得分*************
    //     //开始日期为当前时期
    //     String resultJSON_jfdl = collectScoreByForm.getCurrentScore(scoreURL,compony,null,currentDay);
    //     System.out.println("获取结果：resultJSON："+resultJSON_ycyx);
    //     collectScoreByForm.dealcurrentScore(resultJSON_ycyx);
    //     */
    //
    // }


}