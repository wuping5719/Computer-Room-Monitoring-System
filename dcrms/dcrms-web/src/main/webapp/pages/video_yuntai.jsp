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
<title>站点视频</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">   <!-- 初始化移动浏览显示  -->

 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/sweet-alert.css" rel="stylesheet" type="text/css" />
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/js/sweet-alert.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/video/swfobject.js" type="text/JavaScript" ></script>
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
           <div id="siteDiv" style="text-align:left;">
		      <label style="margin-left:30px; margin-top:10px" >当前所在城市：青岛  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </label>
              <label style="margin-top:10px" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 机房站点：</label>
		      <select style="margin-top:10px; width:120px" >
			     <option value="金家岭">金家岭</option>
			     <option value="李村">李村</option>
			     <option value="明星大厅">明星大厅</option>
			     <option value="人民一路">人民一路</option>
			     <option value="浮山后">浮山后</option>
			     <option value="王哥庄">王哥庄</option>
		      </select>
		      
		      <input type="button" id="playbackBtn" value="视频回放" style="margin:5px 100px 0 320px; height:24px;" />
              <input type="button" id="yunTaiBtn" value="云台控制" style="margin:5px 0 0 5px; height:24px;" />
           </div>
           <div id="yunTaiDiv" >
			  <div id="yunTaiVideo" >
			     <div style="float:left; width:50%; heigth:100%;">
				     <div id="videoPlay" style="margin:10px 30px; width:100%;"></div>
				 </div>
			     <div id="yunTaiMenu" style="float:left; margin:10px 30px 10px 60px; width:40%; border:1px solid #617775;">
			        <table style="text-align:center; vertical-align:middle; width:100%; height:100%;">
			            <tr style="height:42px;">
			               <td>
			                  <label>用户名：</label>
                              <input id="viUserName" style="width:80px; height:24px;" type="text" />
			               </td>
			               <td></td>
			               <td>
			                  <label>密码：</label>
                              <input id="viPassword" style="width:80px; height:24px;" type="text" />
                           </td>
			            </tr>
			            <tr style="height:42px;">
			               <td></td>
			               <td><input type="button" id="upperBtn" value="上" style="width:80px; height:24px;" /></td>
			               <td></td>
			            </tr>
			            <tr style="height:42px;">
			               <td><input type="button" id="leftBtn" value="左" style="width:80px; height:24px;" /></td>
			               <td></td>
			               <td><input type="button" id="rightBtn" value="右" style="width:80px; height:24px;" /></td>
			            </tr>
			            <tr style="height:42px;">
			               <td></td>
			               <td><input type="button" id="downBtn" value="下" style="width:80px; height:24px;" /></td>
			               <td></td>
			            </tr>
			            <tr style="height:42px;">
			               <td><input type="button" id="shrinkBtn" value="缩" style="width:80px; height:24px;" /></td>
			               <td><label>调焦</label></td>
			               <td><input type="button" id="stretchBtn" value="伸" style="width:80px; height:24px;" /></td>
			            </tr>
			            <tr style="height:42px;">
			               <td><input type="button" id="nearBtn" value="近" style="width:80px; height:24px;" /></td>
			               <td><label>聚焦</label></td>
			               <td><input type="button" id="farBtn" value="远" style="width:80px; height:24px;" /></td>
			            </tr>
			            <tr style="height:42px;">
			               <td><input type="button" id="largeBtn" value="大" style="width:80px; height:24px;" /></td>
			               <td><label>光圈</label></td>
			               <td><input type="button" id="smallBtn" value="小" style="width:80px; height:24px;" /></td>
			            </tr>
			            <tr style="height:42px;">
			               <td><label>云台速度：</label></td>
			               <td></td>
			               <td>
			                  <select style="width:80px;" >
			                      <option value="1">1</option>
			                      <option value="2">2</option>
			                      <option value="3">3</option>
			                      <option value="4">4</option>
			                      <option value="5">5</option>
			                      <option value="6">6</option>
		                      </select>
		                   </td>
			            </tr>
			            <tr style="height:42px;">
			               <td><input type="button" id="startBtn" value="开始" style="width:80px; height:24px;" /></td>
			               <td><input type="button" id="stopBtn" value="停止" style="width:80px; height:24px;" /></td>
			               <td><input type="button" id="watchBtn" value="云台守望" style="width:80px; height:24px;" /></td>
			            </tr>
			            <tr style="height:42px;">
			               <td><input type="button" id="playBtn" value="播放" style="width:80px; height:24px;" /></td>
			               <td><input type="button" id="videotapeBtn" value="录像" style="width:80px; height:24px;" /></td>
			               <td><input type="button" id="captureBtn" value="抓图" style="width:80px; height:24px;" /></td>
			            </tr>
			        </table>
				 </div>
			  </div>
            </div>
         </div>
      </div> 
     
      <script type="text/javascript">  
        //创建播放器对象  
        var player = new SWFObject('<%=basePath%>/static/video/player.swf','jw','100%','420px','12','#000000');  
        player.addParam('allowfullscreen','true');  
        player.addParam('allowscriptaccess','always');  
        player.addParam('wmode','opaque');  
        player.addParam('flashvars','file=<%=basePath%>/static/video/IMG_1189.flv&bufferlength=20&stretching=uniform&repeat=always&autostart=false');    
        player.write('videoPlay'); 
      
        $("#playbackBtn").click(function(){
      	    window.location.href = "<%=basePath%>loadVideoPlayback.do";
        });
        
        $("#yunTaiBtn").click(function(){
      	    window.location.href = "<%=basePath%>loadVideoYuntai.do";
        });
      </script>  
      
      <div id="footer">
         <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
   </div> 
  </body>
</html>