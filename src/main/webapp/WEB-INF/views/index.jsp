<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=10,IE=9,IE=8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title>蓝雨麦浪的壁纸库</title>
<script type="text/javascript"
	src="${ctx}/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${ctx}/static/js/blocksit.min.js"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background: url(${ctx}/static/images/bg.gif) 0 0 repeat #f7f5f5;
	color: #333;
	font-family: Cambria, Georgia, serif;
	font-size: 15px;
	overflow-x: hidden;
}

.wrapper {
	width: 100%;
	margin-top: 50px;
}

.content {
	margin: 0 auto 25px;
	padding-bottom: 10px;
	position: relative;
	width: 1400px;
}

.grid {
	width: 300px;
	min-height: 100px;
	padding: 15px;
	background: #fff;
	margin: 8px;
	font-size: 12px;
	float: left;
	box-shadow: 0 1px 3px rgba(34, 25, 25, 0.4);
	-moz-box-shadow: 0 1px 3px rgba(34, 25, 25, 0.4);
	-webkit-box-shadow: 0 1px 3px rgba(34, 25, 25, 0.4);
	-webkit-transition: top 1s ease, left 1s ease;
	-moz-transition: top 1s ease, left 1s ease;
	-o-transition: top 1s ease, left 1s ease;
	-ms-transition: top 1s ease, left 1s ease;
}

.grid img {
	max-width: 100%;
}
</style>
</head>
<body>
	<div class="wrapper container-fluid">
		<div class="content"></div>
	</div>
	<script type="text/javascript">
		$(function() {
			loadMore();

			$(".grid img").lazyload({
				load : function() {
					$(".content").BlocksIt({
						numOfCol : 5,
						offsetX : 8,
						offsetY : 8,
						blockElement : '.grid'
					});
				},
				effect : "fadeIn",
				placeholder : "${ctx}/static/images/load.gif"
			});

			$(window).scroll(
					function() {
						// 当滚动到最底部以上50像素时， 加载新内容
						if ($(document).height() - $(this).scrollTop()
								- $(this).height() < 50) {
							loadMore();
							$('.content').BlocksIt({
								numOfCol : 5,
								offsetX : 8,
								offsetY : 8,
								blockElement:'.grid'
							});
							$(".grid img").lazyload();
						}
					});

			//window resize
			var currentWidth = 1400;
			$(window).resize(function() {
				var winWidth = $(window).width();
				var conWidth;
				if (winWidth < 660) {
					conWidth = 540;
					col = 2
				} else if (winWidth < 880) {
					conWidth = 760;
					col = 3
				} else if (winWidth < 1400) {
					conWidth = 980;
					col = 4;
				} else {
					conWidth = 1400;
					col = 5;
				}

				if (conWidth != currentWidth) {
					currentWidth = conWidth;
					$('.content').width(conWidth);
					$('.content').BlocksIt({
						numOfCol : col,
						offsetX : 8,
						offsetY : 8
					});
				}
				;
			});

		});

		function loadMore() {
			$.ajax({
				url : '${ctx}/image.html?getImage',
				async : false,
				type : 'get',
				success : function(data) {
					var json = eval("(" + data + ")");

					if (json.length == 0) {
						return;
					}

					for ( var i = 0; i < json.length; i++) {
						$(".content").append(
							"<div class='grid'><a href='javascript:;' data-key='"+json[i].image_name+"'><img data-original='http://cnlyml-small.oss-cn-hangzhou.aliyuncs.com/"+json[i].image_name+"' class='img-rounded'></a></div>");
					}
				}
			});
		}
	</script>
</body>
</html>