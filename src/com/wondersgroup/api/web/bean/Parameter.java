package com.wondersgroup.api.web.bean;

import com.wondersgroup.api.framework.enums.Operator;

public class Parameter {
	private String name;
	private Operator op;
	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Operator getOp() {
		return op;
	}

	public void setOp(Operator op) {
		this.op = op;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
