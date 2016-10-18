package com.wondersgroup.api.apicloud.base;

import com.wondersgroup.api.apicloud.v1.service.impl.ApiCloudServiceImpl;
import com.wondersgroup.api.apicloud.v1.service.impl.ApiServiceImpl;
import com.wondersgroup.api.framework.utils.PropertiesHandle;

public class BaseController {

	private static final String API_TYPE = "api.type";
	private static final String APICOUND = "apicloud";

	protected BaseServiceImpl createService(String appId, String appKey) {
		String type = PropertiesHandle.getInstance().getString(API_TYPE);
		if (APICOUND.equalsIgnoreCase(type)) {
			return new ApiCloudServiceImpl(appId, appKey);
		} else {
			return new ApiServiceImpl(appId, appKey);
		}

	}
}
