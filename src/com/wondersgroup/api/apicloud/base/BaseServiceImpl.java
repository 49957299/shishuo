package com.wondersgroup.api.apicloud.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wondersgroup.api.framework.mybatis.SqlSessionTemplateProxy;
import com.wondersgroup.api.framework.utils.PropertiesHandle;

@Service
public abstract class BaseServiceImpl implements BaseService {

	@Autowired
	private SqlSessionTemplateProxy sqlSessionTemplateProxy;

	private static final String API_URL = "api.url";
	private String appId;
	private String appKey;
	private String authorization;

	public String getAppId() {
		return appId;
	}

	public BaseServiceImpl(String appId, String appKey) {
		super();
		this.appId = appId;
		this.appKey = appKey;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	protected String getApiCoundUrl() {
		return PropertiesHandle.getInstance().getString(API_URL);
	}

	public SqlSessionTemplateProxy getSqlSessionTemplateProxy() {
		return sqlSessionTemplateProxy;
	}

	public void setSqlSessionTemplateProxy(
			SqlSessionTemplateProxy sqlSessionTemplateProxy) {
		this.sqlSessionTemplateProxy = sqlSessionTemplateProxy;
	}

}
