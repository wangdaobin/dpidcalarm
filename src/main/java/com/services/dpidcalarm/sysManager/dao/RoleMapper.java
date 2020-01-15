package com.services.dpidcalarm.sysManager.dao;

import com.services.dpidcalarm.sysManager.bean.RoleBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 角色数据库查询
 * @Author Admin
 * @Date 2019/11/17 0017 下午 18:58
 */
@Mapper
@Repository
public interface RoleMapper {

    /**
     * 查询所有角色
     * @return 角色列表 List<RoleBean>
     */
    List<RoleBean> queryRoles();

    int addRole(RoleBean role);

    int updateRole(RoleBean role);

    int deleteRole(int roleId);

}
