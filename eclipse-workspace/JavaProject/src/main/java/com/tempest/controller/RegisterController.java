package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.tempest.model.UserModel;
import com.tempest.model.UserModel.Roles;
import com.tempest.service.RegisterService;
import com.tempest.util.PasswordUtil;
import com.tempest.util.ValidationUtil;
/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final RegisterService registerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        this.registerService = new RegisterService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String first_name = request.getParameter("firstName");
		String last_name = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		Roles role = UserModel.Roles.customer;
		
		// Validate email format
		if (!ValidationUtil.isValidEmail(email)) {
			request.setAttribute("error", "Invalid email format");
			request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/register.jsp").forward(request, response);
			return;
		}
		
		// Check if email already exists
		if (registerService.isEmailExists(email)) {
			request.setAttribute("error", "Email already registered. Please use a different email or login.");
			request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/register.jsp").forward(request, response);
			return;
		}
		
		// Validate phone number
		if (!ValidationUtil.isValidPhoneNumber(phone)) {
			request.setAttribute("error", "Invalid phone number format. Must start with 98 and be 10 digits long.");
			request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/register.jsp").forward(request, response);
			return;
		}
		
		// Validate password strength
		if (!ValidationUtil.isValidPassword(password)) {
			request.setAttribute("error", "Password must contain at least 1 capital letter, 1 number, and 1 special character.");
			request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/register.jsp").forward(request, response);
			return;
		}
		
		password = PasswordUtil.encrypt(email, password);
		
		UserModel currentUser = new UserModel(first_name, last_name, email, password, phone, address, role);
		
		if(registerService.addCustomer(currentUser)) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			request.setAttribute("error", "Registration failed. Please try again.");
			request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/register.jsp").forward(request, response);
		}
	}

}
