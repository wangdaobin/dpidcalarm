package com.services.dpidcalarm.sysManager.dao;

import com.services.dpidcalarm.sysManager.bean.Indicator;
import com.services.dpidcalarm.sysManager.bean.IndicatorAttr;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/1/12 0014 上午 10:40
 */
@Mapper
@Repository
public interface IndicatorMgrMapper {
    /**
     * 查询所有指标类型
     * @return List<Indicator>
     */
    List<Indicator> queryAllIndicator();

    /**
     * 查询指标采集相关属性
     * @return List<IndicatorAttr>
     */
    List<IndicatorAttr> queryIndicatorAttr();

    /**
     * 添加指标信息
     * @param indicator
     * @return
     */
    int addIndicator(Indicator indicator);

    /**
     * 修改指标信息
     * @param indicator
     * @return
     */
    int modifyIndicator(Indicator indicator);
}
