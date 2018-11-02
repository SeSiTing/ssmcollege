package com.dao;

import com.bean.Menu;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer menuid);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuid);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> findbyroleid(int roleid);

    List<Menu> getall();

    List<Menu> getallF();

    //查询该id下是否还有二级目录
    int findbyupid(int menuid);

    //批量删除
    int delbyids(List<Integer> list);


}