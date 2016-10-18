package com.wondersgroup.api.apicloud;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wondersgroup.api.apicloud.base.BaseServiceImpl;
import com.wondersgroup.api.apicloud.v1.service.BusinessService;
import com.wondersgroup.api.apicloud.v1.service.impl.BusinessServiceImpl;
import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.utils.ServletUtils;

@Controller
@RequestMapping("/api/business")
public class BusinessController extends ApiCloud {


	@RequestMapping(value = "/ddxx", method = RequestMethod.POST)
	@ResponseBody
	public void save(HttpServletRequest request, HttpServletResponse response) {
		String data = null;
		try {
			data = ServletUtils.getRequestJson(request);
			String appId = request.getHeader(ApiCloudContent.API_APPID);
			String appKey = request.getHeader(ApiCloudContent.API_APPKEY);
			BusinessService bs = new BusinessServiceImpl();
			String result = bs.saveDDxx(data);
			writeResponse(response, "{\"ddbh\":\""+result+"\"}", 200);
		} catch (BusinessException e) {
			writeResponse(response, e.getMessage(), 389);
		} catch (IOException e) {
			e.printStackTrace();
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
