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
	<title>资源信息列表</title>
	<link href="<%=basePath%>static/css/resources-layout.css" rel="stylesheet" type="text/css" />
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
			   <span><strong style="font-size: 18px; color:#065b9b">资源信息列表</strong></span>
               <span style="margin-left:282px"></span>
               <span>资源名称:</span>
               <input size="12" type="text" id="name" style="background-color:#e4e4e4;"/>
               <span>标识码:</span>
               <input size="12" type="text" id="resCode" style="background-color:#e4e4e4;"/>
               <span>资源类型:</span>
               <select id="resType">
                 <option selected="true" >全部</option>
                 <option>URL资源</option>
                 <option>组件资源</option>
               </select>
               <span style="margin-left:20px"></span>
               <input id="queryRes" size="12" type="button" value="搜索" style="height:25px;"/>
               <input id="newRes" size="12" type="button" value="新建" style="height:25px; color:#0000ff;"/>
			</div>
			
			<div id="resTreeGrid" style="">
              <table id="tgTab" title="查询结果" class="easyui-treegrid" style="width:1080px;height:450px">
                 <thead>
                  <tr>
                     <th data-options="field:'name',width:160">资源名称</th>
                     <th data-options="field:'description',width:100">资源描述</th>
                     <th data-options="field:'code',width:100, align:'center' ">标识码</th>
                     <th data-options="field:'type',width:50, align:'center' ">类型</th>
                     <th data-options="field:'gmtCreate',width:80,align:'center' ">创建时间</th>
                     <th data-options="field:'gmtModified',width:80, align:'center'">最后修改时间</th>
                     <th data-options="field:'id',width:60,formatter:rowFormatter, align:'center' ">操作</th>
                  </tr>
                </thead>
             </table>
           </div>
	   </div>	
	</div>
	
	<script type="text/javascript">
        (function($){
            function pagerFilter(data){
                if ($.isArray(data)){
                    data = {
                        total: data.length,
                        rows: data
                    }
                }
                var dg = $(this);
                var state = dg.data('treegrid');
                var opts = dg.treegrid('options');
                var pager = dg.treegrid('getPager');
                pager.pagination({
                    onSelectPage:function(pageNum, pageSize){
                        opts.pageNumber = pageNum;
                        opts.pageSize = pageSize;
                        pager.pagination('refresh',{
                            pageNumber: pageNum,
                            pageSize: pageSize,
                            beforePageText: '第',  //页数文本框前显示的汉字
                            afterPageText: '页 共 {pages} 页',
                            displayMsg: '当前显示 {from} - {to} 条记录  共 {total} 条记录'
                        });
                        dg.treegrid('loadData',state.originalRows);
                    },
                    beforePageText: '第',  //页数文本框前显示的汉字
                    afterPageText: '页 共 {pages} 页',
                    displayMsg: '当前显示 {from} - {to} 条记录  共 {total} 条记录'
                });
                if (!state.originalRows){
                    state.originalRows = data.rows;
                }
                var topRows = [];
                var childRows = [];
                $.map(state.originalRows, function(row){
                    row._parentId ? childRows.push(row) : topRows.push(row);
                });
                data.total = topRows.length;
                var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
                var end = start + parseInt(opts.pageSize);
                data.rows = $.extend(true,[],topRows.slice(start, end).concat(childRows));
                return data;
            }

            var appendMethod = $.fn.treegrid.methods.append;
            var loadDataMethod = $.fn.treegrid.methods.loadData;
            $.extend($.fn.treegrid.methods, {
                clientPaging: function(jq){
                    return jq.each(function(){
                        var state = $(this).data('treegrid');
                        var opts = state.options;
                        opts.loadFilter = pagerFilter;
                        var onBeforeLoad = opts.onBeforeLoad;
                        opts.onBeforeLoad = function(row,param){
                            state.originalRows = null;
                            onBeforeLoad.call(this, row, param);
                        };
                        $(this).treegrid('loadData', state.data);
                        $(this).treegrid('reload');
                    });
                },
                loadData: function(jq, data){
                    jq.each(function(){
                        $(this).data('treegrid').originalRows = null;
                    });
                    return loadDataMethod.call($.fn.treegrid.methods, jq, data);
                },
                append: function(jq, param){
                    return jq.each(function(){
                        var state = $(this).data('treegrid');
                        if (state.options.loadFilter == pagerFilter){
                            $.map(param.data, function(row){
                                row._parentId = row._parentId || param.parent;
                                state.originalRows.push(row);
                            });
                            $(this).treegrid('loadData', state.originalRows);
                        } else {
                            appendMethod.call($.fn.treegrid.methods, jq, param);
                        }
                    })
                }
            });
        })(jQuery);

        $(function(){
            $('#tgTab').treegrid({
                iconCls: 'icon-ok',
                rownumbers: true,
                animate: true,
                collapsible: true,
                fitColumns: true,
                url: '<%=basePath%>initResourcesList.do?type=-1',
                method: 'get',
                idField: 'id',
                treeField: 'name',
                pagination: true,
                pageSize: 20,
                pageList: [20,50,80]
            }).treegrid('clientPaging');
        });

        function rowFormatter(value, row, index){
            //value为该条数据的field字段的值，row为该行（显示在页面）数据的所有信息，index当前行，单引号里面必须双引号，双引号里面必须单引号，里面再有就需转义
            return '<img id="update" src="<%=basePath%>static/img/update.png" onclick="updateRes('+value+')"/>&nbsp;&nbsp;' +
                    '&nbsp;&nbsp;<img id="delete" src="<%=basePath%>static/img/delete.png" onclick="deleteRes('+value+')"/>';
        }

        // 新建资源
        $("#newRes").click(function () {
            window.location.href = "newResource";
        });

        // 查询资源
        $("#queryRes").click(function () {
            var resName = document.getElementById("name").value;   //资源名称
            var resCode = document.getElementById("resCode").value;
            var resTypeStr = document.getElementById("resType").value;
            var resType = -1;
            if(resTypeStr === "全部"){
                resType = -1;
            }else if(resTypeStr === "组件资源"){
                resType = 1;
            }else{
                resType = 0;
            }
            var urlData="<%=basePath%>initResourcesList.do?type="+ resType +"&name="+ resName +"&code="+ resCode;
            $('#tgTab').treegrid({url: urlData}).treegrid({url: urlData});
        });

        function deleteRes(id) {
            swal({
                title:"提示",
                text:"您确定要删除该资源吗？",
                type:"warning",
                showCancelButton:"true",
                showConfirmButton:"true",
                confirmButtonText:"确定",
                cancelButtonText:"取消",
                animation:"slide-from-top"
            }, function() {
                $.ajax({
                    type : "GET",
                    url : "../api/deleteResource?id=" + id
                }).done(function(msg) {
                    if(msg=="success"){
                        swal("操作成功!", "已成功删除资源！", "success");
                        window.location.href = "resourcesList";
                    }else{
                        swal("操作失败!", "该资源已关联角色，请先将其移除角色！", "warning");
                    }
                }).error(function() {
                    swal("OMG", "删除操作失败了!", "error");
                });
            });
        }

        function updateRes(id) {
            window.location.href = "resourceInfo?id="+ id;
        }
    </script>
</body>
</html>