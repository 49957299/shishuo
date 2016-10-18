package com.wondersgroup.api.apicloud.v1.dao;

import java.util.HashMap;
import java.util.List;

import com.wondersgroup.api.framework.exception.BusinessException;

/**
 * 基本dao类
 * 
 * @author Administrator
 *
 */
public interface ApiDao {

	public String get(String object, String id) throws BusinessException;

	public String delete(String object, String id) throws BusinessException;

	public String save(String object, String data) throws BusinessException;

	public String update(String object, String id, String data) throws BusinessException;

	public String query(String object, String data) throws BusinessException;

	public String login(String data) throws BusinessException;

	public String logout(String authorization) throws BusinessException;

	public String count(String object, String filter) throws BusinessException;

	public String exists(String object, String id) throws BusinessException;

	public String findOne(String object, String id) throws BusinessException;

	public String verify(String object, String id) throws BusinessException;

	public String reset(String object, String id) throws BusinessException;

	public String sendvercode(String object, String id) throws BusinessException;

	public String checkvercode(String object, String id) throws BusinessException;

	public String resetByMobile(String object, String id) throws BusinessException;

	public String batch(String object, String id) throws BusinessException;

	public Object text(String object, String id, String colum) throws BusinessException;

	public void createClass(String classes) throws BusinessException;

	public void addColumn(String classes, String column, String dataType) throws BusinessException;

	public void dropColumn(String classes, String column) throws BusinessException;

	public void alterColumnType(String classes, String column, String dataType) throws BusinessException;

	public void renameColumn(String classes, String column, String newColumn, String dataType) throws BusinessException;

	public void dropClass(String classes) throws BusinessException;

	public String queryClass() throws BusinessException;

	public int batch(List<HashMap<String, Object>> data, HashMap<String, Object> user, String object) throws Exception;

}