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
    <title>年终总结报告系统-我的自评列表页面</title>
    
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
                    <h1 class="page-header2">我的自评</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <c:if test="${empty assessmentList}">
	               <div class="alert alert-warning"><img src="../dist/images/report1.png" />您没有填写任何我的自评，请进行填写我的自评！ </div>
                </c:if>
                <c:if test="${not empty assessmentList}">
                  <div class="alert alert-info"><img src="../dist/images/report1.png" />我一共写了${fn:length(assessmentList)}个自评</div>
			      <c:forEach items="${assessmentList }" var="assessment">
				    <div class="col-lg-3 col-md-6">
				       <div class="panel panel-info">
				       		<!-- 头部 -->
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                     <img  src="../dist/images/sales.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">
	                                   		${assessment.ASSESSMENT_YEAR}年-我的自评
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                        <!-- 状态及操作栏 -->
                            <div class="panel-footer">
                               	<span class="pull-left"><a href="self?op=toAssessmentDetail&id=${assessment.SELF_ASSESSMENT_ID }" >查看<i class="fa fa-search"></i></a></span>
	                               	<c:if test="${assessment.STATUS==1011}">
                               			<span class="pull-right" style="color:#CC0033">待评分</span>
	                               	</c:if>
	                               	<c:if test="${assessment.STATUS==1010}">
	                               		<span class="pull-right" style="color:#286090">已评分</span>
	                               	</c:if>
                               	</span>
                                <div class="clearfix"></div>
                            </div>
	                    </div>
	                </div>
	            </c:forEach>
              </c:if>
            </div>
            <c:if test="${hasAssessment==false}">
	            <div class="row">
	                <div class="alert alert-info"><img src="../dist/images/write.png" />您今年还未填写我的自评！</div>
	            </div>
	            <div class="row">
	              <div class="col-lg-4">
	                    <div class="panel panel-warning">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                     <img  src="../dist/images/sales.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">${currentYear}年-我的自评</div>
	                                </div>
	                            </div>
	                        </div>
	                        <a href="self?op=toMyValue">
	                            <div class="panel-footer">
	                                <span class="pull-left">进入填写</span>
	                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                                <div class="clearfix"></div>
	                            </div>
	                        </a>
	                    </div>
	                </div>
	            </div>
	            <!-- /.row -->
	        </c:if>
	        <c:if test="${hasAssessment==true}">
	        	<div class="row">
	                <div class="alert alert-info"><img src="../dist/images/write.png" />您今年已经填写了我的自评！</div>
	            </div>
	        </c:if>
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
