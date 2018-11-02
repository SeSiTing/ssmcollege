package com.web;

import com.bean.Leavebill;
import com.bean.UserTb;
import com.service.ActivitiService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.registry.infomodel.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class ActivitiController {

    @Autowired
    private ActivitiService activitiService;

    //1 查询流程定义和部署信息
    @RequestMapping("/bushu/getdeploys")
    public String chaxun(ModelMap map) {
        //查询部署信息
        List<Deployment> dlist = activitiService.getdeplist();
        //查询流程定义信息
        List<ProcessDefinition> prolist = activitiService.getprolist();
        System.out.println(prolist.get(0).getName());
        map.put("dlist", dlist);
        map.put("prolist", prolist);
        return "/bushu/list";
    }

    //2 上传流程文件
    @RequestMapping("/bushu/adddeploy")
    public String up(MultipartFile depfile, String name) {
        try {
            activitiService.add(depfile.getInputStream(), name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/bushu/getdeploys";
    }

    //3 查看流程图------保存到本地
    @RequestMapping("/bushu/lookimage")
    public void liuchentu(String depid, String imagename, HttpServletResponse response) {

        try {
            //根据部署id和图片名称，查看文件流
            InputStream inputStream = activitiService.findimage(depid, imagename);
            //将文件保存到本地
            /*FileUtils.copyInputStreamToFile(inputStream, new File("d://" + imagename));
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<script>alert('文件保存在d盘,文件名:"+imagename+"');location.href='/bushu/getdeploys'</script>");
*/
            OutputStream outputStream = response.getOutputStream();
            int k = -1;
            while ((k = inputStream.read()) != -1) {
                outputStream.write(k);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //3 查看流程图------在页面展示图片
    @RequestMapping("/bushu/image")
    public String image(String depid,String imagename,ModelMap map){
        map.put("depid", depid);
        map.put("imagename", imagename);
        return "/bushu/image";
    }


    //4 删除流程部署
    @RequestMapping("/bushu/deletedeploy")
    public void delete(String depid, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            activitiService.deletedeploy(depid);
            response.getWriter().print("<script>location.href='/bushu/getdeploys'</script>");
        } catch (IOException e) {
            try {
                response.getWriter().print("<script>alert('流程正在使用，不能删除');location.href='/bushu/getdeploys'</script>");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //5 提交请假申请
    @RequestMapping("/qingjia/tijiao")
    public String qingjia(int leaveid, HttpSession session){//请假条ID值
        UserTb userTb = (UserTb) session.getAttribute("u1");
        activitiService.qinjia(leaveid, userTb.getUserName());
        return "redirect:/qingjia/getleavebills";
    }

    //6 查询个人任务
    @RequestMapping("/renwu/gettastlist")
    public String renwu(HttpSession session, ModelMap map) {
        UserTb userTb = (UserTb) session.getAttribute("u1");
        List<Task> list = activitiService.gettasks(userTb.getUserName());
        map.put("tlist", list);
        return "/renwu/list";
    }

    //7 查询具体任务
    @RequestMapping("/renwu/banli")
    public String chakan(String taskid) {
        //使用form_key执行流程的请求
        String url = activitiService.getformkey(taskid);
        System.out.println(taskid);
        System.out.println(url);
        return "redirect:/"+url+"?tid="+taskid;
    }

    //8 展示审批页面
    @RequestMapping("/shenpi")
    public String shenpi(String tid, ModelMap map) {
        //1.通过任务id查询请假条
        Leavebill leavebill = activitiService.findleavebillbystaskid(tid);
        map.put("leavebill", leavebill);
        //2.通过任务id，查询正在活动的节点对象，并获得任务的连线信息
        List<String> strs = activitiService.getnames(tid);
        map.put("names", strs);
        //3.保存任务id
        map.put("taskid", tid);
        //4.得到任务的批注信息
        List<Comment> comments = activitiService.getcomments(tid);
        map.put("allcomments", comments);
        return "/renwu/banli";
    }


    //9 审批任务
    @RequestMapping("/renwu/shenpi")
    public String chulirenwu(String taskid, String pizhu,
                             String result, int leaveid,
                             HttpSession session) {
        UserTb userTb = (UserTb) session.getAttribute("u1");
        //审批任务
        activitiService.chuli(taskid,pizhu,result,leaveid,userTb.getUserName());
        return "redirect:/renwu/gettastlist";
    }



}
