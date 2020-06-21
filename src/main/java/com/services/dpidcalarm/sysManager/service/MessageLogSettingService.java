package com.services.dpidcalarm.sysManager.service;

import com.services.dpidcalarm.sysManager.bean.MessageSetting;
import com.services.dpidcalarm.sysManager.bean.SendUsers;
import com.services.dpidcalarm.sysManager.bean.UserBean;
import com.services.dpidcalarm.sysManager.dao.MessageLogSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/6/15 0015 下午 19:12
 */
@Service
@Transactional
public class MessageLogSettingService {

    @Autowired
    private MessageLogSettingMapper messageLogSettingMapper;

    public int saveSendUsers(SendUsers sendUsers){
        int delNum = this.messageLogSettingMapper.deleteOldSendUsers(sendUsers.getIndicatorType());
        int insertNum = this.messageLogSettingMapper.insertSendUsers(sendUsers);
        return insertNum;

    }

    public int saveMessageSetting(MessageSetting messageSetting){
        return this.messageLogSettingMapper.saveMessageSetting(messageSetting);
    }

    public List<String> queryUsers(String indicatorType) {
        return this.messageLogSettingMapper.queryUsers(indicatorType);
    }

    public MessageSetting queryMessageSetting(String indicatorType) {
        return this.messageLogSettingMapper.queryMessageSetting(indicatorType);
    }
}
