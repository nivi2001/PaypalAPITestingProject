package com.paypal.APIrough;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;

import com.paypal.APIFramework.setUp.BaseTest;
import com.paypal.APITesting.pojo.CreateOrder;

//import com.paypal.APITesting.pojo.CreateOrder;

//import org.testng.Assert;
//import org.testng.annotations.Test;

import com.paypal.APITesting.pojo.Orders;
import com.paypal.APITesting.pojo.PurchaseUnits;

import com.paypal.APITesting.pojo.UpdateOrderTestPaypal;
import com.paypal.APITesting.pojo.Value;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

 

public class TestPaypal {
	static String access_token;
	static String client_id="AecwRFlxZfpKSRM-HvoIpkUOp1C-FG1Boa27hZetfxNDCHUokmsBqxrM2_xWgw89B2RTHmHreWF8fZ9X";
	static String secret = "ENzml_OTRRzdvjwDyAowg1eb1j9_qu77YNJvvvxOwq1eODvjaFafCGyrdMxNBVqJGbvsXDPUqmevT3zf";
	static String orderId;
	static String referenceId;

	@Test(priority=1)
	public void getAuthKey() {
		/*CreateOrder createorder = new CreateOrder("AecwRFlxZfpKSRM-HvoIpkUOp1C-FG1Boa27hZetfxNDCHUokmsBqxrM2_xWgw89B2RTHmHreWF8fZ9X", null, "ENzml_OTRRzdvjwDyAowg1eb1j9_qu77YNJvvvxOwq1eODvjaFafCGyrdMxNBVqJGbvsXDPUqmevT3zf", null, null);
		createorder.setClient_id("AecwRFlxZfpKSRM-HvoIpkUOp1C-FG1Boa27hZetfxNDCHUokmsBqxrM2_xWgw89B2RTHmHreWF8fZ9X");
		createorder.setSecret("ENzml_OTRRzdvjwDyAowg1eb1j9_qu77YNJvvvxOwq1eODvjaFafCGyrdMxNBVqJGbvsXDPUqmevT3zf");*/
		
		RestAssured.baseURI = "https://api.sandbox.paypal.com";
	Response response = 	given().param("grant_type", "client_credentials").auth().preemptive()
		.basic(client_id,secret).post("/v1/oauth2/token");
   response.prettyPrint();
  access_token = response.jsonPath().get("access_token");
  System.out.println(access_token);
		
	}
@Test(priority=2, dependsOnMethods="getAuthKey")
public void createOrder() {
	
	ArrayList<PurchaseUnits> list = new ArrayList<PurchaseUnits>();
	list.add(new PurchaseUnits("USD", "500"));
	Orders order = new Orders("CAPTURE", list);
	
	/*String jsonBody = "{\r\n" + 
			"  \"intent\": \"CAPTURE\",\r\n" + 
			"  \"purchase_units\": [\r\n" + 
			"    {\r\n" + 
			"      \"reference_id\": \"PUHF\",\r\n" + 
			"      \"amount\": {\r\n" + 
			"        \"currency_code\": \"USD\",\r\n" + 
			"        \"value\": \"150.00\"\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"  ]\r\n" + 
			"}";*/
	RestAssured.baseURI = "https://api.sandbox.paypal.com";
	Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).body(order).log().everything().post("v2/checkout/orders ");
	response.prettyPrint();
	orderId =	response.jsonPath().get("id").toString();
	AssertJUnit.assertEquals(response.jsonPath().get("status"), "CREATED");
}

@Test(priority=4, dependsOnMethods= {"getAuthKey","createOrder"})
public void getOrder() {
	
	System.out.println("----Getting the ORDER-----");
	System.out.println(orderId);
	RestAssured.baseURI = "https://api.sandbox.paypal.com";
	Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).get("v2/checkout/orders/"+  orderId);
	response.prettyPrint();
//	referenceId = response.jsonPath().get("reference_id").toString();
	//Assert.assertEquals(response.getStatusCode(),200);
	
	
}
@Test(priority=3, dependsOnMethods= {"getAuthKey","createOrder"})
public void updateOrder() {
	System.out.println("Update order");
	Value value = new Value("USD", "170.00");
//	ArrayList<Value> list1 = new ArrayList<Value>();
//	list1.add(new Value("USD", "170.00"));
	//System.out.println(list.get(0));
	ArrayList<UpdateOrderTestPaypal> updateorder = new ArrayList<UpdateOrderTestPaypal>();
	//updateorder.add(new UpdateOrder("replace", "/purchase_units/@reference_id=='default'/amount", value));
	updateorder.add(new UpdateOrderTestPaypal("replace", "/purchase_units/@reference_id=='default'/amount", value));
	
	/*String jsonUpdate ="[{\r\n" +
			"\"op\": \"replace\",\r\n" + 
			"\"path\": \"/purchase_units/@reference_id=='PUHF'/amount\",\r\n" + 
			"\"value\":{\r\n" + 
			"        \"currency_code\": \"USD\",\r\n" + 
			"        \"value\": \"170.00\"\r\n" + 
			"      }\r\n" + 
			"      \r\n" + 
			"      }]";*/
	RestAssured.baseURI = "https://api.sandbox.paypal.com";
	Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).body(updateorder).log().everything().patch("v2/checkout/orders/"+  orderId);
	response.prettyPrint();
	System.out.println(response.getStatusCode());
}
}
