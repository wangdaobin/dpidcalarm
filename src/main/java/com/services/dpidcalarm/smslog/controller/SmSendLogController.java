package com.services.dpidcalarm.smslog.controller;
import com.services.dpidcalarm.base.controller.BaseController;
import com.services.dpidcalarm.base.service.BaseService;
import com.services.dpidcalarm.smslog.bean.SmSendLog;
import com.services.dpidcalarm.smslog.service.SmSendLogService;
import com.services.dpidcalarm.utils.AjaxBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"sendlog"})
public class SmSendLogController extends BaseController<SmSendLog>
{

    @Autowired
    private SmSendLogService smSendLogService;

    @Override
    public BaseService<SmSendLog> baseService() {
        return this.smSendLogService;
    }

    @GetMapping
    @Override
    public AjaxBody pageList(SmSendLog smSendLog, @RequestParam(value="page", required=false, defaultValue="1") Integer pageNum, @RequestParam(value="rows", required=false, defaultValue="10") Integer pageSize)
    {
        return AjaxBody.success(this.smSendLogService.pageList(smSendLog, pageNum, pageSize));
    }
}