package com.paypal.APITesting.pojo;

public class CreateProduct {
	private String name;
	private String description;
	private String type;
	private String category;
	private String image_url;
	private String home_url;
	
	public CreateProduct(String name, String description,String type,String category,String image_url, String home_url) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.category = category;
		this.image_url = image_url;
		this.home_url = home_url;
	}
	
	
	
	
	
	
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getHome_url() {
		return home_url;
	}
	public void setHome_url(String home_url) {
		this.home_url = home_url;
	}
}
