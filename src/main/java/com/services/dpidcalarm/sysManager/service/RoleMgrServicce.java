package com.services.dpidcalarm.sysManager.service;

import com.services.dpidcalarm.sysManager.bean.RoleBean;
import com.services.dpidcalarm.sysManager.dao.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

/**
 * @Description 角色管理
 * @Author Admin
 * @Date 2020/1/14 0014 下午 14:22
 */
@Service
public class RoleMgrServicce {
    @Autowired
    private RoleMapper roleMapper;

    public List<RoleBean> queryRoles(){
        return roleMapper.queryRoles();
    }

    public int deleteRole(int roleId) {
        return roleMapper.deleteRole(roleId);
    }

    public int modifyRole(RoleBean roleInfo) {
        return roleMapper.updateRole(roleInfo);
    }

    public int addRole(RoleBean roleInfo){
        return roleMapper.addRole(roleInfo);
    }
}
