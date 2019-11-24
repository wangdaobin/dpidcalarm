package com.services.dpidcalarm.datainfo.dao;

import com.services.dpidcalarm.base.bean.MyMapper;
import com.services.dpidcalarm.datainfo.bean.DataInfoError;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract interface DataInfoErrorDao extends MyMapper<DataInfoError>
{
  public abstract List<DataInfoError> findByDataId(Integer paramInteger);
}
