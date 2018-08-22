<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>在线考试</title>
<style type="text/css">
.time {
	width: 180px;
	height: 40px;
	color:red;
	font-size: 18px;
	position: absolute;
	top: 0px;
	right: 0px;
}
.con {
	width: 75%;
	margin: 1px auto;
	padding-top:10px;
	position:relative;
	background-color: #fff;
}
.submit {
	position: absolute;
	bottom: 10px;
	right: 50px;
}
.title{
	font-size:16px;
	font-family:"微软雅黑";
	font-weight:700;
	margin-top:10px;
	margin-bottom:10px;
}
h4{
	margin-bottom:5px;
}
h5{
	margin-bottom:30px;
}
</style>
<script type="text/javascript">
	$(function(){
		/* 初始化时间 */
		var time = ${p.totalTime};
		var minute = time%60;
		var hour = parseInt(time/60);
		$('.hour').val(hour);
		$('.minute').val(minute);
		Countdown.init();
		$('.submit').click(function(){
		swal({
			title: "提交试卷", 
			text: "您确定要交卷吗?", 
			type: "warning",
			showCancelButton: true,
			closeOnConfirm: false,
			confirmButtonText: "确定",
			confirmButtonColor: "#ec6c62"
			}, function() {
				$('#fm').submit();
			});
		});
	})
	
</script>
</head>
<body>
	<div class="con">
		<!--配置倒计时 -->
		<div class="setting">
			<input type="hidden" class="input hour" maxlength="2" value="" />
			<input type="hidden" class="input minute" maxlength="2" value="" />
			<input type="hidden" class="input second" maxlength="2" value="00" />
		</div>
		<!--显示倒计时 -->
		<div class="time">
			<ul>
				<li class="num"><span class="old">0</span></li>
				<li class="num"><span class="old">0</span></li>
				<li><span>:</span></li>
				<li class="num"><span class="old">0</span></li>
				<li class="num"><span class="old">0</span></li>
				<li><span>:</span></li>
				<li class="num"><span class="old">0</span></li>
				<li class="num"><span class="old">0</span></li>
			</ul>
		</div>
		<!-- 题目显示区 -->
		<h4 align=center>${paper.paperName} (总分: ${paper.paperScore}分)</h4>
		<h5 align=center>出卷人: ${paper.createPaperName} , 总时间: ${paper.totalTime}分钟</h5>
		<form action="${ctx}/exam/exam/submitPaper.do" method="post" id="fm">
			<!-- 试卷ID -->
			<input type="hidden" name="paperId" value="${p.paperId}"/>
			<table>
				<c:forEach var="q" items="${qlist}" varStatus="i">
					<tr class="title">
						<td>${i.count}.</td>
						<td colspan="2">(${q.type.typeName}) ${q.questionTitle}
								(${q.questionScore}分) <input type="hidden" value="${q.questionId}"
							name="qid"></td>
					</tr>
					<c:if test="${q.type.typeId == 1}">
						<!--单选题  -->
						<tr>
							<td></td>
							<td><input type="radio" id="radio${i.count}A" name="answer${i.count}" value="A"> <label for="radio${i.count}A">${q.optionA}</label></td>
							<td><input type="radio" id="radio${i.count}B" name="answer${i.count}" value="B"> <label for="radio${i.count}B">${q.optionB}</label></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="radio" id="radio${i.count}C" name="answer${i.count}" value="C"> <label for="radio${i.count}C">${q.optionC}</label></td>
							<td><input type="radio" id="radio${i.count}D" name="answer${i.count}" value="D"> <label for="radio${i.count}D">${q.optionD}</label></td>
						</tr>
					</c:if>
					<c:if test="${q.type.typeId ==2}">
						<!--多选题  -->
						<tr>
							<td></td>
							<td><input type="checkbox" id="checkbox${i.count}A" name="answer${i.count}" value="A"> <label for="checkbox${i.count}A">${q.optionA}</label></td>
							<td><input type="checkbox" id="checkbox${i.count}B" name="answer${i.count}" value="B"> <label for="checkbox${i.count}B">${q.optionB}</label></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="checkbox" id="checkbox${i.count}C" name="answer${i.count}" value="C"> <label for="checkbox${i.count}C">${q.optionC}</label></td>
							<td><input type="checkbox" id="checkbox${i.count}D" name="answer${i.count}" value="D"> <label for="checkbox${i.count}D">${q.optionD}</label></td>
						</tr>
					</c:if>
					<c:if test="${q.type.typeId ==3}">
						<!--简答题  -->
						<tr>
							<td></td>
							<td><textarea class="form-control" rows="5" cols="80" name="answer${i.count}"></textarea></td>
						</tr>
					</c:if>
				</c:forEach>
				<tr>
					<td colspan="2" align="center"><a href="javascript:;" class="btn btn_5 btn-lg btn-primary submit">交卷</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>