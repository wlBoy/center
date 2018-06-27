<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>500错误页面</title>
<style type="text/css">
	div{
		width:500px;
		height:306px;
		margin:100px auto;
		cursor:pointer;
		position:relative;
		background: url("${pageContext.request.contextPath}/images/500.jpg") no-repeat;
	}
	.btn{
		position:absolute;
		display: block;
		width:157px;
		height:36px;
		top:177px;
		left:37px;
		border-radius:5px;
	}
</style>
</head>
<body>
	<div>
		<a href="javascript:;" class="btn"></a>
	</div>
</body>
</html>