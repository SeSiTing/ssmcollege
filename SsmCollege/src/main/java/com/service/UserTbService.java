package com.service;

import com.bean.UserTb;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserTbService {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserTb record);

    int insertSelective(UserTb record);

    UserTb selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserTb record);

    int updateByPrimaryKey(UserTb record);

    //登录
    UserTb login(UserTb userTb);
    //查询
    List<UserTb> findall(int did, int mid, String rolename);

    PageInfo getall_role(int pageindex,int pagesize);

}
