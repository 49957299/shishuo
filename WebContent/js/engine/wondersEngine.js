var client = new Resource("A6992068603519", "A6992068603519");
var Class = client.Factory($('body').attr('tableClass'));
var SysEngineTableCol = client.Factory('sysEngineTableCol');
var SysEngineGridDic = client.Factory('sysEngineGridDic');
var SysEngineTableButton = client.Factory('sysEngineTableButton');
var editDialg = "";
var createDialg = "";
var dicmap={};
$(function() {
	$.extend($.fn.textbox.defaults.rules, {
		minLength : { // 判断最小长度
	        validator : function(value, param) { 
	        	var tempValue=value.replace(/[^\x00-\xff]/g, '__');
	            return tempValue.length >= param[0]; 
	        }, 
	        message : '最少输入 {0} 个字符。'
	    }, 
	    length:{validator:function(value,param){ 
	    	    var tempValue=value.replace(/[^\x00-\xff]/g, '__');
	            return tempValue.length>=param[0]&&tempValue.length<=param[1]; 
	        }, 
	            message:"输入大小不正确"
	    },
	    lengthEq:{validator:function(value,param){ 
    	    var tempValue=value.replace(/[^\x00-\xff]/g, '__');
            return tempValue.length==param[0]; 
        }, 
            message:"输入大小不正确"
        }, 
        maxLength: {     
            validator: function(value, param){
            	var tempValue=value.replace(/[^\x00-\xff]/g, '__');
                return param[0] >= tempValue.length;     
            },     
            message: '请输入最大{0}位字符.'    
        }, 
        phone : {// 验证电话号码
            validator : function(value) { 
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
            }, 
            message : '格式不正确,请使用下面格式:020-88888888'
        }, 
        mobile : {// 验证手机号码
            validator : function(value) { 
                return /^(13|15|18)\d{9}$/i.test(value); 
            }, 
            message : '手机号码格式不正确'
        }, 
        idcard : {// 验证身份证
            validator : function(value) { 
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value); 
            }, 
            message : '身份证号码格式不正确'
        }, 
        intOrFloat : {// 验证整数或小数
            validator : function(value) { 
                return /^\d+(\.\d+)?$/i.test(value); 
            }, 
            message : '请输入数字，并确保格式正确'
        }, 
        currency : {// 验证货币
            validator : function(value) { 
                return /^\d+(\.\d+)?$/i.test(value); 
            }, 
            message : '货币格式不正确'
        }, 
        qq : {// 验证QQ,从10000开始
            validator : function(value) { 
                return /^[1-9]\d{4,9}$/i.test(value); 
            }, 
            message : 'QQ号码格式不正确'
        }, 
        integer : {// 验证整数
            validator : function(value) { 
                return /^[+]?[1-9]+\d*$/i.test(value); 
            }, 
            message : '请输入整数'
        },     
        chinese : {// 验证中文
            validator : function(value) { 
                return /^[\u0391-\uFFE5]+$/i.test(value); 
            }, 
            message : '请输入中文'
        }, 
        english : {// 验证英语
            validator : function(value) { 
                return /^[A-Za-z]+$/i.test(value); 
            }, 
            message : '请输入英文'
        }, 
        unnormal : {// 验证是否包含空格和非法字符
            validator : function(value) { 
                return /.+/i.test(value); 
            }, 
            message : '输入值不能为空和包含其他非法字符'
        }, 
        username : {// 验证用户名
            validator : function(value) { 
                return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value); 
            }, 
            message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
        }, 
        faxno : {// 验证传真
            validator : function(value) { 
// return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
            }, 
            message : '传真号码不正确'
        }, 
        zip : {// 验证邮政编码
            validator : function(value) { 
                return /^[1-9]\d{5}$/i.test(value); 
            }, 
            message : '邮政编码格式不正确'
        }, 
        ip : {// 验证IP地址
            validator : function(value) { 
                return /d+.d+.d+.d+/i.test(value); 
            }, 
            message : 'IP地址格式不正确'
        }, 
        name : {// 验证姓名，可以是中文或英文
                validator : function(value) { 
                    return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value); 
                }, 
                message : '请输入姓名'
        }, 
        carNo:{ 
            validator : function(value){ 
                return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value); 
            }, 
            message : '车牌号码无效（例：粤J12350）'
        }, 
        carenergin:{ 
            validator : function(value){ 
                return /^[a-zA-Z0-9]{16}$/.test(value); 
            }, 
            message : '发动机型号无效(例：FG6H012345654584)'
        }, 
        email:{ 
            validator : function(value){ 
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
        }, 
        message : '请输入有效的电子邮件账号(例：abc@126.com)'   
        }, 
        msn:{ 
            validator : function(value){ 
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
        }, 
        message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
        },
        same:{ 
            validator : function(value, param){ 
                if($("#"+param[0]).val() != "" && value != ""){ 
                    return $("#"+param[0]).val() == value; 
                }else{ 
                    return true; 
                } 
            }, 
            message : '两次输入的密码不一致！'   
        },
        warnmintime : { // 判断告警的值只能一级一级的增加，最小值
            validator : function(value, param) { 
                value = $.trim(value); // 去空格
                if( value !="")
                for(var i=0;i<param.length; i++){
                    $(param[i]).val();
                    if($(param[i]).combobox('getValue')){
                        var temp=$.trim($(param[i]).combobox('getValue'));
                        if(temp !="" && !isNaN(temp) && parseInt(value) <= parseInt(temp))
                            return false;
                       }
                }
                return true;
            }, 
            message : '不能小于当前告警的前一级的告警时间'
        },
        warnmaxtime : { // 判断告警的值只能一级一级的增加，最大值
            validator : function(value, param) { 
                value = $.trim(value); // 去空格
                if( value !="")
                for(var i=0;i<param.length; i++){
                    $(param[i]).val();
                    if($(param[i]).combobox('getValue')){
                        var temp=$.trim($(param[i]).combobox('getValue'));
                        if(temp !="" && !isNaN(temp) && parseInt(value) >= parseInt(temp))
                            return false;
                       }
                }
                return true;
            }, 
            message : '不能大于当前告警的后一级的告警时间'
        },
        compareDate: {
            validator: function (value, param) {
            return dateCompare($(param[0]).datetimebox('getValue'), value); // 注意easyui
																			// 时间控制获取值的方式
            },
            message: '开始日期不能大于结束日期'
        }     
    });
	$.map(['validatebox','textbox','filebox','searchbox',
			'combo','combobox','combogrid','combotree',
			'datebox','datetimebox','numberbox',
			'spinner','numberspinner','timespinner','datetimespinner'], function(plugin){
		if ($.fn[plugin]){
			$.fn[plugin].defaults.missingMessage = '此项目是必填项.';
		}
	});
	if ($.fn.calendar) {
		$.fn.calendar.defaults.weeks = [ '日', '一', '二', '三', '四', '五', '六' ];
		$.fn.calendar.defaults.months = [ '一月', '二月', '三月', '四月', '五月', '六月',
				'七月', '八月', '九月', '十月', '十一月', '十二月' ];
	}
	if ($.fn.datebox) {
		$.fn.datebox.defaults.currentText = '今天';
		$.fn.datebox.defaults.closeText = '关闭';
		$.fn.datebox.defaults.okText = '确定';
		$.fn.datebox.defaults.missingMessage = '该输入项为必输项';
		$.fn.datebox.defaults.formatter = function(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
					+ (d < 10 ? ('0' + d) : d);
		};
		$.fn.datebox.defaults.parser = function(s) {
			if (!s)
				return new Date();
			var ss = s.split('-');
			var y = parseInt(ss[0], 10);
			var m = parseInt(ss[1], 10);
			var d = parseInt(ss[2], 10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
				return new Date(y, m - 1, d);
			} else {
				return new Date();
			}
		};
	}
	if ($.fn.datetimebox && $.fn.datebox) {
		$.extend($.fn.datetimebox.defaults, {
			currentText : $.fn.datebox.defaults.currentText,
			closeText : $.fn.datebox.defaults.closeText,
			okText : $.fn.datebox.defaults.okText,
			missingMessage : $.fn.datebox.defaults.missingMessage
		});
	}

	function adddaytimes() {
		$("#ExpireTime").datetimebox("setValue",
				seprateDateString($("#ReleaseTime").datetimebox("getValue")));
	}
	$("#ReleaseTime").datetimebox({
		onChange : adddaytimes
	});

});
function initDataGrid() {
	Class = client.Factory($('body').attr('tableClass'));
	SysEngineTableCol.query({
		"filter" : {
			"order" : {
				"name" : "ID",
				"order" : "asc"
			},
			"page" : {
				"pageNumber" : 1,
				"pageSize" : 100000
			},
			"where" : [ {
				"name" : "OBJECT_NAME",
				"op" : "EQ",
				"value" : $('body').attr('tableClass')
			} ]
		}
	}, function(ret, xhr) {
		if (xhr.getResponseHeader('code') != -1) {
			var columnsArray = new Array();
			if(ret != undefined){
			for (var i = 0; i < ret.content.length; i++) {
				columns = {};
				columns["field"] = ret.content[i].COL_NAME;
				columns["title"] = ret.content[i].NOTE_NAME;
				columns["hidden"] = ret.content[i].HIDDEN_TYPE == '1';
				// columns["width"] = 100;
				columns["align"] = "left";
				if(ret.content[i].DIC_TYPE=="ENUMS"){
					columns["formatter"] = function(value,row,index,field){
						$('#'+field).combobox('setValue',value) ;
						var text=$('#'+field).combobox('getText');
						$('#'+field).combobox('setValue','') ;
						return text;
					};
				}
				if(ret.content[i].DIC_TYPE=="TREE"){
					columns["formatter"] = function(value,row,index,field){
						$('#'+field).combotree('setValue',value) ;
						var text=$('#'+field).combobox('getText');
						$('#'+field).combotree('setValue','') ;
						return text;
					};
				}
				columnsArray.push(columns);
			}
			// 生成检索条件
			getSearchDom(ret.content);
			// 生成dialog编辑页面
			getDialogDom(ret.content);

			$('#dataGrid').datagrid({
				singleSelect : true,
				columns : [ columnsArray ],
				pagination : true
			});

			
			// 初始化button组建
			initUiButton();
			// 初始化easyUI组建
			initEasyUi();
			}
		} else {
			$.messager.alert("提示", "系统异常请与管理员联系！", "error");
		}
	});

}

function find() {
	var pageopt = $('#dataGrid').datagrid('getPager').data("pagination").options
	var whereArray = new Array();
	$('#search').find('[isSubmit="1"]').each(function() {
		if (getObjectValue($(this))!= '') {
			columns = {};
			columns["name"] = $(this).attr("id");
			columns["op"] = $(this).attr("operator");
			columns["value"] = getObjectValue($(this));
			whereArray.push(columns);
		}
	});
	Class.query(
			{
				"filter" : {
					"order" : {
						"name" : "ID",
						"order" : "asc"
					},
					"page" : {
						"pageNumber" : pageopt.pageNumber == 0 ? 1
								: pageopt.pageNumber,
						"pageSize" : pageopt.pageSize
					},
					"where" : whereArray
				}
			}, function(ret, xhr) {
					ret=replaceDic(ret);
				if (xhr.getResponseHeader('code') != -1) {
					$('#dataGrid').datagrid('loadData', {
						"total" : ret.totalElements,
						"rows" : ret.content
					});
				} else {
					$.messager.alert("提示", "系统异常请与管理员联系！", "error");
				}

			});
}
function del() {
	var row = $("#dataGrid").datagrid("getSelected");
	$.messager.confirm("操作提示", "您确定要执行删除操作吗？", function(data) {
		if (data) {
			Class.delete({
				"_id" : row.ID
			}, function(ret, xhr) {
				if (xhr.getResponseHeader('code') != -1) {
					$.messager.alert("提示", "记录删除成功！", "info");
				} else {
					$.messager.alert("提示", "记录删除失败！", "error");
				}
				find();
			});
		} else {

		}
	});

}
function save() {
	if(!validate()) {
		$.messager.alert("提示", "填写错误，请按页面提示填写(红色)！", "error");
		return;
	}
	if ($('#ID').val() == "") {
		insert();
	} else {
		update();
	}
}
function insert() {
	var data = {};
	$('#dialog').find('[isSubmit="1"]').each(function() {
		if($(this).attr("id")!='ID'){
			data[$(this).attr("id")] = getObjectValue($(this));
		}
	});
	Class.save(data, function(ret, xhr) {
		if (xhr.getResponseHeader('code') != -1) {
			$.messager.confirm("操作提示", "记录新增成功,是否继续新增数据？", function(data) {
				if (data) {
					return;
				} else {
					$('#dialog').dialog('close');
					find();
				}
			});
		} else {
			$.messager.alert("提示", "记录新增失败！", "error");
		}
	});
}
function update() {
	var data = {};
	$('#dialog').find('[isSubmit="1"]').each(function() {
		if($(this).attr("id")!='ID'){
			data[$(this).attr("id")] = getObjectValue($(this));
		}
	});
	var id = {};
	id["_id"] = $('#dialog').find('#ID').val();
	Class.save(id, data, function(ret, xhr) {
		if (xhr.getResponseHeader('code') != -1) {
			$.messager.alert("操作提示", "记录修改成功！", "info", function() {
				$('#dialog').dialog('close');
				find();
			});
		}  else {
			$.messager.alert("提示", "记录修改失败！", "error");
		}
	});
}
function create() {
	clearDialog();
	$("#dialog").show();// 必须先显示，再弹出
	$("#dialog").dialog({
		title : "添加",
		width : 1000,
		height : 600
	});
}
function edit() {
	var row = $("#dataGrid").datagrid("getSelected");
	if(row==null){
		$.messager.alert("提示", "请选择一条记录！", "info");
		return;
	}
	Class.get({
		"_id" : row.ID
	}, function(ret, xhr) {
		if (xhr.getResponseHeader('code') == -1) {
			$.messager.alert("提示", "查询异常！", "error");
		}  else {
			$('#dialog').find('[isSubmit="1"]').each(function() {
				setObjectValue($(this),eval("ret." + $(this).attr('id')));
			});
			$("#dialog").show();// 必须先显示，再弹出
			$("#dialog").dialog({
				title : "编辑",
				width : 900,
				height : 550
			});
		}
	});
}
/**
 * 分页组件
 */
function addPagination() {
	var pg = $("#dataGrid").datagrid("getPager");
	if (pg) {
		$(pg).pagination({
			pageSize : 20,// 每页显示的记录条数，默认为10
			pageList : [ 10, 20, 30, 50, 100 ],// 可以设置每页记录条数的列表
			beforePageText : '第',// 页数文本框前显示的汉字
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onRefresh : function(pageNumber, pageSize) {
				find(pageNumber, pageSize);
			},
			onChangePageSize : function() {

			},
			onSelectPage : function(pageNumber, pageSize) {
				find(pageNumber, pageSize);
			}
		});
	}
}


/**
 * 生成检索dom对象
 */
function getSearchDom(cols) {
	var searchIndex = 1;
	var searchHtml = "<table class='search-table'><tr>";
	for (var i = 0; i < cols.length; i++) {
		var col = cols[i];
		if (col.IS_SEARCH != '1')
			continue;
		searchHtml += '<td>' + col.NOTE_NAME + ':</td>\
			<td>'
				+ getDomObject(col,'search') + '</td>';
		if (searchIndex % 3 == 0) {
			searchHtml += '</tr><tr>';
		}
		searchIndex++;
	}
	searchHtml += '</tr></table>';
	$('#search').prepend(searchHtml);

}
/**
 * 生成编辑dom对象
 */
function getDialogDom(cols) {
	var dialogIndex = 1;
	var dialogHtml = "<table class='dialog-table'><tr>";
	for (var i = 0; i < cols.length; i++) {
		var col = cols[i];
		if (col.HIDDEN_TYPE == '1'){
			dialogHtml+=getDomObject(col,'dialog');
		}else{
			dialogHtml += '<td>' + col.NOTE_NAME + ':</td>\
			<td>'
				+ getDomObject(col) + '</td>';
		   if (dialogIndex % 2 == 0) {
			dialogHtml += '</tr><tr>';
		   }
		   dialogIndex++;
		}
		
	}
	dialogHtml += '</tr></table>';
	$('#dialog').prepend(dialogHtml);
}
/**
 * 初始化easyUI组建
 */
function initEasyUi() {
	$('.easyui-textbox').each(function() {
		var options={};
		var dataLength=$(this).attr('dataLength');
		var nullAble=$(this).attr('nullAble');
		var data_options=$(this).attr('data_options');
		if($(this).attr("operator")==""){
			if(nullAble=='1') options['required']=true;
			var validType=new Array();
			if(dataLength!='undefined'){
				validType.push("maxLength["+dataLength+"]")
			}else{
				validType.push("maxLength["+4000+"]")
			}
			if(typeof(data_options)!='undefined'&&data_options!='undefined') validType.push(data_options);
			options['validType']=validType;
		}
		if($(this).attr('type')=='hidden'){
			$(this).textbox({type:'hidden'});
		}else{
			$(this).textbox(options);
		}
		

	});
	$('.easyui-numberbox').each(function() {
		var dataPrecision=$(this).attr('dataPrecision');
		var dataScale=$(this).attr('dataScale');
		var maxNum=9;
		for(var i=1;i<dataScale;i++){
			maxNum+=Math.pow(10,i)*9
		}
		$(this).numberbox({
			max:maxNum,
			min : 0,
			precision : dataPrecision=="undefined"?0:dataPrecision,
			groupSeparator : ','
		});

	});
	$('.easyui-filebox').each(function() {
		$(this).filebox({buttonText: '选择文件', buttonAlign: 'right' });

	});
	$('.easyui-datebox').each(function() {
		$(this).datebox({});

	});
	$('.easyui-datetimebox').each(function() {
		$(this).datetimebox({});

	});
	$('.easyui-combobox').each(function() {
		$(this).combobox({
			url : '../../json/' + $(this).attr('dic_code') + '.json',
			method : 'GET',valueField: 'id',textField: 'text'
		});

	});
	$('.easyui-combotree').each(function() {
		$(this).combotree({
			url : '../../json/' + $(this).attr('dic_code') + '.json',
			method : 'GET',valueField: 'id',textField: 'text'
		});

	});

	$('.easyui-combogrid').each(function() {
		var comboxGridObj=$(this);
		$.getJSON('../../json/' + $(this).attr('dic_code') + '.json',function(data){
			$(comboxGridObj).combogrid(data);
		}) 
	});
}
/**
 * 初始化button組建
 */
function initUiButton(){
	SysEngineTableButton.query({
		"filter" : {
			"order" : {
				"name" : "SHOW_INDEX",
				"order" : "asc"
			},
			"page" : {
				"pageNumber" : 1,
				"pageSize" : 100000
			},
			"where" : [ {
				"name" : "OBJECT_NAME",
				"op" : "EQ",
				"value" : $('body').attr('tableClass')
			} ]
		}
	}, function(ret, xhr) {
		if (xhr.getResponseHeader('code') != -1) {
			var columnsArray = new Array();
			$('#search').append('<div id="search-buttons" align="right"></div>')
			$('#dialog').append('<div id="dialog-buttons" align="center"></div>')
			if(ret.content !=undefined){
			for (var i = 0; i < ret.content.length; i++) {
				if(ret.content[i].DIV_AREA=='dataGrid') {
					columns = {};
					columns["text"] = ret.content[i].BUTTON_NAME;
					columns["iconCls"] = ret.content[i].BUTTON_ICON;
					columns["handler"] = 'function() {'+ret.content[i].BUTTON_EVENT+'}';
					columnsArray.push(columns);
				}else if(ret.content[i].DIV_AREA=='search'){
					$('#search-buttons').append('<a id="'+ret.content[i].ID+'" href="javascript:void(0)" onclick="'+ret.content[i].BUTTON_EVENT+'">'+ret.content[i].BUTTON_NAME+'</a>');
					$('#'+ret.content[i].ID).linkbutton({    
					    iconCls: ret.content[i].BUTTON_ICON   
					}); 
				}else if(ret.content[i].DIV_AREA=='dialog'){
					$('#dialog-buttons').append('<a id="'+ret.content[i].ID+'" href="javascript:void(0)" onclick="'+ret.content[i].BUTTON_EVENT+'">'+ret.content[i].BUTTON_NAME+'</a>');
					$('#'+ret.content[i].ID).linkbutton({    
					    iconCls: ret.content[i].BUTTON_ICON   
					});
				}
			}}
			var toolBar=JSON.stringify(columnsArray).replace(new RegExp(/("function)/g),'function').replace(new RegExp(/(}"})/g),'}}');
			$('#dataGrid').datagrid({toolbar : eval(toolBar)});
			// 初始化翻页
			addPagination();
		} else {
			// $.messager.alert("提示", "系统异常请与管理员联系！", "error");
		}
	});
	
	
}
/**
 * 清空dialog
 */
function clearDialog(){
	$('#dialog').find('[isSubmit="1"]').each(function() {
		if($(this).attr("id")!='ID'){
			clearObjectValue($(this));
		}
	});
}
/**
 * 清空对象值
 */
function clearObjectValue(object) {
	var classStr=$(object).attr('class')+"";

	if(classStr.indexOf('easyui-numberbox')>=0){
		 $(object).numberbox('clear');
	}
	else if(classStr.indexOf('easyui-datebox')>=0){
		 $(object).datebox('clear');
	}
	else if(classStr.indexOf('easyui-datetimebox')>=0){
		 $(object).datetimebox('clear');
	}
	else if(classStr.indexOf('easyui-combobox')>=0){
		 $(object).combobox('clear');
	}
	else if(classStr.indexOf('easyui-combotree')>=0){
		 $(object).combotree('clear');
	}
	else if(classStr.indexOf('easyui-combogrid')>=0){
		 $(object).combogrid('clear');
	}
	else if(classStr.indexOf('easyui-textbox')>=0){
		 $(object).textbox('clear');
	}else{
		 $(object).val('');		
	}
}
/**
 * 获得对象值
 */
function getObjectValue(object) {
	var classStr=$(object).attr('class')+"";

	if(classStr.indexOf('easyui-numberbox')>=0){
		return $(object).numberbox('getValue');
	}
	else if(classStr.indexOf('easyui-datebox')>=0){
		return $(object).datebox('getValue');
	}
	else if(classStr.indexOf('easyui-datetimebox')>=0){
		return $(object).datetimebox('getValue');
	}
	else if(classStr.indexOf('easyui-combobox')>=0){
		return $(object).combobox('getValue');
	}
	else if(classStr.indexOf('easyui-combotree')>=0){
		return $(object).combotree('getValue');
	}
	else if(classStr.indexOf('easyui-combogrid')>=0){
		return $(object).combogrid('getValue');
	}
	else if(classStr.indexOf('easyui-textbox')>=0){
		return $(object).textbox('getValue');
	}else{
		return $(object).val();		
	}
	
}
/**
 * 设置对象值
 */
function setObjectValue(object,val) {
	var classStr=$(object).attr('class')+"";
	if(classStr.indexOf('easyui-numberbox')>=0){
		$(object).numberbox('setValue',val);
	}
	else if(classStr.indexOf('easyui-datebox')>=0){
		$(object).datebox('setValue',val);
	}
	else if(classStr.indexOf('easyui-datetimebox')>=0){
		$(object).datetimebox('setValue',val);
	}
	else if(classStr.indexOf('easyui-combobox')>=0){
		$(object).combobox('setValue',val);
	}
	else if(classStr.indexOf('easyui-combotree')>=0){
		$(object).combotree('setValue',val);
	}
	else if(classStr.indexOf('easyui-combogrid')>=0){
		$(object).combogrid('setValue',val);
	}
	else if(classStr.indexOf('easyui-textbox')>=0){
		$(object).textbox('setValue',val);
	}else{
		$(object).val(val);
	}
	
}
/**
 * 生成dom对象
 */
function getDomObject(col,area) {
	var classStr = 'easyui-textbox';
	if (col.DATA_TYPE == 'FILE') {
		classStr = 'easyui-filebox';
	}else if (col.DATA_TYPE == 'NUMBER') {
		classStr = 'easyui-numberbox';
	} else if (col.DATA_TYPE == 'DATE') {
		classStr = 'easyui-datebox';
	} else if (col.DATA_TYPE == 'DATETIME') {
		classStr = 'easyui-datetimebox';
	} else if (col.DATA_TYPE == 'STRING') {
		if (col.DIC_TYPE == 'ENUMS') {
			classStr = 'easyui-combobox';
		} else if (col.DIC_TYPE == 'TREE') {
			classStr = 'easyui-combotree';
		} else if (col.DIC_TYPE == 'GRID') {
			classStr = 'easyui-combogrid';
		} else {
			classStr = 'easyui-textbox';
		}
	}
	if (col.HIDDEN_TYPE == '1') {
		type = 'hidden';
		classStr="";
	} else {
		type = 'text';
	} 
	if(area=='search'&&typeof(col.SEARCH_OPERATOR)!=undefined){
		dom = '<input style="width:300px" isSubmit="1" class="' + classStr + '" type="' + type
		+ '" id="' + col.COL_NAME + '" data_options="'
		+ col.DATA_OPTIONS + '" operator="' + col.SEARCH_OPERATOR
		+ '" dic_code="' + col.DIC_CODE + '" dataLength="' + col.DATA_LENGTH + '" dataPrecision="' + col.DATA_PRECISION + '" dataScale="' + col.DATA_SCALE + '" nullAble="' + col.NULLABLE + '"/>';
	}else{
		dom = '<input style="width:300px" isSubmit="1" class="' + classStr + '" type="' + type
		+ '" id="' + col.COL_NAME + '" data_options="'
		+ col.DATA_OPTIONS + '" operator="" dic_code="' + col.DIC_CODE + '" dataLength="' + col.DATA_LENGTH + '" dataPrecision="' + col.DATA_PRECISION + '" dataScale="' + col.DATA_SCALE + '" nullAble="' + col.NULLABLE + '"/>';
	}

	return dom;
}
/**
 * 字典项目显示处理
 * 
 * @param ret
 * @returns
 */
function replaceDic(ret){
	return ret;
}
/**
 * 提交验证
 */
function validate(){
	var isValid=true;
	$('.easyui-textbox').each(function() {
		 if(!$(this).textbox("isValid")){
			 isValid=false;
		 }
	});
	$('.easyui-numberbox').each(function() {
		 if(!$(this).numberbox("isValid")){
			 isValid=false;
		 }
	});
	return isValid;
}

