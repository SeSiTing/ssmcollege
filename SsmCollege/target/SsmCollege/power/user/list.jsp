<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fy" uri="http://java.sun.com/jsp/femye/fy" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
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
    <script>
		function del(uid){
			var flag = confirm("确定删除？");
            if (flag == true) {
                del2(uid);
            }
		}
		function del2(uid) {
            $.ajax({
                url:"/user/void/deluser",
                data:{userId:uid,_method:"delete"},
                type:"post",
                dataType:"text",
                success:function (rs) {
                    if (rs == "true") {
                        $("#tr"+uid).html("");
                    }
                }
            })
        }
	</script>

</head>
<body>


<div class="div_head" style="width: 100%;text-align:center;">
		<span> <span style="float:left">当前位置是：权限管理-》人员管理</span> <span
			style="float:right;margin-right: 8px;font-weight: bold">
            <a style="text-decoration: none;" href="javascript:alert('操作成功！');">【导出excel】</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a style="text-decoration: none;" href="javascript:alert('操作成功！');">【批量删除】</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a style="text-decoration: none;" href="/user/string/showoneuser_add">【新增人员】</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</span>
		</span>
	</div>

<div class="morebt">
 
</div>
 <div class="cztable" style="width: 100%;">
        
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tbody>
                <tr style="height: 25px;" align="center">
                    
                    <th  width="8%">
						<input type="checkbox"/>
					</th>
					<th scope="col" width="15%">
                        序号
                    </th>
                    <th scope="col" width="15%">
                        账号
                    </th>
                    <th scope="col" width="15%">
                        姓名
                    </th>
                    <th scope="col" width="15%">
                        角色
                    </th>
                   
                    <th scope="col" >
                        操作
                    </th>
                </tr>
                
               <c:forEach items="${upi.list}" var="u">
                
                <tr align="center" id="tr${u.userId}">
					<th><input type="checkbox"/></th>
                    <td>
                        ${u.userId}
                    </td>
                    
                    <td>
                      ${u.userName}
                    </td>
                    <td>
                       <a href="info.jsp">${u.userRealname}</a>
                    </td>
                    
                    <td>&nbsp;
                       ${u.role.rolename}
                    </td>
                    
                    <td>&nbsp;
                        <a href="/user/string/showoneuser_edit?userId=${u.userId}&roleid=${u.role.roleid}">修改</a>
						<a href="javascript:void(0)" onclick="del(${u.userId});return false" class="tablelink"> 删除</a>
                    </td>
                </tr>

               </c:forEach>
                

                
                
               
                
               
                
                
            </tbody>
        </table>

     <div class='MainStyle'>
         <fy:fy url="/user/string/getallusers?1=1" pageInfo="${upi}"></fy:fy>
     </div>
 </div>

<script type="text/javascript">
    $(function () {
        $("[name=num]").change(function () {
            var pagesize = $(this).val();
            location.href = "/user/string/getallusers?pagesize=" + pagesize;
        });
    })
</script>

        
        
      
    </div>
</body>
</html>