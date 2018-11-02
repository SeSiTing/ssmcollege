package com.service.Impl;

import com.bean.Department;
import com.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentService implements com.service.DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public int deleteByPrimaryKey(Integer departid) {
        return 0;
    }

    @Override
    public int insert(Department record) {
        return 0;
    }

    @Override
    public int insertSelective(Department record) {
        return 0;
    }

    @Override
    public Department selectByPrimaryKey(Integer departid) {
        Department department = departmentMapper.selectByPrimaryKey(departid);
        return department;
    }

    @Override
    public int updateByPrimaryKeySelective(Department record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return 0;
    }

    @Override
    public List<Department> findall() {
        return departmentMapper.findall();
    }
}
