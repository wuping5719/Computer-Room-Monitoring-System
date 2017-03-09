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
 <script src="<%=basePath%>static/echarts/echarts.js" type="text/javascript"></script>
</head>
 
<body>
    <div id="container">
        <div id="curveDiv" style="width:320px; height:480px"></div> 
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
    
     var dom = document.getElementById("curveDiv");
     var myChart = echarts.init(dom);
     var option = null;
     option = {
          title: {
            //text: '李村站点数据曲线',
            //subtext: '李村站点数据曲线'
         },
         tooltip: {
           trigger: 'axis'
         },
         legend: {
            data:['回风温度(℃)', '水温(℃)', '回风湿度(%)' ]
         }, 
         color:[ '#FF3333',    //温度1曲线颜色
                 '#53FF53',    //温度2曲线颜色
                 '#B15BFF'     //湿度曲线颜色
         ],
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
             min: -20,
             max: 50,
             name: '温度',
             position: 'left'
          },{
             type: 'value',
             scale: false,
             min: 0,
             max: 100,
             name: '湿度',
             position: 'right'
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
          }
       ]
     };

    setInterval(function (){
       axisData = CurentTime();
       var tmp1 = option.series[0].data;
       var tmp2 = option.series[1].data;
       var hums1 = option.series[2].data;
       tmp1.shift();
       tmp1.push((Math.random() * 6 + 26).toFixed(2));
       tmp2.shift();
       tmp2.push((Math.random() * 6 + 24).toFixed(2));
       hums1.shift();
       hums1.push((Math.random() * 5 + 40).toFixed(2));
       
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