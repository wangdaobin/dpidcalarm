package com.services.dpidcalarm.sconf.bean;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/4 0004 11:19:44
 */
import com.services.dpidcalarm.base.bean.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="properties_configuration")
@Entity
public class PropertiesConfiguration extends IdEntity
{
    public static final int EQUALS = 0;
    public static final int LESS_EQUALS = 1;
    public static final int LESS = 2;
    public static final int GREATER = 3;
    public static final int GREATER_EQUALS = 4;

    @Column(name="c_condition")
    private Integer condition;

    @Column(name="c_value")
    private Float value;

    @Column(name="invalid_time")
    private Integer invalidTime;

    @Column(name="cycle")
    private Integer cycle;

    @Column(name="work_time_start")
    private Integer workTimeStart;

    @Column(name="work_time_end")
    private Integer workTimeEnd;

    @Column(name="is_weekend")
    private Integer isWeekend;

    @Column(name="short_msg_model")
    private String shortMsgModel;
    private Integer indicatorId;

    public Integer getCondition()
    {
        return this.condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Float getValue() {
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Integer invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Integer getCycle() {
        return this.cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getWorkTimeStart() {
        return this.workTimeStart;
    }

    public void setWorkTimeStart(Integer workTimeStart) {
        this.workTimeStart = workTimeStart;
    }

    public Integer getWorkTimeEnd() {
        return this.workTimeEnd;
    }

    public void setWorkTimeEnd(Integer workTimeEnd) {
        this.workTimeEnd = workTimeEnd;
    }

    public Integer getIsWeekend() {
        return this.isWeekend;
    }

    public void setIsWeekend(Integer isWeekend) {
        this.isWeekend = isWeekend;
    }

    public String getShortMsgModel() {
        return this.shortMsgModel;
    }

    public void setShortMsgModel(String shortMsgModel) {
        this.shortMsgModel = shortMsgModel;
    }

    public Integer getIndicatorId() {
        return this.indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }
}