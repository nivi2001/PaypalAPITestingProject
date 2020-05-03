package com.paypal.APIrough;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.paypal.APIFramework.setUp.BaseTest;
import com.paypal.APITesting.pojo.CreateProduct;
import com.paypal.APITesting.pojo.UpdateOrder;
import com.paypal.APITesting.pojo.UpdateProduct;
import com.paypal.APITesting.pojo.Value;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PaypalProductTest extends BaseTest {
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
	  //System.out.println(access_token);
	}
	
	@Test(priority=2, dependsOnMethods="getAuthKey")
	public void CreateCatalogProduct() {
		CreateProduct createproduct = new CreateProduct("Video Streaming Service", "Video streaming service", "SERVICE", "SOFTWARE", "https://example.com/streaming.jpg", "https://example.com/home");
		/*createproduct.setName("Video Streaming Service");
		createproduct.setDescription("Video streaming service");
		createproduct.setType("SERVICE");
		createproduct.setCategory("SOFTWARE");
		createproduct.setImage_url("https://example.com/streaming.jpg");
		createproduct.setHome_url("https://example.com/home");*/

		Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).body(createproduct).post(config.getProperty("createProductAPIEndPoint"));
		response.prettyPrint();
	     productID = response.jsonPath().get("id").toString();
	     System.out.println(response.getStatusCode());
		}
	
	@Test(priority=3, dependsOnMethods="getAuthKey")
	public void ListCatalogProduct() {
		System.out.println("----Getting the List-----");
		RestAssured.baseURI = "https://api.sandbox.paypal.com";
		//Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).get("/catalogs/products?page_size=2&page=1&total_required=true");
		Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().get(config.getProperty("ListEndPoint"));
		response.prettyPrint();
		//System.out.println(response.getStatusCode());
		
	}
	
	@Test(priority=4, dependsOnMethods="getAuthKey")
	public void UpdateCatalogProduct() {
		//Value value = new Value(null, null);
		ArrayList<UpdateProduct> updateproduct = new ArrayList<UpdateProduct>();
		updateproduct.add(new UpdateProduct("replace", "/description", "Premium video streaming service"));
		/*String jsonBody = "[\r\n" + 
				"  {\r\n" + 
				"    \"op\": \"replace\",\r\n" + 
				"    \"path\": \"/description\",\r\n" + 
				"    \"value\": \"Premium video streaming service\"\r\n" + 
				"  }\r\n" + 
				"]";*/
	Response response =  given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(updateproduct).patch(config.getProperty("updateProductAPIEndPoint"));
		response.prettyPrint();
		System.out.println(response);
	}
	
	@Test(priority=5, dependsOnMethods="getAuthKey")
	public void ShowProductDetails() {
		Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).get(config.getProperty("UpdateProductEndPoint"));
		response.prettyPrint();
		System.out.println(response);
		//System.out.println(response.getStatusCode());
		
	}
	
	
	}


