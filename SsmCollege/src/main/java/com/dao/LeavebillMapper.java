package com.dao;

import com.bean.Leavebill;

import java.util.List;

public interface LeavebillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Leavebill record);

    int insertSelective(Leavebill record);

    Leavebill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Leavebill record);

    int updateByPrimaryKey(Leavebill record);

    //查询用户请假条
    List<Leavebill> findleavelist(int userid);
}