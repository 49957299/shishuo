package com.wondersgroup.api.framework.mybatis.dialect;

import com.wondersgroup.api.framework.logger.Logger;

/**
 * 方言常量定义类.
 */
public abstract class DialectConstant {
	public static String DIALECT_ORACLE = "oracle";
	public static String DIALECT_MICROSOFT_SQL_SERVER = "microsoft sql server";
	public static String DIALECT_MYSQL = "mysql";
	public static String DIALECT_DB2 = "db2";
	public static String DIALECT_DB2_AS_400 = "db2 as/400";
	public static String DIALECT_DB2_OS_39 = "db2 os390";
	public static String DIALECT_POSTGRESQL = "postgresql";
	public static String DIALECT_MYSQL_WITH_INNODB = "mysql with innodb";
	public static String DIALECT_MYSQL_WITH_MYISAM = "mysql with myisam";
	public static String DIALECT_SYBASE_ = "sybase";
	public static String DIALECT_SYBASE_ANYWHERE = "sybase anywhere";
	public static String DIALECT_INFORMIX = "informix";
	public static String DIALECT_SPA_DB = "spa db";
	public static String DIALECT_HYPERSONIC_SQL = "hypersonicsql";
	public static String DIALECT_INGRES = "ingres";
	public static String DIALECT_PROGRESS = "progress";
	public static String DIALECT_MCKOI_SQL = "mckoi sql";
	public static String DIALECT_INTERBASE = "interbase";
	public static String DIALECT_POINTBASE = "pointbase";
	public static String DIALECT_FRONTBASE = "frontbase";
	public static String DIALECT_FIREBIRD = "firebird";

	private static final Logger logger = new Logger(
			DialectConstant.class.getName());

}
