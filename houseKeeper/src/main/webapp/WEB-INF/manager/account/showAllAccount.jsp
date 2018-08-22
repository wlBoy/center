<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有用户的总账务</title>
<style type="text/css">
.con {
	width: 100%;
	margin: 50px auto;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 1366px;
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
</head>
<body>
	<div class="tip">
		温馨提示:本页面只支持查询操作!
	</div>
	<div class="contact">
		<!-- 表单区,根据查询条件筛选列表数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/account/account/showAllAccount.do" 
			method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
			<fieldset>
            <div class="row">
             <div class="col-md-2">
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
              <div class="col-md-2">
              	<div class="form-group">
		            <label class="control-label col-md-4">父类别</label>
		            <div class="col-md-7">
		           		<select name="type.parentType" id="selectParentType" class="form-control" >
		           			<option value="" selected="selected">--请选择--</option>
		           			<c:if test="${pages.bean.type.parentType eq '收入'}">
		           				<option value="收入" selected="selected">收入</option>
		           			</c:if>
		           			<c:if test="${pages.bean.type.parentType ne '收入'}">
		           				<option value="收入">收入</option>
		           			</c:if>
		           			<c:if test="${pages.bean.type.parentType eq '支出'}">
		           				<option value="支出" selected="selected">支出</option>
		           			</c:if>
		           			<c:if test="${pages.bean.type.parentType ne '支出'}">
		           				<option value="支出">支出</option>
		           			</c:if>
						</select>
		            </div>
          		</div>
              </div>
              <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">账务标题</label>
			            <div class="col-md-7">
			              <input class="form-control" name="accountTitle" value="${pages.bean.accountTitle}" type="text">
			            </div>
	            	</div>
              </div>
              <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">账务方式</label>
			            <div class="col-md-7">
			              <input class="form-control" name="accountType" value="${pages.bean.accountType}" type="text">
			            </div>
	            	</div>
              </div>
              <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">创建月份</label>
			            <div class="col-md-7">
			             <input onClick="WdatePicker({dateFmt:'yyyyMM'})" readonly="readonly"
					    	type="text"  class="form-control" name="curmonth" value="${pages.bean.curmonth}"/>
			            </div>
	            	</div>
              </div>
              <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">创建日期</label>
			            <div class="col-md-7">
			              <input onClick="WdatePicker({dateFmt:'yyyyMMdd'})" readonly="readonly"
					    	type="text"  class="form-control" name="curday" value="${pages.bean.curday}"/>
			            </div>
	            	</div>
              </div>
            </div>
          </fieldset>
          <input type="submit" value="查询" class="btn btn_5 btn-lg btn-primary" id="query">
		</form>
	</div>
	<div style="clear: both"></div>
	<!-- 表格区,列表内容显示区 -->
	<div class="con">
		<div class="bs-example4" data-example-id="contextual-table">
			<span class="title">总账务列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th>序号</th>
						<th>用户名</th>
						<th>账务标题</th>
						<th>账务类别名</th>
						<th>父级类别</th>
						<th>账务金额(元)</th>
						<th>账务方式</th>
						<th>创建月份</th>
						<th>创建日期</th>
						<th>创建时间</th>
						<th>最后修改时间</th>
						<th>账务描述</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="a" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td>${i.count}</td>
							<td>${a.userName}</td>
							<td>${a.accountTitle}</td>
							<td>${a.type.typeName}</td>
							<td>${a.type.parentType}</td>
							<td>${a.accountFee}</td>
							<td>${a.accountType}</td>
							<td>${a.curmonth}</td>
							<td>${a.curday}</td>
							<td>${a.createTime}</td>
							<td>${a.updateTime}</td>
							<td>${a.remark}</td>
						</tr>
						<c:if test="${a.type.parentType eq '收入'}">
			             	<c:set var="comeIn" scope="page" value="${comeIn+a.accountFee}"/>
				 		</c:if>
				 		<c:if test="${a.type.parentType eq '支出'}">
			             	<c:set var="comeOut" scope="page" value="${comeOut+a.accountFee}"/>
				 		</c:if>
					</c:forEach>
					<!--显示个人总收入，总支出和总资产 -->
					<tr>
						<td colspan="4">总计</td>
						<td colspan="3">总收入<span class="label label-info"><fmt:formatNumber value="${comeIn}" pattern="0.000"/></span></td>
						<td colspan="3">总支出<span class="label label-danger"><fmt:formatNumber value="${comeOut}" pattern="0.000"/></span></td>
						<td colspan="3">
							<c:if test="${comeIn-comeOut > 0}">
								总资产<span class="label label-info"><fmt:formatNumber value="${comeIn-comeOut}" pattern="0.000"/></span>
							</c:if>
							<c:if test="${comeIn-comeOut <= 0}">
								总资产<span class="label label-danger"><fmt:formatNumber value="${comeIn-comeOut}" pattern="0.000"/></span>
							</c:if>
						</td>
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