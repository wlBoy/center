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
    <title>年终总结报告系统-年度评选页面</title>
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
    	.red{
    		color:#CC0033;
    		font-weight: bold;
    	}
    	.voteContent{
	    	width:100%;
	    	height:30px;
    		border:1px solid #e7e7e7;
    		border-radius:3px;
    	}
    	.checkbox-inline{
    		min-width: 100px;
    		line-height:25px;
    	}
    	.radio-inline + .radio-inline, .checkbox-inline + .checkbox-inline {
		    margin-top: 0;
		    margin-left: 0; 
		}
    	.islocked{
    		disabled:disabled;
    		cursor: not-allowed; 
    		background-color: #e6e6e6;
    	}
    	.form-group {
		    margin-bottom: 15px;
		    margin-left: 30px;
		}
    </style>
</head>

<body>

<div id="wrapper">
    <%@include file="../common/head.jsp"%>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header5">年度评选</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                       	${currentYear}年度评选
                    </div>
                    <!-- /.panel-heading -->
                    <form id="data">
                        <div class="panel-body">
                        	<c:forEach items="${prizeTypeList}" var="prizeType">
                               <c:if test="${prizeType.code == 'goldPrize'|| prizeType.code == 'silverPrize'|| prizeType.code == 'bronzePrize'}">
                               	   <div class="form-group">
	                              	   <!-- 金，银，铜3奖  -->
		                               <label>${prizeType.desc}(${prizeType.count}名)</label><a style="margin-left:10px;cursor:pointer;" id="${prizeType.code}" onclick="openModal('${prizeType.desc}_候选人名单(${prizeType.count}名)',this,'excellentEmployeePrize',${prizeType.count})">投票<i class="fa fa-pencil"></i></a>
		                               <div class="voteContent" id="${prizeType.code}_show"></div>
	                               </div>
                               </c:if>
                               <c:if test="${prizeType.code != 'goldPrize' && prizeType.code != 'silverPrize' && prizeType.code != 'bronzePrize'}">
                               	   <div class="form-group">
	                               	   <!-- 其他奖  -->
	                               	   <label>${prizeType.desc}(${prizeType.count}名)</label><a style="margin-left:10px;cursor:pointer;" id="${prizeType.code}" onclick="openModal('${prizeType.desc}_候选人名单(${prizeType.count}名)',this,'${prizeType.code}',${prizeType.count})">投票<i class="fa fa-pencil"></i></a>
		                               <div class="voteContent" id="${prizeType.code}_show"></div>
	                               </div>
                               </c:if>
                        	</c:forEach>
                            <div class="operatorBtnDiv" style="text-align:center;margin-bottom:20px;">
                                <button type="button"  id="voteBtn" class="btn btn-primary"
                                        style="width: 200px">投票</button>
                            </div>
                            <div class="form-group">
                                 <label>【选票注意事项】</label>
							     <p>1、年度评选的奖项设置为：<c:forEach items="${prizeTypeList}" var="prizeType" varStatus="c">${prizeType.desc}<c:if test="${c.count!=prizeTypeList.size()}">、</c:if></c:forEach>。</p>
							     <p>2、名额候选为：<c:forEach items="${prizeTypeList}" var="prizeType" varStatus="c">${prizeType.desc}（${prizeType.count}名）<c:if test="${c.count!=prizeTypeList.size()}">、</c:if></c:forEach>共计28人次。</p>
							     <p>3、投选时请在投票结果相对应的奖项空格内标注相应编号，每个编号不得<span class="red">重复填写</span>。</p>
							     <p>4、投选时应按候选名额进行投选，每张选票的投选总人次<span class="red">应在13-28人次。</span></p>
							     <p>5、评选必须做<span class="red">跨部门选择，每个奖项投票本部门或产品线不超过2项</span>(即从本选票的投选结果上应反映有不同部门和平台的人员入围)。</p>
							     <p>6、本次为全公司人员<span class="red">实名制</span>进行网络投票。</p> 
							     <p>7、以上排名按姓氏字母排列，不分先后。</p> 
							     <p>8、3-6项中有一项不满足则视为废票。 </p>
                            </div>
                            <div class="form-group">
                            	<p style="text-align:right;"><span style="margin-right:20px;font-weight: bold;">投票人：${userInSession.sName}</span></p>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                    </form>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        	<!-- 模态框标题 -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <!-- 模态框内容 -->
            <div class="modal-body">
            	<div class="form-group" id="dataDiv">
                </div>
            </div>
            <!-- 模态框底部按钮 -->
            <div class="modal-footer" id="confirmDiv">
                <button type="button" class="btn btn-primary" onclick="">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script>
	/*弹出模态框并加载数据*/
	function openModal(title,obj,type,num){
		$("#myModalLabel").html(title);//生成标题
		var flag = getUserData(obj,type);//获取候选人员名单
		$("#confirmDiv").html('<button type="button" class="btn btn-primary" id="'+obj.id+'" onclick="confirmVote(this,'+num+')">确定</button>');//生成确定按钮
		$("#myModal").modal('show');//显示模态框
	}
	/*确定按钮，检验并回显表单数据*/
	function confirmVote(obj,num){
		// 先检查选取个数对不对
		var checkBoxs=$('#dataDiv input[type=checkbox]:checked');
		if(checkBoxs.length>num){
			alert("最多只能选取"+num+"个候选人!");
			return;
		}
		var count = 0;
		var selfDept = '${userInSession.depName}';//自己部门名称
		// 再检查同部门不超过2个
		$(checkBoxs).each(function(index,o){
			var chooseDept = $(o).attr("title");
			if(chooseDept==selfDept){
				count++;
			}
		})
		if(count>2){
			alert("最多选取2个本部门或产品线的候选人!");
			return;
		}
		// 清空候选名单
		$("#"+obj.id+"_show").html('');
		// 拼接选中的人员编号并回显至对应的输入框中
		$(checkBoxs).each(function(index,o){
			$("#"+obj.id+"_show").append("<span userId='"+$(o).val()+"' title='"+$(o).attr("title")+"' style='text-align:center;' class='btn btn-default'>"+$(o).attr("userName")+"</span>&nbsp;");
		})
		// 关闭模态框
		$("#myModal").modal('hide');
	}
	/*ajax获取候选人名单并回显在模态框中*/
	function getUserData(obj,type){
		 var count=0;
		 var ids = getUserId(obj.id+"_show").split(",");
		 //每次清空数据div，防止数据累加
		 $('#myModal #dataDiv').html('');
		 $.post('vote?op=getCandidateUsers', {
	            'userVoteType': type
	     }, function (data) {
	    	 if(data.length==0){
	    		 alert("今年的该奖项候选人名单暂未生成，请联系管理员!");
	    		 return;
	    	 }
	    	 var needs = needLockedArray(obj.id+"_show");
	       	 $(data).each(function(index,obj){
	       		var flag = hasId(ids,obj.id)?'checked':'';
	       		var userDept = obj.userDept==undefined?"无部门": obj.userDept;
	       		count++;
	       		if(isLocked(needs,obj.id)){
		       	 	$('#myModal #dataDiv').append('<label title="'+userDept+'" class="checkbox-inline islocked"><input onclick="return false;" title="'+userDept+'" type="checkbox" '+flag+' userName="'+obj.userName+'" value="'+obj.id+'">'+obj.userName+'</label>');
	       		}else{
		       	 	$('#myModal #dataDiv').append('<label title="'+userDept+'" class="checkbox-inline"><input type="checkbox" '+flag+' title="'+userDept+'" userName="'+obj.userName+'" value="'+obj.id+'">'+obj.userName+'</label>');
	       		}
	       	 	if((count%=10)==0){
       				$('#myModal #dataDiv').append('<div style="height:20px;"></div>');
       			}
	       	 });
	     }, 'json');
	}
	/*判断当前userID是否在needLockedArray数组当中*/
	function isLocked(needs,userId){
		  for(var i = 0; i < needs.length; i++){
			  if(userId==needs[i]){
				  return true;
			  }
		  }
		  return false;
	}
	/*获取金奖，银奖，铜奖需要过滤的ID数组*/
	function needLockedArray(divId){
	  var showArray = ["silverPrize_show","bronzePrize_show","goldPrize_show"];
	  var need = new Array();
	  for(var i = 0; i < showArray.length; i++){
		  var showId = showArray[i];
		  if(divId != showId){
			  need = need.concat(getUserId(showId).split(","))
		  }
	  }
	  return need;
	}
	/*点击投票按钮回显用户*/
	function hasId(ids,id){
		for (var i = 0; i < ids.length; i++) {
			if(ids[i]==id){
				return true;
			}
		}
		return false;
	}
	/*获取用户投票的总票数*/
	function getUserSum(){
		 var sum = new Array();
	     // 所有奖项code字符串
    	 var prizeTypeCode = '${prizeTypeCode}';
    	 var array  = prizeTypeCode.split(",");
		 for(var i = 0; i < array.length; i++){
			 if(array[i]==''){
	    		continue;
	    	 }
			 var userId = getUserId(array[i]+'_show');
			 if(userId!=''){
			 	sum = sum.concat(userId.split(","));
			 }
		 }
		 return sum;
	}
	/*ajax保存用户投票信息*/
	function userVote(params){
		 $.post('vote?op=voteUser', {
			 "params":params
         }, function (data) {
             if (data.res==1) {
                 alert("投票成功！");
                 location='/vote?op=toVoteList';
             } else {
                 alert("投票失败！");
             }
         }, 'json');
	}
	/*拼接用户ID入库*/
	function getUserId(divId){
		var userId ='';
		var objs =  $('#'+divId).find("span");
		objs.each(function(index,obj){
			userId+=$(obj).attr("userId")+",";
		});
		if(userId!=''){
			userId = userId.substring(0,userId.length-1);
		}
		return userId;
	}
	$(function(){
		/*投票按钮事件*/
	    $('#voteBtn').click(function () {
	    	var flag = true;
	    	// 所有奖项code字符串
	    	var prizeTypeCode = '${prizeTypeCode}';
	    	var array  = prizeTypeCode.split(",");
	    	// 获取对应奖项的投票结果拼接成json串作为参数
	    	var params = '{';
	    	for (var i = 0; i < array.length; i++) {
	    		if(array[i]==''){
	    			continue;
	    		}
	    		var voteResult = getUserId(array[i]+'_show');
	    		params += '"'+array[i]+'":"'+voteResult+'",';
			}
	        // 用户投票总数校验，不符合条件为无效票
	        var ticketStatus=0;//投票有效性，0代表有效，1代表无效
	        var tip = '';
	        var userSumArray = getUserSum();
	        if(userSumArray.length < 13){
	        	ticketStatus = 1;
	        	tip = "您的总票数不在13-28票之间，将视为废票，确定是否继续投票？";
	        }else{
	        	tip = "您的总票数为"+userSumArray.length+"票，确认投票吗？";
	        }
	        if (window.confirm(tip)) {
	        	params = params+'"ticketStatus":'+ticketStatus+"}";
	       	    userVote(params)
	        }
	    });
	})
</script>
</body>

</html>
