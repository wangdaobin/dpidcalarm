package com.services.dpidcalarm.sysManager.controller;

import com.services.dpidcalarm.sysManager.bean.MessageSetting;
import com.services.dpidcalarm.sysManager.bean.SendUsers;
import com.services.dpidcalarm.sysManager.service.MessageLogSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 短信告警设置
 * @Author wangdaobin
 * @Date 2020/6/15 0015 下午 16:24
 */
@Controller
@RequestMapping("messageLogSetting")
public class MessageLogSettingController {

    @Autowired
    private MessageLogSettingService messageLogSettingService;

    @RequestMapping("saveSendUsers")
    @ResponseBody
    public int saveSendUsers(@RequestBody SendUsers sendUsers){
        int result = this.messageLogSettingService.saveSendUsers(sendUsers);
        return result;
    }
    @RequestMapping("saveMessageSetting")
    @ResponseBody
    public int saveMessageSetting(@RequestBody MessageSetting messageSetting){
        return this.messageLogSettingService.saveMessageSetting(messageSetting);
    }

    @RequestMapping("queryMessageSetting")
    @ResponseBody
    public Map<String,Object> queryMessageSettingInfo(String indicatorType){
        List<String> users = this.messageLogSettingService.queryUsers(indicatorType);
        MessageSetting messageSetting = this.messageLogSettingService.queryMessageSetting(indicatorType);

        Map<String,Object> map = new HashMap<>();
        map.put("users",users);
        map.put("messageSetting",messageSetting);

        return map;
    }

}
