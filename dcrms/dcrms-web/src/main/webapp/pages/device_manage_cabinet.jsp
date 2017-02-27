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
              <div id="deviceName" style="float:left; height:2%; width:100%; "><label style="margin-top:30px;" >机柜</label></div>
              <div id="devicePic" style="float:left; height:88%; width:100%; background: center no-repeat; background-size:85%;"></div>
           </div>
           <div id="rightPanel" style="float:left; height:100%; width:70%;">
              <div id="voltage" style="float:left; height:36%; width:25%;">
              </div>
              <div id="current" style="float:left; height:36%; width:25%;">
              </div>
              <div id="power" style="float:left; height:36%; width:25%;">
              </div>
              <div id="frequency" style="float:left; height:36%; width:25%;">
              </div>
              <div id="cabinetState" style="float:left; height:64%; width:50%;">
                  <div id="temperature" style="float:left; height:30%; width:100%;">
                  </div>
                  <div id="humidity" style="float:left; height:30%; width:100%;">
                  </div>
                  <div id="consumption" style="float:left; height:30%; width:100%;">
                  </div>
              </div>
              <div id="history" style="float:left; height:64%; width:50%;">
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
    	  var currentImg = '<%=basePath%>static/img/device/cabinet.png';
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
      
      var voltageChart = echarts.init(document.getElementById('voltage'));
      var voltageOption = {
    	  tooltip : {
    		 formatter: "{a} : {c}V"
    	  },
          series: [{
    		 name: '电压',
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
      
      var currentChart = echarts.init(document.getElementById('current'));
      var currentOption = {
    	  tooltip : {
    		 formatter: "{a} : {c}A"
    	  },
    	  series: [{
    		 name: '电流',
    		 type: 'gauge',
    		 min: 0,
    	     max: 20,
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
    		      data: [{value: 9, name: '电流'}]
    	  }]
      };
      currentChart.setOption(currentOption, true);
      
      var powerChart = echarts.init(document.getElementById('power'));
      var powerOption = {
    	  tooltip : {
    		 formatter: "{a} : {c}kW"
    	  },
    	  series: [{
    		 name: '功率',
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
    		 detail: {formatter:'{value}kW'},
    		      data: [{value: 6, name: '功率'}]
    	  }]
      };
      powerChart.setOption(powerOption, true);
      
      var frequencyChart = echarts.init(document.getElementById('frequency'));
      var frequencyOption = {
    	  tooltip : {
    		 formatter: "{a} : {c}HZ"
    	  },
    	  series: [{
    		 name: '频率',
    		 type: 'gauge',
    		 min: 0,
    	     max: 80,
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
    		 detail: {formatter:'{value}HZ'},
    		      data: [{value: 50, name: '频率'}]
    	  }]
      };
      frequencyChart.setOption(frequencyOption, true);
      
      setInterval(function () {
    	  voltageOption.series[0].data[0].value = (Math.random() * 5 + 218).toFixed(2) - 0;
    	  voltageChart.setOption(voltageOption, true);
    	  
    	  currentOption.series[0].data[0].value = (Math.random() * 4 + 8).toFixed(2) - 0;
    	  currentChart.setOption(currentOption, true);
    	  
    	  powerOption.series[0].data[0].value = (Math.random() + 5).toFixed(2) - 0;
    	  powerChart.setOption(powerOption, true);
    	  
    	  frequencyOption.series[0].data[0].value = (Math.random() * 3 + 48).toFixed(2) - 0;
    	  frequencyChart.setOption(frequencyOption, true);
      },2000);
         
      var historyOption = {
        	    title: {
        	        subtext: '历史波动：输出电压/电流',
        	        x: 'center'
        	    },
        	    tooltip: {
        	        trigger: 'axis',
        	        axisPointer: {
        	            animation: false
        	        }
        	    },
        	    grid: [{
        	    	left: 40,
        	        right: 40,
        	        bottom: 40
        	    }],
        	    xAxis : [
        	        {
        	            type : 'category',
        	            data : [
        	                    '2017-02-14 10:05:00', '2017-02-14 10:10:00', 
        	                    '2017-02-14 10:15:00', '2017-02-14 10:20:00',
        	                    '2017-02-14 10:25:00', '2017-02-14 10:30:00',
        	                    '2017-02-14 10:35:00', '2017-02-14 10:40:00',
        	                    '2017-02-14 10:45:00', '2017-02-14 10:50:00'
        	                ].map(function (str) {
        	                    return str.replace(' ', '\n');
        	              })
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            name : '电压(V)',
        	            type : 'value'
        	        },
        	        {
        	            name : '电流(A)',
        	            type : 'value',
        	            nameLocation: 'start',
        	            inverse: true
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'电压',
        	            type:'line',
        	            symbolSize: 8,
        	            hoverAnimation: false,
        	            data:[
        	                56,120,180,210,232,228,226,222,226,230
        	            ]
        	        },
        	        {
        	            name:'电流',
        	            type:'line',
        	            yAxisIndex: 1,
        	            symbolSize: 8,
        	            hoverAnimation: false,
        	            data: [
                           5,6,8,12,10,8,9,16,12,8
        	            ]
        	        }
        	    ]
        };
        
        var historyChart = echarts.init(document.getElementById('history'));
        historyChart.setOption(historyOption, true);
        
        
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
        
        var consumptionChart = echarts.init(document.getElementById('consumption'));
        var consumptionOption = {
      	  tooltip: {
      		  trigger: 'axis',
      		  formatter: "{a} : {c}kW/h",
      		  axisPointer: {
      		     type: 'shadow'
      		  }
      	  },
      	  xAxis: {
      	      type: 'value',
      	      boundaryGap: [0, 0.01],
      	      min: 0,
      	      max: 20
      	  },
      	  yAxis: {
      		  type: 'category',
      		  data: ['耗电量']
      	  },
      	  series: [{
      		  name: '耗电量',
      		  type: 'bar',
      		  itemStyle: {
       	         normal: {
       	            color: '#CD0000'
       	         }
       	      },
      		  data: [5.6]
      		}
      	  ]
        };
        consumptionChart.setOption(consumptionOption, true);
	</script>
  </body>
</html>