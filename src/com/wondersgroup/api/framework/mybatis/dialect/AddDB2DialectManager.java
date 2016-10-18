package com.wondersgroup.api.framework.mybatis.dialect;

import com.wondersgroup.api.framework.logger.Logger;

/**
 *
 */
public class AddDB2DialectManager extends DefalutDialectManager {
	private static final Logger logger = new Logger(
			AddDB2DialectManager.class.getName());

	@Override
	public void setSupportDialects() {
		super.setSupportDialects();
		this.DIALECT_MAP.put(DialectConstant.DIALECT_DB2, new OracleDialect());
	}

}
