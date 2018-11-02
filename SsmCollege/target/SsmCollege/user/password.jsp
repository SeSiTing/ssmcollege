<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>
	学生信息管理平台
</title>
	<link href="../Style/StudentStyle.css" rel="stylesheet" type="text/css" />
	<link href="../Script/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" />
	<link href="../Style/ks.css" rel="stylesheet" type="text/css" />
	<link href="../css/mine.css" type="text/css" rel="stylesheet">
    <script src="../Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
    <script src="../Script/jBox/jquery.jBox-2.3.min.js" type="text/javascript"></script>
    <script src="../Script/jBox/i18n/jquery.jBox-zh-CN.js" type="text/javascript"></script>
    <script src="../Script/Common.js" type="text/javascript"></script>
    <script src="../Script/Data.js" type="text/javascript"></script>
    <script src="../Script/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="../Script/jquery.validate.js" type="text/javascript"></script>

<%--    <script type="text/javascript">
        var ok1=false,ok2=false,ok3=false;
        $(function () {
            //验证密码
            $("[name=oldpass]").blur(function () {
                var upass = $(this).val();
                $.ajax({
                    url:"/user/checkpass",
                    data:"upass="+upass,
                    type:"get",
                    dataType:"text",
                    success:function (rs) {
                        if (rs == "密码正确"){
                            ok1=true;
                            $("[name=oldpass]").next().html("OK");
                        }else {
                            $("[name=oldpass]").next().html("<font color='red'>"+"密码有误"+"</font>");
                        }

                    }
                });
            });

            //验证密码长度   正则
            $("[name=userPs]").blur(function () {
                var newpass = $(this).val();
                //定义正则
                var reg=/^\w{3,}$/;
                if (reg.test(newpass)) {
                    $(this).next().html("OK");
                    ok2=true;
                }else {
                    $(this).next().html("密码至少三位");
                }
            });

            //密码一致
            $("[name=confirmpass]").blur(function () {
                var pass2 = $(this).val();
                var pass1 = $("[name=userPs]").val();
                if (pass1 == pass2) {
                    $(this).next().html("OK");
                    ok3=true;
                }else {
                    $(this).next().html("密码不一致");
                }
            })

            $("#button2").click(function(){
                if(ok1&&ok2&&ok3){
                    document.forms[0].submit();
                }else{
                    alert("填写信息有误");
                }
            });

        });
    </script>--%>

    <script type="text/javascript">
        $(function () {
            $("form").validate({
                rules:{
                    oldpass:{required:true,
                        remote:"/user/checkpass"},
                    userPs:{require:true, minlength:3},
                    confirmpass:{require:true, equalTo:"#userPs"
                    }
                },
                messages:{
                    oldpass:{
                        required:"请输入原密码",
                        remote:"密码有误"
                    },
                    userPs:{
                        required:"请输入新密码",
                        minlength:"新密码至少3位"
                    },
                    confirmpass:{
                        required:"请确认密码",
                        equalTo:"密码不一致"
                    }
                }
            })
        })
    </script>
</head>
<body>

		<div class="div_head">
            <span>
                <span style="float:left">当前位置是：个人中心-》密码修改</span>
                <span style="float:right;margin-right: 8px;font-weight: bold"></span>
            </span>
        </div>
</div>
<div class="cztable">
    <form action="/user/updatepass" method="post">
        <input type="hidden" name="_method" value="put"/>
    <table width="100%" cellpadding="0" cellspacing="0">
        <input type="hidden" name="userId" value="${u1.userId}" >
        <tr>
            <td align="right" width="80">原密码：</td>
            <td><input type="password" name="oldpass" /><span></span></td>
        </tr>
        <tr>
            <td align="right" width="90">新密码：</td>
            <td><input type="password" name="userPs" /><span></span></td>
        </tr>
        <tr>
            <td align="right" width="90">密码确认：</td>
            <td><input type="password" name="confirmpass" /><span></span></td>
        </tr>
       
        <tr align="center">
            <td colspan="5" height="40">
                <div align="center">
                    
                    <%--<input type="button" id="button2" value="确认" />--%>
                        <input type="submit" id="button2" value="确认" />
                </div>
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
