<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/core.jsp"%>
<html lang="en">
<head>
	<title>水利设备管理系统后台首页</title>
</head>
<body>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo"><a href="#">水利设备管理系统</a></div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
        <ul class="layui-nav left fast-add" lay-filter="">
          <li class="layui-nav-item">
           <a class="time" ></a>
          </li>
        </ul>
        <ul class="layui-nav right" lay-filter="">
          <li class="layui-nav-item">
            <a href="javascript:;">${user.userName}</a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
              <dd><a href="${ctx}/system/user/logout">退出</a></dd>
            </dl>
          </li>
        </ul>
    </div>
    <!-- 顶部结束 -->
     <!-- 左侧菜单开始 -->
    <div class="left-nav">
      <div id="side-nav">
        <ul id="nav">
           <c:forEach var="menu" items="${menuList}">
            <li>
                <a href="javascript:;">
                    <i class="iconfont">${menu.icon}</i>
                    <cite>${menu.moduleName}</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>                              
                <ul class="sub-menu" style="display:block;">
                 <c:forEach var="m" items="${menu.children}">
                    <li>
                        <a _href=".././../${m.actionUrl}">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>${m.moduleName}</cite>
                        </a>
                    </li >
                  </c:forEach> 
                </ul>                            
            </li>
         </c:forEach> 
        </ul>
      </div>
    </div>
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
          <ul class="layui-tab-title">
            <li>我的主页</li>
          </ul>
          <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='${ctx}/manager/welcome.jsp' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright">Copyright ©2018 水利设备管理系统  v1.0 All Rights Reserved</div>  
    </div>
    <script>
  //获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
    function getNowFormatDate() {
    	var date = new Date();
    	var seperator1 = "-";
    	var seperator2 = ":";
    	var year = date.getFullYear();//年
    	var month = date.getMonth() + 1;//月
    	var strDate = date.getDate();//日
    	var hours = date.getHours();//时
    	var minutes = date.getMinutes();//分
    	var seconds = date.getSeconds();//秒
    	var dayWeek = "星期" + "日一二三四五六".charAt(date.getDay());
    	if (month >= 1 && month <= 9) {
    		month = "0" + month;
    	}
    	if (strDate >= 0 && strDate <= 9) {
    		strDate = "0" + strDate;
    	}
    	if (hours >= 0 && hours <= 9) {
    		hours = "0" + hours;
    	}
    	if (minutes >= 0 && minutes <= 9) {
    		minutes = "0" + minutes;
    	}
    	if (seconds >= 0 && seconds <= 9) {
    		seconds = "0" + seconds;
    	}
    	var currentdate = year + seperator1 + month + seperator1
    			+ strDate + " " + dayWeek + " " + hours + seperator2
    			+ minutes + seperator2 + seconds;
    	$('.time').text("当前时间为  : " + currentdate);
    }
    setInterval(getNowFormatDate, 1000);//每秒刷新
    </script>
</body>
</html>