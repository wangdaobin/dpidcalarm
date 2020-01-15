package com.services.dpidcalarm.dataquery.bean;

/**
 * @Description 历史数据
 * @Author Admin
 * @Date 2019/12/24 0024 下午 22:33
 */
public class IndicatorHisData {
    private int id;
    private int type;
    private String name;
    private String value;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "IndicatorHisData{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", vlaue='" + value + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
