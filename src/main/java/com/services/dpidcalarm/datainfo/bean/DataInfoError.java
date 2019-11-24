package com.services.dpidcalarm.datainfo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.dpidcalarm.base.bean.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table(name="data_info_error")
@Entity
public class DataInfoError extends IdEntity
{

    @Column(name="device_name")
    private String deviceName;

    @Column(name="device_type")
    private String deviceType;

    @Column(name="test_value")
    private Float testValue;

    @Column(name="estimate_value")
    private Float estimateValue;

    @Column(name="data_id")
    private Integer dataId;
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Float getTestValue() {
        return this.testValue;
    }

    public void setTestValue(Float testValue) {
        this.testValue = testValue;
    }

    public Float getEstimateValue() {
        return this.estimateValue;
    }

    public void setEstimateValue(Float estimateValue) {
        this.estimateValue = estimateValue;
    }

    public Integer getDataId() {
        return this.dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public DataInfoError() {
    }

    public DataInfoError(String deviceName, String deviceType, Float testValue, Float estimateValue, Integer dataId) {
        this.deviceName = deviceName;
        this.testValue = testValue;
        this.estimateValue = estimateValue;
        this.dataId = dataId;
        this.deviceType = deviceType;
    }
}