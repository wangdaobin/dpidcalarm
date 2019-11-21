package com.services.dpidcalarm;

import com.services.dpidcalarm.sysManager.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DpidcalarmApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void getOneTest(){
        System.out.println("password:" + passwordEncoder.encode("123"));
    }
    @Test
    void contextLoads() {
    }

}
