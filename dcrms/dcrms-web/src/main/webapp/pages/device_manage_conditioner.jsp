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
          <li ><a id="nav_li_a5" href="<%=basePath%>loadDeviceManage.do">设备管理</a></li>
          <li ><a id="nav_li_a6" href="<%=basePath%>loadUserList.do">用户管理</a></li>
          <li ><a id="nav_li_a7" href="<%=basePath%>loadRoleList.do">权限管理</a></li>
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
           <div id="leftPanel" style="float:left; height:100%; width:30%;">
              <div id="siteInfo" style="float:left; height:6%; width:100%; ">
                 <label style="margin:20px 2px; " >当前所在城市：青岛 ，机房站点：</label>
                 <select style="margin:20px 2px; width:80px;" >
			         <option value="金家岭">金家岭</option>
			         <option value="李村">李村</option>
			         <option value="明星大厅">明星大厅</option>
			         <option value="人民一路">人民一路</option>
			         <option value="浮山后">浮山后</option>
			         <option value="王哥庄">王哥庄</option>
		         </select>
              </div>
              <div id="deviceMenu" style="float:left; height:8%; width:100%; ">
                  <input type="button" id="conditionerBtn" value="精密空调" style="margin:30px 12px; height:24px;" />
                  <input type="button" id="upsBtn" value="UPS" style="margin:30px 12px; height:24px;" />
                  <input type="button" id="cabinetBtn" value="机柜" style="margin:30px 12px; height:24px;" />
                  <input type="button" id="acquistionBtn" value="采集配置" style="margin:30px 12px; height:24px;" />
              </div>
              <div id="deviceName" style="float:left; height:2%; width:100%; "><label style="margin-top:30px;" >精密空调</label></div>
              <div id="devicePic" style="float:left; height:88%; width:100%; background: center no-repeat; background-size:85%;"></div>
           </div>
           <div id="rightPanel" style="float:left; height:100%; width:70%;">
              <div id="operate" style="float:left; height:10%; width:100%;">
                  <label style="margin-left:2px;">开关机控制：</label>
                  <input type="button" id="startUp" value="开机" style="margin:20px 0; height:24px;" />
                  <label style="margin-left:40px;">温度控制：</label>
                  <input id="temp" style="width:80px; height:24px;" type="text" />
                  <input type="button" id="operateTemp" value="远程控制" style="margin:20px 0; height:24px;" />
                  <label style="margin-left:40px;">湿度控制：</label>
                  <input id="hum" style="width:80px; height:24px;" type="text" />
                  <input type="button" id="operateHum" value="远程控制" style="margin:20px 0; height:24px;" />
              </div>
              <div style="float:left; height:2%; width:100%;"></div>
              <div id="temperature" style="float:left; margin-left:10px; height:6%; width:98%;"></div>
              <div style="float:left; height:1%; width:100%;"></div>
              <div id="humidity" style="float:left; margin-left:10px; height:6%; width:98%;"></div>
              <div id="bottomDiv" style="float:left; height:65%; width:100%;">
                  <div style="float:left; height:10%; width:100%;">
                     <input type="button" id="operateHum" value="空调运行及报警状态监测" style="margin:10px; height:24px;" />
                  </div>                  
                  <div style="float:left; margin-top:10px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调制冷：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="是" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:10px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调加热：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调加湿：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="是" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调除湿：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>回风温度过高：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>回风温度过低：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>回风湿度过高：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>回风湿度过低：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>   
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>送风温度过高：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>送风温度过低：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>送风湿度过高：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>送风湿度过低：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>水温过高：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>水温过低：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="否" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调温度：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="24℃" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调湿度：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="36%" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调白天设定温度：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="24℃" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调夜间设定温度：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="27℃" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                        <label>空调开关机状态：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                        <input id="hum" value="开机" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div> 
                  <div style="float:left; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:50%;">
                       <label>空调运行时间：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:50%;">
                       <input id="hum" value="308h" style="width:160px; height:24px; background-color:#f5f5f5" type="text" />
                     </div>
                  </div>  
              </div>
           </div>
        </div>
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
    </div> 
    
    <script type="text/javascript">
      $(document).ready(function() {
    	  var currentImg = '<%=basePath%>static/img/device/aimosheng.png';
          document.getElementById("devicePic").style.backgroundImage="url("+ currentImg +")";
      });
      
      $("#conditionerBtn").click(function(){
    	  window.location.href = "<%=basePath%>loadDeviceManage.do";
      });
      
      $("#upsBtn").click(function(){
    	  window.location.href = "<%=basePath%>loadDeviceManageUPS.do";
      });
      
      $("#acquistionBtn").click(function(){
    	  window.location.href = "<%=basePath%>loadDeviceAcquisitionConfig.do";
      });
      
      $("#cabinetBtn").click(function(){
    	  window.location.href = "<%=basePath%>loadDeviceManageCabinet.do";
      });
      
      
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
    		  itemStyle: {
    	         normal: {
    	            color: '#63B8FF'
    	         }
    	      },
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
    		  itemStyle: {
     	         normal: {
     	            color: '#66CD00'
     	         }
     	      },
    		  data: [30]
    		}
    	  ]
      };
      humChart.setOption(humOption, true);
	</script>
  </body>
</html>