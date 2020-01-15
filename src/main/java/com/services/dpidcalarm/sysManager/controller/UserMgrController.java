package com.services.dpidcalarm.sysManager.controller;

import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.sysManager.bean.UserBean;
import com.services.dpidcalarm.sysManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

/**
 * @Description 用户管理控制层
 * @Author Admin
 * @Date 2020/1/12 0013 上午 11:01
 */
@Controller
@RequestMapping("user")
public class UserMgrController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("queryCurrentUser")
    @ResponseBody
    public UserBean queryCurrentUser(){
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        return userService.queryUserByName(userName);
    }

    @RequestMapping("modifyPassword")
    @ResponseBody
    public String modifyPassword(String userName,String password_old,String password_new){
        UserBean user = userService.queryUserByName(userName);
        if(user.getPasswd().equals(passwordEncoder.encode(password_old))){
            int i = userService.updatePassword(userName,passwordEncoder.encode(password_new));
            if(i > 0){
                return "修改成功";
            }else{
                return "修改失败，请联系管理员修改！";
            }

        }else{
            return "原密码输入有误！";
        }
    }
    @RequestMapping("queryUsers")
    @ResponseBody
    public PageInfo<UserBean> queryUsers(String userName,int pageNum, int pageSize){
        return this.userService.queryUsers(userName,pageNum,pageSize);
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public int deleteUser(int userId){
        return this.userService.deleteUser(userId);
    }

    @RequestMapping("addUser")
    @ResponseBody
    public int addUser(@RequestBody UserBean userInfo){
        userInfo.setPasswd(passwordEncoder.encode(userInfo.getPasswd()));
        userInfo.setStatus("1");
        return this.userService.addUser(userInfo);
    }

    @RequestMapping("modifyUser")
    @ResponseBody
    public int modifyUser(@RequestBody UserBean userInfo){
        if( null != userInfo.getPasswd() && userInfo.getPasswd().length()<= 16){
            userInfo.setPasswd(passwordEncoder.encode(userInfo.getPasswd()));
        }
        userInfo.setStatus("1");
        return this.userService.modifyUser(userInfo);
    }
}
