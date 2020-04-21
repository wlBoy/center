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
    <title>年终总结报告系统-票数汇总页面</title>
    <script src="vendor/jquery/jquery.min.js"></script>
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
    <!-- jQuery -->
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
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/tableContent.js"></script>
    <link rel="stylesheet" href="plugin/bootstrap-validator/css/bootstrapValidator.css"/>
    <script type="text/javascript" src="plugin/bootstrap-validator/js/bootstrapValidator.js"></script>
    <style type="text/css">
    	.voteContent{
	    	width:100%;
	    	min-height:30px;
    		border:1px solid #e7e7e7;
    		border-radius:3px;
    	}
    	.ticketCount{
    		color:red;
    		font-style:normal;
    		font-weight:bold;
    		position:relative;
    		top:-13px;
    		right:12px;
    	}
    </style>
</head>

<body>

<div id="wrapper">
        <%@include file="../common/head.jsp"%>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header6">票数汇总</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                       	查询条件
                    </div>
                    <div class="panel-body">
	                    <form id="searchForm" method="post">
							<div id="formSearch" class="form-horizontal">
								<div class="form-group" style="margin-top: 15px">
									<label class="control-label col-sm-1" for="search_status" style="margin-left:30%">评选年份</label>
									<div class="col-sm-1">
										<select class="form-control" name="voteYear" id="voteYear">
											<c:forEach var="map" items="${selectYearMap}">
												<option value="${map.key}" <c:if test="${currentYear == map.key}">selected="selected"</c:if>>${map.value}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-1" style="text-align: left;">
										<button type="button" style="margin-left: 30px;margin-top:5px;" id="searchBtn" 
											class="btn btn-primary">查询</button>
									</div>
									<div class="col-sm-1" style="text-align: left;">
										<button type="button" style="margin-top:5px;"  id="exportBtn" 
										class="btn btn-primary">导出</button>
									</div>
								</div>
							</div>
						</form>
					</div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
        </div>
        <!-- 内容区 -->
        <div class="row">
            <form id="data">
                <div class="panel-body">
                	<c:forEach items="${prizeTypeList}" var="prizeType">
	                 	<div class="form-group">
	                        <label>${prizeType.desc}票数汇总(${prizeType.count}名)</label>
	                        <div class="voteContent" id="${prizeType.code}_show"></div>
	                     </div>
                    </c:forEach>
                    <!-- /.table-responsive -->
                </div>
            </form>
		</div>
    </div>
    <!-- /#page-wrapper -->

</div>
<script>
	$(function(){
		getSumVoteByPrize();
		// 查询按钮触发事件
		$("#searchBtn").click(function(){
			// 清空展示区
			// 所有奖项code字符串
	    	var prizeTypeCode = '${prizeTypeCode}';
	    	var array  = prizeTypeCode.split(",");
	    	for (var i = 0; i < array.length; i++) {
				$("#"+array[i]+"_show").html("");
			}
			getSumVoteByPrize();
		});
		// 导出按钮触发事件
		$("#exportBtn").click(function(){
			exportVoteResultByPrize();
		});
	})
	// 汇总今年所有的投票结果
	function getSumVoteByPrize(){
		var voteYear = $("#voteYear").val();
		// 汇总今年所有的投票结果
    	$.post('vote?op=getSumVoteByPrize', {
    		 voteYear:voteYear
        }, function (data) {
        	 $(data).each(function(index,obj){
        		 appendSpanByPrize(obj);
        	 });
        }, 'json');
	}
	//将投票结果追加到显示区
	function appendSpanByPrize(obj){
		var candidateDept = obj.candidateDept== undefined?"无部门":obj.candidateDept;
		$("#"+obj.prizeType+"_show").append("<div style='display:inline-block;position:absoulte;margin-right:3px;'><span class='btn btn-default' title='"+candidateDept+"' style='text-align:center;'>"+obj.candidateName+"</span><i class='ticketCount'>"+obj.count+"</i></div>");
	}
	/*导出各奖项票数汇总*/
	function exportVoteResultByPrize(){
		$("#searchForm").attr("action","/vote?op=exportVoteResultByPrize");
    	$("#searchForm").submit();
	}
</script>
</body>

</html>
