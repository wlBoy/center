<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>分页显示所有的日志</title>
<style type="text/css">
.con {
	width: 95%;
	margin: 50px auto;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 1050px;
	height: 80px;
	margin:10px auto;
}
.table , .table th{
	text-align: center;
}
.modal-content{
	top:100px;
	right:24px;
}
#query{
	position: relative;
	top:-5px;
	left:30px;
}
.title{
	float:right;
	position:relative;
	top:20px;
	font-size:16px;
}
</style>
<script type="text/javascript">
/*复选框联动*/
function check(isChecked) {
	$('.box').each(function(i, o) {
		$(o).prop("checked", isChecked);
	});
}
</script>
</head>
<body>
	<div class="contact">
		<!-- 表单区,用来处理用户的查询提交数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/system/log/showAllLog.do" method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
			<fieldset>
            <div class="row">
             <div class="col-md-3">
              	<div class="form-group">
		            <label class="control-label col-md-5">请求类</label>
		            <div class="col-md-7">
		              <input class="form-control" name="requestClass" value="${pages.bean.requestClass}" type="text">
		            </div>
            	</div>
              </div>
             <div class="col-md-3">
              	<div class="form-group">
		            <label class="control-label col-md-5">请求方法</label>
		            <div class="col-md-7">
		              <input class="form-control" name="requestMethod" value="${pages.bean.requestMethod}" type="text">
		            </div>
            	</div>
              </div>
             <div class="col-md-2">
              	<div class="form-group">
		            <label class="control-label col-md-4">用户</label>
		            <div class="col-md-8">
		           		<select name="userId" class="form-control">
							<option value="" selected="selected">--请选择--</option>
							<c:forEach var="u" items="${users}">
		           				<c:if test="${pages.bean.userId==u.userId}">
			           				<option value="${u.userId}" selected="selected">${u.userName}</option>
			           			</c:if>
			           			<c:if test="${pages.bean.userId!=u.userId}">
			           				<option value="${u.userId}">${u.userName}</option>
			           			</c:if>
		           			</c:forEach>
						</select>
		            </div>
          	    </div>
	          </div>	    
              <div class="col-md-3">
              	<div class="form-group">
		            <label class="control-label col-md-5">创建日期</label>
		            <div class="col-md-7">
		              <input onClick="WdatePicker({dateFmt:'yyyyMMdd'})" readonly="readonly"
				    	type="text"  class="form-control" name="curday" value="${pages.bean.curday}"/>
		            </div>
            	</div>
              </div>
              <div class="col-md-1">
               	<input type="submit" value="查询" class="btn btn_5 btn-lg btn-primary" id="query">
              </div>
            </div>
          </fieldset>
		</form>
	</div>
	<div style="clear: both"></div>
	<!-- 主题内容区 -->
	<div class="con">
		<div class="bs-example4" data-example-id="contextual-table">
			<span class="title">所有日志列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th><input type="checkbox" onclick="check(this.checked)"></th>
						<th>序号</th>
						<th>用户名</th>
						<th>IP地址</th>
						<th>请求类名</th>
						<th>请求方法名</th>
						<th>创建日期</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="l" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td><input type="checkbox" class="box" value="${l.logId}"></td>
							<td>${i.count}</td>
							<td>${l.userName}</td>
							<td>${l.requestIp}</td>
							<td>${l.requestClass}</td>
							<td>${l.requestMethod}</td>
							<td>${l.curday}</td>
							<td>${l.createTime}</td>
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