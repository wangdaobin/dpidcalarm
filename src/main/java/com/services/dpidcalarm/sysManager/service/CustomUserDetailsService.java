package com.services.dpidcalarm.sysManager.service;

import com.services.dpidcalarm.sysManager.bean.UserBean;
import com.services.dpidcalarm.sysManager.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description springSecurity加密
 * @Author Admin
 * @Date 2019/11/17 0017 下午 18:50
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserBean user = this.userMapper.findUserByName(s);
        User userDetails = null;
        if(user != null){
             userDetails = new User(user.getName(),user.getPasswd(),AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        }else{
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return userDetails;
    }
}
