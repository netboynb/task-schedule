package com.netboy.scheduler.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.jboss.com.sun.net.httpserver.HttpExchange;
import org.jboss.com.sun.net.httpserver.HttpHandler;
import org.jboss.com.sun.net.httpserver.HttpServer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestHttpServer {
	public static void main(String[] args) {
		try {
			HttpServer hs = HttpServer.create(new InetSocketAddress(7777), 0);
			hs.createContext("/merge", new MergeHandler());
			hs.createContext("/optDone", new OptDoneHandler());
			hs.setExecutor(null);
			hs.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MergeHandler implements HttpHandler {
	public void handle(HttpExchange t) throws IOException {
		InputStream is = t.getRequestBody();
		byte[] temp = new byte[is.available()];
		is.read(temp);
		String msg = new String(temp);
		
		JsonObject json = new JsonParser().parse(msg).getAsJsonObject();
		json.addProperty("success", "true");
		String response = json.toString();
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}

class OptDoneHandler implements HttpHandler {
	public void handle(HttpExchange t) throws IOException {
		System.out.println(t.getRequestURI().toString());
		InputStream is = t.getRequestBody();
		byte[] temp = new byte[is.available()];
		is.read(temp);
		System.out.println(new String(temp));
		String response = "optdone";
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
