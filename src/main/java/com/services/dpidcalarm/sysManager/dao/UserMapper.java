package com.services.dpidcalarm.sysManager.dao;

import com.services.dpidcalarm.sysManager.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 用户数据库查询
 * @Author Admin
 * @Date 2019/11/17 0017 下午 18:58
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * 查找所有用户
     * @return List<UserBean>
     */
    List<UserBean> findAllUser();

    /**
     * 根据用户名查找用户
     * @param name 要查找的用户名
     * @return UserBean
     */
    UserBean findUserByName(@Param("name") String name);
}
