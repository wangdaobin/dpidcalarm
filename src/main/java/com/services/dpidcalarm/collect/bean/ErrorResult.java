package com.services.dpidcalarm.collect.bean;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/2 0002 15:48:23
 */
public class ErrorResult
{
    private String area;
    private String czName;
    private String sbName;
    private String sbType;
    private float gjValue;
    private float lcValue;
    private float wcPercent;
    private float ccValue;
    private float jzValue;

    public String getArea()
    {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCzName() {
        return this.czName;
    }

    public void setCzName(String czName) {
        this.czName = czName;
    }

    public String getSbName() {
        return this.sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getSbType() {
        return this.sbType;
    }

    public void setSbType(String sbType) {
        this.sbType = sbType;
    }

    public float getGjValue() {
        return this.gjValue;
    }

    public void setGjValue(float gjValue) {
        this.gjValue = gjValue;
    }

    public float getLcValue() {
        return this.lcValue;
    }

    public void setLcValue(float lcValue) {
        this.lcValue = lcValue;
    }

    public float getWcPercent() {
        return this.wcPercent;
    }

    public void setWcPercent(float wcPercent) {
        this.wcPercent = wcPercent;
    }

    public float getCcValue() {
        return this.ccValue;
    }

    public void setCcValue(float ccValue) {
        this.ccValue = ccValue;
    }

    public float getJzValue() {
        return this.jzValue;
    }

    public void setJzValue(float jzValue) {
        this.jzValue = jzValue;
    }
    @Override
    public String toString()
    {
        return "ErrorResult{area='" + this.area + '\'' + ", czName='" + this.czName + '\'' + ", sbName='" + this.sbName + '\'' + ", sbType='" + this.sbType + '\'' + ", gjValue=" + this.gjValue + ", lcValue=" + this.lcValue + ", wcPercent=" + this.wcPercent + ", ccValue=" + this.ccValue + ", jzValue=" + this.jzValue + '}';
    }
}