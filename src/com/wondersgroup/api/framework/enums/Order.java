package com.wondersgroup.api.framework.enums;

public class Order {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private String name;
	private String order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
