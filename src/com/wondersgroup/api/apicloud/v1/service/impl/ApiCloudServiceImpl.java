package com.wondersgroup.api.apicloud.v1.service.impl;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.api.apicloud.ApiCloudContent;
import com.wondersgroup.api.apicloud.base.BaseServiceImpl;
import com.wondersgroup.api.apicloud.v1.service.ApiCloudService;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.utils.Encodes;
import com.wondersgroup.api.framework.utils.HttpClientUtils;

public class ApiCloudServiceImpl extends BaseServiceImpl implements
		ApiCloudService {

	public ApiCloudServiceImpl(String appId, String appKey) {
		super(appId, appKey);
	}

	@Override
	public String get(String object, String id) {
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/" + object + "/" + id;
		return HttpClientUtils.doGet(url, header);
	}

	@Override
	public String delete(String object, String id) {
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/" + object + "/" + id;
		return HttpClientUtils.doDelete(url, header);
	}

	@Override
	public String save(String object, String data) {
		HashMap param = (HashMap) JSON.parseArray(data, HashMap.class);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/" + object;
		return HttpClientUtils.doPost(url, param, header);
	}

	@Override
	public String update(String object, String id, String data) {
		HashMap param = (HashMap) JSON.parseArray(data, HashMap.class);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/" + object + "/" + id;
		return HttpClientUtils.doPut(url, param, header);
	}

	@Override
	public String query(String object, String data) {
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/" + object + "/count?filter="
				+ Encodes.urlEncode(data);
		return HttpClientUtils.doGet(url, header);
	}

	@Override
	public String login(String username, String password) {
		HashMap param = new HashMap();
		param.put("username", username);
		param.put("password", password);
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/user/login";
		return HttpClientUtils.doPost(url, param, header);
	}

	@Override
	public String logout(String token) {
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		header.put(ApiCloudContent.AUTHORIZATION, token);
		String url = getApiCoundUrl() + "/User/logout";
		return HttpClientUtils.doPost(url, null, header);
	}

	@Override
	public String count(String object, String data) {
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/" + object + "/count?filter="
				+ Encodes.urlEncode(data);
		return HttpClientUtils.doGet(url, header);
	}

	@Override
	public String exists(String object, String id) {
		HashMap<String, String> header = new HashMap<String, String>();
		header.put(ApiCloudContent.API_CLOUD_APPID, getAppId());
		header.put(ApiCloudContent.API_CLOUD_APPKEY, getAppKey());
		String url = getApiCoundUrl() + "/" + object + "/" + id + "/exists";
		return HttpClientUtils.doGet(url, header);
	}

	@Override
	public String findOne(String object, String id) {

		return null;
	}

	@Override
	public String verify(String object, String id) {

		return null;
	}

	@Override
	public String reset(String object, String id) {

		return null;
	}

	@Override
	public String sendvercode(String object, String id) {

		return null;
	}

	@Override
	public String checkvercode(String object, String id) {

		return null;
	}

	@Override
	public String resetByMobile(String object, String id) {

		return null;
	}

	@Override
	public String batch(String object, String id) {

		return null;
	}

	@Override
	public Object text(String object, String id, String colum) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int batch(List<HashMap<String, Object>> data, HashMap<String, Object> user, String object) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
