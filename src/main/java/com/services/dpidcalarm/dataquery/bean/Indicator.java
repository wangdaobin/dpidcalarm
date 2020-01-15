package com.services.dpidcalarm.dataquery.bean;

/**
 * @Description 实时指标数据值
 * @Author Admin
 * @Date 2019/12/24 0024 下午 21:42
 */
public class Indicator {
    private String id;
    private String type;
    private String name;
    private String lastValue;
    private String lastTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastValue() {
        return lastValue;
    }

    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
