<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>年终总结报告系统-排名汇总页面</title>

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
<link href="../vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">


<link href="../plugin/bootstrap-table/bootstrap-table.css"
	rel="stylesheet" type="text/css">

<link href="../plugin/bootstrap-select2/css/select2.css"
	rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
        <script src="。。/plugin/html5shiv.js"></script>
        <script src="。。/plugin/respond.min.js"></script>
    <![endif]-->
    
<style type="text/css">
    .select2-container .select2-selection--single{  
      height:34px;  
      line-height: 34px;  
    }  
</style>

</head>

<body>

	<div id="wrapper">

		    <%@include file="../common/head.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header6">排名汇总</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="panel panel-default">
					<div class="panel-heading">查询条件</div>
					<div class="panel-body">
						<form id="searchForm" action="vote?op=toSumVoteByWeight" method="post">
							<div id="formSearch" class="form-horizontal">
								<div class="form-group" style="margin-top: 15px">
									<label class="control-label col-sm-1" for="search_apply">金奖权重(分)</label>
									<div class="col-sm-1">
										<input type="number" class="form-control" name="goldWeight" id="goldWeight" value="5">
									</div>
									<label class="control-label col-sm-1" for="search_org">银奖权重(分)</label>
									<div class="col-sm-1">
										<input type="number" class="form-control" name="silverWeight" id="silverWeight" value="3">
									</div>
									<label class="control-label col-sm-1" for="search_status">铜奖权重(分)</label>
									<div class="col-sm-1">
										<input type="number" class="form-control" name="bronzeWeight" id="bronzeWeight" value="1">
									</div>
									<label class="control-label col-sm-1" for="search_status">评选年份</label>
									<div class="col-sm-1">
										<select class="form-control" name="voteYear" id="voteYear">
											<c:forEach var="map" items="${selectYearMap}">
												<option value="${map.key}" <c:if test="${currentYear == map.key}">selected="selected"</c:if>>${map.value}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-1" style="text-align: left;">
										<button type="button" style="margin-left: 50px" id="searchBtn"
											class="btn btn-primary">查询</button>
									</div>
									<div class="col-sm-1" style="text-align: left;">
										<button type="button" id="exportBtn"
											class="btn btn-primary">导出</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<table id="tb_vote_result_by_weight"></table>
			</div>
		</div>
	</div>
	<!-- /#page-wrapper -->
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../plugin/bootstrap-table/bootstrap-table.js"></script>
	<script src="../plugin/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
	
	<script src="../plugin/bootstrap-select2/js/select2.full.js"></script>
	<script src="../plugin/bootstrap-select2/js/i18n/zh-CN.js"></script>
	<script type="text/javascript">
		var orgSelect2;
		$(function() {
	
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			//远程筛选
		    orgSelect2 = $("#search_org").select2({
		    	language:'zh-CN',
		    	placeholder:'所有',
		    	allowClear:true,
		        ajax: {
		            url: "/org?op=getOrgData",
		            dataType: 'json',
		            delay: 250,
		            data: function (params) {
		                return {
		                    orgName: params.term
		                };
		            },
		            processResults: function (data, params) {
	
		                return {
		                    results: data
		                };
		            },
		            cache: false
		        },
		        escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
		        minimumInputLength: 1,
		        templateResult: formatRepoProvince, // omitted for brevity, see the source of this page
		        templateSelection: formatRepoProvince // omitted for brevity, see the source of this page
		    });
			 
		    function formatRepoProvince(repo) {
		        if (repo.loading) return repo.text;
		        var markup = "<div>"+repo.text+"</div>";
		        return markup;
		    }
	
		});
		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_vote_result_by_weight')
						.bootstrapTable(
								{
									url : '/vote?op=getSumVoteByWeight', //请求后台的URL（*）
									method : 'get', //请求方式（*）
									striped : true, //是否显示行间隔色
									cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
									pagination : true, //是否显示分页（*）
									sortable : false, //是否启用排序
									queryParamsType : '',
									queryParams : oTableInit.queryParams,//传递参数（*）
									sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
									pageNumber : 1, //初始化加载第一页，默认第一页
									pageSize : 20, //每页的记录行数（*）
									pageList : [ 20, 50, 100 ], //可供选择的每页的行数（*）
									minimumCountColumns : 2, //最少允许的列数
									//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
									uniqueId : "ID", //每一行的唯一标识，一般为主键列
									cardView : false, //是否显示详细视图
									detailView : false, //是否显示父子表
									columns : [
											{
												title : '序号',
												formatter : function(value,
														row, index) {
													return index + 1;
												}
											},
											{
												field : 'candidateName',
												title : '候选人名称'
											},
											{
												field : 'candidateDept',
												title : '候选人部门'
											},
											{
												field : 'goldCount',
												title : '金奖票数'
											},
											{
												field : 'silverCount',
												title : '银奖票数'
											},
											{
												field : 'bronzeCount',
												title : '铜奖票数'
											},
											{
												field : 'totalCount',
												title : '总票数'
											},
											{
												field : 'sumScore',
												title : '总分'
											},
											{
												field : 'voteYear',
												title : '评选年份'
											},
											]
								});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					limit : params.pageSize, //页面大小
					offset : params.pageNumber, //页码
					goldWeight : $("#goldWeight").val(),
					silverWeight : $("#silverWeight").val(),
					bronzeWeight : $("#bronzeWeight").val(),
					voteYear : $("#voteYear").val()
				};
				return temp;
			};
			return oTableInit;
		};

		var ButtonInit = function() {
			var oInit = new Object();
			var postdata = {};

			oInit.Init = function() {
				//初始化页面上面的按钮事件
			};

			return oInit;
		};

		$("#searchBtn").click(function() {
			$("#tb_vote_result_by_weight").bootstrapTable('refresh');

		});
		// 导出按钮触发事件
		$("#exportBtn").click(function(){
			exportVoteResultByWeight();
		});
		/*导出排名总分汇总*/
		function exportVoteResultByWeight(){
			$("#searchForm").attr("action","/vote?op=exportVoteResultByWeight");
	    	$("#searchForm").submit();
		}
	</script>
</body>

</html>
