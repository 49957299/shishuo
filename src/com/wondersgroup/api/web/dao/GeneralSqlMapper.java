package com.wondersgroup.api.web.dao;

import java.util.HashMap;
import java.util.List;

import com.wondersgroup.api.framework.exception.BusinessException;

public interface GeneralSqlMapper {

	/**
	 * sql通用调用方法
	 * 
	 * @param sqlKey
	 *            sql唯一值
	 * @param params
	 *            sql条件参数值
	 * @return 查询结果
	 */

	public String findOne(String sqlKey) throws BusinessException;

	/**
	 * 查询所有
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<HashMap<String, Object>> find() throws BusinessException;
}
