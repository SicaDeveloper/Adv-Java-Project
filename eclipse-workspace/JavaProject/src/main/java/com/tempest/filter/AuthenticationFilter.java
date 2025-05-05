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
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String DASHBOARD = "/admin/dashboard";
	private static final String PRODUCT_DASHBOARD = "/admin/product";
	private static final String PRODUCT_EDIT = "/admin/product/edit";
	private static final String PRODUCT_ADD = "/admin/product/add";
	private static final String PRODUCT_UPDATE = "/admin/product/edit";
	private static final String PRODUCT_DELETE = "/admin/product/add";
	private static final String PROFILE_UPDATE = "/profile";
	private static final String ADMIN_ORDER = "/adminOrder";
	private static final String ABOUT = "/aboutus";
	private static final String ORDER = "/orders";
	private static final String CONTACT = "/contact";
	private static final String ORDER_LIST = "/orderlist";
	private static final String CART = "/cart";

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
		if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".css") || path.endsWith(".js")) {
			chain.doFilter(request, response);
			return;
		}
		
		boolean isLoggedIn = SessionUtil.getSession(req, "email") != null;
		String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue() : null;
		
		if(isLoggedIn) {
			if ("admin".equals(userRole)) {
				// Admin is logged in
				if (path.equals(LOGIN) || path.equals(REGISTER)) {
					res.sendRedirect(contextPath + DASHBOARD);
				} else if (path.equals(DASHBOARD) || path.equals(PRODUCT_DASHBOARD) || path.equals(PRODUCT_EDIT) 
						|| path.equals(PRODUCT_UPDATE) || path.equals(PRODUCT_DELETE) ||  path.equals(PRODUCT_ADD) 
						|| path.equals(ADMIN_ORDER) || path.equals(ORDER) || path.equals(HOME) || path.equals(ROOT)) {
					chain.doFilter(request, response);
				} else if (path.equals(ORDER_LIST) || path.equals(CART)) {
					res.sendRedirect(contextPath + DASHBOARD);
				} else {
					res.sendRedirect(contextPath + DASHBOARD);
				}
			} else {
				// User is logged in
				if (path.equals(LOGIN) || path.equals(REGISTER)) {
					res.sendRedirect(contextPath + HOME);
				} else if (path.equals(HOME) || path.equals(ROOT) || path.equals(ABOUT) ||
						path.equals(CONTACT) || path.equals(ORDER_LIST) || path.equals(CART)) {
					chain.doFilter(request, response);
				} else if (path.equals(DASHBOARD) || path.equals(PROFILE_UPDATE) ||
						path.equals(ADMIN_ORDER)) {
					res.sendRedirect(contextPath + HOME);
				} else {
					res.sendRedirect(contextPath + HOME);
				}
			}
		} else {
			// Not logged in
			if (path.equals(LOGIN) || path.equals(REGISTER) || path.equals(HOME) || path.equals(ROOT)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(contextPath + LOGIN);
			}
		}
	}

	@Override
	public void destroy() {
		// Cleanup logic, if required
	}
}