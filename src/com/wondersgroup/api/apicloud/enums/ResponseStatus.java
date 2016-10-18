package com.wondersgroup.api.apicloud.enums;

public enum ResponseStatus {
	E100(100,"操作失败"),E101(101,"应用不存在"),
	E102(102,"数据云服务未开启，请在控制台的云开发中开启！"),E103(103,"您没有应用访问权限"),
	E104(104,"参数错误"),E105(105,"Class不存在"),
	E106(106,"Class名称错误"),E107(107,"Class名称已经存在"),
	E108(108,"连接数据库失败"),E109(109,"内置Class不可删除"),
	E110(110,"列字段已经存在"),E111(111,"删除Class失败"),
	E112(112,"删除数据失败"),E113(113,"默认字段不可删除"),
	E114(114,"删除列失败"),E115(115,"导入数据文件不可为空"),
	E116(116,"导入数据失败"),E117(117,"导出数据失败"),
	E118(118,"邮箱格式错误"),E119(119,"邮箱验证未开启, 请在控制台的云开发中开启！"),
	E120(120,"文件不存在"),E121(121,"文件上传失败"),
	E122(122,"保存文件失败"),E123(123,"文件大小不能大于20M"),
	E124(124,"导出数据不存在");
	
	private int status;
	private String error;

	private ResponseStatus(int status, String error) {
		this.status = status;
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
