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
<body tableClass="xxgl">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
			<div data-options="name:'Xxmc'">消息名称</div>
		</div>
	</div>
	<div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
	<div id="dialog" style="display: none;">
		<form action="" method="post">
			<div>
				<h1 align="center" style="">消息管理</h1>
				<hr />
			</div>
			<div>
				<table align="center">
					<tr>
						<input id="ID" name="ID" type="hidden" isSubmit="1" />
						<input id="FBID" name="FBID" type="hidden" isSubmit="1" />
					</tr>
					<tr>
						<td><label>消息名称:</label></td>
						<td><input id="XXMC" name="XXMC" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,32]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>消息标题:</label></td>
						<td><input id="XXBT" name="XXBT" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,32]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>消息内容:</label></td>
						<td><input id="XXNR" name="XXNR" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,200]']" isSubmit="1" style="width: 400px; height: 100px"  /></td>
					</tr>
					<tr>
						<input id="XXTP" name="XXTP" type="hidden" isSubmit="1" />
						<td><label>消息图片:</label></td>
						<td><iframe src="../../teacher/Xxgl_xxtpImg/default" style="width: 220px; height: 20px" frameborder="no" border="0" marginwidth="0" marginheight="0"
								scrolling="no" allowtransparency="yes"></iframe></td>
					</tr>
					<tr>
						<td id="xxtpView" style="text-align: center;"></td>
					</tr>
					<tr>
						<td><label>发布对象:</label></td>
						<td><input id="FBDX" name="FBDX" class="easyui-combobox"  isSubmit="1" /></td>
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
<script type="text/javascript" src="${cxt}/js/view/teacher/xxgl.js"></script>
<script type="text/javascript">
function setGrzp(uploadId) {
	if (uploadId != "") {
		$("#XXTP").val(uploadId);
		$("#xxtpView").html("<img style='width:32px;height:32px' src='../../read/xxgl/"+uploadId+"' width=100 height=100/>");
	}
}
	
</script>
</html>
