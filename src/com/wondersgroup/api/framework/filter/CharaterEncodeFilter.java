package com.wondersgroup.api.framework.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.wondersgroup.api.framework.logger.Logger;

/**
 * 过滤
 */
public class CharaterEncodeFilter implements Filter {

	/**
	 * 日志记录器.
	 */
	private static final Logger logger = new Logger(
			CharaterEncodeFilter.class.getName());

	private FilterConfig config = null;

	private String encoding = null;

	private String payPath;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		payPath = this.config.getInitParameter("payPath");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest httprequest = (HttpServletRequest) request;
		String servletPath = httprequest.getServletPath();
		// 进行URL过滤
		if (payPath != null && !servletPath.matches(payPath)) {
			request = new charterHttpRequest((HttpServletRequest) request);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		config = null;
		encoding = null;
	}

	class charterHttpRequest extends HttpServletRequestWrapper {

		public charterHttpRequest(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			String value = super.getParameter(name);
			if (value == null || value.length() <= 0) {
				return value;
			}
			try {
				value = URLDecoder.decode(value, "UTF-8");
				return value;
				// return new String(value.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {

			}
			return null;
		}

		@Override
		public Map getParameterMap() {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			try {
				Enumeration<?> parameterNames = super.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					String key = (String) parameterNames.nextElement();
					String value = URLDecoder.decode(this.getParameter(key),
							"UTF-8");
					parameterMap.put(URLDecoder.decode(key, "UTF-8"), value);
				}
			} catch (UnsupportedEncodingException e) {

			}
			return parameterMap;
		}

	}
}
