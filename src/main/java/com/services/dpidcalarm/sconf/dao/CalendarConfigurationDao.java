package com.services.dpidcalarm.sconf.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.sconf.bean.CalendarConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public abstract interface CalendarConfigurationDao extends MyMapper<CalendarConfiguration>
{
  public abstract void deleteByYear(Integer paramInteger)
          throws Exception;
}