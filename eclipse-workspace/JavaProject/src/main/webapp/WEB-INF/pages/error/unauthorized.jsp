<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Unauthorized Access</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>401 - Unauthorized Access</h1>
        <p>${authError != null ? authError : 'You do not have permission to access this resource.'}</p>
        <a href="${pageContext.request.contextPath}/home" class="home-button">Return to Home</a>
    </div>
</body>
</html> 