package com.wondersgroup.api.framework.mybatis;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;

import com.wondersgroup.api.framework.content.ErrorCodes;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.logger.Logger;
import com.wondersgroup.api.framework.mybatis.dialect.ADialectManager;
import com.wondersgroup.api.framework.mybatis.dialect.Dialect;
import com.wondersgroup.api.framework.mybatis.dialect.IDialect;

/**
 *
 */
public class SqlSessionFactoryBean extends
		org.mybatis.spring.SqlSessionFactoryBean {
	private static final Logger logger = new Logger(
			SqlSessionFactoryBean.class.getName());

	private ADialectManager m_dialectManager = null;

	private Resource[] extraConfigLocation;

	public void setExtraConfigLocation(Resource[] extraConfigLocation) {
		if (logger.isEnteringLoggable()) {
			logger.entering(String.valueOf(extraConfigLocation));
		}
		this.extraConfigLocation = extraConfigLocation;
		logger.exiting();
	}

	public ADialectManager getDialectManager() {
		logger.entering();
		if (logger.isExitingLoggable()) {
			logger.exiting(String.valueOf(m_dialectManager));
		}
		return m_dialectManager;
	}

	public void setDialectManager(ADialectManager dialectManager) {
		if (logger.isEnteringLoggable()) {
			logger.entering(String.valueOf(dialectManager));
		}
		this.m_dialectManager = dialectManager;
		// 设置支持的方言.
		this.m_dialectManager.setSupportDialects();
		logger.exiting();
	}

	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();

		IDialect dialect = getDialectSupported(sqlSessionFactory);

		// 根据数据库特性，把本数据库的Statement后缀去除，以兼容MyBatis-Spring DAO整合方式
		Configuration configuration = sqlSessionFactory.getConfiguration();

		if (null != this.extraConfigLocation) {
			System.out.println("==============读取模块中配置文件"
					+ this.extraConfigLocation.length + "个");
			for (int i = 0; i < this.extraConfigLocation.length; i++) {
				Configuration extraConfiguration = buildExtraConfiguration(i);

				addStatementsAndTypeAlias(configuration, extraConfiguration);
			}
		}

		// Configuration configuration = sqlSession.getConfiguration ();
		Object[] statements = (Object[]) configuration.getMappedStatements()
				.toArray(new Object[0]);
		for (Object statementTmp : statements) {
			Object obj = statementTmp;
			if (!(obj instanceof MappedStatement))
				continue;
			MappedStatement statement = (MappedStatement) obj;
			String id = statement.getId();

			int index = id.lastIndexOf("_" + dialect.getDatabaseName());
			if (index > 0) {
				// 得到最新的Statement ID.
				String newId = null;
				if (id.endsWith(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					if (logger.isInfoLoggable()) {
						logger.info("Statement id contain !selectKey.");
					}

					newId = id.substring(0, index)
							+ SelectKeyGenerator.SELECT_KEY_SUFFIX;
				} else {
					if (logger.isInfoLoggable()) {
						logger.info("Statement id not contain !selectKey.");
					}

					newId = id.substring(0, index);
				}

				// 得到原有的Statement中的 KeyGenerator对象.
				KeyGenerator keyGenerator = statement.getKeyGenerator();

				if (logger.isInfoLoggable()) {
					logger.info("Replace statement id by database info, old statement id is "
							+ id + " and new statement id is " + newId);
				}

				Field idField = ReflectionUtils.findField(
						MappedStatement.class, "id");
				idField.setAccessible(true);
				ReflectionUtils.setField(idField, statement, newId);

				if (logger.isInfoLoggable()) {
					logger.info("Reflection MappedStatement.id property success. new statement id is "
							+ newId);
				}

				// 如果在原MyBatis的 Configuration的属性mappedStatements包含新的Statement Id
				// 则先移除，再Put进去.
				if (configuration.hasStatement(newId)) {
					if (logger.isInfoLoggable()) {
						logger.info("Mapped Statements collection already contains value for "
								+ newId + ", so remove old statement.");
					}

					Field mappedStatementsField = ReflectionUtils.findField(
							Configuration.class, "mappedStatements");
					mappedStatementsField.setAccessible(true);

					Map mappedStatements = (Map) ReflectionUtils.getField(
							mappedStatementsField, configuration);
					mappedStatements.remove(newId);

				}

				if (null != keyGenerator) {
					if (logger.isInfoLoggable()) {
						logger.info("Mapped Statement ID " + newId
								+ " KeyGenerator is not null.");
					}

					if (keyGenerator instanceof SelectKeyGenerator) {
						if (logger.isInfoLoggable()) {
							logger.info("Mapped Statement ID "
									+ newId
									+ " KeyGenerator typeof is SelectKeyGenerator.");
						}

						SelectKeyGenerator selectKeyGenerator = (SelectKeyGenerator) keyGenerator;
						Field keyStatementField = ReflectionUtils.findField(
								SelectKeyGenerator.class, "keyStatement");
						keyStatementField.setAccessible(true);
						MappedStatement keyGeneratorMappedStatement = (MappedStatement) ReflectionUtils
								.getField(keyStatementField, selectKeyGenerator);
						String keyGeneratorId = keyGeneratorMappedStatement
								.getId();

						index = keyGeneratorId.lastIndexOf("_"
								+ dialect.getDatabaseName()
								+ SelectKeyGenerator.SELECT_KEY_SUFFIX);
						if (index > 0) {
							keyGeneratorId = keyGeneratorId.substring(0, index)
									+ SelectKeyGenerator.SELECT_KEY_SUFFIX;
							ReflectionUtils
									.setField(idField,
											keyGeneratorMappedStatement,
											keyGeneratorId);
						}
						String oldKeyGeneratorId = id
								+ SelectKeyGenerator.SELECT_KEY_SUFFIX;
						if (configuration.hasStatement(oldKeyGeneratorId)) {
							if (logger.isInfoLoggable()) {
								logger.info("Replace old mapping Statement ID "
										+ oldKeyGeneratorId
										+ " exists in the configuration.");
							}

							Field mappedStatementsField = ReflectionUtils
									.findField(Configuration.class,
											"mappedStatements");
							mappedStatementsField.setAccessible(true);

							Map mappedStatements = (Map) ReflectionUtils
									.getField(mappedStatementsField,
											configuration);
							mappedStatements.remove(oldKeyGeneratorId);

							if (mappedStatements.containsKey(keyGeneratorId)) {
								if (logger.isInfoLoggable()) {
									logger.info("Replace new mapping Statement ID "
											+ oldKeyGeneratorId
											+ "exists in the configuration.");
								}

								mappedStatements.remove(keyGeneratorId);
							}

							mappedStatements.put(keyGeneratorId,
									keyGeneratorMappedStatement);
						}

					}

				}

				// deal with cache.
				String tmpStatementId = statement.getId().substring(0,
						statement.getId().lastIndexOf("."));
				if (configuration.getCacheNames().contains(tmpStatementId)) {
					Cache cache = configuration.getCache(tmpStatementId);

					Field mappedStatementsFlushCacheRequiredField = ReflectionUtils
							.findField(MappedStatement.class,
									"flushCacheRequired");
					mappedStatementsFlushCacheRequiredField.setAccessible(true);
					ReflectionUtils.setField(
							mappedStatementsFlushCacheRequiredField, statement,
							false);

					Field mappedStatementsUseCacheField = ReflectionUtils
							.findField(MappedStatement.class, "useCache");
					mappedStatementsUseCacheField.setAccessible(true);
					ReflectionUtils.setField(mappedStatementsUseCacheField,
							statement, true);

					Field mappedStatementsCacheField = ReflectionUtils
							.findField(MappedStatement.class, "cache");
					mappedStatementsCacheField.setAccessible(true);
					ReflectionUtils.setField(mappedStatementsCacheField,
							statement, cache);
				}
				// 把最新修改过的Statement存入Configuration的属性mappedStatements中.
				configuration.addMappedStatement(statement);
			}
		}

		// SqlSessionFactoryProxy sqlSessionFactoryProxy = new
		// SqlSessionFactoryProxy(sqlSessionFactory);
		// sqlSessionFactoryProxy.setDialectName (databaseName);
		// return sqlSessionFactoryProxy;
		return sqlSessionFactory;
	}

	/**
	 * 得到方言支持类
	 * 
	 * @param sqlSessionFactory
	 * @return
	 */
	private IDialect getDialectSupported(SqlSessionFactory sqlSessionFactory) {
		if (logger.isInfoLoggable()) {
			logger.info("start dialect inject............................");
		}

		SqlSession sqlSession = sqlSessionFactory.openSession();
		String databaseName = null;
		try {
			databaseName = "mysql";
		} catch (Exception e) {
			logger.admin("get current databasename error:" + e);
		} finally {
			if (null != sqlSession) {
				sqlSession.close();
			}
		}

		if (logger.isInfoLoggable()) {
			logger.info("current database is " + databaseName
					+ "............................");
		}

		if (!m_dialectManager.isSupportedDialect(databaseName)) {
			logger.admin("not support  this database:" + databaseName
					+ " database dialect.");
			throw new BusinessException(ErrorCodes.UnSupportDialectException,
					"Unsupport this dialect. database type : " + databaseName
							+ ".");
		}

		return m_dialectManager.getDialectByDbName(databaseName);
	}

	private Configuration buildExtraConfiguration(int index) {
		XMLConfigBuilder xmlConfigBuilder;
		Configuration configuration = null;

		if (this.extraConfigLocation != null) {
			try {
				xmlConfigBuilder = new XMLConfigBuilder(
						this.extraConfigLocation[index].getInputStream());
				configuration = xmlConfigBuilder.parse();
			} catch (Exception ex) {
				logger.admin("Failed to parse config resource: "
						+ this.extraConfigLocation, ex);
			}
		}
		return configuration;
	}

	private void addStatementsAndTypeAlias(Configuration configuration,
			Configuration extraConfiguration) {
		if (null == extraConfiguration) {
			return;
		}
		Field typeAliesMapField = ReflectionUtils.findField(
				TypeAliasRegistry.class, "TYPE_ALIASES");
		typeAliesMapField.setAccessible(true);

		Field mapperRegistryField = ReflectionUtils.findField(
				Configuration.class, "mapperRegistry");
		mapperRegistryField.setAccessible(true);

		Field knownMappersField = ReflectionUtils.findField(
				MapperRegistry.class, "knownMappers");
		knownMappersField.setAccessible(true);

		// 获取configuration的属性
		TypeAliasRegistry typeAlias = configuration.getTypeAliasRegistry();
		HashMap<String, Class> typeAliasMap = (HashMap<String, Class>) ReflectionUtils
				.getField(typeAliesMapField, typeAlias);

		MapperRegistry mapperRegistry = (MapperRegistry) ReflectionUtils
				.getField(mapperRegistryField, configuration);
		HashSet<Class> knownMappers = (HashSet<Class>) ReflectionUtils
				.getField(knownMappersField, mapperRegistry);

		// 获取extraConfiguration的TypeAlias对象的内置alias集合和statement集合
		TypeAliasRegistry extraTypeAlias = extraConfiguration
				.getTypeAliasRegistry();
		HashMap<String, Class> extraTypeAliasMap = (HashMap<String, Class>) ReflectionUtils
				.getField(typeAliesMapField, extraTypeAlias);

		MapperRegistry extraMapperRegistry = (MapperRegistry) ReflectionUtils
				.getField(mapperRegistryField, extraConfiguration);
		HashSet<Class> extraKnownMappers = (HashSet<Class>) ReflectionUtils
				.getField(knownMappersField, extraMapperRegistry);

		Map<String, XNode> sqlFragments = configuration.getSqlFragments();

		Map<String, XNode> extraSqlFragments = extraConfiguration
				.getSqlFragments();

		for (Class clazz : extraKnownMappers) {
			if (!configuration.hasMapper(clazz)) {
				configuration.addMapper(clazz);
			}
		}

		for (String ids : extraSqlFragments.keySet()) {
			if (!sqlFragments.keySet().contains(ids)) {
				sqlFragments.put(ids, extraSqlFragments.get(ids));
			}
		}

		for (String alias : extraTypeAliasMap.keySet()) {
			if (!typeAliasMap.containsKey(alias)) {
				typeAliasMap.put(alias, extraTypeAliasMap.get(alias));
			}
		}

		for (String ids : extraConfiguration.getMappedStatementNames()) {
			if (!configuration.getMappedStatementNames().contains(ids)) {
				configuration.addMappedStatement(extraConfiguration
						.getMappedStatement(ids));
			}
		}

		for (String ids : extraConfiguration.getParameterMapNames()) {
			if (!configuration.hasParameterMap(ids)) {
				configuration.addParameterMap(extraConfiguration
						.getParameterMap(ids));
			}
		}
		for (String ids : extraConfiguration.getCacheNames()) {
			if (!configuration.hasCache(ids)) {
				configuration.addCache(extraConfiguration.getCache(ids));
			}
		}
		for (String ids : extraConfiguration.getKeyGeneratorNames()) {
			if (!configuration.hasKeyGenerator(ids)) {
				configuration.addKeyGenerator(ids,
						extraConfiguration.getKeyGenerator(ids));
			}
		}

		for (String ids : extraConfiguration.getResultMapNames()) {
			if (!configuration.hasResultMap(ids)) {
				configuration
						.addResultMap(extraConfiguration.getResultMap(ids));
			}
		}
	}

	private String getShortName(String key) {
		final String[] keyparts = key.split("\\.");
		final String shortKey = keyparts[keyparts.length - 1];
		return shortKey;
	}

}
