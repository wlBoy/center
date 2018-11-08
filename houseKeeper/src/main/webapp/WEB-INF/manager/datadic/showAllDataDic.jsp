<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有的数据字典</title>
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
	width: 1024px;
	height: 80px;
	margin:1px auto;
}
.table , .table th{
	text-align: center;
}
.modal-content{
	top:100px;
	right:24px;
}
.btn-xs{
	position: relative;
	top:5px;
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
/*弹出层*/
$(function(){
	// 修改弹出层
	$(".update").click(function() {
		var modal = showNormalLay("修改数据字典",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/data/dic/update.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("500px");
		var did = $(this).attr("value");
		// 回显数据
		$.post("${ctx}/data/rest/findByDataDicId.do","dataDicId="+did,function(d){
			$("#modal2 form :text[name=dataDicName]").val(d.data.dataDicName);
			$("#modal2 form :text[name=dataDicCode]").val(d.data.dataDicCode);
			$("#modal2 form :text[name=remark]").val(d.data.remark);
			$("<input type='hidden' name='dataDicId' value='"+d.data.dataDicId+"'>").appendTo($("#modal2 form"));
		},"json"); 
	});
	// 添加弹出层
	$("#add").click(function() {
		var modal = showNormalLay("添加数据字典",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/data/dic/add.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("500px");
		//ajax请求数据库是否存在该数据字典
		$("#modal2 form :text[name=dataDicCode]").blur(function(){
			var dataDicCode = $("#modal2 form :text[name=dataDicCode]").val();
			$.post("${ctx}/data/rest/findByDataDicCode.do","dataDicCode="+dataDicCode,function(d){
				if(d.data != null){
					swal("OMG!", "该数据字典代码已存在，请更换一个!", "error");
				}
			},"json");  
		});
	});
})
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除个人数据字典", 
	text: "你确定删除这些记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		var param = "";
		var isCkecked = false;
		$(".box").each(function(i,o){
			if(o.checked){
				isCkecked = true;
				param += "dataDicIds="+o.value+"&";
			}
			
		});
		if(isCkecked){
			location = "${ctx}/data/dic/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*删除一条记录*/
function doDelete(tid){
swal({
	title: "删除个人数据字典", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/data/dic/delete.do?dataDicIds="+tid;
	});
}
/*复选框联动*/
function check(isChecked){
	$('.box').each(function(i,o){
		$(o).prop("checked",isChecked);
	});
}
</script>
</head>
<body>
	<c:if test="${empty pages.bean}" >
		<h3 align="center">对不起，您现在还没有任何数据字典!
			<button type="button" id="add" class="btn btn_5 btn-lg btn-primary">添加</button>
		</h3>
	</c:if>
	<c:if test="${!empty pages.bean}">
	<div class="contact">
		<!-- 表单区,根据查询条件筛选列表数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/data/dic/showAllDataDic.do" 
			method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
				<fieldset>
            <div class="row">
              <div class="col-md-5">
              	<div class="form-group">
		            <label class="control-label col-md-3">数据字典名称</label>
		            <div class="col-md-8">
		              <input class="form-control" name="dataDicName" value="${pages.bean.dataDicName}" type="text">
		            </div>
            	</div>
              </div>
              <div class="col-md-5">
              	<div class="form-group">
		            <label class="control-label col-md-3">数据字典代码</label>
		            <div class="col-md-8">
		              <input class="form-control" name="dataDicCode" value="${pages.bean.dataDicCode}" type="text">
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
			<button type="button" id="add" class="btn btn_5 btn-lg btn-primary">添加</button>
			<button type="button" id="delete"
				class="btn btn_5 btn-lg btn-Danger" onclick="doDeleteMore();">批删</button>
			<span class="title">数据字典列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th><input type="checkbox" onclick="check(this.checked)"></th>
						<th>序号</th>
						<th>数据字典名称</th>
						<th>数据字典代码</th>
						<th>创建时间</th>
						<th>最后修改时间</th>
						<th>数据字典描述</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="d" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td><input type="checkbox" class="box" value="${d.dataDicId}"></td>
							<td>${i.count}</td>
							<td>${d.dataDicName}</td>
							<td>${d.dataDicCode}</td>
							<td>${d.createTime}</td>
							<td>${d.updateTime}</td>
							<td>${d.remark}</td>
							<td>
								<a href="javascript:;" class="update btn btn_5 btn-xs btn-info" value="${d.dataDicId}">修改</a>
								<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger" onclick="doDelete('${d.dataDicId}');">删除</a>
								<a href="${ctx}/data/dic/term/showTermByDataDicCode.do?dataDicCode=${d.dataDicCode}" class="btn btn_5 btn-xs btn-info">维护字典项</a>
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
	</c:if>
	<!-- 弹出层表单 -->
	<div id="FormLay" style="display: none">
		<form class="form-horizontal" method="post">
			<div class="form-group">
	            <label class="control-label col-md-3">数据字典名称</label>
	            <div class="col-md-8">
	              <input class="form-control" name="dataDicName" type="text">
	            </div>
            </div>
			<div class="form-group">
	            <label class="control-label col-md-3">数据字典代码</label>
	            <div class="col-md-8">
	              <input class="form-control" name="dataDicCode" type="text">
	            </div>
            </div>
			<div class="form-group">
	            <label class="control-label col-md-3">备注信息</label>
	            <div class="col-md-8">
	              <input class="form-control" name="remark" type="text">
	            </div>
            </div>
		</form>
	</div>
</body>
</html>