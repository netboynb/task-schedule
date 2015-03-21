package com.netboy.http.handler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonRspHandler implements ResponseHandler<JsonObject> {

	@Override
	public JsonObject handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int status = response.getStatusLine().getStatusCode();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}

			return new JsonParser().parse(EntityUtils.toString(entity)).getAsJsonObject();
		} else {
			throw new ClientProtocolException("请求异常，状态码 [ " + status+" ]");
		}
	}

}
