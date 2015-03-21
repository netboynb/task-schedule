package com.netboy.http.client;

import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.netboy.http.handler.JsonRspHandler;
import com.netboy.http.handler.StrRspHandler;
import com.netboy.http.handler.XmlRspHandler;

public class ThreadLocalHttpClientBuilder {

	private static RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectTimeout(20000)
			.setSocketTimeout(20000);

	private static ConnectionConfig.Builder connectionConfigBuilder = ConnectionConfig.custom()
			.setCharset(Consts.UTF_8);

	/**
	 * 线程隔离
	 */
	private static ThreadLocal<CloseableHttpClient> clientHolder = new ThreadLocal<CloseableHttpClient>() {
		@Override
		protected CloseableHttpClient initialValue() {
			return createClient();
		}
	};

	public static CloseableHttpClient getHttpClient() {
		return clientHolder.get();
	}

	private static CloseableHttpClient createClient() {
		org.apache.http.impl.client.HttpClientBuilder clientBuilder = HttpClients.custom();
		clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
		clientBuilder.setDefaultConnectionConfig(connectionConfigBuilder.build());
		return clientBuilder.build();
	}

	public static void setConnectionConfigBuilder(ConnectionConfig.Builder connectionConfigBuilder) {
		ThreadLocalHttpClientBuilder.connectionConfigBuilder = connectionConfigBuilder;
	}

	public static void setRequestConfigBuilder(RequestConfig.Builder requestConfigBuilder) {
		ThreadLocalHttpClientBuilder.requestConfigBuilder = requestConfigBuilder;
	}

	/**
	 * josn 格式返回结果
	 */
	protected Object jsonReq(HttpRequestBase request, JsonRspHandler handler) throws Exception {
		CloseableHttpClient client = getHttpClient();
		return client.execute(request, handler);
	}

	/**
	 * xml 格式返回结果
	 */
	protected Object xmlReq(HttpRequestBase request, XmlRspHandler handler) throws Exception {
		CloseableHttpClient client = getHttpClient();
		return client.execute(request, handler);
	}

	/**
	 * string 格式返回结果
	 */
	protected Object strReq(HttpRequestBase request, StrRspHandler handler) throws Exception {
		CloseableHttpClient client = getHttpClient();
		return client.execute(request, handler);
	}

}
