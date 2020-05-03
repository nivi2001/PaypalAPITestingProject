package com.paypal.APITesting.pojo;

public class CreateOrder {
	private String client_id;
	private String access_token;
	private String secret;
	private String orderId;
	private String referenceId;
	
	public CreateOrder(String client_id, String access_token,String secret,String orderId,String referenceId) {
		this.client_id = client_id;
		this.access_token = access_token;
		this.secret = secret;
		this.orderId = orderId;
		this.referenceId = referenceId;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}
