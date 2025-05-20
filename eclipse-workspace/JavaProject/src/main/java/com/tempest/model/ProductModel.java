package com.tempest.model;

public class ProductModel{
	
	private int id;
	private String name;
	private String description;
	private double price;
	private String imageUrl;
	private int quantity;
	private int categoryId;
	private int cartQuantity; // Quantity in cart
	
	public ProductModel(String name, int quantity, double price, String imageUrl){
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public ProductModel(int id, String name, int quantity, double price, String imageUrl){
		this.id= id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public ProductModel(int id, String name, String description,double price,int quantity, String imageUrl ){
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.imageUrl = imageUrl;
	}
	
	// Getter methods

    public ProductModel(String name, String description, double price, int quantity, String imageUrl) {
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.imageUrl = imageUrl;
	}

	public int getId() {
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
    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    
    public int getCategoryId() {
        return categoryId;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    // Setter methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}