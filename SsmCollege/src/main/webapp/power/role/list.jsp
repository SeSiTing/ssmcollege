<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fy" uri="http://java.sun.com/jsp/femye/fy" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>
        学生信息管理平台
    </title>
    <link href="../../Style/StudentStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../../Script/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css"/>
    <link href="../../Style/ks.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/mine.css" type="text/css" rel="stylesheet">
    <script src="../../Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
    <script src="../../Script/jBox/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <script src="../../Script/jBox/i18n/jquery.jBox-zh-CN.js" type="text/javascript"></script>
    <script src="../../Script/Common.js" type="text/javascript"></script>
    <script src="../../Script/Data.js" type="text/javascript"></script>
    <script type="text/javascript">
        function del(rid) {
            var rs = confirm("确认删除？");
            if (rs == true) {
                delaj(rid);
            }
        }

        function delaj(rid) {
            $.ajax({
                url:"/role/void/delrole",
                data:{roleid:rid,_method:"delete"},
                type:"post",
                dataType:"text",
                success: function (rs) {
                    if ("true" == rs) {
                        alert("删除成功");
                        $("#td" + rid).html("已删除");
                    }else {
                        alert("删除失败，请先删除角色下的用户");
                    }
                }
            })
        }


    </script>
</head>
<body>


<div class="div_head" style="width: 100%;text-align:center;">
		<span> <span style="float:left">当前位置是：权限管理-》角色管理</span> <span
                style="float:right;margin-right: 8px;font-weight: bold">
			<a style="text-decoration: none;" href="/role/string/getallmenus">【新增角色】</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</span>
		</span>
</div>

<div class="morebt">

</div>


<div class="cztable">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
        <tr style="height: 25px" align="center">
            <th><input type="checkbox"/></th>
            <th scope="col">
                序号
            </th>

            <th scope="col">
                角色名称
            </th>
            <th scope="col">
                状态
            </th>
            <th scope="col" width="300px">
                操作
            </th>
        </tr>


        <c:forEach items="${rpage.list}" var="r" varStatus="sta">

            <tr align="center">
                <th><input type="checkbox"/></th>
                <td>
                        ${sta.count}
                </td>
                <td>
                        ${r.rolename}
                </td>
                <td id="t${r.roleid}">
                        ${r.rolestate==1?"启用":"禁用"}
                </td>

                <td id="td${r.roleid}">&nbsp;
                    <a name="changestate" id="${r.roleid}" style="color: #0a6397;cursor: pointer"
                       onclick="changestate(${r.roleid})">${r.rolestate==1?"启用":"禁用"}</a>
                        <%--<c:if test="${r.rolestate==0}">
                            <a name="changestate" style="color: #0a6397;cursor: pointer"
                               onclick="changestate(${r.rolestate},${r.roleid})">禁用</a>
                        </c:if>--%>
                    <a href="/role/string/showonerole_info?roleid=${r.roleid}">详情</a>
                    <a href="/role/string/showonerole_edit?roleid=${r.roleid}">修改</a>
                    <c:if test="${r.roleid!=u1.role.roleid}">
                    <a href="javascript:void(0)" onclick="del(${r.roleid});return false" class="tablelink"> 删除</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

    <div class='MainStyle'>
        <fy:fy url="/role/string/getallroles?1=1" pageInfo="${rpage}"></fy:fy>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("[name=num]").change(function () {
            var pagesize = $(this).val();
            location.href = "/role/string/getallroles?pagesize=" + pagesize;
        });
    })

    function changestate(rid) {
        var sta = $("#" + rid).html();
        $.ajax({
            url: "/role/void/changestate",
            data: {sta: sta, roleid: rid, _method: "put"},
            type: "post",
            dataType: "text",
            success: function (rs) {
                if (rs == "禁用") {
                    $("#" + rid).html("禁用");
                    $("#t" + rid).html("禁用");
                } else {
                    $("#" + rid).html("启用");
                    $("#t" + rid).html("启用");
                }
            }
        })
    }
</script>

</div>
</body>
</html>