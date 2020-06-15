package com.services.dpidcalarm.sysManager.controller;

import com.services.dpidcalarm.sysManager.bean.Indicator;
import com.services.dpidcalarm.sysManager.bean.IndicatorAttr;
import com.services.dpidcalarm.sysManager.service.IndicatorMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 指标管理控制层
 * @Author Admin
 * @Date 2020/1/12 0014 上午 10:33
 */
@Controller
@RequestMapping("indicatorMgr")
public class IndicatorMgrController {

    @Autowired
    private IndicatorMgrService indicatorMgrService;

    @RequestMapping("queryIndicator")
    @ResponseBody
    public List<Indicator> queryIndicator(){
        return indicatorMgrService.queryIndicator();
    }

    @RequestMapping("queryIndicatorAttr")
    @ResponseBody
    public List<IndicatorAttr> queryIndicatorAttr(){
        return indicatorMgrService.queryIndicatorAttr();
    }

    @RequestMapping("addIndicator")
    @ResponseBody
    public int addIndicator(@RequestBody Indicator indicator){
        return indicatorMgrService.addIndicator(indicator);
    }
    @RequestMapping("modifyIndicator")
    @ResponseBody
    public int modifyIndicator(@RequestBody Indicator indicator){
        return indicatorMgrService.modifyIndicator(indicator);
    }
    @RequestMapping("deleteIndicator")
    @ResponseBody
    public int deleteIndicator(String id){
        return indicatorMgrService.deleteIndicator(id);
    }
}
