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
	
	$('#USERTYPE').combobox({
        data : [{'id': '老师','text': '老师'},{'id': '家长','text': '家长'}],
        valueField : 'id',
        textField : 'text',
        editable : false,
        panelHeight : 'auto'
    });
	
	
	var dataGrid = {
		column : function() {
			return [ [ {
				field : 'ID',
				checkbox : true
			}, {
				field : 'USERNAME',
				title : '用户名称',
				width : 150,
				align : "center"
			}, {
				field : 'EMAIL',
				title : '邮件地址',
				width : 150,
				align : "center"
			}, {
				field : 'MOBILE',
				title : '手机号吗',
				width : 150,
				align : "CENTER"
			},  {
				field : 'USERTYPE',
				title : '用户类型',
				width : 150,
				align : "CENTER"
			},{
				field : 'CREATEAD',
				title : '创建时间',
				width : 150,
				align : "center",
				formatter : function(value, row, index) {
					if (value == "" || value == undefined) {
						return "";
					}
					return formatter(value);

				}
			}, {
				field : 'UPDATEAD',
				title : '更新时间',
				width : 150,
				align : "center",
				formatter : function(value, row, index) {
					if (value == "" || value == undefined) {
						return "";
					}
					return formatter(value);
				}
			} ] ];
		},
		toolbar : function() {
			return [ 
//			         {
//				text : "新增",
//				iconCls : 'icon-add',
//				handler : function() {
//					dataGrid.add();
//				}
//			}, '-', 
			{
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
//			, '-', {
//				text : "查看",
//				iconCls : 'icon-search',
//				handler : function() {
//					dataGrid.get();
//				}
//			}
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
			clearDialog();
			$("#dialog").show();// 必须先显示，再弹出
			$("#dialog").dialog({
				title : "添加用户信息",
				width : 800,
				height : 300
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
						width : 800,
						height : 300
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
				title : '用户信息列表',
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
