package com.paypal.APITesting.testcases;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.paypal.APIFramework.setUp.BaseTest;
import com.paypal.APITesting.pojo.CreateProduct;
import com.paypal.APITesting.utilities.DataUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateProductTest extends BaseTest {

	static String access_token;
	static String client_id="AecwRFlxZfpKSRM-HvoIpkUOp1C-FG1Boa27hZetfxNDCHUokmsBqxrM2_xWgw89B2RTHmHreWF8fZ9X";
	static String secret = "ENzml_OTRRzdvjwDyAowg1eb1j9_qu77YNJvvvxOwq1eODvjaFafCGyrdMxNBVqJGbvsXDPUqmevT3zf";
	static String productID;
	

	@Test
	public void getAuthKey() {
		//RestAssured.baseURI = "https://api.sandbox.paypal.com";
		Response response = 	given().param("grant_type", "client_credentials").auth().preemptive()
			.basic(client_id,secret).post("/oauth2/token");
	   response.prettyPrint();
	  access_token = response.jsonPath().get("access_token");
	  System.out.println(access_token);
	}
	
	
	@Test(priority=1, dependsOnMethods="getAuthKey")
    public void validateCreateProductAPIWithValidSecretKey() {
	CreateProduct createproduct = new CreateProduct("Video Streaming Service", "Video streaming service", "SERVICE", "SOFTWARE", "https://example.com/streaming.jpg", "https://example.com/home");
	Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(createproduct).post(config.getProperty("ProductAPIEndPoint"));
	response.prettyPrint();
	System.out.println(response.statusCode());
	Assert.assertEquals(response.statusCode(), 201);
	//Assert.assertEquals(response.getHeader(), expected);
	
	
		
	}
	@Test(priority=2, dependsOnMethods="getAuthKey")
	public void validateCreateProductAPIWithInvalidSecretKey() {
		CreateProduct createproduct = new CreateProduct("Video Streaming Service", "Video streaming service", "SERVICE", "SOFTWARE", "https://example.com/streaming.jpg", "https://example.com/home");
		Response response = given().contentType(ContentType.JSON).auth().basic(config.getProperty("invalidSecretKey"), access_token).log().everything().body(createproduct).post(config.getProperty("ProductAPIEndPoint"));
		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 401);
			
	}
	
	@Test(priority=3, dependsOnMethods="getAuthKey")
	public void validatevalidHTTPrequest () {
	    
	    Header authorizationHeader = new Header("Authorization", "Bearer getAuthKey");
	    RequestSpecification httpRequest = RestAssured.given();
	    httpRequest.header(authorizationHeader);
	    Response response = httpRequest.post(config.getProperty("ProductAPIEndPoint"));
	    Assert.assertEquals(401, response.getStatusCode());
	}
	
	@Test(priority=4, dependsOnMethods="getAuthKey")
	public void validatethatvalidtestvalues  () {
		CreateProduct createproduct = new CreateProduct("Video Streaming Service", "Video streaming service", "SERVICE", "SOFTWARE", "https://example.com/streaming.jpg", "https://example.com/home");
		Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(createproduct).post(config.getProperty("ProductAPIEndPoint"));
		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 201);
		
		
		
	}
	}
	    
	



