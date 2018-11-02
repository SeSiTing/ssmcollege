package com.web;

import com.bean.Menu;
import com.github.pagehelper.PageInfo;
import com.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/menu/string/{m1}", method = RequestMethod.GET)
    public String get(@PathVariable String m1, ModelMap map, Menu menu,
                      @RequestParam(defaultValue = "1") int pageindex,
                      @RequestParam(defaultValue = "5") int pagesize) {
        System.out.println("GET menu    string  m1=" + m1);
        if ("showmenus".equals(m1)) {
            PageInfo pageInfo = menuService.getallFS(pageindex, pagesize);
            map.put("pi", pageInfo);
            return "/power/menu/list";
        } else if ("showonemenu_info".equals(m1)) {
            //显示详情
            Menu menu1 = menuService.selectByPrimaryKey(menu.getMenuid());
            if (menu.getUpmenuid() == -1) {
                map.put("upmenuname", "顶级菜单");
            } else {
                Menu upmenu = menuService.selectByPrimaryKey(menu.getUpmenuid());
                map.put("upmenuname", upmenu.getMenuname());
            }
            map.put("menu", menu1);
            return "/power/menu/info";
        } else if ("showonemenu_edit".equals(m1)) {
            //显示修改页面
            Menu menu1 = menuService.selectByPrimaryKey(menu.getMenuid());
            List<Menu> list = menuService.getallF();
            map.put("menu", menu1);
            map.put("flist", list);
            return "/power/menu/edit";
        } else if ("showaddmenu".equals(m1)) {
            //显示新增页面
            List<Menu> list = menuService.getallF();
            map.put("flist", list);
            return "/power/menu/add";
        }
        return null;
    }

    @RequestMapping(value = "/menu/string/{m1}", method = RequestMethod.POST)
    public String post(@PathVariable String m1, Menu menu) {
        System.out.println("POST    menu    string  m1=" + m1);
        if ("addmenu".equals(m1)) {
            //新增菜单
            System.out.println(menu.getUpmenuid());
            System.out.println(menu.getMenuid() + "---" + menu.getMenuname() + "---" + menu.getMenupath());
            menuService.insert(menu);
            return "redirect:/menu/string/showmenus";
        }

        return null;
    }

    @RequestMapping(value = "/menu/string/{m1}", method = RequestMethod.PUT)
    public String put(@PathVariable String m1, Menu menu) {
        System.out.println("PUT menu    string  m1=" + m1);
        if ("updatemenu".equals(m1)) {
            //更新菜单
            int k = menuService.updateByPrimaryKeySelective(menu);
            return "redirect:/menu/string/showmenus";
        }
        return null;
    }

    @RequestMapping(value = "/menu/void/{m1}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String m1, int menuid, HttpServletResponse response) {
        System.out.println("DELETE  menu    void    m1=" + m1);
        if ("delmenu".equals(m1)) {
            //删除菜单
            int k = menuService.deleteByPrimaryKey(menuid);
            response.setContentType("text/html;charset=utf-8");
            try {
                PrintWriter out = response.getWriter();
                if (k > 0) {
                    out.print(true);
                } else {
                    out.print(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/menu/string/{m1}", method = RequestMethod.DELETE)
    public String delall(@PathVariable String m1 ,String [] menuids) {
        System.out.println("DELETE  menu    string    m1=" + m1);
         if ("delall".equals(m1)) {
            //批量删除
             System.out.println(Arrays.toString(menuids));
             menuService.delbyids(menuids);
             return "redirect:/menu/string/showmenus";
        }
        return null;
    }


}
