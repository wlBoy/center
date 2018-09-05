<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有的账务</title>
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
	display:block;
	margin:1px auto;
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
</style>
<script type="text/javascript">
/*弹出层*/
$(function(){
	// 加载查询区域的下拉框切换效果
	changeSelectType();
	// 修改弹出层
	$(".update").click(function() {
		var modal = showNormalLay("修改个人账务信息",$("#FormLay"),function(){
			// 修改表单提交地址，非空检验后提交表单
			$("#modal2 form").attr("action","${ctx}/account/account/update.do");
			if(checkForm()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		var aid = $(this).attr("value");
		// 回显数据
		$.post("${ctx}/account/rest/findByAccountId.do","accountId="+aid,function(r){
			$("#modal2 form :text[name=accountTitle]").val(r.data.accountTitle);
			$("#modal2 form :text[name=accountFee]").val(r.data.accountFee);
			$("#modal2 form :text[name=accountType]").val(r.data.accountType);
			$("#modal2 form :text[name=remark]").val(r.data.remark);
			$("<input type='hidden' name='accountId' value='"+r.data.accountId+"'>").appendTo($("#modal2 form"));
			/*回显父类别 */
			$("#modal2 #parentType").find("option").each(function(index,obj){
				if(r.data.type.parentType==$(obj).val()){
					$(obj).prop("selected",true);
				}
			});
			/*显示并回显子类别 */
			changeType(r.data.type.parentType,r.data.type.typeId);
			/*修改弹出层切换父级别并回显子类别*/
			$("#modal2 #parentType").change(function(){
				changeType($(this).val(),r.data.type.typeId);
			});
		},"json"); 
		checkAccountTitle();
	});
	// 添加弹出层
	$("#add").click(function() {
		var modal = showNormalLay("添加个人账务信息",$("#FormLay"),function(){
			// 修改表单提交地址，非空检验后提交表单
			$("#modal2 form").attr("action","${ctx}/account/account/add.do");
			if(checkForm()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		$("#modal2 #parentType").change(function(){
			/*账务级别联动*/
			changeType($(this).val(),0);
		});
		checkAccountTitle();
	});
})
//检查该账务标题是否存在，保证账务标题的唯一性
function checkAccountTitle(){
	var accountTitleInput = $("#modal2 form :text[name=accountTitle]");
	//ajax请求数据库是否存在该用户
	accountTitleInput.blur(function(){
		$.post("${ctx}/account/rest/findByAccountTitle.do","accountTitle="+accountTitleInput.val(),function(r){
			if(r.data != null){
				accountTitleInput.val("");
				accountTitleInput.select();
				swal("OMG!", "该账务标题已存在，请更换一个!", "error");
			}
		},"json");    
	});
}
//弹出层表单非空校验
function checkForm(){
	var accountTitle = $("#modal2 form :text[name=accountTitle]").val();
	var accountFee = $("#modal2 form input[name=accountFee]").val();
	var accountType = $("#modal2 form :text[name=accountType]").val();
	if(accountTitle==null||accountTitle==''){
		swal("OMG!", "账务标题不能为空!", "error");
		return false;
	}
	if(accountFee==null||accountFee==0){
		swal("OMG!", "账务金额不能为空!", "error");
		return false;
	}
	if(accountType==null||accountType==''){
		swal("OMG!", "账务方式不能为空!", "error");
		return false;
	}
	return true;
}
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除个人账务", 
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
				param += "accountIds="+o.value+"&";
			}
			
		});
		if(isCkecked){
			location = "${ctx}/account/account/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*删除一条记录*/
function doDelete(aid){
swal({
	title: "删除个人账务", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/account/account/delete.do?accountIds="+aid;
	});
}
/*复选框联动*/
function check(isChecked){
	$('.box').each(function(i,o){
		$(o).prop("checked",isChecked);
	});
}
/*弹出层账务级别联动*/
function changeType(parentType,typeId){
	if(parentType=="收入"){
		$("#modal2 #typeId").find("option").filter(".comeInTypes").each(function(index,obj){
			$(obj).removeClass("hide").addClass("show");
			/*回显子类别 */
			if(typeId==$(obj).val()){
				$(obj).prop("selected",true);
			}
			if(index==0){
				$(obj).prop("selected",true);
			}
		});
		$("#modal2 #typeId").find("option").filter(".comeOutTypes").each(function(index,obj){
			$(obj).removeClass("show").addClass("hide");
		});
	}else{
		// 支出
		$("#modal2 #typeId").find("option").filter(".comeInTypes").each(function(index,obj){
			$(obj).removeClass("show").addClass("hide");
		});
		$("#modal2 #typeId").find("option").filter(".comeOutTypes").each(function(index,obj){
			$(obj).removeClass("hide").addClass("show");
			/*回显子类别 */
			if(typeId==$(obj).val()){
				$(obj).prop("selected",true);
			}
			if(index==0){
				$(obj).prop("selected",true);
			}
		});
	}
}
/*查询区域账务级别联动*/
function changeSelectType(){
	$("#selectParentType").change(function(){
		var value = $(this).val();
		if(value==""){
			$("#selectTypeId").find("option").filter(".comeInSelectTypes").each(function(index,obj){
				$(obj).removeClass("show").addClass("hide");
			});
			$("#selectTypeId").find("option").filter(".comeOutSelectTypes").each(function(index,obj){
				$(obj).removeClass("show").addClass("hide");
			});
		}else{
			if(value=="收入"){
				$("#selectTypeId").find("option").filter(".comeInSelectTypes").each(function(index,obj){
					$(obj).removeClass("hide").addClass("show");
				});
				$("selectTypeId").find("option").filter(".comeOutSelectTypes").each(function(index,obj){
					$(obj).removeClass("show").addClass("hide");
				});
			}else{
				// 支出
				$("#selectTypeId").find("option").filter(".comeInSelectTypes").each(function(index,obj){
					$(obj).removeClass("show").addClass("hide");
				});
				$("#selectTypeId").find("option").filter(".comeOutSelectTypes").each(function(index,obj){
					$(obj).removeClass("hide").addClass("show");
				});
			}
		}
	});
}
</script>
</head>
<body>
	<c:if test="${empty pages.bean}">
		<h3 align="center">对不起，您现在还没有任何个人账务记录!
			<button type="button" id="add" class="btn btn_5 btn-lg btn-primary">添加</button>
		</h3>
	</c:if>
	<c:if test="${!empty pages.bean}">
	<div class="contact">
		<!-- 表单区,根据查询条件筛选列表数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/account/account/showPersonalAccount.do" 
			method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
			<fieldset>
            <div class="row">
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
			            <label class="control-label col-md-5">账务类别</label>
			            <div class="col-md-7">
			           		<select name="type.typeId" id="selectTypeId" class="form-control">
			           			<option value="">--请选择--</option>
			           			<c:forEach var="cit" items="${comeInTypes}">
			           				<c:if test="${pages.bean.type.typeId eq cit.typeId}">
				           				<option class="comeInSelectTypes hide" value="${cit.typeId}" selected="selected">${cit.typeName}</option>
				           			</c:if>
				           			<c:if test="${pages.bean.type.typeId ne cit.typeId}">
				           				<option class="comeInSelectTypes hide" value="${cit.typeId}">${cit.typeName}</option>
				           			</c:if>
			           			</c:forEach>
			           			<c:forEach var="cot" items="${comeOutTypes}">
			           				<c:if test="${pages.bean.type.typeId eq cot.typeId}">
				           				<option class="comeOutSelectTypes hide" value="${cot.typeId}" selected="selected">${cot.typeName}</option>
				           			</c:if>
				           			<c:if test="${pages.bean.type.typeId ne cot.typeId}">
				           				<option class="comeOutSelectTypes hide" value="${cot.typeId}">${cot.typeName}</option>
				           			</c:if>
			           			</c:forEach>
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
			<button type="button" id="add" class="btn btn_5 btn-lg btn-primary">添加</button>
			<button type="button" id="delete"
				class="btn btn_5 btn-lg btn-Danger" onclick="doDeleteMore();">批删</button>
			<span class="title">个人账务列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th><input type="checkbox" onclick="check(this.checked)"></th>
						<th>序号</th>
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
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="a" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td><input type="checkbox" class="box" value="${a.accountId}"></td>
							<td>${i.count}</td>
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
							<td>
								<a href="javascript:;" class="update btn btn_5 btn-xs btn-info" value="${a.accountId}">修改</a>
								<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger" onclick="doDelete('${a.accountId}');">删除</a>
							</td>
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
	</c:if>
	<!-- 弹出层表单 -->
	<div id="FormLay" style="display: none">
		<form class="form-horizontal" method="post">
			<div class="form-group">
	            <label class="control-label col-md-3">账务标题</label>
	            <div class="col-md-8">
	              <input class="form-control" name="accountTitle" type="text">
	            </div>
            </div>
			<div class="form-group">
	            <label class="control-label col-md-3">父级类别</label>
	            <div class="col-md-8">
	           		<select name="type.parentType" id="parentType" class="form-control" >
	           			<option value="收入" selected="selected">收入</option>
	           			<option value="支出">支出</option>
					</select>
	            </div>
          	</div>
            <div class="form-group">
	            <label class="control-label col-md-3">账务类别</label>
	            <div class="col-md-8">
	           		<select name="type.typeId" id="typeId" class="form-control" >
	           			<c:forEach var="cit" items="${comeInTypes}">
	           				<option class="comeInTypes show" value="${cit.typeId}">${cit.typeName}</option>
	           			</c:forEach>
	           			<c:forEach var="cot" items="${comeOutTypes}">
		           				<option class="comeOutTypes hide" value="${cot.typeId}">${cot.typeName}</option>
		           		</c:forEach>
	           		</select>
	            </div>
          	</div>
			<div class="form-group">
	            <label class="control-label col-md-3">账务金额</label>
	            <div class="col-md-8">
	              <input class="form-control" name="accountFee" type="number">
	            </div>
            </div>
			<div class="form-group">
	            <label class="control-label col-md-3">账务方式</label>
	            <div class="col-md-8">
	              <input class="form-control" name="accountType" type="text">
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