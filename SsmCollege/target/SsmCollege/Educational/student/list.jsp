<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学生信息管理平台</title>
<link href="../../Style/StudentStyle.css" rel="stylesheet" type="text/css" />
<link href="../../Script/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" />
<link href="../../Style/ks.css" rel="stylesheet" type="text/css" />
<link href="../../css/mine.css" type="text/css" rel="stylesheet">
<script src="../../Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="../../Script/jBox/jquery.jBox-2.3.min.js"
	type="text/javascript"></script>
<script src="../../Script/jBox/i18n/jquery.jBox-zh-CN.js"
	type="text/javascript"></script>
<script src="../../Script/Common.js" type="text/javascript"></script>
<script src="../../Script/Data.js" type="text/javascript"></script>

<script>
	function del(){
		confirm("确认操作？");
	}

</script>



</head>
<body>
	
	<div class="div_head" style="width: 100%;text-align:center;">
		<span>
                <span style="float: left;">当前位置是：教务中心-》学生管理</span>
                <span style="float: right; margin-right: 8px; font-weight: bold;">
                    <a style="text-decoration: none;" href="/department/string/findalldepa_s">【新增学生】</a>&emsp;&emsp;&emsp;&emsp;
                </span>
            </span>
	</div>

	<div class="cztable">
		<div>
				  <form action="/student/string/show" method="get">
					  <input type="hidden" name="pagesize" value="${pagesize}"/>
                    学生名称: 
					<input type="text" name="stuname" value="${stuname}"/>
                     学生学号: 
					<input type="text" name="studentno" value="${studentno}"/>
					性别:

					<select name="stusex">
							<option value="0">--请选择--</option>
							<option value="1" <c:if test="${stusex==1}">selected</c:if>>男</option>
							<option value="2" <c:if test="${stusex==2}">selected</c:if>>女</option>
						</select>
					<input type="submit" value="查询" />

                </form>



		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tbody>
				<tr style="height: 25px" align="center">
                        <th >学号</th>
						<th width="">姓名</th>
						<th width="">性别</th>
                        <th width="15%">联系电话</th>						
                        <th width="15%">专业</th>
						<th width="15%">登记时间</th>
						<th>操作</th>
                    </tr>

				<c:forEach items="${pi.list}" var="stu">
					<tr id="product1">
						<td align="center">${stu.studentno}</td>
						<td align="center">${stu.stuname}</td>
						<td align="center">
							<c:if test="${stu.stusex==1}">男</c:if>
							<c:if test="${stu.stusex==2}">女</c:if>
						</td>
						<td align="center">${stu.phone}</td>
						<td align="center">${stu.major.majorname}</td>

						<td align="center"><fmt:formatDate value="${stu.regdate}" pattern="yyyy-MM-dd"/></td>
						<td align="center">
							<a href="add.jsp">修改</a>
							<a href="javascript:void(0)" onclick="del();return false" class="tablelink"> 退学</a>
							<a href="view.html">详细</a>
						</td>
					</tr>
				</c:forEach>



				<tr>
					<td colspan="20" style="text-align: center;">
						<a style="text-decoration: none;" href="#">
							<a href="/student/string/show?stuname=${stuname}&studentno=${studentno}&stusex=${stusex}&pageindex=1&pagesize=${pagesize}">首页</a>
							<a href="/student/string/show?stuname=${stuname}&studentno=${studentno}&stusex=${stusex}&pageindex=${pi.prePage==0?1:pi.prePage}&pagesize=${pagesize}">上一页</a>
							<c:forEach var="i" begin="1" end="${pi.pages}">
								<a href="/student/string/show?stuname=${stuname}&studentno=${studentno}&stusex=${stusex}&pageindex=${pi.prePage}&pagesize=${pagesize}">${i}</a>
							</c:forEach>
							<a href="/student/string/show?stuname=${stuname}&studentno=${studentno}&stusex=${stusex}&pageindex=${pi.nextPage==0?pi.pages:pi.nextPage}&pagesize=${pagesize}">下一页</a>
							<a href="/student/string/show?stuname=${stuname}&studentno=${studentno}&stusex=${stusex}&pageindex=${pi.pages}&pagesize=${pagesize}">尾页</a>
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
                                location.href = "/student/string/show?pagesize=" + pagesize;
                            });
                        })
					</script>

				</tr>
                </tbody>
            </table>
	</div>

	</div>
	</div>

	</div>
</body>
</html>
