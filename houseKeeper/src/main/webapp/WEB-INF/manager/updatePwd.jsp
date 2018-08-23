<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>修改密码</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/css/demo.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/passwordStrength-min.js"></script>
<!-- 提示框插件 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/plugins/SweetAlert/sweetalert.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/SweetAlert/sweetalert.min.js"></script>
<style type="text/css">
	.sa-button-container{
		text-align:center; 
	}
</style>
<script type="text/javascript">
function checkOldPwd(obj){
	var userId = '${user.userId}';
	var newPwd = $(obj).val();
	$.get("${pageContext.request.contextPath}/system/rest/checkOldPwd.do","userId="+userId+"&newPwd="+newPwd,function(result){
		if(result.code == 0){
			$(obj).val("");
			$(obj).select();
			swal("OMG!", "新密码与旧密码一致，请更换!", "error");
		}
	},"text");  
}
$(function(){
	$(".registerform").Validform({
		tiptype:2,
		usePlugin:{
			passwordstrength:{
				minLen:6,//设置密码长度最小值，默认为0;
				maxLen:18,//设置密码长度最大值，默认为30;
				trigger:function(obj,error){
					//该表单元素的keyup和blur事件会触发该函数的执行;
					//obj:当前表单元素jquery对象;
					//error:所设密码是否符合验证要求，验证不能通过error为true，验证通过则为false;
					if(error){
						obj.parent().next().find(".Validform_checktip").show();
						obj.parent().next().find(".passwordStrength").hide();
					}else{
						obj.parent().next().find(".Validform_checktip").hide();
						obj.parent().next().find(".passwordStrength").show();	
					}
				}
			}
		}
	});
})
</script>
</head>

<body>
	${msg}
	<!-- 提示信息 -->
	<c:if test="${not empty sessionScope.msg}">
		<c:remove var="msg" scope="session"/>
	</c:if>
	<div class="main">
		<div class="wraper">
			<h3 class="blue">修改密码</h3>
			<form class="registerform" method="post" action="${pageContext.request.contextPath}/system/user/updatePwd.do">
				<table width="100%" style="table-layout: fixed;">
					<input type="hidden" name="userId" value="${user.userId}" />
					<tr>
						<td class="need" style="width: 10px;">*</td>
						<td style="width: 70px;"><span style="margin-left:11px;">新密码：</span></td>
						<td style="width: 210px;"><input type="password" value=""
							name="newpassword" onblur="checkOldPwd(this);" class="inputxt"
							nullmsg="请设置新密码！" plugin="passwordStrength" datatype="*6-18"
							errormsg="密码至少6个字符,最多18个字符！" /></td>
						<td>
							<div class="Validform_checktip">密码至少6个字符,最多18个字符！</div>
							<div class="passwordStrength" style="display: none;">
								<b>密码强度：</b> <span>弱</span><span>中</span><span class="last">强</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="need">*</td>
						<td>确认密码：</td>
						<td><input type="password" value="" name="repassword"
							class="inputxt" nullmsg="请确认新密码！" recheck="newpassword"
							datatype="*6-18" errormsg="两次输入的密码不一致！" /></td>
						<td><div class="Validform_checktip"></div></td>
					</tr>
					<tr>
						<td class="need"></td>
						<td></td>
						<td colspan="2" style="padding: 10px 0 18px 0;"><input
							type="submit" value="提 交" /><span style="margin-left:15px;"></span><input type="reset" value="重 置" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>