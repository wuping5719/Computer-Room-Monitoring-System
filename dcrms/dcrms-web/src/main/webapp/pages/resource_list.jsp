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
	<title>角色列表</title>
	<link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link href="<%=basePath%>static/css/sweet-alert.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
	<link href="<%=basePath%>static/css/res-layout.css" rel="stylesheet" type="text/css" />
	
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
               <input id="rolesListBtn" value="角色列表" type="button" style="margin-left:-40px;height:24px; color:#0000FF; " />
               <input id="resListBtn" value="资源列表" type="button" style="margin-left:10px; height:24px; color:#F000F0; " />
			   
			   <label style="margin-left:360px;">资源名称：</label>
               <input id="resName" style="width:120px; height:24px;" type="text" />
               <label>资源类型：</label>
               <select id="resType" style="width:80px; height:24px;" >
			      <option value="默认">默认</option>
			      <option value="URL资源">URL资源</option>
			      <option value="组件资源">组件资源</option>
			      <option value="虚拟资源">虚拟资源</option>
		       </select>
               <img id="resSearch" class="rolesSkip" src="<%=basePath%>static/img/search.png" />
			   <input type="button" id="newRes" value="新建资源" style="margin-left:10px; height:24px;" />
			</div>
			
			<div id="resListTable" >
				<table id="resTab">
					<tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						<td style="width:50px">序号</td>
						<td style="width:100px">资源名</td>
						<td style="width:140px">描述</td>
						<td style="width:50px">URL</td>
						<td style="width:70px">类型</td>
						<td style="width:90px">创建日期</td>
						<td style="width:90px">最后修改日期</td>
						<td style="width:40px">修改</td>
						<td style="width:40px">删除</td>
						<td style="display:none">主键ID</td>
					</tr>
				</table>
				
				<table id="resPage">
					<tr style="height:5px;">
                        <td style="width :20px;"></td>
                        <td style="width:50px;" ></td>
                        <td style="width:40px;">
                            <img id="resFirst" class="resSkip" src="<%=basePath%>static/img/first.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="resPrevious" class="resSkip" src="<%=basePath%>static/img/left.png" />
                        </td>
                        <td id="resCenterBar" style="width:60px;" >
                            <input id="resCurPageNum" readonly="readonly" style="width:30px;"/>
                            <label>/</label>
                            <input id="resTotalPage" readonly="readonly" style="width:30px;" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;">
                            <img id="resNext" class="resSkip" src="<%=basePath%>static/img/right.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="resEnd" class="resSkip" src="<%=basePath%>static/img/end.png" />
                        </td>
                        <td style="width:60px;" >
                            <label>跳转到</label>
                            <input id ="resCurNo" style="width:40px;" type="text" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;" >
                            <img id="resJumpN" class="resSkip" src="<%=basePath%>static/img/sureBtn.png" />
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
     $("#rolesListBtn").click(function(){
	   	 window.location.href = "<%=basePath%>loadRoleList.do";
	 });
		 
     $("#resListBtn").click(function(){
		 window.location.href = "<%=basePath%>loadResList.do";
	 });
     
     $(document).ready(function () {
		 //ajax发请求
         $.ajax({
             type : "GET",
             url : "<%=basePath%>searchRes.do?resName=&resType=&pageNum=1",
             success : function(msg) {
                 var msgRes = JSON.parse(msg);//返回json对象
                 //更新记录总数
                 document.getElementById("resPage").rows[0].cells[1].innerHTML = "总计"+msgRes.resTotalNum+"条记录";
                 $("#resCurPageNum").attr("value", 1);
                 $("#resTotalPage").attr("value", msgRes.resTotalPage);
                 $("#resCurNo").attr("value", 1);

                 //清空table
                 var resTB = document.getElementById("resTab");
                 var rowNum = resTB.rows.length;
                 for (var i=1; i<rowNum; i++){
                	 resTB.deleteRow(i);
                     rowNum = rowNum-1;
                     i = i-1;
                 }

                 //插入行数据
                 var resItem;
                 $.each(msgRes.resDTOsList,function(i,result){
                     if (i % 2 ==0){
                    	 resItem = "<tr style=\"background-color:#ffffff\">\
				               <td >" + result.sortIndex + "</td>\
				               <td >" + result.resName + "</td>\
				               <td >" + result.description + "</td>\
				               <td >" + result.url + "</td>\
				               <td >" + result.resType + "</td>\
				               <td >" + result.gmtCreate + "</td>\
				               <td >" + result.gmtModified + "</td>\
				               <td ><a href=\"#\">修改</a></td>\
				               <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
				               <td style=\"display:none\">" + result.resId + "</td></tr>";
                         $("#resTab").append(resItem);
                     }
                     else{
                    	 resItem = "<tr style=\"background-color:#f5f5f5\">\
                    		   <td >" + result.sortIndex + "</td>\
				               <td >" + result.resName + "</td>\
				               <td >" + result.description + "</td>\
				               <td >" + result.url + "</td>\
				               <td >" + result.resType + "</td>\
				               <td >" + result.gmtCreate + "</td>\
				               <td >" + result.gmtModified + "</td>\
				               <td ><a href=\"#\">修改</a></td>\
				               <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
				               <td style=\"display:none\">" + result.resId + "</td></tr>";
                         $("#resTab").append(resItem);
                     }
                 });
             },
             error:function(e){
                 sweetAlert("网页发生错误：", e, "error");
             }
        });
	 });
	 
     /*首页、下一页、上一页、末页按钮*/
     $(".resSkip").click(function(){
          var resCurPageNum = Number(document.getElementById("resCurPageNum").value);//当前页码
          var resPageNum = 1;  //将要跳转的页码
          var resTotalPage = Number(document.getElementById("resTotalPage").value);//页码总数
          if (this.id == "resFirst") {//首页
        	  resPageNum = 1;
          } else if (this.id == "resPrevious") { //上一页
                 if (resCurPageNum > 1 && resCurPageNum <= resTotalPage) {
                	 resPageNum = Number(resCurPageNum) - 1;
                 } else {
                	 resPageNum = 1;
                 }
             } else if (this.id == "resNext") { //下一页
                 if (resCurPageNum < resTotalPage){
                	 resPageNum = Number(resCurPageNum) + 1;
                 }else {
                	 resPageNum = resTotalPage;
                 }
             } else if (this.id == "resEnd") { //末页
            	 resPageNum = resTotalPage;
             } else if (this.id == "resSearch") { //查询
            	 resPageNum = 1;
             } else if (this.id == "resJumpN") {
                 var resCurNo = Number(document.getElementById("resCurNo").value);
                 if (resCurNo < 1) {
                     sweetAlert("警告", "页号为正整数！", "warning");
                     return;
                 } else if (resCurNo > resTotalPage) {
                     sweetAlert("警告", "所选页号超过总页数！", "warning");
                     return;
                 }
                 resPageNum = Number(resCurNo);
             }

             //查询条件
             var resName = document.getElementById("resName").value;   
             var resType = document.getElementById("resType").value;   

             //ajax发请求
             $.ajax({
                 type : "GET",
                 url : "<%=basePath%>searchRes.do?resName=" + resName + "&resType=" + resType + "&pageNum=" + pageNum,
                 success : function(msg) {
                     var msgRes = JSON.parse(msg);//返回json对象
                     //更新记录总数
                     document.getElementById("resPage").rows[0].cells[1].innerHTML = "总计"+msgRes.resTotalNum+"条记录";
                     $("#resCurPageNum").attr("value", resPageNum);
                     $("#resTotalPage").attr("value", msgRes.resTotalPage);
                     $("#resCurNo").attr("value", resPageNum);

                     //清空table
                     var resTB = document.getElementById("resTab");
                     var rowNum = resTB.rows.length;
                     for (var i=1; i<rowNum; i++){
                    	 resTB.deleteRow(i);
                         rowNum = rowNum-1;
                         i = i-1;
                     }

                     //插入行数据
                     var resItem;
                     $.each(msgRes.resDTOsList,function(i,result){
                         if (i % 2 ==0){
                        	 resItem = "<tr style=\"background-color:#ffffff\">\
                        		 <td >" + result.sortIndex + "</td>\
  				                 <td >" + result.resName + "</td>\
  				                 <td >" + result.description + "</td>\
  				                 <td >" + result.url + "</td>\
  				                 <td >" + result.resType + "</td>\
  				                 <td >" + result.gmtCreate + "</td>\
  				                 <td >" + result.gmtModified + "</td>\
  				                 <td ><a href=\"#\">修改</a></td>\
  				                 <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
  				                 <td style=\"display:none\">" + result.resId + "</td></tr>";
                             $("#rolesTab").append(resItem);
                         }
                         else{
                        	 resItem = "<tr style=\"background-color:#f5f5f5\">\
                        		 <td >" + result.sortIndex + "</td>\
  				                 <td >" + result.resName + "</td>\
  				                 <td >" + result.description + "</td>\
  				                 <td >" + result.url + "</td>\
  				                 <td >" + result.resType + "</td>\
  				                 <td >" + result.gmtCreate + "</td>\
  				                 <td >" + result.gmtModified + "</td>\
  				                 <td ><a href=\"#\">修改</a></td>\
  				                 <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
  				                 <td style=\"display:none\">" + result.resId + "</td></tr>";
                             $("#resTab").append(resItem);
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