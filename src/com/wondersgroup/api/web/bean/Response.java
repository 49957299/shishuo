package com.wondersgroup.api.web.bean;

import com.wondersgroup.api.framework.enums.SuccessEnums;

/**
 * 响应结果
 * 
 * @author Administrator
 *
 */
public class Response {

	private SuccessEnums success;// 0 成功 1失败
	private Object result;// 返回结果
	private String message;// 信息

	public SuccessEnums getSuccess() {
		return success;
	}

	public void setSuccess(SuccessEnums success) {
		this.success = success;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
