package com.wondersgroup.api.framework.enums;

public enum SuccessEnums {
	SUCCESS(0), ERROR(-1);

	private int status;

	SuccessEnums(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
