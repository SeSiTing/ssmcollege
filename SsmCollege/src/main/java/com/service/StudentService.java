package com.service;

import com.bean.Student;
import com.github.pagehelper.PageInfo;

public interface StudentService {
    int deleteByPrimaryKey(Integer studentid);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer studentid);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
    //查询全部的方法
    public PageInfo getall(String name, String classnum,Integer stusex,int pageindex, int size, int[] ids, String studentstate);
}
