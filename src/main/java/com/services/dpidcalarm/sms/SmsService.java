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

    public synchronized void sendSms(String telephone, String content) {
        try {
            Client client = new Client(new URL("http://10.55.22.180:8080/sdc_Message/services/SMSplatform?wsdl"));
            client.addOutHandler(new ClientAuthenticationHandler("oms2", "nari-1234"));
            String str = "[{'mc':'vsz','dxfshm':'" + telephone + "','dxfsnr':'" + content + "','userid':'','ssdw':'vsz','dxfssj':''}]";
            client.invoke("oms2sdc_save_dxfs", new Object[] { str });
            client.close();
            Thread.currentThread();
            Thread.sleep(5000L);
        }catch (Exception e){
            logger.error("发送短信失败：" + e.getMessage());
            e.printStackTrace();
        }catch (Throwable e){
            logger.error("发送短信失败：" + e.getMessage());
            e.printStackTrace();
        }

    }
}