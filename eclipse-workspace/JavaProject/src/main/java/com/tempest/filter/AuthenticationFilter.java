package com.tempest.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.tempest.util.CookieUtil;
import com.tempest.util.SessionUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String LOGOUT = "/logout";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String PRODUCT = "/products";
	private static final String ROOT = "/";
	private static final String DASHBOARD = "/admin/dashboard";
	private static final String PRODUCT_DASHBOARD = "/admin/product";
	private static final String PRODUCT_EDIT = "/admin/product/edit";
	private static final String PRODUCT_ADD = "/admin/product/add";
	private static final String PRODUCT_DELETE = "/admin/product/delete";
	private static final String ADMIN_ORDER = "/admin/order";
	private static final String ORDER_EDIT = "/admin/order/edit";
	private static final String ORDER_ADD = "/admin/order/add";
	private static final String ORDER_DELETE = "/admin/order/delete";
	private static final String PROFILE_UPDATE = "/profile/update";
	private static final String ABOUT = "/aboutus";
	private static final String ORDER = "/orders";
	private static final String CONTACT = "/contact";
	private static final String ORDER_LIST = "/orderlist";
	private static final String CART = "/cart";
	private static final String CART_ADD = "/cart/add";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic, if required
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length());
		
		// Allow access to resources
		if (isResource(path)) {
			chain.doFilter(request, response);
			return;
		}
		
		boolean isLoggedIn = SessionUtil.getSession(req, "email") != null;
		String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue() : null;
		
		try {
			if(isLoggedIn) {
				if ("admin".equals(userRole)) {
					handleAdminAccess(path, req, res, chain);
				} else {
					handleUserAccess(path, req, res, chain);
				}
			} else {
				handlePublicAccess(path, req, res, chain);
			}
		} catch (IllegalStateException e) {
			// If response is already committed, just let the request continue
			chain.doFilter(request, response);
		}
	}

	private boolean isResource(String path) {
		return path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".avif") ||
			   path.endsWith(".css") || path.endsWith(".js") ||
			   path.endsWith(".ico") || path.endsWith(".gif");
	}

	private void handleAdminAccess(String path, HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
			throws IOException, ServletException {
		String contextPath = req.getContextPath();
		
		if (path.equals(LOGIN) || path.equals(REGISTER)) {
			res.sendRedirect(contextPath + DASHBOARD);
		} else if (isAdminPath(path)) {
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(contextPath + DASHBOARD);
		}
	}

	private void handleUserAccess(String path, HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
			throws IOException, ServletException {
		String contextPath = req.getContextPath();
		
		if (path.equals(LOGIN) || path.equals(REGISTER)) {
			res.sendRedirect(contextPath + HOME);
		} else if (isUserPath(path)) {
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(contextPath + HOME);
		}
	}

	private void handlePublicAccess(String path, HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
			throws IOException, ServletException {
		String contextPath = req.getContextPath();
		
		if (isPublicPath(path)) {
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(contextPath + LOGIN);
		}
	}

	private boolean isAdminPath(String path) {
		return path.equals(DASHBOARD) || path.equals(PRODUCT_DASHBOARD) || path.equals(LOGOUT) ||
			   path.startsWith(PRODUCT_EDIT) || path.equals(PRODUCT_ADD) || 
			   path.equals(PRODUCT_DELETE) || path.equals(ADMIN_ORDER) || path.equals(PRODUCT) || path.equals(PROFILE_UPDATE) ||									
			   path.startsWith(ORDER_EDIT) || path.startsWith(ORDER_ADD) || 
			   path.equals(ORDER_DELETE) || path.equals(ORDER) || 
			   path.equals(HOME) || path.equals(ROOT) ;
	}

	private boolean isUserPath(String path) {
		return path.equals(HOME) || path.equals(ROOT) || path.equals(ABOUT) || 
			   path.equals(CONTACT) || path.equals(ORDER_LIST) || path.equals(CART) || 
			   path.equals(CART_ADD) || path.equals(LOGOUT);
	}

	private boolean isPublicPath(String path) {
		return path.equals(LOGIN) || path.equals(REGISTER) || 
			   path.equals(HOME) || path.equals(ROOT);
	}

	@Override
	public void destroy() {
		// Cleanup logic, if required
	}
}