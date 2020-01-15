package com.services.dpidcalarm.sysManager.controller;

import com.services.dpidcalarm.sysManager.bean.RoleBean;
import com.services.dpidcalarm.sysManager.service.RoleMgrServicce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 角色管理
 * @Author Admin
 * @Date 2020/1/12 0014 下午 14:21
 */
@Controller
@RequestMapping("role")
public class RoleMgrController {

    @Autowired
    private RoleMgrServicce roleMgrServicce;

    @RequestMapping("queryRoles")
    @ResponseBody
    public List<RoleBean> queryRoles(){
        return this.roleMgrServicce.queryRoles();
    }
    @RequestMapping("deleteRole")
    @ResponseBody
    public int deleteRole(int roleId){
        return this.roleMgrServicce.deleteRole(roleId);
    }
    @RequestMapping("modifyRole")
    @ResponseBody
    public int modifyRole(@RequestBody RoleBean roleInfo){
        return this.roleMgrServicce.modifyRole(roleInfo);
    }

    @RequestMapping("addRole")
    @ResponseBody
    public int addRole(@RequestBody RoleBean roleInfo){
        return this.roleMgrServicce.addRole(roleInfo);
    }

}
