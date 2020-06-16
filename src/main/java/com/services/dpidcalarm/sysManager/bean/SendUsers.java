package com.services.dpidcalarm.sysManager.bean;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/6/15 0015 下午 17:53
 */
public class SendUsers {
    private String indicatorType;
    private List<UserBean> users;

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public List<UserBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }
}
