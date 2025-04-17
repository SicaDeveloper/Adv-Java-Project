package com.tempest.util;

import java.time.LocalDate;


public class ValidationUtil {
	
	public ValidationUtil() {
		
	}
	
	 public static boolean isNullOrEmpty(String value) {
	        return value == null || value.trim().isEmpty();
	    }
	
}