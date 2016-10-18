package com.wondersgroup.api.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wondersgroup.api.framework.content.CommonConst;
import com.wondersgroup.api.framework.logger.Logger;

public class LoginFilter implements Filter {

	private static final String LOGIN_URL = "LOGIN_URL";// 用户登录的URL地址

	private static final String HOME_URL = "HOME_URL";// 用户登录主页面的URL地址

	private static final String CONTROL_URL = "CONTROL_URL";// server控制页面

	private static final String LOAD_URL = "LOAD_URL";// 下载页面

	private static final String SESSION_TIME = "SESSION_TIME";// Session超时时间

	private static final String NO_FILTER_PATH = "NO_FILTER_PATH";// 退费回写action链接

	private static String sessionId;

	private String logon_page;

	private String home_page;

	private String control_page;

	private String load_page;

	private int sessionTime;

	private String no_filter_path;

	/*
	 * server访问的URL路径
	 */

	@Override
	public void destroy() {
	}

	@Override
	@SuppressWarnings("deprecation")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 设置请求的参数
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		// 得到用户请求的URL
		String request_uri = req.getRequestURI();
		// 得到web应用程序的上下文环境
		String ctxPath = req.getContextPath();

		// 去除上下文路径，得到剩余部分的路径
		String uri = request_uri.substring(ctxPath.length());
		if (uri.equals(logon_page) || uri.equals("/loginAction.action")
				|| uri.equals("/ajaxFindHospital.action")) {
			chain.doFilter(request, response);
			return;
		} else if (uri.equals("/shortMessageServiceAction.action")) {
			chain.doFilter(request, response);
			return;
		}
		// 设置session的超时时间
		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(sessionTime);
		if (session.getAttribute(CommonConst.SESSION_USER_NAME) != null
				&& session.getAttribute(CommonConst.SESSION_USER_PASSWORD) != null) {
			chain.doFilter(request, response);
		} else if (no_filter_path != null && uri.matches(no_filter_path)) {
			session.setAttribute(CommonConst.SESSION_USER_NAME, "temp");
			session.setAttribute(CommonConst.SESSION_USER_PASSWORD, "temp");
			chain.doFilter(request, response);
		} else {
			// 判断是否是异步请求
			if (req.getHeader("X-Requested-With") != null
					&& req.getHeader("X-Requested-With").equalsIgnoreCase(
							"XMLHttpRequest")) {
				resp.getWriter().write("连接已超时,请重新登录！");
			} else {
				// resp.sendRedirect("login.jsp");
				resp.getWriter()
						.write("<script>top.location.href='"+req.getContextPath()+"/login.jsp'</script>");
			}
			return;
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 从部署描述符中获取登录页面和首页的URI
		 logon_page = config.getInitParameter (LOGIN_URL);
		 home_page = config.getInitParameter (HOME_URL);
		// control_page = config.getInitParameter (CONTROL_URL);
		// load_page = config.getInitParameter (LOAD_URL);
		// no_filter_path = config.getInitParameter (NO_FILTER_PATH);
		// if (null == logon_page || null == home_page)
		// {
		// throw new ServletException ("没有找到登录页面！");
		// }
		String sessionTimeStr = config.getInitParameter(SESSION_TIME);
		try {
			this.sessionTime = sessionTimeStr != null ? Integer
					.parseInt(sessionTimeStr) : 1800;
		} catch (NumberFormatException e) {
			this.sessionTime = 1800;
			if (logger.isExitingLoggable()) {
				logger.exiting("Session超时时间设置失败:字符串到整数转换异常,Session已设为默认时间(30分钟)");
			}
			throw new NumberFormatException(
					"Session超时时间设置失败:字符串到整数转换异常,Session已设为默认时间(30分钟)");
		}
	}

	/**
	 * 日志记录器.
	 */
	private static final Logger logger = new Logger(LoginFilter.class.getName());
}
