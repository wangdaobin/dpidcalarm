// package com.services.dpidcalarm.collect.job;
// import com.services.dpidcalarm.collect.bean.StationMap;
// import com.services.dpidcalarm.sconf.bean.PropertiesConfiguration;
// import com.services.dpidcalarm.sconf.service.PropertiesConfigurationService;
// import com.services.dpidcalarm.sms.SmsService;
// import com.services.dpidcalarm.smslog.dao.SmSendLogDao;
// import org.springframework.stereotype.*;
// import org.springframework.beans.factory.annotation.*;
// import java.io.*;
// import org.apache.http.impl.client.*;
// import java.util.*;
// import org.slf4j.*;
// /**
//  * @Description：
//  * @Author：zhangtao
//  * @Date：2019/11/24 0024 23:59:29
//  */
// @Component
// public class BusinessTransportAvailabilityServiceCore extends ThreadCommon implements Runnable
// {
//     private static Logger logger;
//     @Autowired
//     private PropertiesConfigurationService propertiesConfigurationService;
//     @Autowired
//     private SmSendLogDao smSendLogDao;
//     // @Autowired
//     // private UserDao userDao;
//     @Autowired
//     private SmsService smsService;
//     @Autowired
//     private BusinessTransportAvailabilityDao businessTransportAvailabilityDao;
//     private static final int PREVIOUS_DAY = 0;
//     private static final int CURRENT_DAY = 1;
//
//     public void run() {
//         final boolean action = true;
//         final CollectDataBTA collectDataBTA = new CollectDataBTA();
//         final Map<String, Integer> unSendMap = new HashMap<String, Integer>();
//         final Map<String, Integer> sendedTotalCount = new HashMap<String, Integer>();
//         final Map<String, Integer> remaindMap = new HashMap<String, Integer>();
//         final Map<String, Integer> map = new HashMap<String, Integer>();
//         final Map<String, Integer> map2 = new HashMap<String, Integer>();
//         final Map<String, Integer> map3 = new HashMap<String, Integer>();
//         StationMap.map.forEach((k, v) -> {
//             map.put(k, 0);
//             map2.put(k, 0);
//             map3.put(k, 0);
//             return;
//         });
//         while (action) {
//             final Calendar calendar = Calendar.getInstance();
//             final int minute = calendar.get(12);
//             if (minute % 5 == 0) {
//                 //final List<User> users = (List<User>)this.userDao.findByIndicator(Integer.valueOf(2));
//                 if (calendar.get(11) == 0 && calendar.get(12) == 0) {
//                     final Map<String, Integer> map4 = new HashMap<String, Integer>();
//                     final Map<String, Integer> map5 = new HashMap<String, Integer>();
//                     final Map<K, Integer> map6 = new HashMap<String, Integer>();
//                     StationMap.map.forEach((k, v) -> {
//                         map4.put(k, 0);
//                         map5.put(k, map6.get(k));
//                         map6.put((K)k, 0);
//                         return;
//                     });
//                 }
//                 if (calendar.get(11) == 8 && calendar.get(12) == 0) {
//                     final StringBuilder sb = new StringBuilder();
//                     final StringBuilder sb3 = new StringBuilder();
//                     remaindMap.forEach((k, v) -> {
//                         if (v != 0) {
//                             if (sb3.length() != 0) {
//                                 sb3.append(";");
//                             }
//                             sb3.append(k).append(":").append(v);
//                         }
//                         return;
//                     });
//                     final Integer n;
//                     StationMap.map.forEach((k, v) -> n = remaindMap.put(k, 0));
//                     try {
//                         if (sb.length() > 0) {
//                             //this.sendSmsHandle(users, sb.toString(), 0);
//                         }
//                     }
//                     catch (Exception e) {
//                         BusinessTransportAvailabilityServiceCore.logger.error("\u77ed\u4fe1\u53d1\u9001\u5f02\u5e38:" + e.getMessage());
//                         BusinessTransportAvailabilityServiceCore.logger.info("\u77ed\u4fe1\u5185\u5bb9:" + (Object)sb);
//                     }
//                 }
//                 final int day = calendar.get(7);
//                 final PropertiesConfiguration configuration = this.propertiesConfigurationService.findConfig(Integer.valueOf((int)((day == 7 || day == 1) ? 1 : 0)), Integer.valueOf(2));
//                 CloseableHttpClient client = null;
//                 final String smContent = null;
//                 try {
//                     client = collectDataBTA.login();
//                     final String html = collectDataBTA.getErrorCount(client);
//                     if (html != null && html.length() > 0) {
//                         BusinessTransportAvailabilityServiceCore.logger.info("BTA HTML RETURN SUCCESS.");
//                     }
//                     else {
//                         BusinessTransportAvailabilityServiceCore.logger.info("BTA HTML RETURN IS NULL");
//                     }
//                     final Map<String, Integer> countMap = (Map<String, Integer>)ErrorCountHtmlUtils.getCountMap(html);
//                     final StringBuilder sb2 = new StringBuilder();
//                     final Set<String> keys = countMap.keySet();
//                     BusinessTransportAvailabilityServiceCore.logger.info("BTA DATA:" + countMap);
//                     int alarmCount = 0;
//                     final int hourOfDay = calendar.get(11);
//                     final boolean inTimeScope = hourOfDay >= configuration.getWorkTimeStart() && hourOfDay < configuration.getWorkTimeEnd();
//                     for (final String key : keys) {
//                         boolean isSatisfactory = false;
//                         final Integer val = countMap.get(key);
//                         if (val != 0) {
//                             final Integer sendedCount = sendedTotalCount.get(key);
//                             if (val > sendedCount) {
//                                 final int sub = val - sendedCount;
//                                 if (sub >= configuration.getValue().intValue() && inTimeScope) {
//                                     isSatisfactory = true;
//                                 }
//                                 else {
//                                     unSendMap.put(key, sub);
//                                 }
//                             }
//                         }
//                         if (isSatisfactory) {
//                             if (++alarmCount != 1) {
//                                 sb2.append(";");
//                             }
//                             sb2.append(key).append(":").append(val);
//                             sendedTotalCount.put(key, val);
//                             unSendMap.put(key, 0);
//                         }
//                         final BusinessTransportAvailability bta = new BusinessTransportAvailability();
//                         bta.setIsSatisfactory(Integer.valueOf((int)(isSatisfactory ? 1 : 0)));
//                         bta.setErrorCount(val);
//                         bta.setCreateTime(calendar.getTime());
//                         bta.setStation(key);
//                         this.saveBTA(key, bta);
//                     }
//                     if (sb2.length() > 0) {
//                         //this.sendSmsHandle(users, sb2.toString(), 1);
//                     }
//                 }
//                 catch (IOException e2) {
//                     BusinessTransportAvailabilityServiceCore.logger.error("\u4e1a\u52a1\u4f20\u8f93\u53ef\u7528\u7387 - \u767b\u5f55\u5f02\u5e38\uff1a" + e2.getMessage());
//                 }
//                 catch (Exception e3) {
//                     BusinessTransportAvailabilityServiceCore.logger.error("\u4e1a\u52a1\u4f20\u8f93\u53ef\u7528\u7387 - \u77ed\u4fe1\u53d1\u9001\u5f02\u5e38:" + e3.getMessage());
//                     BusinessTransportAvailabilityServiceCore.logger.error("\u77ed\u4fe1\u5185\u5bb9\uff1a" + smContent);
//                 }
//                 finally {
//                     if (client != null) {
//                         try {
//                             client.close();
//                         }
//                         catch (IOException e4) {
//                             BusinessTransportAvailabilityServiceCore.logger.error("dataBTA client close exception" + e4.getMessage());
//                             client = null;
//                         }
//                         finally {
//                             client = null;
//                         }
//                     }
//                 }
//                 this.sleepThread(Thread.currentThread(), Integer.valueOf(minute));
//             }
//             else {
//                 try {
//                     Thread.currentThread();
//                     Thread.sleep(1000L);
//                 }
//                 catch (InterruptedException e5) {
//                     e5.printStackTrace();
//                 }
//             }
//         }
//     }
//
//     private void sendSmsHandle(final List<User> users, final String content, final int dayType) throws Exception {
//         String smContent = null;
//         final String[] contentArray = content.split(";");
//         if (contentArray.length > 2) {
//             smContent = "\u62a5\u8b66\u5382\u7ad9\u6570:" + contentArray.length + ",";
//             int stationCount = 0;
//             for (final String c : contentArray) {
//                 final Integer count = Integer.parseInt(c.split(":")[1]);
//                 stationCount += count;
//             }
//             smContent = smContent + "\u9519\u8bef\u603b\u6570:" + stationCount;
//         }
//         String message = (smContent == null) ? content : smContent;
//         if (dayType == 0) {
//             message = "\u6628\u65e5\u672a\u62a5\u8b66\u5185\u5bb9->" + message;
//         }
//         final Date date = new Date();
//         if (users == null || users.size() == 0) {
//             BusinessTransportAvailabilityServiceCore.logger.info("\u77ed\u4fe1\u65e0\u4eba\u63a5\u6536");
//         }
//         else {
//             for (final User user : users) {
//                 this.smsService.sendSms(user.getTelephone(), message);
//                 final SmSendLog smSendLog = new SmSendLog(user.getTelephone(), message, Integer.valueOf(2), date);
//                 this.smSendLogDao.insert((Object)smSendLog);
//                 BusinessTransportAvailabilityServiceCore.logger.info("\u4e1a\u52a1\u4f20\u8f93\u53ef\u7528\u7387\uff0c\u77ed\u4fe1\u53d1\u9001\u6210\u529f\uff0c\u63a5\u6536\u4eba\uff1a" + user.getTelephone() + "\uff0c\u5185\u5bb9\uff1a" + message);
//             }
//         }
//     }
//
//     private void saveBTA(final String station, final BusinessTransportAvailability bta) {
//         try {
//             this.businessTransportAvailabilityDao.add((String)StationMap.map.get(station), bta);
//         }
//         catch (Exception e) {
//             BusinessTransportAvailabilityServiceCore.logger.error("\u4fdd\u5b58\u4e1a\u52a1\u4f20\u8f93\u53ef\u7528\u7387\u5f02\u5e38\uff1a" + e.getMessage());
//         }
//     }
//
//     static {
//         BusinessTransportAvailabilityServiceCore.logger = LoggerFactory.getLogger((Class)BusinessTransportAvailabilityServiceCore.class);
//     }
// }
//
