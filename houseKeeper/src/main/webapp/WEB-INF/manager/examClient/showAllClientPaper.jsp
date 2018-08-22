<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有的前台试卷</title>
<style type="text/css">
.con {
	width: 100%;
	margin: 10px auto;
}

li {
	list-style: none;
}

.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor: pointer;
}

.contact {
	width: 1200px;
	height: 80px;
	margin: 10px auto;
}

.table, .table th {
	text-align: center;
}

.modal-content {
	top: 80px;
	right: 24px;
}

.btn-xs {
	position: relative;
	top: 5px;
}

#query {
	position: relative;
	top: -5px;
	left: 30px;
}

.title {
	float: right;
	font-size: 16px;
}

.tip {
	width: 100% px;
	height: 30px;
	text-align: center;
	line-height: 30px;
	color: red;
	font-size: 14px;
}
</style>
<script type="text/javascript">
//获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
function getNowFormatDate() {
    var date = new Date();
    var year = date.getFullYear();//年
    var month = date.getMonth() + 1;//月
    var strDate = date.getDate();//日
    var hours = date.getHours();//时
    var minutes = date.getMinutes();//分
    var seconds = date.getSeconds();//秒
    return year + "-" + p(month) + "-"
        + p(strDate) + " "+ p(hours) + ":"
        + p(minutes) + ":" + p(seconds);
}

/*小于10的补0*/
function p(s) {
    return s < 10 ? '0' + s : s;
}
/*进入考试*/
function goExam(paperId){
	// 回显数据
	$.get("${ctx}/exam/rest/findByPaperId.do","paperId="+paperId,function(p){
		var startTime = p.startTime;
		var endTime = p.endTime;
		var nowDate = getNowFormatDate();
		if(nowDate<startTime){
			swal("OMG!", "对不起,该试卷还未开考!", "error");
		}else if(nowDate>endTime){
			swal("OMG!", "对不起,该考试已结束!", "error");
		}else{
			location = "${ctx}/exam/exam/onlineExam.do?paperId="+p.paperId;
		}
	},"json"); 
}
</script>
</head>
<body>
	<div class="contact">
		<!-- 表单区,根据查询条件筛选列表数据 -->
		<form id="pageFm" class="form-horizontal"
			action="${ctx}/exam/exam/showAllClientPaper.do" method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
			<fieldset>
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<label class="control-label col-md-3">试卷名称</label>
							<div class="col-md-8">
								<input class="form-control" name="paperName"
									value="${pages.bean.paperName}" type="text">
							</div>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label col-md-4">出卷人</label>
							<div class="col-md-7">
								<select name="createPaperId" class="form-control">
									<option value="" selected="selected">-请选择-</option>
									<c:forEach var="u" items="${users}">
										<c:if test="${pages.bean.createPaperId==u.userId}">
											<option value="${u.userId}" selected="selected">${u.userName}</option>
										</c:if>
										<c:if test="${pages.bean.createPaperId!=u.userId}">
											<option value="${u.userId}">${u.userName}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label class="control-label col-md-5">试卷总分</label>
							<div class="col-md-7">
								<select name="paperScore" class="form-control">
									<option value="" selected="selected">-请选择-</option>
									<c:if test="${pages.bean.paperScore == 100}">
										<option value="100" selected="selected">100</option>
									</c:if>
									<c:if test="${pages.bean.paperScore != 100}">
										<option value="100">100</option>
									</c:if>
									<c:if test="${pages.bean.paperScore == 120}">
										<option value="120" selected="selected">120</option>
									</c:if>
									<c:if test="${pages.bean.paperScore != 120}">
										<option value="120">120</option>
									</c:if>
									<c:if test="${pages.bean.paperScore == 150}">
										<option value="150" selected="selected">150</option>
									</c:if>
									<c:if test="${pages.bean.paperScore != 150}">
										<option value="150">150</option>
									</c:if>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label class="control-label col-md-5">创建日期</label>
							<div class="col-md-7">
								<input onClick="WdatePicker({dateFmt:'yyyyMMdd'})"
									readonly="readonly" type="text" class="form-control"
									name="curday" value="${pages.bean.curday}" />
							</div>
						</div>
					</div>
					<div class="col-md-2">
						<input type="submit" value="查询"
							class="btn btn_5 btn-lg btn-primary" id="query">
					</div>
				</div>
			</fieldset>
		</form>
	</div>
	<div style="clear: both"></div>
	<!-- 表格区,列表内容显示区 -->
	<div class="con">
		<div class="bs-example4" data-example-id="contextual-table">
			<span class="title">所有试卷列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th>序号</th>
						<th>试卷名称</th>
						<th>出卷人</th>
						<th>试卷总分</th>
						<th>试卷总时间(分)</th>
						<th>开考时间</th>
						<th>结束时间</th>
						<th>创建日期</th>
						<th>创建时间</th>
						<th>备注信息</th>
						<th>试卷状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td>${i.count}</td>
							<td>${p.paperName}</td>
							<td>${p.createPaperName}</td>
							<td>${p.paperScore}</td>
							<td>${p.totalTime}</td>
							<td>${p.startTime}</td>
							<td>${p.endTime}</td>
							<td>${p.curday}</td>
							<td>${p.createTime}</td>
							<td>${p.remark}</td>
							<td><c:if test="${p.paperStatus==null}">
									<label class="label label-info">未考</label>
								</c:if> <c:if test="${p.paperStatus==1}">
									<label class="label label-success">已考</label>
								</c:if> <c:if test="${p.paperStatus==2}">
									<label class="label label-danger">已批阅</label>
								</c:if></td>
							<td><c:if test="${p.paperStatus==0 || p.paperStatus==null}">
									<a href="javascript:;" class="btn btn_5 btn-xs btn-info"
										onclick="goExam(${p.paperId});">进入考试</a>
								</c:if> <c:if test="${p.paperStatus==1 || p.paperStatus==2}">
									<a href="javascript:;" disabled="disabled"
										class="btn btn_5 btn-xs btn-info"
										onclick="goExam(${p.paperId});">进入考试</a>
								</c:if></td>
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