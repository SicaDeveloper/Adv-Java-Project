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
                <form method="post" action="register" onsubmit="return validateForm()">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <input type="text" id="firstName" name="firstName" placeholder="Enter your first name" required 
                                   pattern="^[a-zA-Z]+$" title="First name should contain only letters" />
                            <span class="error" id="firstNameError"></span>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <input type="text" id="lastName" name="lastName" placeholder="Enter your last name" required 
                                   pattern="^[a-zA-Z]+$" title="Last name should contain only letters" />
                            <span class="error" id="lastNameError"></span>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input type="text" id="address" name="address" placeholder="Enter your address" required 
                                   maxlength="100" />
                            <span class="error" id="addressError"></span>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="tel" id="phone" name="phone" placeholder="Enter your phone number" required 
                                   pattern="^98\d{8}$" title="Phone number should start with 98 followed by 8 digits" />
                            <span class="error" id="phoneError"></span>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" placeholder="Enter your email" required 
                                   pattern="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$" title="Please enter a valid email address" />
                            <span class="error" id="emailError"></span>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" placeholder="Enter your password" required 
                                   pattern="^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$" 
                                   title="Password must contain at least 8 characters, one uppercase letter, one number, and one special character" />
                            <span class="error" id="passwordError"></span>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="retypePassword">Retype Password</label>
                            <input type="password" id="retypePassword" name="retypePassword" placeholder="Retype your password" required />
                            <span class="error" id="retypePasswordError"></span>
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
        <script>
        function validateForm() {
            let isValid = true;
            const firstName = document.getElementById('firstName');
            const lastName = document.getElementById('lastName');
            const address = document.getElementById('address');
            const phone = document.getElementById('phone');
            const email = document.getElementById('email');
            const password = document.getElementById('password');
            const retypePassword = document.getElementById('retypePassword');

            // Reset error messages
            document.querySelectorAll('.error').forEach(error => error.textContent = '');

            // Validate first name
            if (!firstName.value.match(/^[a-zA-Z]+$/)) {
                document.getElementById('firstNameError').textContent = 'First name should contain only letters';
                isValid = false;
            }

            // Validate last name
            if (!lastName.value.match(/^[a-zA-Z]+$/)) {
                document.getElementById('lastNameError').textContent = 'Last name should contain only letters';
                isValid = false;
            }

            // Validate address
            if (address.value.trim().length === 0) {
                document.getElementById('addressError').textContent = 'Address is required';
                isValid = false;
            }

            // Validate phone number
            if (!phone.value.match(/^98\d{8}$/)) {
                document.getElementById('phoneError').textContent = 'Phone number should start with 98 followed by 8 digits';
                isValid = false;
            }

            // Validate email
            if (!email.value.match(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/)) {
                document.getElementById('emailError').textContent = 'Please enter a valid email address';
                isValid = false;
            }

            // Validate password
            if (!password.value.match(/^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)) {
                document.getElementById('passwordError').textContent = 'Password must contain at least 8 characters, one uppercase letter, one number, and one special character';
                isValid = false;
            }

            // Validate password match
            if (password.value !== retypePassword.value) {
                document.getElementById('retypePasswordError').textContent = 'Passwords do not match';
                isValid = false;
            }

            return isValid;
        }
        </script>
    </body>
</html>