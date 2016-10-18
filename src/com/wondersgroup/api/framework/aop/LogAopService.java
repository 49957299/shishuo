package com.wondersgroup.api.framework.aop;

import org.aspectj.lang.JoinPoint;

/**
 * 日志输出aop服务接口
 */
public interface LogAopService {
	/**
	 * 进入方法日志
	 * 
	 * @param point
	 *            JoinPoint 切入点
	 */
	void enterMethodLog(JoinPoint point);

	/**
	 * 离开方法日志
	 * 
	 * @param point
	 *            JoinPoint 切入点
	 * @param returnObj
	 *            Object 返回值
	 */
	void exitMethodLog(JoinPoint point, Object returnObj);

	/**
	 * 异常日志
	 * 
	 * @param point
	 *            JoinPoint 切入点
	 * @param exception
	 *            Exception 异常
	 */
	void exceptionLog(JoinPoint point, Exception exception);

}
