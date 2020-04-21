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
    <title>年终总结报告系统-填写报告页面</title>
    
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
    <link rel="stylesheet" href="plugin/bootstrap-validator/css/bootstrapValidator.css"/>
    <script type="text/javascript" src="plugin/bootstrap-validator/js/bootstrapValidator.js"></script>
    <!-- 提示框插件 -->
    <script src="js/ui.js"></script>
	<link href="css/style.css" rel="stylesheet">
    <style type="text/css">
		.toast-content{
			margin:0px !important;
		}
		#saveBtn:disabled{
			cursor: wait;
		}
		#submitBtn:disabled{
			cursor: wait;
		}
		.tableHeader{
			color:#CC0033;
			font-weight: bold;
		}
    </style>
</head>

<body>

	<div id="wrapper">
		<%@include file="../common/head.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header"><c:if test="${add=='add'}">填写</c:if><c:if test="${add!='add'}">修改</c:if>总结-
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
							   <form id="myform">
								   <div class="form-group">
                                            <label>岗位职责</label>
                                            <textarea class="form-control" id="RESPONSIBILITIES" name="RESPONSIBILITIES" rows="10" placeholder="请概述你目前岗位工作职责"><c:if test="${not empty report }">${report.RESPONSIBILITIES }</c:if></textarea>
                                    </div>
                                    <!--技术类 -->
								    <c:if test="${type=='tec'}">
										<div class="form-group">
											<div><label>技术研发人员工作概况，请描述<span class="tableHeader">(表头不允许修改)</span></label></div>
											<script id="developWorkEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.DEVELOP_WORK }</c:if></script>
										</div>
										<div class="form-group">
											<label>实施人员工作概况，请描述<span class="tableHeader">(表头不允许修改)</span></label>
											<script id="deployWorkEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.DEPLOY_WORK }</c:if></script>
										</div>
										<div class="form-group">
											<label>测试人员工作概况，请描述<span class="tableHeader">(表头不允许修改)</span></label>
											<script id="testWorkEditor" type="text/plain" ><c:if test="${not empty report }">${report.TEST_WORK }</c:if></script>
										</div>
								    </c:if>
								    <!--销售类 -->
								    <c:if test="${type=='sale'}">
										<div>
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
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row1col1"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row1col2"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row1col3"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row1col4"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row1col5"  /></div></td>
			                                        </tr>
			                                        <tr>
			                                            <td>新签差价</td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row2col1"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row2col2"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row2col3"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row2col4"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row2col5"  /></div></td>
			                                        </tr>
			                                        <tr>
			                                            <td>到款金额</td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row3col1"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row3col2"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row3col3"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row3col4"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row3col5"  /></div></td>
			                                        </tr>
			                                         <tr>
			                                            <td>到款差价</td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row4col1"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row4col2"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row4col3"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row4col4"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row4col5"  /></div></td>
			                                        </tr>
			                                         <tr>
			                                            <td>外报收入</td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row5col1"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row5col2"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row5col3"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row5col4"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row5col5"  /></div></td>
			                                        </tr>
			                                        <tr>
			                                            <td>销售费用</td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row6col1"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row6col2"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row6col3"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row6col4"  /></div></td>
			                                            <td><div class="form-group"><input class="form-control saleHisInput" name="row6col5"  /></div></td>
			                                        </tr>
			                                    </tbody>
			                                </table>
			                            </div>
			                        </div>
										<div class="form-group">
											<label>新客户开拓情况<span class="tableHeader">(表头不允许修改)</span></label>
											<script id="newCustomerEditor" type="text/plain" ><c:if test="${not empty report }"> ${report.NEW_CUSTOMER }</c:if></script>
										</div>
										<div class="form-group">
											<label>个人前3-5大项目推进情况<span class="tableHeader">(表头不允许修改)</span></label>
											<script id="bigProjectEditor" type="text/plain" ><c:if test="${not empty report }">${report.BIG_PROJECT }</c:if></script>
										</div>
								   </c:if>
								   <!--行政类 -->
								   <c:if test="${type=='administration'}">
										<div class="form-group">
											<label>工作内容<span class="tableHeader">(表头不允许修改)</span></label>
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
						<div class="panel-heading">2.保密工作情况及总结<span class="tableHeader">(表头不允许修改)</span></div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
										<div class="form-group">
											<script id="secretEditor" type="text/plain" ><c:if test="${not empty report }">${report.SECRECT_WORK }</c:if></script>
										</div>
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
										<div class="form-group">
										 <label>个人目标的实现程度</label>
                                            <textarea class="form-control" id="PERSONALGOALS" name="PERSONALGOALS" rows="10" placeholder="自己年初的目标是&#13;&#10;今年实现的情况与自我评价"><c:if test="${not empty report }">${report.PERSONAL_GOALS }</c:if></textarea>
										</div>
										<div class="form-group">
										 <label>进步和收获</label>
                                            <textarea class="form-control" id="PROGRESS" name="PROGRESS" rows="10" placeholder="含知识技能、合作、荣誉与证书、成果等&#13;&#10;技术上的创新和解决的问题"><c:if test="${not empty report }">${report.PROGRESS }</c:if></textarea>
										</div>
										<div class="form-group">
										 <label>问题与不足</label>
                                            <textarea class="form-control" id="PROBLEM" name="PROBLEM" rows="10" placeholder="遇到问题有&#13;&#10;自省不足有"><c:if test="${not empty report }">${report.PROBLEM }</c:if></textarea>
										</div>
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
										<div class="form-group">
                                            <textarea class="form-control" id="TARGET" name="TARGET" rows="10" placeholder="计划目标是&#13;&#10;希望对公司和团队的需求支持是"><c:if test="${not empty report }">${report.TARGET }</c:if></textarea>
										</div>
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
						<div class="panel-heading">5.对公司和团队的建议<span class="tableHeader">(表头不允许修改)</span></div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
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
										
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${report.STATUS!=2000}">
				<div class="operatorBtnDiv" style="text-align:center;margin-bottom:20px;">
					<button type="button"  id="saveBtn" class="btn btn-info" style="width:10%;margin-right:20px;">保存</button>
					<button type="button"  id="submitBtn" class="btn btn-primary" style="width:10%;">提交</button>
				</div>
			</c:if>
			
		</div>
	</div>
    <script>
        $(function () {
            var type='${type}';//模板类型
            if('tec'== type){
	            //<-- 1.技术类输入框
	        	var developWorkEditor = UE.getEditor('developWorkEditor');
				developWorkEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	developWorkEditor.setContent(developWorkTable);
	                }
	            });
				var deployWorkEditor = UE.getEditor('deployWorkEditor');
				deployWorkEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	deployWorkEditor.setContent(deployWorkTable);
	                }
	            });
				var testWorkEditor = UE.getEditor('testWorkEditor');
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
				var newCustomerEditor = UE.getEditor('newCustomerEditor');
				newCustomerEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	newCustomerEditor.setContent(newCustomerTable);
	                }
	            });
				var bigProjectEditor = UE.getEditor('bigProjectEditor');
				bigProjectEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	bigProjectEditor.setContent(bigProjectTable);
	                }
	            });
				// 销售类输入框 -->
            }else if('administration'== type){
				// <-- 1.行政类输入框
				var adminWorkEditor = UE.getEditor('adminWorkEditor');
				adminWorkEditor.ready(function() {
	                //设置编辑器的内容
	                if("${add}"=='add'){
	                	adminWorkEditor.setContent(adminWorkTable);
	                }
	            });
				// 行政类输入框 -->
            }
			
			// <-- 2.保密工作情况及总结输入框
			var secretEditor=UE.getEditor('secretEditor');
            secretEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	secretEditor.setContent(secretTable);
                }
            });
        	// 保密工作情况及总结输入框 -->
        	
        	// <-- 5.对公司和团队的建议输入框
            var productSuggestEditor=UE.getEditor('productSuggestEditor');
            productSuggestEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	productSuggestEditor.setContent(productSuggestTable);
                }
            });
            var projectSuggestEditor=UE.getEditor('projectSuggestEditor');
            projectSuggestEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	projectSuggestEditor.setContent(projectSuggestTable);
                }
            });
            var adminSuggestEditor=UE.getEditor('adminSuggestEditor');
            adminSuggestEditor.ready(function() {
                //设置编辑器的内容
                if("${add}"=='add'){
                	adminSuggestEditor.setContent(adminSuggestTable);
                }
            });
         	// 对公司和团队的建议输入框 -->
         	
         	// 提交本页面的所有数据至数据库中
            function submitData(act,actType){
         		 var reportStatus = '${report.STATUS}';
         		 var actionType = '';
         		 if(actType==undefined){
         			actionType='save';
         		 }else{
         			actionType=actType;
         		 }
	           	 var uuid='${id}';//模板ID
	           	 var RESPONSIBILITIES=$('#RESPONSIBILITIES').val();
	           	 var DEVELOP_WORK = '';
	           	 var DEPLOY_WORK = '';
	           	 var TEST_WORK = '';
	           	 var SALE_HISTORY = '';
	           	 var NEW_CUSTOMER = '';
	           	 var BIG_PROJECT = '';
	           	 var ADMIN_WORK = '';
	           	 var flag1 = true;
	           	 if('tec'== type) {
              	     DEVELOP_WORK= developWorkEditor.getContent();
              	     DEPLOY_WORK= deployWorkEditor.getContent();
              	     TEST_WORK= testWorkEditor.getContent();
              	     flag1 = compareCol(DEVELOP_WORK,6,"技术研发人员工作概况描述") && compareCol(DEPLOY_WORK,5,"实施人员工作概况描述")
          	  		 && compareCol(TEST_WORK,7,"测试人员工作概况");
	           	 } else if('sale'== type){
	           		 SALE_HISTORY = getSaleHistory();
              	     NEW_CUSTOMER= newCustomerEditor.getContent();
              	     BIG_PROJECT= bigProjectEditor.getContent();
              	   	 flag1 = compareCol(NEW_CUSTOMER,7,"新客户开拓情况") && compareCol(BIG_PROJECT,3,"个人前3-5大项目推进情况");
	           	 }	else if('administration'== type){
	           		 ADMIN_WORK= adminWorkEditor.getContent();
	           		 flag1 = compareCol(ADMIN_WORK,5,"工作内容");
	           	 }	
	           	 var SECRECT_WORK= secretEditor.getContent();
	           	 var PERSONAL_GOALS=$('#PERSONALGOALS').val();
	           	 var PROGRESS=$('#PROGRESS').val();
	           	 var PROBLEM=$('#PROBLEM').val();
	           	 var TARGET=$('#TARGET').val();
	           	 var PRODUCT_SUGGEST= productSuggestEditor.getContent();
	          	 var PROJECT_SUGGEST= projectSuggestEditor.getContent();
	          	 var ADMIN_SUGGEST= adminSuggestEditor.getContent();
	          	 var flag =  flag1 && compareCol(SECRECT_WORK,5,"保密工作情况及总结") &&compareCol(PRODUCT_SUGGEST,4,"产品建议") &&compareCol(PROJECT_SUGGEST,3,"项目建议") &&compareCol(ADMIN_SUGGEST,3,"管理建议");
      	  		 // 校验表格中的列数，列数一致才提交ajax；不一致给出提示，不提交ajax
	          	 if(flag){
	          	     $.post('rs?op=saveReport',{'id':uuid,
	          	    	 "RESPONSIBILITIES":RESPONSIBILITIES,
	          	    	 "DEVELOP_WORK":DEVELOP_WORK,
	          	    	 "DEPLOY_WORK":DEPLOY_WORK,
	          	    	 "TEST_WORK":TEST_WORK,
	          	    	 "SALE_HISTORY":SALE_HISTORY,
	          	    	 "NEW_CUSTOMER":NEW_CUSTOMER,
	          	    	 "BIG_PROJECT":BIG_PROJECT,
	          	    	 "ADMIN_WORK":ADMIN_WORK,
	          	    	 "SECRECT_WORK":SECRECT_WORK,
	          	    	 "PERSONAL_GOALS":PERSONAL_GOALS,
	          	    	 "PROGRESS":PROGRESS,
	          	    	 "PROBLEM":PROBLEM,
	          	    	 "TARGET":TARGET,
	          	    	 "PRODUCT_SUGGEST":PRODUCT_SUGGEST,
	          	    	 "PROJECT_SUGGEST":PROJECT_SUGGEST,
	          	    	 "ADMIN_SUGGEST":ADMIN_SUGGEST,
	          	    	 "actionType":actionType,
	          	    	 "type":type,
	          	    	 "reportStatus":reportStatus
	          	    	 },function(data){
	          	    		 if(act=='manual'){
	          	    			 if(data.res==1){
	          	    				 if('save'==actionType){
	          	    				 	alert("保存成功!")
	          	    				 }else{
	          	    				 	window.clearTimeout(timer);//提交成功后，清除定时器
	          	    				 	alert("提交成功!")
	          	    				 	window.location = '/rs?op=toIndex';
	          	    				 }
	          	    			 }else{
	          	    				if('save'==actionType){
	          	    				 	alert("保存失败!")
	          	    				 }else{
	          	    				 	alert("提交失败!")
	          	    				 }
	          	    			 }
	          	    			// 将按钮变为可用
	          	       			$("#saveBtn").attr('disabled',false);
	          	       			$("#submitBtn").attr('disabled',false);
	          	    		 }else{
		          	    		 if(data.res==1){
		          	    			mizhu.toastInfo('系统已经为您自动保存');
		          	    		 }
	          	    		 }
	          	     },'json'); 
	          	 }
           };
           // 定时调用ajax提交本页面的数据,submitData过60s后调用该函数,submitData()会立即调用该函数
           var timer = window.setInterval(submitData,60000); //60s
           // 手动提交本页面的数据
           $("#submitBtn").click(function(){
        	   if(confirm('提交之后将不能再修改了，确认提交该报告吗？')){
        			// 将按钮变为不可用
          			$("#submitBtn").attr('disabled',true);
	     			submitData('manual','submit');
	     		}
           });
           // 手动保存本页面的数据
           $("#saveBtn").click(function(){
        		// 将按钮变为不可用
       			$("#saveBtn").attr('disabled',true);
	     		submitData('manual','save');
           });
           // 检验表格中的数据是否为数字
       	   $('#myform').bootstrapValidator({
       	        message: '无效值',
       	        feedbackIcons: {
       	            valid: '',
       	            invalid: 'glyphicon glyphicon-remove',
       	            validating: 'glyphicon glyphicon-refresh'
       	        },
       	        fields: {
       	        	row1col1: {
       	                message: '',
       	                validators: {
       	                	regexp: {
                                regexp: /^\d+(\.\d+)?$/,
                                message: '必须为数字!'
                            }
       	                }
       	            },
       	         	row1col2: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row1col3: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row1col4: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
    	                        message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row1col5: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },row2col1: {
       	                message: '',
       	                validators: {
       	                    regexp: {
       	                        regexp: /^\d+(\.\d+)?$/,
       	                     	message: '必须为数字!'
       	                    }
       	                }
       	            },
       	         	row2col2: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row2col3: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row2col4: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
    	                        message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row2col5: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row3col1: {
       	                message: '',
       	                validators: {
       	                    regexp: {
       	                        regexp: /^\d+(\.\d+)?$/,
       	                     	message: '必须为数字!'
       	                    }
       	                }
       	            },
       	         	row3col2: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row3col3: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row3col4: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
    	                        message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row3col5: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row4col1: {
       	                message: '',
       	                validators: {
       	                    regexp: {
       	                        regexp: /^\d+(\.\d+)?$/,
       	                     	message: '必须为数字!'
       	                    }
       	                }
       	            },
       	         	row4col2: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row4col3: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row4col4: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
    	                        message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row4col5: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row5col1: {
       	                message: '',
       	                validators: {
       	                    regexp: {
       	                        regexp: /^\d+(\.\d+)?$/,
       	                     	message: '必须为数字!'
       	                    }
       	                }
       	            },
       	         	row5col2: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row5col3: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row5col4: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
    	                        message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row5col5: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row6col1: {
       	                message: '',
       	                validators: {
       	                    regexp: {
       	                        regexp: /^\d+(\.\d+)?$/,
       	                     	message: '必须为数字!'
       	                    }
       	                }
       	            },
       	         	row6col2: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row6col3: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row6col4: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
    	                        message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            row6col5: {
    	                message: '',
    	                validators: {
    	                    regexp: {
        	                    regexp: /^\d+(\.\d+)?$/,
        	                    message: '必须为数字!'
    	                    }
    	                }
    	            },
    	            /*RESPONSIBILITIES: {
    	                message: '值无效',
    	                validators: {
    	                    notEmpty: {
    	                        message: '必填项，请输入！'
    	                    }
    	                }
    	            },
    	            PERSONALGOALS: {
    	                message: '值无效',
    	                validators: {
    	                    notEmpty: {
    	                        message: '必填项，请输入！'
    	                    }
    	                }
    	            },
    	            PROGRESS: {
    	                message: '值无效',
    	                validators: {
    	                    notEmpty: {
    	                        message: '必填项，请输入！'
    	                    }
    	                }
    	            },
    	            PROBLEM: {
    	                message: '值无效',
    	                validators: {
    	                    notEmpty: {
    	                        message: '必填项，请输入！'
    	                    }
    	                }
    	            },
    	            TARGET: {
    	                message: '值无效',
    	                validators: {
    	                    notEmpty: {
    	                        message: '必填项，请输入！'
    	                    }
    	                }
    	            },*/
       	        }
       	    });
        });
        // 将表格中的数据序列化成json字符串
        function getSaleHistory(){
        	var saleHisInput=$(".saleHisInput");
    		var saleHis=new Object();
        	saleHisInput.each(function(i,data){
        		var key=$(data).attr('name');
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
	        		$('input[name="'+id+'"]').val(value);
	        	});
        	}
        };
        // 比对表格中的列数是否正确
        function compareCol(str,num,msg) {
            var re = /<\/th>/ig;
            var length = str.match(re) ? str.match(re).length : 0;
            if(length!=num){
            	mizhu.toastWarn("【"+msg+"】表格中的列数错误，请检查并重置！", 8000);
            	return false;
            }else{
            	return true;
            }
        }
    </script>
</body>
</html>
