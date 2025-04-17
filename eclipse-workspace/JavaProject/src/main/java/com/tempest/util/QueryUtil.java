package com.tempest.util;


public class QueryUtil{
	
	public static String loginQuery = "SELECT username, password FROM temp WHERE username = ?";
	public static String createUserQuery = "INSERT INTO User(username, password, role) VALUES (?,?,?)";
}