<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Customer</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit-customer.css">
    <style>
        .role-select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: white;
            font-family: 'Poppins', sans-serif;
            font-size: 14px;
            color: #333;
            cursor: pointer;
            transition: border-color 0.3s ease;
        }

        .role-select:hover {
            border-color: #999;
        }

        .role-select:focus {
            outline: none;
            border-color: #000;
            box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.1);
        }

        .role-select option {
            padding: 10px;
            background-color: white;
        }
    </style>
</head>
<body>
    <div class="add-product-bg">
        <div class="add-product-main">
            <div class="container">
                <div class="form-container">
                    <h2>Edit Customer</h2>
                    
                    <c:if test="${not empty error}">
                        <div class="error-message">
                            ${error}
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty success}">
                        <div class="success-message">
                            ${success}
                        </div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/admin/customer/edit" method="post">
                        <input type="hidden" name="id" value="${user.user_id}">
                        
                        <div class="form-row">
                            <div class="form-item">
                                <label for="firstName">First Name</label>
                                <input type="text" id="firstName" name="firstName" value="${user.first_name}" required maxlength="20">
                            </div>
                            
                            <div class="form-item">
                                <label for="lastName">Last Name</label>
                                <input type="text" id="lastName" name="lastName" value="${user.last_name}" required maxlength="20">
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-item">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="email" value="${user.email}" required maxlength="50">
                            </div>
                            
                            <div class="form-item">
                                <label for="phone">Phone Number</label>
                                <input type="tel" id="phone" name="phone" value="${user.phone}" required maxlength="10" pattern="[0-9]{10}">
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-item full-width">
                                <label for="address">Address</label>
                                <input type="text" id="address" name="address" value="${user.address}" required maxlength="20">
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-item">
                                <label for="role">Role</label>
                                <select id="role" name="role" class="role-select" required>
                                    <option value="customer" ${user.role == 'customer' ? 'selected' : ''}>Customer</option>
                                    <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>Admin</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="button-group">
                            <button type="submit" class="btn-primary">Update Customer</button>
                            <a href="${pageContext.request.contextPath}/admin/customer" class="btn-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 