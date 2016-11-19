<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();   
    String basePath = request.getScheme() + "://"
		    + request.getServerName() + ":" + request.getServerPort()
		    + path + "/";   
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=UTF-8" http-equiv="content-type" />
<title>通用权限管理平台</title>

<link href="<%=basePath%>static/css/global.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
<script src="<%=basePath%>static/js/global.js" type="text/javascript" ></script>

</head>
<body id="main">
	<div id="main_top">
		<div id="logo">
		   <a href="#" style="height:118px;width:37px;">
			  <img src="<%=basePath%>static/img/main/ouc1.png" />
		   </a>
		</div>
		<div class="hg_nav">
			<div class="navi">
				<ul class="hg_nav_list js_nav_list">
					<li id="hg_nav_li1" >
					   <a href="#" id="dp1">
					   <img id="tp1" src="<%=basePath%>static/img/main/stationb.png" />业务平台</a>
					</li>
					<li id="hg_nav_li2" >
					   <a href="#" id="dp2" target="_blank">
					   <img id="tp2" src="<%=basePath%>static/img/main/qualityw.png" />性能监测</a>
				   </li>
				</ul>
			</div>
			<div class="hg_sch">
				<input id="userName" type="text" readonly="readonly" value="admin" />
				<a id="a_logout" href="logout" onclick="userLogout()" >注销</a>
			</div>
		</div>
	</div>
	
	<div id="main_middle">
	  <div id="middle_workbench">
        <div id="workbenchTitle" >
          <img src="<%=basePath%>static/img/main/work.png" class="titleImg" />
          <b style="color:#065b9b; font-size: 16px; ">工作台</b>
          <hr style="color:#065b9b;" />
        </div>
		<div id="workbench_entry1" class="workbench_entry" style="top: 40px; left: 70px;">
			<a id="workbenchUser" href="<%=basePath%>pages/authority/usersList.jsp" target="_blank">
			   <img src="<%=basePath%>static/img/main/usermanage.png" />
			</a>
			<p>用户管理</p>
		</div>
		<div id="workbench_entry2" class="workbench_entry" style="top: 40px; left: 230px;">
			<a id="workbenchRes" href="<%=basePath%>pages/authority/resourcesList.jsp" target="_blank">
			   <img src="<%=basePath%>static/img/main/resmanage.png" />
			</a>
			<p>资源管理</p>
        </div>
        <div id="workbench_entry3" class="workbench_entry" style="top: 40px; left: 390px;">
			<a id="workbenchRole" href="<%=basePath%>pages/authority/rolesList.jsp" target="_blank" >
			<img src="<%=basePath%>static/img/main/rolemanage.png" /></a>
			<p>角色管理</p>
        </div>
        <div id="workbench_entry4" class="workbench_entry" style="top: 40px; left: 550px;">
			<a href="#" onclick="sAlert('该功能模块正在开发，敬请期待')">
			<img src="<%=basePath%>static/img/main/dataanly.png" /></a>
			<p>数据分析</p>
        </div>
        <div id="workbench_entry5" class="workbench_entry" style="top: 150px; left: 70px;">
			<a href="#" onclick="sAlert('该功能模块正在开发，敬请期待')">
			<img src="<%=basePath%>static/img/main/appmanage.png" /></a>
			<p>应用管理</p>
        </div>
        <div id="workbench_entry6" class="workbench_entry" style="top: 150px; left: 230px;">
			<a href="#" onclick="sAlert('该功能模块正在开发，敬请期待')">
			<img src="<%=basePath%>static/img/main/performance.png" /></a>
			<p>性能监控</p>
        </div>
        <div id="workbench_entry7" class="workbench_entry" style="top: 150px; left: 390px;">
			<a href="#" onclick="sAlert('该功能模块正在开发，敬请期待')">
			<img src="<%=basePath%>static/img/main/changemanage.png" /></a>
			<p>装修系统</p>
        </div>
        <div id="workbench_entry8" class="workbench_entry" style="top: 150px; left: 550px;">
			<a href="#" onclick="sAlert('该功能模块正在开发，敬请期待')">
			<img src="<%=basePath%>static/img/main/usermanage.png" /></a>
			<p>流程控制</p>
        </div>
    </div>
    
    <div id="middle_notice">
       <div id="noticeTitle" >
          <img src="<%=basePath%>static/img/main/system.png" style="margin:3px 5px;height:18px;width:20px;align:bottom;"/>
          <b style="color:#065b9b; font-size: 16px; ">系统公告</b>
          <hr style="color:#065b9b;" />
       </div>
       <div id="marquee" >
          <table>
              <tr><td>2016-6-18</td></tr>
              <tr>
                 <td id="notice1">
                 <a href="#" onclick="sAlert('文档服务器还未启用，敬请期待')">关于用户管理模块上线的通知</a>
                 </td>
              </tr>
              <tr><td></td></tr>
              <tr><td>2016-6-28</td></tr>
              <tr>
                 <td id="notice2">
                 <a href="#" onclick="sAlert('文档服务器还未启用，敬请期待')">关于资源管理模块上线的通知</a>
                 </td>
              </tr>
              <tr><td></td></tr>
              <tr><td>2016-7-10</td></tr>
              <tr>
                 <td id="notice3">
                 <a href="#" onclick="sAlert('文档服务器还未启用，敬请期待')">关于角色管理模块上线的通知</a>
                 </td>
              </tr>
              <tr><td></td></tr>
              <tr>
                 <td id="notice4">
                 <a href="#" onclick="sAlert('文档服务器还未启用，敬请期待')">更多</a>
                 </td>
              </tr>
         </table>
      </div>
  </div>
            
  <div id="middle_common">
      <div id="commonTitle">
          <img src="<%=basePath%>static/img/main/mainuse.png" class="titleImg" />
          <b style="color:#065b9b; font-size: 16px; ">常用功能</b>
          <hr style="color:#065b9b;" />
      </div>
      <div id="commonContent1" class="commonContent" style="left: 60px;">
         <ul> 
           <li id="system_1" class="systemTitle1" >
            <img src="<%=basePath%>static/img/main/user.png"/>
            <a id="userManage" href="#" >用户管理</a>
           </li>
           <li id="system_2" class="systemTitle2" >
             <a id="viewUser" href="" target="_blank">查看用户</a>
           </li>
           <li id="system_3" class="systemTitle2" >
             <a id="changeUser" href="" target="_blank">修改用户</a>
           </li>
           <li id="system_4" class="systemTitle2" >
              <a id="userAuthority" href="" target="_blank">用户权限</a>
           </li>
         </ul>
     </div>
     <div class="commonLine" style="left: 180px;"></div>
     <div id="commonContent2" class="commonContent" style="left: 190px;">
         <ul> 
           <li id="system_5" class="systemTitle1" >
            <img src="<%=basePath%>static/img/main/base.png"/>
            <a id="resourceManage" href="#" >资源管理</a>
           </li>
           <li id="system_6" class="systemTitle2" >
             <a id="viewResource" href="" target="_blank">查看资源</a>
           </li>
           <li id="system_7" class="systemTitle2" >
             <a id="newResource" href="" target="_blank">新建资源</a>
           </li>
           <li id="system_8" class="systemTitle2" >
              <a id="changeResource" href="" target="_blank">修改资源</a>
           </li>
           <li id="system_9" class="systemTitle2" >
              <a id="relatedResource" href="" target="_blank">角色关联资源</a>
           </li>
         </ul>               
      </div>                 
      <div class="commonLine" style="left: 310px;"></div>
      <div id="commonContent3" class="commonContent" style="left: 320px;">
         <ul> 
           <li id="system_10" class="systemTitle1" >
            <img src="<%=basePath%>static/img/main/new.png"/>
            <a id="roleManage" href="#" >角色管理</a>
           </li>
           <li id="system_11" class="systemTitle2" >
             <a id="viewRole" href="" target="_blank">查看资源</a>
           </li>
           <li id="system_12" class="systemTitle2" >
             <a id="newRole" href="" target="_blank">新建角色</a>
           </li>
           <li id="system_13" class="systemTitle2" >
              <a id="changeRole" href="" target="_blank">修改角色</a>
           </li>
           <li id="system_14" class="systemTitle2" >
              <a id="relatedUser" href="" target="_blank">用户关联角色</a>
           </li>
         </ul>               
      </div>                   
      <div class="commonLine" style="left: 440px;"></div>
      <div id="commonContent4" class="commonContent" style="left: 450px;">
         <ul> 
           <li id="system_15" class="systemTitle1" >
            <img src="<%=basePath%>static/img/main/data.png"/>
            <a id="data1" href="#" >数据分析</a>
           </li>
           <li id="system_16" class="systemTitle2" >
             <a id="data2" href="" target="_blank">分析工具</a>
           </li>
           <li id="system_17" class="systemTitle2" >
             <a id="data3" href="" target="_blank">数据真实性分析</a>
           </li>
           <li id="system_18" class="systemTitle2" >
              <a id="data4" href="" target="_blank">一致性分析</a>
           </li>
           <li id="system_19" class="systemTitle2" >
              <a id="data5" href="" target="_blank">数据比对分析</a>
           </li>
         </ul>               
      </div>
      <div class="commonLine" style="left: 570px;"></div>
      <div id="commonContent5" class="commonContent" style="left: 580px;">
         <ul> 
           <li id="system_20" class="systemTitle1" >
            <img src="<%=basePath%>static/img/main/online.png"/>
            <a id="appManage" href="#" >应用管理</a>
           </li>
           <li id="system_21" class="systemTitle2" >
             <a id="viewApp" href="" target="_blank">查看应用</a>
           </li>
           <li id="system_22" class="systemTitle2" >
             <a id="appPerformance" href="" target="_blank">应用性能分析</a>
           </li>
         </ul>               
      </div>                 
  </div>

  <div id="middle_neededDealt">
     <div id="neededDealtTitle">
          <img src="<%=basePath%>static/img/main/wait.png" class="titleImg" />
          <b style="color:#065b9b; font-size: 16px;">待办</b>
          <hr style="color:#065b9b;" />
     </div>
     <div id="DealtedContent" >
        <ul>
          <li id="main_work_1" >
            <a href="#" id="backLog" >您有2条代办事项, 请及时处理</a>
          </li>
        </ul>
     </div>
  </div>
</div>
  <div id="main_bottom"></div>
</body>
</html>
