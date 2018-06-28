<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>讯牛后台</title>
    <%@ include file="/common/core.jsp"%>
  </head>
  
  <body>
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">演示</a>
        <a>
          <cite>导航元素</cite></a>
      </span>
      <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
   
    <div class="x-body">
      <div class="layui-row" >
        <form id="searchForm" class="layui-form layui-col-md12 x-so" >
          <input type="text" name="equipName"  placeholder="请输入设备名" autocomplete="off" class="layui-input">
          <p id="search" class="btn btn-info"><i class="layui-icon">&#xe615;</i></p>
        </form>
       </div> 
  
      <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button>
        <button class="layui-btn" onclick="x_admin_show('添加设备','${ctx}/equip/equip/showtype',600,600)"><i class="layui-icon"></i>添加</button>
      </xblock>
    
  
      <table id="table" data-toggle="table" data-url="${ctx}/equip/equip/getTableData"  data-query-params="queryParams"
		data-show-refresh = "false" data-show-toggle = "false" data-show-columns = "true">
        <thead>
          <tr>
            <th data-checkbox="true">ID</th>
            <th data-field="" data-formatter="serialFormatter">序号</th>
            <th data-field="equipName">设备名称</th>
            <th data-field="typeName">设备类别名称</th>
            <th data-field="equipPrice">设备价格</th>
            <th data-field="equipProducer">设备厂家</th>
            <th data-field="addPeople">添加设备人</th>
            <th data-field="remark">备注</th>
            <th data-field="createTime" data-formatter="dateFormatter">创建时间</th>
            <th data-field="updateTime" data-formatter="dateFormatter">更新时间</th>
            <th data-field="equipId" data-formatter="operateFormatter">操作</th>
          </tr>  
        </thead>
      </table> 

    </div>
    <script>
    // 查询
    $(function(){				
		$("#search").click(function(){
			$("#table").bootstrapTable('refresh');
		})	
    });
 	// 序号
	function serialFormatter(value, row, index) { 	
		return index+1;
	}
    // 操作
	function operateFormatter(value, row, index) {
	 	var str = "<a title='编辑设备'  onclick=\"x_admin_show('编辑','${ctx}/equip/equip/showedit?equipId=%s',600,600)\" href='javascript:;'><i class='layui-icon'>&#xe642;</i></a>";
        return util.strFormat(str, value);
	}
	// 删除
    function delAll () {   	 
        var row=$("#table").bootstrapTable('getSelections');
       
        if (row.length == 0) {
			layer.msg("请选择要删除的记录!", {
				icon : 0
			});
		} else {
			layer.confirm('你确定要删除所选记录？', function() {
			 var ids="";
		        for(var i=0;i<row.length;i++){
		        	if(i==0 || i=="0"){
		        		ids+=row[i].equipId;
		        	}else{
		        		ids+=","+row[i].equipId;
		           }
		        }			
		        $.get("${ctx}/equip/equip/delete?ids="+ids, function(result){
		        	if(result.code==-1){
		        		layer.alert(result.msg, {icon : 5});      
			        }else{
			            layer.alert(result.msg, {icon : 6});
			            $("#table").bootstrapTable('refresh');        
				   }		        	
		       });
		   });       
		}
		return;
      }
    </script>

  </body>

</html>