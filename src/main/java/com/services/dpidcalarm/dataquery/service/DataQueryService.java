package com.services.dpidcalarm.dataquery.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.dataquery.bean.IndicatorHisData;
import com.services.dpidcalarm.dataquery.dao.DataQureyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2019/12/24 0024 下午 21:49
 */
@Service
public class DataQueryService {
    @Autowired
    private DataQureyMapper dataQureyMapper;

    public String queryRealTimeData(int type){
        return this.dataQureyMapper.queryRealTimeData(type);
    }

    public PageInfo<IndicatorHisData> queryHisData(int type, String begin, String end, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<IndicatorHisData> list = this.dataQureyMapper.queryHisData(type,begin,end);
        return new PageInfo<IndicatorHisData>(list);
    }

    public PageInfo<HashMap<String,String>> queryDetail(String tableName,String indicatorDataid,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<HashMap<String,String>> list = this.dataQureyMapper.queryDetail(tableName,indicatorDataid);
        return new PageInfo<HashMap<String,String>>(list);
    }
}
