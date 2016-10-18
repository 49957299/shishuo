package com.wondersgroup.api.web.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.mybatis.SqlSessionTemplateProxy;
import com.wondersgroup.api.web.dao.GeneralSqlMapper;
import com.wondersgroup.api.web.service.GeneralSqlService;

@Service
public class GeneralSqlServiceImpl implements GeneralSqlService {

	@Autowired
	private SqlSessionTemplateProxy sqlSessionTemplateProxy;

	@Override
	public List<HashMap<String, Object>> find() throws BusinessException {
		GeneralSqlMapper generalSqlMapper = sqlSessionTemplateProxy
				.getMapper(GeneralSqlMapper.class);
		return generalSqlMapper.find();
	}

	@Override
	public String findOne(String sqlKey) throws BusinessException {
		GeneralSqlMapper generalSqlMapper = sqlSessionTemplateProxy
				.getMapper(GeneralSqlMapper.class);
		return generalSqlMapper.findOne(sqlKey);
	}

}
