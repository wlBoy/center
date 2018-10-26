<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="page" var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!-- 后台首页界面 -->	
<link href="${ctx}/seven/stylesheets/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/se7en-font.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/isotope.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/fullcalendar.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/wizard.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/morris.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/datatables.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/timepicker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/colorpicker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/bootstrap-switch.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/daterange-picker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/typeahead.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/summernote.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/pygments.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/style.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ctx}/seven/stylesheets/color/green.css" media="all" rel="alternate stylesheet" title="green-theme" type="text/css" />
<link href="${ctx}/seven/stylesheets/color/orange.css" media="all" rel="alternate stylesheet" title="orange-theme" type="text/css" />
<link href="${ctx}/seven/stylesheets/color/magenta.css" media="all" rel="alternate stylesheet" title="magenta-theme" type="text/css" />
<link href="${ctx}/seven/stylesheets/color/gray.css" media="all" rel="alternate stylesheet" title="gray-theme" type="text/css" />
<script src="${ctx}/seven/javascripts/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery-ui.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/raphael.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/selectivizr-min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.mousewheel.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.vmap.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.vmap.sampledata.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.vmap.world.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.bootstrap.wizard.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/fullcalendar.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/gcal.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/datatable-editable.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.easy-pie-chart.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/excanvas.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.isotope.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/isotope_extras.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/modernizr.custom.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/select2.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/styleswitcher.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/wysiwyg.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/summernote.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.inputmask.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/bootstrap-timepicker.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/bootstrap-colorpicker.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/typeahead.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/daterange-picker.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/date.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/morris.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/skycons.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/fitvids.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/jquery.sparkline.min.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/main.js" type="text/javascript"></script>
<script src="${ctx}/seven/javascripts/respond.js" type="text/javascript"></script>

<!-- 弹出层 -->
<link rel="stylesheet" href="${ctx}/plugins/openplugus/css/bootstrapEx.css">
<script type="text/javascript" src="${ctx}/plugins/openplugus/js/bootstrapEx.js"></script>
<script type="text/javascript" src="${ctx}/plugins/openplugus/js/function.js"></script>

<!-- 日期插件 -->
<link type="text/css" rel="stylesheet" href="${ctx}/plugins/jedate/jedate/jedate.css">
<script type="text/javascript" src="${ctx}/plugins/jedate/jedate/jquery.jedate.js"></script>
<script type="text/javascript" src="${ctx}/plugins/my97date/WdatePicker.js"></script>
<!-- 倒计时插件 -->
<link type="text/css" rel="stylesheet" href="${ctx}/plugins/countdown/jquery.countdown.css">
<script type="text/javascript" src="${ctx}/plugins/countdown/jquery.countdown.js"></script>
<!-- 提示框插件 -->
<link type="text/css" rel="stylesheet" href="${ctx}/plugins/SweetAlert/sweetalert.css">
<script type="text/javascript" src="${ctx}/plugins/SweetAlert/sweetalert.min.js"></script>

${msg}
<!-- 提示信息 -->
<c:if test="${not empty sessionScope.msg}">
	<c:remove var="msg" scope="session"/>
</c:if>
