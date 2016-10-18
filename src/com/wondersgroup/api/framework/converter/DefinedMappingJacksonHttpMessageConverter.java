package com.wondersgroup.api.framework.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wondersgroup.api.web.bean.Response;

public class DefinedMappingJacksonHttpMessageConverter extends FastJsonHttpMessageConverter {

	private static final int CODE_DEFALUT = 0;

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		if (obj instanceof List) {
			setHeader(outputMessage, CODE_DEFALUT, null);
			super.writeInternal(obj, outputMessage);
			return;
		} else if (obj instanceof Response) {
			Response response = (Response) obj;
			if (response.getResult() instanceof List) {
				setHeader(outputMessage, response.getSuccess().getStatus(), response.getMessage());
			}
			if (response.getResult() instanceof Integer) {
				Integer rs = Integer.parseInt(response.getResult().toString());
				if (rs == 0) {
					setHeader(outputMessage, CODE_DEFALUT, response.getMessage());
				} else {
					setHeader(outputMessage, response.getSuccess().getStatus(), response.getMessage());
				}
			}
			setHeader(outputMessage, response.getSuccess().getStatus(), response.getMessage());
			super.writeInternal(response.getResult(), outputMessage);
		} else {
			super.writeInternal(obj, outputMessage);
		}

	}

	private void setHeader(HttpOutputMessage outputMessage, int code, String message) {
		outputMessage.getHeaders().add("Cache-Control", "no-cache");
		outputMessage.getHeaders().add("Prama", "no-cache");
		outputMessage.getHeaders().add("code", code + "");
		outputMessage.getHeaders().add("message", message);
	}
}
