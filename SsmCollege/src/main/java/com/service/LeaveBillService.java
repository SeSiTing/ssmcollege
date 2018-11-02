package com.service;

import com.bean.Leavebill;
import com.bean.Leavebill;

import java.util.List;

public interface LeaveBillService {
    //查询用户的请假条
    List<Leavebill> findleavelist(int userid);

    //添加请假条
    int insert(Leavebill record);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Leavebill record);

    Leavebill selectByPrimaryKey(Integer id);

    //修改请假条
    int updateByPrimaryKeySelective(Leavebill record);

    int updateByPrimaryKey(Leavebill record);
}
