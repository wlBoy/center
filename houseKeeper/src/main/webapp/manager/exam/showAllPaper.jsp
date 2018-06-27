<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>显示所有的后台试卷</title>
		<style type="text/css">
.con {
	width: 100%;
	margin: 10px auto;
}
li{
	list-style: none;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 1300px;
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
	top:80px;
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
/*弹出层*/
$(function(){
	// 修改弹出层
	$(".update").click(function() {
		var modal = showNormalLay("修改试卷信息",$("#updateFormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/exam/paper/update.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("280px");
		modal.setWidth("400px");
		/*弹出层加载时间插件*/
		loadTime();
		var pid = $(this).attr("value");
		// 回显数据
		$.get("${ctx}/exam/rest/findByPaperId.do","paperId="+pid,function(p){
			$("#modal2 form :text[name=paperName]").val(p.paperName);
			$("#modal2 form :text[name=totalTime]").val(p.totalTime);
			$("#modal2 form :text[name=startTime]").val(p.startTime);
			$("#modal2 form :text[name=endTime]").val(p.endTime);
			$("#modal2 form :text[name=remark]").val(p.remark);
			$("<input type='hidden' name='paperId' value='"+p.paperId+"'>").appendTo($("#modal2 form"));
		},"json"); 
	});
	// 添加弹出层
	$("#add").click(function() {
		var modal = showNormalLay("添加试卷信息",$("#FormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/exam/paper/add.do");
			if(checkTotalScore()){
				// 符合总分才能添加试卷
				$("#modal2 form").submit();
			}
		});
		modal.show();
		modal.setHeigth("430px");
		modal.setWidth("600px");
		/*弹出层加载时间插件*/
		loadTime();
	});
})
/*弹出层加载时间插件*/
function loadTime(){
	// 加载日期插件
	$("#modal2 #date1").jeDate({
	    //isinitVal:true,
	    festival:true,
	    ishmsVal:false,
	    minDate: '2016-06-16 23:59:59',
	    maxDate: '2099-12-31 23:59:59',
	    format:"YYYY-MM-DD hh:mm:ss",
	    zIndex:3000,
	});
	$("#modal2 #date2").jeDate({
	    //isinitVal:true,
	    festival:true,
	    ishmsVal:false,
	    minDate: '2016-06-16 23:59:59',
	    maxDate: '2099-12-31 23:59:59',
	    format:"YYYY-MM-DD hh:mm:ss",
	    zIndex:3000,
	});
}
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除试卷", 
	text: "你确定删除这些记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		var param = "";
		var isCkecked = false;
		$(".box").each(function(i,o){
			if(o.checked){
				isCkecked = true;
				param += "paperIds="+o.value+"&";
			}
			
		});
		if(isCkecked){
			location = "${ctx}/exam/paper/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*符合总分才能添加试卷*/
function checkTotalScore(){
	var paperScore = $("#modal2 #paperScore").val();
	var nums = $("#modal2 .typeNum");
	var scores = $("#modal2 .typeScore");
	var sum = 0;
	for (var i = 0; i < nums.length; i++) {
		sum+=nums[i].value*scores[i].value;
	}
	if(sum==paperScore){
		$("#modal2 #tip").html("");
		return true;
	}else{
		$("#modal2 #tip").html("<span style='color:red;'>你分配的总分为:"+sum+",不符合总分"+paperScore+",不能创建试卷!</span>");
		return false;
	}
} 
/*删除一条记录*/
function doDelete(pid){
swal({
	title: "删除试卷", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/exam/paper/delete.do?paperIds="+pid;
	});
}
/*复选框联动*/
function check(isChecked){
	$('.box').each(function(i,o){
		$(o).prop("checked",isChecked);
	});
}
/*切换状态*/
function changeState(pid){
swal({
	title: "切换试卷状态", 
	text: "你确定切换试卷状态?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/exam/paper/changeState.do?paperId="+pid;
	});
}
</script>
	</head>
	<body>
		<div class="tip">
			温馨提示:试卷冻结了就不能进行考试,只能修改试卷基本信息!
		</div>
		<div class="contact">
			<!-- 表单区,根据查询条件筛选列表数据 -->
			<form id="pageFm" class="form-horizontal" action="${ctx}/exam/paper/showAllPaper.do"
				method="post">
				<input id="pageNum" type="hidden" name="pageNum">
				<input value="${pages.size}" id="size" type="hidden" name="size">
				<fieldset>
	             <div class="row">
	               <div class="col-md-3">
	              	<div class="form-group">
			            <label class="control-label col-md-3">试卷名称</label>
			            <div class="col-md-8">
			              <input class="form-control" name="paperName" value="${pages.bean.paperName}" type="text">
			            </div>
	            	</div>
	               </div>
	               <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-4">出卷人</label>
			            <div class="col-md-7">
			              	<select name="createPaperId" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:forEach var="u" items="${users}">
			           				<c:if test="${pages.bean.createPaperId==u.userId}">
				           				<option value="${u.userId}" selected="selected">${u.userName}</option>
				           			</c:if>
				           			<c:if test="${pages.bean.createPaperId!=u.userId}">
				           				<option value="${u.userId}">${u.userName}</option>
				           			</c:if>
			           			</c:forEach>
							</select>
			            </div>
	            	</div>
	               </div>
                   <div class="col-md-2">
	              	<div class="form-group">
			            <label class="control-label col-md-5">试卷总分</label>
			            <div class="col-md-7">
			           		<select name="paperScore" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:if test="${pages.bean.paperScore == 100}">
									<option value="100" selected="selected">100</option>
								</c:if>
								<c:if test="${pages.bean.paperScore != 100}">
									<option value="100">100</option>
								</c:if>
								<c:if test="${pages.bean.paperScore == 120}">
									<option value="120" selected="selected">120</option>
								</c:if>
								<c:if test="${pages.bean.paperScore != 120}">
									<option value="120">120</option>
								</c:if>
								<c:if test="${pages.bean.paperScore == 150}">
									<option value="150" selected="selected">150</option>
								</c:if>
								<c:if test="${pages.bean.paperScore != 150}">
									<option value="150">150</option>
								</c:if>
							</select>
			            </div>
	          		  </div>
          		  </div>
	              <div class="col-md-2">
	              	 <div class="form-group">
			            <label class="control-label col-md-5">试卷状态</label>
			            <div class="col-md-7">
			           		<select name="isAllowed" class="form-control">
								<option value="" selected="selected">-请选择-</option>
								<c:if test="${pages.bean.isAllowed == 0}">
									<option value="0" selected="selected">正常</option>
								</c:if>
								<c:if test="${pages.bean.isAllowed != 0}">
									<option value="0">正常</option>
								</c:if>
								<c:if test="${pages.bean.isAllowed == 1}">
									<option value="1" selected="selected">冻结</option>
								</c:if>
								<c:if test="${pages.bean.isAllowed != 1}">
									<option value="1">冻结</option>
								</c:if>
							</select>
			            </div>
	          		 </div>
	               </div>
	               <div class="col-md-2">
		              	<div class="form-group">
				            <label class="control-label col-md-5">创建日期</label>
				            <div class="col-md-7">
				              <input onClick="WdatePicker({dateFmt:'yyyyMMdd'})" readonly="readonly"
						    	type="text"  class="form-control" name="curday" value="${pages.bean.curday}"/>
				            </div>
		            	</div>
	              </div>
	              <div class="col-md-1">
	              		<input type="submit" value="查询" class="btn btn_5 btn-lg btn-primary" id="query">
	              </div>
	          </div>
         	 </fieldset>
			</form>
		</div>
		<div style="clear: both"></div>
		<!-- 表格区,列表内容显示区 -->
		<div class="con">
			<div class="bs-example4" data-example-id="contextual-table">
				<button type="button" id="add" class="btn btn_5 btn-lg btn-primary">添加</button>
				<button type="button" id="delete"
					class="btn btn_5 btn-lg btn-Danger" onclick="doDeleteMore();">批删</button>
				<span class="title">所有试卷列表</span>
				<table class="table">
					<thead>
						<tr bgcolor="#D9edf7">
							<th><input type="checkbox" onclick="check(this.checked)"></th>
							<th>序号</th>
							<th>试卷名称</th>
							<th>出卷人</th>
							<th>试卷总分</th>
							<th>试卷总时间(分)</th>
							<th>开考时间</th>
							<th>结束时间</th>
							<th>创建日期</th>
							<th>创建时间</th>
							<th>更新时间</th>
							<th>试卷状态</th>
							<th>备注信息</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${pages.list}" varStatus="i">
							<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
								<td><input type="checkbox" class="box" value="${p.paperId}"></td>
								<td>${i.count}</td>
								<td>${p.paperName}</td>
								<td>${p.createPaperName}</td>
								<td>${p.paperScore}</td>
								<td>${p.totalTime}</td>
								<td>${p.startTime}</td>
								<td>${p.endTime}</td>
								<td>${p.curday}</td>
								<td>${p.createTime}</td>
								<td>${p.updateTime}</td>
								<!-- 状态切换区 -->
								<td>
									<c:if test="${p.isAllowed==0}">
										<a href="javascript:;" onclick="changeState(${p.paperId});"
											class="label label-info state1">正常</a>
									</c:if>
									<c:if test="${p.isAllowed==1}">
										<a href="javascript:;" onclick="changeState(${p.paperId});"
											class="label label-danger state2">冻结</a>
									</c:if>
								</td>
								<td>${p.remark}</td>
								<td>
									<a href="javascript:;" class="update btn btn_5 btn-xs btn-info"
										value="${p.paperId}">修改</a>
									<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger"
										onclick="doDelete(${p.paperId});">删除</a>
								</td>
							</tr>
						</c:forEach>
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
		<!-- 弹出层表单 -->
		<div id="FormLay" style="display: none">
			<form class="form-horizontal" method="post">
	          <div class="form-group">
	            <label class="control-label col-md-3">试卷名称</label>
	            <div class="col-md-8">
	              <input class="form-control" name="paperName" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">总时间(分)</label>
	            <div class="col-md-8">
	           		 <input class="form-control" name="totalTime" type="number">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">开考时间</label>
	            <div class="col-md-8">
	           		 <input class="form-control datainp wicon ipt phone" id="date1" readonly="readonly" name="startTime" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">结束时间</label>
	            <div class="col-md-8">
	           		 <input class="form-control datainp wicon ipt phone" id="date1" readonly="readonly" name="endTime" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">试卷总分</label>
	            <div class="col-md-8">
	           		 <select name="paperScore" id="paperScore" class="form-control">
						<option value="100">100</option>
						<option value="120">120</option>
						<option value="150">150</option>
					</select>
	            </div>
	          </div>
	          <c:forEach var="t" items="${types}" varStatus="i">
		          <div class="form-group">
		            <label class="control-label col-md-3">${t.typeName}</label>
		            <input type="hidden" value="${t.typeId}" name="typeIds[${i.index}]">
		            <div class="col-md-3">
		            	<input type="number" name="typeNums[${i.index}]" class="typeNum form-control">
		            </div>
		            <label class="control-label col-md-1">题</label>
		            <div class="col-md-3">
		            	<input type="number" name="typeScores[${i.index}]" class="typeScore form-control">
		            </div>
		            <label class="control-label col-md-1">分</label>
		          </div>
				</c:forEach>
				<div class="form-group">
		            <label class="control-label col-md-3">备注信息</label>
		            <div class="col-md-8">
		           		 <input class="form-control" name="remark" type="text">
		            </div>
	          </div>
	          <div class="form-group">
	            <div class="col-md-11" id="tip" style="text-align:center;"></div>
	          </div>
	        </form>
		</div>
		<!-- 修改弹出层表单 -->
		<div id="updateFormLay" style="display: none">
			<form class="form-horizontal" method="post">
	          <div class="form-group">
	            <label class="control-label col-md-3">试卷名称</label>
	            <div class="col-md-8">
	              <input class="form-control" name="paperName" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">总时间(分)</label>
	            <div class="col-md-8">
	           		 <input class="form-control" name="totalTime" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">开考时间</label>
	            <div class="col-md-8">
	           		 <input class="form-control datainp wicon ipt phone" id="date1" readonly="readonly" name="startTime" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">结束时间</label>
	            <div class="col-md-8">
	           		 <input class="form-control datainp wicon ipt phone" id="date1" readonly="readonly" name="endTime" type="text">
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label col-md-3">备注信息</label>
	            <div class="col-md-8">
	           		 <input class="form-control" name="remark" type="text">
	            </div>
	         </div>
	        </form>
		</div>
	</body>
</html>