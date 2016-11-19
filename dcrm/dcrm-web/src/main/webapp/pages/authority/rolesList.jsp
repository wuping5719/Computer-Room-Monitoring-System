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
	<title>角色信息列表</title>
	<link href="<%=basePath%>static/css/roles-layout.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>static/css/sweet-alert.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>static/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet" type="text/css" />
    
	<script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
	<script src="<%=basePath%>static/js/sweet-alert.min.js" type="text/javascript" ></script>
	<script src="<%=basePath%>static/jquery-easyui-1.4.5/jquery.min.js" type="text/javascript" ></script>
    <script src="<%=basePath%>static/jquery-easyui-1.4.5/jquery.easyui.min.js" type="text/javascript" ></script>
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
			   <span><strong style="font-size: 18px; color:#065b9b">角色列表</strong></span>
               <span style="margin-left:240px"></span>
               <span>角色名称：</span>
               <input id="name" class="easyui-textbox" style="width:200px;" />
               <span>角色描述：</span>
               <input id="description" class="easyui-textbox" style="width:200px;" />
               <a id="searchRoles" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
               <a id="clearRoles" href="#" class="easyui-linkbutton" iconCls="icon-search">清空</a>
			</div>
			
			<div id="roleTable">  
			   <div id="rolesList" class="easyui-tabs" ></div>  
			</div>
	   </div>	
	</div>
	
	<script type="text/javascript">
      var rolesDatagrid;
      $(document).ready(function() {  
    	  rolesDatagrid=$("#rolesList").datagrid({  
                loadMsg:'数据加载中....',  
                title:'角色信息一览表',          //标题
                iconCls:'icon-edit',         //图标 
                width:1060,                   
                height:400,                  //高度 
                //从远程站点请求数据的网址  
                url:'<%=basePath%>initRolesList.do',
                nowrap: false,         //在一行中显示数据,设置为真能提高加载性能
                striped: true,         //奇偶行颜色不同  
                border: true,  
                collapsible:true,      //是否可折叠的 
                //fitColumns: true,      //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例
                //fit: true,           //自动大小 
                idField:'id',          //主键字段 
                //sortName: 'id',      //定义该列可以排序
                //sortOrder: 'desc',    //定义的列的排序顺序，只能“升序”或“降序”
                remoteSort: false,     //定义是否从服务器上排序数据
                queryParams:{},        //查询条件    
                singleSelect:false,     //是否单选  false(非单选)   
                pagination:true,        //分页控件
                //rownumbers:true,      //行号
                showFooter: true,

                //每个列具体内容   
                columns:[[  
                    {title:'ID',field:'id',width:100,sortable:true,align:'center'},
                    {field:'name',title:'名称',width:108,sortable:true,align:'center'},  
                    {field:'description',title:'描述',width:110,sortable:true,align:'center'}, 
                    {field:'roleType',title:'类型',width:80,sortable:true,align:'center'}, 
                    {field:'roleCode',title:'编码',width:100,sortable:true,align:'center'},  
                    {field:'gmtCreate',title:'创建时间',width:130,sortable:true,align:'center'}, 
                    {field:'gmtModified',title:'最后修改时间',width:130,sortable:true,align:'center'},  
                    {field:'createBy',title:'创建人',width:100,sortable:true,align:'center'},  
                    {field:'lastModifedBy',title:'最后修改人',width:100,sortable:true,align:'center'},  
                    {field:'delete',title:'操作',width:100,align:'center',
                        formatter:function(value, row, rowIndex){ 
                            return '<a class="easyui-linkbutton" iconCls="icon-cut" href="<%=basePath%>deleteRoles.do?id='+row.id+'&specialNo='+row.specialNo+'" target=mainframe ><font color="red">删除</font></a>';  
                        }    
                    }     
                ]],  
                //设置奇偶行颜色不同
                rowStyler: function (index, row) { 
                     if (index % 2 == 0) { 
                       return 'background-color:#f5f5f5;'; 
                     }else{
                       return 'background-color:#dcdcdc;'; 
                     }
                },
                //工具条   
                toolbar:[{  
                    id:'btnadd',  
                    text:'增加',  
                    iconCls:'icon-add',  
                    handler:function(){   //回调函数 
                        window.location="";
                    }  
                },'-',{  
                    id:'btnmodify',  
                    text:'修改',  
                    iconCls:'icon-edit',  
                    handler:function(){  
                        window.location="";
                    }  
                }]     
        }); 
      
       var p = rolesDatagrid.datagrid('getPager');  
       if (p){  
             $(p).pagination({  
                 pageSize: 10,       //每页显示的记录条数，默认为10
                 pageList: [10,20,30],   //可以设置每页记录条数的列表  
                 beforePageText: '第',    //页数文本框前显示的汉字 
                 afterPageText: '页    共 {pages} 页',
                 displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',   
                });  
            }  
       });   
  </script>
</body>
</html>