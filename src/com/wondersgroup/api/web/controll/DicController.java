package com.wondersgroup.api.web.controll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.api.apicloud.bean.ApiResponse;
import com.wondersgroup.api.framework.enums.Operator;
import com.wondersgroup.api.framework.enums.SuccessEnums;
import com.wondersgroup.api.framework.page.Page;
import com.wondersgroup.api.framework.utils.ServletUtils;
import com.wondersgroup.api.web.bean.Parameter;
import com.wondersgroup.api.web.bean.Request;
import com.wondersgroup.api.web.bean.Response;
import com.wondersgroup.api.web.service.EngineService;

@Controller
@RequestMapping("/dic")
public class DicController {

	@Resource
	private EngineService engineService;

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
	@RequestMapping(value = "/{object}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> find(@PathVariable String object,
			HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			Request req = new Request();
			String q = request.getParameter("q");
			String field = request.getParameter("field");
			Parameter p = new Parameter();
			p.setName(field);
			p.setOp(Operator.LIKE);
			p.setValue(q);
			List<Parameter> pList = new ArrayList<Parameter>();
			if (q != null)
				pList.add(p);
			req.setWhere(pList);
			req.setPage(new Page());
			res.setResult(engineService.find(req, object));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ((PageImpl) res.getResult()).getContent();
	}

	@RequestMapping(value = "/{object}/remote", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> dic(@PathVariable String object,
			HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		try {
			String data = ServletUtils.getRequestJson(request);
			Request req = null;
			if (StringUtils.isNoneBlank(data)) {
				req = JSON.parseObject(data, Request.class);
			} else {
				req = new Request();
			}
			res.setResult(engineService.find(req, object));
			res.setSuccess(SuccessEnums.SUCCESS);
			res.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("查询失败:" + e.toString());
		}
		return ((PageImpl) res.getResult()).getContent();
	}

	@RequestMapping(value = "/{object}/file", method = RequestMethod.POST)
	@ResponseBody
	public List<HashMap> dicfile(@PathVariable String object,
			HttpServletRequest request, HttpServletResponse response) {
		Response res = new Response();
		List<HashMap> map = null;
		try {
			String data = ServletUtils.getRequestJson(request);
			String filePath = request.getRealPath("/");
			filePath = filePath + File.separator + "json" + File.separator
					+ object + ".json";

			File file = new File(filePath);
			if (!file.exists()) {
				throw new Exception("文件不存在");
			}
			String rs = ReadFile(filePath);
			map = JSON.parseArray(rs, HashMap.class);

		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(SuccessEnums.ERROR);
			res.setMessage("查询失败:" + e.toString());
		}
		return map;
	}

	private String ReadFile(String string) {
		File redfile = new File(string);
		BufferedReader reader = null;
		String laststr = "";
		try {
			reader = new BufferedReader(new FileReader(redfile));
			String tempString = null;
			int line = 1; // 一次读入一行，直到读入null为文件结束
			try {
				while ((tempString = reader.readLine()) != null) {
					laststr = laststr + tempString;
					line++;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}

	private Boolean getFiles(String filePath, String object) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.equals(object)) {
				return true;
			}
		}
		return false;
	}

	public void setEngineService(EngineService engineService) {
		this.engineService = engineService;
	}

}
