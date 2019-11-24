package com.services.dpidcalarm.base.bean;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/10 0010 16:23:35
 */
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public abstract interface MyMapper<T>
        extends Mapper<T>, MySqlMapper<T>
{}