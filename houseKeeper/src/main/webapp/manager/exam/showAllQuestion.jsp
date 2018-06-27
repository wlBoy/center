<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>显示所有的题目</title>
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
	width: 1200px;
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
.hide{
	display:none;
}
.show{
	display:block;
}
strong{
	margin-left:5px;
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
	//详细信息弹出层
	$(".detail").click(function() {
		var qid = $(this).attr("value");
		var modal = showNormalLay("该题的详细信息如下",$("#detailFormLay"+qid),function(){
			//不执行任何操作
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		//隐藏保存按钮
		$('#modal2 #btn_save').css('display','none');
	});
	// 修改弹出层
	$(".update").click(function() {
		var modal = showNormalLay("修改题目信息",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/exam/question/update.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("430px");
		modal.setWidth("600px");
		var qid = $(this).attr("value");
		// 回显数据
		$.get("${ctx}/exam/rest/findByQuestionId.do","questionId="+qid,function(q){
			$("#modal2 form :text[name=questionTitle]").val(q.questionTitle);
			$("#modal2 form").find("textarea").html(q.answer);
			$("#modal2 form :text[name=optionA]").val(q.optionA);
			$("#modal2 form :text[name=optionB]").val(q.optionB);
			$("#modal2 form :text[name=optionC]").val(q.optionC);
			$("#modal2 form :text[name=optionD]").val(q.optionD);
			$("#modal2 form :text[name=remark]").val(q.remark);
			$("<input type='hidden' name='questionId' value='"+q.questionId+"'>").appendTo($("#modal2 form"));
			/*回显题型 */
			$("#modal2 #typeId").find("option").each(function(index,obj){
				if(q.type.typeId==$(obj).val()){
					$(obj).prop("selected",true);
					
				}
			});
			changeType(q.type.typeId);
			/*题型与选项联动*/
			$("#modal2 #typeId").change(function(){
				changeType($(this).val());
			});
		},"json"); 
	});
	// 添加弹出层
	$("#add").click(function() {
		var modal = showNormalLay("添加题目信息",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/exam/question/add.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("430px");
		modal.setWidth("600px");
		$("#modal2 .option").each(function(i,o){
			$(o).prop("disabled",true);
		});
		/*题型与选项联动*/
		$("#modal2 #typeId").change(function(){
			changeType($(this).val());
		});
	});
})
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除题目", 
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
				param += "questionIds="+o.value+"&";
			}
			
		});
		if(isCkecked){
			location = "${ctx}/exam/question/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*删除一条记录*/
function doDelete(qid){
swal({
	title: "删除题目", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/exam/question/delete.do?questionIds="+qid;
	});
}
/*复选框联动*/
function check(isChecked){
	$('.box').each(function(i,o){
		$(o).prop("checked",isChecked);
	});
}
/*切换状态*/
function changeState(qid){
swal({
	title: "切换题目状态", 
	text: "你确定切换题目状态?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/exam/question/changeState.do?questionId="+qid;
	});
}
/*题型与选项联动*/
function changeType(typeId){
	if(typeId==3){
		// 简答题
		$("#modal2 .option").each(function(i,o){
			$(o).removeClass("show").addClass("hide");
		});
	}else{
		// 单选题或多选题
		$("#modal2 .option").each(function(i,o){
			$(o).removeClass("hide").addClass("show");
		});
	}
}
</script>
	</head>
	<body>
		<div class="tip">
			温馨提示:题目被冻结了就不能被分配给试卷!
		</div>
		<div class="contact">
			<!-- 表单区,根据查询条件筛选列表数据 -->
			<form id="pageFm" class="form-horizontal" action="${ctx}/exam/question/showAllQuestion.do"
				method="post">
				<input id="pageNum" type="hidden" name="pageNum">
				<input value="${pages.size}" id="size" type="hidden" name="size">
				<fieldset>
	            <div class="row">
	              <div class="col-md-3">
	              	<div class="form-group">
			            <label class="control-label col-md-3">题目标题</label>
			            <div class="col-md-8">
			              <input class="form-control" name="questionTitle" value="${pages.bean.questionTitle}" type="text">
			            </div>
	            	</div>
	              </div>
	              <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-4">题型</label>
			            <div class="col-md-7">
			           		<select name="type.typeId" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:forEach var="t" items="${types}">
									<c:if test="${pages.bean.type.typeId == t.typeId}">
										<option selected="selected" value="${t.typeId}">${t.typeName}</option>
									</c:if>
									<c:if test="${pages.bean.type.typeId != t.typeId}">
										<option value="${t.typeId}">${t.typeName}</option>
									</c:if>
								</c:forEach>
							</select>
			            </div>
	          		</div>
	              </div>
	              <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">题目状态</label>
			            <div class="col-md-7">
			           		<select name="questionStatus" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:if test="${pages.bean.questionStatus == 0}">
									<option value="0" selected="selected">正常</option>
								</c:if>
								<c:if test="${pages.bean.questionStatus != 0}">
									<option value="0">正常</option>
								</c:if>
								<c:if test="${pages.bean.questionStatus == 1}">
									<option value="1" selected="selected">冻结</option>
								</c:if>
								<c:if test="${pages.bean.questionStatus != 1}">
									<option value="1">冻结</option>
								</c:if>
							</select>
			            </div>
	          		</div>
	              </div>
	              <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">备注信息</label>
			            <div class="col-md-7">
			              <input class="form-control" name="remark" value="${pages.bean.remark}" type="text">
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
	              <div class="col-md-1">
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
				<span class="title">所有题目列表</span>
				<table class="table">
					<thead>
						<tr bgcolor="#D9edf7">
							<th><input type="checkbox" onclick="check(this.checked)"></th>
							<th>序号</th>
							<th>题目标题</th>
							<th>题型</th>
							<th>创建日期</th>
							<th>创建时间</th>
							<th>最后修改时间</th>
							<th>题目状态</th>
							<th>备注信息</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="q" items="${pages.list}" varStatus="i">
							<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
								<td><input type="checkbox" class="box" value="${q.questionId}"></td>
								<td>${i.count}</td>
								<td>${q.questionTitle}</td>
								<td>${q.type.typeName}</td>
								<td>${q.curday}</td>
								<td>${q.createTime}</td>
								<td>${q.updateTime}</td>
								<!-- 状态切换区 -->
								<td>
									<c:if test="${q.questionStatus==0}">
										<a href="javascript:;" onclick="changeState(${q.questionId});"
											class="label label-info state1">正常</a>
									</c:if>
									<c:if test="${q.questionStatus==1}">
										<a href="javascript:;" onclick="changeState(${q.questionId});"
											class="label label-danger state2">冻结</a>
									</c:if>
								</td>
								<td>${q.remark}</td>
								<td>
									<a href="javascript:;" class="detail btn btn_5 btn-xs btn-info" value="${q.questionId}">详情</a>
									<a href="javascript:;" class="update btn btn_5 btn-xs btn-info"
										value="${q.questionId}">修改</a>
									<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger"
										onclick="doDelete(${q.questionId});">删除</a>
								</td>
							</tr>
							<!-- 题目详细信息弹出层 -->
							<div id="detailFormLay${q.questionId}" style="display: none">
								<c:if test="${q.type.typeId != 3}">
									<li>题目:<strong>${q.questionTitle}</strong></li>
									<li>${q.optionA}</li>
									<li>${q.optionB}</li>
									<li>${q.optionC}</li>
									<li>${q.optionD}</li>
									<li>答案:<strong>${q.answer}</strong></li>
								</c:if>
								<c:if test="${q.type.typeId == 3}">
									<li>题目:<strong>${q.questionTitle}</strong></li>
									<li>答案:<strong><textarea rows="8" cols="40">${q.answer}</textarea></strong></li>
								</c:if>
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
		<!-- 弹出层表单 -->
		<div id="FormLay" style="display: none">
			<form class="form-horizontal" method="post">
	          <div class="form-group">
	            <label class="control-label col-md-3">题目标题</label>
	            <div class="col-md-8">
	              <input class="form-control" name="questionTitle" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">题型</label>
	            <div class="col-md-8">
	              <select name="type.typeId" id="typeId" class="form-control">
					<c:forEach var="t" items="${types}">
						<option value="${t.typeId}">${t.typeName}</option>
					</c:forEach>
				 </select>
	            </div>
	          </div>
	          <div class="form-group option hide">
	            <label class="control-label col-md-3">选择A</label>
	            <div class="col-md-8">
	           		 <input class="form-control" name="optionA" type="text">
	            </div>
	          </div>
	          <div class="form-group option hide">
	            <label class="control-label col-md-3">选择B</label>
	            <div class="col-md-8">
	           		 <input class="form-control" name="optionB" type="text">
	            </div>
	          </div>
	          <div class="form-group option hide">
	            <label class="control-label col-md-3">选择C</label>
	            <div class="col-md-8">
	           		 <input class="form-control" name="optionC" type="text">
	            </div>
	          </div>
	          <div class="form-group option hide">
	            <label class="control-label col-md-3">选择D</label>
	            <div class="col-md-8">
	           		 <input class="form-control" name="optionD" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">正确答案</label>
	            <div class="col-md-8">
	            	 <textarea rows="8" cols="40" name="answer" class="form-control answer"></textarea>
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