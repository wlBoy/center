<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>讯牛后台</title>
		<%@ include file="/common/core.jsp"%>
		<!-- zree 树形菜单  -->
        <link rel="stylesheet" href="${ctx}/js/zTree/css/zTreeStyle/zTreeStyle.css">
        <script src="${ctx}/js/zTree/js/jquery.ztree.core.min.js"></script>
        <script src="${ctx}/js/zTree/js/jquery.ztree.excheck.min.js"></script>
	</head>

	<body>
		<div class="x-body">
			<form class="layui-form">
				<c:set var="r" value="${role}" />
				<input type="hidden" id="roleId" name="roleId" value="${r.roleId}" />
				<div class="layui-form-item">
					<label for="userName" class="layui-form-label">
						<span class="x-red">*</span>角色名
					</label>
					<div class="layui-input-inline">
						<input type="text" id="roleName" name="roleName" value="${r.roleName}" lay-verify="roleName" autocomplete="off" class="layui-input">
					</div>
				</div>
				
				<div class="layui-form-item">
					<label for="remark" class="layui-form-label">
						<span class="x-red"></span>备注
					</label>
					<div class="layui-input-inline">
						<input type="text" id="remark" name="remark" lay-verify="remark" autocomplete="off" value="${r.remark}" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label for="power" class="layui-form-label">
						<span class="x-red">*</span>权限
					</label>	
					<div class="layui-input-inline">			
						<ul id="treeMenu" class="ztree"></ul>
					</div>
				</div>				
				<div class="layui-form-item">
					<label for="L_repass" class="layui-form-label">
					</label>
					<button class="layui-btn"  lay-filter="add" lay-submit="">
						提交
					</button>
				</div>
			</form>
		</div>
		<script>	
		var setting = {
				check: {
					enable: true,
					chkDisabledInherit: true
				},
				view: {
					showIcon: false
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
		$.fn.zTree.init($("#treeMenu"), setting, ${nodeList}); //树
		layui.use( [ 'form', 'layer' ], function() {
			$ = layui.jquery;
			var form = layui.form, layer = layui.layer;
			//自定义验证规则
				form.verify( {
					roleName : function(value) {
						if (value.length < 1) {
							return '角色名至少得写吧';
						}
					}
				});

				//监听提交
				form.on('submit(add)',function(data) {
					var roleId = $("#roleId").val();
					var treeObj=$.fn.zTree.getZTreeObj("treeMenu");
					var roleName=$("#roleName").val();
				    var remark=$("#remark").val();
			        var nodes=treeObj.getCheckedNodes(true);
			     	var ids=[];
				 for(var i=0;i<nodes.length;i++){
				           ids[i] = nodes[i].id; 				   
				 }
				if (roleId == '') {//添加
					$.post("${ctx}/system/role/add",{"roleName":roleName,"remark":remark,"ids":ids}, function(result){
						if(result.code==-1){
			        		layer.alert(result.msg, {icon : 5});      
				        }else{
				            layer.alert(result.msg, {icon : 6},function(){
				            window.parent.location.reload();
							var index = parent.layer.getFrameIndex(window.name);// 获得frame索引
							parent.layer.close(index);//关闭当前frame      
					   });
				      }		   	
		            });

				} else {//修改
					$.post("${ctx}/system/role/update",{"roleId":roleId,"roleName":roleName,"remark":remark,"ids":ids}, function(result){
						if(result.code==-1){
			        		layer.alert(result.msg, {icon : 5});      
				        }else{
				            layer.alert(result.msg, {icon : 6},function(){
				            window.parent.location.reload();
							var index = parent.layer.getFrameIndex(window.name);// 获得frame索引
							parent.layer.close(index);//关闭当前frame      
					   });
				      }		   	
		            });
					 
	      }
		return false;
});
});
</script>

	</body>

</html>