package com.services.dpidcalarm.sconf.controller;

import com.services.dpidcalarm.base.controller.BaseController;
import com.services.dpidcalarm.base.service.BaseService;
import com.services.dpidcalarm.sconf.bean.PropertiesConfiguration;
import com.services.dpidcalarm.sconf.service.PropertiesConfigurationService;
import com.services.dpidcalarm.utils.AjaxBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"sconf"})
public class PropertiesConfigurationController extends BaseController<PropertiesConfiguration>
{

    @Autowired
    private PropertiesConfigurationService propertiesConfigurationService;


    @Override
    public BaseService<PropertiesConfiguration> baseService() {
        return this.propertiesConfigurationService;
    }
    @GetMapping({"recevier/{indicatorId}"})
    @ResponseBody
    public AjaxBody recevier(@PathVariable("indicatorId") Integer indicatorId) {
        //return AjaxBody.success(this.userService.findByIndicator(indicatorId));
        return AjaxBody.success();
    }

    @PutMapping({"recevier"})
    @ResponseBody
    public AjaxBody updateRecevier(@RequestParam("indicatorId") Integer indicatorId, @RequestParam("ids") Integer[] ids) {
        try {
            List idsInt = new ArrayList();
            if ((ids != null) && (ids.length > 0)) {
                idsInt = Arrays.asList(ids);
            }
            //this.userService.setReceiver(idsInt, indicatorId);
            return AjaxBody.success();
        } catch (Exception e) {
            return AjaxBody.fail(e.getMessage());
        }

    }
}