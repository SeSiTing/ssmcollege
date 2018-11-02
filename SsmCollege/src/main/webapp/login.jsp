<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script src="../../Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
	<script src="../../Script/jBox/jquery.jBox-2.3.min.js"
			type="text/javascript"></script>
	<script src="../../Script/jBox/i18n/jquery.jBox-zh-CN.js"
			type="text/javascript"></script>
	<script src="../../Script/Common.js" type="text/javascript"></script>
	<script src="../../Script/Data.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录</title>

<link href="css/login.css" type="text/css" rel="stylesheet" />

</head>
<body id="userlogin_body">

<form action="login" method="post">
<div id="user_login">
	<dl>
		<dd id="user_top">
			<ul>
				<li class="user_top_l"></li>
				<li class="user_top_c"></li>
				<li class="user_top_r"></li>
			</ul>
		</dd>
		<dd id="user_main">
			<ul>
				<li class="user_main_l"></li>
				<li class="user_main_c">
					<div class="user_main_box">
						<ul>
							<li class="user_main_text">用户名： </li>
							<li class="user_main_input">
								<input type="text" name="userName" maxlength="20" id="TxtUserName" class="txtusernamecssclass"> </li>
						</ul>
						<ul>
							<li class="user_main_text">密 码： </li>
							<li class="user_main_input">
								<input type="password" name="userPs" id="TxtPassword" class="txtpasswordcssclass"> </li>
						</ul>
						<ul>
							<li class="user_main_text">验证码： </li>
							<li class="user_main_input">
								<input type="text" id="mycode" name="mycode" style="width: 100px" maxlength="4">&nbsp;&nbsp;&nbsp;&nbsp;
							</li>
							<%--<li>
								<img id="codeimg" src="/getcode" onclick="changecoke()" />
							</li>--%>

							<script type="text/javascript">
								function changecoke() {
									document.getElementById("codeimg").src="/getcode?num="+Math.random();
                                    //("#mycode").html("") ;
                                    /*var td = document.getElementById("mycode");
                                    td.innerHTML = "";*/
                                    $("#mycode").val("");
                                }
							</script>
						</ul>
						<ul>
							<li class="user_main_text">Cookie： </li>
							<li class="user_main_input"><select name="DropExpiration" id="DropExpiration"> 
							<option selected="" value="None">不保存</option> <option value="Day">保存一天</option> 
							<option value="Month">保存一月</option> <option value="Year">保存一年</option></select> </li>
						</ul>
					</div>
				</li>
				<input type="hidden" name="flag" value="f">

				<li class="user_main_r">
				<input id="img11" type="image"  src="img/user_botton.gif" class="ibtnentercssclass">
				</a>
				</li>
			</ul>
		</dd>
		<dd id="user_bottom">
			<ul>
				<li class="user_bottom_l"></li>
				<li class="user_bottom_c"></li>
				<li class="user_bottom_r"></li>
			</ul>
		</dd>
	</dl>
</div>
</form>
<script type="text/javascript">

</script>

<script type="text/javascript">
	$(function () {
		$("#mycode").blur(function (e) {
            var mycode = $("[name=mycode]").val();
            $.ajax({
                url: "/audit",
                data: {mycode: mycode},
                type: "post",
				async:false,
                dataType: "text",
                success: function (rs) {
                    if (rs == "false") {
                        alert("验证码错误");
                        changecoke();
                        //e.preventDefault();不让表单提交
                    }/*else if(rs == "true"){
                        //alert("验证码正确");
                        //$("form").submit();
					} else {
                        alert("都不是");
					}*/
                }
            });
        })

		/*$("form").submit(function (e) {
			var code = $("#mycode").val();
			if (code=="") {
                alert("请输入验证码");
                e.preventDefault();
            }
        })*/
    })

    /*<!-- 刷新图片 -->
    $("#codeimg").unbind("click").bind("click",function(){
        <!-- 传递一个随机数给后台 -->
        changecoke();
		("#mycode").html("") ;
        //$("#verify").attr("src","verify/code.action?r"+Math.random());
    });*/
</script>



</body>
</html>