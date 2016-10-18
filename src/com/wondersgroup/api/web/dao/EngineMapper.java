package com.wondersgroup.api.web.dao;

import java.util.HashMap;
import java.util.List;

import com.wondersgroup.api.framework.page.Page;

public interface EngineMapper {

	/**
	 * 执行insert语句
	 * 
	 * @param sql
	 * @param value
	 * @return
	 */
	public int save(HashMap<String, Object> params);

	/**
	 * 执行update语句
	 * 
	 * @param sql
	 * @param value
	 * @return
	 */
	public int update(HashMap<String, Object> params);

	/**
	 * 执行delete语句
	 * 
	 * @param sql
	 * @param value
	 * @return
	 */
	public int delete(HashMap<String, Object> params);

	/**
	 * 查询数据
	 * 
	 * @param sql
	 * @param value
	 * @return
	 */
	public List<HashMap<String, Object>> find(HashMap<String, Object> params);

	/**
	 * 查询数据
	 * 
	 * @param sql
	 * @param value
	 * @return
	 */
	public HashMap<String, Object> findOne(HashMap<String, Object> params);

	/**
	 * 执行count语句
	 * 
	 * @param sql
	 * @param value
	 * @return
	 */
	public int count(HashMap<String, Object> params);

}
