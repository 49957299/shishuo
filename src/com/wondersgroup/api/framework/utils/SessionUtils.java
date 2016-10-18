package com.wondersgroup.api.framework.utils;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

public class SessionUtils {

	public static final String SESSION_USER_KEY = "user";
	public static final String SESSION_RESOURCE_KEY = "resource";
	public static final String MENUS = "menus";
	public static final String ROLES = "roles";
	public static final String UNIT = "unit";

	public static HashMap<String, Object> getUser(HttpSession session) {
		return (HashMap<String, Object>) session.getAttribute(SESSION_USER_KEY);
	}

	public static HashMap<String, Object> getResource(HttpSession session) {
		return (HashMap<String, Object>) session
				.getAttribute(SESSION_RESOURCE_KEY);
	}

	public static String getMenus(HttpSession session) {
		return (String) session.getAttribute(MENUS);
	}

	public static List<HashMap<String, Object>> getRole(HttpSession session) {
		return (List<HashMap<String, Object>>) session.getAttribute(ROLES);
	}

	public static HashMap<String, Object> getUnit(HttpSession session) {
		return (HashMap<String, Object>) session.getAttribute(UNIT);
	}

	public static Long getUnitid(HttpSession session) {
		HashMap<String, Object> rs = getUnit(session);
		return (Long) rs.get("unitid");
	}

	public static String getUnitname(HttpSession session) {
		HashMap<String, Object> rs = getUnit(session);
		return (String) rs.get("unitname");
	}

	public static String getUnitcode(HttpSession session) {
		HashMap<String, Object> rs = getUnit(session);
		return (String) rs.get("unitcode");
	}

	public static String getRoleid(HttpSession session) {
		List rs = getRole(session);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rs.size(); i++) {
			JSONObject map = (JSONObject) rs.get(i);
			sb.append(map.get("roleid"));
			sb.append(",");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		return sb.toString();
	}

	public static String getRolename(HttpSession session) {
		List rs = getRole(session);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rs.size(); i++) {
			JSONObject map = (JSONObject) rs.get(i);
			sb.append(map.get("rolename"));
			sb.append(",");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		return sb.toString();
	}

	public static String getUserid(HttpSession session) {
		HashMap<String, Object> rs = getUser(session);
		return String.valueOf(rs.get("LOGINNAME"));
	}

	public static String getUsername(HttpSession session) {
		HashMap<String, Object> rs = getUser(session);
		return String.valueOf(rs.get("USERNAME"));
	}
}
