package com.wondersgroup.api.apicloud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.api.apicloud.base.BaseController;
import com.wondersgroup.api.apicloud.base.BaseServiceImpl;
import com.wondersgroup.api.apicloud.v1.service.impl.ApiServiceImpl;
import com.wondersgroup.api.framework.enums.SuccessEnums;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.web.bean.Response;

@Controller
@RequestMapping("/protal")
public class ApiProtal extends BaseController {

	@RequestMapping(value = "/{object}", method = RequestMethod.GET)
	@ResponseBody
	public Object addClass(@PathVariable String object,
			HttpServletRequest request, HttpServletResponse response) {
		ApiServiceImpl apiServiceImpl = new ApiServiceImpl("", "");
		try {
			apiServiceImpl.createClass(object);
			Response res = new Response();
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setResult(1);
			res.setMessage("保存成功");
			return res;
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}

	}

	@RequestMapping(value = "queryClass", method = RequestMethod.POST)
	@ResponseBody
	public Object queryClass(HttpServletRequest request,
			HttpServletResponse response) {
		ApiServiceImpl apiServiceImpl = new ApiServiceImpl("", "");
		try {
			Response res = new Response();
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setResult(apiServiceImpl.queryClass());
			res.setMessage("查询成功");
			return res;
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/{object}", method = RequestMethod.PUT)
	@ResponseBody
	public Object update(@PathVariable String object, @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		String appId = request.getHeader("appId");
		String appKey = request.getHeader("appKey");
		BaseServiceImpl baseServiceImpl = createService(appId, appKey);
		try {
			return baseServiceImpl.update(object, id, data);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}

	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		String appId = request.getHeader("appId");
		String appKey = request.getHeader("appKey");
		BaseServiceImpl baseServiceImpl = createService(appId, appKey);
		try {
			return baseServiceImpl.login("","");
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(HttpServletRequest request,
			HttpServletResponse response) {
		String authorization = request.getHeader("authorization");
		String appId = request.getHeader("appId");
		String appKey = request.getHeader("appKey");
		BaseServiceImpl baseServiceImpl = createService(appId, appKey);
		try {
			return baseServiceImpl.logout(authorization);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/{object}/count", method = RequestMethod.GET)
	@ResponseBody
	public Object count(@PathVariable String object,
			HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("filter");
		String appId = request.getHeader("appId");
		String appKey = request.getHeader("appKey");
		BaseServiceImpl baseServiceImpl = createService(appId, appKey);
		try {
			return baseServiceImpl.count(object, data);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/{object}/{id}/exists", method = RequestMethod.GET)
	@ResponseBody
	public Object exists(@PathVariable String object, @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		String appId = request.getHeader("appId");
		String appKey = request.getHeader("appKey");
		BaseServiceImpl baseServiceImpl = createService(appId, appKey);
		try {
			return baseServiceImpl.exists(object, id);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/{object}/{id}/findOne", method = RequestMethod.GET)
	@ResponseBody
	public Object findOne(@PathVariable String object, @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/verify", method = RequestMethod.POST)
	@ResponseBody
	public Object verify(@PathVariable String object, @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/reset", method = RequestMethod.POST)
	@ResponseBody
	public Object reset(@PathVariable String object, @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/sendvercode", method = RequestMethod.POST)
	@ResponseBody
	public Object sendvercode(@PathVariable String object,
			@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/checkvercode", method = RequestMethod.POST)
	@ResponseBody
	public Object checkvercode(@PathVariable String object,
			@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/resetByMobile", method = RequestMethod.POST)
	@ResponseBody
	public Object resetByMobile(@PathVariable String object,
			@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/batch", method = RequestMethod.POST)
	@ResponseBody
	public Object batch(@PathVariable String object, @PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

}
