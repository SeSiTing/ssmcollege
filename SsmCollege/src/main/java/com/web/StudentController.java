package com.web;

import com.bean.Classes;
import com.bean.Student;
import com.github.pagehelper.PageInfo;
import com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;


    //===查询  Class  string GET
    //展示页面
    //展示审核页面-----------------
    @RequestMapping(value = "/student/string/{m1}", method = RequestMethod.GET)
    public String getall(String stuname, String studentno,Integer stusex,
                         @RequestParam(value = "pageindex", defaultValue = "1") int pageindex,
                         ModelMap map, @PathVariable String m1,
                         @RequestParam(value = "pagesize", defaultValue = "5") int pagesize) {
        System.out.println("GET student string  m1=" + m1);
        //展示页面-----------------
        if ("show".equals(m1)) {
            PageInfo pageInfo = studentService.getall(stuname, studentno,stusex, pageindex, pagesize, null,null);
            map.put("pi", pageInfo);
            System.out.println(stusex);
            map.put("stuname", stuname);
            map.put("studentno", studentno);
            map.put("stusex",stusex);
            map.put("pagesize", pagesize);
            return "/Educational/student/list";
        }
        return null;
    }


    //新增   Class  POST
    //新增学生
    @RequestMapping(value = "/student/string/{m1}", method = RequestMethod.POST)
    public String add(Student student, @PathVariable("m1") String m1) {
        System.out.println("POST    classes string  m1=" + m1);
        //新增学生-------------------------
        if ("addstudent".equals(m1)) {
            System.out.println(student.getClassid());
            student.setRegdate(new Date());
            int k = studentService.insertSelective(student);
            System.out.println(k);
            return "redirect:/student/string/show";
        }
        return null;
    }


}
