package com.wondersgroup.api.framework.mybatis.dialect;

import com.wondersgroup.api.framework.logger.Logger;

/**
 *
 */
public class DB2Dialect implements IDialect {
	private static final Logger logger = new Logger(DB2Dialect.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.carefx.framework.mybatis.dialect.Dialect#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return "db2";
	}

	@Override
	public String buildPageSqlPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildPageSqlSubfix() {
		// TODO Auto-generated method stub
		return null;
	}

}
