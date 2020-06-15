package com.services.dpidcalarm.dataquery.dao;

import com.services.dpidcalarm.dataquery.bean.MessageLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/6/13 0013 上午 9:38
 */
@Mapper
@Repository
public interface MessageLogDao {
    public List<MessageLog> queryMessageLogs(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                                             @Param("type") String type, @Param("content") String content);

    public List<MessageLog> queryMessageLogsNew(int size);
}
