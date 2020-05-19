package com.services.dpidcalarm.collect.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.collect.bean.IndicatorData;
import com.services.dpidcalarm.datainfo.bean.DataInfo;
import com.services.dpidcalarm.sysManager.bean.Indicator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/12/24 0024 18:42:12
 */
@Repository
@Mapper
public abstract interface IndicatorDataDao{


    /**
     * 插入指标得分历史数据
     * @param indicatorData 指标对象
     * @return
     */
    Integer insertIndicatorHisData(IndicatorData indicatorData);


    /**
     * 更新实时指标
     * @param id 指标id
     * @param idcValue 最近采集值
     * @param collectTime  最近采集时间
     */
    Integer updateIndicatorRtData(@Param("id") int id,@Param("lastValue") float idcValue, @Param("lastTime") Date collectTime);


    /**
     * 更新实时指标-短信发送次数
     * @param id
     * @param sendMsgCount
     * @return
     */
    Integer updateIndicatorMsgSendCount(@Param("id") int id,@Param("sendMsgCount") int sendMsgCount);

    /**
     * 查询全部指标
     * @return
     */
    public List<Indicator> queryAllIndicator();
    /**
     * 插入指标得分对应的详情
     * @param indicatorDataId
     * @param stationName
     * @param problemCount
     * @param deductPoints
     * @return
     */
    Integer insertBDZYCSXDetailData(@Param("indicatorDataId") int indicatorDataId,
                                    @Param("stationName") String stationName, @Param("problemCount") int problemCount,@Param("deductPoints") float deductPoints);


    /**
     * 插入短信发送记录
     * @param userId
     * @param phone
     * @param content
     * @param status
     * @param sendTime
     * @return
     */
    Integer insertSMLog(@Param("userId") int userId, @Param("phone") String phone,@Param("content") String content,
                        @Param("status") int status, @Param("sendTime") Date sendTime);


}