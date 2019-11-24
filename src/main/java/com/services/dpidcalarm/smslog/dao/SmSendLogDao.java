package com.services.dpidcalarm.smslog.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.smslog.bean.SmSendLog;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface SmSendLogDao extends MyMapper<SmSendLog>
{
}