package com.wondersgroup.api.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 系统路径工具类
 * 
 * @author 王永成
 * 
 */
public class FilePathUtils {

	/**
	 * 得到系统路径 去掉WEB-INF 适用于bs
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getResourcePath() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		try {
			if (null != url) {
				String path = url.getPath();
				if (path.indexOf("/WEB-INF") != -1)
					path = path.substring(0, path.indexOf("/WEB-INF") + 1);
				path = URLDecoder.decode(path, "utf-8");
				if (path.indexOf("/") == 0)
					return path.substring(1, path.length());
				return path;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";

	}

	public static String getClassPath() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		try {
			if (null != url) {
				String path = url.getPath();
				path = URLDecoder.decode(path, "utf-8");
				if (path.indexOf("/") == 0)
					return path.substring(1, path.length());
				return path;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 得到系统路径 带有bin
	 * 
	 * @return
	 */
	public static String getPath() {
		URL url = ClassLoader.getSystemResource(".");
		try {
			String path = URLDecoder.decode(url.getPath(), "utf-8");
			System.out.println(" path = " + path);
			return path;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到系统路径 工程路径
	 * 
	 * @return
	 */
	public static String getRootPath() {
		return new File("").getAbsolutePath();
	}

	public static InputStream getJarPath(String config) {
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		return classloader.getResourceAsStream(config);
	}
}
