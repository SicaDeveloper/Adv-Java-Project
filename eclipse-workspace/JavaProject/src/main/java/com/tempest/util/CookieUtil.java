package com.tempest.util;

import java.util.Arrays;

import jakarta.servlet.http.*; 
  
public class CookieUtil {
	private static final int DEFAULT_MAX_AGE = 30 * 24 * 60 * 60; // 30 days in seconds
	
	/**
	 * Get a cookie value by name
	 * @param request The HTTP request
	 * @param name The cookie name
	 * @return The cookie value, or null if not found
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * Set a cookie with default max age (30 days)
	 * @param response The HTTP response
	 * @param name The cookie name
	 * @param value The cookie value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, DEFAULT_MAX_AGE);
	}
	
	/**
	 * Set a cookie with specified max age
	 * @param response The HTTP response
	 * @param name The cookie name
	 * @param value The cookie value
	 * @param maxAge The max age in seconds
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static Cookie getCookie(HttpServletRequest request, String name) {
		if (request.getCookies()!= null) {
			return Arrays.stream(request.getCookies()).filter(Cookie -> name.equals(Cookie.getName())).findFirst().orElse(null);			
		}
		return null;
	}
	
	/**
	 * Delete a cookie
	 * @param response The HTTP response
	 * @param name The cookie name
	 */
	public static void deleteCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}