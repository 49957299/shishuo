<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
label {
	display: inline-block;
	width: 86px;
}
</style>
<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="yhz">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
			<div data-options="name:'Fzmc'">分组名称</div>


		</div>
	</div>
	<div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
	<div id="dialog" style="display: none;">
		<form action="" method="post">
			<div>
				<h1 align="center" style="">用户组信息</h1>
				<hr />
			</div>
			<div>
				<table align="center">
					<tr>
						<input id="ID" name="ID" type="hidden" isSubmit="1" />
<!-- 						<input id="LSID" name="LSID" type="hidden" isSubmit="1" /> -->
<!-- 						<input id="JZID" name="JZID" type="hidden" isSubmit="1" /> -->
						<input id="YHID" name="YHID" type="hidden" isSubmit="1" />
						<input id="YHMC" name="YHMC" type="hidden" isSubmit="1" />
					</tr>
					<tr>
						<td><label>分组名称:</label></td>
						<td><input id="FZMC" name="FZMC" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,20]']" isSubmit="1" /></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td><label>老师列表:</label></td> -->
<!-- 						<td><input id="LSXM" name="LSXM" class="easyui-combogrid"  isSubmit="1" /></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td><label>家长列表:</label></td> -->
<!-- 						<td><input id="JZXM" name="JZXM" class="easyui-combogrid"  isSubmit="1" /></td> -->
<!-- 					</tr> -->
					<tr>
						<td><label>用户列表:</label></td>
						<td><input id="YHMC1" name="YHMC1" class="easyui-combogrid"  isSubmit="0" /></td>
					</tr>
					
					<tr>
						<td align="center" colspan="9"><a id="sub" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> <a id="cols" href="#"
							class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a>
					</tr>
				</table>
			</div>
		</form>
	</div>

</body>
<script type="text/javascript" src="${cxt}/js/view/teacher/yhz.js"></script>
<script type="text/javascript">
	
</script>
</html>
