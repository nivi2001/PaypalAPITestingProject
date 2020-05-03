package com.paypal.APITesting.utilities;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//import java.lang.reflect.Method;

public class TestParametrization {

  @Test(dataProvider="getData")
	public void testData(String currency_code, String currency_value, String status){
	  
	  
  }
	  
	@DataProvider
	public Object[][] getData() { 
	  ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+".\\src\\test\\resources\\excel\\testData.xlsx");
		int rows = excel.getRowCount(Constants.DATA_SHEET);// Read number of rows in excelsheet
		int cols =  excel.getColumnCount(Constants.DATA_SHEET); // Read number of columns in excelsheet
		System.out.println("Total rows are :"+rows); // print number of rows
		
		String testName = " TC01  Validate valid request for creating an Order";
		int testCaseRowNum=1;
		//Navigate till the row in which testcase name is found
		for (testCaseRowNum=1; testCaseRowNum<= rows; testCaseRowNum++) {
			String testCaseName = 	excel.getCellData(Constants.DATA_SHEET, 0, testCaseRowNum);
			if(testCaseName.equalsIgnoreCase(testName))
				break;
			}
			System.out.println("Test case starts from row num: "+testCaseRowNum);
		//Checking total number of rows 
		int dataStartRowNum;
		dataStartRowNum= testCaseRowNum +1;
		int testRows = 0;
		while (!excel.getCellData(Constants.DATA_SHEET,0,dataStartRowNum+testRows).equals("")){
			testRows++;
		}
		System.out.println("Total rows of data are: "+testRows);
		
		//Checking total cols in test case
		int dataStartColNum;
		int colStartColNum = testCaseRowNum +1;
		int testCols=0;
		while(excel.getCellData(Constants.DATA_SHEET, testCols, colStartColNum).equals("")){
			testCols++;
		}
		System.out.println("Total cols are: "+testCols);
		
		//Printing data
		   Object[][] data = new Object[testRows][testCols];	
          for (int rNum = dataStartRowNum; rNum<= (dataStartRowNum+testRows); rNum++) {
			for(int cNum = 0 ; cNum <testCols; cNum++) {
			 data[rNum][cNum] = excel.getCellData(Constants.DATA_SHEET, cNum, rNum);
			 
				
			}
		}
		
          return data;
		
		
		
		
		
		
	}
	}



	