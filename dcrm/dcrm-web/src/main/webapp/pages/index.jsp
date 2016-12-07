<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	// 获得本项目的地址(例如: http://localhost:8080/MyApp/)赋值给basePath变量    
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	// 将 "项目路径basePath" 放入pageContext中，待以后用EL表达式读出。    
	pageContext.setAttribute("basePath", basePath);
%>

<!DOCTYPE HTML>
<html>

<head>
<title>主页</title>
<meta charset="utf-8">
 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/js/main.js" type="text/javascript" ></script>
</head>

<body>
    <div class="container">
      <div class="masthead">
        <h3 class="text-muted">分布式机房监控平台</h3>
        <ul class="nav nav-justified">
          <li ><a id="nav_li_a1" href="#">主页</a></li>
          <li ><a id="nav_li_a2" href="#">报警</a></li>
          <li ><a id="nav_li_a3" href="#">视频</a></li>
          <li ><a id="nav_li_a4" href="#">可视化数据</a></li>
          <li ><a id="nav_li_a5" href="#">设备</a></li>
          <li ><a id="nav_li_a6" href="#">采集</a></li>
        </ul>
      </div>

      <div class="jumbotron">
        <div id="slider">
			<img src="<%=basePath%>static/img/error.jpg" >				
		</div>
      </div>

      <div class="footer">
        <p>&copy; OUC 2016</p>
      </div>
    </div> 
  </body>
</html>