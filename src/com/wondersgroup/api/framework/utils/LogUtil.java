package com.wondersgroup.api.framework.utils;

import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * 日志工具
 */
public class LogUtil {
	private static Logger logger;

	public LogUtil(Logger logger) {
		this.logger = logger;
	}

	/**
	 * 记录error级别的日志
	 * 
	 * @param logInfo
	 *            日志信息
	 */
	public void errorLog(String logInfo) {
		logger.error(logInfo);
	}

	/**
	 * 记录error级别的日志
	 * 
	 * @param logInfo
	 *            日志信息
	 * @param t
	 *            Throwable
	 */
	public void errorLog(String logInfo, Throwable t) {
		logger.error(logInfo, t);
	}

	/**
	 * 记录debug级别的日志
	 * 
	 * @param logInfo
	 *            日志信息
	 */
	public void debugLog(String logInfo) {
		if (logger.isDebugEnabled()) {
			logger.debug(logInfo);
		}
	}

	/**
	 * 记录debug级别的日志
	 * 
	 * @param logInfo
	 *            日志信息
	 * @param t
	 *            Throwable
	 */
	public void debugLog(String logInfo, Throwable t) {
		if (logger.isDebugEnabled()) {
			logger.debug(logInfo, t);
		}
	}

	/**
	 * 记录info级别的日志
	 * 
	 * @param logInfo
	 *            日志信息
	 */
	public void infoLog(String logInfo) {
		if (logger.isInfoEnabled()) {
			logger.info(logInfo);
		}
	}

	/**
	 * 记录info级别的日志
	 * 
	 * @param logInfo
	 *            日志信息
	 * @param t
	 *            Throwable
	 */
	public void infoLog(String logInfo, Throwable t) {
		if (logger.isInfoEnabled()) {
			logger.info(logInfo, t);
		}
	}

	/**
	 * 记录错误信息到表
	 * 
	 * @param logInfo
	 *            日志信息
	 */
	public static void insertLogDB(String userid, String msg) {
		MDC.put("userId", userid);
		logger.error(msg);
		MDC.remove(userid);
	}

}
