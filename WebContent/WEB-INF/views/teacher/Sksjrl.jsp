
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
label {
	display: inline-block;
	width: 80px;
}
</style>
<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="sksjrl">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
			<div data-options="name:'Lsxm'">老师姓名</div>
			<div data-options="name:'N'">年</div>
			<div data-options="name:'Y'">月</div>
			<div data-options="name:'R'">日</div>
		</div>
	</div>
	<div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
	<div id="dialog" style="display: none;">
		<form action="" method="post">
			<div>
				<h1 align="center" style="">授课时间日历详细信息</h1>
				<hr />
			</div>
			<div>
				<table align="center">
					<input id="ID" name="ID" type="hidden" isSubmit="1" />
					<tr>
					</tr>
					<tr>
						<td><label>LSID:</label> <input id="LSID" name="LSID" class="easyui-textbox" required="true" validType="username" isSubmit="1" /></td>

					</tr>
					<tr>
						<td><label>N:</label> <input id="N" name="N" class="easyui-textbox" required="true" validType="username" isSubmit="1" /></td>

					</tr>
					<tr>
						<td><label>Y:</label> <input id="Y" name="Y" class="easyui-textbox" required="true" validType="username" isSubmit="1" /></td>

					</tr>
					<tr>
						<td><label>R:</label> <input id="R" name="R" class="easyui-textbox" required="true" validType="username" isSubmit="1" /></td>

					</tr>
					<tr>
						<td><label>KSSJ:</label> <input id="KSSJ" name="KSSJ" class="easyui-textbox" required="true" validType="username" isSubmit="1" /></td>

					</tr>
					<tr>
						<td><label>JSJS:</label> <input id="JSJS" name="JSJS" class="easyui-textbox" required="true" validType="username" isSubmit="1" /></td>

					</tr>
					<tr>
						<td><label>SFSD:</label> <input id="SFSD" name="SFSD" class="easyui-textbox" required="true" validType="username" isSubmit="1" /></td>

					</tr>
					<tr>
						<td><label>CREATEAD:</label> <input id="CREATEAD" name="CREATEAD" isSubmit="1" class="easyui-datetimebox" required="true" /></td>
					</tr>
					<tr>
						<td><label>UPDATEAD:</label> <input id="UPDATEAD" name="UPDATEAD" isSubmit="1" class="easyui-datetimebox" required="true" /></td>
					</tr>
					<tr>
						<td align="center" colspan="3"><a id="sub" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> <a id="cols" href="#"
							class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div id="view" style="display: none;">
		<div>
			<h1 align="center" style="">授课时间日历详细信息</h1>
			<hr />
		</div>
		<div>
			<table align="center">
				<tr>
				</tr>
				<tr>
					<td><label>老师姓名:</label> <input id="LSXM" name="LSXM" readonly="readonly" isView="1" /></td>

				</tr>
				<tr>
					<td><label>年:</label> <input id="N" name="N" readonly="readonly" isView="1" /></td>

				</tr>
				<tr>
					<td><label>月:</label> <input id="Y" name="Y" readonly="readonly" isView="1" /></td>

				</tr>
				<tr>
					<td><label>日:</label> <input id="R" name="R" readonly="readonly" isView="1" /></td>

				</tr>
				<tr>
					<td><label>开始时间:</label> <input id="KSSJ" name="KSSJ" readonly="readonly" isView="1" /></td>

				</tr>
				<tr>
					<td><label>结束时间:</label> <input id="JSJS" name="JSJS" readonly="readonly" isView="1" /></td>

				</tr>
				<tr>
					<td><label>是否锁定:</label> <input id="SFSD" name="SFSD" readonly="readonly" isView="1" /></td>

				</tr>
				<tr>
					<td><font>&nbsp;&nbsp;</font><label>创建时间:</label> <input id="CREATEAD" name="CREATEAD" readonly="readonly" isView="1" /></td>
				</tr>
				<tr>
					<td><font>&nbsp;&nbsp;</font><label>更新时间:</label> <input id="UPDATEAD" name="UPDATEAD" readonly="readonly" isView="1" /></td>
				</tr>
				<tr>
					<td align="center" colspan="3"><a id="col" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript" src="${cxt}/js/view/teacher/Sksjrl.js"></script>
<script type="text/javascript">
	
</script>
</html>
