package com.wondersgroup.api.framework.content;

/**
 * 通用常量
 */
public interface CommonConst {

	/**
	 * 服务器Response对象ContentType: text/xml;charset=utf-8
	 */
	final String RESPONSE_CONTENTTYPE = "text/xml;charset=utf-8";

	/**
	 * 自动登录Cookies变量名
	 */
	final String COOKIES_AUTOMATIC_LOGON = "AUTOMATIC_LOGON";

	/**
	 * 用户名Cookies变量名
	 */
	final String COOKIES_USER_NAME = "USER_NAME";

	/**
	 * 密码Cookies变量名
	 */
	final String COOKIES_PASS_WORD = "PASS_WORD";

	/**
	 * 时间标志：完整时间yyyy-MM-dd HH:mm:ss
	 */
	final String TIME_SYMBOL_FULLTIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间标志：只有日期yyyy-MM-dd
	 */
	final String TIME_SYMBOL_DATE = "yyyy-MM-dd";

	/**
	 * 字符集编码方式：UTF-8
	 */
	final String ENCODE_UTF8 = "UTF-8";
	/**
	 * 一天的毫秒数：86400000
	 */
	public final long MILLI_SECOND_OF_DAY = 86400000L;
	/**
	 * 正则表达式：只允许数字
	 */
	final String REGEX_ONLY_NUMBERS = "[0-9]+";

	static final String JDBC_PROPERTIES = "/properties/jdbc.properties";
	/**
	 * 登录信息
	 */
	public final String SESSION_USER_ID = "USER_ID";
	public final String SESSION_USER_NAME = "USER_NAME";
	public final String SESSION_USER_PASSWORD = "USER_PASSWORD";
	public final String SESSION_ORGID = "ORGID";
	public final String SESSION_ORGNAME = "ORGNAME";

	/**
	 * 获取权限
	 */
	public static final String INT_HOS_AUTH = "AUTH";

}
