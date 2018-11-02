package com.service.Impl;

import com.bean.Student;
import com.dao.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public int deleteByPrimaryKey(Integer studentid) {
        return 0;
    }

    @Override
    public int insert(Student record) {
        return 0;
    }

    @Override
    public int insertSelective(Student record) {
        return studentMapper.insertSelective(record);
    }

    @Override
    public Student selectByPrimaryKey(Integer studentid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Student record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Student record) {
        return 0;
    }

    @Override
    public PageInfo getall(String stuname, String studentno,  Integer stusex,
                           int pageindex, int size, int[] ids, String studentstate) {
        Map map = new HashMap();
        map.put("stuname", stuname);
        map.put("studentno", studentno);
        map.put("stusex", stusex);
        PageHelper.startPage(pageindex, size);
        List<Student> list = studentMapper.getall(map);
        PageInfo pi = new PageInfo(list);
        return pi;
    }
}
