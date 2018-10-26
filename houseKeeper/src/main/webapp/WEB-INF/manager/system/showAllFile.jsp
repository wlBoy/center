<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>分页显示所有的文件</title>
<style type="text/css">
.con {
	width: 95%;
	margin: 50px auto;
}
.bs-example4 tbody tr:hover {
	background-color: #ABCDEF;
	cursor:pointer;
}
.contact {
	width: 80%;
	height: 80px;
	margin:10px auto;
}
.table , .table th{
	text-align: center;
}
.modal-content{
	top:100px;
	right:24px;
}
#query{
	position: relative;
	top:-5px;
	left:30px;
}
.title{
	float:right;
	font-size:16px;
}
</style>
<script type="text/javascript">
/*弹出层*/
$(function(){
	// 修改弹出层
	$(".update").click(function() {
		var modal = showNormalLay("修改文件信息",$("#updateFormLay"),function(){
			// 修改表单提交地址，非空检验后提交表单
			$("#modal2 form").attr("action","${ctx}/system/file/update.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("220px");
		modal.setWidth("400px");
		// 回显数据
		var fid = $(this).attr("value");
		$.post("${ctx}/system/rest/findByFileId.do","fileId="+fid,function(f){
			$("#modal2 form :text[name=fileName]").val(f.data.fileName).attr("readonly","readonly");
			$("#modal2 form :text[name=remark]").val(f.data.remark);
			$("<input type='hidden' name='fileId' value='"+f.data.fileId+"'>").appendTo($("#modal2 form"));
		},"json"); 
	});
	// 上传文件弹出层
	$("#uploadFile").click(function() {
		var modal = showNormalLay("上传文件",$("#uploadFileFormLay"),function(){
			// 修改表单提交地址，提交表单
			$("#modal2 form").attr("action","${ctx}/system/file/uploadFile.do");
			$("#modal2 form").submit();
		});
		modal.show();
		modal.setHeigth("100px");
		modal.setWidth("600px");
	});
})
/*复选框联动*/
function check(isChecked) {
	$('.box').each(function(i, o) {
		$(o).prop("checked", isChecked);
	});
}
/*删除多条记录*/
function doDeleteMore(){
swal({
	title: "删除用户", 
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
				param += "fileIds="+o.value+"&";
			}
		});
		if(isCkecked){
			location = "${ctx}/system/file/delete.do?"+param;
		}else{
			swal("OMG!", "请选择你要删除的记录！", "error");
		}
	});
}
/*删除一条记录*/
function doDelete(fid){
swal({
	title: "删除用户", 
	text: "你确定删除这条记录?", 
	type: "warning",
	showCancelButton: true,
	closeOnConfirm: false,
	confirmButtonText: "确定",
	confirmButtonColor: "#ec6c62"
	}, function() {
		location = "${ctx}/system/file/delete.do?fileIds="+fid;
	});
}
</script>
</head>
<body>
	<div class="contact">
		<!-- 表单区,用来处理用户的查询提交数据 -->
		<form id="pageFm" class="form-horizontal" action="${ctx}/system/file/showAllFile.do" method="post">
			<input id="pageNum" type="hidden" name="pageNum"> <input
				value="${pages.size}" id="size" type="hidden" name="size">
			<fieldset>
            <div class="row">
             <div class="col-md-3">
              	<div class="form-group">
		            <label class="control-label col-md-5">文件名</label>
		            <div class="col-md-7">
		              <input class="form-control" name="fileName" value="${pages.bean.fileName}" type="text">
		            </div>
            	</div>
              </div>
             <div class="col-md-3">
              	<div class="form-group">
		            <label class="control-label col-md-4">用户</label>
		            <div class="col-md-8">
		           		<select name="uploadBy" class="form-control">
							<option value="" selected="selected">--请选择--</option>
							<c:forEach var="u" items="${users}">
		           				<c:if test="${pages.bean.uploadBy==u.userId}">
			           				<option value="${u.userId}" selected="selected">${u.userName}</option>
			           			</c:if>
			           			<c:if test="${pages.bean.uploadBy!=u.userId}">
			           				<option value="${u.userId}">${u.userName}</option>
			           			</c:if>
		           			</c:forEach>
						</select>
		            </div>
          	    </div>
	          </div>	
	          <div class="col-md-3">
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
	<!-- 主题内容区 -->
	<div class="con">
		<div class="bs-example4" data-example-id="contextual-table">
			<button type="button" id="uploadFile" class="btn btn_5 btn-lg btn-primary">上传</button>
			<button type="button" id="delete"
				class="btn btn_5 btn-lg btn-Danger" onclick="doDeleteMore();">批删</button>
			<span class="title">所有文件列表</span>
			<table class="table">
				<thead>
					<tr bgcolor="#D9edf7">
						<th><input type="checkbox" onclick="check(this.checked)"></th>
						<th>序号</th>
						<th>文件名</th>
						<th>文件类型</th>
						<th>上传人</th>
						<th>上传日期</th>
						<th>上传时间</th>
						<th>最后更新时间</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="f" items="${pages.list}" varStatus="i">
						<tr bgcolor="${i.count%2==0?'#DEDEDE':'#ffffff'}">
							<td><input type="checkbox" class="box" value="${f.fileId}"></td>
							<td>${i.count}</td>
							<td>${f.fileName}</td>
							<td>${f.fileTypeName}</td>
							<td>${f.uploadByName}</td>
							<td>${f.curday}</td>
							<td>${f.uploadTime}</td>
							<td>${f.updateTime}</td>
							<td>${f.remark}</td>
							<td>
								<a href="javascript:;" class="update btn btn_5 btn-xs btn-info" value="${f.fileId}">修改</a>
								<a href="${ctx}/system/file/downloadFile.do?fileId=${f.fileId}" class="btn btn_5 btn-xs btn-info">下载</a>
								<a href="javascript:;" class="btn btn_5 btn-xs btn-Danger" onclick="doDelete('${f.fileId}');">删除</a>
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
	<!-- 上传文件弹出层表单 -->
	<div id="uploadFileFormLay" style="display: none">
		<form class="form-horizontal" method="post" enctype="multipart/form-data">
		 <div class="form-group" style="margin-top:30px;">
	            <label class="control-label col-md-2">上传文件</label>
	            <div class="col-md-9">
	              <div class="fileupload fileupload-new" data-provides="fileupload">
	                <div class="input-group">
	                  <div class="form-control">
	                    <i class="icon-file fileupload-exists"></i><span class="fileupload-preview"></span>
	                  </div>
	                  <div class="input-group-btn">
	                    <a class="btn btn-default fileupload-exists" data-dismiss="fileupload" href="#">移除</a><span class="btn btn-default btn-file"><span class="fileupload-new">选择</span><span class="fileupload-exists">更改</span><input type="file" name="uploadFile"></span>
	                  </div>
	                </div>
	              </div>
	            </div>
          </div>
          <div class="form-group">
	            <label class="control-label col-md-2">备注信息</label>
	            <div class="col-md-9">
	              <input class="form-control" name="remark" type="text">
	            </div>
         </div>
		</form>
	</div>
	<!-- 修改弹出层表单 -->
	<div id="updateFormLay" style="display: none">
		<form class="form-horizontal" method="post">
			<div class="form-group">
	            <label class="control-label col-md-3">文件名</label>
	            <div class="col-md-8">
	              <input class="form-control" name="fileName" type="text">
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