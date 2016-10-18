package com.wondersgroup.api.framework.mybatis.dialect;

import com.wondersgroup.api.framework.db.OperationCoreImpl;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.logger.Logger;

public class OracleDialect extends Dialect implements IDialect {

	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect
					.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			// int end = offset+limit;
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= " + endString
					+ " and rownum_ > " + offsetPlaceholder);
		} else {
			pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

	private static final Logger logger = new Logger(
			OracleDialect.class.getName());

	@Override
	public String getDatabaseName() {
		init();
		return "oracle";
	}

	private static final String CREATE_TOKEN = "create table token(id varchar2(100) not null,ttl number,userId varchar2(36),createdAt date,updateAt date)";
	private static final String CREATE_FILE = "create table file(id varchar2(36) not null,name varchar2(50),url varchar2(400),type varchar2(50),size number,createdAt date,updateAt date)";
	private static final String CREATE_ROLE = "create table role(id varchar2(36) not null,name varchar2(50),description varchar2(400),principals varchar2(50),createdAt date,updateAt date)";
	private static final String CREATE_ROLEMAPPING = "create table rolemapping(id varchar2(36) not null,principaltype varchar2(50),principalid varchar2(50),createdAt date,updateAt date)";
	private static final String CREATE_USER = "create table user(id varchar2(36) not null,username varchar2(50),password varchar2(50),email varchar2(50),emailverified int,accesstokens varchar2(100),mobile varchar2(20),createdAt date,updateAt date)";

	public void init() {
		try {
			createTable(CREATE_TOKEN);
			createTable(CREATE_FILE);
			createTable(CREATE_ROLE);
			createTable(CREATE_ROLEMAPPING);
			createTable(CREATE_USER);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	protected void createTable(String sql) throws Exception {
		OperationCoreImpl impl = OperationCoreImpl.createFactory();
		int rs = impl.executeUpdate(sql);
		if (rs != -1) {
			throw new Exception("创建表" + sql + "失败");
		}
	}

	@Override
	public String buildPageSqlPrefix() {
		return null;
	}

	@Override
	public String buildPageSqlSubfix() {
		return null;
	}
}
