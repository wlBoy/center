<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>管家婆后台首页</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport">
<style type="text/css">
.currenttime {
	width: 280px;
	height: 46px;
	line-height: 46px;
	color: #666;
}

.pull-left {
	line-height: 46px;
}
</style>
<script type="text/javascript">
	function doJump(actionUrl) {
		//在指定框架中跳转
		window.top.ifm.location = '${ctx}' + actionUrl;
	}
	//获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
    function getNowFormatDate() {
        var date = new Date();
        var year = date.getFullYear();//年
        var month = date.getMonth() + 1;//月
        var strDate = date.getDate();//日
        var hours = date.getHours();//时
        var minutes = date.getMinutes();//分
        var seconds = date.getSeconds();//秒
        var dayWeek = "星期" + "日一二三四五六".charAt(date.getDay());
        return year + "-" + p(month) + "-"
            + p(strDate) + " " + dayWeek + " " + p(hours) + ":"
            + p(minutes) + ":" + p(seconds);
    }

    /*小于10的补0*/
    function p(s) {
        return s < 10 ? '0' + s : s;
    }

    /*填充当前时间*/
    function fillDate() {
        $('.currenttime').text("当前时间为  : " + getNowFormatDate());
    }
	$(function() {
		/* 页面初始化时，ifm默认加载首页*/
		$("#ifm").attr("src","${ctx}/system/user/towelcome.do"); 
		/* 用户注销 */
		$('.logoff').click(function() {
		swal({
			title: "注销用户", 
			text: "您确定要注销?", 
			type: "warning",
			showCancelButton: true,
			closeOnConfirm: false,
			confirmButtonText: "确定",
			confirmButtonColor: "#ec6c62"
			}, function() {
				location = '${ctx}/system/user/logoff.do';
			});
		});
		/* 定时器每1s获取系统当前时间 */
		setInterval(fillDate, 1000);
	});
</script>
</head>
<body>
	<div class="modal-shiftfix">
		<!-- Navigation -->
		<div class="navbar navbar-fixed-top scroll-hide">
			<div class="container-fluid top-bar">
				<div class="pull-right">
					<ul class="nav navbar-nav pull-right">
						<li class="dropdown settings hidden-xs">
							<div class="dropdown-toggle currenttime"></div>
						</li>
						<li class="dropdown user hidden-xs"><a data-toggle="dropdown"
							class="dropdown-toggle" href="#"> <!--img图片不存在时设置默认图片,通过虚拟路径(/faces)引用本地图片 -->
								<img width="34px" height="34px" src="/faces/${user.userFace}"
								onerror="this.src='${ctx}/images/male.jpg;this.onerror=null'" />${user.userName}-${user.role.roleName}<b
								class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="${ctx}/system/user/toUpdatePwd.do" target="ifm"> <i
										 class="icon-key"></i>修改密码
								</a><a href="javascript:;" class="logoff"> <i
										class="icon-signout"></i>注销
								</a></li>
							</ul></li>
					</ul>
				</div>
				<div class="nav navbar-nav pull-left" style="font-size:16px;">管家婆后台管理系统</div>
			</div>
			<div class="container-fluid main-nav clearfix">
				<div class="nav-collapse">
					<ul class="nav">
						<li><a class="current" href="${ctx}/system/user/towelcome.do"
							target="ifm"><span aria-hidden="true" class="se7en-home"></span>首页</a>
						</li>
						<!-- 一级菜单循环显示-->
						<c:forEach var="om" items="${oneModules}">
							<li class="dropdown"><a data-toggle="dropdown" href="#"><span
									aria-hidden="true" class="se7en-forms"></span>${om.moduleName}<b
									class="caret"></b></a>
								<ul class="dropdown-menu">
									<!-- 循环所有的二级菜单，当内层循环到的parentId等于一级菜单循环的modelId时，即为该一级模块的子模块-->
									<c:forEach var="tm" items="${twoModules}">
										<c:if test="${tm.parentId==om.moduleId}">
											<li><a target="ifm" href="javascript:;"
												onclick="doJump('${tm.actionUrl}');">${tm.moduleName}</a></li>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<!-- End Navigation -->
		<div class="container-fluid main-content">
			<iframe width="100%" height="100%" frameborder="no" name="ifm"
				id="ifm" scrolling="auto"> </iframe>

		</div>
</body>
</html>