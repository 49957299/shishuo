package com.wondersgroup.api.web.bean;

import java.util.HashMap;
import java.util.List;

import com.wondersgroup.api.framework.enums.Order;
import com.wondersgroup.api.framework.page.Page;

/**
 * 请求参数
 * 
 * @author yc
 *
 */
public class Request {

	private List<String> coloum;// 查询显示列
	private HashMap<String, Object> data;// 数据
	private List<Parameter> where;// 条件参数
	private Order order;// 排序
	private Page page = new Page();

	public List<String> getColoum() {
		return coloum;
	}

	public void setColoum(List<String> coloum) {
		this.coloum = coloum;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}

	public List<Parameter> getWhere() {
		return where;
	}

	public void setWhere(List<Parameter> where) {
		this.where = where;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
