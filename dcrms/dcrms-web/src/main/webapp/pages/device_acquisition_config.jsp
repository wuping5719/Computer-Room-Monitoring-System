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
              <div id="deviceName" style="float:left; height:2%; width:100%; "><label style="margin-top:40px;" >数据采集器</label></div>
              <div id="devicePic" style="float:left; height:42%; width:100%; background: center no-repeat; background-size:90%;"></div>
              <div id="parameter" style="float:left; height:46%; width:100%; ">
                 <div id="temperature" style="float:left; margin-left:10px; height:36%; width:98%;"></div>
                 <div id="humidity" style="float:left; margin-left:10px; height:36%; width:98%;"></div>
              </div>
           </div>
           <div id="rightPanel" style="float:left; height:100%; width:70%;">
              <div id="operate" style="float:left; height:10%; width:100%;">
                  <label style="margin-left:2px;">采集周期(s)：</label>
                  <input id="temp" value="5" style="width:80px; height:24px;" type="text" />
                  <label style="margin-left:40px;">数据库IP：</label>
                  <input id="temp" value="222.159.131.156" style="width:120px; height:24px;" type="text" />
                  <label style="margin-left:40px;">端口：</label>
                  <input id="hum" value="6860" style="width:80px; height:24px;" type="text" />
                  <input type="button" id="operateHum" value="开始采集" style="margin:20px 20px; height:24px;" />
              </div>
              <div style="float:left; height:2%; width:100%;"></div>
              <div id="bottomDiv" style="float:left; height:65%; width:100%;">
                  <div style="float:left; height:10%; width:100%;">
                     <input type="button" id="operateHum" value="数据采集器运行及报警状态监测" style="margin:10px; height:24px;" />
                  </div>                  
                  <div style="float:left; margin-top:10px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>温度1：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="25.6℃" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:10px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>湿度1：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="41.2% RH" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>温度2：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="24.3℃" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>湿度2：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="39.2% RH" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>温度3：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="26.1℃" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>湿度3：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="42.8% RH" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>温度4：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="27.2℃" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>湿度3：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="51.0% RH" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>水浸1：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>水浸2：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>烟感1：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>烟感2：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>红外1：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="异常" style="width:160px; height:24px; background-color:#f5f5f5; color:#f00;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>红外2：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>火感1：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>火感2：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>门磁1：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:40%;">
                       <label>门磁2：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:60%;">
                       <input id="hum" value="正常" style="width:160px; height:24px; background-color:#f5f5f5;" type="text" />
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
    	  var currentImg = '<%=basePath%>static/img/device/acqu.png';
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