package com.service.Impl;

import com.bean.Infotype;
import com.dao.InfotypeMapper;
import com.service.InfotypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfotypeServiceImpl implements InfotypeService {
    @Autowired
    private InfotypeMapper infotypeMapper;
    @Override
    public int deleteByPrimaryKey(Integer infoid) {
        return 0;
    }

    @Override
    public int insert(Infotype record) {
        return 0;
    }

    @Override
    public int insertSelective(Infotype record) {
        return 0;
    }

    @Override
    public Infotype selectByPrimaryKey(Integer infoid) {
        return infotypeMapper.selectByPrimaryKey(infoid);
    }

    @Override
    public int updateByPrimaryKeySelective(Infotype record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Infotype record) {
        return 0;
    }

    @Override
    public List<Infotype> getall() {
        return infotypeMapper.getall();
    }
}
