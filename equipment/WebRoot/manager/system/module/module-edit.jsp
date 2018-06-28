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
				<c:set var="m" value="${module}" />
				<input type="hidden" id="moduleId" name="moduleId" value="${m.moduleId}" />
				<div class="layui-form-item">
					<label for="moduleName" class="layui-form-label">
						<span class="x-red">*</span>模块名
					</label>
					<div class="layui-input-inline">
						<input type="text" id="moduleName" name="moduleName" value="${m.moduleName}" lay-verify="moduleName" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label for="userPwd" class="layui-form-label">
						<span class="x-red">*</span>菜单级别
					</label>
					<div class="layui-input-inline">
						<select name="moduleLevel" id="moduleLevel" lay-verify="moduleLevel" lay-search="">
							<option value="">请选择级别</option>
							 <c:if test="${m.moduleLevel==1}">
							 	<option value="1" selected="selected">模块级(一级)</option>
							 	<option value="2">页面级(二级)</option>
							 </c:if>
							 <c:if test="${m.moduleLevel==2}">
								 <option value="1">模块级(一级)</option>
	                             <option value="2" selected="selected">页面级(二级)</option>
                             </c:if>
                             <c:if test="${m.moduleLevel==null}">
	                             <option value="1">模块级(一级)</option>
	                             <option value="2">页面级(二级)</option>
                             </c:if>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label for="roleId" class="layui-form-label">
						<span class="x-red"></span>上一级
					</label>
					<div class="layui-input-inline">
						<select name="parentId" id="parentId" lay-verify="parentId" lay-search="">
							<option value="0">请选择父级模块</option>
							<c:forEach var="ms" items="${modules}">
								<c:if test="${m.parentId == ms.moduleId}">
									<option selected="selected" value="${ms.moduleId}">${ms.moduleName}</option>
								</c:if>
								<c:if test="${m.parentId != ms.moduleId}">
									<option value="${ms.moduleId}">${ms.moduleName}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="layui-form-mid layui-word-aux">
						<span class="x-red">*</span>父级模块
					</div>
				</div>				
				<div class="layui-form-item">
					<label for="moduleName" class="layui-form-label">
						<span class="x-red">*</span>请求动作URL
					</label>
					<div class="layui-input-inline">
						<input type="text" id="actionUrl" name="actionUrl" value="${m.actionUrl}" lay-verify="actionUrl" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label for="moduleName" class="layui-form-label">
						<span class="x-red"></span>图标
					</label>
					<div class="layui-input-inline">
						<input type="text" id="icon" name="icon" value="${m.icon}" lay-verify="icon" autocomplete="off" class="layui-input">
					</div>
					<div class="layui-form-mid layui-word-aux">
						<span class="x-red">*</span>模块级(一级)使用
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">
						<span class="x-red">*</span>是否可分配
					</label>
					<div class="layui-input-inline">
						<c:if test="${m.isAllowed == 0 || m.isAllowed == null}">
							<input type="radio" name="isAllowed" value="0" title="可以" checked>
							<input type="radio" name="isAllowed" value="1" title="不可以">
						</c:if>
						<c:if test="${m.isAllowed == 1}">
							<input type="radio" name="isAllowed" value="0" title="可以">
							<input type="radio" name="isAllowed" value="1" title="不可以" checked>
						</c:if>
					</div>
				</div>
				<div class="layui-form-item">
					<label for="remark" class="layui-form-label">
						<span class="x-red"></span>备注
					</label>
					<div class="layui-input-inline">
						<input type="text" id="remark" name="remark" lay-verify="remark" autocomplete="off" value="${m.remark}" class="layui-input">
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
						moduleName : function(value) {
							if (value.length < 1) {
								return '模块名至少得写吧';
							}
						},
						moduleLevel : function(value) {
							//alert("value值"+value);
							if (value == '') {
								return '模块级别选一个吧';
							}else if (value == 1) {
								var parentId=$("#parentId option:selected").val();
								//alert("value=1:"+parentId);
				                if(parentId != 0){					            
								return '一级模块没有父级模块！！！';
				              }									
						   }else{
								var parentId=$("#parentId option:selected").val();
								//alert("value=2:"+parentId);
				                if(parentId == 0){					            
								return '父级模块选一个吧';
				              }												
						   }
							
						},
						actionUrl : function(value) {
							if (value < 1) {
								return '请求动作URL得写一个吧';
							}
						}
					});

					//监听提交
					form.on('submit(add)',function(data) {
						var moduleId = $("#moduleId").val();
					if (moduleId == '') {//添加
						$.ajax( {
									url : "${ctx}/system/module/add",
									type : "post",
									data : JSON.stringify(data.field),
									contentType : "application/json; charset=utf-8",//后台接受标准的json格式数据
									dataType : "json",
							success : function(result) {
							if (result.code == 0) {
								layer.alert(result.msg,{icon : 6},function() {
									window.parent.location.reload();
									var index = parent.layer.getFrameIndex(window.name);// 获得frame索引
									parent.layer.close(index);//关闭当前frame
								});
							} else {
										layer.alert(result.msg,{icon : 5});
									}
							}
								});
					} else {//修改
						 $.ajax( {
									url : "${ctx}/system/module/update",
									type : "post",
									data : JSON.stringify(data.field),
									contentType : "application/json; charset=utf-8",//后台接受标准的json格式数据
									dataType : "json",
						success : function(result) {
						if (result.code == 0) {
							layer.alert(result.msg,{icon : 6},function() {
								window.parent.location.reload();
								var index = parent.layer.getFrameIndex(window.name);// 获得frame索引
								parent.layer.close(index);//关闭当前frame
						});
					} else {
							layer.alert(result.msg,{icon : 5});
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