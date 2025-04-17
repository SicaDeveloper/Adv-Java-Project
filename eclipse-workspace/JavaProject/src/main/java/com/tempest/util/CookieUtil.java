package com.tempest.util;

import jakarta.servlet.http.*; 
  
public class CookieUtil {
	
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
	}
	
	
	public static void getCookie(HttpServletRequest request, String name) {
		if (request.getCookies()!= null) {
			return Arrays.stream(request.getCookies()).filter(Cookie -> name.equals(cookie.getName())).findFirst().orElse(null);
					
		}
		return null;
	}
	
	public static void deleteCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}