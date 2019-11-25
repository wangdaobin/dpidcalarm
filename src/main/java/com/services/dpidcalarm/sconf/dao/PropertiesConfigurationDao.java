package com.services.dpidcalarm.sconf.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.sconf.bean.PropertiesConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public abstract interface PropertiesConfigurationDao extends MyMapper<PropertiesConfiguration>
{
  public abstract PropertiesConfiguration findConfig(@Param("isWeekend") Integer paramInteger1, @Param("indicatorId") Integer paramInteger2);
}

