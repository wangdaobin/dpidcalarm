package com.services.dpidcalarm.collect.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.datainfo.bean.DataInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/12/24 0024 18:42:12
 */
@Repository
@Mapper
public abstract interface IndicatorDataDao{

    /**
     * 存储指标数据
     * @param idcID  指标id
     * @param idcValue  指标值
     * @param collectTime  采集时间
     */
    void insertIndicatorHisData(@Param("idcID") int idcID,@Param("idcValue") float idcValue, @Param("collectTime") Date collectTime);


    /**
     * 更新实时指标
     * @param id 指标id
     * @param idcValue 最近采集值
     * @param collectTime  最近采集时间
     */
    void updateIndicatorRtData(@Param("id") int id,@Param("lastValue") float idcValue, @Param("lastTime") Date collectTime);
}