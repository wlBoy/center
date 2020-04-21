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
    <title>年终总结报告系统-系统首页</title>
    
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
                    <h1 class="page-header">我的总结</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <c:if test="${empty reportList}">
	               <div class="alert alert-warning"><img src="../dist/images/report1.png" />您没有填写任何年终总结报告，请按照您的岗位进行报告填写！ </div>
                </c:if>
                <c:if test="${not empty reportList}">
                  <div class="alert alert-info"><img src="../dist/images/report1.png" />我一共写了${fn:length(reportList)}个报告</div>
			      <c:forEach items="${reportList }" var="report">
				    <div class="col-lg-3 col-md-6">
				       <div class="panel panel-info">
				       		<!-- 头部 -->
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
		                                </div>
		                            </div>
	                            </c:if>
	                        </div>
	                        <!-- 状态及操作栏 -->
                            <div class="panel-footer">
                           		<!-- 已提交 -->
                            	<c:if test="${report.STATUS==2000}">
                                	<span class="pull-left"><a href="rs?op=toReportDetail&id=${report.ID }" >查看<i class="fa fa-search"></i></a></span>
                                	<span class="pull-right" style="color:#286090">已提交</span>
                            	</c:if>
                            	<!-- 未提交 -->
                            	<c:if test="${report.STATUS==9000}">
                                	<span class="pull-left" style="margin-right:15px;"><a href="rs?op=updateReport&id=${report.ID }" >修改<i class="fa fa-pencil"></i></a></span>
                               		<span class="pull-left" style="margin-right:15px;"><a href="javaScript:deleteReport('${report.ID }')">删除<i class="fa fa-times"></i></a></span>
                               		<span class="pull-left" style="margin-right:15px;"><a href="javaScript:submitReport('${report.ID }')">提交<i class="fa fa-check"></i></a></span>
                               		<span class="pull-right" style="color:#CC0033">未提交</span>
                            	</c:if>
                            	<!-- 已驳回 -->
                            	<c:if test="${report.STATUS==1010}">
                                	<span class="pull-left" style="margin-right:15px;"><a href="rs?op=updateReport&id=${report.ID }" >修改<i class="fa fa-pencil"></i></a></span>
                               		<span class="pull-left" style="margin-right:15px;"><a href="javaScript:submitReport('${report.ID }')">提交<i class="fa fa-check"></i></a></span>
                               		<span class="pull-right" style="color:#CC0033">已驳回</span>
                            	</c:if>
                                <div class="clearfix"></div>
                            </div>
	                    </div>
	                </div>
	            </c:forEach>
              </c:if>
            </div>
           <c:if test="${hasSale==false or hasTec==false or hasAdministration==false}">
	            <div class="row">
	                <div class="alert alert-info"><img src="../dist/images/write.png" />您今年可以按照您的岗位继续填写报告！</div>
	            </div>
           </c:if>
           <div class="row">
             <c:if test="${hasSale==false}">
	              <div class="col-lg-4">
	                    <div class="panel panel-warning">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                     <img  src="../dist/images/sales.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">${currentYear}年-销售类</div>
	                                    <div>销售类人员年度工作总结</div>
	                                </div>
	                            </div>
	                        </div>
	                        <a href="rs?op=toSaveReport&type=sale">
	                            <div class="panel-footer">
	                                <span class="pull-left">进入填写</span>
	                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                                <div class="clearfix"></div>
	                            </div>
	                        </a>
	                    </div>
	                </div>
             </c:if>
             <c:if test="${hasTec==false}">
	                <div class="col-lg-4">
	                    <div class="panel panel-info">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <img  src="../dist/images/tec.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">${currentYear}年-技术类</div>
	                                    <div>技术类人员年度工作总结</div>
	                                </div>
	                            </div>
	                        </div>
	                        <a href="rs?op=toSaveReport&type=tec" >
	                            <div class="panel-footer">
	                                <span class="pull-left">进入填写</span>
	                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                                <div class="clearfix"></div>
	                            </div>
	                        </a>
	                    </div>
	                </div>
               </c:if>
               <c:if test="${hasAdministration==false}">
	                <div class="col-lg-4">
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                     <img  src="../dist/images/humans.png" />
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">${currentYear}年-管理类、行政类</div>
	                                    <div>管理类、行政类人员年度工作总结</div>
	                                </div>
	                            </div>
	                        </div>
	                        <a href="rs?op=toSaveReport&type=administration" >
	                            <div class="panel-footer">
	                                <span class="pull-left">进入填写</span>
	                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
	                                <div class="clearfix"></div>
	                            </div>
	                        </a>
	                    </div>
	                </div>
               </c:if>
            </div>
            <!-- /.row -->
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

    <script type="text/javascript">
    	  // 删除报告
          function deleteReport(id){
       	       if(window.confirm('确认删除该报告吗？')){
                    $.post('rs?op=delReport',{'id':id},function(data){
                   	 if(data.res==1){
       	    			 alert("删除成功!");
       	    		 }else{
       	    			 alert("删除失败!");
       	    		 }
                   	 window.location.reload();
                    },'json');        	    	   
       	       }
          }
    	  // 快速提交报告
          function submitReport(id){
   	       if(window.confirm('提交之后将不能再修改了，确认提交该报告吗？')){
                $.post('rs?op=submitReport',{'id':id},function(data){
               	 if(data.res==1){
   	    			 alert("提交成功!");
   	    		 }else{
   	    			 alert("提交失败!");
   	    		 }
               	 window.location.reload();
                },'json');        	    	   
   	       }
     }
    </script>

</body>

</html>
