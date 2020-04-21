<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公共导航头页面</title>
</head>
<body>
	<nav class="navbar navbar-static-top" role="navigation"
		style="margin-bottom: 0">
		<!-- 顶部信息栏 -->
		<div class="navbar-header">
			<img class="leftlogo" src="../vendor/images/logo.png" style="position: relative;top:2px;"/> <span
				class="navbar-brand" href="rs?op=toIndex" style="position: relative;top:3px;">年终总结报告系统</span>
		</div>
		<ul class="nav navbar-top-links navbar-right">
			<!-- 所属部门  -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#" style="color:white;"> <i class="fa fa-home fa-fw"></i>${userInSession.depName}</i>
			</a></li>
			<!-- 用户名 -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#" style="color:white;"> <i class="fa fa-user fa-fw"></i>${userInSession.sName}</i>
			</a></li>
		</ul>
		<!-- 左侧导航栏 -->
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul id="nav" class="nav" id="side-menu">
					<li><a href="rs?op=toIndex"><img
							src="../dist/images/icon1.png" /> 我的总结</a></li>
					<li><a href="self?op=toMyValueList"><img
							src="../dist/images/icon2.png" /> 我的自评</a></li>
					<!-- 部门领导或兼职部门的且有权限的 -->
					<c:if test="${not empty orgs or not empty userInSession.role}">
						<li><a href="rs?op=toReportList"><img
								src="../dist/images/icon4.png" /> 部门总结</a></li>
						<li><a href="self?op=toAssessmentList"><img
								src="../dist/images/icon3.png" /> 部门评分</a></li>
						<li><a href="rs?op=toExportAll"><img
								src="../dist/images/icon5.png" /> 数据汇总</a></li>
					</c:if>
					<li><a href="vote?op=toVoteList"><img
							src="../dist/images/icon6.png" /> 年度评选</a></li>
					<!-- 特殊角色，全部开放的权限，相关于超级管理员，人事和维护人员拥有  -->
					<c:if test="${userInSession.role=='all'}">
						<li>
						<a href="#"><img
										src="../dist/images/icon7.png" /> 评选汇总<span class="fa arrow"></span></a>
	                            <ul class="nav nav-second-level collapse in">
	                                <li>
	                                    <a class="active" href="vote?op=toSumVoteByPrize">票数汇总</a>
	                                </li>
	                                <li>
	                                    <a class="active" href="vote?op=toSumVoteByWeight">排名汇总</a>
	                                </li>
	                            </ul>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	<script type="text/javascript">
		var url = window.location.href;
		$("#nav a").each(function(){
		    $(this).removeClass("active");
		    if(url.indexOf($(this).attr("href")) >= 0){
		    	$(this).addClass("active");
		    }
		 });
	</script>
</body>
</html>