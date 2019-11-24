package com.services.dpidcalarm.sconf.bean;

import com.services.dpidcalarm.base.bean.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="calendar_configuration")
@Entity
public class CalendarConfiguration extends IdEntity
{
    private Integer cYear;
    private Integer cMonth;
    private Integer cDay;
    private Integer cStatus;

    @Column(name="c_year")
    public Integer getcYear()
    {
        return this.cYear;
    }

    public void setcYear(Integer cYear) {
        this.cYear = cYear;
    }
    @Column(name="c_month")
    public Integer getcMonth() {
        return this.cMonth;
    }

    public void setcMonth(Integer cMonth) {
        this.cMonth = cMonth;
    }
    @Column(name="c_day")
    public Integer getcDay() {
        return this.cDay;
    }

    public void setcDay(Integer cDay) {
        this.cDay = cDay;
    }
    @Column(name="c_status")
    public Integer getcStatus() {
        return this.cStatus;
    }

    public void setcStatus(Integer cStatus) {
        this.cStatus = cStatus;
    }

    public CalendarConfiguration(Integer cYear, Integer cMonth, Integer cDay, Integer cStatus) {
        this.cYear = cYear;
        this.cMonth = cMonth;
        this.cDay = cDay;
        this.cStatus = cStatus;
    }

    public CalendarConfiguration()
    {
    }
}