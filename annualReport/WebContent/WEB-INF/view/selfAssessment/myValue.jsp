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

    <title>年终总结报告系统-我的自评页面</title>

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
    	#selfAssessmentBtn:disabled{
			cursor: wait;
		}
    </style>
</head>

<body>

<div id="wrapper">

       <%@include file="../common/head.jsp"%>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header1">我的自评</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <form id="data">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            年终考核标准与评估
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <%--表头包含的内容--%>
                                    <thead>
                                    <tr>
                                        <th width="5%">序号</th>
                                        <th width="10%">考核项</th>
                                        <th width="5%">加权系数</th>
                                        <th width="50%">考核内容与标准</th>
                                        <th width="10%">分值</th>
                                        <th width="10%">自评分</th>
                                        <th width="10%">自评</th>
                                    </tr>
                                    </thead>
                                        <%--填写各项分数--%>
                                        <tbody>
                                        <tr>
                                            <td rowspan="4">1</td>
                                            <td  rowspan="4">计划管理</td>
                                            <td  rowspan="4">10%</td>
                                            <td >能非常有效地制定自我工作计划，并能准确地预计工作期限，并对执行中可能产生的问题进行准确的预测和制定预案，并能够在工作中总结经验提高计划能力。</td>
                                            <td>101-120</td>
                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input type="text" class="form-control" id="pm" name="pm" value="<c:if test="${not empty selfAssessment }">${selfAssessment.PM}</c:if>" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('pm',0.1)"/>
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="truepm" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >比较有效地制定自我工作计划，不会出现计划不能完成的情况。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >能够制定自我工作计划，但是处于对执行中所能遇到的问题预计不准而偶有出现计划不能完成的情况，考核期内未按计划完成情况3次以内。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >能制定大致的计划，但计划效果差，常出差错，考核期内出现未按计划完成情况3次以上。</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td rowspan="4">2</td>
                                            <td  rowspan="4">执行力</td>
                                            <td  rowspan="4">20%</td>
                                            <td >能够准确地理解领导意图，在全面考虑各方面因素后在规定时间内圆满完成任务，并第一时间反馈结果，得到直接主管的高度认可。</td>
                                            <td>101-120</td>

                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input type="text" id="ic" name="ic" class="form-control"  value="<c:if test="${not empty selfAssessment }">${selfAssessment.IC}</c:if>"  <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('ic',0.2)">
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="trueic" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >能够比较好地理解领导的意图，并及时完成任务，但偶有忽视结果反馈需要领导追问的情况。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >基本能理解领导的意图，但偶有出现不能完成任务的情况；或偶有不能很好地历届领导意图导致完成结果产生加大偏差的情况，此类情况不超过3次。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >较多出现不能正确理解领导意图或不按时完成任务的情况，常出差错。</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td rowspan="4">3</td>
                                            <td  rowspan="4">解决力</td>
                                            <td  rowspan="4">20%</td>
                                            <td >接到任何问题都能在4小时内给予答复，24小时内给予处理完毕，且得到公司内部同事、外部客户的高度认可和评价；无内、外书面投诉。</td>
                                            <td>101-120</td>
                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input type="text" id="sv" name="sv" class="form-control"  value="<c:if test="${not empty selfAssessment }">${selfAssessment.SV}</c:if>" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('sv',0.2)">
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="truesv" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >接到问题都能在4小时内给予答复，并48小时内处理完毕或拿出初步解决方案，无内、外部书面投诉。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >接到问题在8小时内给予答复，并在48小时拿出解决方案、落实完成，考核周期内有少于3项书面投诉。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >接到问题在8小时内答复，但并没有提出解决方案，也未落实完成，考核期内有超过3项书面投诉。</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td rowspan="4">4</td>
                                            <td  rowspan="4">挑战承压</td>
                                            <td  rowspan="4">10%</td>
                                            <td >在工作中遇到艰巨任务能够持之以恒，不为外界不利因素和干扰所影响，没有任何抱怨，以最大的热情和干劲完成任务。</td>
                                            <td>101-120</td>
                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input id="rp" name="rp" class="form-control"  value="<c:if test="${not empty selfAssessment }">${selfAssessment.RP}</c:if>" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('rp',0.1)">
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="truerp" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >在工作中遇到困难能够承受压力，主动想办法解决，尽力完成工作任务，考核期内未按要求完成工作在3项以内。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >在工作中遇到困难后，通常是自己想办法解决，但是偶有逃避而无法完成工作任务的情况，考核期内未按要求完成工作在6项以内。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >在工作中遇到困难后，经常不是自己先想办法解决，而是拖延或逃避，从而无法完成工作任务，考核期内未按要求完成工作超过6项。</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td rowspan="4">5</td>
                                            <td  rowspan="4">尽职自律</td>
                                            <td  rowspan="4">10%</td>
                                            <td >任何情况都遵守时间约定，能勇于接受艰巨任务，承担责任，讲求奉献而不计回报，为公司树立了榜样。</td>
                                            <td>101-120</td>
                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input id="sd" name="sd" class="form-control"  value="<c:if test="${not empty selfAssessment }">${selfAssessment.SD}</c:if>" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('sd',0.1)">
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="truesd" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >勇于接受任务，承担责任，并在工作中有较强的成本控制意识。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >能够履行岗位职责，承担应有的责任。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >大多数情况能够履行岗位职责，偶尔出现逃避责任或先计较报酬的情况。</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td rowspan="4">6</td>
                                            <td  rowspan="4">工作态度和协作精神</td>
                                            <td  rowspan="4">10%</td>
                                            <td >能与他人密切合作，共同圆满地完成团队目标；乐于分享资源，乐于协助同事解决问题，并尊重他人的意见、积极参加团队活动。</td>
                                            <td>101-120</td>
                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input id="ts" name="ts" class="form-control"  value="<c:if test="${not empty selfAssessment }">${selfAssessment.TS}</c:if>" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('ts',0.1)">
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="truets" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >能与他人合作，并在目标完成上取得较好效果。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >能与他人合作沟通，但有时表现出个人主义倾向；在资源分享、协助同事上表现不积极或对团队活动表现出不关注、不积极。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >很少与团队成员合作，过分个人主义，缺乏集体观念。</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td rowspan="4">7</td>
                                            <td  rowspan="4">文明建设和制度执行</td>
                                            <td  rowspan="4">10%</td>
                                            <td >从未出现迟到、早退、旷工、请假等情况，严格遵守公司各项规章制度，倡导文明。</td>
                                            <td>101-120</td>
                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input id="si" name="si" class="form-control"  value="<c:if test="${not empty selfAssessment }">${selfAssessment.SI}</c:if>" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('si',0.1)">
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="truesi" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >极少出现迟到、早退、旷工、请假等情况，考核期内出现总次数控制3次以内，较好遵守公司规章制度，环境建设。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >迟到、早退、旷工、请假等情况考核周期内出现次数控制在6次以内，基本能够遵守公司规章制度，爱护环境。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >迟到、早退、旷工、请假等情况考核周期内出现次数超过6次以上，无视公司规章制度的存在，不文明。</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td rowspan="4">8</td>
                                            <td  rowspan="4">保密工作</td>
                                            <td  rowspan="4">10%</td>
                                            <td >有很强保密意识，有计划宣传且主导完成保密工作。</td>
                                            <td>101-120</td>
                                            <td  rowspan="4">
                                                <div class="form-group">
                                                    <input id="cw" name="cw" class="form-control"  value="<c:if test="${not empty selfAssessment }">${selfAssessment.CW}</c:if>" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if> onchange="getScore('cw',0.1)">
                                                </div>
                                            </td>
                                            <td  rowspan="4"><input id="truecw" class="form-control"  readonly="readonly"></td>
                                        </tr>
                                        <tr>
                                            <td  >熟悉保密制度和职责、能执行保密流程、能积极参加保密教育，考核80份以上、按时提交保密自查报告。</td>
                                            <td>81-100</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >一般了解保密制度和职责、基本执行保密流程、基本参加保密教育，考核合格、能提交保密自查报告。</td>
                                            <td>61-80</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        <tr>
                                            <td  >告知后能遵守保密制度职责、告知后能执行保密流程、告知后能参加保密教育、告知后能提交保密自查报告</td>
                                            <td>41-60</td>
                                            <%--<td><input class="form-control" ></td>--%>
                                            <%--<td><input class="form-control" ></td>--%>
                                        </tr>
                                        </tbody>
                                        <%--填写的分数总分展示--%>
                                        <tfoot>
                                        <tr>
                                            <td colspan="2">总分</td>
                                            <td colspan="1">100%</td>
                                            <td colspan="3"></td>
                                            <td><label id="sum"></label></td>
                                        </tr>
                                        </tfoot>
                                </table>
                            </div>
                            <div class="form-group">
                                <label>优秀典型事例</label>
                                <textarea class="form-control" id="excellentExamples"  rows="10" placeholder="请填写您的优秀典型事例" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if>><c:if test="${not empty selfAssessment }">${selfAssessment.EXCELLENT_EXAMPLES}</c:if></textarea>
                            </div>
                            <div class="form-group">
                                <label>自我评语</label>
                                <textarea class="form-control" id="selfComment" name="selfComment" rows="10" placeholder="请填写您的自我评语" <c:if test="${not empty selfAssessment }"> readonly="readonly" </c:if>><c:if test="${not empty selfAssessment }">${selfAssessment.SELF_COMMENT}</c:if></textarea>
                            </div>
                            <input type="hidden" id="assessmentId" value="<c:if test="${not empty selfAssessment }">${selfAssessment.SELF_ASSESSMENT_ID}</c:if>">
                            <%--判断是否需要展示提交按钮，对于已经提交过自我评价的则不需要展示提交按钮--%>
                            <c:if test="${empty selfAssessment }">
                                <div class="operatorBtnDiv" style="text-align:center;margin-bottom:20px;">
                                    <button type="button"  id="selfAssessmentBtn" class="btn btn-primary"
                                            style="width: 200px">提交</button>
                                </div>
                            </c:if>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
            </div>
        </form>
			
			
			

    </div>
    <!-- /#page-wrapper -->

</div>
<script type="text/javascript">
    $(document).ready(function() {
        $('#data').bootstrapValidator({
            message: '无效值',
            feedbackIcons: {
                valid: '',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                pm: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                ic: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                sv: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                rp: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                sd: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                ts: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                si: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                cw: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入具体数值'
                        },
                        between: {
                            min: 41,
                            max: 120,
                            message: '请输入介于41到120的值！'
                        }
                    }
                },
                selfComment: {
                    message: '值无效',
                    validators: {
                        notEmpty: {
                            message: '必填项，请输入！'
                        }
                    }
                },
            }
        });
    });
</script>
<!-- /#wrapper -->
<script>

    $(function () {
        getScore("pm",0.1);
        getScore('ic',0.2)
        getScore("sv",0.2);
        getScore("rp",0.1);
        getScore("sd",0.1);
        getScore("ts",0.1);
        getScore("si",0.1);
        getScore("cw",0.1);
    });
    function getScore(x,y){
        var pm = $("#" + x);
        var pmVal = pm.val();
        var truePm = $("#true" + x);
        if (!isNaN(pmVal)){
            truePm.val((pmVal*y).toFixed(1));
        }else {
            truePm.value=0;
        }
        getSum();
    };
    function getSum(){
        var pmScore = $('#truepm').val();
        var icScore = $('#trueic').val();
        var svScore = $('#truesv').val();
        var rpScore = $('#truerp').val();
        var sdScore = $('#truesd').val();
        var tsScore = $('#truets').val();
        var siScore = $('#truesi').val();
        var cwScore = $('#truecw').val();
        var sum = document.getElementById("sum");
        sum.innerText = (Number(pmScore)+Number(icScore)+Number(svScore)+Number(rpScore)+Number(sdScore)+Number(tsScore)+Number(siScore)+Number(cwScore)).toFixed(1);
    }

    $('#selfAssessmentBtn').click(function () {

        $('#data').bootstrapValidator('validate');
        //ajax提交
        if (!$('#data').data('bootstrapValidator').isValid()) {
            return;
        }
     	// 将按钮变为不可用
		$("#selfAssessmentBtn").attr('disabled',true);
        var pmScore = $('#pm').val();
        var icScore = $('#ic').val();
        var svScore = $('#sv').val();
        var rpScore = $('#rp').val();
        var sdScore = $('#sd').val();
        var tsScore = $('#ts').val();
        var siScore = $('#si').val();
        var cwScore = $('#cw').val();
        var excellentExamplesContent = $('#excellentExamples').val();
        var selfComment = $('#selfComment').val();
        var assessmentId = $('#assessmentId').val();
        if (pmScore !== '' && icScore !== '' && svScore !== '' && rpScore !== '' && sdScore !== '' && tsScore !== '' &&
            siScore !== '' && cwScore !== '') {
            if (assessmentId !== null && assessmentId !== '' && assessmentId !== undefined) {
                alert("您已提交过，无法再次提交！");
            } else {
                if (window.confirm("您确认提交吗？提交后将不可再做修改！")) {
                    $.post('self?op=saveAssessment', {
                        "PM": pmScore,
                        "IC": icScore,
                        "SV": svScore,
                        "RP": rpScore,
                        "SD": sdScore,
                        "TS": tsScore,
                        "SI": siScore,
                        "CW": cwScore,
                        "excellentExamples": excellentExamplesContent,
                        "selfComment": selfComment,
                    }, function (data) {
                        var result = JSON.parse(data);
                        if (result.res === "1") {
                            alert("提交成功！");
                       		window.location.reload();
                        } else {
                            alert("提交失败！");
                        }
	                    // 将按钮变为可用
	                	$("#selfAssessmentBtn").attr('disabled',false);
                    }, 'text');
                }
            }

        } else {
            alert("您尚有选项未评分，请先评完分！");
        }


    });
</script>
</body>

</html>
