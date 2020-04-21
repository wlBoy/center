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
    <title>年终总结报告系统-报告详情页面</title>
    
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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
      <!--[if lt IE 9]>
        <script src="。。/plugin/html5shiv.js"></script>
        <script src="。。/plugin/respond.min.js"></script>
    <![endif]-->
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
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/tableContent.js"></script>
    <!-- 提示框插件 -->
    <script src="js/ui.js"></script>
	<link href="css/style.css" rel="stylesheet">
    <style type="text/css">
		.toast-content{
			background: #26b670;
			margin:0px !important;
		}
    </style>
</head>

<body>

	<div id="wrapper">
	
   		 <%@include file="../common/head.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">查看总结-
					<c:if test="${type=='tec'}">技术类</c:if>
					<c:if test="${type=='sale'}">销售类</c:if>
					<c:if test="${type=='administration'}">管理类、行政类</c:if>
					</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">1.工作总结</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
							   <form role="form">
								   <div class="form-group">
                                            <label>岗位职责</label>
                                            <textarea class="form-control" readonly="readonly" id="RESPONSIBILITIES"  rows="10" placeholder="请概述你目前岗位工作职责"><c:if test="${not empty report }">${report.RESPONSIBILITIES }</c:if></textarea>
                                    </div>
                                    <!--技术类 -->
								    <c:if test="${type=='tec'}">
										<div class="form-group">
											<div><label>技术研发人员工作概况，请描述</label></div>
											<script id="developWorkEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.DEVELOP_WORK }</c:if></script>
										</div>
										<div class="form-group">
											<label>实施人员工作概况，请描述</label>
											<script id="deployWorkEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.DEPLOY_WORK }</c:if></script>
										</div>
										<div class="form-group">
											<label>测试人员工作概况，请描述</label>
											<script id="testWorkEditor" type="text/plain" ><c:if test="${not empty report }">${report.TEST_WORK }</c:if></script>
										</div>
								    </c:if>
								    <!--销售类 -->
								    <c:if test="${type=='sale'}">
										<div class="form-group">
					                  <div><label>销售回顾 </label></div>
			                           <div class="table-responsive">
			                                <table class="table table-striped table-bordered table-hover">
			                                    <thead>
			                                        <tr>
			                                            <th></th>
			                                            <th>指标（万）</th>
			                                            <th>实际完成（万）</th>
			                                            <th>完成率（%）</th>
			                                            <th>增长率（相比去年）（%）</th>
			                                            <th>近3年平均增长率（%）</th>
			                                        </tr>
			                                    </thead>
			                                    <tbody>
			                                      <tr>
			                                            <td>新签合同</td>
			                                            <td><input class="form-control" readonly="readonly" id="row1col1" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row1col2" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row1col3" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row1col4" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row1col5" name="saleHisInput" /></td>
			                                        </tr>
			                                        <tr>
			                                            <td>新签差价</td>
			                                            <td><input class="form-control" readonly="readonly" id="row2col1" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row2col2" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row2col3" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row2col4" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row2col5" name="saleHisInput" /></td>
			                                        </tr>
			                                        <tr>
			                                            <td>到款金额</td>
			                                            <td><input class="form-control" readonly="readonly" id="row3col1" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row3col2" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row3col3" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row3col4" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row3col5" name="saleHisInput" /></td>
			                                        </tr>
			                                         <tr>
			                                            <td>到款差价</td>
			                                            <td><input class="form-control" readonly="readonly" id="row4col1" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row4col2" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row4col3" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row4col4" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row4col5" name="saleHisInput" /></td>
			                                        </tr>
			                                         <tr>
			                                            <td>外报收入</td>
			                                            <td><input class="form-control" readonly="readonly" id="row5col1" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row5col2" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row5col3" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row5col4" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row5col5" name="saleHisInput" /></td>
			                                        </tr>
			                                        <tr>
			                                            <td>销售费用</td>
			                                            <td><input class="form-control" readonly="readonly" id="row6col1" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row6col2" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row6col3" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row6col4" name="saleHisInput" /></td>
			                                            <td><input class="form-control" readonly="readonly" id="row6col5" name="saleHisInput" /></td>
			                                        </tr>
			                                    </tbody>
			                                </table>
			                            </div>
			                        </div>
										<div class="form-group">
											<label>新客户开拓情况</label>
											<script id="newCustomerEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.NEW_CUSTOMER }</c:if></script>
										</div>
										<div class="form-group">
											<label>个人前3-5大项目推进情况</label>
											<script id="bigProjectEditor" type="text/plain" ><c:if test="${not empty report }">${report.BIG_PROJECT }</c:if></script>
										</div>
								   </c:if>
								   <!--行政类 -->
								   <c:if test="${type=='administration'}">
										<div class="form-group">
											<label>工作内容</label>
											<script id="adminWorkEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.ADMIN_WORK }</c:if></script>
										</div>
								   </c:if>
								   </form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--保密工作情况及总结 -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">2.保密工作情况及总结</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form">
										<div class="form-group">
											<script id="secretEditor" type="text/plain" ><c:if test="${not empty report }">${report.SECRECT_WORK }</c:if></script>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--经验体会 -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">3.经验体会</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form">
										<div class="form-group">
										 <label>个人目标的实现程度</label>
                                            <textarea class="form-control" id="PERSONALGOALS"  readonly="readonly" rows="10" placeholder="自己年初的目标是&#13;&#10;今年实现的情况与自我评价"><c:if test="${not empty report }">${report.PERSONAL_GOALS }</c:if></textarea>
										</div>
										<div class="form-group">
										 <label>进步和收获</label>
                                            <textarea class="form-control" id="PROGRESS"  readonly="readonly" rows="10" placeholder="含知识技能、合作、荣誉与证书、成果等"><c:if test="${not empty report }">${report.PROGRESS }</c:if></textarea>
										</div>
										<div class="form-group">
										 <label>问题与不足</label>
                                            <textarea class="form-control" id="PROBLEM"  readonly="readonly" rows="10" placeholder="遇到问题有&#13;&#10;自省不足有"><c:if test="${not empty report }">${report.PROBLEM }</c:if></textarea>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--明年目标 -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">4.明年目标</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form">
										<div class="form-group">
                                            <textarea class="form-control" id="TARGET"  readonly="readonly" rows="10" placeholder="计划目标是&#13;&#10;希望对公司和团队的需求支持是"><c:if test="${not empty report }">${report.TARGET }</c:if></textarea>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--5.对公司和团队的建议 -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">5.对公司和团队的建议</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form">
										<div class="form-group">
										   <label>产品建议</label>
										   <script id="productSuggestEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.PRODUCT_SUGGEST }</c:if></script>
										</div>
										
										<div class="form-group">
										   <label>项目建议</label>
										   <script id="projectSuggestEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.PROJECT_SUGGEST }</c:if></script>
										</div>
										<div class="form-group">
										   <label>管理建议</label>
										   <script id="adminSuggestEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.ADMIN_SUGGEST }</c:if></script>
										</div>
										
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
    <script>
        $(function () {
            var type='${type}';//模板类型
            if('tec'== type){
	            //<-- 1.技术类输入框
	        	var developWorkEditor = UE.getEditor('developWorkEditor', {
	        	    toolbars: [
	        	        []
	        	    ],readonly:true
	        	});
				developWorkEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	developWorkEditor.setContent(devWorkTable);
	                }
	            });
				var deployWorkEditor = UE.getEditor('deployWorkEditor', {
	        	    toolbars: [
	        	        []
	        	    ],readonly:true
	        	});
				deployWorkEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	deployWorkEditor.setContent(depolyWorkTable);
	                }
	            });
				var testWorkEditor = UE.getEditor('testWorkEditor', {
	        	    toolbars: [
	        	        []
	        	    ],readonly:true
	        	});
				testWorkEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	testWorkEditor.setContent(testWorkTable);
	                }
	            });
				// 技术类输入框 -->
            }else if('sale'== type){
            	setSaleHistory();
				// <-- 1.销售类输入框
				var newCustomerEditor = UE.getEditor('newCustomerEditor', {
	        	    toolbars: [
	        	        []
	        	    ],readonly:true
	        	});
				newCustomerEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	newCustomerEditor.setContent(newCustomerTable);
	                }
	            });
				var bigProjectEditor = UE.getEditor('bigProjectEditor', {
	        	    toolbars: [
	        	        []
	        	    ],readonly:true
	        	});
				bigProjectEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	bigProjectEditor.setContent(bigProjectTable);
	                }
	            });
				// 销售类输入框 -->
            }else if('administration'== type){
				// <-- 1.行政类输入框
				var adminWorkEditor = UE.getEditor('adminWorkEditor', {
	        	    toolbars: [
	        	        []
	        	    ],readonly:true
	        	});
				adminWorkEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	adminWorkEditor.setContent(adminWorkTable);
	                }
	            });
				// 行政类输入框 -->
            }
			
			// <-- 2.保密工作情况及总结输入框
			var secretEditor=UE.getEditor('secretEditor', {
        	    toolbars: [
        	        []
        	    ],readonly:true
        	});
            secretEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	secretEditor.setContent(secWorkTable);
                }
            });
        	// 保密工作情况及总结输入框 -->
        	
        	// <-- 5.对公司和团队的建议输入框
            var productSuggestEditor=UE.getEditor('productSuggestEditor', {
        	    toolbars: [
        	        []
        	    ],readonly:true
        	});
            productSuggestEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	productSuggestEditor.setContent(productSuggestTable);
                }
            });
            var projectSuggestEditor=UE.getEditor('projectSuggestEditor', {
        	    toolbars: [
        	        []
        	    ],readonly:true
        	});
            projectSuggestEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	projectSuggestEditor.setContent(projectSuggestTable);
                }
            });
            var adminSuggestEditor=UE.getEditor('adminSuggestEditor', {
        	    toolbars: [
        	        []
        	    ],readonly:true
        	});
            adminSuggestEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	adminSuggestEditor.setContent(adminSuggestTable);
                }
            });
         	// 对公司和团队的建议输入框 -->
        });
        // 将表格中的数据序列化成json字符串
        function getSaleHistory(){
        	var saleHisInput=$('input[name="saleHisInput"]');
    		var saleHis=new Object();
        	saleHisInput.each(function(i,data){
        		var key=$(data).attr('id');
        		var value=$(data).val();
        		saleHis[key]=value;
        	});
    		return JSON.stringify(saleHis);
        }
        // 解析后台传回来的json字符串并回显输入框的value值
        function setSaleHistory(){
        	var saleHis = '${report.SALE_HISTORY}';
        	if(saleHis!=''){
	        	$.each(JSON.parse(saleHis), function(id, value) {
	        		$("#"+id).val(value);
	        	});
        	}
        };
    </script>
</body>
</html>
