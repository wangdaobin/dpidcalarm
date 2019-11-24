package com.services.dpidcalarm.collect.job;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/4 0004 10:28:08
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class ThreadCommon
{
    private static Logger logger = LoggerFactory.getLogger(ThreadCommon.class);
    public static final int SEND_SUCCESS = 1;
    public static final int SEND_NO_PHONE = 2;
    public static final int SEND_EXCEPTION = 3;
    public static final int SEND_NOT_IN_INVALID_TIME = 4;

    public void sleepThread(Thread thread, Integer minute)
    {
        Calendar now = Calendar.getInstance();
        if (now.get(12) == minute.intValue()) {
            int ms = (60 - now.get(13)) * 1000;
            logger.info("提前完成处理，线程sleep:" + ms);
            try {
                Thread.currentThread(); Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}