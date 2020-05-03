package com.paypal.APIFramework.setUp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.paypal.APITesting.utilities.ExcelReader;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
	
	public static Properties config = new Properties();
	private FileInputStream fis;
	public static ExcelReader excel = new ExcelReader(".\\src\\test\\resources\\excel\\testData.xlsx");
     
	@BeforeSuite
	public void setUp() {
		 try {
			fis  = new FileInputStream(".\\src\\test\\resources\\properties\\config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			config.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 RestAssured.baseURI = config.getProperty("baseURI");
		// RestAssured.basePath = config.getProperty("basePath");
		 
		/* RequestSpecification request = RestAssured.given();
		 request.baseUri(config.getProperty("baseURI"));
		 request.basePath(config.getProperty("basePathCreateOrder"));*/
		
		
	}
	
	
	
	@AfterSuite
	public void tearDown() {                          //if you want to end the test
		
	}
}
