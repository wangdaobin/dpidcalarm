package com.services.dpidcalarm.collect.service;

import com.services.dpidcalarm.collect.bean.BDZYCSXDetails;
import com.services.dpidcalarm.collect.bean.BDZYCSXScore;
import com.services.dpidcalarm.collect.bean.IndicatorData;
import com.services.dpidcalarm.collect.dao.IndicatorDataDao;
import com.services.dpidcalarm.collect.job.CollectDataBDZYCSX;
import com.services.dpidcalarm.collect.job.CollectDataZTGJ;
import com.services.dpidcalarm.collect.job.CollectScoreByForm;
import com.services.dpidcalarm.sms.SmsService;
import com.services.dpidcalarm.sysManager.bean.Indicator;
import com.services.dpidcalarm.utils.ErrorCountHtmlUtils;
import com.services.dpidcalarm.utils.IndicatorUtils;
import com.services.dpidcalarm.utils.MyDateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    //供电公司中文名称（含“分公司”3个字）
    @Value("${com.name}")
    private String comName;
    //供电公司中文简称（不含“分公司”3个字，变电站遥测刷新率专用）
    @Value("${com.shortname}")
    private String comShortName;
    //供电公司编号
    @Value("${com.no}")
    private String comNo;

    @Autowired
    private IndicatorDataDao indicatorDataDao;

    double limitValue ;
    long currentTime;
    private SmsService smsService = null;

    //指标工具对象
    IndicatorUtils indicatorUtils = new IndicatorUtils();

    /**
     * 业务逻辑
     */
    public void reptilian(){
        smsService = new SmsService();
        currentTime = System.currentTimeMillis();



        logger.info("参数-是否发送短信(0不发送，1发送): " + sendFlag);
        logger.info("参数-发送号码：" + phone);

        logger.info("供电公司中文名称：" + comName);
        logger.info("供电公司中文简称：" + comShortName);
        logger.info("供电公司编号：" + comNo);




        //变电站遥测刷新率
        this.dealBDZSXScore();







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
        //开始日期为当前时期
        String currentDay = MyDateUtils.getCurrentDayStr();


        try {
            //*************2、开关指标得分*************//
            //开关表名
            String table_kg = "bk_con_cord_5";
            String resultJSON = collectScoreByForm.getCurrentScoreJSON(scoreURL,comNo,table_kg,currentDay);
            // System.out.println("开关指标得分获取结果：resultJSON："+resultJSON);
            logger.info("开关指标得分获取结果：resultJSON：" + resultJSON);
            double scoreKGZB = collectScoreByForm.dealcurrentScoreJSON(resultJSON);
            logger.info("开关指标得分：" + scoreKGZB);

            /********存储历史指标*********/
            //新建历史对象
            IndicatorData  indicatorData = new IndicatorData();
            //设置指标id、值、时间
            indicatorData.setIdcID(2);
            indicatorData.setIdcValue((float)scoreKGZB);
            indicatorData.setCollectTime(new Date(currentTime));
            //插入历史指标
            int id1  = indicatorDataDao.insertIndicatorHisData(indicatorData);
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
            String resultJSON_ycyx = collectScoreByForm.getCurrentScoreJSON(scoreURL,comNo,table_ycyx,currentDay);
            // System.out.println("遥测遥信获取结果：resultJSON："+resultJSON_ycyx);
            logger.info("遥测遥信获取结果：resultJSON：" + resultJSON_ycyx);
            double scoreYCYX = collectScoreByForm.dealcurrentScoreJSON(resultJSON_ycyx);
            logger.info("遥测遥信得分：" + scoreYCYX);

            /********存储历史指标*********/
            //新建历史对象
            IndicatorData  indicatorData = new IndicatorData();
            //设置指标id、值、时间
            indicatorData.setIdcID(3);
            indicatorData.setIdcValue((float)scoreYCYX);
            indicatorData.setCollectTime(new Date(currentTime));
            //插入历史指标
            int id1  = indicatorDataDao.insertIndicatorHisData(indicatorData);
            indicatorDataDao.updateIndicatorRtData(3,(float)scoreYCYX , new Date(currentTime));

        }catch (Exception e){
            logger.error("遥测遥信出错" + e.getMessage());
            e.getMessage();
        }





        /****6 状态估计合格率****/

        try {
            //指标数据获取
            logger.info("状态估计合格率指标数据获取开始：");
            CollectDataZTGJ collectData= new CollectDataZTGJ();
            collectData.login(null,null,null);
            String svgImage = collectData.getSvgImageForKHZB(null);
            logger.info("状态估计合格率指标指标数据：svgImage：" + svgImage);
            double scoreZTGJ =  collectData.getResultZTGJ(svgImage,comShortName);
            logger.info("状态估计合格率指标得分：" + scoreZTGJ);

            /********存储历史指标*********/
            //新建历史对象
            IndicatorData  indicatorData = new IndicatorData();
            //设置指标id、值、时间
            indicatorData.setIdcID(6);
            indicatorData.setIdcValue((float)scoreZTGJ);
            indicatorData.setCollectTime(new Date(currentTime));
            //插入历史指标
            int id1  = indicatorDataDao.insertIndicatorHisData(indicatorData);
            //更新实时
            indicatorDataDao.updateIndicatorRtData(6,(float)scoreZTGJ , new Date(currentTime));

        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
        }

        //更新指标工具对象
        indicatorUtils.setListIndicator(indicatorDataDao.queryAllIndicator());
        /**
         * 这两个指标都是今天才能取到昨天统计的值，所以都定义为每天8之后点取指标。
         * 如果没取到则继续去，如果取到了，则更新最新记录、历史记录、以及历史记录对应的详情，
         * 下次5分钟时判断当天是否已经有值
         */

        //如果最后时间不是今天，并且当前时间是8点以后
        Calendar calendar  = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(!indicatorUtils.getIndicatorIsUpdateToday(4) && hour>8){
            try {
                //*************4、积分电量得分  昨日的值*************
                String scoreURL_jfdl = "http://10.55.6.114/analysis/TmrJFMonthCord_j.gc";
                // String compony_jfdl = "南岸分公司";
                // compony_jfdl = URLEncoder.encode(compony_jfdl,"UTF-8");
                // logger.info("compony_jfdl：" + compony_jfdl);
                //String compony_jfdl = "%e5%8d%97%e5%b2%b8%e5%88%86%e5%85%ac%e5%8f%b8";
                //开始日期为昨日时间
                String yesterdayStr = MyDateUtils.getYesterdayDayStr();
                String resultJSON_jfdl = collectScoreByForm.getCurrentScoreJSON(scoreURL_jfdl,comName,null,yesterdayStr);
                // System.out.println("积分电量的获取结果：resultJSON："+resultJSON_jfdl);
                logger.info("积分电量的获取结果：resultJSON："+resultJSON_jfdl);
                double scoreJFDL = collectScoreByForm.dealcurrentScoreJSON(resultJSON_jfdl);
                logger.info("积分电量得分：" + scoreJFDL);

                /********存储历史指标*********/
                //新建历史对象
                IndicatorData  indicatorData = new IndicatorData();
                //设置指标id、值、时间
                indicatorData.setIdcID(4);
                indicatorData.setIdcValue((float)scoreJFDL);
                indicatorData.setCollectTime(new Date(currentTime));
                //插入历史指标
                int id1  = indicatorDataDao.insertIndicatorHisData(indicatorData);
                //更新实时
                indicatorDataDao.updateIndicatorRtData(4,(float)scoreJFDL , new Date(currentTime));
            }catch (Exception e){
                logger.error("积分电量出错" + e.getMessage());
                e.getMessage();
            }
        }



        if(!indicatorUtils.getIndicatorIsUpdateToday(5) && hour>8){
            try {
                //*************5、事故分闸得分  昨日的值*************
                //url http://10.55.6.114/analysis/Daycord_j.gc
                //String scoreURL_sgfz = "http://10.55.6.114/analysis/SdcInfoCordRep_SdcInfoCordRep.gc.gc";
                String scoreURL_sgfz = "http://10.55.6.114/analysis/Daycord_j.gc";
                String table_sgfz = "sdcinfoCord";
                //开始日期为昨日时间
                String yesterdayStr = MyDateUtils.getYesterdayDayStr();
                String resultJSON_sgfz = collectScoreByForm.getCurrentScoreJSON(scoreURL_sgfz,comNo,table_sgfz,yesterdayStr);
                logger.info("事故分闸得分获取结果：resultJSON：" + resultJSON_sgfz);
                double scoreSGFZ = collectScoreByForm.dealcurrentScoreJSON(resultJSON_sgfz);

                /********存储历史指标*********/
                //新建历史对象
                IndicatorData  indicatorData = new IndicatorData();
                //设置指标id、值、时间
                indicatorData.setIdcID(5);
                indicatorData.setIdcValue((float)scoreSGFZ);
                indicatorData.setCollectTime(new Date(currentTime));
                //插入历史指标
                int id1  = indicatorDataDao.insertIndicatorHisData(indicatorData);
                indicatorDataDao.updateIndicatorRtData(5,(float)scoreSGFZ , new Date(currentTime));

            }catch (Exception e){
                logger.error("事故分闸出错" + e.getMessage());
                e.getMessage();
            }
        }



        //更新指标工具对象
        indicatorUtils.setListIndicator(indicatorDataDao.queryAllIndicator());

        //执行发送短信流程

        this.sendMsg(indicatorUtils);

    }






    /**
     * 变电站遥测刷新指标
     */
    private void dealBDZSXScore(){


        try {
            /*******指标数据数据模拟 ***********/
            /*
            File file  = new File("E:\\31-重庆\\网站数据\\指标20191212\\01变电站刷新\\变电站刷新-详细--20191224.html");
            String detailsHtml = null;
            try {
                detailsHtml = FileUtils.readFileToString(file);
                // Map<String,String> map = ErrorCountHtmlUtils.getCountMap(fileContent);
                // double currentScore = ErrorCountHtmlUtils.getCurrentScore(fileContent);
                // System.out.println(currentScore);
            }catch (Exception e){
                e.printStackTrace();
            }
            */


            //指标数据获取
            logger.info("变电站遥测刷新指标数据获取开始：");
            CollectDataBDZYCSX collectDataBDZYCSX = new CollectDataBDZYCSX();
            collectDataBDZYCSX.login(null,null,null);
            String detailsHtml = collectDataBDZYCSX.getDetailsHtml(null,comShortName,null,null);
            logger.info("变电站遥测刷新指标数据：resultHTML：" + detailsHtml);
            BDZYCSXScore bdzycsxScore =  collectDataBDZYCSX.getCurrentBDZYCSXScore(detailsHtml);
            double score = bdzycsxScore.getScore();
            logger.info("变电站遥测刷新指标得分：" + score);

            /********更新实时*********/
            Integer id  = indicatorDataDao.updateIndicatorRtData(1,(float)score , new Date(currentTime));
            /********存储历史指标*********/
            //新建历史对象
            IndicatorData  indicatorData = new IndicatorData();
            //设置指标id、值、时间
            indicatorData.setIdcID(1);
            indicatorData.setIdcValue((float)score);
            indicatorData.setCollectTime(new Date(currentTime));
            //插入历史指标
            int id1  = indicatorDataDao.insertIndicatorHisData(indicatorData);


            /********存储历史指标对应的详情扣分*********/
            if(id1==1 && score<100){
                //历史指标插入成功，并且得分小于100（说明有问题点数）进行详情插入
                List<BDZYCSXDetails> detailsList = bdzycsxScore.getDetailsList();
                for(BDZYCSXDetails details: detailsList){
                    indicatorDataDao.insertBDZYCSXDetailData(indicatorData.getId(),details.getStationName(),
                            details.getProblemCount(),details.getDeductPoint());
                }

            }

            logger.info("indicatorData.id：" + indicatorData.getId());


        }catch (Exception e){
            logger.error(e.getMessage());
            e.getMessage();
        }

    }

    /**
     * 发短信工具
     */
    private void sendMsg(IndicatorUtils indicatorUtils){

        for(int indicatorID=1;indicatorID<7;indicatorID++){
            //指标名称
            String name = indicatorUtils.getIndicatorName(indicatorID);
            //告警以发送次数
            int sendMsgCount = indicatorUtils.getIndicatorSentMsgCount(indicatorID);
            //指标限值
            float limitValue = indicatorUtils.getIndicatorLimitValue(indicatorID);
            //指标最后获取值
            float lastValue = indicatorUtils.getIndicatorLastValue(indicatorID);
            //最后更新值不出错（-1），并且小于限值，并且发送次数小于3
            if(lastValue!=-1 && lastValue < limitValue && 1==sendFlag && sendMsgCount<2){
                //发送短息
                logger.info(name + "得分:" + lastValue + "，小于限值" + limitValue + "发送短息到" + phone);
                smsService.sendSms(phone, name +"得分:" + lastValue + "，小于限值" + limitValue);
                //更新最后发送次数
                indicatorDataDao.updateIndicatorMsgSendCount(indicatorID,sendMsgCount+1);
            }else if(lastValue >= limitValue){
                //
                indicatorDataDao.updateIndicatorMsgSendCount(indicatorID,0);
            }
        }

    }



}