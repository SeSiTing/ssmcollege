<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fy" uri="http://java.sun.com/jsp/femye/fy" %>
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
    <script src="../../Script/jBox/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <script src="../../Script/jBox/i18n/jquery.jBox-zh-CN.js" type="text/javascript"></script>
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
		<span> <span style="float:left">当前位置是：教务中心-》班级管理</span> <span
                style="float: right; margin-right: 8px; font-weight: lighter">
            <span onclick="todaochu()" style="color: blue;cursor:pointer ">【导出excel】&nbsp;&nbsp;</span>
            <a style="text-decoration: blink" href="/department/string/findalldepa">【新增班级】&emsp;&emsp;&emsp;&emsp;</a>
		</span>
		</span>
</div>

<div class="cztable">
    <div>

        <ul class="seachform1">
            <form action="/classes/string/show" method="get">
                <input type="hidden" name="pagesize" value="${pagesize}"/>
                <li>
                    <label>班级名称:</label>
                    <input name="classname" type="text" class="scinput1" value="${cname}"/>&nbsp;&nbsp;
                    <label>班级编号:</label>
                    <input name="classnum" type="text" class="scinput1" value="${cnum}"/>&nbsp;&nbsp;
                    <input type="submit" class="scbtn" value="查询"/>&nbsp;
                </li>

            </form>

            <script type="text/javascript">
                $(function () {
                    $("[name=all]").click(function () {
                        var singles = $("[name=single]");
                        for (var i = 0; i < singles.length; i++) {
                            singles[i].checked = $(this)[0].checked;
                        }
                    })
                })

                function todaochu() {
                    var singles = $("[name=single]");
                    var k = -1;
                    for (var i = 0; i < singles.length; i++) {
                        if (singles[i].checked == true) {
                            k = 1;
                            document.forms[1].submit();
                            break;
                        }
                    }
                    if (k == -1) {
                        alert("请先选择导出的班级");
                    }
                }
            </script>


            <form action="/classes/void/daochu" ,method="get">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr style="font-weight: bold;">
                        <th width="8%">
                            <input name="all" type="checkbox"/>
                        </th>
                        <th>院系</th>
                        <th width="">班级编号</th>
                        <th width="">班级名称</th>
                        <th width="15%">班主任老师</th>
                        <th width="15%">人数</th>
                        <th width="15%">班级状态</th>
                        <th width="20%">操作</th>
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
                            <td id="${cla.classid}1" align="center">${cla.classstate}</td>
                            <td id="${cla.classid}2" align="center">
                                <c:if test="${cla.classstate=='未审核'||cla.classstate=='审核未通过'}">
                                    <a href="/classes/string/showone?classid=${cla.classid}">详情</a>,
                                    <a href="/classes/string/showone_update?classid=${cla.classid}&deptid=${cla.department.departid}&majorid=${cla.majorid}">修改</a>,
                                    <a name="submitview" style="color:blue;cursor: pointer"
                                       onclick="submitview(${cla.classid},${cla.auditcount})">提交审核</a>
                                    ,<a style="color: blue;cursor: pointer" onclick="del(${cla.classid})">删除</a>
                                </c:if>
                                <c:if test="${cla.classstate=='审核中'||cla.classstate=='已毕业'}">
                                    <a href="/classes/string/showone?classid=${cla.classid}">详情</a>
                                </c:if>
                                <c:if test="${cla.classstate=='审核通过'}">
                                    发书, <a href="/classes/string/showone?classid=${cla.classid}">详情</a>
                                </c:if>
                            </td>
                        </tr>

                    </c:forEach>

                    <script type="text/javascript">
                        function submitview(classid, auditcount) {
                            var audit = 1;
                            $.ajax({
                                url: "/classes/void/submitview",
                                data: {classid: classid, _method: "put", auditcount: auditcount, audit: audit},
                                type: "post",
                                dataType: "text",
                                success: function (rs) {
                                    if (rs == "true") {
                                        $("#" + classid + "1").html("审核中");
                                        $("#" + classid + "2").html("<a href='" + "/classes/string/showone?classid=" + classid + "'>详情</a>");
                                    }
                                }
                            });
                        }


                        function del(classid) {
                            $.ajax({
                                url: "/classes/void/deleteoneclass",
                                data: {classid: classid, _method: "delete"},
                                type: "post",
                                dataType: "text",
                                success: function (rs) {
                                    if (rs == "true") {
                                        $("#" + classid + "2").html("已删除");
                                    }
                                }
                            })
                        }
                    </script>

                    <tr>
                        <%--<td colspan="20" style="text-align: center;">
                            <a style="text-decoration: none;" href="#">
                                <a href="/classes/string/show?classname=${cname}&classnum=${cnum}&pageindex=1&pagesize=${pagesize}">首页</a>
                                <a href="/classes/string/show?classname=${cname}&classnum=${cnum}&pageindex=${pi.prePage==0?1:pi.prePage}&pagesize=${pagesize}">上一页</a>
                                <c:forEach var="i" begin="1" end="${pi.pages}">
                                    <a href="/classes/string/show?classname=${cname}&classnum=${cnum}&pageindex=${pi.prePage}&pagesize=${pagesize}">${i}</a>
                                </c:forEach>
                                <a href="/classes/string/show?classname=${cname}&classnum=${cnum}&pageindex=${pi.nextPage==0?pi.pages:pi.nextPage}&pagesize=${pagesize}">下一页</a>
                                <a href="/classes/string/show?classname=${cname}&classnum=${cnum}&pageindex=${pi.pages}&pagesize=${pagesize}">尾页</a>
                                共${pi.total}条
                                每页显示
                                <select name="num">
                                    <option value="5" <c:if test="${pagesize==5}">selected</c:if>>5</option>
                                    <option value="10" <c:if test="${pagesize==10}">selected</c:if>>10</option>
                                    <option value="15" <c:if test="${pagesize==15}">selected</c:if>>15</option>
                                </select>
                                ${pi.pageNum}/${pi.pages}
                            </a>
                        </td>--%>

                        <td colspan="20" style="text-align: center;">
                            <fy:fy url="/classes/string/show?classname=${cname}&classnum=${cnum}"
                                   pageInfo="${pi}"></fy:fy>
                        </td>

                        <script type="text/javascript">
                            $(function () {
                                $("[name=num]").change(function () {
                                    var pagesize = $(this).val();
                                    location.href = "/classes/string/show?pagesize=" + pagesize;
                                });
                            })
                        </script>

                    </tr>
                    </tbody>
                </table>
            </form>
        </ul>
    </div>
</div>
</div>

</div>
</body>
</html>
