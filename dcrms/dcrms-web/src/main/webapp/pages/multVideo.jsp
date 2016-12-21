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
<title>站点曲线</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">   <!-- 在IE运行最新的渲染模式 -->
<meta name="viewport" content="width=device-width, initial-scale=1">   <!-- 初始化移动浏览显示  -->

 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/js/main.js" type="text/javascript" ></script>
 
 <script src="<%=basePath%>static/video/swfobject.js" type="text/JavaScript" ></script>
</head>
 
<body>
    <div id="container">
      <div id="head">
        <div id="title">
          <span id="usrSpan">当前用户： </span>
          <a id="user" href="#">admin</a>
          <a id="logout" href="#">注销</a>
        </div>
        <ul class="nav nav-justified">
          <li ><a id="nav_li_a1" href="<%=basePath%>pages/index.jsp">站点导航</a></li>
          <li ><a id="nav_li_a2" href="<%=basePath%>pages/multVideo.jsp">多路视频</a></li>
          <li ><a id="nav_li_a3" href="#">报警查询</a></li>
          <li ><a id="nav_li_a4" href="<%=basePath%>pages/siteCurve.jsp">站点数据</a></li>
          <li ><a id="nav_li_a5" href="#">设备管理</a></li>
          <li ><a id="nav_li_a6" href="#">用户管理</a></li>
          <li ><a id="nav_li_a7" href="#">权限管理</a></li>
        </ul>
      </div>

      <div id="main">
        <div id="site">
           <ul class="siteList">
				<li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >北京</a></li>
				<li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >上海</a></li>
				<li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >深圳</a></li>
				<li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >广州</a></li>
				<li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >青岛</a></li>
			    <li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >成都</a></li>
			    <li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >沈阳</a></li>
			    <li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >重庆</a></li>
			</ul>
		</div>
        <div id="panel">
           <div id="video1" style="float:left; margin:30px 30px" ></div>
           <div id="video2" style="float:left; margin:30px 30px" ></div>
           <div id="video3" style="float:left; margin:30px 30px" ></div>
           <div id="video4" style="float:left; margin:30px 30px" ></div>
           <div id="video5" style="float:left; margin:30px 30px" ></div>
           <div id="video6" style="float:left; margin:30px 30px" ></div>
        </div>
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2016  中国海洋大学     版权所有   </p>
      </div>
      
      <script type="text/javascript">  
        //创建播放器对象  
        var player = new SWFObject('<%=basePath%>static/video/player.swf','jw','100%','100%','9','#000000');  
        player.addParam('allowfullscreen','true');  
        player.addParam('allowscriptaccess','always');  
        player.addParam('wmode','opaque');  
        player.addParam('flashvars','file=<%=basePath%>static/video/IMG_1189.flv&bufferlength=20&stretching=uniform&repeat=always&autostart=false');    
        player.write('video1'); 
        player.write('video2');  
        player.write('video3');  
        player.write('video4'); 
        player.write('video5');  
        player.write('video6');  
     </script>  
    </div> 
  </body>
</html>