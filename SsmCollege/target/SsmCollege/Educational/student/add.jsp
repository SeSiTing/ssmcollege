<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>
	学生信息管理平台
</title>
	<link href="../../Style/StudentStyle.css" rel="stylesheet" type="text/css" />
	<link href="../../Script/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" />
	<link href="../../Style/ks.css" rel="stylesheet" type="text/css" />
	<link href="../../css/mine.css" type="text/css" rel="stylesheet">
    <script src="../../Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
    <script src="../../Script/jBox/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <script src="../../Script/jBox/i18n/jquery.jBox-zh-CN.js" type="text/javascript"></script>
    <script src="../../Script/Common.js" type="text/javascript"></script>
    <script src="../../Script/Data.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            $("[name=deptid]").change(function () {
                var did = $(this).val();
                if (did == -1) {
                    $("[name=deptspan]").html(" *请先选择学院");
                    $("[name=majorid]")[0].length = 0;
                    $("[name=classid]")[0].length = 0;
                    $("[name=majorid]")[0].add(new Option("请选择", -1));
                } else {
                    $.ajax({
                        url: "/major/list/findbydeptid_s",
                        data: "did=" + did,
                        type: "get",
                        dataType: "json",
                        success: function (rs) {
                            //将专业添加到元素中
                            $("[name=deptspan]").html("");
                            $("[name=majorid]")[0].length = 0;
                            $("[name=classid]")[0].length = 0;
                            $("[name=majorid]")[0].add(new Option("请选择", -1));
                            for (var i = 0; i < rs.length; i++) {
                                $("[name=majorid]")[0].add(
                                    new Option(rs[i].majorname, rs[i].majorid)
                                )
                            }
                        }

                    })
                }
            })

            $("[name=majorid]").change(function () {
                var did = $("[name=deptid]").val();
                var mid = $("[name=majorid]").val();
                if (mid == "-1") {
                    $("[name=majorspan]").html(" *请先选择专业");
                    $("[name=classid]")[0].length = 0;
                } else {
                    $.ajax({
                        url: "/classes/list/getclasses",
                        data: {did: did, mid: mid},
                        type: "get",
                        dataType: "json",
                        success: function (rs) {
                            $("[name=majorspan]").html("");
                            $("[name=classid]")[0].length = 0;
                            for (var i = 0; i < rs.length; i++) {
                                $("[name=classid]")[0].add(
                                    new Option(rs[i].classname, rs[i].classid)
                                );
                            }
                        }
                    })
                }
            })


        })
    </script>
    

</head>
<body>

		<div class="div_head">
            <span>
                <span style="float:left">当前位置是：教务中心-》学生管理-》新增</span>
                <span style="float:right;margin-right: 8px;font-weight: bold"></span>
            </span>
        </div>
</div>
<div class="cztable">
    <form action="/student/string/addstudent" method="post">
	<table border="1" width="100%" class="table_a">
                <tr  width="120px;">
                    <td width="10%">学号：<span style="color:red">*</span>：</td>
                    <td>
						<input type="text"  name="studentno" value="" />
					</td>
                </tr>

                <tr  width="120px;">
                    <td>姓名<span style="color:red">*</span>：</td>
                    <td>
						<input type="text"  name="stuname" value="" />
					</td>
                </tr>
               <tr>
                    <td>学院<span style="color:red">*</span>：</td>
                    <td>
                        <select name="deptid">
                            <option value="-1">请选择</option>
                            <c:forEach items="${dlist}" var="d">
                                <option value="${d.departid}">${d.departname}</option>
                            </c:forEach>
                        </select>
                        <span name="deptspan" style="color: red"></span>
                    </td>
                </tr>
                <tr>
                    <td>专业<span style="color:red">*</span>：</td>
                    <td>
                        <select name="majorid">
                            <option value="-1">请选择</option>
                        </select>
                        <span name="majorspan" style="color: red"></span>
                    </td>
                </tr>
                <tr>
                    <td>班级<span style="color:red">*</span>：</td>
                    <td>
                        <select name="classid">
                            <option>请选择</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>性别<span style="color:red">*</span>：</td>
                    <td>
                        <input type="radio" name="stusex" checked value="1" />男
                        <input type="radio" name="stusex" value="2"/>女
                    </td>
                </tr>


				<tr>
                    <td>EMAIL：</td>
                    <td>
                        <input type="text" name="email" value="" />
                    </td>                
                </tr>

				<tr>
                    <td>联系电话：</td>
                    <td>
                        <input type="text" name="phone" value="" />
                    </td>                
                </tr>

				<tr>
                    <td>户口所在地：</td>
                    <td>
                        <input type="text" name="registered" value=""  />
                    </td>                
                </tr>

				<tr>
                    <td>住址：</td>
                    <td>
                        <input type="text" name="address" value="" />
                    </td>                
                </tr>
				<tr>
                    <td>政治面貌：</td>
                    <td>
                        <input type="text" name="politics" value="" />
                    </td>                
                </tr>
				<tr>
                    <td>身份证号：</td>
                    <td>
                        <input type="text" name="cardid" value="" />
                    </td>                
                </tr>

				<tr>
                    <td>简介<span style="color:red">*</span>：</td>
                    <td>
                        <textarea name="stucontent">一个新开辟领域的探讨，探讨摸索</textarea>
                    </td>
                </tr>
				<tr>
					<td colspan=2 align="center">
						<input type="submit" value="添加" /> 
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
