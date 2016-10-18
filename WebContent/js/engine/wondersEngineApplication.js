//url="toKjKyApplication";
//client = new Resource("A6992068603519", "A6992068603519",url);

function submit() {
	alert("是否提交");
}

/**
 * 清空dialogAddMembers
 */
function clearDialogAddMembers() {
	$('#dialogAddMembers').find('[isSubmit="1"]').each(function() {
		if ($(this).attr("id") != 'ID') {
			clearObjectValue($(this));
		}
	});
}
/**
 * 清空dialogAddMember
 */
function clearDialogAddMember() {
	$('#dialogAddMember').find('[isSubmit="1"]').each(function() {
		if ($(this).attr("id") != 'ID') {
			clearObjectValue($(this));
		}
	});
}


function addMembers() {

	var row = $("#dataGrid").datagrid("getSelected");
	if (row == null) {
		$.messager.alert("提示", "请选择一条记录！", "info");
		return;
	}

	clearDialogAddMembers();
	$("#dialogAddMembers").show();// 必须先显示，再弹出
	$("#dialogAddMembers").dialog({
		title : "添加成员",
		width : 800,
		height : 500
	});
	var fkId = $("#dataGrid").datagrid("getSelected").ID;// 获取当前选中行数据主键
	addMember(fkId);
}

// 添加课题组成员
function addMember(fk_id) {
	var findTopicUrl = "/web/KjKyApplication/toFindMembersByFkId/" + fk_id;
	// alert(findTopicUrl);
	// $("#dialogAddMember").append("");

	
	$.ajax({
		url:findTopicUrl,
		method:"post",
		success:function(data){
			$('#dialogAddMembers').datagrid({
				loadMsg : "服务器异常,请耐心等候...",
				checkOnSelect : true,
				columns : [ [ {field : 'XM', title : '姓名', width : 50},
				              {field : 'SEX', title : '性别', width : 50},
				              {field : 'AGE', title : '年龄', width : 50},
				              {field : 'ZC', title : '职称', width : 50},
				              {field : 'CSZY', title : '从事专业', width : 100},
				              {field : 'GZDW', title : '工作单位', width : 100}, 
				              {field : 'XMFG', title : '项目分工', width : 100}, 
				              {field : 'XGSJ', title : '修改时间', width : 100}, 
				              {field : 'YLYL1', title : '预留一', width : 100}, 
				              {field : 'YLYL2', title : '预留二', width : 100} ] ],
				toolbar : [ {
					iconCls : 'icon-add',
					text : "新增",
					handler : function() {
						alert('新增按钮');
						
						
						$("#dialogAddMember").html("");
						$("#dialogAddMember").show();// 必须先显示，再弹出
						$("#dialogAddMember").dialog({
							title : "添加课题成员",
							width : 700,
							height : 400
						});
						$("#dialogAddMember").append("<table cellpadding='5'><tr><td>姓名:</td><td><input class='easyui-textbox' type='text' name='name' data-options='required:true'></input></td></tr>");
						$("#dialogAddMember").append("<tr><td>性別:</td><td><select class='easyui-combobox' name='sex'><option value='0'>男</option><option value='1'>女</option><option value='2'>未知</option></select></td></tr>");

						$("#dialogAddMember").append("</table>");
						
					}
				}, '-', {
					iconCls : 'icon-edit',
					text : "修改",
					handler : function() {
						alert('修改按钮');
						$("#dialogAddMember").dialog({
							title : "修改课题成员",
							width : 700,
							height : 400
						});
					}
				}, '-', {
					iconCls : 'icon-cancel',
					text : "删除",
					handler : function() {
						alert('删除按钮');
					}
				} ],
				singleSelect : true,
				pagination : true
			});
			
			$('#dialogAddMembers').datagrid('loadData', {
				"total" : data.total,
				"rows" : data.content
			});
		}
		
	});
	

}
