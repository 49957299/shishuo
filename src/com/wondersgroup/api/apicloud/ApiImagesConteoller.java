package com.wondersgroup.api.apicloud;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApiImagesConteoller {
	@RequestMapping(value = "/apiread/{object}/{id}", method = RequestMethod.GET)
	public void read(@PathVariable String object, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		String deployPath = "d:\\pic";
		String readPath = deployPath + File.separator + object;

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
