
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="hdfb">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
			<div data-options="name:'Hdmc'">活动名称</div>
			<div data-options="name:'Yhqmc'">优惠券名称</div>
		</div>
	</div>
	<div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
	<div id="dialog" style="display: none;">
		<form action="" method="post">
			<div>
				<h1 align="center" style="">优惠券详细信息</h1>
				<hr />
			</div>
			<div>
				<table align="center">
					<input id="ID" name="ID" type="hidden" isSubmit="1" />
					<tr>
						<td><label>活动名称:</label></td>
						<td><input id="HDMC" name="HDMC" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,64]']" isSubmit="1" /></td>
						<td><label>活动详细页:</label></td>
						<td><input id="HDXXY" name="HDXXY" class="easyui-textbox" data-options="required:true" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>活动开始时间:</label></td>
						<td><input id="HDKSSJ" name="HDKSSJ" class="easyui-datetimebox" data-options="required:true" isSubmit="1" /></td>
						<td><label>活动结束时间:</label></td>
						<td><input id="HDJSSJ" name="HDJSSJ" class="easyui-datetimebox" data-options="required:true" isSubmit="1" /></td>
					</tr>
					<tr>
						<input id="YHQID" name="YHQID" type="hidden" isSubmit="1" />
						<td><label>优惠券:</label></td>
						<td><input id="YHQMC" name="YHQMC" class="easyui-textbox" data-options="required:true" isSubmit="1" /></td>
						<td><label>活动状态:</label></td>
						<td><input id="HDZT" name="HDZT" class="easyui-textbox" data-options="required:true" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>积分数量:</label></td>
						<td colspan="3"><input id="JFSL" name="JFSL" class="easyui-numberbox" data-options="required:true,validType:['integer','length[0,10]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>活动简介:</label></td>
						<td colspan="3"><input id="HFJJ" name="HFJJ" class="easyui-textbox" style="width: 400px; height: 100px"
							data-options="required:true,multiline:true,validType:['unnormal','length[0,128]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<td align="center" colspan="3"><a id="sub" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> <a id="cols" href="#"
							class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="${cxt}/js/view/teacher/Hdfb.js"></script>
<script type="text/javascript">
	
</script>
</html>
