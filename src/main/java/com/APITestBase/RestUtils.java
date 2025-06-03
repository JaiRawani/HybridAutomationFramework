package com.APITestBase;


import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class RestUtils {
	
	//private static final Logger logger = LoggerUtils.getLogger(RestUtils.class);
	/**
	 * 
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param jsonBody
	 * @return
	 */
	public ResponseHolder postRequest(String url,Map<String,String> headers,Map<String , String> cookies , Map<String , String> queryParams,String jsonBody ) throws Exception {
		String callingMethodName=Thread.currentThread().getStackTrace()[2].getMethodName();

		ResponseHolder responseHolder=new ResponseHolder();

		RestAssured.baseURI=url;
		RestAssured.useRelaxedHTTPSValidation();
		//logger.info("POST URL for "+callingMethodName+" --> {} "+url);

		RequestSpecification requestSpicification=RestAssured.given(); 

		if(headers!=null)
		{
			requestSpicification.headers(headers);
		//logger.info("POST Request headers for "+callingMethodName+" --> {} "+headers);
		}

		if(cookies!=null) {
			requestSpicification.cookies(cookies);
		//logger.info("POST Request cookies for "+callingMethodName+" --> {} "+cookies);
		}
		if(queryParams!=null) {
//			requestSpicification.params(params);
			requestSpicification.queryParams(queryParams);
			//logger.info("POST Request params for "+callingMethodName+" --> {} "+queryParams);
		}




		if(jsonBody!=null) {
			requestSpicification.body(jsonBody);
			//logger.info("POST RequestBody for "+callingMethodName+" --> {} "+jsonBody);
		}
		Response	response=	requestSpicification.request(Method.POST);


		if(response.statusCode()==500)
		{
			//logger.error(("ResponseCode {}"+response.statusCode());
			throw new Exception("Getting 500 status code. Host URL : "+url+" \n \n Response Body  "+ response.asString()+" \n \n Headers : "+headers);
		}
		else
			//logger.info("ResponseCode {}"+response.statusCode());

		//logger.info("POST Response for "+callingMethodName+" --> {} "+response.asString());

		responseHolder.setRequestBody(jsonBody);
		responseHolder.setResponse(response);

		return responseHolder;
	}
	/**
	 * 
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param params
	 * @return
	 */
	public ResponseHolder getRequest(String url,Map<String,String> headers,Map<String , String> cookies,Map<String,String> params) throws Exception {
		String callingMethodName=Thread.currentThread().getStackTrace()[2].getMethodName();
		ResponseHolder responseHolder=new ResponseHolder();
		RestAssured.baseURI=url;
		//logger.info("GET URL for "+callingMethodName+" --> {} "+url);		
		RestAssured.useRelaxedHTTPSValidation();
		RequestSpecification requestSpicification=RestAssured.given(); 

		if(headers!=null)
		{
			requestSpicification.headers(headers);
			//logger.info("GET Request headers for "+callingMethodName+"--> {} "+headers);
		}
		if(cookies!=null) {
			requestSpicification.cookies(cookies);
			//logger.info("GET Request cookies for "+callingMethodName+"--> {} "+cookies);
		}

		if(params!=null) {
			requestSpicification.params(params);
			//logger.info("GET Request Params for "+callingMethodName+"--> {} "+params);
		}

		Response	response =	requestSpicification.request(Method.GET);
		responseHolder.setResponse(response);

		if(response.statusCode()==500)
		{
			//logger.error(("ResponseCode {}"+response.statusCode());
			throw new Exception("Getting 500 status code. Host URL : "+url+" \n \n Response Body  "+ response.asString()+" \n \n Headers : "+headers+ " \n \n Params : "+params);
		}
		else
			//logger.info("ResponseCode {}"+response.statusCode());

	//logger.info("GET Response for "+callingMethodName+"--> {} "+response.asString());


		return responseHolder;
	}

	/**
	 * 
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param jsonBody
	 * @return
	 */
	public ResponseHolder putRequest(String url,Map<String,String> headers,Map<String , String> cookies , String jsonBody ) throws Exception {
		String callingMethodName=Thread.currentThread().getStackTrace()[2].getMethodName();

		ResponseHolder responseHolder=new ResponseHolder();

		RestAssured.baseURI=url;
		RestAssured.useRelaxedHTTPSValidation();
		//logger.info("PUT URL for "+callingMethodName+" --> {} "+url);

		RequestSpecification requestSpicification=RestAssured.given(); 

		if(headers!=null)
		{
			requestSpicification.headers(headers);
			//logger.info("PUT Request headers for "+callingMethodName+" --> {} "+headers);
		}

		if(cookies!=null) {
			requestSpicification.cookies(cookies);
			//logger.info("PUT Request cookies for "+callingMethodName+" --> {} "+cookies);
		}

		if(jsonBody!=null) {
			requestSpicification.body(jsonBody);
			//logger.info("PUT RequestBody for "+callingMethodName+" --> {} "+jsonBody);
		}
		Response	response=	requestSpicification.request(Method.PUT);


		if(response.statusCode()==500)
		{
			//logger.error(("ResponseCode {}"+response.statusCode());
			throw new Exception("Getting 500 status code.  Host URL : "+url+" \n \n Response Body  "+ response.asString()+" \n \n Headers : "+headers);
		}
		else
			//logger.info("ResponseCode {}"+response.statusCode());

		//logger.info("PUT Response for "+callingMethodName+" --> {} "+response.asString());

		responseHolder.setRequestBody(jsonBody);
		responseHolder.setResponse(response);

		return responseHolder;
	}
	
	
	/**
	 * 
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param jsonBody
	 * @return
	 */
	public ResponseHolder putRequest(String url,Map<String,String> headers,Map<String , String> cookies ,Map<String,String> params, String jsonBody) throws Exception {
		String callingMethodName=Thread.currentThread().getStackTrace()[2].getMethodName();

		ResponseHolder responseHolder=new ResponseHolder();

		RestAssured.baseURI=url;
		RestAssured.useRelaxedHTTPSValidation();
		//logger.info("PUT URL for "+callingMethodName+" --> {} "+url);

		RequestSpecification requestSpicification=RestAssured.given(); 

		if(headers!=null)
		{
			requestSpicification.headers(headers);
			//logger.info("PUT Request headers for "+callingMethodName+" --> {} "+headers);
		}

		if(cookies!=null) {
			requestSpicification.cookies(cookies);
			//logger.info("PUT Request cookies for "+callingMethodName+" --> {} "+cookies);
		}
		
		if(params!=null) {
			requestSpicification.params(params);
			//logger.info("GET Request Params for "+callingMethodName+"--> {} "+params);
		}

		if(jsonBody!=null) {
			requestSpicification.body(jsonBody);
			//logger.info("PUT RequestBody for "+callingMethodName+" --> {} "+jsonBody);
		}
		Response	response=	requestSpicification.request(Method.PUT);


		if(response.statusCode()==500)
		{
			//logger.error(("ResponseCode {}"+response.statusCode());
			throw new Exception("Getting 500 status code. Host URL : "+url+" \n \n Response Body  "+ response.asString()+" \n \n Headers : "+headers);
		}
		else
			//logger.info("ResponseCode {}"+response.statusCode());

		//logger.info("PUT Response for "+callingMethodName+" --> {} "+response.asString());

		responseHolder.setRequestBody(jsonBody);
		responseHolder.setResponse(response);

		return responseHolder;
	}



	public static void main(String ...s) {
		//getPickListDetails("storeNumber"+"28410215331647745");
		//getPickList("storeNumber"+ "94");
		//String updatePL="";

		//updatePickList("storeNumber"+ "5585-3-1566406800-1566417600"+ updatePL);
	}


	public ResponseHolder getRequest(RequestSpecification requestSpecification) throws Exception {
		String callingMethodName=Thread.currentThread().getStackTrace()[2].getMethodName();
		ResponseHolder responseHolder=new ResponseHolder();
		RestAssured.useRelaxedHTTPSValidation();

		RequestSpecification requestSpecificationm = RestAssured.given();

		Response	response =	requestSpecificationm.request(Method.GET);
		responseHolder.setResponse(response);

		if(response.statusCode()==500)
		{
			//logger.error(("ResponseCode {}"+response.statusCode());
			throw new Exception("Getting 500 status code. Host URL : "+response.getBody());
		}
		else
			//logger.info("ResponseCode {}"+response.statusCode());

		//logger.info("GET Response for "+callingMethodName+"--> {} "+response.asString());

		return responseHolder;
	}




}
