package com.service;

import com.bean.Menu;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MenuService {
    int deleteByPrimaryKey(Integer menuid);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuid);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    //根据角色id查询菜单集合
    List<Menu> findbyroleid(int roleid);

    //查询全部菜单
    List<Menu> getall();

    //返回分页 菜单
    PageInfo getallFS(int pageindex, int pagesize);

    //获得所有一级目录
    List<Menu> getallF();

    //批量删除
    int delbyids(String[] ids);

}
