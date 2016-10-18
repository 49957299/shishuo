
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="yhq">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
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
						<td><label>优惠券名称:</label></td>
						<td><input id="YHQMC" name="YHQMC" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,20]']" isSubmit="1" /></td>
						<td style="margin-left: 10px"><label>数量:</label></td>
						<td><input id="SL" name="SL" class="easyui-textbox" data-options="required:true,validType:['intOrFloat','length[0,8]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>优惠方式:</label></td>
						<td><input id="YHFS" name="YHFS" class="easyui-textbox" data-options="required:true" isSubmit="1" /></td>
						<td style="margin-left: 10px"><label>优惠值:</label></td>
						<td><input id="YHZ" name="YHZ" class="easyui-textbox" data-options="required:true,validType:['intOrFloat','length[0,8]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>使用条件比较符号:</label></td>
						<td><input id="SYTJBJFH" name="SYTJBJFH" class="easyui-textbox" data-options="required:true" isSubmit="1" /></td>
						<td><label>使用条件比较类型:</label></td>
						<td><input id="SYTJBJLX" name="SYTJBJLX" class="easyui-textbox" data-options="required:true" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>使用条件比较数值:</label></td>
						<td><input id="SYTJBJSZ" name="SYTJBJSZ" class="easyui-textbox" data-options="required:true,validType:['intOrFloat','length[0,8]']" isSubmit="1" /></td>
						<td><label>有效期:</label></td>
						<td><input id="YXQ" name="YXQ" class="easyui-datebox" data-options="required:true" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>优惠券说明:</label></td>
						<td colspan="3"><input id="YHQSM" name="YHQSM" class="easyui-textbox" data-options="required:true,multiline:true" style="width:400px;height: 100px" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>优惠券图片:</label></td>
						<input id="YHQTP" name="YHQTP" isSubmit="1" type="hidden" />
						<td colspan="3"><iframe src="../../teacher/yhqImg/default" style="width: 400px; height: 30px" frameborder="no" border="0" marginwidth="0"
								marginheight="0" scrolling="no" allowtransparency="yes"></iframe></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td id="imgView" colspan="2"></td>
						<td></td>
						<td></td>
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
<script type="text/javascript" src="${cxt}/js/view/teacher/Yhq.js"></script>
<script type="text/javascript">
	function setUplod(uploadId) {
		if (uploadId != "") {
			$("#YHQTP").val(uploadId);
			$("#imgView").html("<img style='width:32px;height:32px' src='../../read/yhq/"+uploadId+"'/>")
		}
	}
</script>
</html>
