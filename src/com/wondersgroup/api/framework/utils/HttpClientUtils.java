package com.wondersgroup.api.framework.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

/**
 * HttpClient请求工具类
 * 
 *
 */
public class HttpClientUtils {

	// 请求超时时间 30秒
	private static final int TIME_OUT = 30000;

	// 请求失败返回信息
	private static final String REQUEST_FAILED_MESSAGE = "error";

	/**
	 * Post请求
	 * 
	 * @param url
	 * @param param
	 *            请求参数
	 * @return 服务返回信息
	 */
	public static String doPost(String url, Map<String, Object> param,
			HashMap<String, String> header) {
		StringBuilder sb = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					TIME_OUT);
			HttpPost post = new HttpPost(url);
			if (header != null && !header.isEmpty()) {
				for (Iterator<Entry<String, String>> it = header.entrySet()
						.iterator(); it.hasNext();) {
					Entry<String, String> entry = it.next();
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			if (param != null && param.size() > 0) {
				List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(
						param.size());
				Set<String> keys = param.keySet();
				for (Object o : keys) {
					String key = (String) o;
					nameValuePairs.add(new BasicNameValuePair(key, String
							.valueOf(param.get(key))));
				}
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			}
			HttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				sb = new StringBuilder();
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(instream));
					String tempLine;
					while ((tempLine = br.readLine()) != null) {
						sb.append(tempLine);
					}
				}
			}
			post.abort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * delete请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doDelete(String url, HashMap<String, String> header) {
		StringBuilder sb = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					TIME_OUT);

			// HttpGet
			HttpDelete get = new HttpDelete(url);
			if (header != null && !header.isEmpty()) {
				for (Iterator<Entry<String, String>> it = header.entrySet()
						.iterator(); it.hasNext();) {
					Entry<String, String> entry = it.next();
					get.addHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse response = client.execute(get);
			StatusLine state = response.getStatusLine();
			if (state.getStatusCode() == HttpStatus.SC_OK) {

				sb = new StringBuilder();

				HttpEntity eneity = response.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						eneity.getContent()));
				String content;
				while ((content = br.readLine()) != null) {
					sb.append(content);
				}
			}
			get.abort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Get请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url, HashMap<String, String> header) {
		StringBuilder sb = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					TIME_OUT);

			// HttpGet
			HttpGet get = new HttpGet(url);
			if (header != null && !header.isEmpty()) {
				for (Iterator<Entry<String, String>> it = header.entrySet()
						.iterator(); it.hasNext();) {
					Entry<String, String> entry = it.next();
					get.addHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse response = client.execute(get);
			StatusLine state = response.getStatusLine();
			if (state.getStatusCode() == HttpStatus.SC_OK) {

				sb = new StringBuilder();

				HttpEntity eneity = response.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						eneity.getContent()));
				String content;
				while ((content = br.readLine()) != null) {
					sb.append(content);
				}
			}
			get.abort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * put请求
	 * 
	 * @param url
	 * @return
	 */
	/**
	 * Post请求
	 * 
	 * @param url
	 * @param param
	 *            请求参数
	 * @return 服务返回信息
	 */
	public static String doPut(String url, Map<String, Object> param,
			HashMap<String, String> header) {
		StringBuilder sb = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					TIME_OUT);
			HttpPut post = new HttpPut(url);
			if (header != null && !header.isEmpty()) {
				for (Iterator<Entry<String, String>> it = header.entrySet()
						.iterator(); it.hasNext();) {
					Entry<String, String> entry = it.next();
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			if (param != null && param.size() > 0) {
				List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(
						param.size());
				Set<String> keys = param.keySet();
				for (Object o : keys) {
					String key = (String) o;
					nameValuePairs.add(new BasicNameValuePair(key, String
							.valueOf(param.get(key))));
				}
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			}
			HttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				sb = new StringBuilder();
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(instream));
					String tempLine;
					while ((tempLine = br.readLine()) != null) {
						sb.append(tempLine);
					}
				}
			}
			post.abort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("username", "wangyongcheng");
		params.put("password", "wangyongcheng");

		HashMap<String, String> header = new HashMap<String, String>();
		header.put("X-APICloud-AppId", "A6916094041457");
		header.put("X-APICloud-AppKey",
				"d31c0d99f8387cb53ce65fa87c060260d6839473.1461565230326");
		header.put("authorization",
				"FRoUZWhR9fOBZ0QjKC7ZHZ19zsraMXhF188RqnvTec9eKj25GntaDBxJSzAsN9TX");

		String url = Encodes.urlEncode("{\"where\":{\"name\":\"ggggggg\"}}");
		String aa = HttpClientUtils.doPost(
				"https://d.apicloud.com/mcm/api/User/logout", null, header);
		System.out.println(aa);
	}
}
