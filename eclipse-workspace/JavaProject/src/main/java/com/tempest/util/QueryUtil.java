package com.tempest.util;


public class QueryUtil{
	public static String loginQuery = "SELECT email, password FROM user WHERE email = ?";
	public static String createUserQuery = "INSERT INTO users (first_name, last_name, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
}