package com.services.dpidcalarm.sysManager.dao;

import com.services.dpidcalarm.sysManager.bean.SendUsers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/6/15 0015 下午 19:13
 */
@Mapper
@Repository
public interface MessageLogSettingMapper {

    public int deleteOldSendUsers(String indicatorType);

    public int insertSendUsers(SendUsers sendUsers);
}
