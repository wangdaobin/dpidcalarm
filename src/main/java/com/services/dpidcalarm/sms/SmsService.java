package com.services.dpidcalarm.sms;

import org.codehaus.xfire.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * @Description：短信发送模块，多线程短信发送互斥synchronized
 * @Author：zhangtao
 * @Date：2019/11/2 0002 15:40:51
 */
@Component
public class SmsService
{
    private static Logger logger = LoggerFactory.getLogger(SmsService.class);
    /**
     * 默认的平台地址，以及账号密码
     */
    private static String smsAddress = "http://10.55.22.180:8080/sdc_Message/services/SMSplatform?wsdl";
    private static String userName = "oms2";
    private static String password = "nari-1234";

    public static void  setSMSInfo(String smsAddress, String userName, String password){
        SmsService.smsAddress = smsAddress;
        SmsService.userName = userName;
        SmsService.password = password;
    }

    public static synchronized boolean sendSms(String telephone, String content) {
        try {
            //南岸平台
            // Client client = new Client(new URL("http://10.55.22.180:8080/sdc_Message/services/SMSplatform?wsdl"));
            // client.addOutHandler(new ClientAuthenticationHandler("oms2", "nari-1234"));
            //市调平台
            //Client client = new Client(new URL("http://10.55.6.35:8080/sdc_Message/services/SMSplatform?wsdl"));
            //client.addOutHandler(new ClientAuthenticationHandler("sy_0001", "sy_0001"));

            Client client = new Client(new URL(SmsService.smsAddress));
            client.addOutHandler(new ClientAuthenticationHandler(SmsService.userName, SmsService.password));
            String str = "[{'mc':'vsz','dxfshm':'" + telephone + "','dxfsnr':'" + content + "','userid':'','ssdw':'vsz','dxfssj':''}]";
            client.invoke("oms2sdc_save_dxfs", new Object[] { str });
            client.close();
            Thread.currentThread();
            logger.info("发送短信成功：" + str);
            Thread.sleep(500L);
            return true;

        }catch (Exception e){
            logger.error("发送短信失败：",e);
            e.printStackTrace();
            return false;
        }catch (Throwable e){
            logger.error("发送短信失败：",e);
            e.printStackTrace();
            return false;
        }

    }
}