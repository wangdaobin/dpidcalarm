package com.services.dpidcalarm.dataquery.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.dataquery.bean.MessageLog;
import com.services.dpidcalarm.dataquery.dao.MessageLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/6/13 0013 上午 9:38
 */
@Service
public class MessageLogService {

    @Autowired
    private MessageLogDao messageLogDao;

    public PageInfo<MessageLog> queryMessageLogs(String beginTime, String endTime, String type, String content,
                                                 int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MessageLog> logs = messageLogDao.queryMessageLogs( beginTime,endTime,type,content);
        return new PageInfo<MessageLog>(logs);
    }

    public List<MessageLog> queryMessageLogsNew(int size){
        return this.messageLogDao.queryMessageLogsNew(size);
    }
}
