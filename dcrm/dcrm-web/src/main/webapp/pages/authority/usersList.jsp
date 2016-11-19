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
	<meta content="text/html; charset=UTF-8" http-equiv="content-type" />
	<title>用户信息列表</title>
	<link href="<%=basePath%>static/css/users-layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>static/css/sweet-alert.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
	<script src="<%=basePath%>static/js/sweet-alert.min.js" type="text/javascript" ></script>
	<script>
	 $(document).ready(function () {
		 //ajax发请求
         $.ajax({
             type : "GET",
             url : "<%=basePath%>searchUsers.do?loginName=&trueName=&pageNum=1",
             success : function(msg) {
                 var msgRes = JSON.parse(msg);//返回json对象
                 //更新记录总数
                 document.getElementById("midPage").rows[0].cells[1].innerHTML = "总计"+msgRes.userTotalNum+"条记录";
                 $("#usersCurPageNum").attr("value", 1);
                 $("#usersTotalPage").attr("value", msgRes.userTotalPage);
                 $("#usersCurNo").attr("value", 1);

                 //清空table
                 var usersTB = document.getElementById("usersTab");
                 var rowNum = usersTB.rows.length;
                 for (var i=1; i<rowNum; i++){
                	 usersTB.deleteRow(i);
                     rowNum=rowNum-1;
                     i=i-1;
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
				             <td style=\"display:none\">" + result.id + "</td></tr>";
                         $("#usersTab").append(usersItem);
                     }
                 });
             },
             error:function(e){
                 sweetAlert("网页发生错误：", e, "error");
             }
      });
      
     /*问题改善跟踪  首页、下一页、上一页、末页按钮*/
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
                     document.getElementById("midPage").rows[0].cells[1].innerHTML = "总计"+msgRes.userTotalNum+"条记录";
                     $("#usersCurPageNum").attr("value", usersPageNum);
                     $("#usersTotalPage").attr("value", msgRes.userTotalPage);
                     $("#usersCurNo").attr("value", usersPageNum);

                     //清空table
                     var usersTB = document.getElementById("usersTab");
                     var rowNum = usersTB.rows.length;
                     for (var i=1; i<rowNum; i++){
                    	 usersTB.deleteRow(i);
                         rowNum=rowNum-1;
                         i=i-1;
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
	  });
	</script>
</head>

<body>
	<div id="container">
		<div id="headerLayout">
			<div id="authTop">
				<span id="mes1">欢迎来到通用权限管理平台</span> 
				<span id="mes2"><a href="#">帮助中心</a></span> 
				<span id="mes3"><a href="<%=basePath%>pages/authority/auth_main.jsp">返回首页</a></span> 
				<span id="mes4"><a href="#">当前用户：admin</a></span> 
				<span id="mes5"><a href="#">注销</a></span>
			</div>
			<div id="authMid">
				<div id="comLog">
					<a href="#" ><img src="<%=basePath%>static/img/main/ouc1.png" /></a>
				</div>
				<div id="authMenu">
					<ul>
						<li id="u1"><a id="usersManage">用户管理</a></li>
						<li id="u2"><a id="rolesManage">角色管理</a></li>
						<li id="u4"><a id="resourcesManage" href="#">资源管理</a></li>
					</ul>
				</div>
			</div>
		</div>

		<div id="layoutBody">
			<div id="midTitle">
			   <img src="<%=basePath%>static/img/chartmark.png" style="height:20px; width:20px;" />
			   <span>用户列表</span> 
			</div>
			<div id="midTabQuery" >
			   <label style="margin-left:680px;">登录名：</label>
               <input id ="loginName" style="width:120px;" type="text" />
               <label>姓名：</label>
               <input id ="trueName" style="width:120px;" type="text" />
               <img id="usersSearch" class="usersSkip" src="<%=basePath%>static/img/search.png" />
			</div>
			<div id="midTable" >
				<table id="usersTab">
					<tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						<td style="width:50px">序号</td>
						<td style="width:120px">登录名</td>
						<td style="width:100px">姓名</td>
						<td style="width:120px">邮箱</td>
						<td style="width:120px">手机</td>
						<td style="width:60px">性别</td>
						<td style="display:none">主键ID</td>
					</tr>
				</table>
				
				<table id="midPage">
					<tr style="height:5px;">
                        <td style="width :20px;"></td>
                        <td id="userstotalNum" style="width:50px;" ></td>
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
</body>
</html>