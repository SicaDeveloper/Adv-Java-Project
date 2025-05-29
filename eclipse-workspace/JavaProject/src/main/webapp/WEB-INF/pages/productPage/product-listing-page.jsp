<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products - Tempest</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-card.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-listing-page.css">
</head>
<body>
<jsp:include page="../homePage/navbar.jsp"></jsp:include>
<jsp:include page="product-listing.jsp"></jsp:include>
</body>
</html>