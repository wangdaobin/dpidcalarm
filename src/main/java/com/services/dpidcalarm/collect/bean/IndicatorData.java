package com.services.dpidcalarm.collect.bean;

import java.util.Date;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2020/4/22 0022 11:20:59
 */
public class IndicatorData {
    //数据库中的记录自增id
    private int id;
    //指标定义的id
    private int idcID;
    //指标值
    private float idcValue;
    //指标采集时间
    private Date collectTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcID() {
        return idcID;
    }

    public void setIdcID(int idcID) {
        this.idcID = idcID;
    }

    public float getIdcValue() {
        return idcValue;
    }

    public void setIdcValue(float idcValue) {
        this.idcValue = idcValue;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
}