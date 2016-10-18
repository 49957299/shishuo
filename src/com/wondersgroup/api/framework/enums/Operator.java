package com.wondersgroup.api.framework.enums;

public enum Operator {
	AND("&"), OR("|"), GT(">"), GTE(">="), LT("<"), LTE("<="), BETWEEN("between"), INQ("in"), NIN("not in"), NEAR("near"), NE("!="), LIKE("like"), NLIKE(
			"not like"), EQ("="), IN("in"),INQLIKE("");
	private String operator;

	Operator(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
