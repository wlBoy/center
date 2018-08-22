<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示渠道数据页面</title>
<style type="text/css">
.con {
	width: 90%;
	margin: 10px auto;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 650px;
	height: 80px;
	margin:10px auto;
}
.table , .table th{
	text-align: center;
}
.state1{
	color:#58c0de;
}
.state2{
	color:#d2322d;
}
.modal-content{
	top:100px;
	right:24px;
}
.btn-xs{
	position: relative;
	top:5px;
}
#query{
	position: relative;
	top:-5px;
	left:30px;
}
.title{
	float:right;
	position:relative;
	top:20px;
	font-size:16px;
}
.hide{
	display:none;
}
.show{
	display:block;
}
.tip{
	width: 100%px;
	height: 30px;
	text-align:center;
	line-height:30px;
	color:red;
	font-size:14px;
}
</style>
<script type="text/javascript">
$(function(){
	$("#import").click(function() {
		var modal = showNormalLay("导入EXCEL数据",$("#importFormLay"),function(){
			if(checkForm()){
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("120px");
		modal.setWidth("320px");
	});
	$("#exportAll").click(function() {
		var modal = showNormalLay("导出所有数据为EXCEL文件",$("#exportAllFormLay"),function(){
			$("#modal2 form").submit();
			// 取消弹出层
			$("#modal2 #closebtn").click();
		});
		modal.show();
		modal.setHeigth("120px");
		modal.setWidth("320px");
	});
})
//校验上传文件是否为ECXEL格式的文件   
function checkForm(){    
   var fileDir = $("#modal2 #upfile").val();    
   var suffix = fileDir.substr(fileDir.lastIndexOf("."));    
   if("" == fileDir){ 
	   swal("OMG!", "选择需要导入的Excel文件！", "error");
       return false;    
   }    
   if(".xls" != suffix && ".xlsx" != suffix ){ 
	   swal("OMG!", "选择Excel格式的文件导入！", "error");
       return false;    
   }    
   return true;    
} 
</script>
</head>
<body>
	<div class="contact">
		<!-- 表单区,用来处理用户的查询提交数据 -->
		<form id="pageFm" action="${ctx}/data/channel/showAllChannelData.do"
			method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
				<fieldset>
	            	<div class="row">
		              <div class="col-md-5">
		              	<div class="form-group">
				            <label class="control-label col-md-5">应用名</label>
				            <div class="col-md-7">
				              <input class="form-control" name="appName" value="${pages.bean.appName}" type="text">
				            </div>
		            	</div>
		              </div>
		              <div class="col-md-5">
			              	<div class="form-group">
					            <label class="control-label col-md-3">日期</label>
					            <div class="col-md-8">
					              <input onClick="WdatePicker({dateFmt:'yyyyMMdd'})" readonly="readonly"
							    	type="text"  class="form-control" name="curday" value="${pages.bean.curday}"/>
					            </div>
			            	</div>
		              </div>
		              <div class="col-md-2">
		              		<input type="submit" value="查询" class="btn btn_5 btn-lg btn-primary" id="query">
		              </div>
	              </div>
	          </fieldset>
		</form>
	</div>
	<div style="clear: both"></div>
	<!-- 主题内容区 -->
	<div class="con">
		<div class="bs-example4" data-example-id="contextual-table">
			<button type="button" id="import" class="btn btn_5 btn-lg btn-primary">
				导入
			</button>
			<button type="button" id="exportAll" class="btn btn_5 btn-lg btn-primary">
				导出全部
			</button>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th>序号</th>	
						<th>合作方</th>
						<th>应用名称</th>
						<th>渠道号</th>
						<th>激活数</th>										
						<th>单价</th>										
						<th>总额</th>										
						<th>开放状态</th>
						<th>合作方式</th>	
						<th>日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="np" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td>${i.count}</td>
							<td>${np.partner}</td>
							<td>${np.appName}</td>
							<td>${np.channelNum}</td>
							<td>${np.activeNum}</td>
							<td>${np.fee}</td>
							<td>${np.sumFee}</td>
							<td>${np.status}</td>
							<td>${np.partnerType}</td>
							<td>${np.curday}</td>
						</tr>
					</c:forEach>
				  <c:forEach items="${pages.list}" var="use" >
			             <c:set var="sumNum" value="${sumNum+use.activeNum}"/>
			             <c:set var="totalFee" value="${totalFee+use.sumFee}"/>
			      </c:forEach> 
			      <p>
				      <label>总激活数：${sumNum}</label>&nbsp;&nbsp;&nbsp;
				      <label>总价：<fmt:formatNumber value="${totalFee}" pattern="0.00"/></label>
				      <span style="float:right">渠道数据</span>
			      </p>
				</tbody>
			</table>
		</div>
		<!-- 动态include分页pageTool.jsp的界面-->
		<jsp:include page="/common/pageTool.jsp">
			<jsp:param value="${pages.count }" name="count" />
			<jsp:param value="${pages.pages }" name="pages" />
			<jsp:param value="${pages.pageNum }" name="pageNum" />
			<jsp:param value="${pages.size }" name="size" />
		</jsp:include>
	</div>
	<!-- 导入EXCEL表单 -->
	<div id="importFormLay" style="display: none">
		<form action="${ctx}/data/channel/importExcel.do" enctype="multipart/form-data" method="post">
			<input id="upfile" type="file" name="upfile">
		</form>
	</div>
	<!-- 导出EXCEL表单，指定文件名 -->
	<div id="exportAllFormLay" style="display: none">
		<form action="${ctx}/data/channel/exportAll.do" method="post">
			文件名: <input type="text" name="fileName">
		</form>
	</div>
</body>
</html>