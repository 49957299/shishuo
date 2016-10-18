var url ="/engine";
 client = new Resource("A6992068603519", "A6992068603519", url);

var url1 ="/personMove";
var client1 = new Resource("A6992068603519", "A6992068603519", url1);
var Class1 = client1.Factory($('body').attr('tableClass'));
function callIn() {

	if ($("#dataGrid").datagrid("getSelected") == null) {
		$.messager.alert("提示", "请选择一条记录！", "info");
		return;
	}
	if ($("#dataGrid").datagrid("getSelected").DWBH != "123456") {
		$.messager.alert("操作提示", "不能执行调入操作！");
		return;
	}
	;
	edit();

}
function callUp() {

	if ($("#dataGrid").datagrid("getSelected") == null) {
		$.messager.alert("提示", "请选择一条记录！", "info");
		return;
	}
	if ($("#dataGrid").datagrid("getSelected").DWBH == "123456") {
		$.messager.alert("操作提示", "不能执行调出操作！");
		return;
	}
	;
	edit();
}
function update() {
	var data = {};
	$('#dialog').find('[isSubmit="1"]').each(function() {
		if ($(this).attr("id") != 'ID') {
			data[$(this).attr("id")] = getObjectValue($(this));
		}
	});
	var id = {};
	id["_id"] = $('#dialog').find('#ID').val();
	Class1.save(id, data, function(ret, xhr) {
		if (xhr.getResponseHeader('code') != -1) {
			$.messager.alert("操作提示", "执行成功！", "info", function() {
				find();
			});
		} else {
			$.messager.alert("提示", "记录修改失败！", "error");
		}
	});
}
function edit() {
	var row = $("#dataGrid").datagrid("getSelected");
	if (row == null) {
		$.messager.alert("提示", "请选择一条记录！", "info");
		return;
	}
	Class1.get({
		"_id" : row.ID
	}, function(ret, xhr) {
		if (xhr.getResponseHeader('code') == -1) {
			$.messager.alert("提示", "查询异常！", "error");
		} else {
			$('#dialog').find('[isSubmit="1"]').each(function() {
				setObjectValue($(this), eval("ret." + $(this).attr('id')));
			});
			update();
		}
	});
}
function moveRecord() {
	dd_find();
}

function dd_find() {
	var row = $("#dataGrid").datagrid("getSelected");
	if (row == null) {
		$.messager.alert("提示", "请选择一条记录！", "info");
		return;
	}
	var pageopt = $('#dataGrid').datagrid('getPager').data("pagination").options
	var whereArray = new Array();
	columns = {};
	columns["name"] = "RYID";
	columns["op"] = "EQ";
	columns["value"] = row.ID;
	whereArray.push(columns);
	Class1.query(
			{
				"filter" : {
					"order" : {
						"name" : "DDSJ",
						"order" : "asc"
					},
					"page" : {
						"pageNumber" : pageopt.pageNumber == 0 ? 1
								: pageopt.pageNumber,
						"pageSize" : pageopt.pageSize,
					},
					"where" : whereArray
				}
			}, function(ret, xhr) {				
				ret = replaceDic(ret);
				if (xhr.getResponseHeader('code') != -1) {
					$('#callIn').dialog({
						title:'调动记录',
					    width: 565,
					    height: 340,
					    closed: false,
					    cache: false,
					    modal: true
					});
					$('#callIndataGrid').datagrid({
						width: 545,
						height: 300,
						pageList: [10, 20, 30], 
						columns :[[ {
							field : 'RYBH',
							title : '人员编号',
							width : 100
						}, {
							field : 'XM',
							title : '姓名',
							width : 100
						}, {
							field : 'DRDW',
							title : '调入单位',
							width : 100
						}, {
							field : 'DCDW',
							title : '调出单位',
							width : 100
						}, {
							field : 'DDSJ',
							title : '调动时间',
							width : 140
						} ]] 
					});
					$('#callIndataGrid').datagrid('loadData', {
						"total" : ret.totalElements,
						"rows" : ret.content
					});
				} else {
					$.messager.alert("提示", "系统异常请与管理员联系！", "error");
				}

			});
}
 
/*--撤销单位--*/
function recall(){
	recallDia();
}
function recallDia(){
	 $('#recall').dialog({ 
		 title:'单位撤销',
         width: 400,   
         height: 180,   
         closed: false,   
         cache: false,   
         modal: true, 
         buttons:[{
        	    text:'确定',
        	    handler:function(){	
        	    	var dwbhval=$('#dwmc').combobox('getValues');
        	    	alert(dwbhval);
        	    	if(dwbhval==null||dwbhval ==""){
        	    		alert("对不起，您尚未选择要撤销的单位！");
        	    	}else if(dwbhval=="1111"){
        	    		alert("对不起，公共单位不可撤销！！");
        	    	}else{
        	    		var msg ="确定撤销该单位吗？";
        	    	 if(confirm(msg)==true){
        	    		 $.ajax({
        	    			 type:"post",
        	    			 url:'/web/personMove/dwcx/'+dwbhval,
        	    				 });
        	    	 }else{      	    		 
        	    	 };
        	    	}
        	    }
        	   },{
        	    text:'取消',
        	    handler:function(){
        	    	$('#recall').dialog('close');	 
        	    }
        	   }]
     });
	 $('#dwmc').combobox({
		 method:'POST',
		 url:'/web//personMove/findUnit',
		 valueField:'DWBH',
		 textField:'DWMC'
	 });
	
	 
}