package com.services.dpidcalarm.sysManager.controller;

import com.services.dpidcalarm.sysManager.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 登录处理
 * @Author Admin
 * @Date 2019/11/17 0017 下午 19:14
 */
@Controller
public class LoginController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
