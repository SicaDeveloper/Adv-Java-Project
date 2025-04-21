<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Register</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css" />
    </head>
    <body>
        <div class="register-container">
            <div class="register-form">
                <div class="title">
                    <h2>Get Started Now</h2>
                </div>
                <form method="post" action="register">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <input type="text" id="firstName" name="firstName" placeholder="Enter your first name" required />
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <input type="text" id="lastName" name="lastName" placeholder="Enter your last name" required />
                        </div>
                    </div>
                    <div class="form-row">
                        <!-- Previously "Email" is now replaced with "Address" -->
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input type="text" id="address" name="address" placeholder="Enter your address" required />
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="tel" id="phone" name="phone" placeholder="Enter your phone number" required />
                        </div>
                    </div>
                    <div class="form-row">
                        <!-- Previously "Address" is now replaced with "Email" -->
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" placeholder="Enter your email" required />
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" placeholder="Enter your password" required />
                        </div>
                    </div>
                    <div class="terms-agreement">
                        <input type="checkbox" name="terms" required />
                        I agree to the <a class="terms-link" href="#">terms and conditions</a>
                    </div>
                    <div class="form-group">
                        <button class="register-button" type="submit">Sign Up</button>
                    </div>
                </form>
                <div class="divider">
                    <span class="left"></span>
                    <span class="or">or</span>
                    <span class="right"></span>
                </div>
                <div class="oauth-register">
                    <button>
                        <img class="btn-logo" src="${pageContext.request.contextPath}/resource/icons-google.png" />
                        <span>Sign in with Google</span>
                    </button>
                    <button>
                        <img class="btn-logo" src="${pageContext.request.contextPath}/resource/icons-apple.png" />
                        <span>Sign in with Apple</span>
                    </button>
                </div>
                <div class="signin">Already have an account? <a class="signin-link" href="${pageContext.request.contextPath}/login">Sign in</a></div>
            </div>
            <img class="register-image" src="${pageContext.request.contextPath}/resource/le chong we badminton smsah for regisration.jpg" alt="Registration Banner">
        </div>
    </body>
</html>