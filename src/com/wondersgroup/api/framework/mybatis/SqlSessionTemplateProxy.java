package com.wondersgroup.api.framework.mybatis;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.wondersgroup.api.framework.logger.Logger;

/**
 *
 */
public class SqlSessionTemplateProxy extends SqlSessionTemplate {
	private static final Logger logger = new Logger(
			SqlSessionTemplateProxy.class.getName());

	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * Constructs a Spring managed SqlSession with the {@code SqlSessionFactory}
	 * provided as an argument.
	 *
	 * @param sqlSessionFactory
	 */
	public SqlSessionTemplateProxy(SqlSessionFactory sqlSessionFactory,
			SqlSessionTemplate sqlSessionTemplate) {
		super(sqlSessionFactory, sqlSessionFactory.getConfiguration()
				.getDefaultExecutorType());

		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionTemplate.getSqlSessionFactory();
	}

	@Override
	public ExecutorType getExecutorType() {
		return sqlSessionTemplate.getExecutorType();
	}

	@Override
	public PersistenceExceptionTranslator getPersistenceExceptionTranslator() {
		return sqlSessionTemplate.getPersistenceExceptionTranslator();
	}

	@Override
	public Object selectOne(String statement) {
		return sqlSessionTemplate.selectOne(statement);
	}

	@Override
	public Object selectOne(String statement, Object parameter) {
		return sqlSessionTemplate.selectOne(statement, parameter);
	}

	@Override
	public Map<?, ?> selectMap(String statement, String mapKey) {
		return sqlSessionTemplate.selectMap(statement, mapKey);
	}

	@Override
	public Map<?, ?> selectMap(String statement, Object parameter, String mapKey) {
		return sqlSessionTemplate.selectMap(statement, parameter, mapKey);
	}

	@Override
	public Map<?, ?> selectMap(String statement, Object parameter,
			String mapKey, RowBounds rowBounds) {
		return sqlSessionTemplate.selectMap(statement, parameter, mapKey,
				rowBounds);
	}

	@Override
	public List<?> selectList(String statement) {
		return sqlSessionTemplate.selectList(statement);
	}

	@Override
	public List<?> selectList(String statement, Object parameter) {
		return sqlSessionTemplate.selectList(statement, parameter);
	}

	@Override
	public List<?> selectList(String statement, Object parameter,
			RowBounds rowBounds) {
		return sqlSessionTemplate.selectList(statement, parameter, rowBounds);
	}

	@Override
	public void select(String statement, ResultHandler handler) {
		sqlSessionTemplate.select(statement, handler);
	}

	@Override
	public void select(String statement, Object parameter, ResultHandler handler) {
		sqlSessionTemplate.select(statement, parameter, handler);
	}

	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds,
			ResultHandler handler) {
		sqlSessionTemplate.select(statement, parameter, rowBounds, handler);
	}

	@Override
	public int insert(String statement) {
		return sqlSessionTemplate.insert(statement);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return sqlSessionTemplate.insert(statement, parameter);
	}

	@Override
	public int update(String statement) {
		return sqlSessionTemplate.update(statement);
	}

	@Override
	public int update(String statement, Object parameter) {
		return sqlSessionTemplate.update(statement, parameter);
	}

	@Override
	public int delete(String statement) {
		return sqlSessionTemplate.delete(statement);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return sqlSessionTemplate.delete(statement, parameter);
	}

	@Override
	public <T> T getMapper(Class<T> type) {
		return sqlSessionTemplate.getMapper(type);
	}

	@Override
	public void commit() {
		sqlSessionTemplate.commit();
	}

	@Override
	public void commit(boolean force) {
		sqlSessionTemplate.commit(force);
	}

	@Override
	public void rollback() {
		sqlSessionTemplate.rollback();
	}

	@Override
	public void rollback(boolean force) {
		sqlSessionTemplate.rollback(force);
	}

	@Override
	public void close() {
		sqlSessionTemplate.close();
	}

	@Override
	public void clearCache() {
		sqlSessionTemplate.clearCache();
	}

	@Override
	public Configuration getConfiguration() {
		return sqlSessionTemplate.getConfiguration();
	}

	@Override
	public Connection getConnection() {
		return sqlSessionTemplate.getConnection();
	}

}
