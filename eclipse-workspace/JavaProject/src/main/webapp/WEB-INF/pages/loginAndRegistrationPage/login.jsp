<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Login</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
		<!-- 
			Author: Raj Dangol
			Date: 15th April, 2025
			Login page
		-->
	</head>
	<body>
		<div class="login-container">
			<img
				class="login-image"
				src="${pageContext.request.contextPath}/resource/login photo for us sipping.png"
				alt=""
			/>
			<div class="login-form">
				<div class="title">
					<h2>Welcome Back</h2>
					<h5>Enter your credentials to access your account</h5>
				</div>
				<form method="post" action="login">
					<div class="form-group">
						<label for="email">Email</label>
						<input
							type="text"
							id="email"
							name="email"
							placeholder="Enter your email"
							required
						/>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<input
							type="password"
							id="password"
							name="password"
							placeholder="Enter your password"
							required
						/>
					</div>
					<div class="form-group">
						<button class="login-button" type="submit">Login</button>
						<a href="" class="forgot-password">forgot password?</a>
					</div>
				</form>
				<div class="divider">
					<span class="left"></span>
					<span class="or">or</span>
					<span class="right"></span>
				</div>
				<div class="oauth-login">
					<button>
						<img class="btn-logo" src="${pageContext.request.contextPath}/resource/icons-google.png" />
						<span>Sign in with Google</span>
					</button>
					<button>
						<img class="btn-logo" src="${pageContext.request.contextPath}/resource/icons-apple.png" />
						<span>Sign in with Apple</span>
					</button>
				</div>
				<div class="signup">Don't have an account? <a class="signup-link" href="${pageContext.request.contextPath}/register">Sign up</a></div>
			</div>
		</div>
	</body>
</html>
