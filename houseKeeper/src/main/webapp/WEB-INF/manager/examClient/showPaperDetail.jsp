<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示该用户试卷的详情</title>
<style type="text/css">
* {
	padding: 0px;
	margin: 0px;
}
.con {
	width: 75%;
	margin: 0px auto;
	padding-top: 20px;
	background-color: #f7f8f9;
	position: relative;
}
.submit {
	position: absolute;
	bottom: 10px;
	right: 0px;
}
.title{
	font-size:16px;
	font-family:"微软雅黑";
	font-weight:700;
	margin-top:10px;
}
.top h3{
	margin-top:30px;
	margin-bottom:5px;
}
label{
	position: relative;
	bottom:2px;
}
.topTab{
	text-align:center;
	margin:10px auto;
	width:350px;
	height:30px;
}
.topTab span{
	color:red;
	font-size:18px;
}
.answer{
	float:right;
	margin-right:5px;
}
</style>
</head>
<body>
	<body>
		<!-- 试卷内容区 -->
		<div class="top">
			<h3 align="center">${score.paper.paperName} (总分: ${score.paper.paperScore}分)</h3>
			<h5 align=center>出卷人: ${score.paper.createPaperName} , 总时间: ${score.paper.totalTime}分钟</h5>
			<h5 align="center">交卷时间: ${score.submitTime}</h5>
			<table class="topTab" border="1">
				<tr>
					<td></td>
					<td>单选题</td>
					<td>多选题</td>
					<td>简答题</td>
					<td>总分</td>
				</tr>
				<tr>
					<td>得分</td>
					<td><span>${score.singleScore}</span></td>
					<td><span>${score.multipleScore}</span></td>
					<td><span>${score.briefScore}</span></td>
					<td><span>${score.singleScore+score.multipleScore+score.briefScore}</span></td>
				</tr>
				<c:if test="${score.paperStatus==1}">
					<tr>
						<td colspan="5"><span style="color:#337ab7;">您的主观题待批阅,请耐心等待....</span></td>
					</tr>
				</c:if>
			</table>
		</div>
		<!-- 试卷内容区 -->
		<div class="con">
		<table width="100%">
			<c:forEach var="q" items="${qlist}" varStatus="i">
				<c:if test="${q.type.typeId == 1}">
				<!--单选题  -->
					<c:forEach var="us" items="${ulist}" varStatus="j">
						<c:if test="${i.count==j.count}">
							<tr class="title">
								<td>${i.count}.</td>
								<td colspan="2">(${q.type.typeName}) ${q.questionTitle}
									(${us})	(${q.questionScore}分) <span class="answer">
									<c:if test="${us==q.answer}">
										<i class="icon-ok" style="color:#58c0de"> 正确答案:${q.answer}</i>
									</c:if>
									<c:if test="${us!=q.answer}">
										<i class="icon-remove" style="color:red"> 正确答案:${q.answer}</i>
									</c:if>
									</span><input type="hidden" value="${q.questionId}"
									name="qid"></td>
							</tr>
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
					</c:forEach>
				</c:if>
				<c:if test="${q.type.typeId ==2}">
				<!--多选题  -->
					<c:forEach var="us" items="${ulist}" varStatus="j">
						<c:if test="${i.count==j.count}">
							<tr class="title">
								<td>${i.count}.</td>
								<td colspan="2">(${q.type.typeName}) ${q.questionTitle}
									(${us})	(${q.questionScore}分) <span class="answer">
									<c:if test="${us==q.answer}">
										<i class="icon-ok" style="color:#58c0de"> 正确答案:${q.answer}</i>
									</c:if>
									<c:if test="${us!=q.answer}">
										<i class="icon-remove" style="color:red"> 正确答案:${q.answer}</i>
									</c:if>
									</span><input type="hidden" value="${q.questionId}"
									name="qid"></td>
							</tr>
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
					</c:forEach>
				</c:if>	
				<c:if test="${q.type.typeId ==3}">
				<!--简答题  -->
					<c:forEach var="us" items="${ulist}" varStatus="j">
						<c:if test="${i.count==j.count}">
							<tr class="title">
								<td>${i.count}.</td>
								<td colspan="2">(${q.type.typeName}) ${q.questionTitle}
									(${q.questionScore}分) <input type="hidden" value="${q.questionId}"
									name="qid"></td>
							</tr>
							<tr>
								<td></td>
								<td>
									<textarea rows="5" cols="80" name="answer${i.count}">${us}</textarea>
								</td>
							</tr>
						</c:if>	
					</c:forEach>
				</c:if>
			</c:forEach>
			<tr>
				<td colspan="2" align="center"><a href="${ctx}/exam/score/showPersonalScore.do" class="btn btn_5 btn-lg btn-primary submit">查看其他</a></td>
			</tr>
		</table>
	</div>
</body>
</body>
</html>