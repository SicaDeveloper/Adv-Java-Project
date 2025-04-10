package com.tempest.model;

public class UserModel{

	public enum Roles{
		admin,
		customer
	}
	
	private int user_id;
	private String username;
	private String password;
	private Roles role;
	
	public UserModel(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Getter for user_id
    public int getUser_id() {
        return user_id;
    }

    // Getter for username
    public static String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
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