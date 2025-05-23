package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tempest.model.UserModel;
import com.tempest.model.UserModel.Roles;
import com.tempest.service.LoginService;
import com.tempest.service.UserService;
import com.tempest.util.CookieUtil;
import com.tempest.util.SessionUtil;
/**
 * Servlet implementation class LoginController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final LoginService loginService;
	private final UserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
    	super();
        this.loginService = new LoginService();
        this.userService = new UserService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/login.jsp").forward(request, response);	
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserModel user = new UserModel(email, password);
		Boolean loginStatus = loginService.loginUser(user);
		
		if (loginStatus != null && loginStatus) {
			// Get the full user object from the database
			UserModel fullUser = userService.getUserByEmail(email);
			CookieUtil.setCookie(response,"userId",Integer.toString(fullUser.getUser_id()));
			SessionUtil.CreateSession(request, "user", fullUser);
			SessionUtil.CreateSession(request, "email", email);
			SessionUtil.CreateSession(request, "userId", fullUser.getUser_id());
			
			if (userService.getUserRole(email).equals(Roles.admin)) {
				CookieUtil.setCookie(response, "role", "admin", 5 * 30 * 30);
				response.sendRedirect(request.getContextPath() + "/admin/dashboard");
			} else {
				CookieUtil.setCookie(response, "role", "customer", 5 * 30 * 30);
				response.sendRedirect(request.getContextPath() + "/home");
			}
		} else {
			handleLoginFailure(request, response, loginStatus);
		}
	}
	
	
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		
		req.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/login.jsp").forward(req, resp);
	}
}
