package com.services.dpidcalarm.sysManager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.sysManager.bean.UserBean;
import com.services.dpidcalarm.sysManager.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author wangdaobin
 * @Date 2020/1/13 0013 下午 14:19
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public UserBean queryUserByName(String name){
        return userMapper.findUserByName(name);
    }

    public int updatePassword(String userName, String password_new) {
        return userMapper.modifyPassword(userName,password_new);
    }

    public PageInfo<UserBean> queryUsers(String userName,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<UserBean> users = this.userMapper.queryUsers(userName);
        return new PageInfo<UserBean>(users);
    }

    public int deleteUser(int userId) {
        return this.userMapper.deleteUser(userId);
    }

    public int addUser(UserBean userInfo) {
        return this.userMapper.addUser(userInfo);
    }

    public int modifyUser(UserBean userInfo) {
        return this.userMapper.updateUser(userInfo);
    }

    public List<UserBean> queryAllUsers() {
        return this.userMapper.queryAllUsers();
    }
}
