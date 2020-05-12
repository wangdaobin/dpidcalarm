package com.services.dpidcalarm.collect.bean;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2020/4/22 0022 14:14:46
 */
public class BDZYCSXDetails {
    //厂站名
    private String stationName;
    //问题点数
    private int problemCount;
    //扣分项
    private float deductPoint;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getProblemCount() {
        return problemCount;
    }

    public void setProblemCount(int problemCount) {
        this.problemCount = problemCount;
    }

    public float getDeductPoint() {
        return deductPoint;
    }

    public void setDeductPoint(float deductPoint) {
        this.deductPoint = deductPoint;
    }


}