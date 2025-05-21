<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Page Not Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>404 - Page Not Found</h1>
        <p>${error != null ? error : 'The page you are looking for does not exist.'}</p>
        <a href="${pageContext.request.contextPath}/home" class="home-button">Return to Home</a>
    </div>
</body>
</html> 