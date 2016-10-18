package com.wondersgroup.api.apicloud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.api.apicloud.base.BaseController;
import com.wondersgroup.api.apicloud.base.BaseServiceImpl;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.utils.ServletUtils;

@Controller
@RequestMapping("/api")
public class ApiCloud extends BaseController {

	@RequestMapping(value = "/{object}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void get(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		String appId = request.getHeader(ApiCloudContent.API_APPID);
		String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
		BaseServiceImpl baseServiceImpl = createService(appId, appKey);
		try {
			String result = baseServiceImpl.get(object, id);
			writeResponse(response, result, 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		}
	}

	@RequestMapping(value = "/{object}", method = RequestMethod.POST)
	@ResponseBody
	public void save(@PathVariable String object, HttpServletRequest request, HttpServletResponse response) {
		String data = null;
		try {
			data = ServletUtils.getRequestJson(request);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			String result = baseServiceImpl.save(object, data);
			writeResponse(response, result, 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/{object}/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object delete(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		try {
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			return baseServiceImpl.delete(object, id);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/{object}/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		String data = null;
		try {
			data = ServletUtils.getRequestJson(request);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			String result = baseServiceImpl.update(object, id, data);
			writeResponse(response, result, 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/{object}", method = RequestMethod.GET)
	@ResponseBody
	public void query(@PathVariable String object, HttpServletRequest request, HttpServletResponse response) {
		String data = null;
		try {

			data = ServletUtils.getRequestJsonByFilter(request);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			String result = baseServiceImpl.query(object, data);
			writeResponse(response, result, 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		}
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response) {

		try {
			String data = ServletUtils.getRequestJson(request);
			Map<String, String> dataMap = JSON.parseObject(data, Map.class);
			String userName = dataMap.get("username");
			String password = dataMap.get("password");
			;
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			String result = baseServiceImpl.login(userName, password);
			writeResponse(response, result, 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			String token = request.getParameter("token");
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			return baseServiceImpl.logout(token);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/{object}/count", method = RequestMethod.GET)
	@ResponseBody
	public void count(@PathVariable String object, HttpServletRequest request, HttpServletResponse response) {
		String data = null;
		try {
			data = ServletUtils.getRequestJsonByFilter(request);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			String result = baseServiceImpl.count(object, data);
			writeResponse(response, result, 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		}
	}

	@RequestMapping(value = "/{object}/{id}/exists", method = RequestMethod.GET)
	@ResponseBody
	public Object exists(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		String data = null;
		try {
			data = ServletUtils.getRequestJson(request);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			return baseServiceImpl.exists(object, id);
		} catch (BusinessException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return new BusinessException(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/{object}/{id}/findOne", method = RequestMethod.GET)
	@ResponseBody
	public void findOne(@PathVariable String object, HttpServletRequest request, HttpServletResponse response) {
		String data = null;
		try {

			data = ServletUtils.getRequestJsonByFilter(request);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			String result = baseServiceImpl.findOne(object, data);
			writeResponse(response, result, 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		}
	}

	@RequestMapping(value = "/{object}/{id}/verify", method = RequestMethod.POST)
	@ResponseBody
	public Object verify(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/reset", method = RequestMethod.POST)
	@ResponseBody
	public Object reset(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/sendvercode", method = RequestMethod.POST)
	@ResponseBody
	public Object sendvercode(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/checkvercode", method = RequestMethod.POST)
	@ResponseBody
	public Object checkvercode(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@RequestMapping(value = "/{object}/{id}/resetByMobile", method = RequestMethod.POST)
	@ResponseBody
	public Object resetByMobile(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	// @RequestMapping(value = "/{object}/{id}/batch", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Object batch(@PathVariable String object, @PathVariable String id,
	// HttpServletRequest request, HttpServletResponse response) {
	//
	// return null;
	// }

	/**
	 * 保存数据
	 * 
	 * @param object
	 *            查询对象
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return {"message":"删除成功","result":1,"success":0}
	 */
	@RequestMapping(value = "/batch/{object}", method = RequestMethod.POST)
	@ResponseBody
	public void batch(@PathVariable String object, HttpServletRequest request, HttpServletResponse response) {

		try {
			String data = ServletUtils.getRequestJson(request);
			ArrayList d = (ArrayList) JSON.parseArray(data, HashMap.class);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BaseServiceImpl baseServiceImpl = createService(appId, appKey);
			int result=baseServiceImpl.batch(d, null, object);
			writeResponse(response, "{\"result\":\""+result+"\"}", 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/{object}/{id}/{colum}/text", method = RequestMethod.GET)
	public void text(@PathVariable String object, @PathVariable String id, @PathVariable String colum, HttpServletRequest request, HttpServletResponse response) {
		String appId = request.getHeader(ApiCloudContent.API_APPID);
		String appKey = request.getHeader(ApiCloudContent.API_APPKEY);

		BaseServiceImpl baseServiceImpl = createService("", "");
		Object result = baseServiceImpl.text(object, id, colum);
		ServletOutputStream out = null;
		response.setStatus(200);
		try {
			out = response.getOutputStream();
			byte[] b = result.toString().getBytes("UTF-8");
			out.print(new String(b, "ISO-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeResponse(HttpServletResponse response, String body, int status) {
		ServletOutputStream out = null;
		response.setStatus(status);
		try {
			out = response.getOutputStream();
			byte[] b = body.getBytes("UTF-8");
			out.print(new String(b, "ISO-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
