package com.tempest.model;

public class UserModel {

    public enum Roles {
        admin,
        customer
    }

    private int user_id;
    private String first_name;
    private String last_name;
    private String gmail;
    private String phone;
    private String address;
    private String password;
    private Roles role;

    public UserModel(String gmail, String password) {
        this.gmail = gmail;
        this.password = password;
    }
    
    public UserModel()

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

    // Getter for gmail
    public String getGmail() {
        return gmail;
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
    
    // Setter for first_name
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    // Setter for last_name
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    // Setter for gmail
    public void setGmail(String gmail) {
        this.gmail = gmail;
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
}