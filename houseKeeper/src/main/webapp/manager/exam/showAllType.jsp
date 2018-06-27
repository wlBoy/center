<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有的题型</title>
<style type="text/css">
.con {
	width: 80%;
	margin: 100px auto;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
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
.title{
	float:right;
	position:relative;
	top:20px;
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
/*弹出层*/
$(function(){
	// 修改弹出层
	$(".update").click(function() {
		var modal = showNormalLay("修改题型信息",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/exam/type/update.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("120px");
		modal.setWidth("400px");
		//ajax请求为更新题型做数据回显准备
		var tid = $(this).attr("value");
		$.get("${ctx}/exam/rest/findByTypeId.do","typeId="+tid,function(t){
			$("#modal2 form :text[name=typeName]").val(t.typeName);
			$("#modal2 form :text[name=remark]").val(t.remark);
			$("<input type='hidden' name='typeId' value='"+t.typeId+"'>").appendTo($("#modal2 form"));
		},"json"); 
	});
	//添加题型
	$("#add").click(function() {
		var modal = showNormalLay("添加题型信息",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/exam/type/add.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("120px");
		modal.setWidth("400px");
		//ajax请求数据库是否存在该题型
		$("#modal2 form :text[name=typeName]").blur(function(){
			var typeName = $("#modal2 form :text[name=typeName]").val();
			$.get("${ctx}/exam/rest/findByTypeName.do","typeName="+typeName,function(t){
				if(t.typeName != null){
					swal("OMG!", "该题型已存在，请更换一个!", "error");
				} 
			},"json");  
		});
	});
})
/*删除一条记录*/
function doDelete(tid){
swal({
	title: "删除题型", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/exam/type/delete.do?typeIds="+tid;
	});
}
</script>
</head>
<body>
	<div class="tip">
		温馨提示:题型谨慎删除!
	</div>
	<!-- 表格区,列表内容显示区 -->
	<div class="con">
		<div class="bs-example4" data-example-id="contextual-table">
			<button type="button" id="add" class="btn btn_5 btn-lg btn-primary">添加</button>
			<span class="title">所有题型列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th>序号</th>
						<th>题型</th>
						<th>创建时间</th>
						<th>最后修改时间</th>
						<th>描述</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="t" items="${types}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td>${i.count}</td>
							<td>${t.typeName}</td>
							<td>${t.createTime}</td>
							<td>${t.updateTime}</td>
							<td>${t.remark}</td>
							<td>
								<a href="javascript:;" class="update btn btn_5 btn-xs btn-info" value="${t.typeId}">修改</a>
								<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger" disabled="disabled" onclick="doDelete('${t.typeId}');">删除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 弹出层表单 -->
	<div id="FormLay" style="display: none">
		<form class="form-horizontal" method="post">
			<div class="form-group">
	            <label class="control-label col-md-3">题型</label>
	            <div class="col-md-8">
	              <input class="form-control" name="typeName" type="text">
	            </div>
	        </div>
			<div class="form-group">
	            <label class="control-label col-md-3">备注</label>
	            <div class="col-md-8">
	              <input class="form-control" name="remark" type="text">
	            </div>
	        </div>
		</form>
	</div>
</body>
</html>