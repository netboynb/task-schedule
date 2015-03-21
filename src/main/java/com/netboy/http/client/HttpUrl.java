package com.netboy.http.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;


public class HttpUrl implements Serializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUrl.class);
	private static final long serialVersionUID = 1L;
	private String ipPort;
	private String path;
	private JsonObject parmJson;
	private Map<String, String> parmMap;
	private RequstType requestType = RequstType.POST;
	private URIBuilder uriBuilder;

	public enum RequstType {
		GET, POST
	}

	public HttpUrl(String ipPort, String path, RequstType requstType) {
		this.ipPort = ipPort;
		this.path = path;
		this.requestType = requstType;
	}

	public HttpUrl(String ipPort, String path) {
		this.ipPort = ipPort;
		this.path = path;
	}

	public HttpUrl(String ipPort) {
		this.ipPort = ipPort;
	}

	/*
	 * 生成URI （vcloud内部用）
	 */
	public HttpRequestBase build() throws Exception {
		if (StringUtils.isBlank(path) || StringUtils.isBlank(ipPort)) {
			throw new Exception("ipPort/path 不能为空");
		}

		uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http").setHost(ipPort).setPath(path);

		// 参数填充
		if (requestType == RequstType.GET) {
			// get参数填充进uriBuilder
			if (parmMap != null) {
				for (Entry<String, String> kv : parmMap.entrySet()) {
					uriBuilder.addParameter(kv.getKey(), kv.getValue());
				}
			}
			uriBuilder.addParameter("_input_charset", "UTF-8");

			String url = uriBuilder.build().toString();
			HttpGet httpget = new HttpGet(url);
			return httpget;
		} else if (requestType == requestType.POST) {
			// post数据填充进formparams
			try {
				HttpPost httppost = new HttpPost(uriBuilder.build());
				if (parmJson != null) {
					StringEntity entity = new StringEntity(parmJson.toString());
					entity.setContentEncoding("UTF-8");
					entity.setContentType("application/json");
					httppost.setEntity(entity);
				}

				return httppost;
			} catch (Exception e) {
				LOGGER.error("post请求错误！", e);
			}
		}
		return null;
	}

	public void setGetParm(String param, String value) {
		uriBuilder.setParameter(param, value);
	}

	public void addGetParm(String param, String value) {
		if (parmMap == null) {
			parmMap = new HashMap<String, String>();
		}
		parmMap.put(param, value);
	}

	public RequstType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequstType requestType) {
		this.requestType = requestType;
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JsonObject getParmJson() {
		return parmJson;
	}

	public void setParmJson(JsonObject parmJson) {
		this.parmJson = parmJson;
	}

}
