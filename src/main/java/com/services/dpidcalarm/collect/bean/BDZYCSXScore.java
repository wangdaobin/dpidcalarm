package com.services.dpidcalarm.collect.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2020/4/22 0022 14:08:54
 */
public class BDZYCSXScore {
    //当前得分
    private double score;
    //扣分详情
    private List<BDZYCSXDetails> detailsList = new ArrayList<BDZYCSXDetails>();

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<BDZYCSXDetails> getDetailsList() {
        return detailsList;
    }
}