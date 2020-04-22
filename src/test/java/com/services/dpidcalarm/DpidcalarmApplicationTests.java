package com.services.dpidcalarm;

import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.dataquery.bean.IndicatorHisData;
import com.services.dpidcalarm.dataquery.service.DataQueryService;
import com.services.dpidcalarm.sysManager.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class DpidcalarmApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataQueryService dataQueryService;
    @Test
    public void getOneTest(){
        System.out.println("password:" + passwordEncoder.encode("admin"));
    }
    @Test
    void contextLoads() {
    }
    @Test
    void testQueryRealData(){
        System.out.println(dataQueryService.queryRealTimeData(1001));
    }
    @Test
    void testQueryHisData(){
        PageInfo<IndicatorHisData> list = this.dataQueryService.queryHisData(1001,"2019-12-15 19:00:00","2019-12-15 20:00:00",1,10);
        for(IndicatorHisData data : list.getList()){
            System.out.println(data);
        }
    }

    @Test
    void testQueryDetail(){
        PageInfo<HashMap<String,String>> list = this.dataQueryService.queryDetail("indicator_details_ztgj","1",1,10);
        for(HashMap data : list.getList()){
            System.out.println(data);
        }
    }

}
