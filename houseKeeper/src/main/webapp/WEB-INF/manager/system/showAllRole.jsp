<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有的角色</title>
<style type="text/css">
.con {
	width: 95%;
	margin: 10px auto;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 650px;
	height: 80px;
	margin:1px auto;
}
.btn-xs{
	position: relative;
	top:5px;
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
		var modal = showNormalLay("修改角色信息",$("#FormLay"),function(){
			// 修改表单提交地址，非空检验后提交表单
			$("#modal2 form").attr("action","${ctx}/system/role/update.do");
			if(checkForm()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		//ajax请求为更新角色做数据回显准备
		var rid = $(this).attr("value");
		$.post("${ctx}/system/rest/findByRoleId.do","roleId="+rid,function(r){
			$("#modal2 form :text[name=roleName]").val(r.data.roleName);
			$("#modal2 form :text[name=remark]").val(r.data.remark);
			$("<input type='hidden' name='roleId' value='"+r.data.roleId+"'>").appendTo($("#modal2 form"));
			/*回显模块 */
			$("#modal2 #modules").find(":checkbox").each(function(index,obj){
				for (var i = 0; i < r.data.modules.length; i++) {
					if(r.data.modules[i].moduleId==$(obj).val()){
						$(obj).attr("checked",true);
					}
				} 
			});
			checkRoleName();
			// 关联模块
			linkModule();
		},"json"); 
	});
	//添加角色
	$("#add").click(function() {
		var modal = showNormalLay("添加角色信息",$("#FormLay"),function(){
			// 修改表单提交地址，非空检验后提交表单
			$("#modal2 form").attr("action","${ctx}/system/role/add.do");
			if(checkForm()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		//ajax请求数据库是否存在该角色
		$("#modal2 form :text[name=roleName]").blur(function(){
			var roleName = $("#modal2 form :text[name=roleName]").val();
			$.post("${ctx}/system/rest/findByRoleName.do","roleName="+roleName,function(r){
				if(r.data != null){
					swal("OMG!", "该角色已存在，请更换一个!", "error");
				} 
			},"json");  
		});
		checkRoleName();
		// 关联模块
		linkModule();
	});
})
//检查角色名是否存在，保证角色名的唯一性
function checkRoleName(){
	var roleNameInput = $("#modal2 form :text[name=roleName]");
	//ajax请求数据库是否存在该用户
	roleNameInput.blur(function(){
		$.get("${ctx}/system/rest/findByRoleName.do","roleName="+roleNameInput.val(),function(r){
			if(r.roleName != null){
				roleNameInput.val("");
				roleNameInput.select();
				swal("OMG!", "该角色已存在，请更换一个!", "error");
			} 
		},"json");  
	});
}
// 弹出层表单非空校验
function checkForm(){
	var roleName = $("#modal2 form :text[name=roleName]").val();
	if(roleName==null||roleName==''){
		swal("OMG!", "角色名不能为空!", "error");
		return false;
	}
	return true;
}
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除角色", 
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
				param += "roleIds="+o.value+"&";
			}
		});
		if(isCkecked){
			location = "${ctx}/system/role/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*删除一条记录*/
function doDelete(rid){
	swal({
		title: "删除角色", 
		text: "你确定删除这条记录?", 
		type: "warning",
		showCancelButton: true,
		closeOnConfirm: false,
		confirmButtonText: "确定",
		confirmButtonColor: "#ec6c62"
		}, function() {
			location = "${ctx}/system/role/delete.do?roleIds="+rid;
		});
}
/*复选框联动*/
function check(isChecked){
	$('.box').each(function(i,o){
		$(o).prop("checked",isChecked);
	});
}
/*分配模块时模块联动*/
function linkModule(){
	// 点击父级模块，子模块全选或全不选
	$("#modal2 .one").each(function(index,obj){
		$(obj).click(function(){
			// 父模块的模块ID和是否选中状态
			var isChecked = $(obj).prop("checked");
			var moduleId = $(obj).val();
			$("#modal2 .two").each(function(i,o){
				if($(o).attr("data")==moduleId){
					$(o).prop("checked",isChecked);
				}
			});
		});
	});
	// 点击一个子模块，自动选其父模块
	$("#modal2 .two").each(function(index,obj){
		$(obj).click(function(){
			// 子模块的父级模块ID和是否选中状态
			var isChecked = $(obj).prop("checked");
			var parentId = $(obj).attr("data");
			if(isChecked){
				//点击选中一个子模块,自动选其父模块
				$("#modal2 .one").each(function(i,o){
					if($(o).val()==parentId){
						$(o).prop("checked",true);
					}
				});
			}else{
				//点击取消一个子模块,取消最后一个同一父级的兄弟模块,取消选中父模块
				var flag = true; 
				$("#modal2 .two").each(function(k,v){
					//找到其兄弟节点,如果有一个兄弟节点选中,就不能取消选中父级模块
					if(parentId==$(v).attr("data")){
						if($(v).prop("checked")){
							flag = false;
							return;
						}
					}
				});
				// 兄弟节点遍历玩，都是未选中状态，即取消选中父级模块
				if(flag){
					$("#modal2 .one").each(function(i,o){
						if($(o).val()==parentId){
							$(o).prop("checked",false);
						}
					});
				}
			}
		});
	});
}
</script>
</head>
<body>
	<div class="tip">
		温馨提示:角色分配模块后重新登录生效!
	</div>
	<div class="contact">
		<!-- 表单区,根据查询条件筛选列表数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/system/role/showAllRole.do" 
			method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
            <fieldset>
            <div class="row">
              <div class="col-md-5">
              	<div class="form-group">
		            <label class="control-label col-md-3">角色名</label>
		            <div class="col-md-8">
		              <input class="form-control" name="roleName" value="${pages.bean.roleName}" type="text">
		            </div>
            	</div>
              </div>
              <div class="col-md-5">
              	<div class="form-group">
		            <label class="control-label col-md-3">备注</label>
		            <div class="col-md-8">
		              <input class="form-control" name="remark" value="${pages.bean.remark}" type="text">
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
			<span class="title">所有角色列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th><input type="checkbox" onclick="check(this.checked)"></th>
						<th>序号</th>
						<th>角色名</th>
						<th>可访问模块</th>
						<th>创建时间</th>
						<th>最后修改时间</th>
						<th>描述</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="r" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td><input type="checkbox" class="box" value="${r.roleId}"></td>
							<td>${i.count}</td>
							<td>${r.roleName}</td>
							<!--内容超过一定长度以...显示 -->
							<td>
								<c:if test="${fn:length(r.moduleName) >= 30}">
							        ${fn:substring(r.moduleName,0,30)}......
							    </c:if>
								<c:if test="${fn:length(r.moduleName) < 30}">
							        ${r.moduleName}
							    </c:if>
							</td>
							<td>${r.createTime}</td>
							<td>${r.updateTime}</td>
							<td>${r.remark}</td>
							<td>
								<a href="javascript:;" class="update btn btn_5 btn-xs btn-info" value="${r.roleId}">修改</a>
								<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger" onclick="doDelete('${r.roleId}');">删除</a>
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
	            <label class="control-label col-md-3">角色名称</label>
	            <div class="col-md-8">
	              <input class="form-control" name="roleName" type="text">
	            </div>
	        </div>
			<div class="form-group">
	            <label class="control-label col-md-3">备注信息</label>
	            <div class="col-md-8">
	              <input class="form-control" name="remark" type="text">
	            </div>
	        </div>
            <div class="form-group">
	            <label class="control-label col-md-3">一级模块</label>
	            <div class="col-md-9" id="modules">
	              <c:forEach var="o" items="${oneModules}" varStatus="i">
	           		 <!-- 只有这种写法，才能将modelId动态塞入role对象的models属性数组中去 -->
	              	 <input type="checkbox" class="one" value="${o.moduleId}" name="modules[${i.index}].moduleId">${o.moduleName}
	              </c:forEach>
	            </div>
	        </div>
            <div class="form-group">
	            <label class="control-label col-md-3">二级模块</label>
	            <div class="col-md-9" id="modules">
	              <c:forEach var="t" items="${twoModules}" varStatus="i">
	           		 <!-- 只有这种写法，才能将modelId动态塞入role对象的models属性数组中去 -->
	              	 <input type="checkbox" class="two" value="${t.moduleId}" data="${t.parentId}"  name="modules[${oneModulesSize+i.index}].moduleId">${t.moduleName}
	              </c:forEach>
	            </div>
	        </div>
		</form>
	</div>
</body>
</html>