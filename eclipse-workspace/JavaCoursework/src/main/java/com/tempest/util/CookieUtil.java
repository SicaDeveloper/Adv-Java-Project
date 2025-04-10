package com.tempest.util;

import jakarta.servlet.*; 
import jakarta.servlet.http.*; 
import java.io.IOException; 
import java.io.PrintWriter; 
  
public class CookieUtil {
	
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
}