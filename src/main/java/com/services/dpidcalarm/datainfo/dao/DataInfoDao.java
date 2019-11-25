package com.services.dpidcalarm.datainfo.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.datainfo.bean.DataInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public abstract interface DataInfoDao extends MyMapper<DataInfo>
{
  public abstract List<DataInfo> findUnQualified(@Param("entity") DataInfo paramDataInfo);

  public abstract List<Map<String, Object>> monthAvgInfo(Integer paramInteger);

  public abstract List<Map<String, Object>> dayAvgInfo(@Param("year") Integer paramInteger1, @Param("month") Integer paramInteger2);

  public abstract List<Map<String, Object>> minuteInfo(@Param("year") Integer paramInteger1, @Param("month") Integer paramInteger2, @Param("day") Integer paramInteger3);

  public abstract List<DataInfo> last3DataInfo();

  public abstract List<Map<String, Object>> dataInfoErrorSum(@Param("startTimeStr") String paramString1, @Param("endTimeStr") String paramString2);
}
