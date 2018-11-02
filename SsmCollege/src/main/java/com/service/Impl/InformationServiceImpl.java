package com.service.Impl;

import com.bean.Information;
import com.bean.Infotype;
import com.dao.InformationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationMapper informationMapper;
    @Override
    public int deleteByPrimaryKey(Integer informationid) {
        return 0;
    }

    @Override
    public int insert(Information record) {
        return 0;
    }

    @Override
    public int insertSelective(Information record) {
        return informationMapper.insertSelective(record);
    }

    @Override
    public Information selectByPrimaryKey(Integer informationid) {
        return informationMapper.selectByPrimaryKey(informationid);
    }

    @Override
    public int updateByPrimaryKeySelective(Information record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Information record) {
        return 0;
    }

    @Override
    public PageInfo getallinfo(String informationname, Integer typeid, int pageindex, int size) {
        System.out.println("-----------------");
        //封装模糊查条件
        Map map = new HashMap();
        map.put("informationname", informationname);
        map.put("typeid", typeid);
        PageHelper.startPage(pageindex, size);
        List<Information> list = informationMapper.getallinfo(map);
        PageInfo pi = new PageInfo(list);
        return pi;
    }
}
