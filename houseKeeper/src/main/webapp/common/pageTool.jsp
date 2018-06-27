<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<style type="text/css">
.page {
	diaply: block;
	float: left;
	padding: 0px;
	margin: 0px;
	font-size: .8em;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
}
.page ul {
	float: left;
	margin: 0px;
	padding: 10px;
	list-style: none;
	position: relative;
	bottom: 10px;
}
.page li {
	border: 1px solid silver;
	float: left;
	font-weight: 700;
	margin: 0 2px;
	text-align: center;
	border-radius: 3px;
}

.page li a {
	cursor: pointer;
	line-height: 20px;
	display: block;
	padding: 5px;
	float: left;
	width: 50px;
	text-aling: center;
}
#pageNumInput{
	width: 50px;
	height:30px;
	border:0px;
	font-size:16px;
	border-radius:3px;
	text-indent: 10px;
}
.page li i {
	font-style:normal;
	line-height: 20px;
	display: block;
	padding: 5px;
	float: left;
	width: 50px;
	text-aling: center;
}
.page li i:hover{
	cursor:not-allowed;
}
#pageArea span {
	color: #007aff;
	font-family: "微软雅黑";
	font-size: 16px;
	font-weight: 700;
}
#size {
	width: 55px;
	height: 30px;
	text-indent: 5px;
}
</style>
		<script>
	/*按钮跳转*/
	function goPage(pageNum) {
		document.getElementById("pageNum").value=pageNum;
		document.getElementById("pageFm").submit();
	}
	/*改变大小*/
	function changeSize(size) {
		document.getElementById("size").value=size;
		document.getElementById("pageFm").submit();
	}
	/*输入框跳转*/
	function jump(){
		var pageNum = $("#pageNumInput").val();
		var pages = ${param.pages};
		if(pageNum==0){
			alert("请输入页码！");
		}else{
			if(pageNum>=1&&pageNum<=pages){
				goPage(pageNum);
			}else{
				alert("请输入页码在1-总页数之间！");
			}
		}
	}
</script>
	</head>
	<body>
		<!-- 分页分割线,从jsp动态包含的参数中取各个数值,用param.表示用request.getParameter()方法-->
		<div id="pageArea" style="width: 100%; margin: 15px auto;">
			<div style="float: left;">
				共
				<span>${param.count} </span>条记录&nbsp;&nbsp;&nbsp;&nbsp;当前位置
				<span>${param.pageNum}</span>/
				<span>${param.pages}</span>
			</div>
			<div style="float: right;">
				<div class="page">
					<ul>
						<c:if test="${param.pageNum==1}">
							<li>
								<i>首页</i>
							</li>
							<li>
								<i>上一页</i>
							</li>
						</c:if>
						<c:if test="${param.pageNum!=1}">
							<li>
								<a href="javascript:;" onclick="goPage(1);">首页</a>
							</li>
							<li>
								<a href="javascript:;" onclick="goPage(${param.pageNum - 1 });">上一页</a>
							</li>
						</c:if>
						<c:if test="${param.pageNum==param.pages}">
							<li>
								<i>下一页</i>
							</li>
							<li>
								<i>尾页</i>
							</li>
						</c:if>
						<c:if test="${param.pageNum!=param.pages}">
							<li class="next">
								<a href="javascript:;" onclick="goPage(${param.pageNum + 1 });">下一页</a>
							</li>
							<li class="last">
								<a href="javascript:;" onclick="goPage(${param.pages});">尾页</a>
							</li>
						</c:if>
						<!-- 跳转区域 -->
						<c:if test="${1==param.pages}">
							<li>
								<input type="number" id="pageNumInput" disabled="disabled" value="${param.pageNum}"/>
							</li>
							<li>
								<i>跳转</i>
							</li>
						</c:if>
						<c:if test="${1!=param.pages}">
							<li>
								<input type="number" id="pageNumInput" value="${param.pageNum}"/>
							</li>
							<li>
								<a href="javascript:jump();">跳转</a>
							</li>
						</c:if>
					</ul>
				</div>
				<select onchange="changeSize(this.value);" id="size">
					<option value="5">
						5
					</option>
					<c:if test="${param.size==10}">
						<option selected="selected" value="10">
							10
						</option>
					</c:if>
					<c:if test="${param.size!=10}">
						<option value="10">
							10
						</option>
					</c:if>
					<c:if test="${param.size==20}">
						<option selected="selected" value="20">
							20
						</option>
					</c:if>
					<c:if test="${param.size!=20}">
						<option value="20">
							20
						</option>
					</c:if>
				</select>
			</div>
		</div>
		<!-- 分页结束 -->
	</body>
</html>