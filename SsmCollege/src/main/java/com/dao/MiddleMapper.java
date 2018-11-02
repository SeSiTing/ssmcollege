package com.dao;

import com.bean.Middle;

import java.util.Map;

public interface MiddleMapper {
    int deleteByPrimaryKey(Integer middleid);

    int insert(Middle record);

    int insertSelective(Middle record);

    Middle selectByPrimaryKey(Integer middleid);

    int updateByPrimaryKeySelective(Middle record);

    int updateByPrimaryKey(Middle record);

    int insertMiddle(Map map);

    //通过角色id删除中间表
    int deletemiddlebyrid(Integer roleid);

    //通过菜单id 删除中间表
    int deletemiddlebymid(Integer menuid);

    //角色表修改  先删后增
    int insertmiddle_role(Map map);

    //通过菜单id查询是否有角色在使用
    int selcountbymid(Integer menuid);
}