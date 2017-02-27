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
	<meta content="text/html; charset=UTF-8" http-equiv="content-type" />
	<title>用户列表</title>
	<link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link href="<%=basePath%>static/css/sweet-alert.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
	<link href="<%=basePath%>static/css/users-layout.css" rel="stylesheet" type="text/css" />
	
	<script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
	<script src="<%=basePath%>static/js/sweet-alert.min.js" type="text/javascript" ></script>
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
            <div id="queryBar" >
			   <label style="margin-left:600px;">登录名：</label>
               <input id="loginName" style="width:120px; height:24px;" type="text" />
               <label>姓名：</label>
               <input id="trueName" style="width:120px; height:24px;" type="text" />
               <img id="usersSearch" class="usersSkip" src="<%=basePath%>static/img/search.png" />
			</div>
			
			<div id="userListTable" >
				<table id="usersTab">
					<tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						<td style="width:50px">序号</td>
						<td style="width:100px">登录名</td>
						<td style="width:90px">姓名</td>
						<td style="width:120px">邮箱</td>
						<td style="width:100px">手机</td>
						<td style="width:40px">性别</td>
						<td style="width:100px">最后修改日期</td>
						<td style="width:100px">角色</td>
						<td style="display:none">主键ID</td>
					</tr>
				</table>
				
				<table id="usersPage">
					<tr style="height:5px;">
                        <td style="width :20px;"></td>
                        <td style="width:50px;" ></td>
                        <td style="width:40px;">
                            <img id="usersFirst" class="usersSkip" src="<%=basePath%>static/img/first.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="usersPrevious" class="usersSkip" src="<%=basePath%>static/img/left.png" />
                        </td>
                        <td id="usersCenterBar" style="width:60px;" >
                            <input id="usersCurPageNum" readonly="readonly" style="width:30px;"/>
                            <label>/</label>
                            <input id="usersTotalPage" readonly="readonly" style="width:30px;" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;">
                            <img id="usersNext" class="usersSkip" src="<%=basePath%>static/img/right.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="usersEnd" class="usersSkip" src="<%=basePath%>static/img/end.png" />
                        </td>
                        <td style="width:60px;" >
                            <label>跳转到</label>
                            <input id ="usersCurNo" style="width:40px;" type="text" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;" >
                            <img id="usersJumpN" class="usersSkip" src="<%=basePath%>static/img/sureBtn.png" />
                        </td>
                        <td style="width:60px;"></td>
                    </tr>
				</table>
		   </div>
		  
        </div>
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
    </div> 
    
    <script>
	 $(document).ready(function () {
		 //ajax发请求
         $.ajax({
             type : "GET",
             url : "<%=basePath%>searchUsers.do?loginName=&trueName=&pageNum=1",
             success : function(msg) {
                 var msgRes = JSON.parse(msg);//返回json对象
                 //更新记录总数
                 document.getElementById("usersPage").rows[0].cells[1].innerHTML = "总计"+msgRes.userTotalNum+"条记录";
                 $("#usersCurPageNum").attr("value", 1);
                 $("#usersTotalPage").attr("value", msgRes.userTotalPage);
                 $("#usersCurNo").attr("value", 1);

                 //清空table
                 var usersTB = document.getElementById("usersTab");
                 var rowNum = usersTB.rows.length;
                 for (var i=1; i<rowNum; i++){
                	 usersTB.deleteRow(i);
                     rowNum = rowNum-1;
                     i = i-1;
                 }

                 //插入行数据
                 var usersItem;
                 $.each(msgRes.userDTOsList,function(i,result){
                     if (i % 2 ==0){
                    	 usersItem = "<tr style=\"background-color:#ffffff\">\
				               <td >" + result.sortIndex + "</td>\
				               <td >" + result.loginName + "</td>\
				               <td >" + result.trueName + "</td>\
				               <td >" + result.email + "</td>\
				               <td >" + result.mobilePhone + "</td>\
				               <td >" + result.sex + "</td>\
				               <td >" + result.gmtModified + "</td>\
				               <td ><a href=\"#\">" + result.roleName + "</a></td>\
				               <td style=\"display:none\">" + result.id + "</td></tr>";
                         $("#usersTab").append(usersItem);
                     }
                     else{
                    	 usersItem = "<tr style=\"background-color:#f5f5f5\">\
                    		 <td >" + result.sortIndex + "</td>\
				             <td >" + result.loginName + "</td>\
				             <td >" + result.trueName + "</td>\
				             <td >" + result.email + "</td>\
				             <td >" + result.mobilePhone + "</td>\
				             <td >" + result.sex + "</td>\
				             <td >" + result.gmtModified + "</td>\
				             <td ><a href=\"#\">" + result.roleName + "</a></td>\
				             <td style=\"display:none\">" + result.id + "</td></tr>";
                         $("#usersTab").append(usersItem);
                     }
                 });
             },
             error:function(e){
                 sweetAlert("网页发生错误：", e, "error");
             }
      });
	 });
	 
	 /* 首页、下一页、上一页、末页按钮*/
     $(".usersSkip").click(function(){
          var usersCurPageNum = Number(document.getElementById("usersCurPageNum").value);//当前页码
          var usersPageNum = 1;  //将要跳转的页码
          var usersTotalPage = Number(document.getElementById("usersTotalPage").value);//页码总数
          if (this.id == "usersFirst") {//首页
            	 usersPageNum = 1;
          } else if (this.id == "usersPrevious") { //上一页
                 if (usersCurPageNum > 1 && usersCurPageNum <= usersTotalPage) {
                	 usersPageNum = Number(usersCurPageNum) - 1;
                 } else {
                	 usersPageNum = 1;
                 }
             } else if (this.id == "usersNext") { //下一页
                 if (usersCurPageNum < usersTotalPage){
                	 usersPageNum = Number(usersCurPageNum) + 1;
                 }else {
                	 usersPageNum = usersTotalPage;
                 }
             } else if (this.id == "usersEnd") { //末页
            	 usersPageNum = usersTotalPage;
             } else if (this.id == "usersSearch") { //查询
            	 usersPageNum = 1;
             } else if (this.id == "usersJumpN") {
                 var usersCurNo = Number(document.getElementById("usersCurNo").value);
                 if (usersCurNo < 1) {
                     sweetAlert("警告", "页号为正整数！", "warning");
                     return;
                 } else if (usersCurNo > usersTotalPage) {
                     sweetAlert("警告", "所选页号超过总页数！", "warning");
                     return;
                 }
                 usersPageNum = Number(usersCurNo);
             }

             //查询条件
             var loginName = document.getElementById("loginName").value;   
             var trueName = document.getElementById("trueName").value;   

             //ajax发请求
             $.ajax({
                 type : "GET",
                 url : "<%=basePath%>searchUsers.do?loginName=" + loginName + "&trueName=" + trueName + "&pageNum=" + pageNum,
                 success : function(msg) {
                     var msgRes = JSON.parse(msg);//返回json对象
                     //更新记录总数
                     document.getElementById("usersPage").rows[0].cells[1].innerHTML = "总计"+msgRes.userTotalNum+"条记录";
                     $("#usersCurPageNum").attr("value", usersPageNum);
                     $("#usersTotalPage").attr("value", msgRes.userTotalPage);
                     $("#usersCurNo").attr("value", usersPageNum);

                     //清空table
                     var usersTB = document.getElementById("usersTab");
                     var rowNum = usersTB.rows.length;
                     for (var i=1; i<rowNum; i++){
                    	 usersTB.deleteRow(i);
                         rowNum = rowNum-1;
                         i = i-1;
                     }

                     //插入行数据
                     var usersItem;
                     $.each(msgRes.userDTOsList,function(i,result){
                         if (i % 2 ==0){
                        	 usersItem = "<tr style=\"background-color:#ffffff\">\
					               <td >" + result.sortIndex + "</td>\
					               <td >" + result.loginName + "</td>\
					               <td >" + result.trueName + "</td>\
					               <td >" + result.email + "</td>\
					               <td >" + result.mobilePhone + "</td>\
					               <td >" + result.sex + "</td>\
					               <td >" + result.gmtModified + "</td>\
					               <td ><a href=\"#\">" + result.roleName + "</a></td>\
					               <td style=\"display:none\">" + result.id + "</td></tr>";
                             $("#usersTab").append(usersItem);
                         }
                         else{
                        	 usersItem = "<tr style=\"background-color:#f5f5f5\">\
                        		 <td >" + result.sortIndex + "</td>\
					             <td >" + result.loginName + "</td>\
					             <td >" + result.trueName + "</td>\
					             <td >" + result.email + "</td>\
					             <td >" + result.mobilePhone + "</td>\
					             <td >" + result.sex + "</td>\
					             <td >" + result.gmtModified + "</td>\
					             <td ><a href=\"#\">" + result.roleName + "</a></td>\
					             <td style=\"display:none\">" + result.id + "</td></tr>";
                             $("#usersTab").append(usersItem);
                         }
                     });
                 },
                 error:function(e){
                     sweetAlert("网页发生错误：", e, "error");
                 }
          });
      });
	</script>
  </body>
</html>