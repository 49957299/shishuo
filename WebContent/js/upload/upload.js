$()
		.ready(
				function() {
					var myupload = {
						html : "",
						initialize : function() {
							this.html += "<div id=\"fileQueue\"></div>"
							this.html += "<input type=\"file\" name=\"fileInput\" id=\"fileInput\" />";
							this.html += "<input type=\"hidden\" name=\"businessCode\" id=\"businessCode\" />";
							this.html += "<input type=\"hidden\" name=\"businessType\" id=\"businessType\" />";
							this.html += "<input type=\"hidden\" name=\"uploadUserId\" id=\"uploadUserId\" />";
							this.html += "<input type=\"hidden\" name=\"uploadUserName\" id=\"uploadUserName\" />";
							this.html += "<p>";
							this.html += "<a href=\"javascript:upload('fileInput')\">上传</a>|<a href=\"javascript:clear('fileInput')\">取消上传</a>";
							this.html += "</p>";
						},

						finish : function() {
							this.initialize();
							$("#upload").append(this.html);
						}
					}
					myupload.finish();
					$("#fileInput")
							.uploadify(
									{
										height : 30,
										swf : '../../js/upload/uploadify.swf',
										uploader : '../../upload',
										width : 120,
										buttonCursor : 'hand',
										auto : false,
										method : 'get',
										buttonImage : null,
										buttonText : '选择文件',
										multi : true,
										queueSizeLimit : 10,
										onUploadStart : function(file) {
										},
										onSelectError : function(file,
												errorCode, errorMsg) {
											switch (errorCode) {
											case -100:
												alert("上传的文件数量已经超出系统限制的"
														+ $('#fileInput')
																.uploadify(
																		'settings',
																		'queueSizeLimit')
														+ "个文件！");
												break;
											case -110:
												alert("文件 ["
														+ file.name
														+ "] 大小超出系统限制的"
														+ $('#fileInput')
																.uploadify(
																		'settings',
																		'fileSizeLimit')
														+ "大小！");
												break;
											case -120:
												alert("文件 [" + file.name
														+ "] 大小异常！");
												break;
											case -130:
												alert("文件 [" + file.name
														+ "] 类型不正确！");
												break;
											}
										},
										onFallback : function() {
											alert("您未安装FLASH控件，无法上传！请安装FLASH控件后再试。");
										},
										onUploadSuccess : function(file, data,
												response) {
										},
										onUploadError : function(file,
												errorCode, errorMsg,
												errorString) { // 上传失败引起的事件
											switch (errorCode) {
											case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
												msgText += "HTTP 错误\n"
														+ errorMsg;
												break;
											case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
												msgText += "上传文件丢失，请重新上传";
												break;
											case SWFUpload.UPLOAD_ERROR.IO_ERROR:
												msgText += "IO错误";
												break;
											case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
												msgText += "安全性错误\n" + errorMsg;
												break;
											case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
												msgText += "每次最多上传 "
														+ this.settings.uploadLimit
														+ "个";
												break;
											case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
												msgText += errorMsg;
												break;
											case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
												msgText += "找不到指定文件，请重新操作";
												break;
											case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
												msgText += "参数错误";
												break;
											default:
												msgText += "文件:" + file.name
														+ "\n错误码:" + errorCode
														+ "\n" + errorMsg
														+ "\n" + errorString;
											}
											alert(msgText);
										},
										onQueueComplete : function(data) {// 队列中的文件都上传成功触发的事件
											// alert(data.uploadsSuccessful+'个文件上传成功！');
										}
									});
				});

function upload(id) {
	$("#" + id).uploadify("settings", "formData", {
		'uploadUserId' : $("#uploadUserId").val(),
		'uploadUserName' : encodeURI($("#uploadUserName").val()),
		'businessType' : $("#businessType").val(),
		'businessCode' : $("#businessCode").val()
	});

	$("#" + id).uploadify("upload", "*");
}
function clear(id) {
	$("#" + id).uploadify("cancel", "*");
}
