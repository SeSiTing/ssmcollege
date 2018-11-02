package com.web;

import com.bean.Leavebill;
import com.bean.UserTb;
import com.service.LeaveBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LeaveBillController {
    @Autowired
    private LeaveBillService leaveBillService;
    //查询用户的请假条
    @RequestMapping("/qingjia/getleavebills")
    public String findall(HttpSession session, ModelMap map){
        UserTb userTb=(UserTb) session.getAttribute("u1");
        //查看当前登录人的请假信息
        List<Leavebill> list=leaveBillService.findleavelist(userTb.getUserId());
        map.put("leavelist",list);
        return  "/qingjia/list";
    }

    //新增假单
    @RequestMapping("/qingjia/add")
    public String add(Leavebill leaveBill){
        leaveBillService.insert(leaveBill);
        return "redirect:/qingjia/getleavebills";
    }
}
