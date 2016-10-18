package com.wondersgroup.api.web.controll;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.api.framework.base.BaseController;
import com.wondersgroup.api.framework.enums.SuccessEnums;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.utils.ServletUtils;
import com.wondersgroup.api.web.bean.Request;
import com.wondersgroup.api.web.bean.Response;
import com.wondersgroup.api.web.service.EngineService;
import com.wondersgroup.api.web.service.GeneralSqlService;

@Controller
@RequestMapping("/engine")
public class EngineController extends BaseController {

	@Resource
	protected EngineService engineService;
	@Resource
	protected GeneralSqlService generalSqlService;

	/**
	 * 查询方法
	 * 
	 * @param object
	 *            业务对象
	 * @param request
	 *            请求参数
	 * @param response
	 *            响应参数
	 * @return {"message":"删除成功","result":1,"success":0}
	 */
	@RequestMapping(value = "/{object}", method = RequestMethod.GET)
	@ResponseBody
	public Response find(@PathVariable String object, HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			String data = request.getParameter("filter");
			data = URLDecoder.decode(data, "UTF-8");
			Request req = JSON.parseObject(data, Request.class);
			res.setResult(engineService.find(req, object));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("查询失败:" + e.toString());
		}
		return res;
	}

	/**
	 * 查询页面
	 * 
	 * @param id
	 *            查询主键
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return {"message":"删除成功","result":1,"success":0}
	 * @throws Exception
	 */
	@RequestMapping(value = "/{object}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response show(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			res.setResult(engineService.get(null, id, object));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("查询失败:" + e.toString());
		}
		return res;
	}

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
	@RequestMapping(value = "/{object}", method = RequestMethod.POST)
	@ResponseBody
	public Response create(@PathVariable String object, HttpServletRequest request, HttpServletResponse response) {

		Response res = new Response();
		try {
			String data = ServletUtils.getRequestBodyJson(request);
			Request req = new Request();
			HashMap<String, Object> d = JSON.parseObject(data, HashMap.class);
			setCreateAd(request.getSession(), d, object);
			req.setData(d);
			res.setResult(engineService.save(req, object));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("保存成功");

		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("保存失败:" + e.getMessage());
		}

		return res;
	}

	/**
	 * 更新对象
	 * 
	 * @param object
	 *            对象信息
	 * @param id
	 *            更新主键
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return {"message":"删除成功","result":1,"success":0}
	 */
	@RequestMapping(value = "/{object}/{ids}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@PathVariable String object, @PathVariable String ids, HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			String data = ServletUtils.getRequestBodyJson(request);
			Request req = new Request();
			HashMap<String, Object> d = JSON.parseObject(data, HashMap.class);
			setUpdateAd(request.getSession(), d, object);
			req.setData(d);
			res.setResult(engineService.update(req, object, ids));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("更新成功");

		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("更新失败:" + e.toString());
		}
		return res;
	}

	/**
	 * 删除一个对象
	 * 
	 * @param object
	 *            删除对象
	 * @param id
	 *            主键
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return {"message":"删除成功","result":1,"success":0}
	 */
	@RequestMapping(value = "/{object}/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			res.setResult(engineService.delete(object, id));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("删除失败:" + e.toString());
		}
		return res;
	}

	/**
	 * 删除多个
	 * 
	 * @param object
	 *            对象
	 * @param ids
	 *            多个主键，以逗号分割 列： 1,2,3
	 * @param request
	 * @param response
	 * @return {"message":"删除成功","result":1,"success":0}
	 */
	@RequestMapping(value = "/{object}/deletes/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response deletes(@PathVariable String object, @PathVariable String ids, HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			res.setResult(engineService.deletes(object, ids));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("删除失败:" + e.toString());
		}
		return res;
	}

	/**
	 * 
	 * 
	 * @param object
	 *            删除对象
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return {"message":"删除成功","result":1,"success":0}
	 */
	@RequestMapping(value = "/{sqlKey}/general", method = RequestMethod.POST)
	@ResponseBody
	public Response general(@PathVariable String sqlKey, HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			res.setResult(generalSqlService.findOne(sqlKey));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("删除失败:" + e.toString());
		}
		return res;
	}

	public void setEngineService(EngineService engineService) {
		this.engineService = engineService;
	}

	protected void setCreateAd(HttpSession session, HashMap<String, Object> data, String object) {
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		data.put("CREATEAD", sdfDateFormat.format(new Date()));
	}

	protected void setUpdateAd(HttpSession session, HashMap<String, Object> data, String object) {
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		data.put("UPDATEAD", sdfDateFormat.format(new Date()));
	}

}
