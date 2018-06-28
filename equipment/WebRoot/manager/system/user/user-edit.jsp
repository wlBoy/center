<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>讯牛后台</title>
	</head>
	<body>
		<div class="x-body">
			<form class="layui-form">
				<input type="hidden" id="userId" name="userId" value="${u.userId}" />
				<div class="layui-form-item">
					<label for="userName" class="layui-form-label">
						<span class="x-red">*</span>用户名
					</label>
					<div class="layui-input-inline">
						<input type="text" id="userName" name="userName"
							value="${u.userName}" lay-verify="userName" autocomplete="off"
							class="layui-input">
					</div>
					<div class="layui-form-mid layui-word-aux">
						<span class="x-red">*</span>将会成为您唯一的登入名
					</div>
				</div>
				<div class="layui-form-item">
					<label for="roleId" class="layui-form-label">
						<span class="x-red">*</span>角色
					</label>
					<div class="layui-input-inline">
						<select name="roleId" id="roleId" lay-verify="roleId" lay-search="">
							<option value="">请选择角色</option>
							<c:forEach var="r" items="${roles}">
								<c:if test="${u.roleId == r.roleId}">
									<option selected="selected" value="${r.roleId}">${r.roleName}</option>
								</c:if>
								<c:if test="${u.roleId != r.roleId}">
									<option value="${r.roleId}">${r.roleName}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label for="userPwd" class="layui-form-label">
						<span class="x-red">*</span>密码
					</label>
					<div class="layui-input-inline">
						<input type="text" id="userPwd" value="${u.userPwd}"
							name="userPwd" lay-verify="userPwd" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">
						<span class="x-red">*</span>状态
					</label>
					<div class="layui-input-inline">
						<c:if test="${u.userState == 0 || u.userState == null}">
							<input type="radio" name="userState" value="0" title="激活状态"
								checked>
							<input type="radio" name="userState" value="1" title="冻结状态">
						</c:if>
						<c:if test="${u.userState == 1}">
							<input type="radio" name="userState" value="0" title="激活状态">
							<input type="radio" name="userState" value="1" title="冻结状态"
								checked>
						</c:if>
					</div>
				</div>
				<div class="layui-form-item">
					<label for="remark" class="layui-form-label">
						<span class="x-red"></span>备注
					</label>
					<div class="layui-input-inline">
						<input type="text" id="remark" name="remark" lay-verify="remark"
							autocomplete="off" value="${u.remark}" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label for="L_repass" class="layui-form-label">
					</label>
					<button class="layui-btn" lay-filter="add" lay-submit="">
						提交
					</button>
				</div>
			</form>
		</div>
		<script>
	layui.use( [ 'form', 'layer' ], function() {
		$ = layui.jquery;
		var form = layui.form, layer = layui.layer;
		//自定义验证规则
			form.verify( {
				userName : function(value) {
					if (value.length < 1) {
						return '用户名至少得写吧';
					}
				},
				userPwd : function(value) {
					if (value.length < 1) {
						return '密码至少得写吧';
					}
				},
				roleId : function(value) {
					if (value < 1) {
						return '角色选一个吧';
					}
				}
			});
			//监听提交
			form.on('submit(add)', function(data) {
				var userId = $("#userId").val();
				if (userId == '') {//添加
						$.ajax( {
							url : "${ctx}/system/user/add",
							type : "post",
							data : JSON.stringify(data.field),
							contentType : "application/json; charset=utf-8",//后台接受标准的json格式数据
							dataType : "json",
							success : function(result) {
								if (result.code == 0) {
									layer.alert(result.msg, {
										icon : 6
									}, function() {
										window.parent.location.reload();
										var index = parent.layer
												.getFrameIndex(window.name);// 获得frame索引
											parent.layer.close(index);//关闭当前frame
										});
								} else {
									layer.alert(result.msg, {
										icon : 5
									});
								}
							}
						});
					} else {//修改
						$.ajax( {
							url : "${ctx}/system/user/update",
							type : "post",
							data : JSON.stringify(data.field),
							contentType : "application/json; charset=utf-8",//后台接受标准的json格式数据
							dataType : "json",
							success : function(result) {
								if (result.code == 0) {
									layer.alert(result.msg, {
										icon : 6
									}, function() {
										window.parent.location.reload();
										var index = parent.layer
												.getFrameIndex(window.name);// 获得frame索引
											parent.layer.close(index);//关闭当前frame
										});
								} else {
									layer.alert(result.msg, {
										icon : 5
									});
								}
							}
						});
					}
					return false;
				});
		});
</script>
	</body>
</html>