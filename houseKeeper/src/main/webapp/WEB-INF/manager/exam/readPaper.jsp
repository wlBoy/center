<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>批阅试卷</title>
<style type="text/css">
.left {
	width: 35%;
	height: 100%;
	float: left;
	margin-left:4%;
}

.mid {
	width: 35%;
	height: 100%;
	float: left;
	margin-left:4%;
}

.right {
	margin-top:56px;
	width: 14%;
	height: 100%;
	float: left;
	margin-left:4%;
}

h4{
	color:#336699;
	font-weight: 700;
}
p{
	margin:5px;
}
</style>
<script type="text/javascript">
	$(function(){
		//数字直接赋值即可，字符串必须加''
		var score = ${score};
		$('.scoreInput').blur(function() {
			if ($(this).val() < 0 || $(this).val() > score) {
				swal("OMG!", "数字必须在0~" + score + "之间!", "error");
			}
		})
	});
	// 评分
	function submitForm(){
	swal({
		title: "主观题评分", 
		text: "你确定评分?", 
		type: "warning",
		showCancelButton: true,
		closeOnConfirm: false,
		confirmButtonText: "确定",
		confirmButtonColor: "#ec6c62"
		}, function() {
			$('#fm').submit();
		});
	}
</script>
</head>
<body>
	<div class="left">
		<h4>用户主观题答案</h4>
		<c:forEach var="q" items="${qList}" varStatus="i">
			<p>
				<strong>题目${i.count}: ${q.questionTitle}(${score}分)</strong>
			</p>
			<textarea class="form-control" rows="8" cols="60">${q.userSolution}</textarea>
		</c:forEach>
	</div>
	<div class="mid">
		<h4>主观题正确答案</h4>
		<c:forEach var="q" items="${qList}" varStatus="i">
			<p>
				<strong>题目${i.count}: ${q.questionTitle}(${score}分)</strong>
			</p>
			<textarea class="form-control" rows="8" cols="60">${q.answer}</textarea>
		</c:forEach>
	</div>
	<!--评分区-->
	<div class="right">
		<form action="${ctx}/exam/score/updateScore.do" class="form-horizontal" method="post" id="fm">
			<input type="hidden" name="paperId" value="${paperId}">
			<input type="hidden" name="userId" value="${userId}">
			<c:forEach var="q" items="${qList}" varStatus="i">
	            <div class="form-group">
	                <label class="layui-form-label">第${i.count}道题:</label>
	                <div class="layui-input-inline">
	                    <input type="number" class="form-control scoreInput" name="scores">
	                </div>
	            </div>
			</c:forEach>
			<div class="form-group">
	            <input type="button" onclick="submitForm();" class="btn btn_5 btn-lg btn-primary" value="提交评分">
	        </div>
		</form>
	</div>
</body>
</html>