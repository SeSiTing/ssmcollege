<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>学生信息管理平台</title>
    <link href="../../Style/StudentStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../../Script/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css"/>
    <link href="../../Style/ks.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/mine.css" type="text/css" rel="stylesheet">
    <script src="../../Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
    <script src="../../Script/jBox/jquery.jBox-2.3.min.js"
            type="text/javascript"></script>
    <script src="../../Script/jBox/i18n/jquery.jBox-zh-CN.js"
            type="text/javascript"></script>
    <script src="../../Script/Common.js" type="text/javascript"></script>
    <script src="../../Script/Data.js" type="text/javascript"></script>

    <script>
        function del() {
            confirm("确认删除？");
        }

    </script>


</head>
<body>

<div class="div_head" style="width: 100%;text-align:center;">
		<span> <span style="float:left">当前位置是：教务中心-》班级审核</span> <span
                style="float:right;margin-right: 8px;font-weight: bold">
		</span>
		</span>
</div>

<div class="cztable">
    <div>

        <ul class="seachform1">
            <form action="/classes/string/showaudit" method="get">
                <input type="hidden" name="pagesize" value="${pagesize}"/>
                <li>
                    <label>班级编号</label>
                    <input name="classnum" type="text" class="scinput1" value="${cnum}"/>&nbsp;&nbsp;
                    <label>班级名称</label>
                    <input name="classname" type="text" class="scinput1" value="${cname}"/>&nbsp;&nbsp;
                    <input type="submit" class="scbtn" value="查询"/>&nbsp;
                </li>

            </form>
        </ul>
        <br/>

        <table class="table_a" border="1" width="100%">
            <tbody>
            <tr style="font-weight: bold;">
                <td align="center">序号</td>
                <td>院系</td>
                <td width="">班级编号</td>
                <td width="">班级名称</td>
                <td width="15%">班主任老师</td>
                <td width="15%">人数</td>
                <td width="15%">班级状态</td>
                <td width="15%">操作</td>
            </tr>

            <c:forEach items="${pi.list}" var="cla">

                <tr id="product1">
                    <td width="8%" align="center">
                        <input type="checkbox" value="${cla.classid}" name="single"/>
                    </td>
                    <td align="center">${cla.department.departname}</td>
                    <td align="center">${cla.classnum}</td>
                    <td align="center">${cla.classname}</td>
                    <td align="center">${cla.classteacher}</td>
                    <td align="center">${cla.peoplecount}</td>
                    <td align="center">
                        <c:if test="${cla.auditcount==1}">初次审核</c:if>
                        <c:if test="${cla.auditcount>=2}">复审</c:if>
                    </td>
                    <td id="${cla.classid}" align="center">
                        <a name="submitview" style="color:blue;cursor: pointer"
                           onclick="pass(${cla.classid},${cla.auditcount}) ">通过</a>
                        <a name="submitview" style="color:blue;cursor: pointer"
                           onclick="reject(${cla.classid},${cla.auditcount}) ">驳回</a>

                    </td>
                </tr>


            </c:forEach>

            <script type="text/javascript">
                function pass(classid, auditcount) {
                    var audit = 1;
                    $.ajax({
                        url: "/classes/void/auditing",
                        data: {classid: classid, _method: "put", auditcount: auditcount, audit: audit},
                        type: "post",
                        dataType: "text",
                        success: function (rs) {
                            if (rs == "true") {
                                $("#" + classid).html("已通过");
                            }
                        }
                    });
                }
            </script>
            <script type="text/javascript">
                function reject(classid, auditcount) {
                    var audit = 2;
                    $.ajax({
                        url: "/classes/void/auditing",
                        data: {classid: classid, _method: "put", auditcount: auditcount, audit: audit},
                        type: "post",
                        dataType: "text",
                        success: function (rs) {
                            if (rs == "true") {
                                $("#" + classid).html("已驳回");
                            }
                        }
                    });
                }
            </script>


            <tr>
                <td colspan="20" style="text-align: center;">
                    <a style="text-decoration: none;" href="#">
                        <a href="/classes/string/showaudit?classname=${cname}&classnum=${cnum}&pageindex=1&pagesize=${pagesize}">首页</a>
                        <a href="/classes/string/showaudit?classname=${cname}&classnum=${cnum}&pageindex=${pi.prePage==0?1:pi.prePage}&pagesize=${pagesize}">上一页</a>
                        <c:forEach var="i" begin="1" end="${pi.pages}">
                            <a href="/classes/string/showaudit?classname=${cname}&classnum=${cnum}&pageindex=${pi.prePage}&pagesize=${pagesize}">${i}</a>
                        </c:forEach>
                        <a href="/classes/string/showaudit?classname=${cname}&classnum=${cnum}&pageindex=${pi.nextPage==0?pi.pages:pi.nextPage}&pagesize=${pagesize}">下一页</a>
                        <a href="/classes/string/showaudit?classname=${cname}&classnum=${cnum}&pageindex=${pi.pages}&pagesize=${pagesize}">尾页</a>
                        共${pi.total}条
                        每页显示
                        <select name="num">
                            <option value="5" <c:if test="${pagesize==5}">selected</c:if>>5</option>
                            <option value="10" <c:if test="${pagesize==10}">selected</c:if>>10</option>
                            <option value="15" <c:if test="${pagesize==15}">selected</c:if>>15</option>
                        </select>
                        ${pi.pageNum}/${pi.pages}

                    </a>
                </td>


                <script type="text/javascript">
                    $(function () {
                        $("[name=num]").change(function () {
                            var pagesize = $(this).val();
                            location.href = "/classes/string/showaudit?pagesize=" + pagesize;
                        });
                    })
                </script>
            </tbody>
        </table>
    </div>

</div>
</div>

</div>
</body>
</html>
