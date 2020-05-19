package com.services.dpidcalarm.sysManager.service;

import com.services.dpidcalarm.sysManager.bean.Indicator;
import com.services.dpidcalarm.sysManager.bean.IndicatorAttr;
import com.services.dpidcalarm.sysManager.dao.IndicatorMgrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 指标管理业务层
 * @Author wangdaobin
 * @Date 2020/1/12 0014 上午 10:38
 */
@Service
public class IndicatorMgrService {

    @Autowired
    private IndicatorMgrMapper indicatorMgrMapper;

    public List<Indicator> queryIndicator(){
        return indicatorMgrMapper.queryAllIndicator();
    }

    public List<IndicatorAttr> queryIndicatorAttr() {
        return indicatorMgrMapper.queryIndicatorAttr();
    }

    public int addIndicator(Indicator indicator) {
        return indicatorMgrMapper.addIndicator(indicator);
    }

    public int modifyIndicator(Indicator indicator) {
        return indicatorMgrMapper.modifyIndicator(indicator);
    }
}
