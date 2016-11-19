<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	// 获得本项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量    
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	// 将 "项目路径basePath"放入pageContext中，待以后用EL表达式读出。    
	pageContext.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<!--[if IE 8 ]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9 ]> <html lang="en" class="ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户注册</title>
<!--[if lt IE 9]>
<script src="static/html5/js/html5shiv.js"></script>
<![endif]-->

<link href="<%=basePath%>static/html5/css/normalize.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>static/html5/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>static/html5/css/jquery.idealforms.min.css" rel="stylesheet" type="text/css" media="screen" />
<link href="<%=basePath%>static/css/register-style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>static/css/sweet-alert.css" rel="stylesheet" type="text/css" />

</head>

<script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script src="<%=basePath%>static/html5/js/jquery-ui.min.js" type="text/javascript" ></script>
<script src="<%=basePath%>static/html5/js/jquery.idealforms.js" type="text/javascript" ></script>
<script src="<%=basePath%>static/js/sweet-alert.min.js" type="text/javascript" ></script>

<body>
	<div class="main">
		<span id="welcome">欢迎您注册</span>
		<form id="my-form" class="myform">
			<section name="第一步">
				<div>
					<label>用户名:</label><input id="username" name="username" type="text" />
				</div>
				<div>
					<label>密码:</label><input id="pass" name="password" type="password" />
				</div>
				<div>
					<label>确认密码:</label><input id="pass2" name="password"
						type="password" />
				</div>
				<div>
					<label>姓名:</label><input id="trueName" type="text" name="trueName"
						data-ideal="trueName" />
				</div>
				<div>
					<label>性别:</label> <select id="sex">
						<option value="男">男</option>
						<option value="女">女</option>
					</select>
				</div>
			</section>
			<section name="第二步">
				<div>
					<label>手机号码:</label><input id="telephone" type="text" name="phone"
						data-ideal="phone" />
				</div>
				<div>
					<label>邮箱:</label> <input id="email" name="email"
						data-ideal="required email" type="email" />
				</div>
				<div>
					<label>手机验证码:</label> <input id="telCode" type="text" />
					<button id="getTelCode" type="button" style="margin:0 10px;">获取手机校验码</button>
				</div>
				<div style="margin-top:10px; margin-left:100px;margin-right:100px;">
					<button type="button" id="submit" class="submit">提交</button>
					<button id="reset" type="button">重置</button>
				</div>
			</section>
		</form>
	</div>

	<script type="text/javascript">
		var options = {
			onFail : function() {
				alert($myform.getInvalid().length + " invalid fields.");
			},

			inputs : {
				'password' : {
					filters : 'required pass',
				},
				'username' : {
					filters : 'required username',
					data : {}
				},
				'email' : {
					filters : 'required email',
					data : {}
				},
				'phone' : {
					filters : 'required phone',
					data : {}
				},
				'trueName' : {
					filters : 'required',
					data : {}
				}
			}
		};

		var $myform = $('#my-form').idealforms(options).data('idealforms');

		$('#submit').click(function() {
			var username = document.getElementById("username").value; //用户名
			var password = document.getElementById("pass").value;    //密码
			var email = document.getElementById("email").value;     //邮箱
			var telephone = document.getElementById("telephone").value;     //手机号码
			var trueName = document.getElementById("trueName").value;     //姓名
			var sex = document.getElementById("sex").value;     //性别
			
			$.ajax({
				type : "post",
				dataType : "json",
				url : "userRegister.do",
				data:{
				   username:username,password:password,trueName:trueName,
				   email:email,telephone:telephone,sex:sex
                },
				success : function() {
					sweetAlert("注册成功！", "注册成功","success");
				},
				error:function()
				{
				   sweetAlert("注册失败！", "注册失败", "error");
				}
			});
		});
		
		$('#reset').click(function() {
			$myform.reset().fresh().focusFirst();
		});

		$myform.focusFirst();
	</script>

</body>
</html>

