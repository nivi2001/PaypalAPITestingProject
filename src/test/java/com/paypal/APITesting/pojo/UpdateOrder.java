package com.paypal.APITesting.pojo;



//import java.util.ArrayList;

public class UpdateOrder {
	private String op;
	private String path;
	private UpdateOrderValue value;
	
	
	
	public UpdateOrder(String op, String path, UpdateOrderValue value2) {
		this.op = op;
		this.path = path;
		this.value = value2;
		
	}


	public String getOp() {
		return op;
	}


	public void setOp(String op) {
		this.op = op;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public UpdateOrderValue getValue() {
		return value;
	}


	public void setValue(UpdateOrderValue value) {
		this.value = value;
	}

	

	


	
	
	
	
}	


	

	

	
	
	
	
	

