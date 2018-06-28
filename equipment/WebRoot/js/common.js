// 表单查询条件
function queryParams(params) {
	var query = $("#searchForm").serializeJSON();
		query["pageNumber"] = params.pageNumber;
		query["pageSize"] = params.pageSize;
		if (params.sortName) {
			query["order"] = params.sortName + " " + params.sortOrder;
		}
		//alert(util.toString(query));
	return query;
}

//时间格式化,默认时间参数yyyy-MM-dd HH:mm
function dateFormatter(value, row, index) {
	return util.dateFormat(value);
}	
//状态显示
//原始白（layui-btn layui-btn-primary）、默认绿（layui-btn）、百搭蓝（layui-btn layui-btn-normal）、暖色黄（layui-btn layui-btn-warm）、警告橙（layui-btn layui-btn-danger）、禁用灰（layui-btn layui-btn-disabled）
function statusFormatter(value) {
	if (value == "0") {		
		return "<span class='layui-btn layui-btn-normal layui-btn-mini'>启用</span>";		
	}
	if (value == "1") {		
		return "<span class='layui-btn layui-btn-disabled layui-btn-mini'>禁用</span>";
	}
}

//工具方法	
var util={
	/**
	 * 字符串转json对象
	 * @param str 字符串
	 * @returns
	 */
	toJson:function(str){
		return JSON.parse(str||"{}");
	},
	/**
	 * json对象转字符串
	 * @param obj
	 * @returns
	 */
	toString:function(obj){
		return JSON.stringify(obj||"");
	},
	/**
	 * 占位符输出 用法:util.strFormat("123%s5%s",4,6),输出123456
	 * @param str
	 * @returns
	 */
	strFormat : function(str) {
		var args = arguments, flag = true, i = 1;

		str = str.replace(/%s/g, function() {
			var arg = args[i++];

			if (typeof arg === 'undefined') {
				flag = false;
				return '';
			}
			return arg;
		});
		return flag ? str : '';
	},	
	/**
	 * 时间截取
	 */
	dateFormat : function(Stringdate) {
		// 2018-01-27 17:58:59.0
		if (typeof Stringdate === "string" && Stringdate.length > 8) {
			return Stringdate.substr(0, Stringdate.length-2);
		}
		return Stringdate;
	}
		
 }

/**
 * 表单属性转json
 * <p>
 * 去除空字段
 */
$.fn.serializeJSON = function() {
	var json = {};
	var form = this.serializeArray();
	$.each(form, function() {
		if (this.value) {
			if (json[this.name]) {
				if (!json[this.name].push) {
					json[this.name] = [ json[this.name] ];
				}
				json[this.name].push(this.value);
			} else {
				json[this.name] = this.value;
			}
		}
	});
	return json;
}
