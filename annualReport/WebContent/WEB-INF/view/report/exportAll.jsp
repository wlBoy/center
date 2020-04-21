<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
    <title>年终总结报告系统-数据汇总页面</title>
	<!-- Bootstrap Core CSS -->
	<link href="vendor/bootstrap/css/bootstrap.css" rel="stylesheet">
	<!-- MetisMenu CSS -->
	<link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
	<!-- Custom CSS -->
	<link href="dist/css/sb-admin-2.css" rel="stylesheet">
	<!-- Custom Fonts -->
	<link href="vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
	<link href="plugin/bootstrap-table/bootstrap-table.css" rel="stylesheet" type="text/css">
	<script src="vendor/jquery/jquery.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<!-- Metis Menu Plugin JavaScript -->
	<script src="vendor/metisMenu/metisMenu.min.js"></script>
	<!-- Custom Theme JavaScript -->
	<script src="dist/js/sb-admin-2.js"></script>
	 <!-- bootstrap table -->
    <script src="plugin/bootstrap-table/bootstrap-table.js"></script>
    <script src="plugin/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
</head>
<body>
	<div id="wrapper">
	    <%@include file="../common/head.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header4">数据汇总</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                                                                                     查询条件
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
						    <div class="col-lg-12">
			                      <div class="operatorBtnDiv" style="text-align:center;margin-bottom:30px;margin-top:20px;">
			                     		 <p style="font-size:14px;color:#0E69AF;">说明：导出报告按钮用于导出本部门人员的年终总结报告，内容包括：岗位职责，问题与不足，明年目标，产品建议，项目建议，管理建议等；导出评分按钮用于导出本部门人员的评分情况，内容包括：个人评分，部门评分等</p>
			                      </div>
			                      <form id="myForm" class="form-horizontal" method="post">
										<div class="form-group" style="margin-top: 15px;">
											<label class="control-label col-sm-1" for="search_year" style="margin-left:38%">年份</label>
											<div class="col-sm-1">
												<select class="form-control" name="searchYear">
													<option value="">不限</option>
													<c:forEach var="map" items="${selectYearMap}">
														<option value="${map.key}" <c:if test="${currentYear == map.key}">selected="selected"</c:if>>${map.value}</option>
													</c:forEach>
												</select>
											</div>
											<div style="clear:both"></div>
											<div class="operatorBtnDiv" style="text-align:center;margin-bottom:20px;margin-top:30px;">
					                     		 <button type="button"  id="exportReport" class="btn btn-primary"
					                              style="width: 150px">导出报告</button>&nbsp;&nbsp;
					                     		 <button type="button"  id="exportAssessment" class="btn btn-primary"
					                              style="width: 150px">导出评分</button>
											</div>
										</div>
								  </form>
			                </div>
			           </div>
			        </div>
			    </div>
           </div>
		</div>
	</div>
	<script type="text/javascript">
	// 导出报告
    $('#exportReport').click(function () {
    	$("#myForm").attr("action","/rs?op=exportReport");
    	$("#myForm").submit();
    });
	// 导出评分
    $('#exportAssessment').click(function () {
    	$("#myForm").attr("action","/self?op=exportAll");
    	$("#myForm").submit();
    });
	</script>
</body>
</html>
