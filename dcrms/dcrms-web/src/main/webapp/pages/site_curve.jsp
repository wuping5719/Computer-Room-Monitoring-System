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
<title>站点曲线</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">   <!-- 初始化移动浏览显示  -->

 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <link href="<%=basePath%>static/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
 
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/echarts/echarts.js" type="text/javascript"></script>
 <script src="<%=basePath%>static/jquery-ui-1.11.4/external/jquery/jquery.js"></script>
 <script src="<%=basePath%>static/jquery-ui-1.11.4/jquery-ui.js"></script>
 <script src="<%=basePath%>static/js/main.js" type="text/javascript" ></script>
 
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
        <div id="panel">
             <div id="queryDiv">
		        <label style="margin-top:6px;" >当前所在城市：青岛  &nbsp;&nbsp;&nbsp; </label>
                <label style="margin-top:6px;" >&nbsp;&nbsp;&nbsp; 机房站点：</label>
                <select style="margin-top:6px; width:120px;" >
			        <option value="金家岭">金家岭</option>
			        <option value="李村">李村</option>
			        <option value="明星大厅">明星大厅</option>
			        <option value="人民一路">人民一路</option>
			        <option value="浮山后">浮山后</option>
			        <option value="王哥庄">王哥庄</option>
		        </select>
		        <label style="margin-top:6px;" >&nbsp;&nbsp;&nbsp;&nbsp;设备：</label>
		        <select style="margin-top:6px; width:120px;" >
			        <option value="精密空调">精密空调</option>
			        <option value="市电输入">市电输入</option>
			        <option value="UPS">UPS</option>
			        <option value="采集器">采集器</option>
			        <option value="发电机">发电机</option>
			        <option value="C5000">C5000</option>
		        </select>
		        
		        <input type="button" id="vrImg" value="站点虚拟图" style="margin-left:120px; height:24px;" />
		        <input type="button" id="dataList" value="数据列表" style="margin-left:10px; height:24px;" />
             </div>
             <div id="curveDiv" style="margin-top:6px; width:98%; height:92%;"> </div>
        </div>
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
    </div> 
    
    <script type="text/javascript">
    $("#vrImg").click(function(){
    	window.location.href = "<%=basePath%>loadSite3dView.do";
	});
    
    function CurentTime(){ 
        var now = new Date();
        
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        
        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();           //秒
        
        var clock = year + "-";
        
        if(month < 10)
            clock += "0";
        
        clock += month + "-";
        
        if(day < 10)
            clock += "0";
            
        clock += day + " ";
        
        if(hh < 10)
            clock += "0";
            
        clock += hh + ":";
        if (mm < 10) clock += '0'; 
        clock += mm + ":"; 
         
        if (ss < 10) clock += '0'; 
        clock += ss; 
        return(clock); 
     }
    
     var dom = document.getElementById("curveDiv");
     var myChart = echarts.init(dom);
     var option = null;
     option = {
         /* title: {
            text: '站点实时数据曲线'
            subtext: '站点实时数据曲线'
         }, */
         tooltip: {
           trigger: 'axis'
         },
         legend: {
            data:['回风温度(℃)', '送风温度(℃)', '水温(℃)', '回风湿度(%)', '送风湿度(%)', '输入电压(V)', '输入电流(A)']
         },
         /* 
         color:[ '#FF3333',    //温度1曲线颜色
                 '#53FF53',    //温度2曲线颜色
                 '#B15BFF',    //湿度曲线颜色
                 '#68CFE8',    //电压曲线颜色
                 '#FFDC35'     //电流曲线颜色
         ],
         grid: {
        	show: true,
			backgroundColor: '#eeffff'
		 },  */
         toolbox: {
            show: true,
            feature: {
              dataView: {readOnly: false},
              restore: {},
              saveAsImage: {}
            }
         },
         dataZoom: [{
             type: 'slider',      //支持鼠标滚轮缩放
             start: 0,            //默认数据初始缩放范围为10%到90%
             end: 100
           },{
             type: 'inside',     //支持单独的滑动条缩放
             start: 0,           //默认数据初始缩放范围为10%到90%
             end: 100
         }],
         xAxis: [
           {
              type: 'category',
              boundaryGap: true,
              axisTick: {
            	 show: true,
            	 alignWithLabel: true
              },
              data: (function (){
                 var res = [];
                 var len = 10;
                 while (len--) {
                    res.unshift(CurentTime());
                 }
                 return res;
             })()
           }
        ],
        yAxis: [{
             type: 'value',
             scale: true,
             min: -20,
             max: 50,
             name: '温度',
             position: 'left',
             offset: 45,
             axisLabel : {
                 formatter: '{value} ℃'    //控制输出格式
             }
          },{
             type: 'value',
             scale: false,
             min: 0,
             max: 100,
             name: '湿度',
             position: 'left',
             axisLabel : {
                 formatter: '{value} %'
             }
          },{
              type: 'value',
              scale: true,
              min: 0,
              max: 300,
              name: '电压',
              position: 'right',
              axisLabel : {
                  formatter: '{value} V'
              }
          },{
              type: 'value',
              scale: true,
              min: 0,
              max: 8,
              name: '电流',
              position: 'right',
              offset: 45,
              axisLabel : {
                  formatter: '{value} A'
              }
          }
        ],
        series: [{
             name:'回风温度(℃)',
             type:'line',
             symbol:'emptycircle',    //设置折线图中表示每个坐标点的符号: emptycircle：空心圆.
             smooth: true,
             yAxisIndex: 0,
             data:(function (){
                var res = [];
                var len = 10;
                while (len--) {
                    res.push((Math.random() * 6 + 26).toFixed(2));
                }
                return res;
            })()
          },{
             name:'送风温度(℃)', 
             type:'line',
             symbol:'emptycircle',
             smooth: true,
             yAxisIndex: 0,
             data:(function (){
                var res = [];
                var len = 0;
                while (len < 10) {
                    res.push((Math.random() * 6 + 24).toFixed(2));
                    len++;
                }
                return res;
             })()
          },{
              name:'水温(℃)', 
              type:'line',
              symbol:'emptycircle',
              smooth: true,
              yAxisIndex: 0,
              data:(function (){
                 var res = [];
                 var len = 0;
                 while (len < 10) {
                     res.push((Math.random() * 6 + 16).toFixed(2));
                     len++;
                 }
                 return res;
              })()
           },{
              name:'回风湿度(%)',
              type:'line',
              symbol:'emptyrect',  //emptyrect：空心矩形 
              smooth: true,
              yAxisIndex: 1,
              data:(function (){
                  var res = [];
                  var len = 0;
                  while (len < 10) {
                      res.push((Math.random() * 5 + 40).toFixed(2));
                      len++;
                  }
                  return res;
              })()
          },{
              name:'送风湿度(%)',
              type:'line',
              symbol:'emptyrect',  //emptyrect：空心矩形 
              smooth: true,
              yAxisIndex: 1,
              data:(function (){
                  var res = [];
                  var len = 0;
                  while (len < 10) {
                      res.push((Math.random() * 5 + 38).toFixed(2));
                      len++;
                  }
                  return res;
              })()
          },{
              name:'输入电压(V)',
              type:'line',
              symbol:'circle',  //circle：实心圆；emptydiamond：菱形 
              smooth: true,
              yAxisIndex: 2,
              data:(function (){
                  var res = [];
                  var len = 0;
                  while (len < 10) {
                      res.push((Math.random() * 8 + 215).toFixed(2));
                      len++;
                  }
                  return res;
              })()
          },{
              name:'输入电流(A)',
              type:'line',
              symbol:'emptydiamond',  //emptydiamond：菱形 
              smooth: true,
              yAxisIndex: 3,
              data:(function (){
                  var res = [];
                  var len = 0;
                  while (len < 10) {
                      res.push((Math.random() + 2).toFixed(2));
                      len++;
                  }
                  return res;
              })()
          }
       ]
     };

    setInterval(function (){
       axisData = CurentTime();
       var tmp1 = option.series[0].data;
       var tmp2 = option.series[1].data;
       var tmp3 = option.series[2].data;
       var hums1 = option.series[3].data;
       var hums2 = option.series[4].data;
       var vol = option.series[5].data;
       var cur = option.series[6].data;
       tmp1.shift();
       tmp1.push((Math.random() * 6 + 26).toFixed(2));
       tmp2.shift();
       tmp2.push((Math.random() * 6 + 24).toFixed(2));
       tmp3.shift();
       tmp3.push((Math.random() * 6 + 16).toFixed(2));
       hums1.shift();
       hums1.push((Math.random() * 5 + 40).toFixed(2));
       hums2.shift();
       hums2.push((Math.random() * 5 + 38).toFixed(2));
       vol.shift();
       vol.push((Math.random() * 8 + 216).toFixed(2));
       cur.shift();
       cur.push((Math.random() + 2).toFixed(2));
       
       option.xAxis[0].data.shift();
       option.xAxis[0].data.push(axisData);
       
       myChart.setOption(option);
    }, 3600);

   if (option && typeof option === "object") {
      myChart.setOption(option, true);
   }
   </script>
   
   <div id="dataDialog" title="数据列表">
	   <div id="selectDiv" style="float:left; width:25%; height:100%;">
	       <div id="selectSite" style="width:100%; height:20%; border:1px solid #617775;">
	           <ul>
		           <li><label style="margin-top:5px;" >当前所在城市：青岛 </label></li>
		           <li>
		               <label style="margin-top:5px;" >机房站点：</label>
		               <select style="margin-top:5px; width:100px;" >
			              <option value="金家岭">金家岭</option>
			              <option value="李村">李村</option>
			              <option value="明星大厅">明星大厅</option>
			              <option value="人民一路">人民一路</option>
			              <option value="浮山后">浮山后</option>
			              <option value="王哥庄">王哥庄</option>
		               </select>
		           </li>
	            </ul>
	        </div>
	           
	        <div id="selectEqu" style="margin-top:5px; width:100%; height:78%; border:1px solid #617775;">
                <ul>
		            <li><label><input id="equ1" type="checkbox" style="margin-top:5px;" />采集器 </label></li>
		            <li><label><input id="equ2" type="checkbox" style="margin-top:5px;" />C5000</label></li>
		            <li><label><input id="equ3" type="checkbox" style="margin-top:5px;" />LX1配电柜 </label></li>
		            <li><label><input id="equ4" type="checkbox" style="margin-top:5px;" />艾默生空调DM12-1</label></li>
		            <li><label><input id="equ5" type="checkbox" style="margin-top:5px;" />艾默生UPS1</label></li>
		            <li><label><input id="equ6" type="checkbox" style="margin-top:5px;" />发电机 </label></li>
	            </ul>
		   </div>
      </div>
	  <div id="dataDiv" style="float:left; margin-left:5px; width:74%; height:99%; border:1px solid #617775;">
	     <div id="tabs" style="width:100%; height:92%;">
	         <ul>
		        <li><a href="#tabs-analog">模拟量</a></li>
		        <li><a href="#tabs-switch">开关量</a></li>
		        <li><a href="#tabs-download">数据下载</a></li>
	         </ul>
	         <div id="tabs-analog" >
	            <div style="width:100%; height:100%; overflow:auto;">
	               <table id="analogData" style="text-align:center; table-layout:fixed; width:100%; height:100%;"> 
					   <tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						  <td style="width:150px">时间</td>
						  <td style="width:120px">A相输入电压(V)</td>
						  <td style="width:120px">B相输入电压(V)</td>
						  <td style="width:120px">C相输入电压(V)</td>
						  <td style="width:120px">A相输出电压(V)</td>
						  <td style="width:120px">B相输出电压(V)</td>
						  <td style="width:120px">C相输出电压(V)</td>
						  <td style="width:120px">A相输出电流(A)</td>
						  <td style="width:120px">B相输出电流(A)</td>
						  <td style="width:120px">C相输出电流(A)</td>
						  <td style="width:100px">电池电压(V)</td>
						  <td style="width:100px">输出频率(HZ)</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:10:32</td>
						  <td>214.70</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>214.20</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:11:04</td>
						  <td>214.20</td>
						  <td>220.12</td>
						  <td>222.32</td>
						  <td>211.31</td>
						  <td>221.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:11:36</td>
						  <td>214.60</td>
						  <td>220.14</td>
						  <td>221.02</td>
						  <td>214.79</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:12:02</td>
						  <td>214.72</td>
						  <td>220.30</td>
						  <td>221.12</td>
						  <td>214.10</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:12:32</td>
						  <td>214.30</td>
						  <td>220.80</td>
						  <td>221.31</td>
						  <td>214.80</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:13:06</td>
						  <td>214.10</td>
						  <td>220.40</td>
						  <td>221.32</td>
						  <td>212.21</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:13:31</td>
						  <td>214.30</td>
						  <td>219.50</td>
						  <td>221.32</td>
						  <td>214.01</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:14:01</td>
						  <td>214.70</td>
						  <td>218.50</td>
						  <td>221.32</td>
						  <td>211.90</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:14:32</td>
						  <td>214.70</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>214.22</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:15:03</td>
						  <td>221.20</td>
						  <td>219.30</td>
						  <td>221.32</td>
						  <td>214.56</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:15:32</td>
						  <td>211.70</td>
						  <td>219.50</td>
						  <td>215.32</td>
						  <td>214.20</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:16:03</td>
						  <td>214.70</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>214.10</td>
						  <td>220.50</td>
						  <td>221.32</td>
						  <td>4.90</td>
						  <td>5.52</td>
						  <td>4.61</td>
						  <td>220.80</td>
						  <td>50.02</td>
					   </tr>
				   </table>
				</div>
             </div>
	         <div id="tabs-switch">
	            <div style="width:100%; height:100%; overflow:auto;">
	               <table id="switchData" style="text-align:center; table-layout:fixed; width:100%; height:100%;"> 
					   <tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						  <td style="width:150px">时间</td>
						  <td style="width:60px">烟感1</td>
						  <td style="width:60px">烟感2</td>
						  <td style="width:60px">火感1</td>
						  <td style="width:60px">火感2</td>
						  <td style="width:60px">水浸1</td>
						  <td style="width:60px">水浸2</td>
						  <td style="width:60px">红外1</td>
						  <td style="width:60px">红外2</td>
						  <td style="width:60px">门磁1</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:10:32</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:11:04</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:11:36</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:12:02</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:12:32</td>	
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>		  
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:13:06</td>	
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>		  
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:13:31</td>	
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>					  
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:14:01</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:14:32</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:15:03</td>	
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>					 
					   </tr>
					   <tr style="background-color:#ffffff">
						  <td>2017-01-18 13:15:32</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
					   <tr style="background-color:#eeffff;">
						  <td>2017-01-18 13:16:03</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#ff0000;">异常</td>
						  <td style="color:#00ff00;">正常</td>
						  <td style="color:#00ff00;">正常</td>
					   </tr>
				   </table>
				</div>
	         </div>
	         <div id="tabs-download">
	             <input type="button" id="dataDownload" value="以Excel表格形式下载数据" style="margin-left:200px; height:24px;" />
	         </div>
          </div>
          <div id="tool" style="width:100%; height:8%;">
              <label><input id="realTime" type="checkbox" style="margin-left:280px; margin-top:3px;" />实时</label>
              <label><input id="history" type="checkbox" style="margin-left:60px; margin-top:3px;" />历史 </label>
          </div>
       </div>
    </div>
    
    <script type="text/javascript">
      $("#dataList").click(function(){
    	  $("#dataDialog").dialog("open");
    	  event.preventDefault();
	  });
    
      $("#dataDialog").dialog({
    	   autoOpen: false,
    	   width: 1000,
    	   height: 480
       });
      
       $("#tabs").tabs();
    </script>
  </body>
</html>