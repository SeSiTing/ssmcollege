package com.service;

import com.bean.Classes;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ClassService {
    int deleteByPrimaryKey(Integer classid);

    int insert(Classes record);

    int insertSelective(Classes record);

    Classes selectByPrimaryKey(Integer classid);

    int updateByPrimaryKeySelective(Classes record);

    int updateByPrimaryKey(Classes record);

    List<Classes> getall(Map map);
    //查询全部的方法
    PageInfo getall(String stuname, String studentnum, int pageindex, int size,int[] ids,String classstate);

}
