package com.service.Impl;

import com.bean.Menu;
import com.bean.Role;
import com.bean.UserTb;
import com.dao.MiddleMapper;
import com.dao.RoleMapper;
import com.dao.UserTbMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MiddleMapper middleMapper;

    @Override
    public int deleteByPrimaryKey(Integer roleid) {
        return 0;
    }

    @Override
    public int insert(Role record) {
        return 0;
    }

    @Override
    public int insertSelective(Role record) {
        return 0;
    }

    @Override
    public Role selectByPrimaryKey(Integer roleid) {
        return roleMapper.selectByPrimaryKey(roleid);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return 0;
    }

    @Override
    public PageInfo getallrole(int pageindex, int pagesize) {
        PageHelper.startPage(pageindex, pagesize);
        List<Role> list = roleMapper.getallrole();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
    @Override
    public List<Role> getallrole2() {
        List<Role> list = roleMapper.getallrole();
        return list;
    }

    @Override
    public int addrole(Role r, int[] menus) {
        //1.新增到角色
        int k = roleMapper.insert(r);
        //新增中间表
        Map map = new HashMap();
        map.put("roleid", r.getRoleid());
        map.put("menuids", menus);
        int k2 = middleMapper.insertMiddle(map);
        return k2;
    }

    @Override
    public Role getonerole(Integer roleid) {
        Role role = roleMapper.selectByPrimaryKey(roleid);
        List<Menu> list = role.getMenus();
        //菜单分级
        List newlist = new ArrayList();
        for (Menu menu : list) {
            if (menu.getUpmenuid() == -1) {
                List menus = new ArrayList();
                for (Menu menu2 :
                        list) {
                    if (menu2.getUpmenuid() == menu.getMenuid()) {
                        menus.add(menu2);
                    }
                }
                menu.setSeconds(menus);
                newlist.add(menu);
            }
        }
        Map map = new HashMap();
        map.put("role", role);
        map.put("list", newlist);
        role.setMenus(newlist);
        return role;
    }

    @Override
    public int deleterole(int roleid) {
        //先判断该角色下是否有用户
        int count = roleMapper.findusercountbyroleid(roleid);
        if (count > 0) {
            return 0;
        }
        //先删除中间表
        middleMapper.deletemiddlebyrid(roleid);
        //然后删除角色
        roleMapper.deleteByPrimaryKey(roleid);
        return 1;
    }

    @Override
    public int update(Role role, int[] ids) {
        int k=0;
        //修改角色表
        roleMapper.updateByPrimaryKeySelective(role);
        //修改中间表（先删后改）
        middleMapper.deletemiddlebyrid(role.getRoleid());
        Map map = new HashMap();
        map.put("roleid", role.getRoleid());
        map.put("menuids", ids);
        middleMapper.insertmiddle_role(map);
        return k;
    }
}
