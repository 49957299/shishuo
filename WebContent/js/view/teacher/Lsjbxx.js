var client = new Resource("A6992068603519", "A6992068603519");
var model = client.Factory($('body').attr('tableClass'));

$().ready(function() {

	var search = {
		init : function() {
			$('#ss').searchbox({
				searcher : function(value, name) {
					dataGrid.find(name, value);
				},
				menu : '#mm',
				prompt : '请输入你所要查询的值'
			});
		}
	}
	// 证件类型下拉框
	$('#ZJLX').combobox({
        data : [{'id': '身份证','text': '身份证'},{'id': '护照','text': '护照'}],
        valueField : 'id',
        textField : 'text',
        editable : false,
        panelHeight : 'auto'
    });
	// 性别
	$('#XB').combobox({
        data : [{'id': '男','text': '男'},{'id': '女','text': '女'}],
        valueField : 'id',
        textField : 'text',
        editable : false,
        panelHeight : 'auto'
    });
	
	var dataGrid = {
		column : function() {
			return [ [
				{
					field : 'ID',
					checkbox : true
				}
			,{
				field : 'YHID',
				title : '用户ID',
				width : 100,
				align : "center"
			}
			,{
				field : 'ZSXM',
				title : '真实姓名',
				width : 100,
				align : "center"
			}
			,{
				field : 'ZJLX',
				title : '证件类型',
				width : 50,
				align : "center"
			}
			,{
				field : 'ZJHM',
				title : '证件号码',
				width : 150,
				align : "center"
			}
			,{
				field : 'XB',
				title : 'XB',
				width : 50,
				align : "center"
			}
			,{
				field : 'CSRQ',
				title : '出生日期',
				width : 150,
				align : "center",
				formatter : function(value, row, index) {
					if (value == "" || value == undefined) {
						return "";
					}
					return value.substring(0,10);
				}
			}
			,{
				field : 'CSD',
				title : '出生地',
				width : 150,
				align : "center"
			}
			,{
				field : 'BYJDGX',
				title : '毕业/就读高校',
				width : 150,
				align : "center"
			}
			,{
				field : 'SZXY',
				title : '所在学院',
				width : 150,
				align : "center"
			}
			,{
				field : 'SXZY',
				title : '所学专业',
				width : 150,
				align : "center"
			}
			,{
				field : 'MQSF',
				title : '目前身份',
				width : 100,
				align : "center"
			}
			,{
				field : 'GRZP',
				title : '个人照片',
				width : 150,
				align : "center",
				formatter:function(value,row,index){return "<img style='width:32px;height:32px' src='../../read/grz/"+row.GRZP+"'/>";}
			}
			,{
				field : 'BYZZP',
				title : '毕业证照片',
				width : 150,
				align : "center",
				formatter:function(value,row,index){return "<img style='width:32px;height:32px' src='../../read/byz/"+row.BYZZP+"'/>";}
			}
			,{
				field : 'XSZZP',
				title : '学生证照片',
				width : 150,
				align : "center",
				formatter:function(value,row,index){return "<img style='width:32px;height:32px' src='../../read/xsz/"+row.XSZZP+"'/>";}
			}
			,{
				field : 'QQ',
				title : 'QQ',
				width : 100,
				align : "center"
			}
			,{
				field : 'SJ',
				title : '手机',
				width : 100,
				align : "center"
			}
			,{
				field : 'GH',
				title : '固话',
				width : 100,
				align : "center"
			}
			,{
				field : 'YX',
				title : '邮箱',
				width : 150,
				align : "center"
			}
			,{
				field : 'LON',
				title : '纬度',
				width : 50,
				align : "center"
			}
			,{
				field : 'LAT',
				title : '经度',
				width : 50,
				align : "center"
			}
			,{
				field : 'XJDF',
				title : '星级得分',
				width : 50,
				align : "center"
			}
			,{
				field : 'JL',
				title : '教龄',
				width : 50,
				align : "center"
			}
			,{
				field : 'XL',
				title : '学历',
				width : 50,
				align : "center"
			}
			,{
				field : 'XLZT',
				title : '学历状态',
				width : 50,
				align : "center"
			},{
				field : 'JXXD',
				title : '教学心得',
				width : 450,
				align : "center"
			}
			,{
				field : 'CREATEAD',
				title : '创建时间',
				width : 150,
				align : "center"
			}
			,{
				field : 'UPDATEAD',
				title : '更新时间',
				width : 150,
				align : "center"
			}
			] ];
		},
		toolbar : function() {
			return [ {
				text : "新增",
				iconCls : 'icon-add',
				handler : function() {
					dataGrid.add();
				}
			}, '-', {
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					dataGrid.edit();
				}
			}, '-', {
				text : "删除",
				iconCls : 'icon-remove',
				handler : function() {
					dataGrid.remove();
				}
			}
// , '-', {
// text : "查看",
// iconCls : 'icon-search',
// handler : function() {
// dataGrid.get();
// }
// }
			]
		},
		get : function() {
			var row = $("#dataGrid").datagrid("getSelections");
			if (row == null) {
				$.messager.alert("提示", "请选择一条记录！", "info");
				return;
			}
			if (row.length > 1) {
				$.messager.alert("提示", "请选择一条记录！", "info");
				return;
			}
			model.get({
				"_id" : row[0].ID
			}, function(ret, xhr) {
				if (xhr.getResponseHeader('code') == -1) {
					$.messager.alert("提示", "查询异常！", "error");
				} else {
					$('#view').find('[isView="1"]').each(function() {
						setObjectValue($(this), eval("ret." + $(this).attr('ID')));
					});
					$("#view").show();// 必须先显示，再弹出
					$("#view").dialog({
						title : "查看",
						width : 800,
						height : 300
					});
				}
			});

		},
		add : function() {
			$("#ID").val("");
			$("#grzView").html("");
			$("#byzView").html("");
			$("#xszView").html("");
			clearDialog();
			$("#dialog").show();// 必须先显示，再弹出
			$("#dialog").dialog({
				title : "添加老师基本信息",
				width : 1000,
				height : 400
			});
		},
		edit : function() {
			var row = $("#dataGrid").datagrid("getSelections");
			if (row == null) {
				$.messager.alert("提示", "请选择一条记录！", "info");
				return;
			}
			if (row.length > 1) {
				$.messager.alert("提示", "请选择一条记录！", "info");
				return;
			}
			model.get({
				"_id" : row[0].ID
			}, function(ret, xhr) {
				if (xhr.getResponseHeader('code') == -1) {
					$.messager.alert("提示", "查询异常！", "error");
				} else {
					$('#dialog').find('[isSubmit="1"]').each(function() {
						setObjectValue($(this), eval("ret." + $(this).attr('ID')));
					});
					$("#dialog").show();// 必须先显示，再弹出
					$("#dialog").dialog({
						title : "编辑",
						width : 1000,
						height : 400
					});
				}
			});
		},
		remove : function() {
			var row = $("#dataGrid").datagrid("getSelections");
			var ids = "";
			for (var i = 0; i < row.length; i++) {
				ids += "'"+row[i].ID+"'";
				ids += ",";
			}
			ids = ids.substr(0, ids.length - 1);
			$.messager.confirm("操作提示", "您确定要执行删除操作吗？", function(data) {
				if (data) {
					model.delete({
						"_id" : ids
					}, function(ret, xhr) {
						if (xhr.getResponseHeader('code') != -1) {
							$.messager.alert("提示", "记录删除成功！", "info");
							dataGrid.find("all", "");
							$("#dataGrid").datagrid("clearSelections");
						} else {
							$.messager.alert("提示", "记录删除失败！", "error");
						}
					});
				} 
			});

		},
		find : function(name, value) {
			var pageopt = $('#dataGrid').datagrid('getPager').data("pagination").options
			var whereArray = new Array();
			if (name != "all") {
				if (name != "" || value != "") {
					columns = {};
					columns["name"] = name;
					columns["op"] = "EQ";
					columns["value"] = value;
					whereArray.push(columns);
				}
			}
			model.query({
				"filter" : {
					"order" : {
						"name" : "ID",
						"order" : "desc"
					},
					"page" : {
						"pageNumber" : pageopt.pageNumber == 0 ? 1 : pageopt.pageNumber,
						"pageSize" : pageopt.pageSize
					},
					"where" : whereArray
				}
			}, function(ret, xhr) {
				if (xhr.getResponseHeader('code') != -1) {
					$('#dataGrid').datagrid('loadData', {
						"total" : ret.totalElements,
						"rows" : ret.content
					});
				} else {
					$.messager.alert("提示", "系统异常请与管理员联系！", "error");
				}
			});
		},
		save : function() {
			if (this.validator()) {
				var data = {};
				$('#dialog').find('[isSubmit="1"]').each(function() {
					if ($(this).attr("id") != 'ID') {
						data[$(this).attr("id")] = getObjectValue($(this));
					}
				});

				var id = {};
				id["_id"] = "'"+$('#dialog').find('#ID').val()+"'";
				if ($('#ID').val() == "") {
					model.save(data, function(ret, xhr) {
						if (xhr.getResponseHeader('code') != -1) {
							$('#dialog').dialog('close');
							dataGrid.find("all", "");
						} else {
							$.messager.alert("提示", "记录新增失败！", "error");
						}
					});
				} else {
					model.save(id, data, function(ret, xhr) {
						if (xhr.getResponseHeader('code') != -1) {
							$.messager.alert("操作提示", "记录修改成功！", "info", function() {
								$('#dialog').dialog('close');
								dataGrid.find("all", "");
							});
						} else {
							$.messager.alert("提示", "记录修改失败！", "error");
						}
					});
				}
			}
		},
		validator : function() {
			return validateForm();
		},
		init : function() {
			$('#dataGrid').datagrid({
				title : '老师基本列表',
				idField : "ID",
				loadMsg : "正在载入.........",
				singleSelect : false,
				columns : dataGrid.column(),
				toolbar : dataGrid.toolbar(),
				pagination : true,
				singleSelect : false,
				selectOnCheck : true,
				checkOnSelect : true,
				rownumbers : true
			});
			page.pagination();
			this.find("all", "");
		}
	}

	var page = {
		pagination : function() {
			var pg = $("#dataGrid").datagrid("getPager");
			if (pg) {
				$(pg).pagination({
					pageSize : 20,// 每页显示的记录条数，默认为10
					pageList : [ 10, 20, 30, 50, 100 ],// 可以设置每页记录条数的列表
					beforePageText : '第',// 页数文本框前显示的汉字
					afterPageText : '页 共 {pages} 页',
					displayMsg : '当前显示 {from} - {to} 条记录 共 {total} 条记录',
					onRefresh : function(pageNumber, pageSize) {
						dataGrid.find("all", "");
					},
					onChangePageSize : function() {
						dataGrid.find("all", "");
					},
					onSelectPage : function(pageNumber, pageSize) {
						dataGrid.find("all", "");
					}
				});
			}
		}
	}

	search.init();
	dataGrid.init();

	$("#sub").click(function() {
		dataGrid.save();
	});
	$("#cols").click(function() {
		$('#dialog').dialog('close');
	});
	$("#col").click(function() {
		$('#view').dialog('close');
	});

});
