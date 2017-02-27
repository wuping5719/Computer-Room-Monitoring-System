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
              <div id="deviceName" style="float:left; height:2%; width:100%; "><label style="margin-top:30px;" >山特UPS</label></div>
              <div id="devicePic" style="float:left; height:88%; width:100%; background: center no-repeat; background-size:85%;"></div>
           </div>
           <div id="rightPanel" style="float:left; height:100%; width:70%;">
              <div id="mainsVoltage" style="float:left; height:36%; width:33%;">
              </div>
              <div id="outputVoltage" style="float:left; height:36%; width:33%;">
              </div>
              <div id="outputCurrent" style="float:left; height:36%; width:33%;">
              </div>
              <div id="history" style="float:left; height:64%; width:50%;">
              </div>
              <div id="upsState" style="float:left; height:64%; width:50%;">
                 <div style="float:left; height:10%; width:100%;">
                     <input type="button" id="operateHum" value="UPS运行及报警状态监测" style="margin:10px; height:24px;" />
                  </div>                  
                  <div style="float:left; margin-top:10px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>A相功率：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="5.1KW" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:10px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>B相功率：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="8.2KW" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>C相功率：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="6.0KW" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>温度：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="30.8℃" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>功率因素：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="80%" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>频率：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="50.6HZ" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>负载保护：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="正常" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>UPS连合状态：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="正常" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>UPS电池供电：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="正常" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>电池电压状态：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="正常" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>电池供电不足告警：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="正常" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>紧急停机告警：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="正常" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
                     </div>
                  </div>
                  <div style="float:left; margin-top:8px; height:10%; width:45%;">
                     <div style="float:left; text-align:right; height:100%; width:80%;">
                       <label>保护停机告警：</label>
                     </div>
                     <div style="float:left; text-align:left; height:100%; width:20%;">
                       <input id="hum" value="正常" style="width:60px; height:24px; background-color:#f5f5f5;" type="text" />
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
    	  var currentImg = '<%=basePath%>static/img/device/ups.png';
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
      
      var mainsVoltageData = [218, 236, 252];
      var mainsVoltageYMax = 300;
      var mainsVoltageDataShadow = [];
      for (var i = 0; i < mainsVoltageData.length; i++) {
    	  mainsVoltageDataShadow.push(mainsVoltageYMax);
      }
      var mainsVoltageOption = {
    		title: {
    			left: 'center',
    		    subtext: '市电电压'
    		},
    		tooltip: {
    			 trigger: 'axis',
    			 formatter: "{b} : {c1}V",
    		     axisPointer : {        // 坐标轴指示器，坐标轴触发有效
    		         type : 'shadow'    // 默认为直线，可选为：'line' | 'shadow'
    		     }
      	    },
      	    grid: {
               left: '3%',
               right: '4%',
               bottom: '3%',
               containLabel: true
            },
    		xAxis: {
    			type : 'category',
                data : ['A相','B相','C相']
    		},
    		yAxis: {
    	        type : 'value',
    	        name : '电压(V)'
    		},
    		dataZoom: [
    		   {
    		      type: 'inside'
    		   }
    		],
    		series: [
    		  { // For shadow
    		       type: 'bar',
    		       itemStyle: {
    		           normal: {color: 'rgba(0,0,0,0.05)'}
    		       },
    		       barGap:'-100%',
    		       barCategoryGap:'40%',
    		       data: mainsVoltageDataShadow,
    		       animation: false
    		   },
               {
    		       type: 'bar',
    		       itemStyle: {
    		           normal: {
    		                color: new echarts.graphic.LinearGradient(
    		                     0, 0, 0, 1,
    		                     [
    		                       {offset: 0, color: '#83bff6'},
    		                       {offset: 0.5, color: '#188df0'},
    		                       {offset: 1, color: '#188df0'}
    		                     ]
    		                 )
    		           },
    		           emphasis: {
    		                color: new echarts.graphic.LinearGradient(
    		                     0, 0, 0, 1,
    		                     [
    		                        {offset: 0, color: '#2378f7'},
    		                        {offset: 0.7, color: '#2378f7'},
    		                        {offset: 1, color: '#83bff6'}
    		                     ]
    		                 )
    		            }
    		       },
    		       data: mainsVoltageData
    		  }
    	   ]
       };
      
       var mainsVoltageChart = echarts.init(document.getElementById('mainsVoltage'));
       mainsVoltageChart.setOption(mainsVoltageOption, true);
       
       var outputVoltageData = [232, 236, 252];
       var outputVoltageYMax = 300;
       var outputVoltageDataShadow = [];
       for (var i = 0; i < outputVoltageData.length; i++) {
    	   outputVoltageDataShadow.push(outputVoltageYMax);
       }
       var outputVoltageOption = {
     		title: {
     			left: 'center',
     		    subtext: '输出电压'
     		},
     		tooltip: {
     			 trigger: 'axis',
     			 formatter: "{b} : {c1}V",
     		     axisPointer : {        // 坐标轴指示器，坐标轴触发有效
     		         type : 'shadow'    // 默认为直线，可选为：'line' | 'shadow'
     		     }
       	    },
       	    grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
             },
     		xAxis: {
     			type : 'category',
                 data : ['A相','B相','C相']
     		},
     		yAxis: {
     	        type : 'value',
     	        name : '电压(V)'
     		},
     		dataZoom: [
     		   {
     		      type: 'inside'
     		   }
     		],
     		series: [
     		  { // For shadow
     		       type: 'bar',
     		       itemStyle: {
     		           normal: {color: 'rgba(0,0,0,0.05)'}
     		       },
     		       barGap:'-100%',
     		       barCategoryGap:'40%',
     		       data: outputVoltageDataShadow,
     		       animation: false
     		   },
                {
     		       type: 'bar',
     		       itemStyle: {
     		           normal: {
     		                color: new echarts.graphic.LinearGradient(
     		                     0, 0, 0, 1,
     		                     [
     		                       {offset: 0, color: '#83bff6'},
     		                       {offset: 0.5, color: '#188df0'},
     		                       {offset: 1, color: '#188df0'}
     		                     ]
     		                 )
     		           },
     		           emphasis: {
     		                color: new echarts.graphic.LinearGradient(
     		                     0, 0, 0, 1,
     		                     [
     		                        {offset: 0, color: '#2378f7'},
     		                        {offset: 0.7, color: '#2378f7'},
     		                        {offset: 1, color: '#83bff6'}
     		                     ]
     		                 )
     		            }
     		       },
     		       data: outputVoltageData
     		  }
     	   ]
     	};
       
        var outputVoltageChart = echarts.init(document.getElementById('outputVoltage'));
        outputVoltageChart.setOption(outputVoltageOption, true);
        
        var outputCurrentData = [22, 58, 76];
        var outputCurrentYMax = 100;
        var outputCurrentDataShadow = [];
        for (var i = 0; i < outputCurrentData.length; i++) {
        	outputCurrentDataShadow.push(outputCurrentYMax);
        }
        var outputCurrentOption = {
      		title: {
      			left: 'center',
      		    subtext: '输出电流'
      		},
      		tooltip: {
      			 trigger: 'axis',
      			 formatter: "{b} : {c1}A",
      		     axisPointer : {        // 坐标轴指示器，坐标轴触发有效
      		         type : 'shadow'    // 默认为直线，可选为：'line' | 'shadow'
      		     }
        	},
            grid: {
                 left: '3%',
                 right: '4%',
                 bottom: '3%',
                 containLabel: true
            },
      		xAxis: {
      			type : 'category',
                  data : ['A相','B相','C相']
      		},
      		yAxis: {
      	        type : 'value',
      	        name : '电流(A)'
      		},
      		dataZoom: [
      		   {
      		      type: 'inside'
      		   }
      		],
      		series: [
      		  { // For shadow
      		       type: 'bar',
      		       itemStyle: {
      		           normal: {color: 'rgba(0,0,0,0.05)'}
      		       },
      		       barGap:'-100%',
      		       barCategoryGap:'40%',
      		       data: outputCurrentDataShadow,
      		       animation: false
      		   },
                 {
      		       type: 'bar',
      		       itemStyle: {
      		           normal: {
      		                color: new echarts.graphic.LinearGradient(
      		                     0, 0, 0, 1,
      		                     [
      		                       {offset: 0, color: '#83bff6'},
      		                       {offset: 0.5, color: '#188df0'},
      		                       {offset: 1, color: '#188df0'}
      		                     ]
      		                 )
      		           },
      		           emphasis: {
      		                color: new echarts.graphic.LinearGradient(
      		                     0, 0, 0, 1,
      		                     [
      		                        {offset: 0, color: '#2378f7'},
      		                        {offset: 0.7, color: '#2378f7'},
      		                        {offset: 1, color: '#83bff6'}
      		                     ]
      		                 )
      		            }
      		       },
      		       data: outputCurrentData
      		  }
      	   ]
      	};
        
        var outputCurrentChart = echarts.init(document.getElementById('outputCurrent'));
        outputCurrentChart.setOption(outputCurrentOption, true);
         
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
        	                5,6,8,12,10,8,9,12,7,11
        	            ]
        	        }
        	    ]
        };
        
        var historyChart = echarts.init(document.getElementById('history'));
        historyChart.setOption(historyOption, true);
	</script>
  </body>
</html>