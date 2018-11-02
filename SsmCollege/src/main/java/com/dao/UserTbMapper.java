package com.dao;

import com.bean.UserTb;

import java.util.List;
import java.util.Map;

public interface UserTbMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserTb record);

    int insertSelective(UserTb record);

    UserTb selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserTb record);

    int updateByPrimaryKey(UserTb record);

    //登录
    UserTb login(String username);

    //查询老师
    List findall(Map map);

    List<UserTb> getall_role();
}