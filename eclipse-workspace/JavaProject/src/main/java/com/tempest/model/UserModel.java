package com.tempest.model;

public class UserModel {

    public enum Roles {
        admin,
        customer
    }

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String imageUrl;
    private Roles role;
    
    
    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserModel(String email, String password, Roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    public UserModel(int id,String first_name,String last_name, String email, String password, String phone, String address, Roles role) {
    	this.id = id;
    	this.first_name = first_name;
    	this.last_name = last_name;
    	this.email = email;
    	this.phone = phone;
    	this.address = address;
    	this.password = password;
    	this.role = role;
    }
    
    public UserModel(String first_name,String last_name, String email, String password, String phone, String address, Roles role) {
    	this.first_name = first_name;
    	this.last_name = last_name;
    	this.email = email;
    	this.phone = phone;
    	this.address = address;
    	this.password = password;
    	this.role = role;
    }
    
    public UserModel(String first_name,String last_name, String email, String password, String phone, String address) {
    	
    	this.first_name = first_name;
    	this.last_name = last_name;
    	this.email = email;
    	this.phone = phone;
    	this.address = address;
    }

    // Getter for user_id
    public int getUser_id() {
        return user_id;
    }

    // Getter for first_name
    public String getFirst_name() {
        return first_name;
    }

    // Getter for last_name
    public String getLast_name() {
        return last_name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for phone
    public String getPhone() {
        return phone;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }
    
    public String getProfile() {
    	return imageUrl;
    }
    
    // Setter for user_id
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    // Setter for first_name
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    // Setter for last_name
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    // Setter for gmail
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter for phone
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Setter for address
    public void setAddress(String address) {
        this.address = address;
    }
    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
    
    public void setProfile(String imageUrl) {
    	this.imageUrl = imageUrl;
    }
}