<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>管家婆后台登录界面</title>
		<meta
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
			name="viewport">
			<style type="text/css">
				.left{
					width:265px !important;
					float:left;
				}
				.right{
					float:right;
				}
				.right:hover{
					cursor:pointer;
				}
			</style>
		<script type="text/javascript">
		/* 获取cookie值并回显,实现记住账号功能 */
		$(function(){
	        //获取cookie的值,需导入jquery.cookie.js插件包
	        var userName = $.cookie("userName");
	        var userPwd = $.cookie("userPwd");
	        if(userName!=null&&userPwd!=null){
		        //将获取的值填充入输入框中
		        $("#userName").val($.cookie("userName"));
		        $("#userPwd").val($.cookie("userPwd")); 
		        $("#rememberMe").prop("checked",true);
	        }else{
		        $("#rememberMe").prop("checked",false);
	        }
	    });
		/* 切换验证码 */
		function changeCode(obj) {
			obj.src += "?r="+Math.random();
		}
		/* 登录校验 */
		function check(){
			var username = $(':text[name=userName]');
			var userpwd = $(':password[name=userPwd]');
			if (username.val() == '' || userpwd.val() == '' ) {
				swal("OMG!", "用户名或密码不能为空!", "error");
				return false;
			}else {
				return true;
			}
		}
		</script>
	</head>
	<body class="login2">
		<!-- Login Screen -->
		<div class="login-wrapper">
			<div class="login-container">
				<h3 style="margin-top: 150px; margin-bottom: 30px;">
					管家婆系统-后台登录
				</h3>
				<form action="${ctx}/system/user/login.do" method="post"
					onsubmit="return check();">
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="icon-envelope"></i>
							</span>
							<input class="form-control" type="text" name="userName" id="userName">
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="icon-lock"></i>
							</span>
							<input class="form-control" type="password" name="userPwd" id="userPwd">
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><i class="icon-key"></i>
							</span>
							<input class="form-control left" type="text" name="verifyCode">
							<img class="right" onclick="changeCode(this);" src="${ctx}/system/user/getVerifyCode.do">
						</div>
					</div>
					<!-- <div class="form-group">
				        <div class="text-left">
				          <label class="checkbox"><input type="checkbox" name="rememberMe" id="rememberMe"><span>记住我</span></label>
				        </div>
			        </div> -->
					<input type="submit" class="btn btn-lg btn-primary btn-block"
						value="登录">
				</form>
			</div>
		</div>
	</body>
</html>