package com.wondersgroup.api.framework.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSONObject;

public class ExceptionResolver extends SimpleMappingExceptionResolver {
	private static final String[] res = new String[] { "/login", "/index" };

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {

		if (exception instanceof BusinessException) {
			response.setDateHeader("expries", -1);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("text/html; charset=utf-8");
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("errorMsg", exception.getLocalizedMessage());
				jsonObject.put("code", -1);
				PrintWriter pw = response.getWriter();
				pw.write(jsonObject.toJSONString());
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return super.resolveException(request, response, object, exception);
		}
		return super.resolveException(request, response, object, exception);
	}

}
