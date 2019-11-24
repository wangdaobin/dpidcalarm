package com.services.dpidcalarm.sconf.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.sconf.bean.TimingConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface TimingConfigurationDao extends MyMapper<TimingConfiguration>
{
}