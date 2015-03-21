package com.netboy.http.handler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class StrRspHandler implements ResponseHandler<String> {
	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int status = response.getStatusLine().getStatusCode();
		String result = null;
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			result = EntityUtils.toString(response.getEntity());
		} else {
			throw new ClientProtocolException("请求异常，状态码 [ " + status+" ]");
		}

		return result;
	}
}
