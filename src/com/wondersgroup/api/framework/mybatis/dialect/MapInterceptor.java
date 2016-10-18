//package com.wondersgroup.api.framework.mybatis.dialect;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import org.apache.ibatis.executor.parameter.ParameterHandler;
//import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
//import org.apache.ibatis.executor.resultset.ResultSetHandler;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//
//@Intercepts(@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }))
//public class MapInterceptor implements Interceptor {
//	@Override
//	public Object intercept(Invocation invocation) throws Throwable {
//		Object target = invocation.getTarget();
//
//		if (target instanceof DefaultResultSetHandler) {
//			DefaultResultSetHandler handler = (DefaultResultSetHandler) target;
//
//			ParameterHandler pHandler = Reflect.getFieldValue(handler,
//					"parameterHandler");
//			Object paramObj = pHandler.getParameterObject();
//
//			if (paramObj instanceof MapParam) {
//				MapParam param = (MapParam) paramObj;
//
//				String keyField = param.getKeyField();
//				String valueField = param.getValueField();
//				if (valueField == null) {
//					return handleKeyResult(invocation.proceed(), keyField);
//				} else {
//					Statement statement = (Statement) invocation.getArgs()[0];
//
//					return handleResultSet(statement.getResultSet(), keyField,
//							valueField);
//				}
//			}
//		}
//
//		return invocation.proceed();
//	}
//
//	@Override
//	public Object plugin(Object target) {
//		return Plugin.wrap(target, this);
//	}
//
//	@Override
//	public void setProperties(Properties properties) {
//
//	}
//
//	private Object handleKeyResult(Object resultObj, String keyField) {
//		List<?> list = (List<?>) resultObj;
//
//		Map<Object, Object> map = new HashMap<Object, Object>();
//
//		// for (int i = 0; i < list.size(); i++) {
//		// Object obj = list.get(i);
//		//
//		// Object key = null;
//		// if (obj instanceof Map<?, ?>) {
//		// Map<?, ?> tmpMap = (Map<?, ?>) obj;
//		// key = (Object) tmpMap.get(keyField);
//		// } else {
//		// key = Reflect.getFieldValue(obj, keyField);
//		// }
//		// map.put(key, obj);
//		// }
//
//		List<Object> resultList = new ArrayList<Object>();
//		resultList.add(map);
//		return resultList;
//	}
//
//	private Object handleResultSet(ResultSet resultSet, String keyField,
//			String valueField) {
//		if (resultSet != null) {
//			// 定义用于存放Key-Value的Map
//			Map<Object, Object> map = new HashMap<Object, Object>();
//			// handleResultSets的结果一定是一个List，当我们的对应的Mapper接口定义的是返回一个单一的元素，并且handleResultSets返回的列表
//			// 的size为1时，Mybatis会取返回的第一个元素作为对应Mapper接口方法的返回值。
//			List<Object> resultList = new ArrayList<Object>();
//			try {
//				// 把每一行对应的Key和Value存放到Map中
//				while (resultSet.next()) {
//					Object key = resultSet.getObject(keyField);
//					Object value = resultSet.getObject(valueField);
//					map.put(key, value);
//				}
//			} catch (SQLException e) {
//			} finally {
//				closeResultSet(resultSet);
//			}
//			// 把封装好的Map存放到List中并进行返回
//			resultList.add(map);
//			return resultList;
//		}
//		return null;
//	}
//
//	/**
//	 * 关闭ResultSet
//	 * 
//	 * @param resultSet
//	 *            需要关闭的ResultSet
//	 */
//	private void closeResultSet(ResultSet resultSet) {
//		try {
//			if (resultSet != null) {
//				resultSet.close();
//			}
//		} catch (SQLException e) {
//
//		}
//	}
//}
