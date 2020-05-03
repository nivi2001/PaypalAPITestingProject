package com.paypal.APITesting.testcases;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.eclipse.jetty.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.paypal.APIFramework.setUp.BaseTest;
import com.paypal.APITesting.APIs.OrderAPI;
import com.paypal.APITesting.pojo.Orders;
import com.paypal.APITesting.pojo.PurchaseUnits;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;


public class CreateOrdersTest extends BaseTest {
	static String access_token;
	static String client_id="AecwRFlxZfpKSRM-HvoIpkUOp1C-FG1Boa27hZetfxNDCHUokmsBqxrM2_xWgw89B2RTHmHreWF8fZ9X";
	static String secret = "ENzml_OTRRzdvjwDyAowg1eb1j9_qu77YNJvvvxOwq1eODvjaFafCGyrdMxNBVqJGbvsXDPUqmevT3zf";
	static String orderId;
	@Test(priority=1)
	public void getAuthKey() {
		//RestAssured.basePath = config.getProperty("basePath");
		Response response = 	given().param("grant_type", "client_credentials").auth().preemptive()
			                           .basic(client_id,secret).post("/v1/oauth2/token");
	                            response.prettyPrint();
	  access_token = response.jsonPath().get("access_token");
	  System.out.println(access_token);
	}
	
	@Test(priority=2, dependsOnMethods="getAuthKey")
	public void ValidateValidRequestForCreatingAnOrder() {
		
		 ArrayList<PurchaseUnits> list= new  ArrayList<PurchaseUnits>();
	      list.add(new PurchaseUnits("USD", "500"));
	      Orders order = new Orders("CAPTURE",list);
			
		     /* String jsonBody = "{\r\n" + 
		      		"  \"intent\": \"CAPTURE\",\r\n" + 
		      		"  \"purchase_units\": [\r\n" + 
		      		"    {\r\n" + 
		      		"      \"amount\": {\r\n" + 
		      		"        \"currency_code\": \"USD\",\r\n" + 
		      		"        \"value\": \"100.00\"\r\n" + 
		      		"      }\r\n" + 
		      		"    }\r\n" + 
		      		"  ]\r\n" + 
		      		"}";*/
			 
			 Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(order).post("/v2/checkout/orders"); 
			 response.prettyPrint();
			 System.out.println(response);
			 orderId = response.jsonPath().get("id").toString();
			 Assert.assertEquals(response.statusCode(), 201);
			 
			 /*
			 Assert.assertEquals(response.jsonPath().get("status"), "CREATED");
			 
			 //Check that id is not null.
			 Assert.assertEquals(response.jsonPath().get("currency_code"), "USD");*/
			 Assert.assertEquals(orderId.length(), 17);
			 
		    response.then()
		     .body("status", equalTo("CREATED"))
		     .body("links[0].rel", equalTo("self"));
		     
	}
		    @Test(priority=3, dependsOnMethods="getAuthKey")
			public void ValidateErrorMessageWithInvalidCurrencyCode() { 
		    	 ArrayList<PurchaseUnits> list= new  ArrayList<PurchaseUnits>();
			      list.add(new PurchaseUnits("USD", "ABC"));
			      Orders order = new Orders("CAPTURE",list);
		    	/* String jsonBody = "{\r\n" + 
				      		"  \"intent\": \"CAPTURE\",\r\n" + 
				      		"  \"purchase_units\": [\r\n" + 
				      		"    {\r\n" + 
				      		"      \"amount\": {\r\n" + 
				      		"        \"currency_code\": \"USD\",\r\n" + 
				      		"        \"value\": \"ABC.00\"\r\n" + 
				      		"      }\r\n" + 
				      		"    }\r\n" + 
				      		"  ]\r\n" + 
				      		"}";*/
			      
			      Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(order).post("/v2/checkout/orders"); 
				  response.prettyPrint();
				  Assert.assertEquals(response.statusCode(),  400);
			  response.then()
			    .body("name", equalTo("INVALID_REQUEST"))
			    .body("details[0].field", equalTo("/purchase_units/0/amount/value"))
			    .body("details[0].issue", equalTo("INVALID_PARAMETER_SYNTAX"))
			    .body("details[0].description", equalTo("The value of a field does not conform to the expected format."))
			    .body("message", equalTo("Request is not well-formed, syntactically incorrect, or violates schema."))
			    .body("links[0].rel", equalTo("information_link"));
			  
			    
			  
			    
		    
		 
		 
		
		 
		        
	
		 
			
	}

	
}


	


