<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="cxt" value="${pageContext.request.contextPath}" />
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>师说后台管理信息系统</title>
<link href="${cxt}/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${cxt}/css/easyui/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${cxt}/css/easyui/icon.css" />
<style>
#css3menu li {
	float: left;
	list-style-type: none;
}

#css3menu li a {
	color: #fff;
	padding-right: 20px;
}

#css3menu li a.active {
	color: yellow;
}
</style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px; background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
		<span style="float: right; padding-right: 20px;" class="head">欢迎 ${sessionScope.user.USERNAME}
			<a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a>
		</span> <span style="padding-left: 10px; font-size: 16px; float: left;"><img
			src="images/blocks.gif" width="20" height="20" align="absmiddle" />
			师说后台管理信息系统</span>
		<ul id="css3menu"
			style="padding: 0px; margin: 0px; list-type: none; float: left; margin-left: 40px;">
			<li><a class="active" name="basic" href="javascript:;"
				title="基础数据"></a></li>
			<li><a name="point" href="javascript:;" title="积分管理"></a></li>

		</ul>
	</div>
	<div region="south" split="true"
		style="height: 30px; background: #D2E0F2;">
		<div class="footer"></div>
	</div>
	<div region="west" hide="true" split="true" title="导航菜单"
		style="width: 180px;" id="west">
		<div id='wnav' class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->

		</div>

	</div>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">

				<h1>Welcome to using The jQuery EasyUI!</h1>

			</div>
		</div>
	</div>


	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false"
		minimizable="false" maximizable="false" icon="icon-save"
		style="width: 300px; height: 150px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="password" class="txt01" /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="password" class="txt01" /></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0)"> 确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0)">取消</a>
			</div>
		</div>
	</div>

	<div id="loginDialog" class="easyui-dialog" title="系统登录"
		style="width: 400px; height: 200px; padding: 10px;" title="请先登录">
		<form method="post" action="${cxt }/login" id="reloadLoad">
			<div style="margin-bottom: 10px">
				<input class="easyui-textbox" id="logname" name="username"
					style="width: 100%; height: 30px; padding: 12px"
					data-options="required:true,missingMessage:'请输入用户名称',prompt:'登录名称',iconCls:'icon-man',iconWidth:38">
			</div>
			<div style="margin-bottom: 20px">
				<input class="easyui-textbox" id="logpass" type="password"
					name="pass" style="width: 100%; height: 30px; padding: 12px"
					data-options="required:true,missingMessage:'请输入用户密码',prompt:'登录密码',iconCls:'icon-lock',iconWidth:38">
			</div>
		</form>
	</div>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
	<script type="text/javascript" src="js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">

		var _menus = ${sessionScope.menus};
		//设置登录窗口
		function openPwd() {
			$('#w').window({
				title : '修改密码',
				width : 300,
				modal : true,
				shadow : true,
				closed : true,
				height : 160,
				resizable : false
			});
		}

		//关闭登录窗口
		function closePwd() {
			$('#w').window('close');
		}

		//修改密码
		function serverLogin() {
			var $newpass = $('#txtNewPass');
			var $rePass = $('#txtRePass');

			if ($newpass.val() == '') {
				msgShow('系统提示', '请输入密码！', 'warning');
				return false;
			}
			if ($rePass.val() == '') {
				msgShow('系统提示', '请在一次输入密码！', 'warning');
				return false;
			}

			if ($newpass.val() != $rePass.val()) {
				msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
				return false;
			}
			$.post('${cxt}/modifPassword?newpass=' + $newpass.val(), function(
					msg) {
				msgShow('系统提示', '恭喜，密码修改成功！<br>', 'info');
				$('#w').window('close');
				$newpass.val('');
				$rePass.val('');
			})

		}

		$(function() {
			$("#loginDialog").window('close');
			openPwd();
			$('#editpass').click(function() {
				$('#w').window('open');
			});

			$('#btnEp').click(function() {
				serverLogin();
			})

			$('#btnCancel').click(function() {
				closePwd();
			})

			$('#loginOut').click(function() {
				$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

					if (r) {
						location.href = '${cxt}/logout';
					}
				});
			})
		});

		function replayLogin() {
			$('#loginDialog').dialog({
				buttons : [ {
					text : '重新登录',
					handler : function() {
						$('#reloadLoad').form('submit', {
							url : "${cxt }/login",
							success : function(data) {
								if (data != "") {
									var data = eval('(' + data + ')');
									if (data.code = -1) {
										$.messager.show({
											title : '错误',
											msg : data.errorMsg
										});
									}
								} else {
									$("#loginDialog").dialog('close');
								}
							}
						});
					}
				}, {
					text : '返回首页',
					handler : function() {
						window.location = "${cxt}/login.jsp";
					}
				} ]
			});
			$('#logpass').textbox('clear');
			$("#loginDialog").window("open");

		}
	</script>
	<script type="text/javascript" src="js/engine/wondersRestApi.js"></script>
	<script type="text/javascript" src="js/engine/wondersEngine.js"></script>
	<script type="text/javascript" src="js/engine/Global.js"></script>
	<script type="text/javascript" src='js/outlook.js'></script>
</body>
</html>