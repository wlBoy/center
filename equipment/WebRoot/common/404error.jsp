<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>404错误页面</title>
<link href="${pageContext.request.contextPath}/css/pintuer.css" rel="stylesheet"/>
</head>
<body>
	<div class="container" style="margin-top: 8%;">
		<div class="panel margin-big-top">
			<div class="text-center">
				<br>
				<h2 class="padding-top">
					<strong>404错误！抱歉您要找的页面不存在</strong>
				</h2>
				<div>
					<div class="float-left">
						<img src="http://www.pintuer.com/images/ds-1.gif">
						<div class="alert">卧槽！页面不见了！</div>
					</div>
					<div class="float-right">
						<img src="http://www.pintuer.com/images/ds-2.png" width="260">
					</div>
				</div>
				<div class="padding-big">
					<a href="${pageContext.request.contextPath}/manager/index.jsp" class="button bg-yellow">返回首页</a> 
				</div>
			</div>
		</div>
	</div>
</body>
</html>