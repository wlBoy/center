<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>分页显示所有的管理员操作日志</title>
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
	width: 100%;
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
</style>
<script type="text/javascript">
$(function(){
	//详细信息弹出层
	$(".detail").click(function() {
		var lid = $(this).attr("value");
		var modal = showNormalLay("日志的详细信息如下",$("#detailFormLay"+lid),function(){
			//不执行任何操作
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("600px");
		//隐藏保存按钮
		$('#modal2 #btn_save').css('display','none');
	});
});
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
		<form id="pageFm" class="form-horizontal" action="${ctx}/system/log/showAllAdminLog.do" method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
			<fieldset>
            <div class="row">
             <div class="col-md-2">
              	<div class="form-group">
		            <label class="control-label col-md-6">操作名称</label>
		            <div class="col-md-6">
		              <input class="form-control" name="logName" value="${pages.bean.logName}" type="text">
		            </div>
            	</div>
              </div>
              <div class="col-md-2">
              	<div class="form-group">
		            <label class="control-label col-md-6">日志类型</label>
		            <div class="col-md-6">
		           		<select name="logType" class="form-control">
							<option value="" selected="selected">-请选择-</option>
							<c:forEach var="t" items="${logTypes}">
		           				<c:if test="${pages.bean.logType==t.type}">
			           				<option value="${t.type}" selected="selected">${t.desc}</option>
			           			</c:if>
			           			<c:if test="${pages.bean.logType!=t.type}">
			           				<option value="${t.type}">${t.desc}</option>
			           			</c:if>
		           			</c:forEach>
						</select>
		            </div>
          	    </div>
	          </div>	    
             <div class="col-md-2">
              	<div class="form-group">
		            <label class="control-label col-md-4">用户</label>
		            <div class="col-md-8">
		           		<select name="userId" class="form-control">
							<option value="" selected="selected">-请选择-</option>
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
		            <label class="control-label col-md-4">操作结果</label>
		            <div class="col-md-8">
		           		<select name="logResult" class="form-control">
							<option value="" selected="selected">-请选择-</option>
							<c:if test="${pages.bean.logResult == '成功'}">
			           			<option value="成功" selected="selected">成功</option>
							</c:if>
							<c:if test="${pages.bean.logResult != '成功'}">
			           			<option value="成功">成功</option>
							</c:if>
							<c:if test="${pages.bean.logResult == '失败'}">
			           			<option value="失败" selected="selected">失败</option>
							</c:if>
							<c:if test="${pages.bean.logResult != '失败'}">
			           			<option value="失败">失败</option>
							</c:if>
						</select>
		            </div>
          	    </div>
	          </div>	    
              <div class="col-md-2">
              	<div class="form-group">
		            <label class="control-label col-md-6">创建日期</label>
		            <div class="col-md-6">
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
			<span class="title">所有管理员日志列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th><input type="checkbox" onclick="check(this.checked)"></th>
						<th>序号</th>
						<th>用户名</th>
						<th>日志类型</th>
						<th>IP地址</th>
						<th>操作名称</th>
						<!-- <th>操作内容</th> -->
						<th>操作结果</th>
						<th>创建日期</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="l" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td><input type="checkbox" class="box" value="${l.logId}"></td>
							<td>${i.count}</td>
							<td>${l.userName}</td>
							<td>${l.logTypeDesc}</td>
							<td>${l.logIp}</td>
							<td>${l.logName}</td>
							<!--内容超过一定长度以...显示 
							<td>
								<c:if test="${fn:length(l.logContent) >= 100}">
							        ${fn:substring(l.logContent,0,100)}......
							    </c:if>
								<c:if test="${fn:length(l.logContent) < 100}">
							        ${l.logContent}
							    </c:if>
							</td>-->
							<td>${l.logResult}</td>
							<td>${l.curday}</td>
							<td>${l.logTime}</td>
							<td><a href="javascript:;" class="detail btn btn_5 btn-xs btn-info" value="${l.logId}">详情</a></td>
						</tr>
						<!-- 日志详细信息弹出层 -->
						<div id="detailFormLay${l.logId}" style="display: none">
							日志操作内容:<br/>
							<strong><textarea rows="8" cols="70">${l.logContent}</textarea></strong>
						</div>
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