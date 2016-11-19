<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>通用权限管理平台—欢迎登录</title>
<link rel="stylesheet" type="text/css" href="static/css/login-style.css" />
<link rel="stylesheet" type="text/css" href="static/css/sweet-alert.css" />
</head>
<body>
	<script src="static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="static/js/sweet-alert.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("body").keydown(function() {
				if (event.keyCode == "13") {//keyCode=13是回车键
					$('#submit').click();
				}
			});

			$("#submit").click(function() {
				var username = document.getElementById("username").value;
				var password = document.getElementById("password").value;
				if (username == "" && password == "") {
					$("#notice").html('<span style="color:red;">请输入用户名和密码！</span>');//更改p标签，显示提示信息
					$("#username").focus();//获取焦点
					return;
				} else {
					if (username == "") {
						$("#notice").html('<span style="color:red;">请输入用户名！</span>');
						$("#username").focus();
						return;
					} else if (password == "") {
						$("#notice").html('<span style="color:red;">请输入密码！</span>');
						$("#password").focus();
						return;
					}
			   }

			   $.ajax({
				   type : "POST",
				   url : "login.do?username=" + username + "&password=" + password,
				   success : function(msg) {
					  if (msg == "3") {
                         $("#notice").html('<span style="color:red;">密码无效！</span>');
						 $("#password").focus();
					  } else if (msg == "2") {
						$("#notice").html('<span style="color:red;">用户名不存在！</span>');
						$("#username").focus();
					  } else {
						 window.location.href = "loadHome.do?userType=supplier";
					  }
				   },
				   error : function(e) {
					  sweetAlert("网页发生错误：",e,"error");
				   }
			   });
		  });
	});
	</script>
	<form id="login">
		<h1>登 录</h1>
		<p id="notice">欢迎登录，注意保护账号</p>
		<fieldset id="inputs">
			<input id="username" type="text" placeholder="用户名" autofocus required onfocus=""> 
			<input id="password" type="password" placeholder="密码" required onfocus="">
		</fieldset>
		<fieldset id="actions">
			<input type="button" id="submit" value="登　录" onkeyup="submitByEnter(event)"> 
			<a href="">忘记密码？</a>   <a href="pages/authority/users_register.jsp">注册</a>
		</fieldset>
	</form>
</body>
</html>
