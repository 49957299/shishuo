'use strict';
function copy(obj) {
	if (obj == null || typeof (obj) != 'object')
		return obj;

	var temp = obj.constructor();
	// changed

	for ( var key in obj) {
		if (obj.hasOwnProperty(key)) {
			temp[key] = copy(obj[key]);
		}
	}
	return temp;
}

function isType(type) {
	return function(obj) {
		return {}.toString.call(obj) == "[object " + type + "]"
	}
}

var isFunction = isType("Function");

function Resource(appId, appKey, baseurl) {
	var now = Date.now();
	this.appId = appId;
	this.baseurl = getRootPath() + "/" + (baseurl || "engine");
	this.appCode = SHA1(appId + "UZ" + appKey + "UZ" + now) + "." + now;
	this.defaultactions = {
		'get' : {
			method : 'GET',
			params : [ "_id", "_relation" ]
		}, // _relationid 后续支持
		'save' : {
			method : 'POST',
			params : [ "_id", "_relation" ]
		}, // _relationid 后续支持
		'query' : {
			method : 'GET',
			params : [ "filter" ]
		},
		'delete' : {
			method : 'DELETE',
			params : [ "_id", "_relation" ]
		}, // _relationid 后续支持
		'count' : {
			method : "GET",
			params : [ "_id", "_relation", "filter" ]
		},
		'findOne' : {
			method : 'GET',
			params : [ "filter" ]
		}
	};
	this.headers = {};
	this.setHeaders("AppId", this.appId);
	this.setHeaders("AppKey", this.appCode);
	this.setHeaders("Content-Type", "application/jsonp;charset=utf-8");
}
Resource.prototype.setHeaders = function(key, value) {
	this.headers[key] = value;
}
Resource.prototype.batch = function(requests, callback) {
	for (var i = 0, len = requests.length; i < len; i++) {
		var request = requests[i];
		if (request["method"] && request["method"].toUpperCase() === "GET" && request["body"] && request["body"]["filter"]) {
			var url = request["path"];
			var index = url.indexOf('?');
			request["path"] = url.substring(0, index) + "?filter=" + JSON.stringify(request["body"]["filter"]);
			delete request["body"];
		}
	}
	var ajaxConfig = {
		url : this.baseurl + "/batch",
		method : "POST",
		data : {
			body : JSON.stringify({
				requests : requests
			})
		}
	}
	ajaxConfig["headers"] = {};
	for ( var header in this.headers) {
		ajaxConfig["headers"][header] = this.headers[header];
	}
	apiAjax(ajaxConfig, function(ret, err) {
		callback(ret, err)
	});
}
Resource.prototype.upload = function(modelName, isFilter, item, params, callback) {
	if (typeof params == "function") {
		callback = params;
		params = {};
	}
	var filepath = item.path;
	var values = item.values || {};
	var url = params["_id"] && params["_relation"] ? ("/" + modelName + "/" + params["_id"] + "/" + params["_relation"]) : "/file";
	var isPut = (!params["_relation"]) && params["_id"];
	var fileUrl = this.baseurl + url + (isPut ? ("/" + params["_id"]) : "");
	var filename = filepath.substr(filepath.lastIndexOf("/") + 1, filepath.length);
	if (!values["filename"])
		values["filename"] = filename;
	var ajaxConfig = {
		url : fileUrl,
		method : isPut ? "PUT" : "POST",
		data : {
			values : values,
			files : {
				file : filepath
			}
		}
	}
	ajaxConfig["headers"] = {};
	for ( var header in this.headers) {
		if (header == "Content-Type")
			continue;
		ajaxConfig["headers"][header] = this.headers[header];
	}
	apiAjax(ajaxConfig, function(ret, err) {
		if (ret && ret.id && !err) {
			var newobj = {};
			if (isFilter) {
				newobj["id"] = ret["id"];
				newobj["name"] = ret["name"];
				newobj["url"] = ret["url"];
				callback(null, newobj)
			} else {
				callback(null, ret);
			}
		} else {
			callback(ret || err, null)
		}
	});
}

Resource.prototype.Factory = function(modelName) {
	var self = this;
	var route = new Route(modelName, self.baseurl);
	var actions = copy(this.defaultactions);
	var resourceFactory = new Object();
	Object.keys(actions).forEach(function(name) {
		resourceFactory[name] = function(a1, a2, a3) {
			var action = copy(actions[name]);
			var params = {}, data, callback;
			var hasBody = /^(POST|PUT|PATCH)$/i.test(action.method);
			switch (arguments.length) {
			case 3:
				params = a1;
				data = a2;
				callback = a3;
				break;
			case 2:
				if (hasBody)
					data = a1;
				else
					params = a1;
				callback = a2;
				break;
			case 1:
				if (isFunction(a1))
					callback = a1;
				else if (hasBody)
					data = a1;
				else
					params = a1;
				break;
			case 0:
				break;
			default:
				throw new Error("参数最多为3个");
			}
			if (hasBody && name != "logout") {
				var fileCount = 0;
				Object.keys(data).forEach(function(key) {
					var item = data[key];
					if (item && item.isFile) {
						var isFilter = true;
						if (modelName == "file" || item.isFileClass) {
							isFilter = false;
						}
						fileCount++;
						self.upload(modelName, isFilter, item, (modelName == "file" ? params : {}), function(err, returnData) {
							if (err) {
								return callback(null, err);
							} else {
								if (!isFilter)
									return callback(returnData, null);
								data[key] = returnData;
								fileCount--;
								if (fileCount == 0) {
									next();
								}
							}
						})
					}
				});
				if (fileCount == 0) {
					next();
				}
			} else {
				next();
			}
			function next() {
				var httpConfig = {};
				httpConfig["headers"] = {};
				for ( var header in self.headers) {
					httpConfig["headers"][header] = self.headers[header];
				}
				if (name === "logout" && !httpConfig["headers"]["authorization"]) {
					return callback({
						status : 0,
						msg : "未设置authorization参数,无法注销!"
					}, null);
				}
				if (hasBody) {
					httpConfig.data = {
						body : JSON.stringify(data)
					};
				}

				if (params && (name == "save") && params["_id"] && (!params["_relation"]) && (!params["_relationid"])) {
					action.method = "PUT";
				}
				if (params && (name == "save") && params["_id"] && params["_relation"] && params["_relationid"]) {
					action.method = "PUT";
				}
				for ( var key in action) {
					if (key != 'params' && key != "alias") {
						httpConfig[key] = copy(action[key]);
					}
				}

				var curparams = {};
				action.params = action.params || [];
				for (var k = 0, len = action.params.length; k < len; k++) {
					var tempkey = action.params[k];
					if (params[tempkey]) {
						curparams[tempkey] = copy(params[tempkey]);
					}
				}

				route.setUrlParams(httpConfig, curparams);
				httpConfig.cache = true;
				console.log(httpConfig.method + "\t" + httpConfig.url);
				apiAjax(httpConfig, function(ret, err) {
					return callback(ret, err);
				})
			}
		};
	});
	return resourceFactory;
};

function Route(template, baseurl) {
	this.template = template;
	this.baseurl = baseurl;
}

Route.prototype = {
	setUrlParams : function(config, params) {
		var url = "/:_class/:_id/:_relation/:_custom/:_relationid";
		url = url.replace(":_class", this.template);
		var parArr = [];
		Object.keys(params).forEach(function(ckey) {
			if (ckey.charAt(0) == '_') {
				url = url.replace(":" + ckey, params[ckey]);
				delete params[ckey];
			} else {
				if (ckey == "filter") {
					parArr.push(ckey + "=" + encodeURI(encodeURI(JSON.stringify(params[ckey]))));
				}
			}
		});
		url = url.replace(/:[^/]+/ig, '/');
		if (parArr.length > 0) {
			url += ("?" + parArr.join("&"));
		}
		url = url.replace(/\/+/g, '/');
		url = url.replace(/\/$/, '');
		config.url = this.baseurl + url;
	}
};
function SHA1(msg) {

	function rotate_left(n, s) {
		var t4 = (n << s) | (n >>> (32 - s));
		return t4;
	}
	;

	function lsb_hex(val) {
		var str = "";
		var i;
		var vh;
		var vl;

		for (i = 0; i <= 6; i += 2) {
			vh = (val >>> (i * 4 + 4)) & 0x0f;
			vl = (val >>> (i * 4)) & 0x0f;
			str += vh.toString(16) + vl.toString(16);
		}
		return str;
	}
	;

	function cvt_hex(val) {
		var str = "";
		var i;
		var v;

		for (i = 7; i >= 0; i--) {
			v = (val >>> (i * 4)) & 0x0f;
			str += v.toString(16);
		}
		return str;
	}
	;

	function Utf8Encode(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	}
	;

	var blockstart;
	var i, j;
	var W = new Array(80);
	var H0 = 0x67452301;
	var H1 = 0xEFCDAB89;
	var H2 = 0x98BADCFE;
	var H3 = 0x10325476;
	var H4 = 0xC3D2E1F0;
	var A, B, C, D, E;
	var temp;

	msg = Utf8Encode(msg);

	var msg_len = msg.length;

	var word_array = new Array();
	for (i = 0; i < msg_len - 3; i += 4) {
		j = msg.charCodeAt(i) << 24 | msg.charCodeAt(i + 1) << 16 | msg.charCodeAt(i + 2) << 8 | msg.charCodeAt(i + 3);
		word_array.push(j);
	}

	switch (msg_len % 4) {
	case 0:
		i = 0x080000000;
		break;
	case 1:
		i = msg.charCodeAt(msg_len - 1) << 24 | 0x0800000;
		break;

	case 2:
		i = msg.charCodeAt(msg_len - 2) << 24 | msg.charCodeAt(msg_len - 1) << 16 | 0x08000;
		break;

	case 3:
		i = msg.charCodeAt(msg_len - 3) << 24 | msg.charCodeAt(msg_len - 2) << 16 | msg.charCodeAt(msg_len - 1) << 8 | 0x80;
		break;
	}

	word_array.push(i);

	while ((word_array.length % 16) != 14)
		word_array.push(0);

	word_array.push(msg_len >>> 29);
	word_array.push((msg_len << 3) & 0x0ffffffff);

	for (blockstart = 0; blockstart < word_array.length; blockstart += 16) {

		for (i = 0; i < 16; i++)
			W[i] = word_array[blockstart + i];
		for (i = 16; i <= 79; i++)
			W[i] = rotate_left(W[i - 3] ^ W[i - 8] ^ W[i - 14] ^ W[i - 16], 1);

		A = H0;
		B = H1;
		C = H2;
		D = H3;
		E = H4;

		for (i = 0; i <= 19; i++) {
			temp = (rotate_left(A, 5) + ((B & C) | (~B & D)) + E + W[i] + 0x5A827999) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B, 30);
			B = A;
			A = temp;
		}

		for (i = 20; i <= 39; i++) {
			temp = (rotate_left(A, 5) + (B ^ C ^ D) + E + W[i] + 0x6ED9EBA1) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B, 30);
			B = A;
			A = temp;
		}

		for (i = 40; i <= 59; i++) {
			temp = (rotate_left(A, 5) + ((B & C) | (B & D) | (C & D)) + E + W[i] + 0x8F1BBCDC) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B, 30);
			B = A;
			A = temp;
		}

		for (i = 60; i <= 79; i++) {
			temp = (rotate_left(A, 5) + (B ^ C ^ D) + E + W[i] + 0xCA62C1D6) & 0x0ffffffff;
			E = D;
			D = C;
			C = rotate_left(B, 30);
			B = A;
			A = temp;
		}

		H0 = (H0 + A) & 0x0ffffffff;
		H1 = (H1 + B) & 0x0ffffffff;
		H2 = (H2 + C) & 0x0ffffffff;
		H3 = (H3 + D) & 0x0ffffffff;
		H4 = (H4 + E) & 0x0ffffffff;

	}

	var temp = cvt_hex(H0) + cvt_hex(H1) + cvt_hex(H2) + cvt_hex(H3) + cvt_hex(H4);

	return temp.toLowerCase();

}

function apiAjax(ajaxConfig, callback) {
	$.ajax({
		"url" : ajaxConfig.url,
		"method" : ajaxConfig.method,
		"cache" : ajaxConfig.cache,
		"headers" : ajaxConfig.headers,
		"data" : ajaxConfig.data
	}).success(function(data, status, xhr) {
		if (xhr.getResponseHeader('sessionout') == "1") {
			window.parent.replayLogin();
		}
		return callback(data, xhr);
	}).fail(function(header, status, xhr) {
		$.messager.alert("提示", "服务器异常请与管理员联系！", "error");
	});
}

function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

function formatter(value) {
	var date = new Date(value);
	var year = date.getFullYear().toString();
	var month = (date.getMonth() + 1);
	var day = date.getDate().toString();
	var hour = date.getHours().toString();
	var minutes = date.getMinutes().toString();
	var seconds = date.getSeconds().toString();
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	if (seconds < 10) {
		seconds = "0" + seconds;
	}
	return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;

}

/**
 * 清空dialog
 */
function clearDialog() {
	$('#dialog').find('[isSubmit="1"]').each(function() {
		if ($(this).attr("id") != 'ID') {
			clearObjectValue($(this));
		}
	});
}
/**
 * 清空对象值
 */
function clearObjectValue(object) {
	var classStr = $(object).attr('class') + "";

	if (classStr.indexOf('easyui-numberbox') >= 0) {
		$(object).numberbox('clear');
	} else if (classStr.indexOf('easyui-datebox') >= 0) {
		$(object).datebox('clear');
	} else if (classStr.indexOf('easyui-datetimebox') >= 0) {
		$(object).datetimebox('clear');
	} else if (classStr.indexOf('easyui-combobox') >= 0) {
		$(object).combobox('clear');
	} else if (classStr.indexOf('easyui-combotree') >= 0) {
		$(object).combotree('clear');
	} else if (classStr.indexOf('easyui-combogrid') >= 0) {
		$(object).combogrid('clear');
	} else if (classStr.indexOf('easyui-textbox') >= 0) {
		$(object).textbox('clear');
	} else {
		$(object).val('');
	}
}

$(function() {
	$.extend($.fn.textbox.defaults.rules, {
		minLength : { // 判断最小长度
			validator : function(value, param) {
				var tempValue = value.replace(/[^\x00-\xff]/g, '__');
				return tempValue.length >= param[0];
			},
			message : '最少输入 {0} 个字符。'
		},
		length : {
			validator : function(value, param) {
				var tempValue = value.replace(/[^\x00-\xff]/g, '__');
				return tempValue.length >= param[0] && tempValue.length <= param[1];
			},
			message : "输入大小不正确"
		},
		lengthEq : {
			validator : function(value, param) {
				var tempValue = value.replace(/[^\x00-\xff]/g, '__');
				return tempValue.length == param[0];
			},
			message : "输入大小不正确"
		},
		maxLength : {
			validator : function(value, param) {
				var tempValue = value.replace(/[^\x00-\xff]/g, '__');
				return param[0] >= tempValue.length;
			},
			message : '请输入最大{0}位字符.'
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
				// return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[
				// ]){1,12})+$/i.test(value);
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
				return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
			},
			message : '请输入姓名'
		},
		carNo : {
			validator : function(value) {
				return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
			},
			message : '车牌号码无效（例：粤J12350）'
		},
		carenergin : {
			validator : function(value) {
				return /^[a-zA-Z0-9]{16}$/.test(value);
			},
			message : '发动机型号无效(例：FG6H012345654584)'
		},
		email : {
			validator : function(value) {
				return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
			},
			message : '请输入有效的电子邮件账号(例：abc@126.com)'
		},
		msn : {
			validator : function(value) {
				return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
			},
			message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
		},
		same : {
			validator : function(value, param) {
				if ($("#" + param[0]).val() != "" && value != "") {
					return $("#" + param[0]).val() == value;
				} else {
					return true;
				}
			},
			message : '两次输入的密码不一致！'
		},
		warnmintime : { // 判断告警的值只能一级一级的增加，最小值
			validator : function(value, param) {
				value = $.trim(value); // 去空格
				if (value != "")
					for (var i = 0; i < param.length; i++) {
						$(param[i]).val();
						if ($(param[i]).combobox('getValue')) {
							var temp = $.trim($(param[i]).combobox('getValue'));
							if (temp != "" && !isNaN(temp) && parseInt(value) <= parseInt(temp))
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
				if (value != "")
					for (var i = 0; i < param.length; i++) {
						$(param[i]).val();
						if ($(param[i]).combobox('getValue')) {
							var temp = $.trim($(param[i]).combobox('getValue'));
							if (temp != "" && !isNaN(temp) && parseInt(value) >= parseInt(temp))
								return false;
						}
					}
				return true;
			},
			message : '不能大于当前告警的后一级的告警时间'
		},
		compareDate : {
			validator : function(value, param) {
				return dateCompare($(param[0]).datetimebox('getValue'), value); // 注意easyui
				// 时间控制获取值的方式
			},
			message : '开始日期不能大于结束日期'
		}
	});
	$.map([ 'validatebox', 'textbox', 'filebox', 'searchbox', 'combo', 'combobox', 'combogrid', 'combotree', 'datebox', 'datetimebox', 'numberbox', 'spinner',
			'numberspinner', 'timespinner', 'datetimespinner' ], function(plugin) {
		if ($.fn[plugin]) {
			$.fn[plugin].defaults.missingMessage = '此项目是必填项.';
		}
	});
	if ($.fn.calendar) {
		$.fn.calendar.defaults.weeks = [ '日', '一', '二', '三', '四', '五', '六' ];
		$.fn.calendar.defaults.months = [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ];
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
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
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
		$("#ExpireTime").datetimebox("setValue", seprateDateString($("#ReleaseTime").datetimebox("getValue")));
	}
	$("#ReleaseTime").datetimebox({
		onChange : adddaytimes
	});

});

/**
 * 获得对象值
 */
function getObjectValue(object) {
	var classStr = $(object).attr('class') + "";

	if (classStr.indexOf('easyui-numberbox') >= 0) {
		return $(object).numberbox('getValue');
	} else if (classStr.indexOf('easyui-datebox') >= 0) {
		return $(object).datebox('getValue');
	} else if (classStr.indexOf('easyui-datetimebox') >= 0) {
		return $(object).datetimebox('getValue');
	} else if (classStr.indexOf('easyui-combobox') >= 0) {
		return $(object).combobox('getValue');
	} else if (classStr.indexOf('easyui-combotree') >= 0) {
		return $(object).combotree('getValue');
	} else if (classStr.indexOf('easyui-combogrid') >= 0) {
		return $(object).combogrid('getValue');
	} else if (classStr.indexOf('easyui-textbox') >= 0) {
		return $(object).textbox('getValue');
	} else {
		return $(object).val();
	}

}
/**
 * 设置对象值
 */
function setObjectValue(object, val) {
	var classStr = $(object).attr('class') + "";
	if (classStr.indexOf('easyui-numberbox') >= 0) {
		$(object).numberbox('setValue', val);
	} else if (classStr.indexOf('easyui-datebox') >= 0) {
		$(object).datebox('setValue', val);
	} else if (classStr.indexOf('easyui-datetimebox') >= 0) {
		$(object).datetimebox('setValue', val);
	} else if (classStr.indexOf('easyui-combobox') >= 0) {
		$(object).combobox('setValue', val);
	} else if (classStr.indexOf('easyui-combotree') >= 0) {
		$(object).combotree('setValue', val);
	} else if (classStr.indexOf('easyui-combogrid') >= 0) {
		$(object).combogrid('setValue', val);
	} else if (classStr.indexOf('easyui-textbox') >= 0) {
		$(object).textbox('setValue', val);
	} else {
		$(object).val(val);
	}
}

/**
 * 提交验证
 */
function validateForm() {
	var isValid = true;
	$('.easyui-textbox').each(function() {
		if (!$(this).textbox("isValid")) {
			isValid = false;
		}
	});
	$('.easyui-numberbox').each(function() {
		if (!$(this).numberbox("isValid")) {
			isValid = false;
		}
	});
	$('.easyui-combobox').each(function() {
		if (!$(this).numberbox("isValid")) {
			isValid = false;
		}
	});
	$('.easyui-combo').each(function() {
		if (!$(this).numberbox("isValid")) {
			isValid = false;
		}
	});
	return isValid;
}