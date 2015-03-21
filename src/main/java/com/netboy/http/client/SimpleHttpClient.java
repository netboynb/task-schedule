package com.netboy.http.client;

import com.netboy.http.handler.JsonRspHandler;
import com.netboy.http.handler.StrRspHandler;
import com.netboy.http.handler.XmlRspHandler;


public class SimpleHttpClient extends ThreadLocalHttpClientBuilder {
	/**
	 * json 格式返回
	 */
	public Object jsonExecute(HttpUrl httpUrl) throws Exception {
		return jsonReq(httpUrl.build(),new JsonRspHandler());
	}

	/**
	 * xml格式返回
	 */
	public Object xmlExcute(HttpUrl httpUrl) throws Exception {
		return xmlReq(httpUrl.build(),new XmlRspHandler());
	}

	/**
	 * 字符串格式返回
	 */
	public Object strExcute(HttpUrl httpUrl) throws Exception {
		return strReq(httpUrl.build(),new StrRspHandler());
	}
}
