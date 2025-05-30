<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile - Tempest</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css" />
</head>
<body>
    <jsp:include page="/WEB-INF/pages/homePage/navbar.jsp" />
    
    <div class="profile-container">
        <div class="profile-header">
            <h1>Edit Profile</h1>
            <a href="${pageContext.request.contextPath}/profile" class="back-button">Back to Profile</a>
        </div>

        <div class="profile-content">
            <form action="${pageContext.request.contextPath}/profile/update" method="post" class="edit-form">
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" value="${user.first_name}" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" value="${user.last_name}" required>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" value="${user.email}" disabled>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" id="phone" name="phone" value="${user.phone}" required>
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${user.address}" required>
                </div>

                <div class="form-actions">
                    <button type="submit" class="save-button">Save Changes</button>
                    <a href="${pageContext.request.contextPath}/profile" class="cancel-button">Cancel</a>
                </div>

                <c:if test="${not empty error}">
                    <div class="error-message">
                        ${error}
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</body>
</html> 