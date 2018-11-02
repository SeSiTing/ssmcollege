package com.service.Impl;

import com.bean.Menu;
import com.bean.Role;
import com.bean.UserTb;
import com.dao.MenuMapper;
import com.dao.UserTbMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.MenuService;
import com.service.UserTbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserTbServiceImpl implements UserTbService {

    @Autowired
    private UserTbMapper userTbMapper;
    @Autowired
    private MenuService menuService;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return userTbMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(UserTb record) {
        return 0;
    }

    @Override
    public int insertSelective(UserTb record) {
        return userTbMapper.insertSelective(record);
    }

    @Override
    public UserTb selectByPrimaryKey(Integer userId) {
        return userTbMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(UserTb record) {
        System.out.println(record.getUserRealname()+"--"+record.getUserId());
        return userTbMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserTb record) {
        return userTbMapper.updateByPrimaryKey(record);
    }

    //登录
    @Override
    public UserTb login(UserTb userTb) {
        UserTb userTb1 = userTbMapper.login(userTb.getUserName());
        if (userTb1 != null && userTb1.getUserPs().equals(userTb.getUserPs())) {
            //获得角色的职位类
            Role role = userTb1.getRole();
            //根据用户的职位id查询所对应的菜单集合
            List<Menu> list = menuService.findbyroleid(role.getRoleid());
            //将所有菜单进行分类
            List fenjimenu = new ArrayList();
            for (Menu menu : list) {

                if (menu.getUpmenuid() ==-1) {
                    List newlist = new ArrayList();//只保存二级目录
                    for (Menu menu2 : list) {
                        if (menu2.getUpmenuid() == menu.getMenuid()) {
                            newlist.add(menu2);
                        }
                    }
                    menu.setSeconds(newlist);//将二级目录存入对应目录
                    fenjimenu.add(menu);//汇总所有一级目录
                }
            }
            //将分级完成后的菜单赋给角色
            role.setMenus(fenjimenu);
            //将角色赋给用户
            userTb1.setRole(role);

            userTb1.setLogincount(userTb1.getLogincount() + 1);
            userTbMapper.updateByPrimaryKeySelective(userTb1);
            //管理人
            UserTb manager = userTbMapper.selectByPrimaryKey(userTb1.getManagerid());
            userTb1.setManager(manager);
            return userTb1;
        }
        return null;
    }

    @Override
    public List findall(int did, int mid, String rolename) {
        Map map = new HashMap();
        map.put("did", did);
        map.put("mid", mid);
        map.put("rolename", rolename);
        return userTbMapper.findall(map);
    }

    @Override
    public PageInfo getall_role(int pageindex,int pagesize) {
        PageHelper.startPage(pageindex, pagesize);
        List<UserTb> list = userTbMapper.getall_role();
        PageInfo pi = new PageInfo(list);
        return pi;
    }
}
