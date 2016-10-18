package com.wondersgroup.api.web.controll;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wondersgroup.api.framework.exception.BusinessException;
import com.wondersgroup.api.framework.utils.MD5;
import com.wondersgroup.api.framework.utils.PropertiesHandle;
import com.wondersgroup.api.framework.utils.SessionUtils;

@Controller
public class LoginController {

	/**
	 * 登录
	 * */
	@RequestMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String loginName = request.getParameter("username");
		String passWord = request.getParameter("pass");
		boolean isValid = false;
		// // 判断是否已经在sso验证通过
		// HashMap userMap = null;
		// if ((request.getRemoteUser() != null)
		// && (!request.getRemoteUser().equals(""))) {
		// loginName = request.getRemoteUser();
		// // 直接获取用户信息
		// userMap = (HashMap) JSON.parseArray(user, HashMap.class);
		// if (userMap != null && !userMap.isEmpty()) {
		// isValid = true;
		// }
		// } else {
		// // 获得s权限，这个是不使用单点登录过来的
		// if (StringUtils.isNotBlank(loginName)
		// && StringUtils.isNotBlank(passWord)) {
		// String user = AuthLoginName.get(loginName, "getAuthUser");
		// if (StringUtils.isBlank(user)) {
		// throw new BusinessException("用户不存在");
		// }
		// userMap = (HashMap<String, Object>) JSON.parseObject(user,
		// HashMap.class);
		//
		// String PSW = AuthLoginName.get(new Object[] { loginName,
		// passWord }, "validator");
		// boolean isvalidator = Boolean.valueOf(PSW);
		// if (!isvalidator) {
		// throw new BusinessException("请确认用户名或密码是否正确！");
		// }
		//
		// }
		// }
		// String alllist = AuthLoginName.get(AuthLoginName.getKeyWord(),
		// loginName, "findUserResourceListByLoginNameNoLazy");
		// if (StringUtils.isBlank(alllist)) {
		// throw new BusinessException("权限信息不存在，请重试。");
		// }
		// TbAuthResourceBean bean = JSON.parseObject(alllist,
		// TbAuthResourceBean.class);
		//
		// Menus menus = encapMenus(request.getContextPath(),
		// bean.getChildren());
		// String jsonMenus = JSON.toJSONString(menus);
		// if (userMap != null && !userMap.isEmpty()) {
		// HttpSession session = request.getSession();
		// session.setAttribute(SessionUtils.SESSION_USER_KEY, userMap);
		// session.setAttribute(SessionUtils.SESSION_RESOURCE_KEY, bean);
		// session.setAttribute(SessionUtils.MENUS, jsonMenus);
		//
		// String other = AuthLoginName.get(loginName, "getUserOrgOrRole");
		//
		// JSONObject jsonObject = JSON.parseObject(other);
		// List<HashMap<String, Object>> roles = (List<HashMap<String, Object>>)
		// jsonObject
		// .get("role");
		// JSONObject js = (JSONObject) jsonObject.get("org");
		// HashMap<String, Object> orgs = new HashMap<String, Object>();
		// orgs.put("unitid", js.getLong("unitid"));
		// orgs.put("unitname", js.getString("unitname"));
		// orgs.put("unitcode", js.getString("unitcode"));
		//
		// session.setAttribute(SessionUtils.ROLES, roles);
		// session.setAttribute(SessionUtils.UNIT, orgs);
		//
		// }
		//
		// if (menus.getBasic().isEmpty()) {
		// throw new BusinessException("权限信息不存在，请重试。");
		// }

		String dbuser = PropertiesHandle.getInstance().getString("user");
		String dbuserName = PropertiesHandle.getInstance()
				.getString("userName");
		String dbpwd = PropertiesHandle.getInstance().getString("pwd");
		if (!dbuser.equalsIgnoreCase(loginName)) {
			throw new BusinessException("用户名称错误");
		}
		if (!dbpwd.equalsIgnoreCase(MD5.md5(passWord))) {
			throw new BusinessException("密码错误");
		}

		HashMap<String, Object> user = new HashMap<String, Object>();
		user.put("USERID", dbuser);
		user.put("USERNAME", dbuserName);

		String menuString = FileUtils.readFileToString(new File(request
				.getRealPath("/") + "/json/menu.json"));
		HttpSession session = request.getSession();
		session.setAttribute(SessionUtils.SESSION_USER_KEY, user);
		session.setAttribute(SessionUtils.MENUS, menuString);
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ModelAndView view = new ModelAndView("index");
		return view;
	}

	/**
	 * 登出
	 * */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().removeAttribute(SessionUtils.SESSION_USER_KEY);
		response.sendRedirect("login.jsp");
	}

	/**
	 * 登出
	 * */
	// @RequestMapping(value = "/modifPassword")
	// public void modifPassword(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// String password = request.getParameter("newpass");
	// HashMap<String, Object> sessionUser = SessionUtils.getUser(request
	// .getSession());
	// TbAuthUserBean bean = new TbAuthUserBean();
	// bean.setUserid(sessionUser.get("USERID") == null ? "" : sessionUser
	// .get("USERID").toString());
	// bean.setUsername(sessionUser.get("USERNAME") == null ? "" : sessionUser
	// .get("USERNAME").toString());
	// bean.setComments(sessionUser.get("COMMENTS") == null ? "" : sessionUser
	// .get("COMMENTS").toString());
	// bean.setEmail(sessionUser.get("EMAIL") == null ? "" : sessionUser.get(
	// "EMAIL").toString());
	// bean.setLoginname(sessionUser.get("LOGINNAME") == null ? ""
	// : sessionUser.get("LOGINNAME").toString());
	// bean.setMobilephone(sessionUser.get("MOBILEPHONE") == null ? ""
	// : sessionUser.get("MOBILEPHONE").toString());
	// bean.setPsw(MD5.md5(password).toLowerCase());
	// bean.setTelephone(sessionUser.get("TELEPHONE") == null ? ""
	// : sessionUser.get("TELEPHONE").toString());
	// bean.setYsgh(sessionUser.get("YSGH") == null ? "" : sessionUser.get(
	// "YSGH").toString());
	// AuthLoginName.save(JSON.toJSONString(bean), "modifPassword");
	// }

	// private Menus encapMenus(String contentPath, List<TbAuthResourceBean>
	// beans)
	// throws IOException, DocumentException {
	// Menus menus = new Menus();
	// ArrayList<Menu> basic = new ArrayList<Menu>();
	// for (TbAuthResourceBean bean : beans) {
	// Menu menu = new Menu();
	// menu.setMenuid(bean.getResid());
	// menu.setMenuname(bean.getResname());
	// menu.setIcon("icon-nav");
	// menu.setUrl(contentPath + bean.getUrl());
	// basic.add(menu);
	//
	// children(contentPath, menu, bean.getChildren());
	// }
	// menus.setBasic(basic);
	// return menus;
	// }

	// private void children(String contentPath, Menu parent,
	// List<TbAuthResourceBean> beans) {
	// ArrayList<Menu> menus = new ArrayList<Menu>();
	// for (TbAuthResourceBean bean : beans) {
	// Menu menu = new Menu();
	// menu.setMenuid(bean.getResid());
	// menu.setMenuname(bean.getResname());
	// menu.setIcon("icon-nav");
	// menu.setUrl(contentPath + bean.getUrl());
	// menus.add(menu);
	// }
	// parent.setMenus(menus);
	// children(contentPath, menus, beans);
	// }

	// private void children(String contentPath, List<Menu> parent,
	// List<TbAuthResourceBean> beans) {
	// for (Menu menu : parent) {
	// ArrayList<Menu> menus = new ArrayList<Menu>();
	// for (TbAuthResourceBean bean : beans) {
	// if (bean.getPresid().equals(menu.getMenuid())) {
	// children(contentPath, menu, beans);
	// } else {
	// Menu m = new Menu();
	// m.setMenuid(bean.getResid());
	// m.setMenuname(bean.getResname());
	// m.setIcon("icon-nav");
	// m.setUrl(bean.getUrl());
	// menus.add(m);
	// }
	// }
	// }
	// }
}
