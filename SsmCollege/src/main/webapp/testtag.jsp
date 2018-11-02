<%@ page import="com.bean.UserTb" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/22 0022
  Time: 上午 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="my" uri="http://java.sun.com/jsp/mytag/my" %>
<%@taglib prefix="fy" uri="http://java.sun.com/jsp/femye/fy" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>testtag.jsp</h1>
<% UserTb users=new UserTb();
users.setUserName("张三");
pageContext.setAttribute("u1",users);%>

<c:if test="${1<2}">
<my:abc test="(1<2)" yonghu="${u1}">464645</my:abc><br>

<my:abc yonghu="${u1}">55555</my:abc><br>
</c:if>
<my:abc test="${1<2}">668886</my:abc><br>
</body>
</html>
