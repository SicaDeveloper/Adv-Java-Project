<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
					<div class="product-category">
						<input class="checkbox" type="checkbox" name="" id="" />
						<label for="">Category 1</label>
					</div>
					<div class="product-category">
						<input class="checkbox" type="checkbox" name="" id="" />
						<label for="">Category 2</label>
					</div>
					<div class="product-category">
						<input class="checkbox" type="checkbox" name="" id="" />
						<label for="">Category 3</label>
					</div>
					<div class="product-category">
						<input class="checkbox" type="checkbox" name="" id="" />
						<label for="">Category 4</label>
					</div>
					<div class="product-category">
						<input class="checkbox" type="checkbox" name="" id="" />
						<label for="">Category 5</label>
					</div>
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
					/><img class="search-icon" src="../resource/search.png" alt="" />
				</div>
				<div class="product-list">
					<c:forEach var="products" items="${product}">
						<jsp:include page="product-card.jsp">
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
