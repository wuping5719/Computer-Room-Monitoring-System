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
<title>主页</title>
<meta charset="utf-8">
 <link href="<%=basePath%>static/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet" >
 <link href="<%=basePath%>static/css/main-style.css" rel="stylesheet" >
 <script src="<%=basePath%>static/js/jquery-1.9.1.min.js" type="text/javascript" ></script>
 <script src="<%=basePath%>static/js/main.js" type="text/javascript" ></script>
 
 <script src="<%=basePath%>static/echarts/doc/asset/js/esl/esl.js" type="text/javascript"></script>
 <script src="<%=basePath%>static/echarts/doc/asset/js/jquery.min.js" type="text/javascript"></script>
 <script src="<%=basePath%>static/echarts/doc/example/www/js/echarts.js" type="text/javascript"></script>
 <script src="<%=basePath%>static/echarts/doc/example/www/js/chart/map.js" type="text/javascript"></script>
 <script src="<%=basePath%>static/echarts/doc/example/geoJson/china-main-city/china-main-city-map.js" type="text/javascript"></script>
 <script src="<%=basePath%>static/echarts/zrender/src/tool/event.js" type="text/javascript"></script>
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
          <li ><a id="nav_li_a3" href="#">报警查询</a></li>
          <li ><a id="nav_li_a4" href="<%=basePath%>loadSiteCurve.do">站点数据</a></li>
          <li ><a id="nav_li_a5" href="#">设备管理</a></li>
          <li ><a id="nav_li_a6" href="#">用户管理</a></li>
          <li ><a id="nav_li_a7" href="#">权限管理</a></li>
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
        <div id="panel"></div>
        
      </div>

      <div id="footer">
        <p> Copyright  &copy;  2017  中国海洋大学     版权所有   </p>
      </div>
    </div> 
    
    <script type="text/javascript">
	 //声明js包的路径
	 require.config({
		 paths : {
		   'echarts' : "<%=basePath%>static/echarts/doc/example/www/js",
		   'echarts/chart/map' : "<%=basePath%>static/echarts/doc/example/www/js/chart",
		   'zrender' : "<%=basePath%>static/echarts/zrender",
		   'zrender/tool/event' : "<%=basePath%>static/echarts/zrender/src/tool"
		 }
	 });
	 // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
	 require(
	     [ 'echarts', 'echarts/chart/map', //按需加载图表关于地图相关的js文件
	     'zrender/tool/event'], DrawEChart //异步加载的回调函数绘制图表
	 );
					
	 var myChart;
					
     var mapType = [ 'china',
		// 23个省
		'广东', '青海', '四川', '海南', '陕西', '甘肃', '云南', '湖南', '湖北',
		'黑龙江', '贵州', '山东', '江西', '河南', '河北', '山西', '安徽',
		'福建', '浙江', '江苏', '吉林', '辽宁', '台湾',
		// 5个自治区
		'新疆', '广西', '宁夏', '内蒙古', '西藏',
		// 4个直辖市
		'北京', '天津', '上海', '重庆',
		// 2个特别行政区
		'香港', '澳门'  ];
					
	 var zrEvent = require('zrender/tool/event');
	 var curIndx = 0;

	 //创建ECharts图表方法
	 function DrawEChart(ec) {
		debugger;
		//定义图表options
		var option = {
			backgroundColor : '#eeffee',
			color : [ 'gold', 'aqua', 'lime' ],
			title : {
				text : '全国机房监控站点导航图',
				//subtext : 'DCRM',
				x : 'center',
				textStyle : {
				   color : '#000'
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : '{b}'
			},

			/* legend : {
				orient : 'vertical',
				x : 'left',
				data : [ '青岛站点' ],
				selectedMode : 'single',

			    selected : {
					'上海 ' : false,
					'广州 ' : false
				},

				textStyle : {
					color : '#eee'
				}
			}, */

			toolbox : {
				show : true,
				orient : 'vertical',
				x : 'right',
				y : 'center',
				feature : {
					dataView : {
						show : true,
						title : '数据视图',
						readOnly : false,
						lang : [ '数据视图', '关闭', '刷新' ]
					},
					restore : {
						show : true,
						title : '还原'
					},
					saveAsImage : {
						show : true,
						title : '保存为图片',
						type : 'png',
						lang : [ '点击保存' ]
					}
				}
			},

			roamController : {
				show : true,
				x : 'right',
				mapTypeControl : {
					'china' : true
				}
			}, 

			dataRange : {
				min : 0,
				max : 100,
				calculable : true,
				color : [ '#ff3333', 'orange', 'yellow', 'lime', 'aqua' ],
				textStyle : {
					color : '#fff'
				}
			}, 
			animationDurationUpdate : 2000, 
			series : [{
				name : '全国',
				type : 'map',
				roam : true,
				//showLegendSymbol:true,  
				hoverable : true,
				mapType : 'china',
				//selectedMode : 'multiple',
				itemStyle : {
					normal : {
					borderColor : 'rgba(100,149,237,1)',
					borderWidth : 0.5,
					label : {
					   show : true
					},
					labelLine : {
						show : true
					},
					areaStyle : {
						color:'#ffffff' //设置地图背景色的颜色设置  
					}
				},
				emphasis : {
					label : {
						show : true
					}
				}
			},
			
			data : [
				{name: '北京', selected:false,value:34}, {name: '天津', selected:false,value:12},
				{name: '上海', selected:false,value:30}, {name: '重庆', selected:false,value:10},
				{name: '河北', selected:false,value:5}, {name: '河南', selected:false,value:6},
				{name: '云南', selected:false,value:7}, {name: '辽宁', selected:false,value:8},
				{name: '黑龙江', selected:false,value:9}, {name: '湖南', selected:false,value:10},
				{name: '安徽', selected:false,value:11}, {name: '山东', selected:false,value:18},
				{name: '新疆', selected:false,value:13},	{name: '江苏', selected:false,value:14},				
				{name: '浙江', selected:false,value:28},	{name: '江西', selected:false,value:16},					
				{name: '湖北', selected:false,value:17}, {name: '广西', selected:false,value:9},
				{name: '甘肃', selected:false,value:19}, {name: '山西', selected:false,value:11},
				{name: '内蒙古', selected:false,value:8}, {name: '陕西', selected:false,value:22},
				{name: '吉林', selected:false,value:6}, {name: '福建', selected:false,value:10},
				{name: '贵州', selected:false,value:4}, {name: '广东', selected:false,value:36},
				{name: '青海', selected:false,value:3}, {name: '西藏', selected:false,value:2},
				{name: '四川', selected:false,value:25}, {name: '宁夏', selected:false,value:7},
				{name: '海南', selected:false,value:1}, {name: '台湾', selected:false,value:32},
				{name: '香港', selected:false,value:33}, {name: '澳门', selected:false,value:1} 
			], //各省地图颜色数据依赖value,
			
			markLine : {
				smooth : true,
				symbol : [ 'none', 'circle' ],
				symbolSize : 1,
				itemStyle : {
				normal : {
				color : '#fff',
				borderWidth : 1,
				borderColor : 'rgba(30,144,255,0.5)'
			}
		},
		
		data : [ 
		   [ {name : '北京'}, {name : '深圳'} ], [ {name : '深圳'}, {name : '北京'} ],
		   [ {name : '青岛'}, {name : '重庆'} ], [ {name : '重庆'}, {name : '青岛'} ], 
		   [ {name : '哈尔滨'}, {name : '乌鲁木齐'} ], [ {name : '乌鲁木齐'}, {name : '哈尔滨'} ], 
		   [ {name : '成都'}, {name : '大连'} ], [ {name : '大连'}, {name : '成都'} ],
		   [ {name : '上海'}, {name : '广州'} ], [ {name : '广州'}, {name : '上海'} ], 
		   [ {name : '杭州'}, {name : '西安'} ], [ {name : '西安'}, {name : '杭州'} ], 
		   [ {name : '沈阳'}, {name : '长沙'} ], [ {name : '长沙'}, {name : '沈阳'} ], 
		   [ {name : '贵阳'}, {name : '长春'} ], [ {name : '长春'}, {name : '贵阳'} ] ]},
	    
		geoCoord : {
			'北京' : [ 116.4551, 40.2539 ], '深圳' : [ 114.5435, 22.5439 ],
			'青岛' : [ 120.4651, 36.3373 ], '重庆' : [ 107.7539, 30.1904 ],
			'哈尔滨' : [ 127.9688, 45.368 ], '乌鲁木齐' : [ 87.9236, 43.5883 ],
			'成都' : [ 103.9526, 30.7617 ], '大连' : [ 122.2229, 39.4409 ],
			'上海' : [ 121.4648, 31.2891 ], '广州' : [ 113.5107, 23.2196 ],
			'杭州' : [ 119.5313, 29.8773 ], '西安' : [ 109.1162, 34.2004 ],
			'贵阳' : [ 106.6992, 26.7682 ], '长春' : [ 125.8154, 44.2584 ],
			'沈阳' : [ 123.1238, 42.1216 ], 

			/* 
			'东莞' : [ 113.8953, 22.901 ], '兰州' : [ 103.5901, 36.3043 ], 
			'包头' : [ 110.3467, 41.4899 ], '南京' : [ 118.8062, 31.9208 ],
			'南宁' : [ 108.479, 23.1152 ], '南昌' : [ 116.0046, 28.6633 ], 
			'厦门' : [ 118.1689, 24.6478 ], '呼和浩特' : [ 111.4124, 40.4901 ], 
			'唐山' : [ 118.4766, 39.6826 ], '大同' : [ 113.7854, 39.8035 ],
			'天津' : [ 117.4219, 39.4189 ], '太原' : [ 112.3352, 37.9413 ],
			'威海' : [ 121.9482, 37.1393 ], '宁波' : [ 121.5967, 29.6466 ],
			'宝鸡' : [ 107.1826, 34.3433 ], '延安' : [ 109.1052, 36.4252 ], 
            '徐州' : [ 117.5208, 34.3268 ], '扬州' : [ 119.4653, 32.8162 ], 
            '拉萨' : [ 91.1865, 30.1465 ], '无锡' : [ 120.3442, 31.5527 ], 
            '昆明' : [ 102.9199, 25.4663 ], '枣庄' : [ 117.323, 34.8926 ],
			'株洲' : [ 113.5327, 27.0319 ], '汕头' : [ 117.1692, 23.3405 ],
			'泉州' : [ 118.3228, 25.1147 ], '泰安' : [ 117.0264, 36.0516 ],
			'济南' : [ 117.1582, 36.8701 ], '海口' : [ 110.3893, 19.8516 ],
			'淄博' : [ 118.0371, 36.6064 ], '温州' : [ 120.498, 27.8119 ], 
			'潍坊' : [ 119.0918, 36.524 ], '烟台' : [ 120.7397, 37.5128 ],
			'珠海' : [ 113.7305, 22.1155 ], '盐城' : [ 120.2234, 33.5577 ],
			'石家庄' : [ 114.4995, 38.1006 ], '福州' : [ 119.4543, 25.9222 ],
			'秦皇岛' : [ 119.2126, 40.0232 ], '肇庆' : [ 112.1265, 23.5822 ],
			'舟山' : [ 122.2559, 30.2234 ], '苏州' : [ 120.6519, 31.3989 ],
			'菏泽' : [ 115.6201, 35.2057 ], '营口' : [ 122.4316, 40.4297 ],
			'葫芦岛' : [ 120.1575, 40.578 ], '西宁' : [ 101.4038, 36.8207 ],
			'连云港' : [ 119.1248, 34.552 ], '湘潭' : [ 112.5439, 27.7075 ],
			'邢台' : [ 114.8071, 37.2821 ], '邯郸' : [ 114.4775, 36.535 ],
			'鄂尔多斯' : [ 108.9734, 39.2487 ], '银川' : [ 106.3586, 38.1775 ],
			'长治' : [ 112.8625, 36.4746 ], 
		    */
		    
		    '长沙' : [ 113.0823, 28.2568 ]}
        }, {
			name : '青岛站点',
			type : 'map',
			mapType : 'china',
			data : [],
			markLine : {
				smooth : true,
				smoothness : 0.1,

		     /* bundling : {
					enable : true
				},
			 */

				effect : {
					show : true,
					scaleSize : 1,
					period : 30,
					color : '#fff',
					shadowBlur : 10
				},
				itemStyle : {
					normal : {
					borderWidth : 1,
					lineStyle : {
						type : 'solid',
						shadowBlur : 10
					}
				}
			},
			
			data : [ [ {name : '北京'}, {name : '深圳', value : 90} ], 
			         [ {name : '深圳'}, {name : '北京', value : 90} ], 
			         [ {name : '青岛'}, {name : '重庆', value : 30} ], 
			         [ {name : '重庆'}, {name : '青岛', value : 30} ], 
			         [ {name : '哈尔滨'}, {name : '乌鲁木齐', value : 10} ], 
			         [ {name : '乌鲁木齐'}, {name : '哈尔滨', value : 10} ], 
			         [ {name : '成都'}, {name : '大连', value : 56} ], 
			         [ {name : '大连'}, {name : '成都', value : 56} ], 
			         [ {name : '上海'}, {name : '广州', value : 88} ],
			         [ {name : '广州'}, {name : '上海', value : 88} ], 
			         [ {name : '杭州'}, {name : '西安', value : 66} ], 
			         [ {name : '西安'}, {name : '杭州', value : 66} ], 
			         [ {name : '沈阳'}, {name : '长沙', value : 18} ], 
			         [ {name : '长沙'}, {name : '沈阳', value : 18} ], 
			         [ {name : '贵阳'}, {name : '长春', value : 40} ], 
			         [ {name : '长春'}, {name : '贵阳', value : 40} ] ]
			},
			markPoint : {
				//symbol : 'diamond',
				//symbolSize: 6,
				//large: true,
				//symbol: 'image://../asset/img/echarts-logo.png',
				x : 300,
				y : 100,
				symbol : 'emptyCircle',
				symbolSize : function(v) {
					return 10 + v / 10;
				},
				effect : {
					show : true,
					shadowBlur : 0
				},
				itemStyle : {
					normal : {
					  color : '#32cd32',
					  label : {
						 show : true,
						 textStyle : {
						    color : '#fff',
						    fontSize : 15
						 }
					  }
				},
				emphasis : {
					color : '#cd5c5c',
					label : {
						show : true,
						position : 'top',
						textStyle : {
							color : 'blue'
						}
					}
				}
			},
			data : [ {name : '北京', value : 95}, {name : '深圳', value : 80}, 
			         {name : '青岛', value : 30}, {name : '重庆', value : 32}, 
			         {name : '哈尔滨', value : 10}, {name : '乌鲁木齐', value : 6}, 
			         {name : '成都', value : 20}, {name : '大连', value : 18}, 
			         {name : '上海', value : 76}, {name : '广州', value : 68},
			         {name : '杭州', value : 56}, {name : '西安', value : 36},
			         {name : '沈阳', value : 22}, {name : '长沙', value : 12},
			         {name : '贵阳', value : 9}, {name : '长春', value : 5}]
			}
		} ]
	};

	/* 
	myChart.showLoading({
	   text: '正在努力的读取数据中...',    
	});
	*/

    //图表对象渲染
	myChart = ec.init(document.getElementById('panel'));
	var ecConfig = require('echarts/config');
						
	myChart.on(ecConfig.EVENT.CLICK,
		function(param) {
		   if(param.name=='山东'){
			  window.location.href = "<%=basePath%>pages/shanDong.jsp";
		   }
	});

    myChart.setOption(option);
    }
	</script>
  </body>
</html>