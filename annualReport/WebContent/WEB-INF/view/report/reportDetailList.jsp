<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>年终总结报告系统-总结详情列表页面</title>
    
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core CSS -->
    <link href="../vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">


    <link href="../plugin/bootstrap-table/bootstrap-table.css" rel="stylesheet" type="text/css">
    
    
     <!--[if lt IE 9]>
        <script src="。。/plugin/html5shiv.js"></script>
        <script src="。。/plugin/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">
    
        <%@include file="../common/head.jsp"%>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">总结详情列表</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="alert alert-info">
                	<img src="../dist/images/report1.png" />
                	<c:if test="${not empty user}">${user.sName }共提交了${fn:length(reportList)}个报告</c:if>
                    <c:if test="${empty user}">他共提交了${fn:length(reportList)}个报告</c:if>                                                             
                </div>
	            <c:forEach items="${reportList}" var="report">
		        <div class="col-lg-3 col-md-6">
                    <div class="panel panel-warning">
                        <div class="panel-heading">
           					<c:if test="${report.TYPE =='sale'}">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                     <img  src="../dist/images/sales.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">
	                                   		${report.REPORT_YEAR}年-销售类
	                                    </div>
	                                    <div></div>
	                                </div>
	                            </div>
	                         </c:if>
	                         <c:if test="${report.TYPE =='tec'}">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                     <img  src="../dist/images/tec.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">
	                                   		${report.REPORT_YEAR}年-技术类
	                                    </div>
	                                    <div></div>
	                                </div>
	                            </div>
	                         </c:if>
	                         <c:if test="${report.TYPE =='administration'}">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                     <img  src="../dist/images/humans.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">
	                                   		${report.REPORT_YEAR}年-管理类、行政类
	                                    </div>
	                                    <div></div>
	                                </div>
	                            </div>
	                         </c:if>
                        </div>
                        <div class="panel-footer">
                        	<c:if test="${report.STATUS==2000}">
                            	<span class="pull-left"><a target="_blank" href="rs?op=toReportDetail&id=${report.ID }" >查看<i class="fa fa-search"></i></a></span>
                            	<span class="pull-right" style="color:#286090">已提交</span>
                        	</c:if>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
	            </c:forEach>
            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../vendor/metisMenu/metisMenu.min.js"></script>
</body>

</html>
