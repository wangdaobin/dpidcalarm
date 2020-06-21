package com.services.dpidcalarm.sysManager.bean;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/6/16 0016 下午 14:12
 */
public class MessageSetting {
    private String indicatorType;
    private int gzOption;
    private String gzValue;
    private int sendType;
    private String messageInterval;
    private String sendStartTime;
    private String sendEndTime;
    private int nightSetting;
    private String nightStartTime;
    private String nightEndTime;
    private String nightSendInterval;

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public String getGzValue() {
        return gzValue;
    }

    public void setGzValue(String gzValue) {
        this.gzValue = gzValue;
    }

    public void setMessageInterval(String messageInterval) {
        this.messageInterval = messageInterval;
    }

    public String getSendStartTime() {
        return sendStartTime;
    }

    public void setSendStartTime(String sendStartTime) {
        this.sendStartTime = sendStartTime;
    }

    public String getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(String sendEndTime) {
        this.sendEndTime = sendEndTime;
    }


    public String getNightStartTime() {
        return nightStartTime;
    }

    public void setNightStartTime(String nightStartTime) {
        this.nightStartTime = nightStartTime;
    }

    public String getNightEndTime() {
        return nightEndTime;
    }

    public void setNightEndTime(String nightEndTime) {
        this.nightEndTime = nightEndTime;
    }

    public String getNightSendInterval() {
        return nightSendInterval;
    }

    public void setNightSendInterval(String nightSendInterval) {
        this.nightSendInterval = nightSendInterval;
    }

    public int getGzOption() {
        return gzOption;
    }

    public void setGzOption(int gzOption) {
        this.gzOption = gzOption;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getMessageInterval() {
        return messageInterval;
    }

    public int getNightSetting() {
        return nightSetting;
    }

    public void setNightSetting(int nightSetting) {
        this.nightSetting = nightSetting;
    }
}
