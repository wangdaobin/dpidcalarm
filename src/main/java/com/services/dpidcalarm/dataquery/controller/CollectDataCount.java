package com.services.dpidcalarm.dataquery.controller;

import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.dataquery.bean.IndicatorHisData;
import com.services.dpidcalarm.dataquery.service.DataQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @Description 数据查询统计
 * @Author Admin
 * @Date 2019/11/28 0028 下午 18:03
 */
@Controller
public class CollectDataCount {
    @Autowired
    private DataQueryService dataQueryService;

    @RequestMapping("/queryDataCountSingle")
    @ResponseBody
    public String queryDataCountSingle(String begin,String end){
        try{
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * 实时数据查询
     * @param type
     * @return
     */
    @RequestMapping("/queryRealTimeData")
    @ResponseBody
    public String queryRealTimeData(int type){
        try {
            return dataQueryService.queryRealTimeData(type);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * 查询历史数据
     * @param start
     * @param end
     * @param type
     * @return
     */
    @RequestMapping("queryIndicatorHisData")
    @ResponseBody
    public PageInfo<IndicatorHisData> queryIndicatorHisData(String start, String end, int type, int pageNum, int pageSize){
        try{
            return this.dataQueryService.queryHisData(type,start,end,pageNum,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("queryIndicatorDetail")
    @ResponseBody
    public PageInfo<HashMap<String,String>> queryIndicatorDetail(int type,String idpId,int pageNum,int pageSize){
        try{
            String tableName = "";
            if(1001 == type){
                tableName = "indicator_details_bdzycsx";
            }else if(1002 == type){
                tableName = "indicator_details_kgbw";
            }else if(1003 == type){
                tableName = "indicator_details_ycyxztbyz";
            }else if(1004 == type){
                tableName = "indicator_detailsj_jfdlwc_dydj";
            }else if(1005 == type){
                tableName = "indicator_details_sgfz";
            }else if(1006 == type){
                tableName = "indicator_details_ztgj_new";
            }
            return this.dataQueryService.queryDetail(tableName,idpId,pageNum,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
