package com.services.dpidcalarm.dataquery.controller;

import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.dataquery.bean.MessageLog;
import com.services.dpidcalarm.dataquery.service.MessageLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 短信记录
 * @Author wangdaobin
 * @Date 2020/6/13 0013 上午 9:31
 */
@Controller
@RequestMapping("messageLog")
public class MessageLogController {

    @Autowired
    private MessageLogService messageLogService;

    @RequestMapping("queryMsgLog")
    @ResponseBody
    public PageInfo<MessageLog> queryMessageLogs(String beginTime,String endTime,String type,String content,
                                                 int pageNum, int pageSize){
        try{
            return this.messageLogService.queryMessageLogs(beginTime,endTime,type,content,pageNum,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("queryMsgLogNew")
    @ResponseBody
    public List<MessageLog> queryMessageLogsNew(int size){
        try{
            return this.messageLogService.queryMessageLogsNew(size);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
