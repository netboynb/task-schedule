package com.netboy.http.handler;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.w3c.dom.Document;

public class XmlRspHandler implements ResponseHandler<Document> {

	@Override
	public Document handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int status = response.getStatusLine().getStatusCode();
		Document doc = null;
		try {
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					return null;
				}
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				doc = builder.parse(entity.getContent());
			} else {
				throw new ClientProtocolException("请求异常，状态码 [ " + status+" ]");
			}

		} catch (Exception e) {
			throw new ClientProtocolException("转换为xml格式异常" + e.toString());
		}

		return doc;
	}

}
