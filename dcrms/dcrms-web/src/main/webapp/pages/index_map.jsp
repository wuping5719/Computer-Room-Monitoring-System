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
 <title>站点地图</title>
 <meta charset="utf-8">
 <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" ></script>
 <script src="<%=basePath%>static/js/main.js" ></script>
 <script src="http://api.map.baidu.com/api?v=2.0&ak=CBoViYAph0YuVMbpoDFyqGw7DfzUq4AT"></script>
</head>
 
<body>
    <!-- 隐藏input用于参数传递 -->
    <input id="province" type="hidden" value="${province}" />
    
    <div id="container">
      <div id="head">
        <div id="title">
          <span id="usrSpan">当前用户： </span>
          <a id="user" href="#">admin</a>
          <a id="logout" href="#">注销</a>
        </div>
        <ul class="nav nav-justified">
          <li ><a id="nav_li_a1" href="<%=basePath%>loadIndex.do">站点导航</a></li>
          <li ><a id="nav_li_a2" href="<%=basePath%>loadMultVideo.do">多路视频</a></li>
          <li ><a id="nav_li_a3" href="#">报警查询</a></li>
          <li ><a id="nav_li_a4" href="<%=basePath%>loadSiteCurve.do">站点数据</a></li>
          <li ><a id="nav_li_a5" href="#">设备管理</a></li>
          <li ><a id="nav_li_a6" href="#">用户管理</a></li>
          <li ><a id="nav_li_a7" href="#">权限管理</a></li>
        </ul>
      </div>

      <div id="main">
        <div id="site">
           <ul class="siteList">
				<c:forEach items="${cityDTOList}" var="cityDTO" >
					<li><a href="#"><img src="<%=basePath%>static/img/main/icon.png" >${cityDTO.cityname}</a></li>
				</c:forEach>
			</ul>
		</div>
        <div id="panel"></div>
        
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
    </div> 
    
    <script type="text/javascript">
      var province = document.getElementById('province').value;   //省份
      
      // 百度地图API功能
  	  var map = new BMap.Map("panel",{minZoom:5, maxZoom:18}); // 创建Map实例,设置地图允许的最小/大级别
  	  //map.centerAndZoom(province,15);      // 初始化地图,用城市名设置地图中心点
  	  map.centerAndZoom(new BMap.Point(116.404, 39.915), 15);  // 初始化地图,设置中心点坐标和地图级别
  	  map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
  	  //map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
  	  map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

  	  /* //创建小狐狸
  	  var pt = new BMap.Point(116.417, 39.909);
  	  var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
  	  var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
  	  map.addOverlay(marker2);              // 将标注添加到地图中 
  	  */
  	  
  	  //var json_data = [[116.404,39.915],[116.383752,39.91334],[116.384502,39.932241]];
  	  
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
  		 //window.location.href = "<%=basePath%>loadIndexCity.do?province=" + province;
  		 
  		 window.location.href = "<%=basePath%>loadSite3dView.do?siteName=" + province;
  		 
  		 //var p = e.target;
		 //alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat);    
	  }	
  	  
  	  // 随机向地图添加10个标注
  	  var bounds = map.getBounds();
  	  var sw = bounds.getSouthWest();
  	  var ne = bounds.getNorthEast();
  	  var lngSpan = Math.abs(sw.lng - ne.lng);
  	  var latSpan = Math.abs(ne.lat - sw.lat);
  	  for (var i = 0; i < 10; i ++) {
  		 var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
  		 addMarker(point);
  	  }
  	  
	</script>
  </body>
</html>