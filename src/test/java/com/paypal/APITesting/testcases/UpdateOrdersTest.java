package com.paypal.APITesting.testcases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.paypal.APIFramework.setUp.BaseTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateOrdersTest extends BaseTest {
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
	  
	}
	@Test(priority=2, dependsOnMethods="getAuthKey")
	public void CreateAnOrder() {
		
		 
			
		      String jsonBody = "{\r\n" + 
		      		"  \"intent\": \"CAPTURE\",\r\n" + 
		      		"  \"purchase_units\": [\r\n" + 
		      		"    {\r\n" + 
		      		"      \"amount\": {\r\n" + 
		      		"        \"currency_code\": \"USD\",\r\n" + 
		      		"        \"value\": \"100.00\"\r\n" + 
		      		"      }\r\n" + 
		      		"    }\r\n" + 
		      		"  ]\r\n" + 
		      		"}";
			 
			 Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(jsonBody).post("/v2/checkout/orders"); 
			 orderId = response.jsonPath().get("id").toString();
	}
	
	@Test(priority=2, dependsOnMethods="getAuthKey")
	public void AddAdrressByUsingServiceUpdateOrder() {
		String jsonBody = "[\r\n" + 
				"  {\r\n" + 
				"\"op\": \"add\",\r\n" + 
				"\"path\": \"/purchase_units/@reference_id=='default'/shipping/address\",\r\n" + 
				"\"value\": {\r\n" + 
				"  \"address_line_1\": \"123 Townsend St\",\r\n" + 
				"  \"address_line_2\": \"Floor 6\",\r\n" + 
				"  \"admin_area_2\": \"San Francisco\",\r\n" + 
				"  \"admin_area_1\": \"CA\",\r\n" + 
				"  \"postal_code\": \"94107\",\r\n" + 
				"  \"country_code\": \"US\"\r\n" + 
				"}\r\n" + 
				"  }\r\n" + 
				"]";
		Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(jsonBody).patch("/v2/checkout/orders"+"/"+orderId);
		response.prettyPrint();
		response.getStatusCode();
		}
	
	@Test(priority=3, dependsOnMethods="getAuthKey")
	public void  UpdateAdrressByUsingServiceUpdateOrder() {
		String jsonBody = "[\r\n" + 
				"  {\r\n" + 
				"\"op\": \"replace\",\r\n" + 
				"\"path\": \"/purchase_units/@reference_id=='default'/shipping/address\",\r\n" + 
				"\"value\": {\r\n" + 
				"  \"address_line_1\": \"123 Townsend St\",\r\n" + 
				"  \"address_line_2\": \"Floor 6\",\r\n" + 
				"  \"admin_area_2\": \"New York\",\r\n" + 
				"  \"admin_area_1\": \"CA\",\r\n" + 
				"  \"postal_code\": \"94107\",\r\n" + 
				"  \"country_code\": \"US\"\r\n" + 
				"}\r\n" + 
				"  }\r\n" + 
				"]";
		Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(jsonBody).patch("/v2/checkout/orders"+"/"+orderId);
		response.prettyPrint();
		response.getStatusCode();
		}
	
	/*@Test(priority=4, dependsOnMethods="getAuthKey")
	public void ValidateRequestForReplacingPurchaseUnits() {
		String jsonBody = "[\r\n" + 
				"  {\r\n" + 
				"\"op\": \"replace\",\r\n" + 
				"\"path\": \"/purchase_units/@reference_id=='default'/shipping/address\",\r\n" + 
				"\"value\": {\r\n" + 
				"  \"address_line_1\": \"123 Townsend St\",\r\n" + 
				"  \"address_line_2\": \"Floor 6\",\r\n" + 
				"  \"admin_area_2\": \"San Francisco\",\r\n" + 
				"  \"admin_area_1\": \"CA\",\r\n" + 
				"  \"postal_code\": \"94107\",\r\n" + 
				"  \"country_code\": \"US\"\r\n" + 
				"}\r\n" + 
				"  }\r\n" + 
				"]";
		Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(jsonBody).patch("/v2/checkout/orders"+"/"+orderId);
		response.prettyPrint();
		response.getStatusCode();
		}
		
	}*/

}
