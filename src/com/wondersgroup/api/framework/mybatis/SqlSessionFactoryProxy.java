package com.wondersgroup.api.framework.mybatis;

import java.sql.Connection;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

import com.wondersgroup.api.framework.logger.Logger;

/**
 *
 */
public class SqlSessionFactoryProxy implements SqlSessionFactory {
	private static final Logger logger = new Logger(
			SqlSessionFactoryProxy.class.getName());

	private SqlSessionFactory m_sqlSessionFactory;
	private String m_dialectName;

	public SqlSessionFactoryProxy(SqlSessionFactory sqlSessionFactory) {
		this.m_sqlSessionFactory = sqlSessionFactory;
	}

	public SqlSessionFactoryProxy(SqlSessionFactory sqlSessionFactory,
			String dialectName) {
		this.m_sqlSessionFactory = sqlSessionFactory;
		this.m_dialectName = dialectName;
	}

	public void setDialectName(String dialectName) {
		this.m_dialectName = dialectName;
	}

	@Override
	public Configuration getConfiguration() {
		return m_sqlSessionFactory.getConfiguration();
	}

	@Override
	public SqlSession openSession() {
		SqlSession sqlSession = m_sqlSessionFactory.openSession();
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

	@Override
	public SqlSession openSession(boolean autoCommit) {
		SqlSession sqlSession = m_sqlSessionFactory.openSession(autoCommit);
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

	@Override
	public SqlSession openSession(Connection connection) {
		SqlSession sqlSession = m_sqlSessionFactory.openSession(connection);
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

	@Override
	public SqlSession openSession(ExecutorType execType) {
		SqlSession sqlSession = m_sqlSessionFactory.openSession(execType);
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

	@Override
	public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
		SqlSession sqlSession = m_sqlSessionFactory.openSession(execType,
				autoCommit);
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

	@Override
	public SqlSession openSession(ExecutorType execType, Connection connection) {
		SqlSession sqlSession = m_sqlSessionFactory.openSession(execType,
				connection);
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

	@Override
	public SqlSession openSession(ExecutorType execType,
			TransactionIsolationLevel level) {
		SqlSession sqlSession = m_sqlSessionFactory
				.openSession(execType, level);
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

	@Override
	public SqlSession openSession(TransactionIsolationLevel level) {
		SqlSession sqlSession = m_sqlSessionFactory.openSession(level);
		SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(sqlSession,
				m_dialectName);
		return sqlSessionProxy;
	}

}
