package com.web;

import com.service.MajorService;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MajorController {
    @Autowired
    private MajorService majorService;

    //查询    major   GET
    @RequestMapping(value = "/major/list/{m1}",method = RequestMethod.GET)
    @ResponseBody
    public List get(int did, @PathVariable("m1") String m1) {
        List list = new ArrayList();
        System.out.println("GET list    m1="+m1);
        //根据学院编号查询学院对应的专业
        if ("findbydeptid_c".equals(m1)) {
            list = majorService.findbydeptid(did);
            return list;
        }else if ("findbydeptid_s".equals(m1)) {
            list = majorService.findbydeptid(did);
            return list;
        }
        return null;
    }

}
