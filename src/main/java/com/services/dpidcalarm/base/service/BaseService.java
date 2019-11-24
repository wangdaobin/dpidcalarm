package com.services.dpidcalarm.base.service;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/4 0004 11:14:13
 */
import com.github.pagehelper.PageInfo;
import com.services.dpidcalarm.base.bean.MyMapper;

public abstract interface BaseService<T>
{
    public abstract PageInfo<T> pageList(T paramT, Integer paramInteger1, Integer paramInteger2);

    public abstract T findById(Integer paramInteger);

    public abstract void add(T paramT)
            throws Exception;

    public abstract void update(T paramT)
            throws Exception;

    public abstract void delete(Integer paramInteger)
            throws Exception;
    public abstract MyMapper<T> getBaseMapper();
}