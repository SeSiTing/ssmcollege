package com.service.Impl;

import com.bean.Leavebill;
import com.service.ActivitiService;
import com.service.LeaveBillService;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private LeaveBillService leaveBillService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;


    //上传流程文件
    @Override
    public int add(InputStream in, String name) {
        System.out.println(name);
        ZipInputStream zip = new ZipInputStream(in);
        repositoryService.createDeployment()
                .addZipInputStream(zip)
                .name(name)
                .deploy();
        return 1;
    }

    //查询部署信息
    @Override
    public List<Deployment> getdeplist() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        return list;
    }

    //查询流程定义
    @Override
    public List<ProcessDefinition> getprolist() {
        List list = repositoryService.createProcessDefinitionQuery().list();
        return list;
    }

    //查看流程图
    @Override
    public InputStream findimage(String depid, String imagename) {
        return repositoryService.getResourceAsStream(depid, imagename);
    }

    //删除
    @Override
    public int deletedeploy(String depid) {
        repositoryService.deleteDeployment(depid);
        return 1;
    }

    //请假
    @Override
    @Transactional //如何将业务过程和流程定义进行关联
    public int qinjia(int leaveid, String username) {


        Leavebill leavebill = new Leavebill();

        //1.修改请假单的状态
        leavebill.setId(leaveid);
        leavebill.setState(1);
        leaveBillService.updateByPrimaryKeySelective(leavebill);
        //2.启动流程实例时候    需要建立流程和业务之间的关系
        //使用business_key来保存业务的信息
        //business_key的组成方式，流程定义的key+业务的id
        //task(executionid)---execution(business_key)---procdef
        String business = "Leavebill." + leaveid;
        Map map = new HashMap();
        map.put("busid", business);//业务的key值
        map.put("username", username);//请假人的名字
        //key值为  bpmn的id值  应与 实体类名一致
        // 流程定义key值   业务的id   流程变量的值
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("Leavebill", business, map);
        //查询流程实例的ID查询任务对象
        Task task =
                taskService.createTaskQuery()
                        .processInstanceId(instance.getId()).singleResult();
        //完成个人任务
        taskService.complete(task.getId());
        return 1;


    }

    //查询个人任务
    @Override
    public List<Task> gettasks(String username) {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(username).list();
        return list;
    }

    //获得流程图中的form_key
    @Override
    public String getformkey(String tid) {
        TaskFormData data = formService.getTaskFormData(tid);
        return data.getFormKey();
    }

    //通过任务id查询请假单
    @Override
    public Leavebill findleavebillbystaskid(String id) {
        //通过任务id查询任务对象
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        //通过task中得到的流程实例id 来查询流程实例对象
        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery()
                        .processInstanceId(task.getProcessInstanceId())
                        .singleResult();
        //通过流程实例对象得到 business_key
        String str = processInstance.getBusinessKey();
        //截取str  得到uid
        String uid = str.substring(str.indexOf(".") + 1);
        //通过uid查询请假单对象
        Leavebill leavebill = leaveBillService.selectByPrimaryKey(Integer.parseInt(uid));
        return leavebill;
    }

    //获得连线信息
    @Override
    public List<String> getnames(String tid) {
        List names = new ArrayList();
        //通过任务id 查询任务对象
        Task task = taskService.createTaskQuery().taskId(tid).singleResult();
        //得到流程定义id    区分DEF流程定义   INST流程实例
        String processDefinitionId = task.getProcessDefinitionId();
        //通过流程定义ID得到流程图对象信息
        //ProcessDefinitionEntity entity = (ProcessDefinitionEntity)repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId);
        //上面这行代码可以通过流程定义id来查询流程对应的对象  但得不到活动的信息
        ProcessDefinitionEntity entity = (ProcessDefinitionEntity)
                repositoryService
                        .getProcessDefinition(processDefinitionId);
        //得到当前任务正在活动的activiti_id
        String instanceId = task.getProcessInstanceId();//得到流程实例id
        ProcessInstance instance =//通过流程定义id查询流程实例
                runtimeService.createProcessInstanceQuery()
                        .processInstanceId(instanceId).singleResult();
        System.out.println(instanceId+"----"+instance);
        String act_id = instance.getActivityId();//得到正在活动的id
        //查找act对象 例如：表示的是主管审批这个元素
        ActivityImpl activity = entity.findActivity(act_id);
            //得到输出的两条线
        List<PvmTransition> list = activity.getOutgoingTransitions();
        if (list.size() > 0) {
            for (PvmTransition pvm : list
            ) {
                String name = (String) pvm.getProperty("name");
                System.out.println(name);
                names.add(name);
            }
        }
        return names;
    }

    @Override
    public void chuli(String taskid, String pizhu, String result, int leaveid, String username) {
        //1.添加批注信息表
        Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
        Authentication.setAuthenticatedUserId(username);
            //任务ID   流程实例ID    批注信息
        taskService.addComment(taskid, task.getProcessInstanceId(), pizhu);
        //2.设置流程变量的值
        Map map = new HashMap();
        map.put("rs", result);//bpmn流程图上有EL表达式接rs来判断
        //完成个人任务
        taskService.complete(taskid,map);
        if ("驳回".equals(result)) {
            //修改请假条状态
            Leavebill leavebill = new Leavebill();
            leavebill.setState(3);
            leavebill.setId(leaveid);
            leaveBillService.updateByPrimaryKeySelective(leavebill);
        }
        //3.判断任务是否执行完毕
            //查询流程实例对象
        ProcessInstance pro =
                runtimeService.createProcessInstanceQuery()
                        .processInstanceId(task.getProcessInstanceId())
                        .singleResult();
        if (pro == null) {
            //任务完成
            //修改请假条状态
            Leavebill leavebill = new Leavebill();
            leavebill.setState(2);
            leavebill.setId(leaveid);
            leaveBillService.updateByPrimaryKeySelective(leavebill);
        }
    }

    //得到各级批注
    @Override
    public List<Comment> getcomments(String tid) {
        List alllist = new ArrayList();
        //得到任务的流程实例id
        Task task = taskService.createTaskQuery().taskId(tid).singleResult();
        String instanceid = task.getProcessInstanceId();
        //查询历史信息，通过流程实例id查询该ID对应的历史任务
        //方法一
        /*List<HistoricTaskInstance> list =
                historyService.createHistoricTaskInstanceQuery()
                        .processInstanceId(instanceid).list();
        //判断是否有历史信息
        if (list.size() > 0) {
            for (HistoricTaskInstance taskInstance : list) {
                String taskid = taskInstance.getId();//得到历史任务id
                List list2 = taskService.getTaskComments(taskid);//得到任务的批注信息
                alllist.addAll(list2);
            }
        }*/

        //方法2
        alllist = taskService.getProcessInstanceComments(instanceid);


        return alllist;
    }
}
