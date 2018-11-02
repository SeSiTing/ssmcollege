<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
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


</head>
<body>

<div class="div_head">
            <span>
                <span style="float:left">当前位置是：权限管理-》角色管理-》新增</span>
                <span style="float:right;margin-right: 8px;font-weight: bold">
                    <a style="text-decoration: none" href="javascript:history.back();">【返回】</a>
                </span>
            </span>
</div>
</div>
<div class="cztable">
    <form action="/role/string/addrole" method="post">
        <table border="1" width="100%" class="table_a">
            <tr width="120px;">
                <td width="120px">角色名：<span style="color:red">*</span>：</td>
                <td>
                    <input type="text" name="rolename" value=""/>
                </td>
            </tr>

            <tr width="120px;">
                <td>菜单资源<span style="color:red">*</span>：</td>
                <td>

                    <script type="text/javascript">
                        function checksecond(mid) {
                            var cl="a"+mid;
                            var classes=$("[class="+cl+"]");//定位到二级菜单的复选框
                            var menu = $("#m" + mid)[0];//定位到一级菜单的复选框

                            for (var i = 0;i<classes.length;i++) {
                                classes[i].checked = menu.checked;
                            }
                        }
                        function gettop(mid) {
                            //得到所有二级菜单的复选框
                            var cl = "a" + mid;
                            var classes=$("[class="+cl+"]")
                            var k=-1;
                            for (var i = 0;i<classes.length;i++){
                                if (classes[i].checked==true) {
                                    $("#m" + mid )[0].checked = true;
                                    k=2;
                                    break;
                                }
                            }
                            if (k==-1){
                                $("#m" + mid )[0].checked = false;
                            }
                        }
                    </script>

                    <ul>
                        <c:forEach items="${menulist}" var="menu">
                            <li>
                                <input type="checkbox" name="menu" value="${menu.menuid}"
                                       id="m${menu.menuid}" onclick="checksecond(${menu.menuid})">${menu.menuname}
                                <ul>
                                    <c:forEach items="${menu.seconds}" var="menu2">
                                        <li>&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="checkbox" name="menu" value="${menu2.menuid}"
                                             class="a${menu.menuid}" onclick="gettop(${menu.menuid})"/>${menu2.menuname}
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>

                </td>
            </tr>

            <tr>
                <td>启用状态<span style="color:red">*</span>：</td>
                <td>
                    <input type="radio" name="rolestate" checked value="1"/>启用
                    <input type="radio" name="rolestate" value="0"/>禁用
                </td>
            </tr>

            <tr width="120px">
                <td colspan="2" align="center">
                    <input type="submit" value="添加"/>
                </td>
            </tr>
        </table>
    </form>
</div>

</div>
</div>

</div>
</body>
</html>
