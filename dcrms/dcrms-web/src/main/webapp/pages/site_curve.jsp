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
<meta name="viewport" content="width=device-width, initial-scale=1">   <!-- 初始化移动浏览显示  -->

 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/js/main.js" type="text/javascript" ></script>
 
 <script src="<%=basePath%>static/echarts/echarts.js" type="text/javascript"></script>
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
		
		<div>
		    <label>当前所在城市：青岛  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </label>
            <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 机房站点：</label>
            <select style="width:120px" >
			    <option value="金家岭">金家岭</option>
			    <option value="李村">李村</option>
			    <option value="明星大厅">明星大厅</option>
			    <option value="人民一路">人民一路</option>
			    <option value="浮山后">浮山后</option>
			    <option value="王哥庄">王哥庄</option>
		    </select>
        </div>

        <div id="panel"></div>
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
    </div> 
    
    <script type="text/javascript">
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
    
     var dom = document.getElementById("panel");
     var myChart = echarts.init(dom);
     var option = null;
     option = {
         /* title: {
            text: '机房站点实时数据'
            //subtext: '纯属虚构'
         }, */
         tooltip: {
           trigger: 'axis'
         },
         legend: {
            data:['回风温度(℃)', '外部温度(℃)', '回风湿度(%)', 'A相电压(V)', 'A相电流(A)']
         },
         color:[ '#FF3333',    //温度1曲线颜色
                 '#53FF53',    //温度2曲线颜色
                 '#B15BFF',    //湿度曲线颜色
                 '#68CFE8',    //电压曲线颜色
                 '#FFDC35'     //电流曲线颜色
         ],
         /* grid: {
        	show: true,
			backgroundColor: '#eeffff'
		 }, */
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
             name: '温度',
             position: 'left',
             offset: 45,
             axisLabel : {
                 formatter: '{value} ℃'    //控制输出格式
             }
          },{
             type: 'value',
             scale: false,
             name: '湿度',
             position: 'left',
             axisLabel : {
                 formatter: '{value} %'
             }
          },{
              type: 'value',
              scale: true,
              name: '电压',
              position: 'right',
              axisLabel : {
                  formatter: '{value} V'
              }
          },{
              type: 'value',
              scale: true,
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
                    res.push((Math.random() * 10 + 20).toFixed(2));
                }
                return res;
            })()
          },{
             name:'外部温度(℃)', 
             type:'line',
             symbol:'emptycircle',
             smooth: true,
             yAxisIndex: 0,
             data:(function (){
                var res = [];
                var len = 0;
                while (len < 10) {
                    res.push((Math.random() * 10 + 30).toFixed(2));
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
                      res.push((Math.random() * 10 + 40).toFixed(2));
                      len++;
                  }
                  return res;
              })()
          },{
              name:'A相电压(V)',
              type:'line',
              symbol:'circle',  //circle：实心圆；emptydiamond：菱形 
              smooth: true,
              yAxisIndex: 2,
              data:(function (){
                  var res = [];
                  var len = 0;
                  while (len < 10) {
                      res.push((Math.random() * 10 + 215).toFixed(2));
                      len++;
                  }
                  return res;
              })()
          },{
              name:'A相电流(A)',
              type:'line',
              symbol:'emptydiamond',  //emptydiamond：菱形 
              smooth: true,
              yAxisIndex: 3,
              data:(function (){
                  var res = [];
                  var len = 0;
                  while (len < 10) {
                      res.push((Math.random() + 5).toFixed(2));
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
       var hums = option.series[2].data;
       var vol = option.series[3].data;
       var cur = option.series[4].data;
       tmp1.shift();
       tmp1.push((Math.random() * 10 + 20).toFixed(2));
       tmp2.shift();
       tmp2.push((Math.random() * 10 + 30).toFixed(2));
       hums.shift();
       hums.push((Math.random() * 10 + 40).toFixed(2));
       vol.shift();
       vol.push((Math.random() * 10 + 215).toFixed(2));
       cur.shift();
       cur.push((Math.random() + 5).toFixed(2));
       
       option.xAxis[0].data.shift();
       option.xAxis[0].data.push(axisData);
       
       myChart.setOption(option);
    }, 3600);

   if (option && typeof option === "object") {
      myChart.setOption(option, true);
   }
   </script>
   
  </body>
</html>