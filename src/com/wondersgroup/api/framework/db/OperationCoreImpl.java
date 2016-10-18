package com.wondersgroup.api.framework.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;

/**
 * 类名：OperationCoreImplements<br>
 * 
 * 作用: 该类实现IOperationCore接口的所有方法<br>
 * 
 */
public class OperationCoreImpl implements IOperationCore {
	protected Connection aConnection = null;
	protected static OperationCoreImpl m_instance = null;
	private static Logger logger = LoggerFactory.getLogger(OperationCoreImpl.class);

	/**
	 * Singleton 即单例(态)模式,用来生成对象唯一实例的方法
	 * 
	 * @return OperationCoreImplements的一个实例
	 * @throws Exception
	 */
	public static OperationCoreImpl createFactory() throws Exception {
		if (m_instance == null)
			m_instance = new OperationCoreImpl();
		return m_instance;
	}

	/** @exception Exception */
	public OperationCoreImpl() throws Exception {
		init();
	}

	private void init() throws Exception {
		aConnection = ConnectionFactory.getConnection();
	}

	public void commit() throws SQLException {
		aConnection.commit();
	}

	public void rollback() throws SQLException {
		aConnection.rollback();
	}

	public void setAutoCommit(boolean auto) throws SQLException {
		aConnection.setAutoCommit(auto);
	}

	/**
	 * 释放系统连接资源
	 */
	public void dispose() {
		try {

			if (aConnection != null)
				aConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * sql更新语句
	 * 
	 * @param queryString
	 *            查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * 
	 * @exception SQLException
	 */
	public ResultSet executeQuery(String queryString) {
		Statement aStatement = null;
		ResultSet aResultSet = null;

		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + queryString);
		}
		try {
			aStatement = aConnection.createStatement();
			aResultSet = aStatement.executeQuery(queryString);
		} catch (SQLException e) {
			aResultSet = null;
			e.printStackTrace();
		}
		return aResultSet;
	}

	/**
	 * sql更新语句
	 * 
	 * @param queryString
	 *            查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * 
	 * @exception SQLException
	 */
	public List<HashMap<String, Object>> executeQueryByList(String queryString, Object... values) {
		PreparedStatement ps = null;
		ResultSet aResultSet = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + queryString);
		}
		try {
			ps = aConnection.prepareStatement(queryString);
			if (values != null) {
				int index = 1;
				for (Object value : values) {
					setParameter(index, value, ps);
					index++;
				}
			}
			aResultSet = ps.executeQuery();
			return getResultList(aResultSet);
		} catch (Exception e) {
			ps = null;
			aResultSet = null;
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * sql更新语句
	 * 
	 * @param queryString
	 *            查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * 
	 * @exception SQLException
	 */
	public HashMap<String, Object> executeQueryByOne(String queryString, Object... values) {
		PreparedStatement ps = null;
		ResultSet aResultSet = null;
		try {
			ps = aConnection.prepareStatement(queryString);
			if (values != null) {
				int index = 1;
				for (Object value : values) {
					setParameter(index, value, ps);
					index++;
				}
			}
			aResultSet = ps.executeQuery();
			return getResultMap(aResultSet);
		} catch (Exception e) {
			ps = null;
			aResultSet = null;
			e.printStackTrace();
		}
		return null;
	}

	private void setParameter(int index, Object value, PreparedStatement ps) throws Exception {
		if (value instanceof Integer) {
			ps.setInt(index, (Integer) ObjectUtils.defaultIfNull(value, 0));
		} else if (value instanceof Long) {
			ps.setLong(index, (Long) ObjectUtils.defaultIfNull(value, 0));
		} else if (value instanceof Double) {
			ps.setDouble(index, (Double) ObjectUtils.defaultIfNull(value, 0));
		} else if (value instanceof Float) {
			ps.setFloat(index, (Float) ObjectUtils.defaultIfNull(value, 0));
		} else if (value instanceof BigDecimal) {
			ps.setBigDecimal(index, (BigDecimal) ObjectUtils.defaultIfNull(value, 0));
		} else if (value instanceof Date) {
			ps.setTimestamp(index, (Timestamp) value);
		} else if (value instanceof Boolean) {
			ps.setBoolean(index, (Boolean) ObjectUtils.defaultIfNull(value, true));
		} else if (value instanceof Object) {
			ps.setObject(index, value);
		} else {
			ps.setString(index, (String) ObjectUtils.defaultIfNull(value, ""));
		}
	}

	/**
	 * sql更新语句
	 * 
	 * @param updateString
	 *            数据库更新语句
	 * @return 更新数据库影响行数
	 * 
	 * @exception SQLException
	 */
	public int executeUpdate(String updateString) {
		Statement aStatement = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + updateString);
		}
		int effectedRows = 0;
		try {
			aConnection.setAutoCommit(false);
			aStatement = aConnection.createStatement();
			effectedRows = aStatement.executeUpdate(updateString);
			aConnection.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
			if (aConnection != null) {
				try {
					aConnection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return effectedRows;
	}

	/**
	 * sql更新语句
	 * 
	 * @param updateString
	 *            数据库更新语句
	 * @return 更新数据库影响行数
	 * 
	 * @exception SQLException
	 */
	public int executeUpdateNoTransaction(String updateString) {
		Statement aStatement = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + updateString);
		}
		int effectedRows = 0;
		try {
			aStatement = aConnection.createStatement();
			effectedRows = aStatement.executeUpdate(updateString);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return effectedRows;
	}

	/**
	 * sql更新语句
	 * 
	 * @param updateString
	 *            数据库更新语句
	 * @return 更新数据库影响行数
	 * 
	 * @exception SQLException
	 */
	public int executeUpdate(String updateString, Object... values) {
		PreparedStatement ps = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + updateString);
		}
		int effectedRows = 0;
		try {
			aConnection.setAutoCommit(false);
			ps = aConnection.prepareStatement(updateString);
			if (values != null) {
				int index = 1;
				for (Object value : values) {
					setParameter(index, value, ps);
					index++;
				}
			}
			effectedRows = ps.executeUpdate();
			aConnection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			ps = null;
			if (aConnection != null) {
				try {
					aConnection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return effectedRows;
	}

	/**
	 * sql批量更新语句
	 * 
	 * @param updateString
	 *            数据库更新语句
	 * @return 更新数据库影响行数
	 * 
	 * @exception SQLException
	 */
	public int batch(String updateString, List<HashMap<String, Object>> values) {
		PreparedStatement ps = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + updateString);
		}
		final int batchSize = 1000;
		int count = 0;
		int[] effectedRows = new int[] {};
		try {
			aConnection.setAutoCommit(false);
			ps = aConnection.prepareStatement(updateString);
			if (values != null && !values.isEmpty()) {
				for (HashMap<String, Object> data : values) {
					int index = 1;
					for (Entry<String, Object> value : data.entrySet()) {
						setParameter(index, value.getValue(), ps);
						index++;
					}
					ps.addBatch();
					if (++count % batchSize == 0) {
						effectedRows = ps.executeBatch();
					}
				}
			}
			effectedRows = ps.executeBatch();
			aConnection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			ps = null;
			if (aConnection != null) {
				try {
					aConnection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	/**
	 * sql批量更新语句
	 * 
	 * @param updateString
	 *            数据库更新语句
	 * @return 更新数据库影响行数
	 * 
	 * @exception SQLException
	 */
	public int batchTran(String updateString, List<HashMap<String, Object>> values) {
		PreparedStatement ps = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + updateString);
		}
		final int batchSize = 1000;
		int count = 0;
		int[] effectedRows = new int[] {};
		try {
			ps = aConnection.prepareStatement(updateString);
			if (values != null && !values.isEmpty()) {
				for (HashMap<String, Object> data : values) {
					int index = 1;
					for (Entry<String, Object> value : data.entrySet()) {
						setParameter(index, value.getValue(), ps);
						index++;
					}
					ps.addBatch();
					if (++count % batchSize == 0) {
						effectedRows = ps.executeBatch();
					}
				}
			}
			effectedRows = ps.executeBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
			ps = null;
			if (aConnection != null) {
				try {
					aConnection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	/**
	 * sql更新语句
	 * 
	 * @param updateString
	 *            数据库更新语句
	 * @return 更新数据库影响行数
	 * 
	 * @exception SQLException
	 */
	public int executeUpdateNoTransaction(String updateString, Object... values) {
		PreparedStatement ps = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + updateString);
		}
		int effectedRows = 0;
		try {
			ps = aConnection.prepareStatement(updateString);
			if (values != null) {
				int index = 1;
				for (Object value : values) {
					setParameter(index, value, ps);
					index++;
				}
			}
			effectedRows = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			ps = null;
		}
		return effectedRows;
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有列名
	 * 
	 * @param queryString
	 *            用于返回<code>ResultSet</code>结果集的语句
	 * @return 表中的所有列名
	 * @throws SQLException
	 */
	public Collection<String> getColumnNames(String queryString) {
		ResultSet aResultSet = null;
		ArrayList<String> ColumnNames = new ArrayList<String>();
		try {
			aResultSet = executeQuery(queryString);
			ResultSetMetaData rsmd = aResultSet.getMetaData();
			int j = rsmd.getColumnCount();
			for (int k = 0; k < j; k++) {
				ColumnNames.add(rsmd.getColumnName(k + 1));
			}
		} catch (SQLException e) {
			ColumnNames = null;
			e.printStackTrace();
		}
		return ColumnNames;
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有字段类型名称
	 * 
	 * @param queryString
	 *            用于返回查询结果集的语句
	 * @return 表中的所有字段类型名称
	 * @throws SQLException
	 */
	public Collection<String> getColumnTypeNames(String queryString) {
		ResultSet aResultSet = null;
		ArrayList<String> ColumnNames = new ArrayList<String>();
		try {
			aResultSet = executeQuery(queryString);
			ResultSetMetaData rsmd = aResultSet.getMetaData();
			int j = rsmd.getColumnCount();
			for (int k = 0; k < j; k++) {
				ColumnNames.add(rsmd.getColumnTypeName(k + 1));
			}
		} catch (SQLException e) {
			ColumnNames = null;
			e.printStackTrace();
		}
		return ColumnNames;
	}

	/**
	 * 读取列名
	 * 
	 * @param columIndex
	 *            列索引
	 * @param queryString
	 *            提供ResultSet二维表的查询字符串
	 * @return ResultSet表中的指定的列名
	 * 
	 * @exception SQLException
	 */
	public String getColumnName(int columIndex, String queryString) {
		ResultSet aResultSet = null;
		ResultSetMetaData rsmd = null;
		String columnName = null;
		try {
			aResultSet = executeQuery(queryString);
			rsmd = aResultSet.getMetaData();
			columnName = rsmd.getColumnName(columIndex + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnName;
	}

	/**
	 * 读取列数个数
	 * 
	 * @param queryString
	 *            查询语句
	 * @return Transact-SQL 查询后的虚拟表的列数
	 * 
	 * @exception SQLException
	 */
	public int getColumnCount(String queryString) {
		ResultSet aResultSet = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + queryString);
		}
		int columnCount = 0;
		try {
			aResultSet = executeQuery(queryString);
			ResultSetMetaData rsmd = aResultSet.getMetaData();
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnCount;
	}

	/**
	 * 读取行个数
	 * 
	 * @param queryString
	 *            查询语句
	 * @return Transact-SQL 查询后的虚拟表的行数
	 * 
	 * @exception SQLException
	 */
	public int getRowCount(String queryString) {
		ResultSet aResultSet = null;
		if (logger.isInfoEnabled()) {
			logger.info("执行sql=" + queryString);
		}
		int rowCount = 0;
		try {
			aResultSet = executeQuery(queryString);
			while (aResultSet.next())
				rowCount = aResultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	/**
	 * 获取ResultSet二维表中指定位置的值,目前只支持mysql
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param columnIndex
	 *            列索引
	 * @param queryString
	 *            产生一个ResultSet结果集的查询语句
	 * @return 指定位置的数据记录
	 * 
	 * @exception SQLException
	 */
	public Object getValueAt(int rowIndex, int columnIndex, String queryString) {
		ResultSet aResultSet = null;
		Object values = null;
		try {
			aResultSet = executeQuery(queryString);
			aResultSet.absolute(rowIndex + 1);
			values = aResultSet.getObject(columnIndex + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return values;
	}

	public static HashMap<String, Object> getResultMap(ResultSet rs) throws SQLException {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= count; i++) {
				Object obj = rs.getObject(i);
				hm.put(rsmd.getColumnName(i).toLowerCase(), getParameter(obj));
			}
		}
		return hm;
	}

	public static List<HashMap<String, Object>> getResultList(ResultSet rs) throws SQLException {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		while (rs.next()) {
			HashMap<String, Object> ht = new HashMap<String, Object>();
			for (int i = 1; i <= count; i++) {
				Object obj = rs.getObject(i);
				String key = rsmd.getColumnName(i).toLowerCase();
				ht.put(key, getParameter(obj));

			}
			list.add(ht);
		}
		return list;
	}

	private static Object getParameter(Object value) throws NumberFormatException, SQLException {
		if (value instanceof Integer) {
			return (Integer) ObjectUtils.defaultIfNull(value, "");
		} else if (value instanceof Long) {
			return (Long) ObjectUtils.defaultIfNull(value, "");
		} else if (value instanceof Double) {
			return (Double) ObjectUtils.defaultIfNull(value, "");
		} else if (value instanceof Float) {
			return (Float) ObjectUtils.defaultIfNull(value, "");
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) ObjectUtils.defaultIfNull(value, "");
		} else if (value instanceof Timestamp) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			return sdf.format((Timestamp) ObjectUtils.defaultIfNull(value, new Date()));
		} else if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			return sdf.format(value);
		} else if (value instanceof Boolean) {
			return (Boolean) ObjectUtils.defaultIfNull(value, true);
		} else {
			return (String) ObjectUtils.defaultIfNull(value, "");
		}
	}
}