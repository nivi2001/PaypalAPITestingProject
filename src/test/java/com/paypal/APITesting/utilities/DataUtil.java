package com.paypal.APITesting.utilities;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.paypal.APIFramework.setUp.BaseTest;

public class DataUtil extends BaseTest {
 
	@DataProvider(name = "data-provider")
	public Object[][] getData(Method m) {
		//System.out.println("sheetName"+ m.getName());
		String sheetName = m.getName();
	//	ExcelReader excel = new ExcelReader();
		//int rows = excel.getRowCount(Constants.DATA_SHEET);
		//int cols =  excel.getColumnCount(Constants.DATA_SHEET); 
		
		int rows = excel.getRowCount(sheetName);
		int cols =  excel.getColumnCount(sheetName);
		
		System.out.println(sheetName);
		System.out.println("Total rows are :"+rows+"Total cols are :"+ rows);
		
		Object[][] data = new Object[rows-1][cols];		
		
		/*System.out.println(excel.getCellData(sheetName, 0, 2));
	    data[0][0] = excel.getCellData(sheetName, 0, 2);
		data[0][1] = excel.getCellData(sheetName, 1, 2);
		data[0][2] = excel.getCellData(sheetName, 2, 2);
		data[0][3] = excel.getCellData(sheetName, 3, 2);
		data[0][4] = excel.getCellData(sheetName, 4, 2);*/
		
		for (int rowNum = 2; rowNum<= rows; rowNum++) {
			
			for(int colNum = 0 ; colNum <cols; colNum++) {
				
				 data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
				
			}
		}
		
		
		
		return data;
		
}
}
