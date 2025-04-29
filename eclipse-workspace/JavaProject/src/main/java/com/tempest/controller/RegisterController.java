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
		final String first_name = request.getParameter("firstName");
		final String last_name = request.getParameter("lastName");
		final String email = request.getParameter("email");
		final String password = request.getParameter("password");
		final String phone = request.getParameter("phone");
		final String address = request.getParameter("address");
		final Roles role = UserModel.Roles.customer;
		
		UserModel currentUser = new UserModel(first_name, last_name, email, password, phone, address, role);
		
		if(registerService.addCustomer(currentUser)) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			request.setAttribute("error", "Registration failed. Please try again.");
			request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/register.jsp").forward(request, response);
		}
	}

}
