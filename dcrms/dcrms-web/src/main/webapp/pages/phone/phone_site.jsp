<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>手机曲线</title>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">   <!-- 初始化移动浏览显示  -->

 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="http://api.map.baidu.com/api?v=2.0&ak=CBoViYAph0YuVMbpoDFyqGw7DfzUq4AT"></script>
</head>
 
<body>
    <div id="container">
        <div id="panel" style="width:320px; height:480px"></div> 
    </div> 
    
    <script type="text/javascript">
      // 百度地图API功能
	  var map = new BMap.Map("panel",{minZoom:5, maxZoom:18}); // 创建Map实例,设置地图允许的最小/大级别
	  map.centerAndZoom(new BMap.Point(116.404, 39.915), 15);  // 初始化地图,设置中心点坐标和地图级别
	  map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	  map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

	  // 创建标注
	  var n = 1;
	  function addMarker(point){
	     var marker = new BMap.Marker(point);
	     map.addOverlay(marker);
	     var label = new BMap.Label("机房" + n, {offset:new BMap.Size(20,-10)});
 	     label.setStyle({
		    color : "red",
		    fontSize : "15px",
		    height : "20px",
		    lineHeight : "20px",
		    fontFamily:"微软雅黑"
	     });
	     marker.setLabel(label);
 	     marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画 
 	     marker.addEventListener("click", attribute);
 	     n++;
	  }
	  function attribute(e){
		 var p = e.target;
		 alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat);    
	  }	
	  
	  // 随机向地图添加10个标注
	  var bounds = map.getBounds();
	  var sw = bounds.getSouthWest();
	  var ne = bounds.getNorthEast();
	  var lngSpan = Math.abs(sw.lng - ne.lng);
	  var latSpan = Math.abs(ne.lat - sw.lat);
	  for (var i = 0; i < 5; i ++) {
		 var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
		 addMarker(point);
	  }
   </script>
   
  </body>
</html>