<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>显示所有的模块</title>
		<style type="text/css">
.con {
	width: 100%;
	margin: 10px auto;
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
.state1{
	color:#58c0de;
}
.state2{
	color:#d2322d;
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
.hide{
	display:none;
}
.show{
	display:block;
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
		var modal = showNormalLay("修改模块信息",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/system/module/update.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("250px");
		modal.setWidth("400px");
		var mid = $(this).attr("value");
		//回显数据
		$.get("${ctx}/system/rest/findByModuleId.do","moduleId="+mid,function(m){
			$("#modal2 form :text[name=moduleName]").val(m.moduleName);
			$("#modal2 form :text[name=actionUrl]").val(m.actionUrl);
			$("#modal2 form :text[name=remark]").val(m.remark);
			$("<input type='hidden' name='moduleId' value='"+m.moduleId+"'>").appendTo($("#modal2 form"));
			// 回显模块级别
			$("#modal2 #moduleLevel").find("option").each(function(index,obj){
				if(m.moduleLevel==$(obj).val()){
					$(obj).prop("selected",true);
				}
			});
			// 回显父模块模块
			changeModule(m.moduleLevel,m.parentId);
			// 选择框切换模块级别时模块联动
			$("#modal2 #moduleLevel").change(function(){
				changeModule($(this).val(),m.parentId);
			});
		},"json"); 
	});
	// 添加弹出层
	$("#add").click(function() {
		var modal = showNormalLay("添加模块信息",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/system/module/add.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("250px");
		modal.setWidth("400px");
		//ajax请求数据库是否存在该模块
		$("#modal2 form :text[name=moduleName]").blur(function(){
			var moduleName = $("#modal2 form :text[name=moduleName]").val();
			$.get("${ctx}/system/rest/findByModuleName.do","moduleName="+moduleName,function(m){
				if(m.moduleName != null){
					swal("OMG!", "该模块名已存在，请更换一个!", "error");
				} 
			},"json");  
		});
		// 选择框切换模块级别时模块联动
		$("#modal2 #moduleLevel").change(function(){
			changeModule($(this).val(),0);
		});
	});
	
})
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除模块", 
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
				param += "moduleIds="+o.value+"&";
			}
		});
		if(isCkecked){
			location = "${ctx}/system/module/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*删除一条记录*/
function doDelete(mid){
swal({
	title: "删除模块", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/system/module/delete.do?moduleIds="+mid;
	});
}
/*复选框联动*/
function check(isChecked){
	$('.box').each(function(i,o){
		$(o).prop("checked",isChecked);
	});
}
/*切换状态*/
function changeState(mid){
swal({
	title: "切换模块状态", 
	text: "你确定切换模块状态?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/system/module/changeState.do?moduleId="+mid;
	});
}
/*弹出层模块级别与父模块联动*/
function changeModule(moduleLevel,parentId){
	if(moduleLevel==1){
		// 隐藏所有的一级模块
		$("#modal2 #parentModule").find("option").filter(".oneMenus").each(function(index,obj){
			$(obj).removeClass("show").addClass("hide");
		});
		// 显示无选项
		$("#modal2 #parentModule").find("option").filter(".none").each(function(index,obj){
			$(obj).removeClass("hide").addClass("show").prop("selected",true);
		});
		// 禁用访问地址输入框
		$("#modal2 #actionUrl").prop("disabled",true);
	}else{
		// 启用访问地址输入框
		$("#modal2 #actionUrl").prop("disabled",false);
		// 显示所有的一级模块
		$("#modal2 #parentModule").find("option").filter(".oneMenus").each(function(index,obj){
			$(obj).removeClass("hide").addClass("show");
			/*回显父模块 */
			if(parentId==$(obj).val()){
				$(obj).prop("selected",true);
			}
			if(index==0){
				$(obj).prop("selected",true);
			}
		});
		// 隐藏无选项
		$("#modal2 #parentModule").find("option").filter(".none").each(function(index,obj){
			$(obj).removeClass("show").addClass("hide");
		});
	}
}
</script>
	</head>
	<body>
		<div class="tip">
			温馨提示:添加或修改模块后重新登录生效,模块冻结后不能被分配!
		</div>
		<div class="contact">
			<!-- 表单区,根据查询条件筛选列表数据 -->
			<form id="pageFm" class="form-horizontal" action="${ctx}/system/module/showAllModule.do"
				method="post">
				<input id="pageNum" type="hidden" name="pageNum">
				<input value="${pages.size}" id="size" type="hidden" name="size">
				<fieldset>
	            <div class="row">
	              <div class="col-md-4">
	              	<div class="form-group">
			            <label class="control-label col-md-3">模块名</label>
			            <div class="col-md-8">
			              <input class="form-control" name="moduleName" value="${pages.bean.moduleName}" type="text">
			            </div>
	            	</div>
	              </div>
	              <div class="col-md-3">
	              	<div class="form-group">
			            <label class="control-label col-md-4">模块级别</label>
			            <div class="col-md-7">
			           		<select name="moduleLevel" class="form-control">
								<option value="" selected="selected">---请选择---</option>
								<c:if test="${pages.bean.moduleLevel == 1}">
									<option value="1" selected="selected">一级</option>
								</c:if>
								<c:if test="${pages.bean.moduleLevel != 1}">
									<option value="1">一级</option>
								</c:if>
								<c:if test="${pages.bean.moduleLevel == 2}">
									<option value="2" selected="selected">二级</option>
								</c:if>
								<c:if test="${pages.bean.moduleLevel != 2}">
									<option value="2">二级</option>
								</c:if>
							</select>
			            </div>
	          		</div>
	              </div>
	              <div class="col-md-3">
	              	<div class="form-group">
			            <label class="control-label col-md-5">模块状态</label>
			            <div class="col-md-7">
			           		<select name="isAllowed" class="form-control">
								<option value="" selected="selected">---请选择---</option>
								<c:if test="${pages.bean.isAllowed == 0}">
									<option value="0" selected="selected">激活</option>
								</c:if>
								<c:if test="${pages.bean.isAllowed != 0}">
									<option value="0">激活</option>
								</c:if>
								<c:if test="${pages.bean.isAllowed == 1}">
									<option value="1" selected="selected">冻结</option>
								</c:if>
								<c:if test="${pages.bean.isAllowed != 1}">
									<option value="1">冻结</option>
								</c:if>
							</select>
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
				<span class="title">所有模块列表</span>
				<table class="table">
					<thead>
						<tr bgcolor="#D9edf7">
							<th><input type="checkbox" onclick="check(this.checked)"></th>
							<th>序号</th>
							<th>模块名</th>
							<th>父级模块</th>
							<th>模块级别</th>
							<th>访问地址</th>
							<th>创建时间</th>
							<th>最后修改时间</th>
							<th>备注</th>
							<th>模块状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="m" items="${pages.list}" varStatus="i">
							<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
								<td><input type="checkbox" class="box" value="${m.moduleId}"></td>
								<td>${i.count}</td>
								<td>${m.moduleName}</td>
								<td>
									<c:if test="${empty m.parentName}">
										--------
									</c:if>
									<c:if test="${!empty m.parentName}">
										${m.parentName}
									</c:if>
								</td>
								<td>
									<c:if test="${m.moduleLevel==1}">
										一级
									</c:if>
									<c:if test="${m.moduleLevel==2}">
										二级
									</c:if>
								</td>
								<td>
									<c:if test="${empty m.actionUrl}">
										------------
									</c:if>
									<c:if test="${!empty m.actionUrl}">
										${m.actionUrl}
									</c:if>
								</td>
								<td>${m.createTime}</td>
								<td>${m.updateTime}</td>
								<td>${m.remark}</td>
								<!-- 状态切换区 -->
								<td>
									<c:if test="${m.isAllowed==0}">
										<a href="javascript:;" onclick="changeState(${m.moduleId});"
											class="label label-info state1">正常</a>
									</c:if>
									<c:if test="${m.isAllowed==1}">
										<a href="javascript:;" onclick="changeState(${m.moduleId});"
											class="label label-danger state2">冻结</a>
									</c:if>
								</td>
								<td>
									<a href="javascript:;" class="update btn btn_5 btn-xs btn-info"
										value="${m.moduleId}">修改</a>
									<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger"
										onclick="doDelete(${m.moduleId});">删除</a>
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
		<!-- 弹出层表单 -->
		<div id="FormLay" style="display: none">
			<form class="form-horizontal" method="post">
	          <div class="form-group">
	            <label class="control-label col-md-3">模块名称</label>
	            <div class="col-md-8">
	              <input class="form-control" name="moduleName" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">模块级别</label>
	            <div class="col-md-8">
	              <select class="form-control" name="moduleLevel" id="moduleLevel">
	              	<option value="1">一级</option><option value="2">二级</option>
	              </select>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">父级模块</label>
	            <div class="col-md-8">
	           		 <select name="parentId" id="parentModule" class="form-control">
	           		 	<option value="" class="none show" selected="selected">无</option>
	           		 	<c:forEach var="m" items="${oneMenus}">
	           				<option class="oneMenus hide" value="${m.moduleId}">${m.moduleName}</option>
	           			</c:forEach>
					 </select>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">访问地址</label>
	            <div class="col-md-8">
	              <input class="form-control" name="actionUrl" id="actionUrl" disabled="disabled" type="text">
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