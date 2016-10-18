package com.wondersgroup.api.framework.enums;

public enum SqlEnums {

	INSERT("insert into {0}({1}) values ({2})"),
	UPDATE("update {0} set {1} "),
	DELETE("delete from {0} "),
	SELECT(" select {1} from {0} "),
	COUNT(" select count(*) count from {0} ");

	private String sql;

	SqlEnums(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
