
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
<body tableClass="skxl">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
			<div data-options="name:'Kcmc'">课程名称</div>
			<div data-options="name:'Kskxl'">可授课学龄</div>
		</div>
	</div>
	<div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
	<div id="dialog" style="display: none;">
		<form action="" method="post">
			<div>
				<h1 align="center" style="">授课学龄详细信息</h1>
				<hr />
			</div>
			<div>
				<table align="center">
					<input id="ID" name="ID" type="hidden" isSubmit="1" />
					<input id="KCID" name="KCID" type="hidden" isSubmit="1" />
					<input id="KSKXLLJFH" name="KSKXLLJFH" type="hidden" isSubmit="1" />
					<tr>
					</tr>
					<tr>
						<td><font color="red">*</font><label>课程ID:</label> <input id="KCMC" name="KCMC" class="easyui-combobox" required="true" isSubmit="1"
							style="width: 280px; height: 23px;" /></td>

					</tr>
					<tr>
						<td><font color="red">*</font><label>可授课学龄:</label> <input id="KSKXL" name="KSKXL" class="easyui-combobox" required="true" isSubmit="1"
							style="width: 280px; height: 23px;" /></td>

					</tr>
					<tr>
						<td><font color="red">*</font><label>可授课学龄逻辑符号:</label> <input id="KSKXLLJFH" name="KSKXLLJFH" class="easyui-textbox" required="true" isSubmit="1"
							style="width: 280px; height: 23px;" /></td>

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
			<h1 align="center" style="">授课学龄详细信息</h1>
			<hr />
		</div>
		<div>
			<table align="center">
				<tr>
				</tr>
				<tr>
					<td><font color="red">*</font><label>课程名称:</label> <input id="KCMC" name="KCMC" readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
					</td>

				</tr>
				<tr>
					<td><font color="red">*</font><label>可授课学龄:</label> <input id="KSKXL" name="KSKXL" readonly="readonly" isView="1" style="width: 280px; height: 23px;" />
					</td>

				</tr>
				<tr>
					<td><font>&nbsp;&nbsp;</font><label>创建时间:</label> <input id="CREATEAD" name="CREATEAD" readonly="readonly" isView="1"
						style="width: 280px; height: 23px;" /></td>
				</tr>
				<tr>
					<td><font>&nbsp;&nbsp;</font><label>更新时间:</label> <input id="UPDATEAD" name="UPDATEAD" readonly="readonly" isView="1"
						style="width: 280px; height: 23px;" /></td>
				</tr>
				<!--       <tr> -->
				<!--            <td><font color="red">*</font><label>KSKXLLJFH:</label> -->

				<!--                <input id="KSKXLLJFH"  name="KSKXLLJFH"  readonly="readonly" isView="1" style="width: 280px; height: 23px;" /> -->
				<!--             </td> -->

				<!--       </tr> -->
				<tr>
					<td align="center" colspan="3"><a id="col" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript" src="${cxt}/js/view/teacher/Skxl.js"></script>
<script type="text/javascript">
	
</script>
</html>
