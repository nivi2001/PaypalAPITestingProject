package com.paypal.APITesting.DDTTestScripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;


import com.paypal.APITesting.pojo.Orders;
import com.paypal.APITesting.pojo.PurchaseUnits;
import com.paypal.APITesting.utilities.DataUtil;
import com.paypal.APITesting.utilities.TestParametrization;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.paypal.APIFramework.setUp.BaseTest;

public class CreateOrders extends BaseTest {
	static String access_token;
	static String client_id="AecwRFlxZfpKSRM-HvoIpkUOp1C-FG1Boa27hZetfxNDCHUokmsBqxrM2_xWgw89B2RTHmHreWF8fZ9X";
	static String secret = "ENzml_OTRRzdvjwDyAowg1eb1j9_qu77YNJvvvxOwq1eODvjaFafCGyrdMxNBVqJGbvsXDPUqmevT3zf";
	static String orderId;
	@Test(priority=1)
	public void getAuthKey() {
	Response response = 	given().param("grant_type", "client_credentials").auth().preemptive()
			                           .basic(client_id,secret).post("/v1/oauth2/token");
	                            response.prettyPrint();
	  access_token = response.jsonPath().get("access_token");
	  System.out.println(access_token);
	}
	
	
	@Test(priority=2,dataProviderClass=DataUtil.class,dataProvider = "data-provider", dependsOnMethods="getAuthKey")
	//@Test(priority=2,dataProviderClass=TestParametrization.class,dataProvider = "getData", dependsOnMethods="getAuthKey")
	  
	   public void ValidateCreatingAnOrder(String currency_code, String currency_value, String status) {
		/* Step 1 : Create class for creating an order */
		/* Step 2 : Create an array list for purchase units.*/
		ArrayList<PurchaseUnits> list= new  ArrayList<PurchaseUnits>();
		/* Step3 : Add currency code and amount in the list*/
	     list.add(new PurchaseUnits(currency_code, currency_value));
	     /*Step 4 : */ 
	     Orders order = new Orders("CAPTURE",list);
	     Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(order).post("/v2/checkout/orders"); 
		 response.prettyPrint();
		 System.out.println(response);
		 orderId = response.jsonPath().get("id").toString();
		 int i=Integer.parseInt(status);
	//	 Assert.assertEquals(response.statusCode(),i);
		 System.out.println("Status code is "+response.getStatusCode());
		// int statusCode = Integer.parseInt(status);
		
		 
		 Assert.assertEquals(response.jsonPath().get("status"), "CREATED");
		 //Check that id is not null.
		 Assert.assertEquals(response.jsonPath().get("currency_code"), "USD");
		 Assert.assertEquals(orderId.length(), 17);
		 response.then()
		 .body("status", equalTo("CREATED"))
		 .body("links[0].rel", equalTo("self"));
		     
	}
		  @Test(priority=3, dataProviderClass=DataUtil.class,dataProvider = "data-provider", dependsOnMethods="getAuthKey")
			public void ValidateErrorMessageWithInvalidCurrencyCode(String currency_code, String currency_value, String status) { 
		    	 ArrayList<PurchaseUnits> list= new  ArrayList<PurchaseUnits>();
			      list.add(new PurchaseUnits(currency_code, currency_value));
			      Orders order = new Orders("CAPTURE",list);
		    	
			      
			      Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(order).post("/v2/checkout/orders"); 
				  response.prettyPrint();
				  Assert.assertEquals(response.statusCode(),  status);
			  response.then()
			    .body("name", equalTo("INVALID_REQUEST"))
			    .body("details[0].field", equalTo("/purchase_units/0/amount/value"))
			    .body("details[0].issue", equalTo("INVALID_PARAMETER_SYNTAX"))
			    .body("details[0].description", equalTo("The value of a field does not conform to the expected format."))
			    .body("message", equalTo("Request is not well-formed, syntactically incorrect, or violates schema."))
			    .body("links[0].rel", equalTo("information_link"));
			  		
	}

	
}


