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
 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" ></script>
 <script src="<%=basePath%>static/js/main.js" ></script>
 <script src="<%=basePath%>static/echarts/echarts.js"></script>
</head>
 
<body>
    <!-- 隐藏input用于参数传递 -->
    <input id="siteName" type="hidden" value="${siteName}" />
    
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
        <div id="panel" >
           <div id="leftPanel" style="float:left; height:100%; width:65%; background: center no-repeat; background-size:contain;"></div>
           <div id="rightPanel" style="float:left; height:100%; width:35%;">
              <div id="voltagePanel" style="float:left; height:40%; width:50%;"></div>
              <div id="currentPanel" style="float:left; height:40%; width:50%;"></div>
              <div id="powerA" style="float:left; height:6%; width:100%;"></div>
              <div style="float:left; height:5%; width:100%;"></div>
              <div id="powerB" style="float:left; height:6%; width:100%;"></div>
              <div style="float:left; height:5%; width:100%;"></div>
              <div id="powerC" style="float:left; height:6%; width:100%;"></div>
              <div style="float:left; height:5%; width:100%;"></div>
              <div id="temperature" style="float:left; height:6%; width:100%;"></div>
              <div style="float:left; height:5%; width:100%;"></div>
              <div id="humidity" style="float:left; height:6%; width:100%;"></div>
           </div>
        </div>
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
    </div> 
    
    <script type="text/javascript">
      $(document).ready(function() {
    	  //var siteName = document.getElementById('siteName').value;   //站点
          
    	  var currentImg = '<%=basePath%>static/img/siteImg/金家岭.png';
          document.getElementById("leftPanel").style.backgroundImage="url("+ currentImg +")";
      
      });
      
      var voltageChart = echarts.init(document.getElementById('voltagePanel'));
      var voltageOption = {
    	  tooltip : {
    		 formatter: "{a} : {c}V"
    	  },
          series: [{
    		 name: '市电电压',
    		 type: 'gauge',
    		 min: 0,
    	     max: 320,
    	     axisLine: {        // 坐标轴线
     	         lineStyle: {    // 属性lineStyle控制线条样式
     	            width: 8
     	         }
     	     },
     	     axisTick: {          // 坐标轴小标记
     	         length:12,        // 属性length控制线长
     	         lineStyle: {      // 属性lineStyle控制线条样式
     	             color: 'auto'
     	         }
     	     },
     	     splitLine: {          // 分隔线
     	         length:16,         // 属性length控制线长
     	         lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
     	             color: 'auto'
     	         }
     	     },
     	     pointer: {
     	         width:6
     	     },
    		 detail: {formatter:'{value}V'},
    		 data: [{value: 220, name: '电压'}]
    	  }]
      };
      voltageChart.setOption(voltageOption, true);
      
      var currentChart = echarts.init(document.getElementById('currentPanel'));
      var currentOption = {
    	  tooltip : {
    		 formatter: "{a} : {c}A"
    	  },
    	  series: [{
    		 name: '市电电流',
    		 type: 'gauge',
    		 min: 0,
    	     max: 10,
    	     axisLine: {        // 坐标轴线
    	         lineStyle: {    // 属性lineStyle控制线条样式
    	            width: 8
    	         }
    	     },
    	     axisTick: {          // 坐标轴小标记
    	         length:12,        // 属性length控制线长
    	         lineStyle: {      // 属性lineStyle控制线条样式
    	            color: 'auto'
    	         }
    	     },
    	     splitLine: {          // 分隔线
    	         length:16,         // 属性length控制线长
    	         lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
    	             color: 'auto'
    	         }
    	     },
    	     pointer: {
    	          width:6
    	     },
    		 detail: {formatter:'{value}A'},
    		      data: [{value: 5, name: '电流'}]
    	  }]
      };
      currentChart.setOption(currentOption, true);
      
      setInterval(function () {
    	  voltageOption.series[0].data[0].value = (Math.random() * 5 + 218).toFixed(2) - 0;
    	  voltageChart.setOption(voltageOption, true);
    	  
    	  currentOption.series[0].data[0].value = (Math.random() * 5 + 4).toFixed(2) - 0;
    	  currentChart.setOption(currentOption, true);
      },2000);
      
      
      var powerAChart = echarts.init(document.getElementById('powerA'));
      var powerAOption = {
    	  tooltip: {
    		   trigger: 'axis',
    		   formatter: "{a} : {c}kw",
    		   axisPointer: {
    		      type: 'shadow'
    		   }
    	  },
    	  xAxis: {
    	      type: 'value',
    	      min: 0,
    	      max: 2000
    	  },
    	  yAxis: {
    		  type: 'category',
    		  data: ['A功率']
    	  },
    	  series: [{
    		  name: '功率',
    		  type: 'bar',
    		  data: [1200],
    		  boundaryGap: [0, 0.01]
    		}
    	  ]
      };
      powerAChart.setOption(powerAOption, true);
      
      var powerBChart = echarts.init(document.getElementById('powerB'));
      var powerBOption = {
    	  tooltip: {
    		   trigger: 'axis',
    		   formatter: "{a} : {c}kw",
    		   axisPointer: {
    		      type: 'shadow'
    		   }
    	  },
    	  xAxis: {
    	      type: 'value',
    	      min: 0,
    	      max: 2000,
    	      boundaryGap: [0, 0.01]
    	  },
    	  yAxis: {
    		  type: 'category',
    		  data: ['B功率']
    	  },
    	  series: [{
    		  name: '功率',
    		  type: 'bar',
    		  data: [500]
    		}
    	  ]
      };
      powerBChart.setOption(powerBOption, true);
      
      var powerCChart = echarts.init(document.getElementById('powerC'));
      var powerCOption = {
    	  tooltip: {
    		   trigger: 'axis',
    		   formatter: "{a} : {c}kw",
    		   axisPointer: {
    		      type: 'shadow'
    		   }
    	  },
    	  xAxis: {
    	      type: 'value',
    	      min: 0,
    	      max: 2000,
    	      boundaryGap: [0, 0.01]
    	  },
    	  yAxis: {
    		  type: 'category',
    		  data: ['C功率']
    	  },
    	  series: [{
    		  name: '功率',
    		  type: 'bar',
    		  data: [1800]
    		}
    	  ]
      };
      powerCChart.setOption(powerCOption, true);
      
      var tempChart = echarts.init(document.getElementById('temperature'));
      var tempOption = {
    	  tooltip: {
    		  trigger: 'axis',
    		  formatter: "{a} : {c}℃",
    		  axisPointer: {
    		     type: 'shadow'
    		  }
    	  },
    	  xAxis: {
    	      type: 'value',
    	      boundaryGap: [0, 0.01],
    	      min: 0,
    	      max: 60
    	  },
    	  yAxis: {
    		  type: 'category',
    		  data: ['温度']
    	  },
    	  series: [{
    		  name: '温度',
    		  type: 'bar',
    		  data: [28]
    		}
    	  ]
      };
      tempChart.setOption(tempOption, true);
      
      var humChart = echarts.init(document.getElementById('humidity'));
      var humOption = {
    	  tooltip: {
    		  trigger: 'axis',
    		  formatter: "{a} : {c}%",
    		  axisPointer: {
    		     type: 'shadow'
    		  }
    	  },
    	  xAxis: {
    	      type: 'value',
    	      boundaryGap: [0, 0.01],
    	      min: 0,
    	      max: 100
    	  },
    	  yAxis: {
    		  type: 'category',
    		  data: ['湿度']
    	  },
    	  series: [{
    		  name: '湿度',
    		  type: 'bar',
    		  data: [30]
    		}
    	  ]
      };
      humChart.setOption(humOption, true);
	</script>
  </body>
</html>