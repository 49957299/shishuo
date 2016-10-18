package com.wondersgroup.api.web.service;

import org.springframework.data.domain.PageImpl;

import com.wondersgroup.api.framework.base.BaseService;
import com.wondersgroup.api.web.bean.Request;

/**
 * 基本服务类
 * 
 * @author Administrator
 *
 */
public interface EngineService extends BaseService {
	public PageImpl find(Request req, String object) throws Exception;

	/**
	 * 查询所有数据记录数
	 * 
	 * @return 常量值
	 * @throws Exception
	 */
	public int count(Request req, String object) throws Exception;

	/**
	 * 保存数据 {"data":{"PHONE":"","DESIGN":"admin","TIME":"","PORT":"",
	 * "ADMINISTRATOR":"测试新增","PID":1,"IP":"","ADDRESS":"1","ID":
	 * "d2f4b829f543484b90bb9e5be1889370" ,"REMARK":"","PEOPLE":""}}
	 * 
	 * @param req
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int save(Request req, String object) throws Exception;

	public int update(Request req, String object, String ids) throws Exception;

	public int delete(String id, String object) throws Exception;

	/**
	 * {"data":{"PHONE":"","DESIGN":"admin","TIME":"","PORT":"","ADMINISTRATOR":
	 * "测试新增"
	 * ,"PID":1,"IP":"","ADDRESS":"1","ID":"4","REMARK":"","PEOPLE":""},"page"
	 * :{"pageNumber":1,"pageSize":10}}
	 * 
	 * @param ids
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int deletes(String ids, String object) throws Exception;

	public Object get(Request req, String id, String object) throws Exception;

}