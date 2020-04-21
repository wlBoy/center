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
    <title>年终总结报告系统-评选列表页面</title>
    
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
                    <h1 class="page-header6">年度评选</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
              	   <c:if test="${empty voteList}">
	               		<div class="alert alert-warning"><img src="../dist/images/report1.png" />您没有任何的评选记录，请进行年度评选！ </div>
                   </c:if>
                   <c:if test="${not empty voteList}">
		               <div class="alert alert-info"><img src="../dist/images/report1.png" />我一共投了${fn:length(voteList)}个投票</div>
		               <c:forEach items="${voteList}" var="vote">
	                   		<div class="col-lg-3 col-md-6">
			                    <div class="panel panel-default">
			                        <div class="panel-heading">
			                            <div class="row">
			                                <div class="col-xs-3">
			                                     <img  src="../dist/images/humans.png" />
			                                </div>
			                                <div class="col-xs-9 text-right">
			                                    <div>${vote.voteYear}年-年度评选</div>
			                                </div>
			                            </div>
			                        </div>
			                        <div class="panel-footer">
			                            <span class="pull-left"><a href="vote?op=toVoteDetail&id=${vote.id}" >查看<i class="fa fa-search"></i></a></span>
			                       		<!-- 有效 -->
		                            	<c:if test="${vote.ticketStatus==0}">
			                            	<span class="pull-right">已投票</span>
		                            	</c:if>
			                       		<!-- 无效 -->
		                            	<c:if test="${vote.ticketStatus==1}">
			                            	<span class="pull-right" style="color:#CC0033">无效票</span>
		                            	</c:if>
			                            <div class="clearfix"></div>
			                        </div>
			                    </div>
	                 	</div>
		             </c:forEach>
	             </c:if>
            </div>
            <!-- /.row -->
           <c:if test="${votedCurrentYear == false}">
	            <div class="row">
	                <div class="alert alert-info"><img src="../dist/images/write.png" />您今年还没有投票，请进入年度评选！</div>
	            </div>
           </c:if>
           <c:if test="${votedCurrentYear==true}">
	        	<div class="row">
	                <div class="alert alert-info"><img src="../dist/images/write.png" />您今年已经投过票了！</div>
	            </div>
	       </c:if>
           <c:if test="${votedCurrentYear == false}">
	           <div class="row">
	                <div class="col-lg-4">
	                    <div class="panel panel-info">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <img  src="../dist/images/tec.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div>${currentYear}年-年度评选</div>
	                                </div>
	                            </div>
	                        </div>
	                        <a href="javaScript:toVoteIndex();" >
	                            <div class="panel-footer">
	                                <span class="pull-left">进入填写</span>
	                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                                <div class="clearfix"></div>
	                            </div>
	                        </a>
	                    </div>
	                </div>
	            </div>
            </c:if>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <script type="text/javascript">
	 	// 转到年度评选的页面
		function toVoteIndex(){
			// 是否开启年度评选功能
			var enableVote = '${enableVote}';
			if(enableVote=='true'){
				location.href = "vote?op=toAnnualVote";
			}else{
				alert("今年的年度评选暂未开放或已关闭，请联系管理员!");
			}
		}
    </script>
</body>
</html>
