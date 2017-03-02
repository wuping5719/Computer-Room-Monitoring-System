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
<title>报警信息</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">   <!-- 初始化移动浏览显示  -->

 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/sweet-alert.css" rel="stylesheet" type="text/css" />
 <link href="<%=basePath%>static/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/alerts-layout.css" rel="stylesheet" >
 
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/js/sweet-alert.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/jquery-ui-1.11.4/external/jquery/jquery.js"></script>
 <script src="<%=basePath%>static/jquery-ui-1.11.4/jquery-ui.js"></script>
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
			   <label>时间：</label>
			   <input type="text" class="Wdate" id="startDate" 
					onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd', readOnly:true})"
			        style="margin-top:5px; height:24px; width:100px;"></input>
			   <label>-</label>
			   <input type="text" class="Wdate" id="endDate" 
					onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd', readOnly:true})"
			        style="margin-top:5px; height:24px; width:100px;"></input>
               <label style="margin-left:5px;">站点：</label>
               <input id="siteName" style="width:80px; height:24px;" type="text" />
               <label style="margin-left:5px;">报警原因：</label>
               <input id="reason" style="width:100px; height:24px;" type="text" />
               <label style="margin-left:5px;">报警级别：</label>
               <select id="level" style="width:80px; height:24px;" >
			      <option value="默认">默认</option>
			      <option value="紧急报警">紧急报警</option>
			      <option value="重要报警">重要报警</option>
			      <option value="一般报警">一般报警</option>
		       </select>
               <img id="alertsSearch" class="alertsSkip" style="margin-left:10px;"
                   src="<%=basePath%>static/img/search.png" />
               <input type="button" id="cancelAll" value="取消全部报警" style="margin-left:5px; height:24px;" />
               
               <input type="button" id="setLevel" value="级别配置" style="margin-left:5px; height:24px;" />
			   <input type="button" id="setPush" value="报警推送" style="margin-left:5px; height:24px;" />
			</div>
			
			<div id="alertsTable" >
				<table id="alertInfosTab">
					<tr style="font-weight:bold; background-color:#e1ebf5; text-align:center;">
						<td style="width:40px">序号</td>
						<td style="width:60px">站点名称</td>
						<td style="width:100px">设备名称</td>
						<td style="width:90px">报警时间</td>
						<td style="width:70px">报警级别</td>
						<td style="width:80px">报警类型</td>
						<td style="width:150px">报警原因</td>
						<td style="width:50px">处理状态</td>
						<td style="width:50px">所在城市</td>
						<td style="display:none">主键ID</td>
					</tr>
				</table>
				
				<table id="alertInfosPage">
					<tr style="height:5px;">
                        <td style="width :20px;"></td>
                        <td id="alertsTotalNum" style="width:50px;" ></td>
                        <td style="width:40px;">
                            <img id="alertsFirst" class="alertsSkip" src="<%=basePath%>static/img/first.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="alertsPrevious" class="alertsSkip" src="<%=basePath%>static/img/left.png" />
                        </td>
                        <td id="alertsCenterBar" style="width:60px;" >
                            <input id="alertsCurPageNum" readonly="readonly" style="width:30px;"/>
                            <label>/</label>
                            <input id="alertsTotalPage" readonly="readonly" style="width:30px;" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;">
                            <img id="alertsNext" class="alertsSkip" src="<%=basePath%>static/img/right.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="alertsEnd" class="alertsSkip" src="<%=basePath%>static/img/end.png" />
                        </td>
                        <td style="width:60px;" >
                            <label>跳转到</label>
                            <input id ="alertsCurNo" style="width:40px;" type="text" />
                            <label>页</label>
                        </td>
                        <td style="width:40px;" >
                            <img id="alertsJumpN" class="alertsSkip" src="<%=basePath%>static/img/sureBtn.png" />
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
             url : "<%=basePath%>searchAlertInfos.do?startDate=&endDate=&siteName=&reason=&level=&pageNum=1",
             success : function(msg) {
                 var msgRes = JSON.parse(msg);  //返回json对象
                 //更新记录总数
                 document.getElementById("alertInfosPage").rows[0].cells[1].innerHTML = "总计"+msgRes.alertsTotalNum+"条记录";
                 $("#alertsCurPageNum").attr("value", 1);
                 $("#alertsTotalPage").attr("value", msgRes.alertsTotalPage);
                 $("#alertsCurNo").attr("value", 1);

                 //清空table
                 var alertsTB = document.getElementById("alertInfosTab");
                 var rowNum = alertsTB.rows.length;
                 for (var i=1; i<rowNum; i++){
                	 alertsTB.deleteRow(i);
                     rowNum = rowNum-1;
                     i = i-1;
                 }

                 //插入行数据
                 var alertsItem;
                 $.each(msgRes.alertInfoDTOs,function(i, result){
                     if (i % 2 ==0){
                    	 alertsItem = "<tr style=\"background-color:#ffffff\">\
				               <td >" + result.sortIndex + "</td>\
				               <td >" + result.siteName + "</td>\
				               <td >" + result.insName + "</td>\
				               <td >" + result.alertTime + "</td>\
				               <td >" + result.reasonLevel + "</td>\
				               <td >" + result.reasonType + "</td>\
				               <td >" + result.description + "</td>\
				               <td ><a href=\"#\">"+ result.isSolved + "</a></td>\
				               <td >" + result.cityName + "</td>\
				               <td style=\"display:none\">" + result.alertId + "</td></tr>";
                         $("#alertInfosTab").append(alertsItem);
                     }
                     else{
                    	 alertsItem = "<tr style=\"background-color:#f5f5f5\">\
                    		 <td >" + result.sortIndex + "</td>\
				               <td >" + result.siteName + "</td>\
				               <td >" + result.insName + "</td>\
				               <td >" + result.alertTime + "</td>\
				               <td >" + result.reasonLevel + "</td>\
				               <td >" + result.reasonType + "</td>\
				               <td >" + result.description + "</td>\
				               <td ><a href=\"#\">"+ result.isSolved + "</a></td>\
				               <td >" + result.cityName + "</td>\
				               <td style=\"display:none\">" + result.alertId + "</td></tr>";
                         $("#alertInfosTab").append(alertsItem);
                     }
                 });
             },
             error:function(e){
                 sweetAlert("网页发生错误：", e, "error");
             }
      });
      
     /*报警信息  首页、下一页、上一页、末页按钮*/
     $(".alertsSkip").click(function(){
          var alertsCurPageNum = Number(document.getElementById("alertsCurPageNum").value); //当前页码
          var alertsPageNum = 1;  //将要跳转的页码
          var alertsTotalPage = Number(document.getElementById("alertsTotalPage").value); //页码总数
          if (this.id == "alertsFirst") { //首页
            	 usersPageNum = 1;
          } else if (this.id == "alertsPrevious") { //上一页
                 if (alertsCurPageNum > 1 && alertsCurPageNum <= alertsTotalPage) {
                	 alertsPageNum = Number(alertsCurPageNum) - 1;
                 } else {
                	 alertsPageNum = 1;
                 }
             } else if (this.id == "alertsNext") { //下一页
                 if (alertsCurPageNum < alertsTotalPage){
                	 alertsPageNum = Number(alertsCurPageNum) + 1;
                 }else {
                	 alertsPageNum = alertsTotalPage;
                 }
             } else if (this.id == "alertsEnd") { //末页
            	 alertsPageNum = alertsTotalPage;
             } else if (this.id == "alertsSearch") { //查询
            	 alertsPageNum = 1;
             } else if (this.id == "alertsJumpN") {
                 var alertsCurNo = Number(document.getElementById("alertsCurNo").value);
                 if (alertsCurNo < 1) {
                     sweetAlert("警告", "页号为正整数！", "warning");
                     return;
                 } else if (alertsCurNo > alertsTotalPage) {
                     sweetAlert("警告", "所选页号超过总页数！", "warning");
                     return;
                 }
                 alertsPageNum = Number(alertsCurNo);
             }

             //查询条件
             var startDate = document.getElementById("startDate").value;   
             var endDate = document.getElementById("endDate").value;   
             var siteName = document.getElementById("siteName").value;     
             var reason = document.getElementById("reason").value;   
             var level = document.getElementById("level").value;   
             
             //ajax发请求
             $.ajax({
                 type : "GET",
                 url : "<%=basePath%>searchAlertInfos.do?startDate=" + startDate + "&endDate=" + endDate + "&siteName=" + siteName + "&reason=" + reason + "&level=" + level + "&pageNum=" + alertsPageNum,
                 success : function(msg) {
                     var msgRes = JSON.parse(msg); //返回json对象
                     //更新记录总数
                     document.getElementById("alertInfosPage").rows[0].cells[1].innerHTML = "总计"+msgRes.alertsTotalNum+"条记录";
                     $("#alertsCurPageNum").attr("value", alertsPageNum);
                     $("#alertsTotalPage").attr("value", msgRes.alertsTotalPage);
                     $("#alertsCurNo").attr("value", alertsPageNum);

                     //清空table
                     var alertsTB = document.getElementById("alertInfosTab");
                     var rowNum = alertsTB.rows.length;
                     for (var i=1; i<rowNum; i++){
                    	 alertsTB.deleteRow(i);
                         rowNum = rowNum-1;
                         i = i-1;
                     }

                     //插入行数据
                     var alertsItem;
                     $.each(msgRes.alertInfoDTOs,function(i,result){
                         if (i % 2 ==0){
                        	 alertsItem = "<tr style=\"background-color:#ffffff\">\
                        	    <td >" + result.sortIndex + "</td>\
  				                <td >" + result.siteName + "</td>\
  				                <td >" + result.insName + "</td>\
  				                <td >" + result.alertTime + "</td>\
  				                <td >" + result.reasonLevel + "</td>\
  				                <td >" + result.reasonType + "</td>\
  				                <td >" + result.description + "</td>\
  				                <td ><a href=\"#\">"+ result.isSolved + "</a></td>\
  				                <td >" + result.cityName + "</td>\
  				                <td style=\"display:none\">" + result.alertId + "</td></tr>";
                             $("#alertInfosTab").append(alertsItem);
                         }
                         else{
                        	 alertsItem = "<tr style=\"background-color:#f5f5f5\">\
                        		<td >" + result.sortIndex + "</td>\
  				                <td >" + result.siteName + "</td>\
  				                <td >" + result.insName + "</td>\
  				                <td >" + result.alertTime + "</td>\
  				                <td >" + result.reasonLevel + "</td>\
  				                <td >" + result.reasonType + "</td>\
  				                <td >" + result.description + "</td>\
  				                <td ><a href=\"#\">"+ result.isSolved + "</a></td>\
  				                <td >" + result.cityName + "</td>\
  				                <td style=\"display:none\">" + result.alertId + "</td></tr>";
                             $("#alertInfosTab").append(alertsItem);
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
	
	<div id="levelDialog" title="报警级别配置">
	    <div style="margin:2px 2px; border:1px solid #617775; background:#f5f5f5; width:98%; height:98%;">
            <div id="queryReason" >
               <label style="margin-left:50px; margin-top:8px;">报警原因：</label>
               <input id="reason" style="margin-top:8px; width:100px; height:24px;" type="text" />
               <label style="margin-left:20px; margin-top:8px;">报警级别：</label>
               <select id="level" style="margin-top:8px; width:100px; height:24px;" >
			      <option value="全部">全部</option>
			      <option value="紧急报警">紧急报警</option>
			      <option value="重要报警">重要报警</option>
			      <option value="一般报警">一般报警</option>
		       </select>
               <img id="reasonSearch" class="reasonSkip" src="<%=basePath%>static/img/search.png" />
			</div>
			
			<div id="reasonList" style="margin-top:8px;">
				<table id="reasonTab" style="text-align:center; margin-left:10px; width:520px"> 
					<tr style="height:6px; font-weight:bold; background-color:#e1ebf5; text-align:center;">
						<td style="width:50px">序号</td>
						<td style="width:200px">报警原因</td>
						<td style="width:80px">报警级别</td>
						<td style="width:80px">报警类型</td>
						<td style="display:none">主键ID</td>
					</tr>
					<tr style="height:6px; background-color:#ffffff">
						<td>1</td>
						<td>精密空调C1002回风湿度过高</td>
						<td>
						    <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
		                </td>
						<td>模拟量</td>
						<td style="display:none">1</td>
					</tr>
					<tr style="height:6px; background-color:#eeffff;">
						<td>2</td>
						<td>精密空调C1002模块1压缩机故障</td>
						<td> 
						   <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
						</td>
						<td>开关量</td>
						<td style="display:none">2</td>
					</tr>
					<tr style="height:6px; background-color:#ffffff">
						<td>3</td>
						<td>精密空调C1002模块1泵1故障</td>
						<td>
						    <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
		                </td>
						<td>开关量</td>
						<td style="display:none">3</td>
					</tr>
					<tr style="height:6px; background-color:#eeffff;">
						<td>4</td>
						<td>精密空调C5000送风温度过高</td>
						<td> 
						   <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
						</td>
						<td>模拟量</td>
						<td style="display:none">4</td>
					</tr>
					<tr style="height:6px; background-color:#ffffff">
						<td>5</td>
						<td>精密空调C5000模块2气流故障</td>
						<td>
						    <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
		                </td>
						<td>模拟量</td>
						<td style="display:none">5</td>
					</tr>
					<tr style="height:6px; background-color:#eeffff;">
						<td>6</td>
						<td>稳压电源交流输入A相相电压告警</td>
						<td> 
						   <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
						</td>
						<td>模拟量</td>
						<td style="display:none">6</td>
					</tr>
					<tr style="height:6px; background-color:#ffffff">
						<td>7</td>
						<td>烟感告警</td>
						<td>
						    <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
		                </td>
						<td>开关量</td>
						<td style="display:none">7</td>
					</tr>
					<tr style="height:6px; background-color:#eeffff;">
						<td>8</td>
						<td>火感告警</td>
						<td> 
						   <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
						</td>
						<td>开关量</td>
						<td style="display:none">8</td>
					</tr>
					<tr style="height:6px; background-color:#ffffff">
						<td>9</td>
						<td>红外告警</td>
						<td>
						    <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
		                </td>
						<td>开关量</td>
						<td style="display:none">9</td>
					</tr>
					<tr style="height:6px; background-color:#eeffff;">
						<td>10</td>
						<td>UPS输出电压过低</td>
						<td>
						    <select style="width:100px; height:24px;" >
			                   <option value="紧急报警">紧急报警</option>
			                   <option value="重要报警">重要报警</option>
			                   <option value="一般报警">一般报警</option>
		                    </select>
		                </td>
						<td>模拟量</td>
						<td style="display:none">10</td>
					</tr>
				</table>
				
				<table id="reasonPage" style="margin-top:8px;">
					<tr style="height:5px;">
                        <td style="width:5px;"></td>
                        <td style="width:20px;" ></td>
                        <td style="width:50px;">
                            <img id="reasonFirst" class="reasonSkip" src="<%=basePath%>static/img/first.png" />
                        </td>
                        <td style="width:50px;">
                            <img id="reasonPrevious" class="reasonSkip" src="<%=basePath%>static/img/left.png" />
                        </td>
                        <td id="reasonCenterBar" style="width:120px;" >
                            <input id="reasonCurPageNum" readonly="readonly" style="width:30px; height:24px;" value="1" />
                            <label>/</label>
                            <input id="reasonTotalPage" readonly="readonly" style="width:30px; height:24px;" value="2" />
                            <label>页</label>
                        </td>
                        <td style="width:50px;">
                            <img id="reasonNext" class="reasonSkip" src="<%=basePath%>static/img/right.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="reasonEnd" class="reasonSkip" src="<%=basePath%>static/img/end.png" />
                        </td>
                        <td style="width:120px;" >
                            <label>跳转到</label>
                            <input id ="reasonCurNo" style="width:40px; height:24px;" type="text" value="1" />
                            <label>页</label>
                        </td>
                        <td style="width:50px;" >
                            <img id="reasonJumpN" class="reasonSkip" src="<%=basePath%>static/img/sureBtn.png" />
                        </td>
                        <td style="width:20px;"></td>
                    </tr>
				</table>
		   </div>
		</div>
    </div>
    
    <script type="text/javascript">
      $("#setLevel").click(function(){
    	  $("#levelDialog").dialog("open");
    	  event.preventDefault();
	  });
    
      $("#levelDialog").dialog({
    	   autoOpen: false,
    	   width: 600,
    	   height: 500,
    	   buttons: [
    		 {
    			text: "确认",
    			click: function() {
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
    
    <div id="pushDialog" title="报警推送设置">
	    <div style="margin:2px 2px; border:1px solid #617775; background:#f5f5f5; width:98%; height:98%;">
            <div id="queryPush" >
               <label style="margin-left:100px; margin-top:8px;">姓名：</label>
               <input id="pushName" style="margin-top:8px; width:100px; height:24px;" type="text" />
               <label style="margin-left:10px; margin-top:8px;">站点名：</label>
               <input id="siteName" style="margin-top:8px; width:100px; height:24px;" type="text" />
               <img id="pushSearch" class="reasonSkip" src="<%=basePath%>static/img/search.png" />
			</div>
			<div id="pushSelect" >
               <label><input id="allSMS" type="checkbox" style="margin-left:20px; margin-top:16px;" />短信报警 </label>
               <label><input id="allEmail" type="checkbox" style="margin-left:100px; margin-top:16px;" />邮件报警 </label>
               <label><input id="allVoice" type="checkbox" style="margin-left:100px; margin-top:16px;" />语音拨号报警 </label>
			</div>
			
			<div id="pushList" style="margin-top:8px;">
				<table id="pushTab" style="text-align:center; margin-left:10px; width:520px"> 
					<tr style="height:10px; font-weight:bold; background-color:#e1ebf5; text-align:center;">
						<td style="width:50px">序号</td>
						<td style="width:80px">姓名</td>
						<td style="width:80px">所属站点</td>
						<td style="width:80px">短信报警</td>
						<td style="width:80px">邮件报警</td>
						<td style="width:100px">语音拨号报警</td>
						<td style="display:none">主键ID</td>
					</tr>
					<tr style="height:10px; background-color:#ffffff">
						<td>1</td>
						<td>小雪</td>
						<td>李村</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">1</td>
					</tr>
					<tr style="height:10px; background-color:#eeffff">
						<td>2</td>
						<td>王磊</td>
						<td>浮山</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">2</td>
					</tr>
					<tr style="height:10px; background-color:#ffffff">
						<td>3</td>
						<td>刘敏</td>
						<td>金家岭</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">3</td>
					</tr>
					<tr style="height:10px; background-color:#eeffff">
						<td>4</td>
						<td>张明</td>
						<td>澄海三路</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">4</td>
					</tr>
					<tr style="height:10px; background-color:#ffffff">
						<td>5</td>
						<td>张涛</td>
						<td>金家岭</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">5</td>
					</tr>
					<tr style="height:10px; background-color:#eeffff">
						<td>6</td>
						<td>刘鹏</td>
						<td>李村</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">6</td>
					</tr>
					<tr style="height:10px; background-color:#ffffff">
						<td>7</td>
						<td>余浩</td>
						<td>王哥庄</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">7</td>
					</tr>
					<tr style="height:10px; background-color:#eeffff">
						<td>8</td>
						<td>吴迪</td>
						<td>人民一路</td>
						<td><input name="sms" type="checkbox"/></td>
						<td><input name="email" type="checkbox"/></td>
						<td><input name="voice" type="checkbox"/></td>
						<td style="display:none">8</td>
					</tr>
				</table>
				
				<table id="pushPage" style="margin-top:8px;">
					<tr style="height:5px;">
                        <td style="width:5px;"></td>
                        <td style="width:20px;" ></td>
                        <td style="width:50px;">
                            <img id="pushFirst" class="pushSkip" src="<%=basePath%>static/img/first.png" />
                        </td>
                        <td style="width:50px;">
                            <img id="pushPrevious" class="pushSkip" src="<%=basePath%>static/img/left.png" />
                        </td>
                        <td id="pushCenterBar" style="width:120px;" >
                            <input id="pushCurPageNum" readonly="readonly" style="width:30px; height:24px;" value="1" />
                            <label>/</label>
                            <input id="pushTotalPage" readonly="readonly" style="width:30px; height:24px;" value="3" />
                            <label>页</label>
                        </td>
                        <td style="width:50px;">
                            <img id="pushNext" class="pushSkip" src="<%=basePath%>static/img/right.png" />
                        </td>
                        <td style="width:40px;">
                            <img id="pushEnd" class="pushSkip" src="<%=basePath%>static/img/end.png" />
                        </td>
                        <td style="width:120px;" >
                            <label>跳转到</label>
                            <input id ="pushCurNo" style="width:40px; height:24px;" type="text" value="1" />
                            <label>页</label>
                        </td>
                        <td style="width:50px;" >
                            <img id="pushJumpN" class="pushSkip" src="<%=basePath%>static/img/sureBtn.png" />
                        </td>
                        <td style="width:20px;"></td>
                    </tr>
				</table>
		   </div>
		</div>
    </div>
    
    <script type="text/javascript">
      $("#setPush").click(function(){
    	  $("#pushDialog").dialog("open");
    	  event.preventDefault();
	  });
    
      $("#pushDialog").dialog({
    	   autoOpen: false,
    	   width: 600,
    	   height: 465,
    	   buttons: [
    		 {
    			text: "确认",
    			click: function() {
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