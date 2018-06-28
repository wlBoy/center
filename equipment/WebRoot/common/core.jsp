<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<!-- 基本样式 -->
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="${ctx}/css/font.css">
<link rel="stylesheet" href="${ctx}/css/xadmin.css">
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script src="${ctx}/lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/xadmin.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<!--分页需要的文件 -->
<link rel="stylesheet" href="${ctx}/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/js/bootstrap-table/bootstrap-table.min.css">
<script src="${ctx}/js/bootstrap/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-table/bootstrap-table.js"></script>
