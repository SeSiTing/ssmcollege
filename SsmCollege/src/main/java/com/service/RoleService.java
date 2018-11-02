package com.service;

import com.bean.Menu;
import com.bean.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface RoleService {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    PageInfo getallrole(int pageindex, int pagesize);

    //新增角色
    public int addrole(Role r, int[] menus);

    //单个角色的分级目录
    Role getonerole(Integer roleid);

    //删除用户
    public int deleterole(int roleid);

    //修改角色表
    int update(Role role, int[] ids);

    //获得所有角色信息
    public List<Role> getallrole2();
}
