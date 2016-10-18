package com.wondersgroup.api.framework.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.wondersgroup.api.framework.utils.PropertiesHandle;

/**
 * 类名：ConnectionFactory<br>
 * 作用：产生数据库连接对象<br>
 * 属性：<br>
 * 方法：Connection getConnection()<br>
 * 作用：返回数据库连接对象<br>
 * 参数：无<br>
 * 返回：数据库连接对象<br>
 * 其它：返回的aConnection不会自动提交JDBC事务<br>
 */
public abstract class ConnectionFactory {

	public static Connection getConnection() throws Exception {
		String driver = PropertiesHandle.getInstance().getString("db.driver");
		String url = PropertiesHandle.getInstance().getString("db.url");
		String username = PropertiesHandle.getInstance().getString("db.username");
		String password =PropertiesHandle.getInstance().getString("db.password");
		Class.forName(driver).newInstance(); // 申明JDBC驱动程序名

		Connection aConnection = DriverManager.getConnection(url, username,
				password); // 创建aConnection对象
		aConnection.setAutoCommit(true); // 设置不自动提交事务
		return aConnection; // 返回aConnection对象
	}

}