<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示所有的用户</title>
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
		var modal = showNormalLay("修改用户信息",$("#FormLay"),function(){
			// 修改表单提交地址，非空检验后提交表单
			$("#modal2 form").attr("action","${ctx}/system/user/update.do");
			if(checkForm()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		// 回显数据
		var uid = $(this).attr("value");
		$.post("${ctx}/system/rest/findByUserId.do","userId="+uid,function(r){
			$("#modal2 form :text[name=userName]").val(r.data.userName);
			$("#modal2 form :text[name=remark]").val(r.data.remark);
			$("<input type='hidden' name='userId' value='"+r.data.userId+"'>").appendTo($("#modal2 form"));
			/*回显角色 */
			$("#modal2 #role").find("option").each(function(index,obj){
				if(r.data.role.roleId==$(obj).val()){
					$(obj).prop("selected",true);
				}
			});
		},"json"); 
		checkUserName();
	});
	// 添加弹出层
	$("#add").click(function() {
		var modal = showNormalLay("添加用户信息",$("#FormLay"),function(){
			// 修改表单提交地址，非空检验后提交表单
			$("#modal2 form").attr("action","${ctx}/system/user/add.do");
			if(checkForm()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		checkUserName();
	});
	// 上传头像弹出层
	$(".upload").click(function() {
		var modal = showNormalLay("上传头像",$("#uploadFormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/system/user/uploadFace.do");
			if(checkFace()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("100px");
		modal.setWidth("600px");
		var uid = $(this).attr("value");
		$("<input type='hidden' name='userId' value='"+uid+"'>").appendTo($("#modal2 form"));
	});
})
//检查用户名是否存在，保证用户名的唯一性
function checkUserName(){
	var userNameInput = $("#modal2 form :text[name=userName]");
	//ajax请求数据库是否存在该用户
	userNameInput.blur(function(){
		$.post("${ctx}/system/rest/findByUserName.do","userName="+userNameInput.val(),function(r){
			if(r.data != null){
				userNameInput.val("");
				userNameInput.select();
				swal("OMG!", "该用户名已存在，请更换一个!", "error");
			}
		},"json");  
	});
}
// 弹出层表单非空校验
function checkForm(){
	var userName = $("#modal2 form :text[name=userName]").val();
	if(userName==null||userName==''){
		swal("OMG!", "用户名不能为空!", "error");
		return false;
	}
	return true;
}
//校验上传文件是否为图片格式   
function checkFace(){    
   var face = $("#modal2 #userFace").val();    
   if("" == face){    
       swal("OMG!", "请选择文件!", "error");
       return false;    
   }    
   if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(face)){   
	   swal("OMG!", "请选择图片格式的文件！", "error");
       return false;    
   }    
   return true;    
} 
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除用户", 
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
				param += "userIds="+o.value+"&";
			}
		});
		if(isCkecked){
			location = "${ctx}/system/user/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*删除一条记录*/
function doDelete(uid){
swal({
	title: "删除用户", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/system/user/delete.do?userIds="+uid;
	});
}
/*重置用户密码*/
function doResetPwd(uid){
	swal({
		title: "重置密码", 
		text: "你确定重置密码?", 
		type: "warning",
		showCancelButton: true,
		closeOnConfirm: false,
		confirmButtonText: "确定",
		confirmButtonColor: "#ec6c62"
		}, function() {
			location = "${ctx}/system/user/updatePwd.do?userId="+uid;
		});
}
/*复选框联动*/
function check(isChecked){
	$('.box').each(function(i,o){
		$(o).prop("checked",isChecked);
	});
}
/*切换状态*/
function changeState(uid){
swal({
	title: "切换用户状态", 
	text: "你确定切换用户状态?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/system/user/changeState.do?userId="+uid;
	});
}
</script>
</head>
<body>
	<div class="tip">
		温馨提示:用户冻结了就不能进行登录了!
	</div>
	<div class="contact">
		<!-- 表单区,根据查询条件筛选列表数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/system/user/showAllUser.do" 
			method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
			<fieldset>
            <div class="row">
              <div class="col-md-4">
              	<div class="form-group">
		            <label class="control-label col-md-3">用户名</label>
		            <div class="col-md-8">
		              <input class="form-control" name="userName" value="${pages.bean.userName}" type="text">
		            </div>
            	</div>
              </div>
              <div class="col-md-3">
              	<div class="form-group">
		            <label class="control-label col-md-3">角色名</label>
		            <div class="col-md-7">
		           		<select name="role.roleId" class="form-control">
							<option value="" selected="selected">----请选择----</option>
							<c:forEach var="r" items="${roles}">
								<c:if test="${pages.bean.role.roleId == r.roleId}">
									<option selected="selected" value="${r.roleId}">${r.roleName}</option>
								</c:if>
								<c:if test="${pages.bean.role.roleId != r.roleId}">
									<option value="${r.roleId}">${r.roleName}</option>
								</c:if>
							</c:forEach>
						</select>
		            </div>
          		</div>
              </div>
              <div class="col-md-3">
              	<div class="form-group">
		            <label class="control-label col-md-5">用户状态</label>
		            <div class="col-md-7">
		           		<select name="userState" class="form-control">
							<option value="" selected="selected">---请选择---</option>
							<c:if test="${pages.bean.userState == 0}">
								<option value="0" selected="selected">激活</option>
							</c:if>
							<c:if test="${pages.bean.userState != 0}">
								<option value="0">激活</option>
							</c:if>
							<c:if test="${pages.bean.userState == 1}">
								<option value="1" selected="selected">冻结</option>
							</c:if>
							<c:if test="${pages.bean.userState != 1}">
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
			<span class="title">所有用户列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th><input type="checkbox" onclick="check(this.checked)"></th>
						<th>序号</th>
						<th>头像</th>
						<th>用户名</th>
						<th>角色名</th>
						<th>创建时间</th>
						<th>最后修改时间</th>
						<th>描述</th>
						<th>用户状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="u" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td><input type="checkbox" class="box" value="${u.userId}"></td>
							<td>${i.count}</td>
							<td>
								<!--img图片不存在时设置默认图片,通过虚拟路径(/faces)引用本地图片 -->
								<img width="34px" height="34px" src="/faces/${u.userFace}" onerror="this.src='${ctx}/images/male.jpg;this.onerror=null'"/>
							</td>
							<td>${u.userName}</td>
							<td>${u.role.roleName}</td>
							<td>${u.createTime}</td>
							<td>${u.updateTime}</td>
							<td>${u.remark}</td>
							<!-- 状态切换区 -->
							<td>
								<c:if test="${u.userState==0}">
									<a href="javascript:;" onclick="changeState(${u.userId});" class="label label-info state1">激活</a>
								</c:if>
								<c:if test="${u.userState==1}">
									<a href="javascript:;" onclick="changeState(${u.userId});" class="label label-danger state2">冻结</a>
								</c:if>
							</td>
							<td>
								<a href="javascript:;" class="update btn btn_5 btn-xs btn-info" value="${u.userId}">修改</a>
								<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger" onclick="doDelete('${u.userId}');">删除</a>
								<a href="javascript:;" class="upload btn btn_5 btn-xs btn-info" value="${u.userId}">上传头像</a>
								<a href="javascript:;" class="btn btn_5 btn-xs btn-info" onclick="doResetPwd('${u.userId}');">重置密码</a>
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
	            <label class="control-label col-md-3">用户名称</label>
	            <div class="col-md-8">
	              <input class="form-control" name="userName" type="text">
	            </div>
            </div>
            <div class="form-group">
	            <label class="control-label col-md-3">分配角色</label>
	            <div class="col-md-8">
	           		<select name="role.roleId" id="role" class="form-control">
						<c:forEach var="r" items="${roles}">
							<option value="${r.roleId}">${r.roleName}</option>
						</c:forEach>
					</select>
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
	<!-- 上传头像弹出层表单 -->
	<div id="uploadFormLay" style="display: none">
		<form class="form-horizontal" method="post" enctype="multipart/form-data">
			<div class="form-group" style="margin-top:30px;">
	            <label class="control-label col-md-2">上传头像</label>
	            <div class="col-md-9">
	              <div class="fileupload fileupload-new" data-provides="fileupload">
	                <div class="input-group">
	                  <div class="form-control">
	                    <i class="icon-file fileupload-exists"></i><span class="fileupload-preview"></span>
	                  </div>
	                  <div class="input-group-btn">
	                    <a class="btn btn-default fileupload-exists" data-dismiss="fileupload" href="#">移除</a><span class="btn btn-default btn-file"><span class="fileupload-new">选择</span><span class="fileupload-exists">更改</span><input type="file" id="userFace" name="file"></span>
	                  </div>
	                </div>
	              </div>
	            </div>
          </div>
		</form>
	</div>
</body>
</html>