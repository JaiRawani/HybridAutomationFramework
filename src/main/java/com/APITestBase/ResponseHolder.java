package com.APITestBase;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

public class ResponseHolder {

	private String responseCode;
	private String requestBody;
	private String responseBody;
	private Map<String,String> responseCookies;
	private Headers responseHeaders;
	private Response response;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	public Map<String, String> getResponseCookies() {
		return responseCookies;
	}
	public void setResponseCookies(Map<String, String> responseCookies) {
		this.responseCookies = responseCookies;
	}
	public Headers getResponseHeaders() {
		return responseHeaders;
	}
	public void setResponseHeaders(Headers responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
		this.responseCode=response.getStatusCode()+"";
		this.responseBody=response.asString();
		this.responseCookies=response.getCookies();
		this.responseHeaders=response.getHeaders();
		
	}
	@Override
	public String toString() {
		return "ResponseHolder [\n responseCode=" + responseCode + ",\n requestBody=" + requestBody + ",\n responseBody="
				+ responseBody + ",\n responseCookies=" + responseCookies + ",\n responseHeaders=" + responseHeaders
				+ ",\n response=" + response + "]";
	}

	
	
	
}
