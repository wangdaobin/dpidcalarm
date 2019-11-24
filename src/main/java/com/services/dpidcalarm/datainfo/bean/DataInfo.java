package com.services.dpidcalarm.datainfo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.dpidcalarm.base.bean.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="data_info")
public class DataInfo extends IdEntity
{

    @Column(name="a_value")
    private Float aValue;

    @Column(name="is_satisfactory")
    private Integer isSatisfactory;

    @Column(name="create_time")
    private Date createTime;

    @Transient
    private List<DataInfoError> errors;

    @Transient
    private Integer errorCount;

    @Transient
    private String startTimeStr;

    @Transient
    private String endTimeStr;

    public String getStartTimeStr()
    {
        return this.startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return this.endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Float getaValue() {
        return this.aValue;
    }

    public void setaValue(Float aValue) {
        this.aValue = aValue;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<DataInfoError> getErrors()
    {
        return this.errors;
    }

    public void setErrors(List<DataInfoError> errors) {
        this.errors = errors;
    }

    public Integer getIsSatisfactory() {
        return this.isSatisfactory;
    }

    public void setIsSatisfactory(Integer isSatisfactory) {
        this.isSatisfactory = isSatisfactory;
    }
    public DataInfo() {
    }

    public DataInfo(Float aValue, Integer isSatisfactory, Date createTime) {
        this.aValue = aValue;
        this.isSatisfactory = isSatisfactory;
        this.createTime = createTime;
    }

    public Integer getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}