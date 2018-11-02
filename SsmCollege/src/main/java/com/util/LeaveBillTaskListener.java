package com.util;

import com.bean.UserTb;
import com.service.Impl.UserTbServiceImpl;
import com.service.UserTbService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
/*@Component*/
public class LeaveBillTaskListener implements TaskListener {
    /*public LeaveBillTaskListener() {
        System.out.println("-------------------");
    }*/
    /*@Autowired
    private UserTbService userTbService;*/

    //注解不行  就new一个
    /*private UserTbService userTbService = new UserTbServiceImpl(); */

    @Override
    public void notify(DelegateTask delegateTask) {
        //任务执行人
        //当前登录用户  用户登录时 将管理人manger对象封装赋给user对象
        HttpSession session =
                ((ServletRequestAttributes)
                        (RequestContextHolder.getRequestAttributes()))
                        .getRequest()
                        .getSession();
        System.out.println(session);
        UserTb users = (UserTb) session.getAttribute("u1");
        System.out.println(users.getManager().getUserName());
        delegateTask.setAssignee(users.getManager().getUserName());

    }
    //现在是监听器里只能取值   不能调值？？？
}
