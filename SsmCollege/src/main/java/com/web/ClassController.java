package com.web;

import com.bean.Classes;
import com.bean.Department;
import com.bean.Major;
import com.bean.UserTb;
import com.dao.DepartmentMapper;
import com.dao.UserTbMapper;
import com.github.pagehelper.PageInfo;
import com.service.ClassService;
//import com.util.ExcelUtil;
import com.service.DepartmentService;
import com.service.MajorService;
import com.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ClassController {
    @Autowired
    private ClassService classService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MajorService majorService;
    //===查询  Class  string GET
    //展示页面
    //展示审核页面-----------------


    @RequestMapping(value = "/classes/string/{m1}", method = RequestMethod.GET)
    public String get(Classes cla, Integer deptid,Integer majorid,
                      @RequestParam(value = "pageindex", defaultValue = "1") int pageindex,
                      ModelMap map, @PathVariable("m1") String m1,
                      @RequestParam(value = "pagesize", defaultValue = "5") int pagesize) {
        System.out.println("GET classes string  m1=" + m1);
        //展示页面-----------------
        if ("show".equals(m1)) {
            PageInfo pageInfo = classService.getall(cla.getClassname(), cla.getClassnum(), pageindex, pagesize, null, null);
            pageInfo.setPageSize(pagesize);
            map.put("pi", pageInfo);
            map.put("cname", cla.getClassname());
            map.put("cnum", cla.getClassnum());
            map.put("pagesize", pagesize);
            return "/Educational/class/list";
        }
        //展示审核页面-----------------
        else if ("showaudit".equals(m1)) {
            PageInfo pageInfo = classService.getall(cla.getClassname(), cla.getClassnum(), pageindex, pagesize, null, "审核中");
            map.put("pi", pageInfo);
            map.put("cname", cla.getClassname());
            map.put("cnum", cla.getClassnum());
            map.put("pagesize", pagesize);
            return "/Educational/Auditing";
        }
        //查询单个班级  详情页面
        else if ("showone".equals(m1)) {
            Classes oneclass = classService.selectByPrimaryKey(cla.getClassid());
            map.put("oneclass", oneclass);
            return "Educational/class/info";
        }
        //查询单个班级  更新页面 显示信息
        else if ("showone_update".equals(m1)) {
            Classes oneclass = classService.selectByPrimaryKey(cla.getClassid());
            List<Department> list = departmentService.findall();
            Department department = departmentService.selectByPrimaryKey(deptid);
            List<Major> mlist = department.getMajor();
            Major major = majorService.selectByPrimaryKey(majorid);
            List<UserTb> ulist = major.getUserTb();
            map.put("mlist", mlist);
            map.put("dlist", list);
            map.put("ulist",ulist);
            map.put("oneclass", oneclass);
            return "Educational/class/edit";
        }
        return null;
    }

    //===查询  Class  void GET
    //导出excel
    @RequestMapping(value = "/classes/void/{m1}", method = RequestMethod.GET)
    public void get(int[] single, HttpServletResponse resp, @PathVariable("m1") String m1) {
        System.out.println("GET classes void    m1=" + m1);
        //导出excel--------------------
        if ("daochu".equals(m1)) {
            PageInfo pg = classService.getall(null, null, 0, 0, single, null);
            List<Classes> list = pg.getList();

            ExcelUtil.headers = new String[]{"院系", "班级编号", "班级名称", "班主任老师", "人数", "班级状态"};
            ExcelUtil.createhead(ExcelUtil.headers.length);
            ExcelUtil.createother(list);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String date = simpleDateFormat.format(new Date());
            FileOutputStream out = null;
            try {
                out = new FileOutputStream("d:\\class" + date + ".xls");
                ExcelUtil.export(out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resp.setContentType("text/html;charset=utf-8");
            try {
                PrintWriter out2 = resp.getWriter();
                out2.print("<script>alert('导出成功');location.href='/classes/string/show'</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //查询专业学院的班级
    @RequestMapping(value = "/classes/list/{m1}", method = RequestMethod.GET)
    @ResponseBody
    public List getlist(int did, int mid, @PathVariable("m1") String m1) {
        List<Classes> list = new ArrayList();
        System.out.println("GET list    m1=" + m1);
        if ("getclasses".equals(m1)) {
            System.out.println("--" + did + "--" + mid);
            Map map = new HashMap();
            map.put("deptid", did);
            map.put("majorid", mid);
            list = classService.getall(map);
            System.out.println(list.get(0).getClassname());
            return list;
        }
        return null;
    }


    //新增   Class  POST
    //新增班级
    @RequestMapping(value = "/classes/string/{m1}", method = RequestMethod.POST)
    public String add(Classes classes, @PathVariable("m1") String m1) {
        System.out.println("POST    classes string  m1=" + m1);
        //新增班级-------------------------
        if ("addclass".equals(m1)) {
            classes.setAuditcount(0);
            classes.setClassstate("未审核");
            classService.insert(classes);
            return "redirect:/classes/string/show";
        }
        return null;
    }


    //更新    Class   PUT
    //提交审核
    //审核
    @RequestMapping(value = "/classes/void/{m1}", method = RequestMethod.PUT)
    public void update(HttpServletResponse response, @PathVariable String m1
            , int classid, int auditcount, int audit) {
        response.setContentType("text/html;charset=utf-8");
        Classes classes = new Classes();
        System.out.println("PUT classes void    m1=" + m1);
        //提交审核---------------
        if ("submitview".equals(m1)) {
            classes.setClassstate("审核中");
            classes.setAuditcount(auditcount + 1);
            classes.setClassid(classid);
            int k = classService.updateByPrimaryKeySelective(classes);
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
            //审核-----------------------
        } else if ("auditing".equals(m1)) {
            System.out.println("auditing" + audit);
            classes.setClassid(classid);
            if (audit == 1) {
                classes.setClassstate("审核通过");
            } else if (audit == 2) {
                classes.setClassstate("未审核");
            }
            int k = classService.updateByPrimaryKeySelective(classes);
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

    //更新    Class   PUT
    //更新班级信息

    @RequestMapping(value = "/classes/string/{m1}", method = RequestMethod.PUT)
    public String put(@PathVariable String m1, Classes classes) {
        System.out.println("PUT classes string    m1=" + m1);
        if ("updateoneclass".equals(m1)) {
            //System.out.println(classes.getClassteacher());
            int k = classService.updateByPrimaryKeySelective(classes);
            if (k > 0) {
                return "redirect:/classes/string/show";
            } else {
                return "redirect:/classes/string/show";
            }
        }
        return null;
    }

    //删除    Class     DELETE
    //删除班级
    @RequestMapping(value = "/classes/void/{m1}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String m1,Integer classid,HttpServletResponse response) {
        int k = classService.deleteByPrimaryKey(classid);
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            if (k>0){
                out.print(true);
            }else {
                out.print(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
