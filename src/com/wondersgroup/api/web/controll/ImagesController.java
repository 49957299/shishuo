package com.wondersgroup.api.web.controll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wondersgroup.api.framework.exception.BusinessException;

@Controller
public class ImagesController {

	@RequestMapping(value = "/upload/{object}/{url}", method = RequestMethod.POST)
	public ModelAndView upload(@PathVariable String object, @PathVariable String url, HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		ModelAndView model = new ModelAndView("/teacher/" + url);
		DefaultMultipartHttpServletRequest req = (DefaultMultipartHttpServletRequest) request;
		Map<String, MultipartFile> map = req.getFileMap();
		if (map == null || map.isEmpty()) {
			throw new BusinessException("没有上传附件信息");
		}
		String deployPath = "d:\\pic";
		
		String uploadPath = deployPath + File.separator + object;
		if (new File(uploadPath).exists()) {
			new File(uploadPath).mkdirs();
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, MultipartFile> entry : map.entrySet()) {
			String fileName = UUID.randomUUID().toString().replace("-", "");
			try {
				FileUtils.copyInputStreamToFile(entry.getValue().getInputStream(), new File(uploadPath + File.separator + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			sb.append(fileName);
			sb.append(",");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		model.addObject("uploadId", sb.toString());
		return model;
	}

	@RequestMapping(value = "/read/{object}/{id}", method = RequestMethod.GET)
	public void read(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		String deployPath = "d:\\pic";
		
		String readPath = deployPath + File.separator + object;
		(new File(readPath)).mkdirs();
		FileInputStream fis = null;
		ServletOutputStream fos = null;
		byte[] buffer = new byte[1000];
		int temp = 0;
		try {
			fis = new FileInputStream(new File(readPath + File.separator + id));
			fos = response.getOutputStream();
			while (true) {
				temp = fis.read(buffer, 0, buffer.length);
				if (temp == -1) {
					break;
				}
				fos.write(buffer, 0, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
