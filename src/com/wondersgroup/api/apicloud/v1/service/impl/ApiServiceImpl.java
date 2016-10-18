package com.wondersgroup.api.apicloud.v1.service.impl;

import java.util.HashMap;
import java.util.List;

import com.wondersgroup.api.apicloud.base.BaseServiceImpl;
import com.wondersgroup.api.apicloud.v1.dao.impl.ApiDaoImpl;
import com.wondersgroup.api.apicloud.v1.service.ApiService;
import com.wondersgroup.api.framework.exception.BusinessException;

public class ApiServiceImpl extends BaseServiceImpl implements ApiService {

	public ApiServiceImpl(String appId, String appKey) {
		super(appId, appKey);
	}

	@Override
	public String get(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.get(object, id);
	}

	@Override
	public String delete(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.delete(object, id);
	}

	@Override
	public String save(String object, String data) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.save(object, data);
	}

	@Override
	public String update(String object, String id, String data) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.update(object, id, data);
	}

	@Override
	public String query(String object, String data) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.query(object, data);
	}

	@Override
	public String login(String username, String password) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.login(username, password);
	}

	@Override
	public String logout(String token) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.logout(token);
	}

	@Override
	public String count(String object, String filter) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.count(object, filter);
	}

	@Override
	public String exists(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.exists(object, id);
	}

	@Override
	public String findOne(String object, String data) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.findOne(object, data);
	}

	@Override
	public String verify(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.verify(object, id);
	}

	@Override
	public String reset(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.reset(object, id);
	}

	public Object text(String object, String id, String colum) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.text(object, id, colum);
	}

	@Override
	public String sendvercode(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.sendvercode(object, id);
	}

	@Override
	public String checkvercode(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.checkvercode(object, id);
	}

	@Override
	public String resetByMobile(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.resetByMobile(object, id);
	}

	@Override
	public String batch(String object, String id) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.batch(object, id);
	}

	public void createClass(String classes) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		dao.createClass(classes);
	}

	public void addColumn(String classes, String column, String dataType) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		dao.addColumn(classes, column, dataType);
	}

	public void dropColumn(String classes, String column) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		dao.dropColumn(classes, column);
	}

	public void alterColumnType(String classes, String column, String dataType) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		dao.alterColumnType(classes, column, dataType);
	}

	public void renameColumn(String classes, String column, String newColumn, String dataType) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		dao.renameColumn(classes, column, newColumn, dataType);
	}

	public void dropClass(String classes) throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		dao.dropClass(classes);
	}

	public String queryClass() throws BusinessException {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.queryClass();
	}

	@Override
	public int batch(List<HashMap<String, Object>> data, HashMap<String, Object> user, String object) throws Exception {
		ApiDaoImpl dao = new ApiDaoImpl();
		return dao.batch(data, user, object);
	}

}
