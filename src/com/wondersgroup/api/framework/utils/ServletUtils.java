package com.wondersgroup.api.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletUtils {

	private static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

	public static String getRequestJson(HttpServletRequest request)
			throws IOException {

		StringBuffer jb = new StringBuffer();
		String line = null;
		BufferedReader bufferReader = request.getReader();
		while ((line = bufferReader.readLine()) != null)
			jb.append(line);
		if (StringUtils.isBlank(jb)) {
			return "";
		}
		return Encodes.urlDecode(jb.toString());
	}

	public static String getRequestBodyJson(HttpServletRequest request)
			throws IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		BufferedReader bufferReader = request.getReader();
		while ((line = bufferReader.readLine()) != null)
			jb.append(line);
		if (StringUtils.isBlank(jb)) {
			return "";
		}
		return Encodes.urlDecode(jb.toString().substring(5));
	}


	public static String getRequestJsonByFilter(HttpServletRequest request)  {
		String json=null;
		try {
			json = URLDecoder.decode(URLDecoder.decode(request.getParameter("filter"), "UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return json;
		}
		if (logger.isInfoEnabled()) {
			logger.info("请求的json: " + json);
		}
		return json;
	}
}
