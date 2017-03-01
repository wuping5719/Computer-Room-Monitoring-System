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
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/js/main.js" type="text/javascript" ></script>
 
 <script src="<%=basePath%>static/video/swfobject.js" type="text/JavaScript" ></script>
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
           <div style="margin-top:10px; text-align:left;">
              <input type="button" id="playbackBtn" value="视频回放" style="margin:0 10px 0 50px; height:24px;" />
              <input type="button" id="yunTaiBtn" value="云台控制" style="margin:0 120px 0 10px; height:24px;" />
              
              <label style="margin-top:10px" >当前所在城市：青岛  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </label>
              <label style="margin-top:10px" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 机房站点：</label>
		      <select style="margin-top:10px; width:120px" >
			     <option value="金家岭">金家岭</option>
			     <option value="李村">李村</option>
			     <option value="明星大厅">明星大厅</option>
			     <option value="人民一路">人民一路</option>
			     <option value="浮山后">浮山后</option>
			     <option value="王哥庄">王哥庄</option>
		      </select>
           </div>
           
           <div>
              <div style="float:left; margin:15px 30px 5px 30px">
                 <div id="video1" ></div>
                 <label>李村</label>
              </div>
              <div style="float:left; margin:15px 30px 5px 30px">
                 <div id="video2" ></div>
                 <label>台东</label>
              </div>
              <div style="float:left; margin:15px 30px 5px 30px">
                 <div id="video3" ></div>
                 <label>浮山</label>
              </div>
              <div style="float:left; margin:10px 30px 1px 30px">
                 <div id="video4" ></div>
                 <label>城阳</label>
              </div>
              <div style="float:left; margin:10px 30px 1px 30px">
                 <div id="video5" ></div>
                 <label>市南</label>
              </div>
              <div style="float:left; margin:10px 30px 1px 30px">
                 <div id="video6" ></div>
                 <label>市北</label>
              </div>
           </div>
           
           <div>
              <ul class="pagination">
                 <li><a href="#">&laquo;</a></li>
                 <li><a href="#">1</a></li>
                 <li><a href="#">2</a></li>
                 <li><a href="#">3</a></li>
                 <li><a href="#">4</a></li>
                 <li><a href="#">5</a></li>
                 <li><a href="#">6</a></li>
                 <li><a href="#">7</a></li>
                 <li><a href="#">8</a></li>
                 <li><a href="#">9</a></li>
                 <li><a href="#">10</a></li>
                 <li><a href="#">11</a></li>
                 <li><a href="#">12</a></li>
                 <li><a href="#">13</a></li>
                 <li><a href="#">14</a></li>
                 <li><a href="#">15</a></li>
                 <li><a href="#">16</a></li>
                 <li><a href="#">&raquo;</a></li>
              </ul>
           </div>
        </div>
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
      
      <script type="text/javascript">  
        //创建播放器对象  
        var player = new SWFObject('<%=basePath%>/static/video/player.swf','jw','100%','100%','9','#000000');  
        player.addParam('allowfullscreen','true');  
        player.addParam('allowscriptaccess','always');  
        player.addParam('wmode','opaque');  
        player.addParam('flashvars','file=<%=basePath%>/static/video/IMG_1189.flv&bufferlength=20&stretching=uniform&repeat=always&autostart=false');    
        player.write('video1'); 
        player.write('video2');  
        player.write('video3');  
        player.write('video4'); 
        player.write('video5');  
        player.write('video6');  
        
        $("#playbackBtn").click(function(){
      	    window.location.href = "<%=basePath%>loadVideoPlayback.do";
        });
        
        $("#yunTaiBtn").click(function(){
      	    window.location.href = "<%=basePath%>loadVideoYuntai.do";
        });
     </script>  
    </div> 
  </body>
</html>