package com.wondersgroup.api.web.service.impl;

import java.sql.Clob;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.api.framework.enums.Operator;
import com.wondersgroup.api.framework.enums.Order;
import com.wondersgroup.api.framework.enums.SqlEnums;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.mybatis.SqlSessionTemplateProxy;
import com.wondersgroup.api.framework.page.Page;
import com.wondersgroup.api.web.bean.Parameter;
import com.wondersgroup.api.web.bean.Request;
import com.wondersgroup.api.web.dao.EngineMapper;
import com.wondersgroup.api.web.service.EngineService;

@Service
public class EngineServiceImpl implements EngineService {

	@Autowired
	private SqlSessionTemplateProxy sqlSessionTemplateProxy;

	@Override
	public PageImpl find(Request req, String object) throws BusinessException {
		String tableName = findTable(object);
		int count = count(req, object);
		return find(tableName, null, req, SqlEnums.SELECT, count);
	}

	private PageImpl find(String tableName, String pk, Request request, SqlEnums enums, int total) throws BusinessException {
		String sql = enums.getSql();
		List<String> coloums = request.getColoum();
		HashMap<String, Object> data = request.getData();
		List<Parameter> where = request.getWhere();
		Order order = request.getOrder();
		Page page = request.getPage();
		sql = select(sql, tableName, listToString(coloums));
		HashMap<String, Object> params = whereData(where);
		if (where != null && !where.isEmpty()) {
			sql += where(where);
			params.putAll(setWhere(where));
		}
		if (order != null) {
			sql += " order by " + order.getName() + " " + order.getOrder();
		}
		EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
		params.put("sql", sql);
		params.put("page", page);
		List<HashMap<String, Object>> rs = engineMapper.find(params);
		return new PageImpl(rs, null, total);

	}

	private HashMap<String, Object> whereData(List<Parameter> where) {
		HashMap<String, Object> rs = new HashMap<String, Object>();
		if (where == null || where.isEmpty()) {
			return rs;
		}
		for (Parameter parameter : where) {
			rs.put(parameter.getName(), parameter.getValue());
		}
		return rs;
	}

	private String set(HashMap<String, Object> param) {
		if (param == null || param.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Set keys = param.entrySet();
		int i = 1;
		for (Iterator it = keys.iterator(); it.hasNext();) {
			Map.Entry entry = (Entry) it.next();
			if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
				sb.append(entry.getKey());
				sb.append(" = #{" + entry.getKey() + "," + jdbcType(entry.getKey()) + "}");
				sb.append(",");
				i++;
			}
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	private String jdbcType(Object obj) {
		if (obj instanceof String) {
			return "jdbcType=VARCHAR";
		} else if (obj instanceof Integer) {
			return "jdbcType=INTEGER";
		} else if (obj instanceof Long) {
			return "jdbcType=BIGINT";
		} else if (obj instanceof Double) {
			return "jdbcType=DOUBLE";
		} else if (obj instanceof Float) {
			return "jdbcType=FLOAT";
		} else if (obj instanceof Date) {
			return "jdbcType=DATE";
		} else if (obj instanceof Byte) {
			return "jdbcType=TINYINT";
		} else if (obj instanceof Short) {
			return "jdbcType=SMALLINT";
		} else if (obj instanceof Time) {
			return "jdbcType=TIME";
		} else if (obj instanceof Clob) {
			return "jdbcType=CLOB";
		} else {
			return "jdbcType=VARCHAR";
		}
	}

	private String listToString(List<String> coloums) {
		if (coloums == null || coloums.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String value : coloums) {
			sb.append(value);
			sb.append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	private String dataForValues(HashMap<String, Object> values) {
		if (values == null || values.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Set keys = values.entrySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			Map.Entry entry = (Entry) it.next();
			sb.append(" #{" + entry.getKey().toString() + "," + jdbcType(entry.getKey()) + "}");
			sb.append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	private String dataForKey(HashMap<String, Object> values) {
		if (values == null || values.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Set keys = values.entrySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			Map.Entry entry = (Entry) it.next();
			sb.append(entry.getKey());
			sb.append(",");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	private HashMap<String, Object> setWhere(List<Parameter> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			return null;
		}
		HashMap<String, Object> rs = new HashMap<String, Object>();
		for (Parameter parameter : parameters) {
			rs.put(parameter.getName(), parameter.getValue());
		}
		return rs;
	}

	private String where(List<Parameter> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" where 1=1");
		for (Parameter parameter : parameters) {
			sb.append(" and " + parameter.getName());
			if (Operator.EQ == parameter.getOp()) {
				sb.append(" = ");
				sb.append("#{" + parameter.getName() + "," + jdbcType(parameter.getName()) + "}");
			} else if (Operator.GT == parameter.getOp()) {
				sb.append(" > ");
				sb.append("#{" + parameter.getName() + "," + jdbcType(parameter.getName()) + "}");
			} else if (Operator.GTE == parameter.getOp()) {
				sb.append(" >= ");
				sb.append("#{" + parameter.getName() + "," + jdbcType(parameter.getName()) + "}");
			} else if (Operator.LIKE == parameter.getOp()) {
				sb.append(" like ");
				sb.append("#{'%'" + parameter.getName() + "'%'" + "," + jdbcType(parameter.getName()) + "}");
			} else if (Operator.LT == parameter.getOp()) {
				sb.append(" < ");
				sb.append("#{" + parameter.getName() + "," + jdbcType(parameter.getName()) + "}");
			} else if (Operator.LTE == parameter.getOp()) {
				sb.append(" <= ");
				sb.append("#{" + parameter.getName() + "," + jdbcType(parameter.getName()) + "}");
			} else if (Operator.NE == parameter.getOp()) {
				sb.append(" != ");
				sb.append("#{" + parameter.getName() + "," + jdbcType(parameter.getName()) + "}");
			} else if (Operator.IN == parameter.getOp()) {
				sb.append(" in (");
				sb.append(parameter.getValue());
				sb.append(" ) ");
			}

		}
		return sb.toString();
	}

	private String insert(String sql, String object, String coloum, String values) throws BusinessException {
		if (StringUtils.isBlank(sql)) {
			throw new BusinessException("sql为空");
		}
		if (StringUtils.isNotBlank(object)) {
			sql = sql.replace("{0}", object);
		}
		if (StringUtils.isNotBlank(coloum)) {
			sql = sql.replace("{1}", coloum);
		}
		if (StringUtils.isNotBlank(values)) {
			sql = sql.replace("{2}", values);
		}
		return sql;
	}

	private String update(String sql, String object, String set) throws BusinessException {
		if (StringUtils.isBlank(sql)) {
			throw new BusinessException("sql为空");
		}
		if (StringUtils.isNotBlank(object)) {
			sql = sql.replace("{0}", object);
		}
		if (StringUtils.isNotBlank(set)) {
			sql = sql.replace("{1}", set);
		}
		return sql;
	}

	private String del(String sql, String object) throws BusinessException {
		if (StringUtils.isBlank(sql)) {
			throw new BusinessException("sql为空");
		}
		if (StringUtils.isNotBlank(object)) {
			sql = sql.replace("{0}", object);
		}
		return sql;
	}

	private String select(String sql, String object, String coloums) throws BusinessException {
		if (StringUtils.isBlank(sql)) {
			throw new BusinessException("sql为空");
		}
		if (StringUtils.isNotBlank(object)) {
			sql = sql.replace("{0}", object);
		}
		if (StringUtils.isNotBlank(coloums)) {
			sql = sql.replace("{1}", coloums);
		} else {
			sql = sql.replace("{1}", "*");
		}
		return sql;
	}

	private String count(String sql, String object) throws BusinessException {
		if (StringUtils.isBlank(sql)) {
			throw new BusinessException("sql为空");
		}
		if (StringUtils.isNotBlank(object)) {
			sql = sql.replace("{0}", object);
		}
		return sql;
	}

	@Override
	public int count(Request req, String object) throws BusinessException {
		String tableName = findTable(object);
		String sql = SqlEnums.COUNT.getSql();
		List<Parameter> where = req.getWhere();
		sql = count(sql, tableName);
		EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
		if (where != null && !where.isEmpty()) {
			sql += where(where);

		}
		HashMap<String, Object> params = whereData(where);
		params.put("sql", sql);
		Integer rs = (Integer) engineMapper.count(params);
		if (rs == null)
			return 0;
		return rs;
	}

	@Transactional
	@Override
	public int save(Request req, String object) throws BusinessException {
		try {
			String tableName = findTable(object);
			String sql = SqlEnums.INSERT.getSql();
			HashMap<String, Object> data = req.getData();

			if (data.get("id") == null) {
				data.put("id", UUID.randomUUID().toString().replace("-", ""));
			}
			sql = insert(sql, tableName, dataForKey(data), dataForValues(data));

			EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
			data.put("sql", sql);
			int rs = engineMapper.save(data);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Transactional
	@Override
	public int update(Request req, String object, String ids) throws BusinessException {
		try {
			String tableName = findTable(object);
			HashMap<String, Object> data = req.getData();
			String sql = update(SqlEnums.UPDATE.getSql(), tableName, set(data));
			sql = sql + " where id in(" + ids + ")";
			EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
			data.put("sql", sql);
			int rs = engineMapper.update(data);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Transactional
	@Override
	public int delete(String object, String id) throws BusinessException {
		try {
			String tableName = findTable(object);
			HashMap<String, Object> data = new HashMap<String, Object>();
			String sql = del(SqlEnums.DELETE.getSql(), tableName);
			sql += " where id in(" + id + ")";
			EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
			data.put("sql", sql);
			int rs = engineMapper.delete(data);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public int deletes(String ids, String object) throws BusinessException {
		try {
			String tableName = findTable(object);
			if (StringUtils.isBlank(ids)) {
				throw new BusinessException("删除id不存在");
			}
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("id", ids);
			String sql = del(SqlEnums.DELETE.getSql(), tableName);
			sql += " where 1=2 and  or id in (#{id,jdbcType=VARCHAR})";

			EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
			data.put("sql", sql);
			int rs = engineMapper.delete(data);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public Object get(Request req, String id, String object) throws BusinessException {
		String tableName = findTable(object);
		String sql = SqlEnums.SELECT.getSql();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		sql = sql.replace("{0}", tableName);
		sql = sql.replace("{1}", "*");
		sql += " where id = #{id,jdbcType=VARCHAR} ";
		EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
		data.put("sql", sql);
		return engineMapper.findOne(data);

	}

	public static void main(String[] args) {
		Request request = new Request();
		/** 查询 */
		// List<Parameter> parameters = new ArrayList<Parameter>();
		// Parameter parameter = new Parameter();
		// parameter.setName("id");
		// parameter.setOp(Operator.EQ);
		// parameter.setValue("1");
		// parameters.add(parameter);
		// parameter = new Parameter();
		// parameter.setName("add1");
		// parameter.setOp(Operator.LIKE);
		// parameter.setValue("1");
		// parameters.add(parameter);
		// request.setWhere(parameters);
		// Order order = new Order();
		// order.setName("id");
		// order.setOrder(Order.ASC);
		// request.setOrder(order);
		// Page page = new Page();
		// page.setPageNumber(1);
		// page.setPageSize(20);
		// request.setPage(page);

		// /** 插入 */
		// HashMap<String, Object> data = new HashMap<String, Object>();
		// data.put("name", "1");
		// data.put("add1", "测试新增");
		// data.put("adm", "admin");
		// data.put("desi", "");
		// request.setData(data);

		// /** 删除 */
		// List<Parameter> parameters = new ArrayList<Parameter>();
		// Parameter parameter = new Parameter();
		// parameter.setName("id");
		// parameter.setOp(Operator.EQ);
		// parameter.setValue("3");
		// parameters.add(parameter);
		// parameter = new Parameter();
		// parameter.setName("address");
		// parameter.setOp(Operator.EQ);
		// parameter.setValue("1");
		// parameters.add(parameter);
		// request.setWhere(parameters);

		/** 更新 */
		// HashMap<String, Object> data = new HashMap<String, Object>();
		// data.put("ID", "4");
		// data.put("ADDRESS", "2");
		// data.put("ADMINISTRATOR", "测试新增1");
		// data.put("DESIGN", "admin");
		// data.put("IP", "");
		// data.put("PEOPLE", "");
		// data.put("PHONE", "");
		// data.put("PHONE", "");
		// data.put("PORT", "");
		// data.put("REMARK", "");
		// data.put("TIME", "");
		// data.put("PID", 1);
		// request.setData(data);

		System.out.println(JSON.toJSONString(request));

	}

	public String findTable(String object) throws BusinessException {
		EngineMapper engineMapper = sqlSessionTemplateProxy.getMapper(EngineMapper.class);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sql", "SELECT TABLE_NAME FROM SYS_ENGINE_TABLE where OBJECT_NAME = #{OBJECT_NAME}");
		params.put("OBJECT_NAME", object);
		HashMap<String, Object> rs = engineMapper.findOne(params);

		if (rs == null || rs.isEmpty()) {
			return object;
		}
		return (String) rs.get("TABLE_NAME");
	}

	public void setSqlSessionTemplateProxy(SqlSessionTemplateProxy sqlSessionTemplateProxy) {
		this.sqlSessionTemplateProxy = sqlSessionTemplateProxy;
	}

}
