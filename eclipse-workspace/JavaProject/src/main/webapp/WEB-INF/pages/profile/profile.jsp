<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${user.first_name}'s Profile - Tempest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/homePage/navbar.jsp" />
    
    <div class="profile-container">
        <div class="profile-header">
            <h1>${user.first_name} ${user.last_name}'s Profile</h1>
            <a href="${pageContext.request.contextPath}/profile/edit" class="edit-button">Edit Profile</a>
        </div>

        <div class="profile-content">
            <div class="profile-section">
                <h2>Personal Information</h2>
                <div class="info-group">
                    <label>First Name:</label>
                    <span>${user.first_name}</span>
                </div>
                <div class="info-group">
                    <label>Last Name:</label>
                    <span>${user.last_name}</span>
                </div>
                <div class="info-group">
                    <label>Email:</label>
                    <span>${user.email}</span>
                </div>
            </div>

            <div class="profile-section">
                <h2>Contact Information</h2>
                <div class="info-group">
                    <label>Phone:</label>
                    <span>${user.phone}</span>
                </div>
                <div class="info-group">
                    <label>Address:</label>
                    <span>${user.address}</span>
                </div>
            </div>

            <div class="profile-section">
                <h2>Account Information</h2>
                <div class="info-group">
                    <label>Role:</label>
                    <span>${user.role}</span>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
