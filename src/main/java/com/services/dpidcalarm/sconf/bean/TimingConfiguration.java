package com.services.dpidcalarm.sconf.bean;

import com.services.dpidcalarm.base.bean.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="timing_configuration")
@Entity
public class TimingConfiguration extends IdEntity
{
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;
    private PropertiesConfiguration pc;

    @Column(name="start_hour")
    public Integer getStartHour()
    {
        return this.startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }
    @Column(name="start_minute")
    public Integer getStartMinute() {
        return this.startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }
    @Column(name="end_hour")
    public Integer getEndHour() {
        return this.endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }
    @Column(name="end_minute")
    public Integer getEndMinute() {
        return this.endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public PropertiesConfiguration getPc() {
        return this.pc;
    }

    public void setPc(PropertiesConfiguration pc) {
        this.pc = pc;
    }
}