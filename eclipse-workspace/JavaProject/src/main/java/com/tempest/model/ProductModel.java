package com.tempest.model;

public class ProductModel{
	
	private int id;
	private String name;
	private String description;
	private float price;
	private String imageUrl;
	private int quantity;
	
	public ProductModel(String name, int quantity, float price ){
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public ProductModel(int id, String name, String description,float price,String imageUrl ){
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imageUrl = imageUrl;
	}
	
	// Getter methods

    public int getProductId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public int getQuantity() {
    	return quantity;
    }
    public float getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setter methods

    public void setProductId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}