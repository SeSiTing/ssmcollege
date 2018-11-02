package com.service.Impl;

import com.bean.Leavebill;
import com.dao.LeavebillMapper;
import com.service.LeaveBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveBillServiceImpl implements LeaveBillService {
    @Autowired
    private LeavebillMapper leavebillMapper;
    //查询请假条
    @Override
    public List<Leavebill> findleavelist(int userid) {
        return leavebillMapper.findleavelist(userid);
    }

    //添加请假条
    @Override
    public int insert(Leavebill leaveBill) {
        return leavebillMapper.insertSelective(leaveBill);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insertSelective(Leavebill record) {
        return 0;
    }

    @Override
    public Leavebill selectByPrimaryKey(Integer id) {
        return leavebillMapper.selectByPrimaryKey(id);
    }

    //更新请假条
    @Override
    public int updateByPrimaryKeySelective(Leavebill record) {
        return leavebillMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Leavebill record) {
        return 0;
    }

}
