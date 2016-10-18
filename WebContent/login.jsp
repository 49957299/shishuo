<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<link href="css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="css/easyui/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="css/easyui/icon.css" />
<script type="text/javascript" src="js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$().ready(function() {

		$("#sub").click(function() {
			var logname = $("#logname").val();
			var logpass = $("#logpass").val();
			if (logname == "") {
				return false;
			}
			if (logpass == "") {
				return false;
			}
			$('#loginPanle').form('submit', {
				success : function(data) {
					if(data!=""){
						var data = eval('(' + data + ')');
						if (data.code = -1) {
							$.messager.show({
								title : '错误',
								msg : data.errorMsg
							});
						} 
					}else{
						window.location = "<%=request.getContextPath()%>/index";
					}
				}
			});
		});
		//调用表单插件的'submit'方法提交
	});
</script>
</head>
<body>
	<div id="w" class="easyui-window" title="请先登录"
		style="width: 400px; padding: 20px 70px 20px 70px; display: block; width: 388px; left: 452px; top: 150px; z-index: 9001;">
		<form method="post" action="<%=request.getContextPath()%>/login"
			id="loginPanle">
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
			<div>
				<a id="sub" href="javascript:;" class="easyui-linkbutton"
					data-options="iconCls:'icon-ok'"
					style="padding: 5px 0px; width: 100%;"> <span
					style="font-size: 14px;">登录</span>
				</a>
			</div>
		</form>
	</div>
</body>
</html>
