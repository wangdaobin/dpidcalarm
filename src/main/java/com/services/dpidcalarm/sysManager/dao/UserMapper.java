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

    /**
     * 添加用户信息
     * @param user 用户信息
     * @return
     */
    int addUser(@Param("user")UserBean user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(@Param("user")UserBean user);

    int deleteUser(int userId);

    int modifyPassword(@Param("userName") String userName, @Param("password_new")String password_new);

    List<UserBean> queryUsers(String name);

    List<UserBean> queryAllUsers();
}
