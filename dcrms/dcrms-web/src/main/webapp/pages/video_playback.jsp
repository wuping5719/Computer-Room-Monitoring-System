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
           <div id="videoDiv">
               <div id="queryDiv" style="text-align:left;">
			      <label style="margin-left:30px;">时间：</label>
			      <input type="text" class="Wdate" id="startDate" 
					  onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd', readOnly:true})"
			          style="margin-top:5px; height:24px; width:100px;"></input>
			      <label>-</label>
			      <input type="text" class="Wdate" id="endDate" 
					  onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd', readOnly:true})"
			          style="margin-top:5px; height:24px; width:100px;"></input>
                  <label style="margin-left:10px;">录像类型：</label>
                  <select id="level" style="width:80px; height:24px;" >
			          <option value="全部">全部</option>
			          <option value="定时录像">定时录像</option>
			          <option value="移动侦测">移动侦测</option>
			          <option value="报警触发">报警触发</option>
			          <option value="命令触发">命令触发</option>
			          <option value="手动录像">手动录像</option>
			          <option value="智能报警">智能报警</option>
			          <option value="无线报警">无线报警</option>
		          </select>
                  <img id="videoSearch" style="margin-left:10px;"
                     src="<%=basePath%>static/img/search.png" />
			</div>
			
			<div id="video" >
			     <div id="videoFile" style="float:left; width:56%;">
				    <table id="filesTab" style="margin:10px 20px;" >
					   <tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						   <td style="width:30px">序号</td>
						   <td style="width:120px">文件名称</td>
						   <td style="width:80px">大小(MB)</td>
						   <td style="width:100px">开始时间</td>
						   <td style="width:100px">结束时间</td>
						   <td style="width:80px">录像类型</td>
						   <td style="display:none">主键ID</td>
					   </tr>
					   <tr onclick="selectRow(this);" style="background-color:#ffffff">
				           <td>1</td>
				           <td>dvr01_2017010800001</td>
				           <td>562</td>
				           <td>2017-01-07 08:00:08</td>
				           <td>2017-01-07 22:00:05</td>
				           <td>定时录像</td>
				           <td style="display:none">1</td>
				       </tr>
				       <tr style="background-color:#f5f5f5">
				           <td>2</td>
				           <td>dvr01_2017011600002</td>
				           <td>675</td>
				           <td>2017-01-07 07:00:02</td>
				           <td>2017-01-07 23:30:15</td>
				           <td>移动侦测</td>
				           <td style="display:none">2</td>
				       </tr>
				       <tr style="background-color:#ffffff">
				           <td>3</td>
				           <td>dvr02_2017010800003</td>
				           <td>562</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>报警触发</td>
				           <td style="display:none">3</td>
				       </tr>
				       <tr style="background-color:#f5f5f5">
				           <td>4</td>
				           <td>dvr02_2017010800004</td>
				           <td>675</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>手动录像</td>
				           <td style="display:none">4</td>
				       </tr>
				       <tr style="background-color:#ffffff">
				           <td>5</td>
				           <td>dvr02_2017010800005</td>
				           <td>562</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>报警触发</td>
				           <td style="display:none">5</td>
				       </tr>
				       <tr style="background-color:#f5f5f5">
				           <td>6</td>
				           <td>dvr01_2017010800006</td>
				           <td>675</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>手动录像</td>
				           <td style="display:none">6</td>
				       </tr>
				       <tr style="background-color:#ffffff">
				           <td>7</td>
				           <td>dvr02_2017010800007</td>
				           <td>562</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>报警触发</td>
				           <td style="display:none">7</td>
				       </tr>
				       <tr style="background-color:#f5f5f5">
				           <td>8</td>
				           <td>dvr02_2017020800008</td>
				           <td>675</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>手动录像</td>
				           <td style="display:none">8</td>
				       </tr>
				       <tr style="background-color:#ffffff">
				           <td>9</td>
				           <td>dvr01_2017020800009</td>
				           <td>562</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>报警触发</td>
				           <td style="display:none">9</td>
				       </tr>
				       <tr style="background-color:#f5f5f5">
				           <td>10</td>
				           <td>dvr02_2017020800010</td>
				           <td>562</td>
				           <td>2017-01-07 07:16:02</td>
				           <td>2017-01-07 22:12:10</td>
				           <td>手动录像</td>
				           <td style="display:none">10</td>
				       </tr>
				   </table>
				 </div>
				 <div style="float:left; width:42%;">
				     <div id="videoPlay" style="margin:-20px 0; width:100%; heigth:100%;"></div>
				 </div>
			  </div>
            </div>
         </div>
      </div> 
     
      <script type="text/javascript">  
        //创建播放器对象  
        var player = new SWFObject('<%=basePath%>/static/video/player.swf','jw','100%','450px','12','#000000');  
        player.addParam('allowfullscreen','true');  
        player.addParam('allowscriptaccess','always');  
        player.addParam('wmode','opaque');  
        player.addParam('flashvars','file=<%=basePath%>/static/video/IMG_1189.flv&bufferlength=20&stretching=uniform&repeat=always&autostart=false');    
        player.write('videoPlay'); 
      
        var curRow;    //全局行号
        var curRowId; //选中行的记录信息的ID
        var curColor;
        function selectRow(tr){    
            curRow = tr;
            curRowId = tr.id;
            sweetAlert("视频文件名："+tr.cells[1].innerText);
        }
        
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