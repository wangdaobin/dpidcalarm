package com.services.dpidcalarm.base.service;

import com.services.dpidcalarm.base.bean.MyMapper;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/4 0004 10:46:14
 */
public abstract class BaseServiceAdapter<T> extends BaseServiceImpl<T>
        implements BaseService<T>
{
    @Override
    public abstract MyMapper<T> getBaseMapper();
}