<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>显示所有的后台批阅试卷</title>
		<style type="text/css">
.con {
	width: 100%;
	margin: 10px auto;
}
li{
	list-style: none;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 1300px;
	height: 80px;
	margin:10px auto;
}
.table , .table th{
	text-align: center;
}
.state1{
	color:#58c0de;
}
.state2{
	color:#d2322d;
}
.modal-content{
	top:80px;
	right:24px;
}
.btn-xs{
	position: relative;
	top:5px;
}
#query{
	display:block;
	margin:1px auto;
}
.title{
	float:right;
	font-size:16px;
}
.tip{
	width: 100%px;
	height: 30px;
	text-align:center;
	line-height:30px;
	color:red;
	font-size:14px;
}
</style>
		<script type="text/javascript">
</script>
	</head>
	<body>
		<div class="tip">
			温馨提示:
		</div>
		<div class="contact">
			<!-- 表单区,根据查询条件筛选列表数据 -->
			<form id="pageFm" class="form-horizontal" action="${ctx}/exam/score/showAllScore.do"
				method="post">
				<input id="pageNum" type="hidden" name="pageNum">
				<input value="${pages.size}" id="size" type="hidden" name="size">
				<fieldset>
	             <div class="row">
	             	<div class="col-md-2">
		              	<div class="form-group">
				            <label class="control-label col-md-4">出卷人</label>
				            <div class="col-md-7">
				              	<select name="paper.createPaperId" class="form-control">
									<option value="" selected="selected">-请选择-</option>
									<c:forEach var="u" items="${users}">
				           				<c:if test="${pages.bean.paper.createPaperId==u.userId}">
					           				<option value="${u.userId}" selected="selected">${u.userName}</option>
					           			</c:if>
					           			<c:if test="${pages.bean.paper.createPaperId!=u.userId}">
					           				<option value="${u.userId}">${u.userName}</option>
					           			</c:if>
				           			</c:forEach>
								</select>
				            </div>
		            	</div>
	               </div>
	               <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">试卷名称</label>
			            <div class="col-md-7">
			              <input class="form-control" name="paper.paperName" value="${pages.bean.paper.paperName}" type="text">
			            </div>
	            	</div>
	               </div>
                   <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">试卷总分</label>
			            <div class="col-md-7">
			           		<select name="paper.paperScore" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:if test="${pages.bean.paper.paperScore == 100}">
									<option value="100" selected="selected">100</option>
								</c:if>
								<c:if test="${pages.bean.paper.paperScore != 100}">
									<option value="100">100</option>
								</c:if>
								<c:if test="${pages.bean.paper.paperScore == 120}">
									<option value="120" selected="selected">120</option>
								</c:if>
								<c:if test="${pages.bean.paper.paperScore != 120}">
									<option value="120">120</option>
								</c:if>
								<c:if test="${pages.bean.paper.paperScore == 150}">
									<option value="150" selected="selected">150</option>
								</c:if>
								<c:if test="${pages.bean.paper.paperScore != 150}">
									<option value="150">150</option>
								</c:if>
							</select>
			            </div>
	          		  </div>
          		  </div>
          		  <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-4">考卷人</label>
			            <div class="col-md-7">
			              	<select name="examPaperId" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:forEach var="u" items="${users}">
			           				<c:if test="${pages.bean.examPaperId==u.userId}">
				           				<option value="${u.userId}" selected="selected">${u.userName}</option>
				           			</c:if>
				           			<c:if test="${pages.bean.examPaperId!=u.userId}">
				           				<option value="${u.userId}">${u.userName}</option>
				           			</c:if>
			           			</c:forEach>
							</select>
			            </div>
	            	</div>
	               </div>
          		  <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">试卷状态</label>
			            <div class="col-md-7">
			              	<select name="paperStatus" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:if test="${pages.bean.paperStatus == 1}">
									<option value="1" selected="selected">未批阅</option>
								</c:if>
								<c:if test="${pages.bean.paperStatus != 1}">
									<option value="1">未批阅</option>
								</c:if>
								<c:if test="${pages.bean.paperStatus == 2}">
									<option value="2" selected="selected">已批阅</option>
								</c:if>
								<c:if test="${pages.bean.paperStatus != 2}">
									<option value="2">已批阅</option>
								</c:if>
							</select>
			            </div>
	            	</div>
	               </div>
	               <div class="col-md-2">
		              	<div class="form-group">
				            <label class="control-label col-md-5">交卷日期</label>
				            <div class="col-md-7">
				              <input onClick="WdatePicker({dateFmt:'yyyyMMdd'})" readonly="readonly"
						    	type="text"  class="form-control" name="curday" value="${pages.bean.curday}"/>
				            </div>
		            	</div>
	              </div>
	              <input type="submit" value="查询" class="btn btn_5 btn-lg btn-primary" id="query">
	          </div>
         	 </fieldset>
			</form>
		</div>
		<div style="clear: both"></div>
		<!-- 表格区,列表内容显示区 -->
		<div class="con">
			<div class="bs-example4" data-example-id="contextual-table">
				<span class="title">所有未批阅试卷列表</span>
				<table class="table">
					<thead>
						<tr bgcolor="#D9edf7">
							<th>序号</th>
							<th>出卷人</th>
							<th>试卷名称</th>
							<th>试卷总分</th>
							<th>总时间(分钟)</th>
							<th>考卷人</th>
							<th>单选题分数</th>
							<th>多选题分数</th>
							<th>简答题分数</th>
							<th>交卷日期</th>
							<th>交卷时间</th>
							<th>建卷时间</th>
							<th>备注信息</th>
							<th>试卷状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="s" items="${pages.list}" varStatus="i">
							<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
								<td>${i.count}</td>
								<td>${s.paper.createPaperName}</td>
								<td>${s.paper.paperName}</td>
								<td>${s.paper.paperScore}</td>
								<td>${s.paper.totalTime}</td>
								<td>${s.examPaperName}</td>
								<td>${s.singleScore}</td>
								<td>${s.multipleScore}</td>
								<td>${s.briefScore}</td>
								<td>${s.curday}</td>
								<td>${s.submitTime}</td>
								<td>${s.paper.createTime}</td>
								<td>${s.paper.remark}</td>
								<td>
									<c:if test="${s.paperStatus==1}">
										<label class="label label-success">未批阅</label>
									</c:if>
									<c:if test="${s.paperStatus==2}">
										<label class="label label-danger">已批阅</label>
									</c:if>
								</td>
								<td>
									<a href="${ctx}/exam/paper/readPaper.do?paperId=${s.paper.paperId}&userId=${s.examPaperId}" class="btn btn_5 btn-xs btn-info">立即批阅</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 动态include分页pageTool.jsp的界面-->
			<jsp:include page="/common/pageTool.jsp">
				<jsp:param value="${pages.count }" name="count" />
				<jsp:param value="${pages.pages }" name="pages" />
				<jsp:param value="${pages.pageNum }" name="pageNum" />
				<jsp:param value="${pages.size }" name="size" />
			</jsp:include>
		</div>
	</body>
</html>