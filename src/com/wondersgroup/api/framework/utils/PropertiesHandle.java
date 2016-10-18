package com.wondersgroup.api.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandle {

	private static Properties prop = new Properties();
	public static final String API_PATH = "/properties/api.properties";
	private static PropertiesHandle properties = new PropertiesHandle();
	public static final String API_TYPE_DEFAULT = "db";

	public static PropertiesHandle getInstance() {
		try {
			if (properties == null) {
				properties = new PropertiesHandle();
			}
			prop.load(new FileInputStream(FilePathUtils.getClassPath()
					+ API_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public String getString(String key) {
		return prop.getProperty(key);
	}

	public void setString(String key, String value) {
		prop.setProperty(key, value);
	}

}
