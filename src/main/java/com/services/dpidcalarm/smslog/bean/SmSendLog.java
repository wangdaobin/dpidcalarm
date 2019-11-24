package com.services.dpidcalarm.smslog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.dpidcalarm.base.bean.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name="short_msg_send_log")
@Entity
public class SmSendLog extends IdEntity
{

    @Column(name="receiver")
    private String receiver;

    @Column(name="content")
    private String content;

    @Column(name="indicator_id")
    private Integer indicatorId;

    @Column(name="create_time")
    private Date createTime;

    @Transient
    private Date startTime;

    @Transient
    private Date endTime;

    @Transient
    private String startTimeStr;

    @Transient
    private String endTimeStr;

    @Transient
    private String receiverRealName;

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

    public String getReceiverRealName() {
        return this.receiverRealName;
    }

    public void setReceiverRealName(String receiverRealName) {
        this.receiverRealName = receiverRealName;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIndicatorId() {
        return this.indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }
    public SmSendLog() {
    }

    public SmSendLog(String receiver, String content, Integer indicatorId, Date createTime) {
        this.receiver = receiver;
        this.content = content;
        this.indicatorId = indicatorId;
        this.createTime = createTime;
    }
}