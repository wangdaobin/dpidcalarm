package com.services.dpidcalarm.collect.service;

import com.services.dpidcalarm.collect.dao.IndicatorDataDao;
import com.services.dpidcalarm.collect.job.CollectDataBDZYCSX;
import com.services.dpidcalarm.collect.job.CollectDataDDHGL;
import com.services.dpidcalarm.collect.job.CollectScoreByForm;
import com.services.dpidcalarm.sms.SmsService;
import com.services.dpidcalarm.utils.MyDateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IndicatorDataDao indicatorDataDao;


    public void reptilian1(){
        //指标数据获取
        logger.info("指标数据获取："+new Date());


    }
    /**
     * 业务逻辑
     */
    public void reptilian(){
        SmsService smsService = new SmsService();



        logger.info("参数-是否发送短信：" + sendFlag);
        logger.info("参数-限值：" + limitValueStr);
        logger.info("参数-发送号码：" + phone);
        double limitValue =  Double.parseDouble(limitValueStr);
        long currentTime = System.currentTimeMillis();


        /****6 单独合格率****/

        try {
            //指标数据获取
            logger.info("单独合格率指标数据获取开始：");
            CollectDataDDHGL collectData= new CollectDataDDHGL();
            collectData.login(null,null,null);
            String svgImage = collectData.getSvgImageForKHZB(null);
            logger.info("单独合格率指标指标数据：svgImage：" + svgImage);
            double scoreDDHGL =  collectData.getResultDDHGL(svgImage);
            logger.info("单独合格率指标得分：" + scoreDDHGL);
            if(scoreDDHGL < limitValue && 1==sendFlag){
                smsService.sendSms(phone, "单独合格率指标得分" + scoreDDHGL + "，小于限值" + limitValueStr);
                logger.info("单独合格率指标得分" + scoreDDHGL + "，小于限值" + limitValueStr + "发送短息到" + phone);
            }
            //存储指标
            indicatorDataDao.insertIndicatorHisData(6,(float)scoreDDHGL , new Date(currentTime));
            //更新实时
            indicatorDataDao.insertIndicatorHisData(6,(float)scoreDDHGL , new Date(currentTime));

        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
        }

        /**变电站遥测刷新指标**/

        try {
            //指标数据获取
            logger.info("变电站遥测刷新指标数据获取开始：");
            CollectDataBDZYCSX collectDataBDZYCSX = new CollectDataBDZYCSX();
            collectDataBDZYCSX.login(null,null,null);
            String detailsHtml = collectDataBDZYCSX.getDetailsHtml(null,null,null,null);
            logger.info("变电站遥测刷新指标数据：resultHTML：" + detailsHtml);
            double scoreBDZYCSX =  collectDataBDZYCSX.getCurrentScore(detailsHtml);
            logger.info("变电站遥测刷新指标得分：" + scoreBDZYCSX);
            if(scoreBDZYCSX < limitValue && 1==sendFlag){
                smsService.sendSms(phone, "变电站遥测刷新指标得分" + scoreBDZYCSX + "，小于限值" + limitValueStr);
                logger.info("变电站遥测刷新指标得分" + scoreBDZYCSX + "，小于限值" + limitValueStr + "发送短息到" + phone);
            }
            //存储指标
            indicatorDataDao.insertIndicatorHisData(1,(float)scoreBDZYCSX , new Date(currentTime));
            //更新实时
            indicatorDataDao.insertIndicatorHisData(1,(float)scoreBDZYCSX , new Date(currentTime));

        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
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
        String compony = "113715891329302535";
        //开始日期为当前时期
        String currentDay = MyDateUtils.getCurrentDayStr();


        try {
            //*************2、开关指标得分*************//
            //开关表名
            String table_kg = "bk_con_cord_5";
            String resultJSON = collectScoreByForm.getCurrentScoreJSON(scoreURL,compony,table_kg,currentDay);
            // System.out.println("开关指标得分获取结果：resultJSON："+resultJSON);
            logger.info("开关指标得分获取结果：resultJSON：" + resultJSON);
            double scoreKGZB = collectScoreByForm.dealcurrentScoreJSON(resultJSON);
            logger.info("开关指标得分：" + scoreKGZB);
            if(scoreKGZB < limitValue && 1==sendFlag){
                logger.info("开关指标得分" + scoreKGZB + "，小于限值" + limitValueStr + "发送短息到" + phone);
                smsService.sendSms(phone, "开关指标得分" + scoreKGZB + "，小于限值" + limitValueStr);

            }

            //存储历史指标
            indicatorDataDao.insertIndicatorHisData(2,(float)scoreKGZB , new Date(currentTime));
            //存储实时表
            indicatorDataDao.updateIndicatorRtData(2,(float)scoreKGZB , new Date(currentTime));

        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
        }



        try {
            //*************3、遥测遥信得分*************
            //遥测遥信表名
            String table_ycyx = "excDeviceCord";
            //开始日期为当前时期
            String resultJSON_ycyx = collectScoreByForm.getCurrentScoreJSON(scoreURL,compony,table_ycyx,currentDay);
            // System.out.println("遥测遥信获取结果：resultJSON："+resultJSON_ycyx);
            logger.info("遥测遥信获取结果：resultJSON：" + resultJSON_ycyx);
            double scoreYCYX = collectScoreByForm.dealcurrentScoreJSON(resultJSON_ycyx);
            logger.info("遥测遥信得分：" + scoreYCYX);
            if(scoreYCYX < limitValue && 1 == sendFlag){
                logger.info("遥测遥信得分" + scoreYCYX + "，小于限值" + limitValueStr + "发送短息到" + phone);
                smsService.sendSms(phone, "遥测遥信得分" + scoreYCYX + "，小于限值" + limitValueStr);

            }

            //存储指标
            indicatorDataDao.insertIndicatorHisData(3,(float)scoreYCYX , new Date(currentTime));
            indicatorDataDao.updateIndicatorRtData(3,(float)scoreYCYX , new Date(currentTime));

        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
        }



        try {
            //*************4、积分电量得分  昨日的值*************
            String scoreURL_jfdl = "http://10.55.6.114/analysis/TmrJFMonthCord_j.gc";
            //String compony_jfdl = "南岸分公司";
            String compony_jfdl = "%e5%8d%97%e5%b2%b8%e5%88%86%e5%85%ac%e5%8f%b8";
            //开始日期为当前时期
            String yesterdayStr = MyDateUtils.getYesterdayDayStr();
            String resultJSON_jfdl = collectScoreByForm.getCurrentScoreJSON(scoreURL_jfdl,compony_jfdl,null,yesterdayStr);
            // System.out.println("积分电量的获取结果：resultJSON："+resultJSON_jfdl);
            logger.info("积分电量的获取结果：resultJSON："+resultJSON_jfdl);
            double scoreJFDL = collectScoreByForm.dealcurrentScoreJSON(resultJSON_jfdl);
            logger.info("积分电量得分：" + scoreJFDL);
            // if(scoreJFDL < limitValue && "1".equals(sendFlag)){
            //     smsService.sendSms(phone, "积分电量的获取结果" + scoreJFDL + "，小于限值" + limitValueStr);
            //     logger.info("积分电量的获取结果" + scoreYCYX + "，小于限值" + limitValueStr + "发送短息到" + phone);
            // }
            //存储指标
            indicatorDataDao.insertIndicatorHisData(4,(float)scoreJFDL , new Date(currentTime));
            //更新实时
            indicatorDataDao.updateIndicatorRtData(4,(float)scoreJFDL , new Date(currentTime));
        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
        }


        try {
            //*************5、事故分闸得分*************
            //url http://10.55.6.114/analysis/Daycord_j.gc
            String table_sgfz = "sdcinfoCord";
            //开始日期为当前时期
            String resultJSON_sgfz = collectScoreByForm.getCurrentScoreJSON(scoreURL,compony,table_sgfz,currentDay);
            // System.out.println("遥测遥信获取结果：resultJSON："+resultJSON_ycyx);
            logger.info("事故分闸得分获取结果：resultJSON：" + resultJSON_sgfz);
            double scoreSGFZ = collectScoreByForm.dealcurrentScoreJSON(resultJSON_sgfz);
            logger.info("事故分闸得分：" + scoreSGFZ);
            if(scoreSGFZ < limitValue && 1 == sendFlag){
                logger.info("事故分闸得分" + scoreSGFZ + "，小于限值" + limitValueStr + "发送短息到" + phone);
                smsService.sendSms(phone, "事故分闸得分" + scoreSGFZ + "，小于限值" + limitValueStr);

            }

            //存储指标
            indicatorDataDao.insertIndicatorHisData(5,(float)scoreSGFZ , new Date(currentTime));
            indicatorDataDao.updateIndicatorRtData(5,(float)scoreSGFZ , new Date(currentTime));

        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
        }




    }



}