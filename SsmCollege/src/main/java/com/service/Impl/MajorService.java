package com.service.Impl;

import com.bean.Major;
import com.dao.MajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService implements com.service.MajorService {
    @Autowired
    private MajorMapper majorMapper;
    @Override
    public int deleteByPrimaryKey(Integer majorid) {
        return 0;
    }

    @Override
    public int insert(Major record) {
        return 0;
    }

    @Override
    public int insertSelective(Major record) {
        return 0;
    }

    @Override
    public Major selectByPrimaryKey(Integer majorid) {
        Major major = majorMapper.selectByPrimaryKey(majorid);
        return major;
    }

    @Override
    public int updateByPrimaryKeySelective(Major record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Major record) {
        return 0;
    }

    @Override
    public List<Major> findbydeptid(int did) {
        return majorMapper.findbydeptid(did);
    }
}
