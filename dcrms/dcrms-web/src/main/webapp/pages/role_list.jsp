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
    <link href="<%=basePath%>static/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
    <link href="<%=basePath%>static/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet">
    <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
	<link href="<%=basePath%>static/css/roles-layout.css" rel="stylesheet" type="text/css" />
	
	<script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
	<script src="<%=basePath%>static/js/sweet-alert.min.js" type="text/javascript" ></script>
	<script src="<%=basePath%>static/jquery-ui-1.11.4/external/jquery/jquery.js"></script>
    <script src="<%=basePath%>static/jquery-ui-1.11.4/jquery-ui.js"></script>
	<script src="<%=basePath%>static/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
    <script src="<%=basePath%>static/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
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
			   
			   <label style="margin-left:360px;">角色名称：</label>
               <input id="roleName" style="width:120px; height:24px;" type="text" />
               <label>角色描述：</label>
               <input id="description" style="width:120px; height:24px;" type="text" />
               <img id="rolesSearch" class="rolesSkip" src="<%=basePath%>static/img/search.png" />
			   <input type="button" id="newRole" value="新建角色" style="margin-left:10px; height:24px;" />
			</div>
			
			<div id="roleListTable" >
				<table id="rolesTab">
					<tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						<td style="width:50px">序号</td>
						<td style="width:100px">角色名</td>
						<td style="width:140px">描述</td>
						<td style="width:90px">创建日期</td>
						<td style="width:90px">最后修改日期</td>
						<td style="width:50px">创建人</td>
						<td style="width:70px">最后修改人</td>
						<td style="width:40px">修改</td>
						<td style="width:40px">删除</td>
						<td style="display:none">主键ID</td>
					</tr>
				</table>
				
				<table id="rolesPage">
					<tr style="height:5px;">
                        <td style="width :20px;"></td>
                        <td style="width:50px;" ></td>
                        <td style="width:40px;">
                            <img id="rolesFirst" class="rolesSkip" src="<%=basePath%>static/img/first.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="rolesPrevious" class="rolesSkip" src="<%=basePath%>static/img/left.png" />
                        </td>
                        <td id="rolesCenterBar" style="width:60px;" >
                            <input id="rolesCurPageNum" readonly="readonly" style="width:30px;"/>
                            <label>/</label>
                            <input id="rolesTotalPage" readonly="readonly" style="width:30px;" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;">
                            <img id="rolesNext" class="rolesSkip" src="<%=basePath%>static/img/right.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="rolesEnd" class="rolesSkip" src="<%=basePath%>static/img/end.png" />
                        </td>
                        <td style="width:60px;" >
                            <label>跳转到</label>
                            <input id ="rolesCurNo" style="width:40px;" type="text" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;" >
                            <img id="rolesJumpN" class="rolesSkip" src="<%=basePath%>static/img/sureBtn.png" />
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
    	 // 异步加载角色列表
         $.ajax({
             type : "GET",
             url : "<%=basePath%>searchRoles.do?roleName=&description=&pageNum=1",
             success : function(msg) {
                 var msgRes = JSON.parse(msg);//返回json对象
                 //更新记录总数
                 document.getElementById("rolesPage").rows[0].cells[1].innerHTML = "总计"+msgRes.roleTotalNum+"条记录";
                 $("#rolesCurPageNum").attr("value", 1);
                 $("#rolesTotalPage").attr("value", msgRes.roleTotalPage);
                 $("#rolesCurNo").attr("value", 1);

                 //清空table
                 var rolesTB = document.getElementById("rolesTab");
                 var rowNum = rolesTB.rows.length;
                 for (var i=1; i<rowNum; i++){
                	 rolesTB.deleteRow(i);
                     rowNum = rowNum-1;
                     i = i-1;
                 }

                 //插入行数据
                 var rolesItem;
                 $.each(msgRes.roleDTOsList,function(i,result){
                     if (i % 2 ==0){
                    	 rolesItem = "<tr style=\"background-color:#ffffff\">\
				               <td >" + result.sortIndex + "</td>\
				               <td >" + result.roleName + "</td>\
				               <td >" + result.description + "</td>\
				               <td >" + result.gmtCreate + "</td>\
				               <td >" + result.gmtModified + "</td>\
				               <td >" + result.createBy + "</td>\
				               <td >" + result.lastModifedBy + "</td>\
				               <td ><a href=\"#\">修改</a></td>\
				               <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
				               <td style=\"display:none\">" + result.roleId + "</td></tr>";
                         $("#rolesTab").append(rolesItem);
                     }
                     else{
                    	 rolesItem = "<tr style=\"background-color:#f5f5f5\">\
                    		   <td >" + result.sortIndex + "</td>\
				               <td >" + result.roleName + "</td>\
				               <td >" + result.description + "</td>\
				               <td >" + result.gmtCreate + "</td>\
				               <td >" + result.gmtModified + "</td>\
				               <td >" + result.createBy + "</td>\
				               <td >" + result.lastModifedBy + "</td>\
				               <td ><a href=\"#\">修改</a></td>\
				               <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
				               <td style=\"display:none\">" + result.roleId + "</td></tr>";
                         $("#rolesTab").append(rolesItem);
                     }
                 });
             },
             error:function(e){
                 sweetAlert("网页发生错误：", e, "error");
             }
        });
	 });
	 
     /*首页、下一页、上一页、末页按钮*/
     $(".rolesSkip").click(function(){
          var rolesCurPageNum = Number(document.getElementById("rolesCurPageNum").value);//当前页码
          var rolesPageNum = 1;  //将要跳转的页码
          var rolesTotalPage = Number(document.getElementById("rolesTotalPage").value);//页码总数
          if (this.id == "rolesFirst") {//首页
        	  rolesPageNum = 1;
          } else if (this.id == "rolesPrevious") { //上一页
                 if (rolesCurPageNum > 1 && rolesCurPageNum <= rolesTotalPage) {
                	 rolesPageNum = Number(rolesCurPageNum) - 1;
                 } else {
                	 rolesPageNum = 1;
                 }
             } else if (this.id == "rolesNext") { //下一页
                 if (rolesCurPageNum < rolesTotalPage){
                	 rolesPageNum = Number(rolesCurPageNum) + 1;
                 }else {
                	 rolesPageNum = rolesTotalPage;
                 }
             } else if (this.id == "rolesEnd") { //末页
            	 rolesPageNum = rolesTotalPage;
             } else if (this.id == "rolesSearch") { //查询
            	 rolesPageNum = 1;
             } else if (this.id == "rolesJumpN") {
                 var rolesCurNo = Number(document.getElementById("rolesCurNo").value);
                 if (rolesCurNo < 1) {
                     sweetAlert("警告", "页号为正整数！", "warning");
                     return;
                 } else if (rolesCurNo > rolesTotalPage) {
                     sweetAlert("警告", "所选页号超过总页数！", "warning");
                     return;
                 }
                 rolesPageNum = Number(rolesCurNo);
             }

             //查询条件
             var roleName = document.getElementById("roleName").value;   
             var description = document.getElementById("description").value;   

             //ajax发请求
             $.ajax({
                 type : "GET",
                 url : "<%=basePath%>searchRoles.do?roleName=" + roleName + "&description=" + description + "&pageNum=" + rolesPageNum,
                 success : function(msg) {
                     var msgRes = JSON.parse(msg);//返回json对象
                     //更新记录总数
                     document.getElementById("rolesPage").rows[0].cells[1].innerHTML = "总计"+msgRes.roleTotalNum+"条记录";
                     $("#rolesCurPageNum").attr("value", rolesPageNum);
                     $("#rolesTotalPage").attr("value", msgRes.roleTotalPage);
                     $("#rolesCurNo").attr("value", rolesPageNum);

                     //清空table
                     var rolesTB = document.getElementById("rolesTab");
                     var rowNum = rolesTB.rows.length;
                     for (var i=1; i<rowNum; i++){
                    	 rolesTB.deleteRow(i);
                         rowNum = rowNum-1;
                         i = i-1;
                     }

                     //插入行数据
                     var rolesItem;
                     $.each(msgRes.roleDTOsList,function(i,result){
                         if (i % 2 ==0){
                        	 rolesItem = "<tr style=\"background-color:#ffffff\">\
                        		 <td >" + result.sortIndex + "</td>\
  				                 <td >" + result.roleName + "</td>\
  				                 <td >" + result.description + "</td>\
  				                 <td >" + result.gmtCreate + "</td>\
  				                 <td >" + result.gmtModified + "</td>\
  				                 <td >" + result.createBy + "</td>\
  				                 <td >" + result.lastModifedBy + "</td>\
  				                 <td ><a href=\"#\">修改</a></td>\
  				                 <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
  				                 <td style=\"display:none\">" + result.roleId + "</td></tr>";
                             $("#rolesTab").append(rolesItem);
                         }
                         else{
                        	 rolesItem = "<tr style=\"background-color:#f5f5f5\">\
                        		 <td >" + result.sortIndex + "</td>\
  				                 <td >" + result.roleName + "</td>\
  				                 <td >" + result.description + "</td>\
  				                 <td >" + result.gmtCreate + "</td>\
  				                 <td >" + result.gmtModified + "</td>\
  				                 <td >" + result.createBy + "</td>\
  				                 <td >" + result.lastModifedBy + "</td>\
  				                 <td ><a href=\"#\">修改</a></td>\
  				                 <td ><a href=\"#\"><font color=\"red\">删除</font></a></td>\
  				                 <td style=\"display:none\">" + result.roleId + "</td></tr>";
                             $("#rolesTab").append(rolesItem);
                         }
                     });
                 },
                 error:function(e){
                     sweetAlert("网页发生错误：", e, "error");
                 }
          });
     });
	</script>
	
	<div id="dialog" title="新建角色">
	    <div id="roleInfo" style="margin:5px 2px; 
			 border:1px solid #617775; background:#f5f5f5; 
			 width:98%; height:96%;">
		   <div style="margin:10px 10px;">
			  <label style="margin-left:10px; color:#ff0000;">角色名称*：</label>
			  <input id="roleName" type="text" style="width:320px" />
		   </div>
		   <div>
			   <label style="margin-left:20px;">角色描述：</label>
			   <input id="roleDetail" type="text" style="width:325px" />
		   </div>
		   <div id="relatedResource" class="ztree" 
				  style="margin:10px 10px; border:1px solid #617775; 
				  background:#f0f6e4; width:95%; height:250px; 
				  overflow-y:scroll; overflow-x:auto;">
		   </div>
		</div>
     </div>
    
    <script type="text/javascript">
      $("#newRole").click(function(){
    	  // 异步初始化新建角色
          var setting = {
                   check: {
                      enable: true
                   },
                   data: {
                       simpleData: {
                            enable: true
                       }
                   }
           };
           var treeNodes; 
           $.ajax({  
               cache : false, //是否使用缓存  
               type : 'POST', //请求方式：post  
               dataType : 'json',//数据传输格式：json  
               url : "<%=basePath%>initRoleResTree.do",  
               error : function() {  
                   sweetAlert("请求资源树数据失败！");
               },  
               success : function(data) {  
                   treeNodes = data;  //把后台封装好的简单Json格式赋给treeNodes  
                   var tree = $("#relatedResource");  
                   tree = $.fn.zTree.init(tree, setting, treeNodes); 
               }  
          });  
    	  
    	  $("#dialog").dialog("open");
    	  event.preventDefault();
	  });
    
      $("#dialog").dialog({
    	   autoOpen: false,
    	   width: 500,
    	   height: 500,
    	   buttons: [
    		 {
    			text: "创建",
    			click: function() {
    				var treeObj = $.fn.zTree.getZTreeObj("relatedResource");
    				var nodes = treeObj.getCheckedNodes(true);
    	            var length = nodes.length;

    	            var name = document.getElementById("roleName").value; //角色名称
    	            if(name==""){
    	                sweetAlert("角色名称不能为空！");
    	                return;
    	            }
    	            var description = document.getElementById("roleDetail").value; //角色描述
    	            var relatedResIds = "";   //关联资源id序列
    	            for(var i=0; i<length; i++) {
    	                if(i != length-1){
    	                    relatedResIds += nodes[i].id + ",";
    	                }else{
    	                    relatedResIds += nodes[i].id;
    	                }
    	            }

    	            $.ajax({
    	                type: "POST",
    	                url: "<%=basePath%>createRole?name=" + name + "&description=" + description + "&relatedResIds=" + relatedResIds,
    	                success: function () {
    	                	sweetAlert("创建角色成功！", "success");
    	                	window.location.href = "<%=basePath%>loadRoleList.do";
    	                },
    	                error: function (e) {
    	                    sweetAlert("创建角色失败：", e, "error");
    	                }
    	            });
    	            
    				$(this).dialog("close");
    			}
    		 },
    		 {
    			text: "取消",
    			click: function() {
    				$(this).dialog("close");
    			}
    		 }
    	 ]
       });
    </script>
  </body>
</html>