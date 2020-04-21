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
    <title>年终总结报告系统-评选详情页面</title>
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
                       	${voteResult.voteYear}年度评选
                    </div>
                    <!-- /.panel-heading -->
                    <form id="data">
                        <div class="panel-body">
                        	<c:forEach items="${prizeTypeList}" var="prizeType">
                              <div class="form-group">
                               	   <!-- 其他奖  -->
                               	   <label>${prizeType.desc}(${prizeType.count}名)</label>
	                               <div class="voteContent" id="${prizeType.code}_show"></div>
                               </div>
                        	</c:forEach>
                            <div class="form-group" style="margin-top:60px;">
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
<script>
	getVoteDetailData();
	// 获取投票结果
	function getVoteDetailData(){
		// 汇总今年所有的投票结果
    	$.post('vote?op=getVoteDetail', {
    		
        }, function (data) {
        	 $(data).each(function(index,obj){
        		 appendSpanByPrize(obj);
        	 });
        }, 'json');
	}
	//将投票结果追加到显示区
	function appendSpanByPrize(obj){
		$("#"+obj.prizeType+"_show").append("<div style='display:inline-block;position:absoulte;margin-right:10px;'><span class='btn btn-default' title='"+obj.candidateDept+"' style='text-align:center;'>"+obj.candidateName+"</span></div>");
	}
</script>
</body>

</html>
