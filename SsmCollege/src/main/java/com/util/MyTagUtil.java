package com.util;

import com.bean.UserTb;
import com.github.pagehelper.PageInfo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class MyTagUtil extends SimpleTagSupport {
    private String url;//请求地址

    private PageInfo pageInfo;//保存分页所需的数据

    private boolean test;

    private UserTb yonghu;

    public UserTb getYonghu() {
        return yonghu;
    }

    public void setYonghu(UserTb yonghu) {
        this.yonghu = yonghu;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override//定义希望标签做的事情
    public void doTag() throws JspException, IOException {
        //直接给页面输出内容
        JspWriter out = this.getJspContext().getOut();
        out.print("这是我的自定义标签");
        //获得标签对中间的内容并输出
        StringWriter sw = new StringWriter();
        this.getJspBody().invoke(sw);//body获得信息
        this.getJspContext().getOut().print(sw+"----");//context输出信息
        this.getJspContext().getOut().print(test+"--");
        //super.doTag();
    }
}
