<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tempest.model.ProductModel" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Document</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-listing-page.css" />
	</head>
	<body>
		<div class="product-listing-container">
			<div class="product-categories">
				<div class="product-category-header">Shop by categories</div>
				<div class="product-category-container">
				<div class="category-list">
					<c:forEach var="category" items="${categories}">
					 	<input value="${category.id}" type="checkbox" />
					 	<label>${category.name}</label>
	                </c:forEach>
	               </div>
	                <button class="product-filter">Filter</button>
				</div>
			</div>
			<div>
				<div class="product-navbar">
					<input
						class="search-input"
						type="search"
						name="search-products"
						placeholder="Search products"
						id="search-products"
					/><img class="search-icon" src="${pageContext.request.contextPath}/resource/search.png" alt="" />
				</div>
				<div class="product-list">
					<c:forEach var="product" items="${product}">
						<jsp:include page="product-card.jsp">
							<jsp:param name="id" value="${product.id}"/>
							<jsp:param name="name" value="${product.name}"/>
							<jsp:param name="price" value="${product.price}"/>
							<jsp:param name="imageUrl" value="${product.imageUrl}"/>
							<jsp:param name="linkUrl" value="productDetails.jsp?id=${product.id}"/>
        				</jsp:include>
					</c:forEach>
				</div>
			</div>
		</div>
	</body>
</html>
