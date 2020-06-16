package com.services.dpidcalarm.sysManager.controller;

import com.services.dpidcalarm.sysManager.bean.MessageSetting;
import com.services.dpidcalarm.sysManager.bean.SendUsers;
import com.services.dpidcalarm.sysManager.bean.UserBean;
import com.services.dpidcalarm.sysManager.service.MessageLogSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public int saveMessageSetting(MessageSetting messageSetting){
        return 0;
    }

}
