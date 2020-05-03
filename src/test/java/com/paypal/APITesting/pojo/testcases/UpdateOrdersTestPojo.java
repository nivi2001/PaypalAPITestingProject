package com.paypal.APITesting.pojo.testcases;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.paypal.APIFramework.setUp.BaseTest;
import com.paypal.APITesting.pojo.Orders;
import com.paypal.APITesting.pojo.PurchaseUnits;
import com.paypal.APITesting.pojo.UpdateOrder;
import com.paypal.APITesting.pojo.UpdateOrderValue;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateOrdersTestPojo extends BaseTest {
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
		
		 
		ArrayList<PurchaseUnits> list= new  ArrayList<PurchaseUnits>();
	      list.add(new PurchaseUnits("USD", "500"));
	      Orders order = new Orders("CAPTURE",list);
			 
			 Response response = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(order).post("/v2/checkout/orders"); 
			 orderId = response.jsonPath().get("id").toString();
	}
	
	@Test(priority=2, dependsOnMethods= {"getAuthKey","CreateAnOrder"})
	public void AddAddressUsingServiceUpdateOrderPoJo() {
		ArrayList<UpdateOrder> updateorder = new ArrayList<UpdateOrder>();
		UpdateOrderValue addList = new UpdateOrderValue("123 add St", "Floor 6", "San Francisco", "CA", "94107", "US");
		updateorder.add(new UpdateOrder("add","/purchase_units/@reference_id=='default'/shipping/address", addList));
		Response addResponse = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(updateorder).patch("/v2/checkout/orders"+"/"+orderId);
		addResponse.prettyPrint();
		addResponse.getStatusCode();
	}
	@Test(priority=3, dependsOnMethods= {"getAuthKey","AddAddressUsingServiceUpdateOrderPoJo"})
	public void UpdateAddressUsingServiceUpdateOrderPoJo() {
		ArrayList<UpdateOrder> updateorder = new ArrayList<UpdateOrder>();
		UpdateOrderValue updateList = new UpdateOrderValue("123 updatetest St", "Floor 6", "San Francisco", "CA", "94107", "US");
		updateorder.add(new UpdateOrder("replace","/purchase_units/@reference_id=='default'/shipping/address", updateList));
		Response updateResponse = given().contentType(ContentType.JSON).auth().oauth2(access_token).log().everything().body(updateorder).patch("/v2/checkout/orders"+"/"+orderId);
		updateResponse.prettyPrint();
		updateResponse.getStatusCode();
		System.out.println(updateResponse.getStatusCode());
		
		
		
	}

}



