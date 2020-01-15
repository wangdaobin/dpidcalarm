package com.services.dpidcalarm.sysManager.bean;

/**
 * @Description 权限
 * @Author Admin
 * @Date 2019/11/17 0017 下午 18:51
 */
public class RoleBean {
    private int id;
    private String name;
    private String desc;
    private int sysadm;
    private int memadm;
    private int collectadm;
    private int alarmadm;
    private String authority = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSysadm() {
        return sysadm;
    }

    public void setSysadm(int sysadm) {
        if(1 == sysadm){
            authority += "系统管理 ";
        }
        this.sysadm = sysadm;
    }

    public int getMemadm() {
        return memadm;
    }

    public void setMemadm(int memadm) {
        if(1 == memadm){
            authority += "人员管理 ";
        }
        this.memadm = memadm;
    }

    public int getCollectadm() {
        return collectadm;
    }

    public void setCollectadm(int collectadm) {
        if(1 == collectadm){
            authority += "采集管理 ";
        }
        this.collectadm = collectadm;
    }

    public int getAlarmadm() {
        return alarmadm;
    }

    public void setAlarmadm(int alarmadm) {
        if(1 == alarmadm){
            authority += "告警管理 ";
        }
        this.alarmadm = alarmadm;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
