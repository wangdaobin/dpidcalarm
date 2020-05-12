package com.services.dpidcalarm.sysManager.bean;

/**
 * @Description 指标
 * @Author Admin
 * @Date 2020/1/12 0014 上午 10:28
 */
public class Indicator {
    private int code;
    private String name;
    private String cycle;
    private String desc;

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

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
}
