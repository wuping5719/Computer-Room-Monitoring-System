<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" type="text/css" href="static/css/error.css" />
<script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
<title>抱歉，您访问的页面正在整修中</title>
<style type="text/css">

</style>
<!--[if lte IE 8]>
<style type="text/css">
  h2 em { color:#e4ebf8; }
</style>
<![endif]-->

<script type="text/javascript">
  function returnFirst() {  
	  top.location.href = "loadHome.do";
  }
  
  $(function(){
	  var timeDom = $("#time");
	  function timeEve(){
	  	if(timeDom.length==0) return false;
	  	var num = parseInt(timeDom.html());
	  	function countDown(){
	  		if(num<=1){
	  			top.location.href="loadHome.do";
	  			return false;	
	  		}
	  		window.setTimeout(function(){
	  			num--;
	  			timeDom.html(num);
	  			countDown();
	  		},1000);
	  	};
	  	countDown();
	  }
	  timeEve();
	});
</script>

</head>
<body>
    <h1></h1>
    <p class="link">
        <a href="javascript:void(0);" onclick="returnFirst()" >&#9666;返回首页</a>
        <a href="javascript:history.go(-1);">&#9666;返回上一页</a>
    </p>
    <p>
      <dl class="texts" >
        <dt>抱歉，您访问的页面正在整修中...</dt>
        <dt><span id="time" > 10 </span>秒后为您跳转到系统首页 </dt>
      </dl>
	</p>
</body>
</html>
