package com.services.dpidcalarm.collect.job;

/**
 * @Description：单独合格率获取服务
 * @Author：zhangtao
 * @Date：2019/11/2 0002 16:58:27
 */
import com.services.dpidcalarm.collect.bean.ErrorResult;
import com.services.dpidcalarm.collect.bean.SvgUtil;
import com.services.dpidcalarm.datainfo.bean.DataInfo;
import com.services.dpidcalarm.datainfo.bean.DataInfoError;
import com.services.dpidcalarm.datainfo.service.DataInfoServiceAdapter;
import com.services.dpidcalarm.sconf.bean.PropertiesConfiguration;
import com.services.dpidcalarm.sconf.service.PropertiesConfigurationService;
import com.services.dpidcalarm.sms.SmsService;
import com.services.dpidcalarm.smslog.bean.SmSendLog;
import com.services.dpidcalarm.smslog.service.SmSendLogService;
import com.services.dpidcalarm.utils.CommonUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Component
public class SinglePercentOfPassServiceCore extends ThreadCommon
        implements Runnable
{

    @Autowired
    private PropertiesConfigurationService propertiesConfigurationService;

    // @Autowired
    // private UserDao userDao;

    @Autowired
    private SmSendLogService smSendLogService;

    @Autowired
    private DataInfoServiceAdapter dataInfoServiceAdapter;

    @Autowired
    private SmsService smsService;
    private static Logger logger = LoggerFactory.getLogger(SinglePercentOfPassServiceCore.class);

    //电站列表
    public static final List<String> stations = Arrays.asList(new String[] { "南湖站", "惠民站", "木洞站", "接龙站", "纳溪沟站", "安澜站", "鹿角站", "永隆站", "界石站", "五布站", "广阳站", "双河站", "姜家站", "苏家湾站", "东港站", "莲池站", "柳银站", "桥口坝站", "黄桷垭站", "回龙湾站", "百步梯站", "响水洞站", "阳光一百站", "弹子石站", "长生站", "海棠站", "花溪站", "峡口站", "迎龙站", "武堂站", "高峰寺站", "南彭站", "鱼洞站", "南坪站", "李家沱站", "土桥站", "天文站", "梓桐站", "丹桂站", "金竹站", "二塘站", "光国站", "天星寺站", "龙门浩站", "龙洲湾站", "柏子桥站", "书房站", "花红站", "白马山站", "金家岩站", "虎啸站", "四公里站", "鸡冠石站", "走马羊站" });
    private Map<String, Float> stationMap;
    private List<ErrorResult> ers = new ArrayList();

    public void run()
    {
        boolean action = true;
        //初始化合格率指标获取对象
        CollectDataSPOPS cd = new CollectDataSPOPS();
        Map previousSendTimeMap = new HashMap();
        previousSendTimeMap.put("南岸", null);
        previousSendTimeMap.put("南岸f", Integer.valueOf(0));
        while (action) {
            Calendar calendar = Calendar.getInstance();
            //取出当前分钟
            int minute = calendar.get(12);
            if (minute % 5 == 0) {
                logger.info("=================================START=================================");
                CloseableHttpClient client = null;
                try {
                    //先登录
                    client = cd.login();
                    //在获取指标数据
                    String khzb = cd.getSvgImageForKHZB(client);
                    if ((khzb == null) || (khzb.length() == 0)) {
                        logger.info("数据采集为空，线程暂停2秒后重新采集");
                        client.close();
                        client = null;
                        logger.info("client is released.");
                        Thread.currentThread(); Thread.sleep(2000L);
                    }
                    //获取厂站合格率
                    Map qulifyRate = SvgUtil.getQulifyRate(khzb);
                    //??暂时没明白
                    String ersStr = cd.getNananTestError(client);
                    List ers = SvgUtil.getErrorResult(ersStr);
                    this.stationMap = qulifyRate;
                    this.ers = ers;
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("登录时连接服务器异常，线程暂停2秒后重新连接");
                    try {
                        Thread.currentThread(); Thread.sleep(2000L);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } finally {
                    if(client != null) {
                        try {
                            client.close();
                            logger.info("client is released.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            client = null;
                        }
                    }
                }
                //1、获取配置信息
                /**
                 * id,c_condition,c_value,invalid_time,work_time_start,work_time_end,cycle,is_weekend,short_msg_model,indicator_id
                 * 1,4,100,90,7,13,5,1,单独合格率为:{单独合格率};guk,1
                 * 2,3,90.8,20,0,2,5,0,,1
                 * 3,2,0,,18,22,5,1,{厂站名}:{错误点数};yyiy,2
                 * 4,4,20,,2,4,5,0,{厂站名}:{错误点数};nk%&^%&^tuy7ti7tviiyi7tuytut879t68,2
                 */
                //取出当前天
                int day = calendar.get(7);
                //取出来的为第一行 单独合格率
                PropertiesConfiguration configuration = this.propertiesConfigurationService.findConfig(Integer.valueOf((day == 7) || (day == 1) ? 1 : 0), Integer.valueOf(1));
                //用户指标表中没有数据？应该是拿对应指标中的用户，再把电话号码拿出来
                // List<User> users = this.userDao.findByIndicator(Integer.valueOf(1));
                Set<String> phoneList = new HashSet();
                // for (User user : users) {
                //     phoneList.add(user.getTelephone());
                // }
                //获得南岸的合格率，并打印
                Float val = (Float)this.stationMap.get("南岸");
                logger.info(new StringBuilder().append("南岸 val:-------").append(val).toString());
                if (val == null) {
                    //合格率获取失败，2秒后继续获取
                    logger.info("val为空，线程暂停2秒后重新采集");
                    try {
                        Thread.currentThread(); Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                boolean isSatisfactory = false;
                boolean flag = false;
                String conditionStr = null;
                //conditionStr为4；
                switch (configuration.getCondition().intValue()) {
                    case 2:
                        if (val.floatValue() < configuration.getValue().floatValue()){
                            isSatisfactory = true;
                            conditionStr = "<";
                        }
                        break;
                    case 1:
                        if (val.floatValue() <= configuration.getValue().floatValue()){
                            isSatisfactory = true;
                            conditionStr = "<=";
                        }
                        break;
                    case 0:
                        if (val.floatValue() == configuration.getValue()){
                            isSatisfactory = true;
                            conditionStr = "=";
                        }
                        break;
                    case 3:
                        if (val.floatValue() > configuration.getValue().floatValue()){
                            isSatisfactory = true;
                            conditionStr = ">";
                        }

                        break;
                    case 4:
                        if (val.floatValue() >= configuration.getValue().floatValue()){
                            isSatisfactory = true;
                            conditionStr = ">=";
                        }
                        break;

                }

                logger.info(new StringBuilder().append("采集单独合格率：").append(val).toString());
                //条件为4  >=
                logger.info(new StringBuilder().append("条件：").append(conditionStr).toString());
                //预设值为100
                logger.info(new StringBuilder().append("预设值：").append(configuration.getValue()).toString());
                List<ErrorResult> thisErrs = new ArrayList();

                //获得当前是小时，枚举直接转化为11
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                for (Iterator localIterator2 = this.ers.iterator(); localIterator2.hasNext(); ) {
                    ErrorResult er = (ErrorResult)localIterator2.next();
                    for (Iterator localIterator3 = stations.iterator(); localIterator3.hasNext(); ) {
                        String station = (String)localIterator3.next();
                        //厂站名不包含，错误结果list加入
                        if (er.getSbName().indexOf(station) != -1) {
                            thisErrs.add(er);
                            break;
                        }
                    }
                }
                ErrorResult er;
                Iterator localIterator3;
                String station;
                //当前事前是否在比较时间范围内，可能指标是什么时间之后算出来，并且在什么时间之前有效，早7点到13点之间
                boolean inTimeScope = (hourOfDay >= configuration.getWorkTimeStart().intValue()) && (hourOfDay < configuration.getWorkTimeEnd().intValue());
                Integer sendStatus = null;
                if ((isSatisfactory) && (inTimeScope)) {
                    StringBuilder sb = new StringBuilder();
                    for (ErrorResult er1 : thisErrs) {
                        sb.append(";设备名称：")
                                .append(er1.getSbName())
                                .append(",估计值：")
                                .append(er1.getGjValue())
                                .append(",量测值：")
                                .append(er1.getLcValue());
                    }
                    //字符串模板
                    String content = configuration.getShortMsgModel().replaceAll("\\{单独合格率\\}", val.toString());
                    //手机发送人不为空，进行短信发送
                    if (phoneList.size() > 0)
                    {
                        int f;
                        if (previousSendTimeMap.get("南岸") != null) {
                            Calendar previousSendTime = (Calendar)previousSendTimeMap.get("南岸");
                            f = ((Integer)previousSendTimeMap.get("南岸f")).intValue();
                            if (f == 0) {
                                previousSendTime.add(12, configuration.getInvalidTime().intValue());
                                previousSendTimeMap.put("南岸f", Integer.valueOf(f + 1));
                            }
                            if (previousSendTime.before(calendar)) {
                                //在发送时间间隔内
                                int j = 0;
                                for (String phone : phoneList) {
                                    try {
                                        //发送短信
                                        this.smsService.sendSms(phone, content);
                                        j++;
                                    } catch (Exception e) {
                                        logger.error(new StringBuilder().append("短信发送异常:").append(e.getMessage()).toString());
                                        logger.info(new StringBuilder().append("短信内容:").append(content).toString());
                                        e.printStackTrace();
                                    }
                                }
                                if (j == phoneList.size()){
                                    flag = true;
                                }
                                if (flag){
                                    sendStatus = Integer.valueOf(1);
                                } else{
                                    sendStatus = Integer.valueOf(3);
                                }
                                previousSendTimeMap.put("南岸f", Integer.valueOf(0));
                            } else {
                                //不在间隔发送时间 INVALID_TIME
                                sendStatus = Integer.valueOf(4);
                            }
                        } else {
                            int j = 0;
                            for (String phone : phoneList) {
                                try {
                                    this.smsService.sendSms(phone, content);
                                    j++;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (j == phoneList.size()){
                                flag = true;
                            }
                            if (flag){
                                //短信发送发送成功
                                sendStatus = Integer.valueOf(1);
                            } else{
                                //短信发送异常
                                sendStatus = Integer.valueOf(3);
                            }

                        }
                    } else {
                        //短信发送失败，没有接收号码
                        sendStatus = Integer.valueOf(2);
                    }
                    if (sendStatus.intValue() == 1) {
                        logger.info(new StringBuilder().append("单独合格率，短信发送成功，接收人：")
                                .append(CommonUtils.list2string(phoneList)).append("，短信内容：")
                                .append(content).toString());
                        for (String phone : phoneList) {
                            SmSendLog shortMsg = new SmSendLog(phone, content, Integer.valueOf(1), calendar.getTime());
                            try {
                                this.smSendLogService.add(shortMsg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        previousSendTimeMap.put("南岸", Calendar.getInstance());
                    } else {
                        switch (sendStatus.intValue()) {
                            case 2:
                                logger.info("短信发送失败，没有接收号码");
                                break;
                            case 3:
                                logger.error("短信发送异常");
                                break;
                            case 4:
                                logger.info("不在间隔发送时间 INVALID_TIME 外");
                        }
                    }

                }
                else if (!inTimeScope) {
                    logger.info("短信发送失败，不在工作时间范围内");
                } else if (!isSatisfactory) {
                    logger.info("条件不满足，不发送");
                }

                //存库，没明白错误的和正常的
                DataInfo dataInfo = new DataInfo(val, Integer.valueOf(isSatisfactory ? 1 : 0), new Timestamp((new Date()).getTime()));
                if ((isSatisfactory) && (thisErrs.size() > 0)){
                    saveDataInfo(dataInfo, thisErrs);
                } else {
                    saveDataInfo(dataInfo, null);
                }
                logger.info("=================================END=================================");
                sleepThread(Thread.currentThread(), Integer.valueOf(minute));
            } else {
                try {
                    Thread.currentThread(); Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Set<String> setPhoneList(Set<String> phoneList, String listStr)
    {
        String[] list = listStr.split(",");
        phoneList.addAll(Arrays.asList(list));
        Set set = new HashSet(phoneList);
        return set;
    }

    private void saveDataInfo(DataInfo dataInfo, List<ErrorResult> errors)
    {
        try
        {
            List errors1 = new ArrayList();
            if ((errors != null) && (errors.size() > 0)) {
                for (ErrorResult e : errors) {
                    DataInfoError dataInfoError = new DataInfoError(e.getSbName(), e.getSbType(), Float.valueOf(e.getLcValue()), Float.valueOf(e.getGjValue()), null);
                    dataInfoError.setCreateTime(new Date());
                    errors1.add(dataInfoError);
                }
            }
            this.dataInfoServiceAdapter.save(dataInfo, errors1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(new StringBuilder().append("保存采集数据异常").append(e.getMessage()).toString());
        }
    }
}