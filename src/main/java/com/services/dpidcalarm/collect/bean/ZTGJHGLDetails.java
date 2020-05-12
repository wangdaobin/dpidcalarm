package com.services.dpidcalarm.collect.bean;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2020/4/27 0022 14:14:46
 */
public class ZTGJHGLDetails {
    /**
     * fa_name： 父区域名称
     * name： 地区名
     * sum_st： 厂站数
     * rate_dv：地区合格率
     * srate_meas：？
     * sum_meas：遥测总数
     * good_meas：遥测合格数
     * bad_point：错误遥信数
     * sum_v：电压总数
     * good_v：电压合格数
     * sum_ln：线路总数
     * good_ln：线路合格数
     * sum_xf：变压器总数
     * good_xf：变压器合格数
     * visible：？
     * sum_ld：负荷遥测总数
     * good_ld：
     * sum_un：
     * good_un：
     */
    //指标统计表中的id
    private int indicatorDataId;
    //父区域名称
    private String parentAreaName;
    //区域名称
    private String areaName;
    //厂站数
    private int stationNum;
    //地区合格率
    private float rateDv;
    // 不知道什么值？？？？？？
    private float srateMeas;
    // 遥测总数
    private int ycNum;
    //遥测合格数
    private int ycHgNum;
    //错误遥信数
    private int cwyxNum;
    //电压总数
    private int dyNum;
    //电压合格数
    private int dyHgNum;
    //线路总数
    private int xlNum;
    //线路合格数
    private int xlHgNum;
    //变压器总数
    private int byqNum;
    //变压器合格数
    private int byqHgNum;
    //负荷遥测总数
    private int fhycNum;
    //负荷遥测合格总数
    private int fhycHgNum;

    public int getIndicatorDataId() {
        return indicatorDataId;
    }

    public void setIndicatorDataId(int indicatorDataId) {
        this.indicatorDataId = indicatorDataId;
    }

    public String getParentAreaName() {
        return parentAreaName;
    }

    public void setParentAreaName(String parentAreaName) {
        this.parentAreaName = parentAreaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getStationNum() {
        return stationNum;
    }

    public void setStationNum(int stationNum) {
        this.stationNum = stationNum;
    }

    public float getRateDv() {
        return rateDv;
    }

    public void setRateDv(float rateDv) {
        this.rateDv = rateDv;
    }

    public float getSrateMeas() {
        return srateMeas;
    }

    public void setSrateMeas(float srateMeas) {
        this.srateMeas = srateMeas;
    }

    public int getYcNum() {
        return ycNum;
    }

    public void setYcNum(int ycNum) {
        this.ycNum = ycNum;
    }

    public int getYcHgNum() {
        return ycHgNum;
    }

    public void setYcHgNum(int ycHgNum) {
        this.ycHgNum = ycHgNum;
    }

    public int getCwyxNum() {
        return cwyxNum;
    }

    public void setCwyxNum(int cwyxNum) {
        this.cwyxNum = cwyxNum;
    }

    public int getDyNum() {
        return dyNum;
    }

    public void setDyNum(int dyNum) {
        this.dyNum = dyNum;
    }

    public int getDyHgNum() {
        return dyHgNum;
    }

    public void setDyHgNum(int dyHgNum) {
        this.dyHgNum = dyHgNum;
    }

    public int getXlNum() {
        return xlNum;
    }

    public void setXlNum(int xlNum) {
        this.xlNum = xlNum;
    }

    public int getXlHgNum() {
        return xlHgNum;
    }

    public void setXlHgNum(int xlHgNum) {
        this.xlHgNum = xlHgNum;
    }

    public int getByqNum() {
        return byqNum;
    }

    public void setByqNum(int byqNum) {
        this.byqNum = byqNum;
    }

    public int getByqHgNum() {
        return byqHgNum;
    }

    public void setByqHgNum(int byqHgNum) {
        this.byqHgNum = byqHgNum;
    }

    public int getFhycNum() {
        return fhycNum;
    }

    public void setFhycNum(int fhycNum) {
        this.fhycNum = fhycNum;
    }

    public int getFhycHgNum() {
        return fhycHgNum;
    }

    public void setFhycHgNum(int fhycHgNum) {
        this.fhycHgNum = fhycHgNum;
    }
}