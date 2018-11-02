package com.util;

import com.github.pagehelper.PageInfo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class FenyeTagUtil extends SimpleTagSupport {

    private String url;//请求地址
    private PageInfo pageInfo;//保存分页所需的数据

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

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out=this.getJspContext().getOut();
        StringBuffer stringBuffer=new StringBuffer();
        //首页  上一页
        stringBuffer.append("<a href='"+url+"&pageindex=1&pagesize="+pageInfo.getPageSize()+"'>首页</a>");
        stringBuffer.append("<a href='"+url+"&pageindex="+(pageInfo.getPrePage()==0?1:pageInfo.getPrePage())+"&pagesize="+pageInfo.getPageSize()+"'>上一页</a>");

        //页码
        for ( int i = 1;i<=pageInfo.getPages();i++) {
            stringBuffer.append("<a  href='" + url + "&pageindex=" + i + "&pagesize=" + pageInfo.getPageSize() + "'> "+i+" </a>");
        }

        //下一页  尾页
        stringBuffer.append("<a href='"+url+"&pageindex="+(pageInfo.getNextPage()==0?pageInfo.getPages():pageInfo.getNextPage())+"&pagesize="+pageInfo.getPageSize()+"'>下一页</a>");
        stringBuffer.append("<a href='"+url+"&pageindex="+pageInfo.getPages()+"&pagesize="+pageInfo.getPageSize()+"'>尾页</a>");
        stringBuffer.append("共"+pageInfo.getTotal()+"条");

        //每页显示条数
        stringBuffer.append("<select name='num'>");
        stringBuffer.append("<option value='5'"+(pageInfo.getPageSize()==5?"selected":"")+">5</option>");
        stringBuffer.append("<option value='10'"+(pageInfo.getPageSize()==10?"selected":"")+">10</option>");
        stringBuffer.append("<option value='15'"+(pageInfo.getPageSize()==15?"selected":"")+">15</option>");
        stringBuffer.append("</select>");



        out.print(stringBuffer.toString());
        //直接给页面输出内容
        /*JspWriter out = this.getJspContext().getOut();
        out.print("这是我的自定义标签");
        //获得标签对中间的内容并输出
        StringWriter sw = new StringWriter();
        this.getJspBody().invoke(sw);//body获得信息
        this.getJspContext().getOut().print(sw+"----");//context输出信息*/



    }
}
