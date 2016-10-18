
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../views/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Cache-Control content=no-cache />
<style>
</style>
</head>
<body style="background-color: #fff;">
	<div>
		<form action="../../upload/zxglbttp/Zxgl_bttpImg" id="upload" method="post" enctype="multipart/form-data">
			<input id="YHQTPUP" name="YHQTPUP" class="easyui-filebox" isSubmit="1" /> <a id="btn" href="#">上传</a>
		</form>
	</div>
</body>
<script type="text/javascript">
	$().ready(function() {
		$('#YHQTPUP').filebox({
			buttonText : '选择文件....',
			accept : "images"
		})

		$('#btn').linkbutton({
			iconCls : 'icon-add'
		});
		$('#btn').bind('click', function() {
			$('#upload').submit();
		});
		window.parent.setBttp("${uploadId}");
	});
</script>
</html>
