<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" charset="utf-8" src="${cxt}/js/ueditor/ueditor.config.js" ></script>
<script type="text/javascript" charset="utf-8" src="${cxt}/js/ueditor/ueditor.all.min.js" ></script>
<script type="text/javascript" charset="utf-8" src="${cxt}/js/ueditor/lang/zh-cn/zh-cn.js" ></script>
<style>
label {
	display: inline-block;
	width: 86px;
}
</style>

<meta http-equiv=Cache-Control content=no-cache />
</head>
<body tableClass="zxgl">
	<div id="search" style="margin-bottom: 10px; width: 100%">
		<input id="ss" style="width: 50%"></input>
		<div id="mm" style="width: 50%">
			<div data-options="name:'all',iconCls:'icon-ok'">查询所有</div>
			<div data-options="name:'Zsxm'">标题</div>
		</div>
	</div>
	<div id="dataGrid" class="easyui-datagrid" style="width: 100%; height: 450px"></div>
	<div id="dialog" style="display: none;">
		<form action="" method="post">
			<div>
				<h1 align="center" style="">资讯基本信息</h1>
				<hr />
			</div>
			<div>
				<table align="center">
					<tr>
						<input id="ID" name="ID" type="hidden" isSubmit="1" />
					</tr>
					<tr>
						<td><label>标题:</label></td>
						<td><input id="BT" name="BT" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,20]']" isSubmit="1" /></td>
						<td><label>摘要:</label></td>
						<td><input id="ZY" name="ZY" class="easyui-textbox" data-options="required:true,validType:['unnormal','length[0,20]']" isSubmit="1" /></td>
					</tr>
					<tr>
						<input id="BTTP" name="BTTP" type="hidden" isSubmit="1" />
						<input id="NRTP" name="NRTP" type="hidden" isSubmit="1" />
						<td><label>标题图片:</label></td>
						<td><iframe src="../../teacher/Zxgl_bttpImg/default" style="width: 220px; height: 30px" frameborder="no" border="0" marginwidth="0" marginheight="0"
								scrolling="no" allowtransparency="yes"></iframe></td>
						<td><label>内容图片:</label></td>
						<td><iframe src="../../teacher/Zxgl_nrtpImg/default" style="width: 220px; height: 30px" frameborder="no" border="0" marginwidth="0" marginheight="0"
								scrolling="no" allowtransparency="yes"></iframe></td>
					</tr>
					<tr>
						<td></td>
						<td id="bttpView"></td>
						<td></td>
						<td id="nrtpView"></td>
					</tr>
					<tr>
						<td><label>一级分类:</label></td>
						<td><input id="YJFL" name="YJFL" class="easyui-textbox" data-options="required:true,validType:['length[0,32]']" isSubmit="1" /></td>
						<td><label>二级分类:</label></td>
						<td><input id="EJFL" name="EJFL" class="easyui-textbox" data-options="required:true,validType:['length[0,32]']" maxlength="8" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>发布日期:</label></td>
						<td colspan="3"><input id="FBRQ" name="FBRQ" class="easyui-datebox" data-options="required:true" isSubmit="1" /></td>
					</tr>
					<tr>
						<td><label>内容:</label></td>
						<td colspan="3"><script id="editor" type="text/plain" style="width:600px;height:200px;"></script></td>
					</tr>
					<tr>
						<td align="center" colspan="9"><a id="sub" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> <a id="cols" href="#"
							class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div id="view" style="display: none; width: 100%">
		<div>
			<h1 align="center">资讯内容信息</h1>
			<hr />
		</div>
		<div>
			<div id="editorview"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var opts = {
		/*  //定制工具按钮
		 toolbars:[["fullscreen","source","undo","redo","bold","Italic","Underline","|",
		 "StrikeThrough","Horizontal","Date","FontFamily","FontSize","LineHeight","CustomStyle",
		 "JustifyLeft", "JustifyRight", "JustifyCenter","RemoveFormat"]], */
		//获取光标是，是否自动清空初始化数据
		autoClearinitialContent : false,
		//是否展示元素路径
		elementPathEnabled : false,
		//是否计数
		wordCount : false,
		//高度是否自动增长
		autoHeightEnabled : false,
		//后台接受UEditor的数据的参数名称
		textarea : "contact_content"
	};
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = UE.getEditor("editor", opts);
</script>
<script type="text/javascript" src="${cxt}/js/view/teacher/Zxgl.js"></script>
<script type="text/javascript">
	function setBttp(uploadId) {
		if (uploadId != "") {
			$("#BTTP").val(uploadId);
			$("#bttpView").html("<img style='width:32px;height:32px' src='../../read/zxglbttp/"+uploadId+"'/>");
		}
	}
	function setNrtp(uploadId) {
		if (uploadId != "") {
			$("#NRTP").val(uploadId);
			$("#nrtpView").html("<img style='width:32px;height:32px' src='../../read/zxglnrtp/"+uploadId+"'/>");
		}
	}
</script>
</html>
