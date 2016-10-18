package com.wondersgroup.api.web.controll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ToController {

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
	@RequestMapping(value = "/{buss}/{url}/default", method = RequestMethod.GET)
	public ModelAndView defaultUrl(@PathVariable String buss, @PathVariable String url, HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return new ModelAndView("/" + buss + "/" + url);
	}

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
	@RequestMapping(value = "/{buss}/{url}/forward", method = RequestMethod.GET)
	public ModelAndView forward(@PathVariable String buss, @PathVariable String url, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("forward：/" + buss + "/" + url);
	}

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
	@RequestMapping(value = "/{buss}/{url}/redirect", method = RequestMethod.GET)
	public ModelAndView redirect(@PathVariable String buss, @PathVariable String url, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("redirect:/" + buss + "/" + url);
	}

}
