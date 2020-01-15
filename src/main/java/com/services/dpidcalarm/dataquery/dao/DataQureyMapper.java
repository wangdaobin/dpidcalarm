package com.services.dpidcalarm.dataquery.dao;

import com.services.dpidcalarm.dataquery.bean.IndicatorHisData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2019/12/24 0024 下午 21:49
 */
@Mapper
@Repository
public interface DataQureyMapper {

    /**
     * 根据指标类型名称查询实时值
     * @param type
     * @return
     */
    String queryRealTimeData( int type);

    /**
     * 查询历史数据
     * @param type
     * @return
     */
    List<IndicatorHisData> queryHisData(@Param("type") int type, @Param("begin") String begin, @Param("end") String end);
}
