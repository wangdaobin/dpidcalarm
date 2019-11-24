package com.services.dpidcalarm.sconf.service;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/4 0004 10:38:57
 */
import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.base.service.BaseServiceAdapter;
import com.services.dpidcalarm.sconf.bean.PropertiesConfiguration;
import com.services.dpidcalarm.sconf.dao.PropertiesConfigurationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PropertiesConfigurationService extends BaseServiceAdapter<PropertiesConfiguration>
{

    @Autowired
    private PropertiesConfigurationDao propertiesConfigurationDao;

    @Transactional(readOnly=true)
    public PropertiesConfiguration findConfig(Integer isWeekend, Integer indicatorId)
    {
        return this.propertiesConfigurationDao.findConfig(isWeekend, indicatorId);
    }

    @Override
    public MyMapper<PropertiesConfiguration> getBaseMapper() {
        return this.propertiesConfigurationDao;
    }

    public boolean checkCalendar()
    {
        return false;
    }

    public void createCalendar()
            throws Exception
    {
    }
}