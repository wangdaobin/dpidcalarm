package com.services.dpidcalarm.sysManager.bean;

import java.util.Date;

/**
 * @Description 指标
 * @Author Admin
 * @Date 2020/1/12 0014 上午 10:28
 */
public class Indicator {
    private int code;
    private String name;
    private String desc;

    //zt新增

    private int id;
    private String type;
    private float lastValue;
    private Date lastTime;
    private float limitValue;
    private int sendMsgCount;

    public float getLastValue() {
        return lastValue;
    }

    public void setLastValue(float lastValue) {
        this.lastValue = lastValue;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public float getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(float limitValue) {
        this.limitValue = limitValue;
    }

    public int getSendMsgCount() {
        return sendMsgCount;
    }

    public void setSendMsgCount(int sendMsgCount) {
        this.sendMsgCount = sendMsgCount;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }





    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
