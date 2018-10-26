<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有用户的资产</title>
<style type="text/css">
.con {
	width: 80%;
	margin: 10px auto;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 550px;
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
</head>
<body>
	<div class="tip">
		温馨提示:本页面只支持查询操作!
	</div>
	<div class="contact">
		<!-- 表单区,根据查询条件筛选列表数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/account/money/showAllMoney.do" 
			method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
				<fieldset>
            <div class="row">
              <div class="col-md-5">
              	<div class="form-group">
		            <label class="control-label col-md-5">用户</label>
		            <div class="col-md-7">
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
              <div class="col-md-5">
              	<div class="form-group">
		            <label class="control-label col-md-5">创建日期</label>
		            <div class="col-md-7">
		               <input onClick="WdatePicker({dateFmt:'yyyyMMdd'})" readonly="readonly"
					    	type="text"  class="form-control" name="curday" value="${pages.bean.curday}"/>
		            </div>
            	</div>
              </div>
              <div class="col-md-2">
              		<input type="submit" value="查询" class="btn btn_5 btn-lg btn-primary" id="query">
              </div>
            </div>
          </fieldset>
		</form>
	</div>
	<div style="clear: both"></div>
	<!-- 表格区,列表内容显示区 -->
	<div class="con">
		<div class="bs-example4" data-example-id="contextual-table">
			<span class="title">总资产列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th>序号</th>
						<th>用户名</th>
						<th>总收入(元)</th>
						<th>总支出(元)</th>
						<th>总资产(元)</th>
						<th>创建月份</th>
						<th>创建日期</th>
						<th>创建时间</th>
						<th>更新时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="m" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td>${i.count}</td>
							<td>${m.userName}</td>
							<td><fmt:formatNumber value="${m.inFee}" pattern="0.000"/></td>
							<td><fmt:formatNumber value="${m.outFee}" pattern="0.000"/></td>
							<td><fmt:formatNumber value="${m.totalFee}" pattern="0.000"/></td>
							<td>${m.curmonth}</td>
							<td>${m.curday}</td>
							<td>${m.createTime}</td>
							<td>${m.updateTime}</td>
						</tr>
						<!--计算总收入，总支出和总资产 -->
						<c:set var="comeInTotal" scope="page" value="${comeInTotal+m.inFee}"/>
						<c:set var="comeOutTotal" scope="page" value="${comeOutTotal+m.outFee}"/>
						<c:set var="totalFee" scope="page" value="${totalFee+m.totalFee}"/>
					</c:forEach>
					<!--显示总收入，总支出和总资产 -->
					<tr>
						<td colspan="2">总计</td>
						<td colspan="1"><span class="label label-info"><fmt:formatNumber value="${comeInTotal}" pattern="0.000"/></span></td>
						<td colspan="1"><span class="label label-danger"><fmt:formatNumber value="${comeOutTotal}" pattern="0.000"/></span></td>
						<td colspan="1"><span class="label label-info"><fmt:formatNumber value="${totalFee}" pattern="0.000"/></span></td>
					</tr>
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