package com.netboy.http.client;

import junit.framework.TestCase;

import org.junit.Before;

import com.google.gson.JsonObject;

public class SimpleHttpClientTest extends TestCase {
	private SimpleHttpClient client;
	private HttpUrl httpUrl;

	@Before
	public void setUp() {
		client = new SimpleHttpClient();
		httpUrl = new HttpUrl("127.0.0.1:7777");
	}

	public void testJsonExecute() throws Exception {
		// test merge
		httpUrl.setPath("/merge");
		JsonObject json = new JsonObject();
		json.addProperty("ip_port", "127.0.0.1:9999");
		httpUrl.setParmJson(json);
		JsonObject result = (JsonObject) client.jsonExecute(httpUrl);
		System.out.println(result.toString());
	}

	public void testXmlExcute() {
		fail("Not yet implemented");
	}

	public void testStrExcute() {
		fail("Not yet implemented");
	}

}
