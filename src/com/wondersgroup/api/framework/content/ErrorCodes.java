package com.wondersgroup.api.framework.content;

/**
 * 框架错误代码表
 *
 */
public class ErrorCodes {
	/**
	 * 上下文值不能改变
	 */
	public static final String ContextValueCannotChanged = "ERR-00000001";

	/**
	 * 用户认证失败
	 */
	public static final String AuthenticationFailure = "ERR-00000002";

	/**
	 * 检查是否已登录失败
	 */
	public static final String CheckLoginFailure = "ERR-00000003";

	/**
	 * 加载应用配置失败
	 */
	public static final String LoadConfigFailure = "ERR-00000004";

	/**
	 * 初始化应用程序上下文失败
	 */
	public static final String InitAppContextFailure = "ERR-00000005";

	/**
	 * 登录失败
	 */
	public static final String LoginFailure = "ERR-00000006";

	/**
	 * 注销失败
	 */
	public static final String LogoutFailure = "ERR-00000007";

	/**
	 * 改变用户密码失败
	 */
	public static final String ChangePasswordFailure = "ERR-00000008";

	/**
	 * 页面启动上下文为NULL
	 */
	public static final String ModuleContextIsNull = "ERR-00000009";

	/**
	 * 页面参数个数或配置错
	 */
	public static final String ModuleArgumentsError = "ERR-00000010";

	/**
	 * 找不到对应的翻译器
	 */
	public static final String TranslatorNotFound = "ERR-00000011";

	/**
	 * 未定义翻译结果存储字段名称
	 */
	public static final String MissingTargetFieldName = "ERR-00000012";

	/**
	 * 执行翻译发生未知错误
	 */
	public static final String TranslateUnknownException = "ERR-00000013";

	/**
	 * 执行翻译发生未知错误
	 */
	public static final String UnSupportDialectException = "ERR-00000014";

	/**
	 * 没有发现MyBaties定义的ID错误
	 */
	public static final String NoFoundStatementIdException = "ERR-00000015";

	/**
	 * 新增员工组成员失败，不能选择一个已存在的员工进行关联.
	 */
	public static final String AddEmpGroupIsExistException = "ERR-00000016";

	/**
	 * 用户密码过期
	 */
	public static final String PasswordExpiredError = "ERR-00000017";

	/**
	 * 无效的用户名或密码
	 */
	public static final String InvalidUserNameOrPassword = "ERR-00000018";

	/**
	 * 无效的用户名或密码
	 */
	public static final String UserNameHasDisabled = "ERR-00000019";

}
