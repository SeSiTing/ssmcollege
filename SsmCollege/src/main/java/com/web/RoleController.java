package com.web;

import com.bean.Menu;
import com.bean.Role;
import com.github.pagehelper.PageInfo;
import com.service.MenuService;
import com.service.RoleService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    //查询所有角色
    @RequestMapping(value = "/role/string/{m1}", method = RequestMethod.GET)
    public String list(@RequestParam(defaultValue = "1") int pageindex,
                       @RequestParam(defaultValue = "5") int pagesize,
                       ModelMap map, @PathVariable String m1, Role role
            , int[] menu) {
        System.out.println("GET role    string  m1=" + m1);
        //查询所有角色
        if ("getallroles".equals(m1)) {
            PageInfo pageInfo = roleService.getallrole(pageindex, pagesize);
            map.put("rpage", pageInfo);
            return "/power/role/list";
            //查询所有菜单
        } else if ("getallmenus".equals(m1)) {
            List list = menuService.getall();
            map.put("menulist", list);
            return "/power/role/add";
            //查询单个角色信息
        } else if ("showonerole_info".equals((m1))) {
            Role role1 = roleService.getonerole(role.getRoleid());
            map.put("role", role1);
            return "/power/role/info";
        } else if ("showonerole_edit".equals(m1)) {
            Role role1 = roleService.selectByPrimaryKey(role.getRoleid());
            List<Menu> menus = menuService.getall();
            map.put("allmenus", menus);
            map.put("role", role1);
            return "/power/role/edit";
        }
        return null;
    }

    @RequestMapping(value = "/role/string/{m1}", method = RequestMethod.POST)
    public String post(@PathVariable String m1, Role role,
                       int[] menu) {
        System.out.println("POST    role    string  m1=" + m1);
        //新增角色
        if ("addrole".equals(m1)) {
            roleService.addrole(role, menu);
            return "redirect:/role/string/getallroles";
        }
        return null;
    }

    @RequestMapping(value = "role/string/{m1}", method = RequestMethod.PUT)
    public String put(@PathVariable String m1,Role role,int[] menu) {
        System.out.println("PUT role    string    m1=" + m1);
        if ("updaterole".equals(m1)) {
            System.out.println(Arrays.toString(menu));
            roleService.update(role,menu);
            return "redirect:/role/string/getallroles";
        }
        return null;
    }



    @RequestMapping(value = "/role/void/{m1}", method = RequestMethod.PUT)
    public void put(@PathVariable String m1, Role role, HttpServletResponse response, String sta) {
        response.setContentType("text/html;charset=utf-8");
        System.out.println("PUT role    void    m1=" + m1);
        if ("changestate".equals(m1)) {
            try {
                PrintWriter out = response.getWriter();
                if ("启用".equals(sta)) {
                    role.setRolestate(0);
                    roleService.updateByPrimaryKeySelective(role);
                    out.print("禁用");
                } else {
                    role.setRolestate(1);
                    roleService.updateByPrimaryKeySelective(role);
                    out.print("启用");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "/role/void/{m1}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String m1, Role role, HttpServletResponse response) {
        System.out.println("DELETE  role void m1=" + m1);
        response.setContentType("text/html;charset=utf-8");
        if ("delrole".equals(m1)) {
            try {
                System.out.println(role.getRoleid());
                int k = roleService.deleterole(role.getRoleid());
                System.out.println(k);
                PrintWriter out = response.getWriter();
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
