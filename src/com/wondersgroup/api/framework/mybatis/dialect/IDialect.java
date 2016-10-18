package com.wondersgroup.api.framework.mybatis.dialect;

public interface IDialect {

	public String getDatabaseName();

	/**
	 * build the page sql prefix
	 * 
	 * @return
	 */
	public String buildPageSqlPrefix();

	/**
	 * build the page sql prefix
	 * 
	 * @return
	 */
	public String buildPageSqlSubfix();
}
