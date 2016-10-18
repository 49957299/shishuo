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
<body tableClass="lsjbxx">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
			<div data-options="name:'Zsxm'">真实姓名</div>
			<div data-options="name:'Zjhm'">证件号码</div>


		</div>
	</div>
	<div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
	<div id="dialog" style="display: none;">
		<form action="" method="post">
			<div>
				<h1 align="center" style="">老师基本信息</h1>
				<hr />
			</div>
			<div>
				<table align="center">
					<tr>
						<input id="ID" name="ID" type="hidden" isSubmit="1" />
					</tr>
					<tr>
						<td><label>用户ID:</label></td>
						<td><input id="YHID" name="YHID" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,20]']" isSubmit="1" /></td>
						<td><label>真实姓名:</label></td>
						<td><input id="ZSXM" name="ZSXM" class="easyui-textbox" data-options="required:true,validType:['chinese','length[0,20]']" isSubmit="1" /></td>
						<td><label>性别:</label></td>
						<td><input id="XB" name="XB" class="easyui-textbox" data-options="required:true" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>证件类型:</label></td>
						<td><input id="ZJLX" name="ZJLX" class="easyui-combobox" data-options="required:true" isSubmit="1" /></td>
						<td><label>证件号码:</label></td>
						<td><input id="ZJHM" name="ZJHM" class="easyui-textbox" data-options="required:true,validType:['idcard','length[0,18]']" isSubmit="1" /></td>
						<td><label>出生日期:</label></td>
						<td><input id="CSRQ" name="CSRQ" class="easyui-datetimebox" data-options="required:true" maxlength="8" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>出生地:</label></td>
						<td><input id="CSD" name="CSD" class="easyui-textbox" data-options="required:true,validType:['length[0,64]']" isSubmit="1" /></td>
						<td><label>毕业/就读高校:</label></td>
						<td><input id="BYJDGX" name="BYJDGX" class="easyui-textbox" data-options="required:true,validType:['length[0,64]']" isSubmit="1" /></td>
						<td><label>所在学院:</label></td>
						<td><input id="SZXY" name="SZXY" class="easyui-textbox" data-options="required:true,validType:['length[0,64]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>所学专业:</label></td>
						<td><input id="SXZY" name="SXZY" class="easyui-textbox" data-options="required:true,validType:['length[0,64]']" isSubmit="1" /></td>

						<td><label>目前身份:</label></td>
						<td><input id="MQSF" name="MQSF" class="easyui-textbox" isSubmit="1" /></td>
						<td><label>星级得分:</label></td>
						<td><input id="XJDF" name="XJDF" class="easyui-textbox" data-options="required:true,validType:['integer','length[0,1]']" validType="integer"
							isSubmit="1" /></td>
					</tr>
					<tr>
						<input id="GRZP" name="GRZP" type="hidden" isSubmit="1" />
						<input id="BYZZP" name="BYZZP" type="hidden" isSubmit="1" />
						<input id="XSZZP" name="XSZZP" type="hidden" isSubmit="1" />
						<td><label>个人照片:</label></td>
						<td><iframe src="../../teacher/Lsjbxx_grzImg/default" style="width: 220px; height: 20px" frameborder="no" border="0" marginwidth="0" marginheight="0"
								scrolling="no" allowtransparency="yes"></iframe></td>
						<td><label>毕业证照片:</label></td>
						<td><iframe src="../../teacher/Lsjbxx_byzImg/default" style="width: 220px; height: 30px" frameborder="no" border="0" marginwidth="0" marginheight="0"
								scrolling="no" allowtransparency="yes"></iframe></td>
						<td><label>学生证照片:</label></td>
						<td><iframe src="../../teacher/Lsjbxx_xszImg/default" style="width: 220px; height: 30px" frameborder="no" border="0" marginwidth="0" marginheight="0"
								scrolling="no" allowtransparency="yes"></iframe></td>
					</tr>
					<tr>
						<td></td>
						<td id="grzView"></td>
						<td></td>
						<td id="byzView"></td>
						<td></td>
						<td id="xszView"></td>
					</tr>
					<tr>
						<td><label>QQ:</label></td>
						<td><input id="QQ" name="QQ" class="easyui-textbox" isSubmit="1" /></td>
						<td><label>手机:</label></td>
						<td><input id="SJ" name="SJ" class="easyui-textbox" required="true" missingMessage="手机不能为空" maxlength="11" validType="mobile" isSubmit="1" /></td>

						<td><label>固话:</label></td>
						<td><input id="GH" name="GH" class="easyui-textbox" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>邮箱:</label></td>
						<td><input id="YX" name="YX" class="easyui-textbox" validType="email" isSubmit="1" /></td>
						<td><label>纬度:</label></td>
						<td><input id="LON" name="LON" class="easyui-textbox" isSubmit="1" /></td>
						<td><label>经度:</label></td>
						<td><input id="LAT" name="LAT" class="easyui-textbox" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>教龄:</label></td>
						<td><input id="JL" name="JL" class="easyui-textbox" data-options="required:true,validType:['integer','length[0,2]']" isSubmit="1" /></td>
						<td><label>学历:</label></td>
						<td><input id="XL" name="XL" class="easyui-textbox" isSubmit="1" /></td>
						<td><label>学历状态:</label></td>
						<td><input id="XLZT" name="XLZT" class="easyui-textbox" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>教学心得:</label></td>
						<td colspan="5"><input id="JXXD" name="JXXD" class="easyui-textbox" style="height:60px;width: 800px" data-options="required:true" isSubmit="1" /></td>
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
<script type="text/javascript" src="${cxt}/js/view/teacher/Lsjbxx.js"></script>
<script type="text/javascript">
	function setGrzp(uploadId) {
		if (uploadId != "") {
			$("#GRZP").val(uploadId);
			$("#grzView").html("<img style='width:32px;height:32px' src='../../read/grz/"+uploadId+"'/>");
		}
	}
	function setByzzp(uploadId) {
		if (uploadId != "") {
			$("#BYZZP").val(uploadId);
			$("#byzView").html("<img style='width:32px;height:32px' src='../../read/byz/"+uploadId+"'/>");
		}
	}
	function setXszzp(uploadId) {
		if (uploadId != "") {
			$("#XSZZP").val(uploadId);
			$("#xszView").html("<img style='width:32px;height:32px' src='../../read/xsz/"+uploadId+"'/>");

		}
	}
</script>
</html>
