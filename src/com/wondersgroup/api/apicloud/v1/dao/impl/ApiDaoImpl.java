package com.wondersgroup.api.apicloud.v1.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.api.apicloud.v1.dao.ApiDao;
import com.wondersgroup.api.framework.db.OperationCoreImpl;
import com.wondersgroup.api.framework.enums.Operator;
import com.wondersgroup.api.framework.enums.SqlEnums;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.utils.Base64Utils;
import com.wondersgroup.api.framework.utils.MD5;

public class ApiDaoImpl implements ApiDao {

	public String get(String object, String id) throws BusinessException {
		String sql = SqlEnums.SELECT.getSql();
		sql = sql.replace("{1}", "*");
		sql = sql.replace("{0}", object);
		sql += " where id = ? ";
		try {
			HashMap<String, Object> rs = OperationCoreImpl.createFactory().executeQueryByOne(sql, id);
			return JSON.toJSONString(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public String delete(String object, String id) throws BusinessException {
		String sql = SqlEnums.DELETE.getSql();
		sql = sql.replace("{0}", object);
		sql += " where id = ?";
		try {
			int rs = OperationCoreImpl.createFactory().executeUpdate(sql, id);
			return JSON.toJSONString(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public String save(String object, String data) throws BusinessException {
		OperationCoreImpl impl = null;
		try {
			impl = new OperationCoreImpl();
			impl.setAutoCommit(false);
			if (StringUtils.isBlank(data)) {
				throw new BusinessException("没有传递数据");
			}
			String sql = SqlEnums.INSERT.getSql();
			sql = sql.replace("{0}", object);
			JSONObject ds = JSON.parseObject(data);
			ds.put("createAd", new Timestamp(System.currentTimeMillis()));
			ds.put("updateAd", new Timestamp(System.currentTimeMillis()));
			StringBuilder column = new StringBuilder();
			StringBuilder values = new StringBuilder();
			List<Object> param = new ArrayList<Object>();
			for (Iterator<Map.Entry<String, Object>> it = ds.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = it.next();
				column.append(entry.getKey());
				column.append(",");
				values.append(" ?");
				values.append(",");
				if (entry.getKey().equalsIgnoreCase("password")) {
					param.add(MD5.md5(ds.getString("password")));
				} else {
					param.add(entry.getValue());
				}
			}

			String id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			ds.put("id", id);
			column.append("id");
			values.append("?");
			param.add(id);
			sql = sql.replace("{1}", column.toString());
			sql = sql.replace("{2}", values.toString());
			int rs = impl.executeUpdateNoTransaction(sql, param.toArray());
			if (rs > 0) {
				if ("user".equalsIgnoreCase(object)) {
					int insertInt = insertToken(impl, id);
					return selectToken(insertInt, impl, id);
				} else {
					return get(object, id);
				}
			}
			impl.commit();
			return JSON.toJSONString(rs);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				impl.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BusinessException(e);
		}

	}

	private int insertToken(OperationCoreImpl impl, String userId) {
		String tokenSql = "insert into token(id,ttl,userid,createAt,updateAt) values(?,?,?,?,?)";
		String tokenId = Base64Utils.getEncryptString(UUID.randomUUID().toString().replace("-", ""));
		Timestamp createAt = new Timestamp(System.currentTimeMillis());
		Timestamp updateAt = new Timestamp(System.currentTimeMillis());
		Object[] value = new Object[] { tokenId, 1209600, userId, createAt, updateAt };
		return impl.executeUpdateNoTransaction(tokenSql, value);
	}

	private String selectToken(int token, OperationCoreImpl impl, String userId) {
		try {
			HashMap<String, Object> rs = null;
			if (token > 0) {
				rs = impl.executeQueryByOne("select * from token t,user u where u.id = t.userid and u.id = ? ", userId);
				if (StringUtils.isNotBlank(rs.get("password").toString())) {
					rs.remove("password");
				}
			}
			return JSON.toJSONString(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public String update(String object, String id, String data) throws BusinessException {
		OperationCoreImpl impl = null;
		try {
			impl = OperationCoreImpl.createFactory();
			impl.setAutoCommit(false);
			if (StringUtils.isBlank(data)) {
				throw new BusinessException("没有传递数据");
			}

			String sql = SqlEnums.UPDATE.getSql();
			sql = sql.replace("{0}", object);
			JSONObject ds = JSON.parseObject(data);

			Set<Map.Entry<String, Object>> dss = ds.entrySet();
			StringBuilder column = new StringBuilder();
			List<Object> param = new ArrayList<Object>();
			for (Iterator<Map.Entry<String, Object>> it = dss.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = it.next();
				column.append(entry.getKey());
				column.append(" = ");
				column.append(" ?");
				column.append(",");
				param.add(entry.getValue());
			}
			column.delete(column.lastIndexOf(","), column.length());

			param.add(id);
			sql = sql.replace("{1}", column.toString());
			sql += " where id = ?";

			int rs = impl.executeUpdateNoTransaction(sql, param.toArray());
			if (rs > 0) {
				return get(object, id);
			}
			impl.commit();
			return JSON.toJSONString(rs);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				impl.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BusinessException(e);
		}
	}

	public String query(String object, String data) throws BusinessException {
		if (StringUtils.isBlank(data)) {
			throw new BusinessException("没有传递数据");
		}
		OperationCoreImpl impl = null;
		try {
			impl = OperationCoreImpl.createFactory();
			String sql = SqlEnums.SELECT.getSql();
			List<Object> param = new ArrayList<Object>();
			sql = sql.replace("{0}", object);
			JSONObject ds = JSON.parseObject(data);
			JSONObject fields = ds.getJSONObject("fields");
			JSONObject where = ds.getJSONObject("where");
			String order = ds.getString("order");
			Integer skip = ds.getInteger("skip");
			Integer limit = ds.getInteger("limit");
			if (fields == null || fields.isEmpty()) {
				sql = sql.replace("{1}", "*");
			} else {
				StringBuilder column = new StringBuilder();
				for (Iterator<Map.Entry<String, Object>> it = fields.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();
					column.append(entry.getKey());
					column.append(",");
				}
				column.delete(column.lastIndexOf(","), column.length());
				sql = sql.replace("{1}", column);
			}
			if (where != null && !where.isEmpty()) {
				Set<Map.Entry<String, Object>> dss = where.entrySet();
				StringBuilder column = new StringBuilder(" where ");
				for (Iterator<Map.Entry<String, Object>> it = dss.iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();

					Iterator<Map.Entry<String, Object>> opIterator = ((JSONObject) entry.getValue()).entrySet().iterator();
					Map.Entry<String, Object> opEntry = opIterator.next();
					if (opEntry.getKey().toUpperCase().equals("BETWEEN")) {
						column.append(entry.getKey());
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						column.append(" ? and ?");
						param.add(((JSONArray) opEntry.getValue()).toArray()[0]);
						param.add(((JSONArray) opEntry.getValue()).toArray()[1]);
					} else if (opEntry.getKey().toUpperCase().equals("INQ") || opEntry.getKey().toUpperCase().equals("NIN")) {
						column.append(entry.getKey());
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						Object[] params = ((JSONArray) opEntry.getValue()).toArray();
						column.append(" (?");
						param.add(params[0]);
						for (int i = 1; i < params.length; i++) {
							column.append(",?");
							param.add(params[i]);
						}
						column.append(")");

					} else if (opEntry.getKey().toUpperCase().equals("INQLIKE")) {
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						Object[] params = ((JSONArray) opEntry.getValue()).toArray();
						column.append(" (");
						column.append(entry.getKey());
						column.append(" like ?");
						param.add("%" + params[0] + "%");
						for (int i = 1; i < params.length; i++) {
							column.append(" or ");
							column.append(entry.getKey());
							column.append(" like ?");
							param.add("%" + params[i] + "%");
						}
						column.append(")");

					} else if (opEntry.getKey().toUpperCase().equals("LIKE") || opEntry.getKey().toUpperCase().equals("NLIKE")) {
						column.append(entry.getKey());
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						column.append(" ?");
						param.add("%" + opEntry.getValue() + "%");
					} else {
						column.append(entry.getKey());
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						column.append(" ?");
						param.add(opEntry.getValue());
					}
					column.append(" and ");
				}
				column.delete(column.lastIndexOf("and"), column.length());
				sql += column.toString();
			}

			if (StringUtils.isNoneBlank("order")) {
				sql += " order by " + order;
			}
			if (skip != null) {
				sql += " limit " + skip;
				if (limit != null) {
					sql += "," + limit;
				}

			}
			List<HashMap<String, Object>> rs = null;
			if (param.size() == 0) {
				rs = impl.executeQueryByList(sql);
			} else {
				rs = impl.executeQueryByList(sql, param.toArray());
			}
			if ("user".equalsIgnoreCase(object)) {
				for (HashMap<String, Object> map : rs) {
					map.remove("password");
				}
			}
			return JSON.toJSONString(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public String login(String username, String password) throws BusinessException {
		if (StringUtils.isBlank(username)) {
			throw new BusinessException("用户名称不能为空");
		}
		if (StringUtils.isBlank(password)) {
			throw new BusinessException("密码不能为空");
		}
		OperationCoreImpl impl = null;
		try {
			impl = OperationCoreImpl.createFactory();
			String sql = " select * from user where username = ? and password = ?  ";
			Object[] values = new Object[] { username, MD5.md5(password) };
			HashMap<String, Object> rs = impl.executeQueryByOne(sql, values);
			if (rs == null || rs.isEmpty()) {
				throw new BusinessException("用户不存在或密码错误");
			}

			int inertInt = insertToken(impl, rs.get("id").toString());
			HashMap<String, Object> rs1 = null;
			if (inertInt > 0) {
				String sql1 = " SELECT b.id as TOKEN,b.TTL,u.id as USERID,u.USERNAME,u.USERTYPE,u.EMAIL,u.EMAILVERIFIED,u.MOBILE from token b left JOIN user u on u.ID=b.USERID where b.CREATEAT = (SELECT max(t.CREATEAT) FROM token t,USER u WHERE u.id = t.userid AND u.username = ? AND u. PASSWORD = ? GROUP BY	t.userid) ";
				Object[] values1 = new Object[] { username, MD5.md5(password) };
				rs1 = impl.executeQueryByOne(sql1, values1);
			}
			return JSON.toJSONString(rs1);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("服务器连接异常，请稍后重试！", e);
		}
	}

	public String logout(String token) throws BusinessException {
		if (StringUtils.isBlank(token)) {
			throw new BusinessException("token称不能为空");
		}
		return delete("token", token);
	}

	public String count(String object, String filter) throws BusinessException {
		if (StringUtils.isBlank(filter)) {
			throw new BusinessException("没有传递数据");
		}
		String sql = SqlEnums.COUNT.getSql();
		sql = sql.replace("{0}", object);
		JSONObject ds = JSON.parseObject(filter);
		JSONObject where = ds.getJSONObject("where");
		if (!where.isEmpty()) {
			Set<Map.Entry<String, Object>> dss = where.entrySet();
			StringBuilder column = new StringBuilder(" where ");
			List<Object> param = new ArrayList<Object>();
			for (Iterator<Map.Entry<String, Object>> it = dss.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = it.next();
				column.append(entry.getKey());
				Iterator<Map.Entry<String, Object>> opIterator = ((JSONObject) entry.getValue()).entrySet().iterator();
				Map.Entry<String, Object> opEntry = opIterator.next();
				if (opEntry.getKey().toUpperCase().equals("BETWEEN")) {
					column.append(" ");
					column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
					column.append(" ? and ?");
					param.add(((JSONArray) opEntry.getValue()).toArray()[0]);
					param.add(((JSONArray) opEntry.getValue()).toArray()[1]);
				} else if (opEntry.getKey().toUpperCase().equals("INQ") || opEntry.getKey().toUpperCase().equals("NIN")) {
					column.append(" ");
					column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
					Object[] params = ((JSONArray) opEntry.getValue()).toArray();
					column.append(" (?");
					param.add(params[0]);
					for (int i = 1; i < params.length; i++) {
						column.append(",?");
						param.add(params[i]);
					}
					column.append(")");
				} else if (opEntry.getKey().toUpperCase().equals("LIKE") || opEntry.getKey().toUpperCase().equals("NLIKE")) {
					column.append(" ");
					column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
					column.append(" ?");
					param.add("%" + opEntry.getValue() + "%");
				} else {
					column.append(" ");
					column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
					column.append(" ?");
					param.add(opEntry.getValue());
				}
				column.append(" and ");
			}
			column.delete(column.lastIndexOf("and"), column.length());
			sql += column.toString();
			Object[] val = param.toArray();
			try {
				HashMap<String, Object> rs = OperationCoreImpl.createFactory().executeQueryByOne(sql, val);
				return JSON.toJSONString(rs);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
		} else {
			JSONObject rs = new JSONObject();
			rs.put("count", 0);
			return JSON.toJSONString(rs);
		}

	}

	public String exists(String object, String id) throws BusinessException {
		if (StringUtils.isBlank(id)) {
			throw new BusinessException("没有传递数据");
		}
		String sql = SqlEnums.SELECT.getSql();
		sql = sql.replace("{0}", object);
		sql = sql.replace("{1}", "*");

		sql += " where id = ?";
		try {
			HashMap<String, Object> rs = OperationCoreImpl.createFactory().executeQueryByOne(sql, id);
			JSONObject result = new JSONObject();
			if (rs.isEmpty()) {
				result.put("exists", false);
				return JSON.toJSONString(result);
			} else {
				result.put("exists", true);
				return JSON.toJSONString(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public String findOne(String object, String data) throws BusinessException {
		if (StringUtils.isBlank(data)) {
			throw new BusinessException("没有传递数据");
		}
		OperationCoreImpl impl = null;
		try {
			impl = OperationCoreImpl.createFactory();
			String sql = SqlEnums.SELECT.getSql();
			List<Object> param = new ArrayList<Object>();
			sql = sql.replace("{0}", object);
			JSONObject ds = JSON.parseObject(data);
			JSONObject fields = ds.getJSONObject("fields");
			JSONObject where = ds.getJSONObject("where");
			String order = ds.getString("order");
			Integer skip = ds.getInteger("skip");
			Integer limit = ds.getInteger("limit");
			if (fields == null || fields.isEmpty()) {
				sql = sql.replace("{1}", "*");
			} else {
				StringBuilder column = new StringBuilder();
				for (Iterator<Map.Entry<String, Object>> it = fields.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();
					column.append(entry.getKey());
					column.append(",");
				}
				column.delete(column.lastIndexOf(","), column.length());
				sql = sql.replace("{1}", column);
			}
			if (where != null && !where.isEmpty()) {
				Set<Map.Entry<String, Object>> dss = where.entrySet();
				StringBuilder column = new StringBuilder(" where ");
				for (Iterator<Map.Entry<String, Object>> it = dss.iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();
					column.append(entry.getKey());
					Iterator<Map.Entry<String, Object>> opIterator = ((JSONObject) entry.getValue()).entrySet().iterator();
					Map.Entry<String, Object> opEntry = opIterator.next();
					if (opEntry.getKey().toUpperCase().equals("BETWEEN")) {
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						column.append(" ? and ?");
						param.add(((JSONArray) opEntry.getValue()).toArray()[0]);
						param.add(((JSONArray) opEntry.getValue()).toArray()[1]);
					} else if (opEntry.getKey().toUpperCase().equals("INQ") || opEntry.getKey().toUpperCase().equals("NIN")) {
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						Object[] params = ((JSONArray) opEntry.getValue()).toArray();
						column.append(" (?");
						param.add(params[0]);
						for (int i = 1; i < params.length; i++) {
							column.append(",?");
							param.add(params[i]);
						}
						column.append(")");
					} else if (opEntry.getKey().toUpperCase().equals("LIKE") || opEntry.getKey().toUpperCase().equals("NLIKE")) {
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						column.append(" ?");
						param.add("%" + opEntry.getValue() + "%");
					} else {
						column.append(" ");
						column.append(Operator.valueOf(opEntry.getKey().toUpperCase()).getOperator());
						column.append(" ?");
						param.add(opEntry.getValue());
					}
					column.append(" and ");
				}
				column.delete(column.lastIndexOf("and"), column.length());
				sql += column.toString();
			}

			if (StringUtils.isNoneBlank("order")) {
				sql += " order by " + order;
			}
			if (skip != null) {
				sql += " limit 0";
				if (limit != null) {
					sql += ",1";
				}

			}
			List<HashMap<String, Object>> rs = null;
			if (param.size() == 0) {
				rs = impl.executeQueryByList(sql);
			} else {
				rs = impl.executeQueryByList(sql, param.toArray());
			}
			if ("user".equalsIgnoreCase(object)) {
				for (HashMap<String, Object> map : rs) {
					map.remove("password");
				}
			}
			if (rs != null) {
				return JSON.toJSONString(rs.get(0));
			} else {
				return "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public String verify(String object, String id) throws BusinessException {

		return null;
	}

	public String reset(String object, String id) throws BusinessException {

		return null;
	}

	public String sendvercode(String object, String id) throws BusinessException {

		return null;
	}

	public String checkvercode(String object, String id) throws BusinessException {

		return null;
	}

	public String resetByMobile(String object, String id) throws BusinessException {

		return null;
	}

	public String batch(String object, String id) throws BusinessException {

		return null;
	}

	public void createClass(String classes) throws BusinessException {
		String sql = "CREATE TABLE IF NOT EXISTS " + classes.toUpperCase() + "(ID VARCHAR(100) NOT NULL,CREATEDAT DATETIME,UPDATEAT DATETIME)";
		String insertSql = " INSERT INTO PRO_TABLE(ID,CLASSES,NOTICE) VALUES(?,?,?)";
		try {
			OperationCoreImpl impl = OperationCoreImpl.createFactory();
			int query = impl.executeUpdate(sql);
			int update = impl.executeUpdate(insertSql, new Object[] { UUID.randomUUID().toString().replace("-", ""), classes, classes });
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	public void addColumn(String classes, String column, String dataType) throws BusinessException {
		String sql = "ALTER TABLE " + classes.toUpperCase() + " ADD COLUMN " + column.toUpperCase() + dataType;
		try {
			int executeUpdate = OperationCoreImpl.createFactory().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	public void dropColumn(String classes, String column) throws BusinessException {
		String sql = "ALTER TABLE " + classes.toUpperCase() + " DROP COLUMN " + column.toUpperCase();
		try {
			int executeUpdate = OperationCoreImpl.createFactory().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	public void alterColumnType(String classes, String column, String dataType) throws BusinessException {
		String sql = "ALTER TABLE " + classes.toUpperCase() + " ALTER COLUMN " + column.toUpperCase() + dataType;
		try {
			int executeUpdate = OperationCoreImpl.createFactory().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	public void renameColumn(String classes, String column, String newColumn, String dataType) throws BusinessException {
		String sql = "ALTER TABLE " + classes.toUpperCase() + " " + column.toUpperCase() + " RENAME " + newColumn.toUpperCase();
		try {
			int executeUpdate = OperationCoreImpl.createFactory().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	public void dropClass(String classes) throws BusinessException {
		String sql = "DROP TABLE " + classes.toUpperCase();
		try {
			int executeUpdate = OperationCoreImpl.createFactory().executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getLocalizedMessage());
		}
	}

	public String queryClass() throws BusinessException {
		String sql = "SELECT * FROM PRO_TABLE";
		try {
			List<HashMap<String, Object>> rs = OperationCoreImpl.createFactory().executeQueryByList(sql);
			return JSON.toJSONString(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	public Object text(String object, String id, String colum) throws BusinessException {
		OperationCoreImpl impl = null;
		try {
			impl = OperationCoreImpl.createFactory();
			HashMap<String, Object> rs = impl.executeQueryByOne("select " + colum + " from " + object + " where ID = ?", id);
			return rs.get(colum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public int batch(List<HashMap<String, Object>> data, HashMap<String, Object> user, String object) throws Exception {

		String sql = SqlEnums.INSERT.getSql();

		if (data != null && !data.isEmpty()) {
			for (HashMap<String, Object> coloumData : data) {
				String id = UUID.randomUUID().toString().replace("-", "");
				coloumData.put("id", id);
				coloumData.put("createAd", new Timestamp(System.currentTimeMillis()));
				coloumData.put("updateAd", new Timestamp(System.currentTimeMillis()));
				if (user != null && !user.isEmpty()) {
					coloumData.putAll(user);
				}
			}

			HashMap<String, Object> coloumData = data.get(0);
			sql = insert(sql, object, dataForKey(coloumData), dataForValues(coloumData));
		}
		OperationCoreImpl impl = OperationCoreImpl.createFactory();
		int rs = impl.batch(sql, data);
		return rs;
	}

	private String dataForKey(HashMap<String, Object> values) {
		if (values == null || values.isEmpty()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Set keys = values.entrySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			Map.Entry entry = (Entry) it.next();
			if (StringUtils.isNotBlank(entry.getValue().toString())) {
				sb.append(entry.getKey());
				sb.append(",");
			}

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
		int i = 1;
		for (Iterator it = keys.iterator(); it.hasNext();) {
			Map.Entry entry = (Entry) it.next();
			if (StringUtils.isNotBlank(entry.getValue().toString())) {
				sb.append(" ? ");
				sb.append(",");
			}
			i++;
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	private String insert(String sql, String object, String coloum, String values) throws Exception {
		if (StringUtils.isBlank(sql)) {
			throw new Exception("sql为空");
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

	@Override
	public String login(String data) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}
