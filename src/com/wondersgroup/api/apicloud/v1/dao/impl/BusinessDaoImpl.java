package com.wondersgroup.api.apicloud.v1.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
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
import com.wondersgroup.api.apicloud.v1.dao.BusinessDao;
import com.wondersgroup.api.framework.db.OperationCoreImpl;
import com.wondersgroup.api.framework.enums.SqlEnums;
import com.wondersgroup.api.framework.exception.BusinessException;

public class BusinessDaoImpl implements BusinessDao {
	public String saveDDxx(String businessData) {
		OperationCoreImpl impl=null;
		try {
			HashMap dataMap = JSON.parseObject(businessData, HashMap.class);
			JSONObject ddxx = (JSONObject) dataMap.get("ddxx");
			JSONArray ddkcxx = (JSONArray) dataMap.get("ddkcxx");
			JSONArray ddsksjxx = (JSONArray) dataMap.get("ddsksjxx");
			String ddbh = getDdbh();

			HashMap<String, Object> ddxxMap = (HashMap<String, Object>) JSON
					.parseObject(ddxx.toJSONString(), HashMap.class);

			String sql = SqlEnums.INSERT.getSql();
			
			ddxxMap.put("ddbh", ddbh);
			ddxxMap.put("id", UUID.randomUUID().toString().replace("-", ""));
			ddxxMap.put("createAd", new Timestamp(System.currentTimeMillis()));
			ddxxMap.put("updateAd", new Timestamp(System.currentTimeMillis()));
			
			String ddxxSql = insert(sql, "ddxx", dataForKey(ddxxMap),
					dataForValues(ddxxMap));

			List<HashMap<String,Object>> ddxxList =new ArrayList<HashMap<String,Object>>();
			ddxxList.add(ddxxMap);


			List<HashMap<String,Object>> ddkcxxList = (ArrayList) JSON
					.parseArray(ddkcxx.toJSONString(), HashMap.class);
			for (Map map : ddkcxxList) {
				map.put("ddbh", ddbh);
				map.put("id", UUID.randomUUID().toString().replace("-", ""));
				map.put("createAd", new Timestamp(System.currentTimeMillis()));
				map.put("updateAd", new Timestamp(System.currentTimeMillis()));
			}

			String ddkcxxSql = insert(sql, "ddkcxx", dataForKey(ddkcxxList.get(0)),
					dataForValues(ddkcxxList.get(0)));
			

			List<HashMap<String,Object>> ddsksjxxList = (ArrayList) JSON
					.parseArray(ddsksjxx.toJSONString(), HashMap.class);
			for (Map map : ddsksjxxList) {
				map.put("ddbh", ddbh);
				map.put("id", UUID.randomUUID().toString().replace("-", ""));
				map.put("createAd", new Timestamp(System.currentTimeMillis()));
				map.put("updateAd", new Timestamp(System.currentTimeMillis()));
			}
			String ddsksjxxSql = insert(sql, "ddsksjxx",
					dataForKey(ddsksjxxList.get(0)), dataForValues(ddsksjxxList.get(0)));
			
			impl = OperationCoreImpl.createFactory();
			impl.setAutoCommit(false);
			impl.batchTran(ddxxSql, ddxxList);
			impl.batchTran(ddkcxxSql, ddkcxxList);
			impl.batchTran(ddsksjxxSql, ddsksjxxList);
			if("是".equals(ddxxMap.get("sfsyyhq"))){
				impl.executeUpdate("update yhyhq set YHQZT='已使用' where id='"+ddxxMap.get("yhyhqid")+"'");
			}
			if("是".equals(ddxxMap.get("sfsyjf"))){
				ResultSet rs=impl.executeQuery("select jfye from jf where yhid='"+ddxxMap.get("jzyhid")+"'");
				BigDecimal jfye=new BigDecimal(0.0d);
				if(rs.next()){
					jfye=rs.getBigDecimal("jfye");
					jfye.add(jfye);
				}
				Map<String,Object> jfjlMap=new HashMap<String,Object>();
				jfjlMap.put("id", UUID.randomUUID().toString().replace("-", ""));
				jfjlMap.put("yhid", ddxxMap.get("jzyhid"));
				jfjlMap.put("sz", ddxxMap.get("支出"));
				jfjlMap.put("je", jfye);
				jfjlMap.put("jfe", ddxxMap.get("syjfsl"));
				jfjlMap.put("jfyy", "预定老师");
				jfjlMap.put("createAd", new Timestamp(System.currentTimeMillis()));
				jfjlMap.put("updateAd", new Timestamp(System.currentTimeMillis()));
				jfjlMap.put("bz", "预定编号："+ddbh);
				
				impl.executeUpdate("update jf set jfye=jfye-"+ddxxMap.get("syjfsl")+" where yhid='"+ddxxMap.get("jzyhid")+"'");
			}
			impl.commit();
			return ddbh;
		} catch (Exception e) {
			try {
				impl.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BusinessException("预定失败，请与管理员联系！");
		}finally{
			if(impl!=null)
				try {
					impl.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	private String getDdbh() {
		ResultSet rs = null;
		try {
			rs = OperationCoreImpl.createFactory().executeQuery(
					"select ddbh from vddbh");
			rs.next();
			return rs.getString("ddbh");
		} catch (Exception e) {
			throw new BusinessException("获取订单编号异常！");
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String insert(String sql, String object, String coloum,
			String values) throws Exception {
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
			}else{
				sb.append(" ? ");
				sb.append(",");
			}
			i++;
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}
}
