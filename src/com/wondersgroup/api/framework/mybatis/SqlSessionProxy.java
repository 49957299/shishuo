package com.wondersgroup.api.framework.mybatis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.wondersgroup.api.framework.logger.Logger;

/**
 *
 */
public class SqlSessionProxy implements SqlSession {
	private static final Logger logger = new Logger(
			SqlSessionProxy.class.getName());

	private SqlSession m_sqlSession;
	private String m_dialectName;

	public SqlSessionProxy(SqlSession sqlSession, String dialectName) {
		this.m_sqlSession = sqlSession;
		this.m_dialectName = dialectName;
	}

	/**
	 * get the statement with database dialect
	 * 
	 * @param statement
	 * @return statement or statement_databasename
	 */
	public String getStatement(String statement) {
		if (logger.isEnteringLoggable()) {
			logger.entering(statement);
		}
		try {
			DatabaseMetaData dbMD = m_sqlSession.getConnection().getMetaData();
			String databaseName = dbMD.getDatabaseProductName().toLowerCase();

			/********************************************************************
			 * 目标：根据传入的 statement ID 查找所有的SQL映射配置文件： * 1. 如果发现映射脚本，则执行。 * 2.
			 * 如果没有发现，则在类全路径后面加上正在运行的数据库名称和方法后面加 * 上运行数据库名称。 * 例原statement是
			 * org.byd.arke.mybatis3.test.UserMapper.addUser *
			 * 需要调UserMapper类的addUser(User user)方法，那么就应该改为 *
			 * org.byd.arke.mybatis3
			 * .test.UserMapper.addUser_oracle.addUser_oracle
			 ********************************************************************/

			Configuration configuration = m_sqlSession.getConfiguration();
			Collection<String> statements = configuration
					.getMappedStatementNames();

			if (!statements.contains(statement)) {
				// 不包含在标准SQL映射文件中.
				if (logger.isInfoLoggable()) {
					logger.info("invoke nonstandard sql reference.");
				}

				int lastPointIndex = statement.lastIndexOf(".");
				String curAllClassPath = statement.substring(0, lastPointIndex); // 类全名,如
																					// org.byd.arke.mybatis3.test.UserMapper
				String curFunctionStatement = statement
						.substring(lastPointIndex); // 反映的方法名addUser
				StringBuffer sb = new StringBuffer();
				sb.append(curAllClassPath + "_" + databaseName);
				sb.append(curFunctionStatement + "_" + databaseName);

				statement = sb.toString();

				if (logger.isInfoLoggable()) {
					logger.info("invoke nonstandard sql reference. reset statement id : "
							+ statement);
				}
			}

			//
			//
			// for (String _statement : statements)
			// {
			// if (_statement.indexOf ("_") <= 0 || _statement.lastIndexOf ("_")
			// == (_statement.length () - 1))
			// {
			// continue;
			// }
			//
			// String curDatabaseName = _statement.substring
			// (_statement.lastIndexOf ("_") + 1).toLowerCase ();
			// String curStatement = _statement.substring (0,
			// _statement.lastIndexOf ("_"));
			// if (curStatement.equals (statement) && databaseName.equals
			// (curDatabaseName))
			// {
			// statement = _statement;
			// if (logger.isFinestLoggable ())
			// {
			// logger.finest ("get dialect statement " + _statement);
			// }
			// break;
			// }
			// }
		} catch (SQLException e) {
			logger.admin("get statement error", e);
		}

		if (logger.isExitingLoggable()) {
			logger.exiting(statement);
		}

		return statement;
	}

	public Object selectOne(String statement) {

		return m_sqlSession.selectOne(getStatement(statement));
	}

	public Object selectOne(String statement, Object parameter) {
		return m_sqlSession.selectOne(getStatement(statement), parameter);
	}

	public List selectList(String statement) {
		return m_sqlSession.selectList(getStatement(statement));
	}

	public List selectList(String statement, Object parameter) {
		return m_sqlSession.selectList(getStatement(statement), parameter);
	}

	public List selectList(String statement, Object parameter,
			RowBounds rowBounds) {
		return m_sqlSession.selectList(getStatement(statement), parameter,
				rowBounds);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		m_sqlSession.select(getStatement(statement), parameter, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds,
			ResultHandler handler) {
		m_sqlSession.select(getStatement(statement), parameter, rowBounds,
				handler);
	}

	public int insert(String statement) {
		return m_sqlSession.insert(getStatement(statement));
	}

	public int insert(String statement, Object parameter) {
		return m_sqlSession.insert(getStatement(statement), parameter);
	}

	public int update(String statement) {
		return m_sqlSession.update(getStatement(statement));
	}

	public int update(String statement, Object parameter) {
		return m_sqlSession.update(getStatement(statement), parameter);
	}

	public int delete(String statement) {
		return m_sqlSession.delete(getStatement(statement));
	}

	public int delete(String statement, Object parameter) {
		return m_sqlSession.delete(getStatement(statement), parameter);
	}

	public void commit() {
		m_sqlSession.commit();
	}

	public void commit(boolean force) {
		m_sqlSession.commit(force);
	}

	public void rollback() {
		m_sqlSession.rollback();
	}

	public void rollback(boolean force) {
		m_sqlSession.rollback(force);
	}

	public void close() {
		m_sqlSession.close();
	}

	public void clearCache() {
		m_sqlSession.clearCache();
	}

	public Configuration getConfiguration() {
		return m_sqlSession.getConfiguration();
	}

	public <T> T getMapper(Class<T> type) {
		return m_sqlSession.getMapper(type);
	}

	public Connection getConnection() {
		return m_sqlSession.getConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.session.SqlSession#select(java.lang.String,
	 * org.apache.ibatis.session.ResultHandler)
	 */
	@Override
	public void select(String arg0, ResultHandler arg1) {
		m_sqlSession.select(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.session.SqlSession#selectMap(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Map selectMap(String arg0, String arg1) {
		return m_sqlSession.selectMap(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.session.SqlSession#selectMap(java.lang.String,
	 * java.lang.Object, java.lang.String)
	 */
	@Override
	public Map selectMap(String arg0, Object arg1, String arg2) {
		return m_sqlSession.selectMap(arg0, arg1, arg2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.session.SqlSession#selectMap(java.lang.String,
	 * java.lang.Object, java.lang.String, org.apache.ibatis.session.RowBounds)
	 */
	@Override
	public Map selectMap(String arg0, Object arg1, String arg2, RowBounds arg3) {
		return m_sqlSession.selectMap(arg0, arg1, arg2, arg3);
	}

	@Override
	public List<BatchResult> flushStatements() {
		// TODO Auto-generated method stub
		return null;
	}

}
