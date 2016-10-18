package com.wondersgroup.api.framework.aop.impl;

import org.aspectj.lang.JoinPoint;
import org.slf4j.LoggerFactory;

import com.wondersgroup.api.framework.aop.LogAopService;
import com.wondersgroup.api.framework.utils.LogUtil;

public class LogAopServiceImpl implements LogAopService {

	private static LogUtil log = new LogUtil(LoggerFactory.getLogger("日志记录服务"));

	public void enterMethodLog(JoinPoint point) {
		StringBuilder logString = new StringBuilder();
		logString.append(point.getTarget().getClass());
		logString.append('.');
		logString.append(point.getSignature().getName());
		logString.append(" () ");
		Object[] params = point.getArgs();
		if (null != params) {
			logString.append("传入参数：\n");
			for (Object param : params) {
				logString.append(param);
				logString.append(", ");
			}
			if (logString.lastIndexOf(",") >= 0) {
				logString.deleteCharAt(logString.lastIndexOf(","));
			}
		}
		log.debugLog("进入方法：" + logString.toString());
	}

	public void exitMethodLog(JoinPoint point, Object returnObj) {
		StringBuilder logString = new StringBuilder();
		logString.append(point.getTarget().getClass());
		logString.append('.');
		logString.append(point.getSignature().getName());
		logString.append(" () ");
		if (null != returnObj) {
			logString.append("返回值：" + returnObj);
		}
		log.debugLog("离开方法：" + logString.toString());
	}

	public void exceptionLog(JoinPoint point, Exception exception) {
		StringBuilder paramString = new StringBuilder();
		Object[] params = point.getArgs();
		if (null != params) {
			paramString.append("传入参数：");
			for (Object param : params) {
				paramString.append(param);
				paramString.append(", ");
			}
			if (paramString.lastIndexOf(",") >= 0) {
				paramString.deleteCharAt(paramString.lastIndexOf(","));
			}
		}
		log.errorLog("方法" + point.getTarget().getClass() + '.'
				+ point.getSignature().getName() + " () 执行时发生异常，"
				+ paramString.toString() + "异常信息：" + exception.getMessage()
				+ "\n堆栈信息：" + exception.getStackTrace());
	}
}
