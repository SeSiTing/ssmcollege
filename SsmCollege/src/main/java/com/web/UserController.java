package com.web;

import cn.com.webxml.ArrayOfString;
import cn.com.webxml.GetRegionDatasetResponse;
import cn.com.webxml.WeatherWSSoap;
import com.bean.Role;
import com.bean.UserTb;
import com.github.pagehelper.PageInfo;
import com.service.RoleService;
import com.service.UserTbService;
import com.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"u1", "logintime"})
public class UserController {

    @Autowired
    private UserTbService userTbService;
    @Autowired
    private RoleService roleService;
    /*@Autowired
    private MobileCodeWSSoap soap;

    //查询归属地
    @RequestMapping("getaddress")
    public void select(String phone,HttpServletResponse response){
        String str= soap.getMobileCodeInfo(phone,null);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    @Autowired
    private WeatherWSSoap soap;

    //查询天气
    @RequestMapping("getweather")
    public void weather(String address, HttpServletResponse response) {
        System.out.println("地址是"+address);
        ArrayOfString weather = soap.getWeather(address,null);
        List<String> string = weather.getString();
        Object[] objects = string.toArray();
        System.out.println("天气是"+objects[4]);
        Object wea = objects[4];
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out =response.getWriter();
            out.print(wea);
           //out.print("成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //验证验证码
    @RequestMapping("/audit")
    public void audit(HttpServletResponse response, String mycode) {
        String code = ImageUtil.getCode();
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            if (mycode.equalsIgnoreCase(code)) {
                out.print(true);
            } else {
                out.print(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登录
    @RequestMapping("/login")
    public void login(HttpServletResponse resp, UserTb userTb, String DropExpiration, ModelMap map, HttpSession session) {
        resp.setContentType("text/html;charset=utf-8");
        try {
            UserTb u = userTbService.login(userTb);
            PrintWriter out = resp.getWriter();
            if (u == null) {
                out.print("<script>alert('用户名或密码不正确');location.href='login.jsp'</script>");
            } else {
                //存入session
                map.put("u1", u);
                Cookie c = new Cookie("uname", userTb.getUserName());
                if (DropExpiration.equals("Day")) {
                    c.setMaxAge(24 * 60 * 60);
                } else if (DropExpiration.equals("Month")) {
                    c.setMaxAge(24 * 60 * 60 * 31);
                } else if (DropExpiration.equals("Year")) {
                    c.setMaxAge(24 * 60 * 60 * 365);
                }
                resp.addCookie(c);
                //登录时间
                Date date = new Date();
                session.setAttribute("logintime", date);
                map.put("logintime", date);
                out.write("<script>location.href='index.jsp'</script>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
    }


    //查询专业学院的老师
    @RequestMapping(value = "/user/list/{m1}", method = RequestMethod.GET)
    @ResponseBody
    public List getlist(int did, int mid, @PathVariable("m1") String m1) {
        List list = new ArrayList();
        System.out.println("GET list    m1=" + m1);
        if ("getteacher".equals(m1)) {
            list = userTbService.findall(did, mid, "班主任");
            return list;
        }
        return list;
    }

    //===查询   user的GET请求
    @RequestMapping(value = "/user/{m1}", method = RequestMethod.GET)
    public void get(String oldpass, ModelMap map, HttpServletResponse resp,
                    @PathVariable("m1") String m1) {
        resp.setContentType("text/html;charset=utf-8");

        System.out.println("GET m1=" + m1);
        if ("checkpass".equals(m1)) {
            //展示修改页面
            UserTb userTb = (UserTb) map.get("u1");
            try {
                PrintWriter out = resp.getWriter();
                if (userTb.getUserPs().equals(oldpass)) {
                    //out.print("密码正确");
                    out.print(true);
                } else {
                    //out.print("密码错误");
                    out.print(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "user/string/{m1}", method = RequestMethod.GET)
    public String get(@PathVariable String m1, ModelMap map,UserTb userTb,Integer roleid,
                      @RequestParam(defaultValue = "1") int pageindex,
                      @RequestParam(defaultValue = "5") int pagesize) {
        System.out.println("GET user    string  m1=" + m1);
        if ("getallusers".equals(m1)) {
            PageInfo pi = userTbService.getall_role(pageindex, pagesize);
            map.put("upi", pi);
            return "/power/user/list";
        } else if ("showoneuser_edit".equals(m1)) {
            //展示修改页面
            UserTb userTb1 = userTbService.selectByPrimaryKey(userTb.getUserId());
            Role role = roleService.selectByPrimaryKey(roleid);
            List<Role> roles = roleService.getallrole2();
            map.put("userTb", userTb1);
            map.put("role", role);
            map.put("roles", roles);
            return "/power/user/edit";
        } else if ("showoneuser_add".equals(m1)) {
            //展示新增页面
            List<Role> roles = roleService.getallrole2();
            map.put("roles", roles);
            return "/power/user/add";
        }
        return null;
    }

    //新增
    @RequestMapping(value = "/user/string/{m1}", method = RequestMethod.POST)
    public String add(@PathVariable String m1 , UserTb userTb) {
        System.out.println("POST    user    string  m1="+m1);
        if ("adduser".equals(m1)) {
            System.out.println("1111");
            System.out.println(userTb.getUserName()+"--"+userTb.getUserRealname());
            userTbService.insertSelective(userTb);
            return "redirect:/user/string/getallusers";
        }
        return null;
    }

    //===更新   user的PUT请求
    @RequestMapping(value = "/user/{m1}", method = RequestMethod.PUT)
    public void update(UserTb userTb, HttpServletResponse resp, ModelMap map,
                       @PathVariable("m1") String m1, SessionStatus status) {
        resp.setContentType("text/html;charset=utf-8");
        System.out.println("PUT usertb  void    m1=" + m1);
        UserTb u1 = (UserTb) map.get("u1");
        //修改个人信息--------------------------
        if ("updateinfo".equals(m1)) {
            System.out.println(userTb.getUserRealname());
            int k = userTbService.updateByPrimaryKeySelective(userTb);
            try {
                PrintWriter out = resp.getWriter();
                if (k > 0) {
                    System.out.println(userTb.getUserRealname());
                    UserTb userTb1 = userTbService.selectByPrimaryKey(userTb.getUserId());
                    //将角色信息存入新的user里
                    userTb1.setRole(u1.getRole());
                    map.put("u1", userTb1);
                    out.print(true);
                } else {
                    out.print(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("updateuser".equals(m1)) {
            //管理员修改用户信息
            userTbService.updateByPrimaryKeySelective(userTb);
            PrintWriter out = null;
            try {
                out = resp.getWriter();
                out.write("<script>alert('修改成功');location.href='/right.jsp'</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if ("updatepass".equals(m1)) {
            //修改密码--------------------------
            int k = userTbService.updateByPrimaryKeySelective(userTb);
            try {
                PrintWriter out = resp.getWriter();
                if (k > 0) {
                    status.setComplete();
                    out.print("<script>alert('修改成功，请重新登录');top.location.href='/login.jsp'</script>");
                } else {
                    out.print("<script>alert('修改失败');location.href='password.jsp'</script>");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/user/void/{m1}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String m1,UserTb userTb,HttpServletResponse response) {
        System.out.println("DELETE  user    void    m1="+m1);
        if ("deluser".equals(m1)) {
            try {
                PrintWriter out = response.getWriter();
                int k = userTbService.deleteByPrimaryKey(userTb.getUserId());
                if (k > 0) {
                    out.print(true);
                }else {
                    out.print(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
