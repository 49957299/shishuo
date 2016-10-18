package com.wondersgroup.api.framework.mybatis.dialect;

import java.util.HashMap;
import java.util.Map;

import com.wondersgroup.api.framework.logger.Logger;

/**
 *
 */
public abstract class ADialectManager {
	private static final Logger logger = new Logger(
			ADialectManager.class.getName());

	protected Map<String, IDialect> DIALECT_MAP = new HashMap<String, IDialect>();

	/**
	 * 根据当前运行的数据库信息获取方言实现类实例.
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public IDialect getDialectByDbName(String dataBaseName) {
		return DIALECT_MAP.get(dataBaseName);
	}

	/**
	 * 根据当前运行的数据库信息判断系统是否已支持.
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public boolean isSupportedDialect(String dataBaseName) {
		return DIALECT_MAP.containsKey(dataBaseName);
	}

	public abstract void setSupportDialects();
}
