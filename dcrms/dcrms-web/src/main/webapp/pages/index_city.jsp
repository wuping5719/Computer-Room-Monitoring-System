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
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" ></script>
 <script src="<%=basePath%>static/js/main.js" ></script>
 <script src="<%=basePath%>static/echarts/echarts.js"></script>
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
          <li ><a id="nav_li_a3" href="<%=basePath%>loadAlertInfos.do">报警查询</a></li>
          <li ><a id="nav_li_a4" href="<%=basePath%>loadSiteCurve.do">站点数据</a></li>
          <li ><a id="nav_li_a5" href="#">设备管理</a></li>
          <li ><a id="nav_li_a6" href="<%=basePath%>loadUserList.do">用户管理</a></li>
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
    
    <script src="<%=basePath%>static/echarts/map/js/anhui.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/aomen.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/beijing.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/chongqing.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/fujian.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/guangdong.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/gansu.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/guangxi.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/guizhou.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/hainan.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/hebei.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/heilongjiang.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/henan.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/hubei.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/hunan.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/jiangsu.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/jiangxi.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/jilin.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/liaoning.js"></script> 
    <script src="<%=basePath%>static/echarts/map/js/neimenggu.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/ningxia.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/qinghai.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/shandong.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/shanghai.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/shanxi.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/shanxi1.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/sichuan.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/taiwan.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/tianjin.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/xianggang.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/xinjiang.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/xizang.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/yunnan.js"></script>
    <script src="<%=basePath%>static/echarts/map/js/zhejiang.js"></script>
    
    <script type="text/javascript">
      var province = document.getElementById('province').value;   //省份
      
      var chart = echarts.init(document.getElementById('panel'));
      chart.setOption({
    	 title: {
    	    text: province + '机房监控站点导航图',
    	    //subtext: province + '机房监控站点导航图',
    	    x:'center',
    	    textStyle: {
    	       color: '#666',
    	       fontSize: 16
    	    }
    	 },
    	 backgroundColor: '#eeffee',
    	 geo: {
    	    map: province,
    	    label: {
                emphasis: {
                    show: true
                }
            },
    	    itemStyle: {
    	        normal: {
    	            areaColor: '#28ff28',
    	            borderColor: '#111'
    	        },
    	        emphasis: {
    	            areaColor: '#28ff28'
    	        }
    	    }
    	 },
    	 series: [{
            type: 'map',
            coordinateSystem: 'geo',
            label: {
                normal: {
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true
         }]
      });
      
      chart.on('click', function (params) {
    	  window.location.href = "<%=basePath%>loadIndex.do?province=" + params.name;
      });
	</script>
  </body>
</html>