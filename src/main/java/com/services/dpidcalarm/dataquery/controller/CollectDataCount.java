package com.services.dpidcalarm.dataquery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 数据查询统计
 * @Author Admin
 * @Date 2019/11/28 0028 下午 18:03
 */
@Controller
public class CollectDataCount {

    @RequestMapping("/queryDataCountSingle")
    @ResponseBody
    public String queryDataCountSingle(String begin,String end){
        try{
            System.out.println(begin);
            System.out.println(end);
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
