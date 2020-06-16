package com.services.dpidcalarm.sysManager.service;

import com.services.dpidcalarm.sysManager.bean.SendUsers;
import com.services.dpidcalarm.sysManager.dao.MessageLogSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
