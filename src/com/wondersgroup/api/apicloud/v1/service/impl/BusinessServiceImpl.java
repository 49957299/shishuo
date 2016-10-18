package com.wondersgroup.api.apicloud.v1.service.impl;

import com.wondersgroup.api.apicloud.v1.dao.BusinessDao;
import com.wondersgroup.api.apicloud.v1.dao.impl.BusinessDaoImpl;
import com.wondersgroup.api.apicloud.v1.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {

	@Override
	public String saveDDxx(String businessData) {
		// TODO Auto-generated method stub
		BusinessDao dao = new BusinessDaoImpl();
		return dao.saveDDxx(businessData);
	}


}
