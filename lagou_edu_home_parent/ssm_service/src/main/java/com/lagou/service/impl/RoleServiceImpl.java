package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.Role_menu_relation;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    /*
        查询角色
     */
    @Override
    public List<Role> findAllRole(Role role) {
        List<Role> allRole = roleMapper.findAllRole(role);
        return allRole;
    }


    /*
        根据角色ID查询该角色关联的菜单信息ID
     */
    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        List<Integer> menuByRoleId = roleMapper.findMenuByRoleId(roleId);

        return menuByRoleId;
    }

    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {

        //1.清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());

        //2.为角色分配菜单
        for (Integer mid : roleMenuVo.getMenuIdList()) {

            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());

            //封装数据
            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);

            role_menu_relation.setCreatedBy("/system");
            role_menu_relation.setUpdatedby("/system");
            roleMapper.roleContextMenu(role_menu_relation);

        }



    }

    @Override
    public void deleteRole(Integer roleId) {

        //调用根据roleId清空中间表关联关系
        roleMapper.deleteRoleContextMenu(roleId);

        roleMapper.deleteRole(roleId);

    }
}
