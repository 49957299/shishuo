package com.wondersgroup.api.web.bean;

import com.wondersgroup.api.framework.enums.Operator;

public class ServiceExpression {
	public ServiceExpression() {
	}

	private String fieldName; // 属性名
	private Object value; // 对应值
	private Operator operator; // 计算符

	public ServiceExpression(String fieldName, Object value, Operator operator) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

}
