package com.wondersgroup.api.framework.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;

import com.wondersgroup.api.web.bean.Response;

public abstract class BaseController {

	public abstract Response find(@PathVariable String object,
			HttpServletRequest request, HttpServletResponse response);

	public abstract Response show(@PathVariable String object,
			@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response);

	public abstract Response create(@PathVariable String object,
			HttpServletRequest request, HttpServletResponse response);

	public abstract Response update(@PathVariable String object,
			@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response);

	public abstract Response delete(@PathVariable String object,
			@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response);

	public abstract Response deletes(@PathVariable String object,
			@PathVariable String ids, HttpServletRequest request,
			HttpServletResponse response);

}
