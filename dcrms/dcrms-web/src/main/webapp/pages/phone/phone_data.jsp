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
 <script src="http://api.map.baidu.com/api?v=2.0&ak=CBoViYAph0YuVMbpoDFyqGw7DfzUq4AT"></script>
</head>
 
<body>
    <div id="container">
        <div id="panel" style="width:320px; height:480px">
           <table id="alertTab" style="text-align:center; word-wrap:break-word; white-space:nowrap; width:100%; height:90%;"> 
					<tr style="height:30px; font-weight:bold; ">
						<td style="width:160px">报警时间</td>
						<td style="width:100px">报警设备</td>
						<td style="width:120px">报警原因</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-18 19:10:12</td>
						<td >精密空调</td>
						<td >回风温度过高</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-17 10:30:18</td>
						<td >A40</td>
						<td >A40电压超上限</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-16 18:00:19</td>
						<td >艾默生</td>
						<td >直流熔丝1断</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-15 09:20:48</td>
						<td >精密空调</td>
						<td >水温过高</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-13 22:01:10</td>
						<td >精密空调</td>
						<td >模块1压缩机故障</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-10 03:10:56</td>
						<td >精密空调</td>
						<td >模块2气流故障</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-08 22:50:12</td>
						<td >发电机</td>
						<td >发电机低油</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-06 08:40:38</td>
						<td >G5000</td>
						<td >电池寿命终止</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-05 21:00:14</td>
						<td >AMSAPM150</td>
						<td >逆变晶闸管故障</td>
					</tr>
					<tr style="height:30px;">
						<td >2017-01-02 14:36:11</td>
						<td >烟感</td>
						<td >烟感报警</td>
					</tr>
				</table>
				
				<table id="alertPage" style="width:100%; height:10%;">
					<tr style="height:5px;">
                        <td style="width:5px;" ></td>
                        <td style="width:50px;">
                            <img id="logsFirst" class="logsSkip" src="<%=basePath%>static/img/first.png" />
                        </td>
                        <td style="width:50px;">
                            <img id="logsPrevious" class="logsSkip" src="<%=basePath%>static/img/left.png" />
                        </td>
                        <td id="logsCenterBar" style="width:120px;" >
                            <label>1/3页</label>
                        </td>
                        <td style="width:50px;">
                            <img id="logsNext" class="logsSkip" src="<%=basePath%>static/img/right.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="logsEnd" class="logsSkip" src="<%=basePath%>static/img/end.png" />
                        </td>
                        <td style="width:5px;"></td>
                    </tr>
				</table>
        </div> 
    </div> 
  </body>
</html>