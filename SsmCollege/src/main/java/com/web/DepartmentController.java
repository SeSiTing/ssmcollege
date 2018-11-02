package com.web;

import com.bean.Department;
import com.dao.DepartmentMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentMapper departmentMapper;

    //===查询  Department  GET
    @RequestMapping(value = "/department/string/{m1}", method = RequestMethod.GET)
    public String get(ModelMap map, @PathVariable("m1") String m1) {
        System.out.println("GET department   string  m1="+m1);
        //查询所有学院-------添加班级
        if ("findalldepa".equals(m1)) {
            List<Department> list = departmentMapper.findall();
            map.put("dlist", list);
            return "/Educational/class/add";
        }
        //查询所有学院-------添加学生
        if ("findalldepa_s".equals(m1)) {
            List<Department> list = departmentMapper.findall();
            map.put("dlist", list);
            return "/Educational/student/add";
        }
        return null;
    }


}
