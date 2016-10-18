package com.wondersgroup.api.framework.mybatis.dialect;

import com.wondersgroup.api.framework.logger.Logger;

/**
 * 默认方言管理类。 默认支持3种方言：Oracle, Mysql, SQL Server. 如果想支持其它数据库，请扩展 ADialectManager
 * 类，并且重写setSupportDialects()方法 实现或直接继承 DefalutDialectManager 类也行，在重新的
 * setSupportDialects()方法中 开始处调用 super.setSupportDialects()即可.
 * 
 */
public class DefalutDialectManager extends ADialectManager {
	private static final Logger logger = new Logger(
			DefalutDialectManager.class.getName());

	/*
	 * 设置所要支持的方言.
	 * 
	 * @see
	 * net.carefx.framework.mybatis.dialect.ADialectManager#setSupportDialects()
	 */
	@Override
	public void setSupportDialects() {
		this.DIALECT_MAP.put(DialectConstant.DIALECT_ORACLE,
				new OracleDialect());
		this.DIALECT_MAP.put(DialectConstant.DIALECT_MICROSOFT_SQL_SERVER,
				new SQLServerDialect());
		this.DIALECT_MAP.put(DialectConstant.DIALECT_MYSQL, new MySqlDialect());
	}
}
