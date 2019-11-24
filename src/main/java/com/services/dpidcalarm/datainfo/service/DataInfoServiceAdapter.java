package com.services.dpidcalarm.datainfo.service;

import com.services.dpidcalarm.datainfo.bean.DataInfo;
import com.services.dpidcalarm.datainfo.bean.DataInfoError;
import com.services.dpidcalarm.datainfo.dao.DataInfoDao;
import com.services.dpidcalarm.datainfo.dao.DataInfoErrorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DataInfoServiceAdapter
{

    @Autowired
    private DataInfoDao dataInfoDao;

    @Autowired
    private DataInfoErrorDao dataInfoErrorDao;

    public void save(DataInfo dataInfo, List<DataInfoError> errors)
    {
        this.dataInfoDao.insert(dataInfo);
        if ((errors != null) && (errors.size() > 0)){
            for (DataInfoError dataInfoError : errors) {
                dataInfoError.setDataId(dataInfo.getId());
                this.dataInfoErrorDao.insert(dataInfoError);
            }
        }

    }
}