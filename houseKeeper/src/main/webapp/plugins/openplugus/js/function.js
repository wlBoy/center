/*插件分割线start*/
function showNormalLay(title, dv, saveFn) {
	$("#modal2").remove();
	$(document.body).append('<div id="modal2"></div>');
	var modal = new Modal();
	modal.btns = [ {
		id : "btn_save",
		text : "保存"
	}, {
		id : "closebtn"
	} ]
	modal.InitMax = true;//是否最大化
	modal.renderto = "#modal2";//绘制到div
	modal.title = title;
	modal.body = dv.html();
	modal.OnfirstInited = function() {//绘制完成后事件
		$("#btn_save").click(saveFn);
	};
	return modal;
}

function showTabLay() {
	$("#modal3").remove(); // 清除上次的div
	$(document.body).append('<div id="modal3"></div>');
	var modal = new Modal();
	modal.renderto = "#modal3";
	modal.InitShow = true;
	modal.btns = [ {
		id : "btn_save",
		text : "保存"
	}, {
		id : "closebtn"
	} ]
	var tab1 = new Tab();
	modal.OnfirstInited = function(thismodal) {
		tab1.tabs = [ {
			id : "tabs1",
			title : "页面1",
			active : true,
			isiframe : true,
			url : "${pageContext.request.contextPath}/day0831/goodsServer.jsp"
		}, {
			id : "tabs2",
			showclosebtn : true,
			title : '页面2',
			bodyel : "其他信息222"
		}, {
			id : "tabs3",
			showclosebtn : true,
			title : '页面3',
			bodyel : "其他信息333"
		}, {
			id : "tabs4",
			showclosebtn : true,
			title : '页面4',
			bodyel : "其他信息444"
		} ];
		tab1.renderto = thismodal.body;
		tab1.Init();

		// 保存
		$("#btn_save").click(function() {
			alert(333);
		});
	}
	return modal;
}
/*插件分割线end*/