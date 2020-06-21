package com.services.dpidcalarm.sysManager.dao;

import com.services.dpidcalarm.sysManager.bean.MessageSetting;
import com.services.dpidcalarm.sysManager.bean.SendUsers;
import com.services.dpidcalarm.sysManager.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/6/15 0015 下午 19:13
 */
@Mapper
@Repository
public interface MessageLogSettingMapper {

    int deleteOldSendUsers(String indicatorType);

    int insertSendUsers(SendUsers sendUsers);

    int saveMessageSetting(MessageSetting messageSetting);

    List<String> queryUsers(String indicatorType);

    MessageSetting queryMessageSetting(String indicatorType);
}
