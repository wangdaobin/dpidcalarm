package com.services.dpidcalarm.sysManager.bean;

/**
 * @Description 指标采集属性
 * @Author Admin
 * @Date 2020/1/12 0014 上午 11:51
 */
public class IndicatorAttr {
    private int id;
    private int idcCode;
    private String idcName;
    private String loginAddr;
    private String loginName;
    private String loginPassword;
    private int collectCycle;
    private int collectType;
    private String collectAddr;
    private String param1;
    private String param2;
    private String param3;
    private String param4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcCode() {
        return idcCode;
    }

    public void setIdcCode(int idcCode) {
        this.idcCode = idcCode;
    }

    public String getIdcName() {
        return idcName;
    }

    public void setIdcName(String idcName) {
        this.idcName = idcName;
    }

    public String getLoginAddr() {
        return loginAddr;
    }

    public void setLoginAddr(String loginAddr) {
        this.loginAddr = loginAddr;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public int getCollectCycle() {
        return collectCycle;
    }

    public void setCollectCycle(int collectCycle) {
        this.collectCycle = collectCycle;
    }

    public int getCollectType() {
        return collectType;
    }

    public void setCollectType(int collectType) {
        this.collectType = collectType;
    }

    public String getCollectAddr() {
        return collectAddr;
    }

    public void setCollectAddr(String collectAddr) {
        this.collectAddr = collectAddr;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }
}
