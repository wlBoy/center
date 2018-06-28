<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
	<title>水利设备管理系统后台登录界面</title>
</head>
<body class="login-bg">
    <div class="login">
        <div class="message">水利设备管理系统后台登录</div>
        <div id="darkbannerwrap"></div>
        <form method="post" class="layui-form">
            <input name="userName" lay-verify="userName" placeholder="用户名"  type="text"  class="layui-input" >
            <hr class="hr15">
            <input name="userPwd" lay-verify="userPwd" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login"  style="width:100%;" type="submit">
            <hr class="hr20" >
        </form>
    </div>
    <script>
    $(function  () {
        layui.use('form', function(){
          var form = layui.form;
          //自定义验证规则
          form.verify({
        	  userName: function(value){
              if(value.length < 1){
                return '用户名不能为空';
              }
            },
              userPwd: function(value){
                if(value.length < 1){
                  return '密码不能为空';
                }
              }
          });
          //监听提交
       form.on('submit(login)', function(data){  
        $.post("${ctx}/system/user/login",{ "userName": $("[name=userName]").val(), "userPwd": $("[name=userPwd]").val()},function(result){
        	if(result.code==-1){
        		layer.alert(result.msg, {icon : 5});        		            
             }else{
                location.href="${ctx}/system/module/menuList";
             }
          });
            return false;
          });        
        });
    })
    </script>
</body>
</html>
