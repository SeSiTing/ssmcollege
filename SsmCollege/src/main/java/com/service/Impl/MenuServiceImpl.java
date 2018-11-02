package com.service.Impl;

import com.bean.Menu;
import com.dao.MenuMapper;
import com.dao.MiddleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MiddleMapper middleMapper;

    @Override
    public int deleteByPrimaryKey(Integer menuid) {
        //先查看被删除的ID是否还有二级目录
        int k = menuMapper.findbyupid(menuid);
        if (k > 0) {
            return 0;
        }
        //先查看是否有角色在使用该菜单
        int count = middleMapper.selcountbymid(menuid);
        if (count > 0) {
            //如果有角色在使用先删中间表
            middleMapper.deletemiddlebymid(menuid);
        }
        //最后删菜单表
        return menuMapper.deleteByPrimaryKey(menuid);
    }

    //新增菜单
    @Override
    public int insert(Menu record) {
        return menuMapper.insert(record);
    }

    @Override
    public int insertSelective(Menu record) {
        return 0;
    }

    @Override
    public Menu selectByPrimaryKey(Integer menuid) {
        return menuMapper.selectByPrimaryKey(menuid);
    }

    @Override
    public int updateByPrimaryKeySelective(Menu record) {
        return menuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Menu record) {
        return 0;
    }

    @Override
    public List<Menu> findbyroleid(int roleid) {
        return menuMapper.findbyroleid(roleid);
    }

    @Override
    public List<Menu> getall() {
        List<Menu> list = menuMapper.getall();
        //菜单分级
        List newlist = new ArrayList();//分级之后的menu
        for (Menu menu : list) {
            if (menu.getUpmenuid() == -1) {
                List menus = new ArrayList();
                for (Menu menu2 : list) {
                    if (menu2.getUpmenuid() == menu.getMenuid()) {
                        menus.add(menu2);
                    }
                }
                menu.setSeconds(menus);
                newlist.add(menu);
            }
        }
        return newlist;
    }

    @Override
    public PageInfo getallFS(int pageindex, int pagesize) {
        PageHelper.startPage(pageindex, pagesize);
        List<Menu> list = menuMapper.getall();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public List<Menu> getallF() {
        return menuMapper.getallF();
    }

    @Autowired
    private DataSourceTransactionManager tx;

    //批量删除
    @Override
    @Transactional
    public int delbyids(String[] ids) {
        DefaultTransactionDefinition ddf = new DefaultTransactionDefinition();
        TransactionStatus status = tx.getTransaction(ddf);
        try {
            //1.先将被删除的菜单分类
            List<Integer> first = new ArrayList<>();//保存一级目录
            List<Integer> second = new ArrayList();//保存二级目录
            for (String id :
                    ids) {
                int index = id.indexOf("-");
                int upid = Integer.parseInt(id.substring(index + 1));
                int mid = Integer.parseInt((id.substring(0, index)));
                if (upid == -1) {
                    first.add(mid);
                } else {
                    second.add(mid);
                }
            }
            //2.先删除二级菜单，然后再判断一级菜单
            if (first.size() == ids.length) {//全是一级菜单的情况
                System.out.println("全是一级菜单");
                //对应一级菜单中的二级全都不存在，可以删除
                for (Integer id :
                        first) {
                    int k = menuMapper.findbyupid(id);
                    if (k > 0) {
                        //不能删
                        System.out.println("一级目录下有二级目录  不能删");
                        throw new Exception("aaa");
                    } else {
                        //可以删除
                        System.out.println("可以删:" + id);
                        int a = menuMapper.deleteByPrimaryKey(id);
                        System.out.println(a);
                    }
                }
            } else {
                //包含了二级菜单
                if (second.size() == ids.length) {//全是二级菜单
                    System.out.println("全是二级菜单----:" + second.get(0));
                    menuMapper.delbyids(second);
                } else {
                    System.out.println("先删除二级菜单");
                    //先删除二级菜单
                    menuMapper.delbyids(second);
                    //然后判断一级菜单是否还有子菜单
                    for (Integer mid :
                            first) {
                        int k = menuMapper.findbyupid(mid);
                        if (k > 0) {
                            System.out.println("不能删除");
                            //不能删除
                            throw new Exception("aaa");
                        } else {
                            menuMapper.deleteByPrimaryKey(mid);
                        }
                    }
                }
            }
            tx.commit(status);//提交事务
        } catch (Exception e) {
            //e.printStackTrace();
            tx.rollback(status);//事务回滚
        }
        return 0;
    }
}
