<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<link rel="preconnect" href="https://fonts.googleapis.com" />
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
		<link
			href="https://fonts.googleapis.com/css2?family=Rubik+Mono+One&display=swap"
			rel="stylesheet"
		/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main-content.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-card.css" />
	</head>
	<body>
		<div class="main-content">
			<div class="slogan">SUCCESS ISN'T GIVEN</div>
			<div class="featured-products">
				<div class="featured-products-title">Featured Products</div>
				<div class="featured-product-list">
					<c:forEach var="products" items="${product}">
						<jsp:include page="${pageContext.request.contextPath}/productPage/product-card.jsp">
							<jsp:param name="name" value="${product.name}"/>
							<jsp:param name="price" value="${product.price}"/>
							<jsp:param name="imageUrl" value="${product.imageUrl}"/>
							<jsp:param name="linkUrl" value="productDetails.jsp?id=${product.id}"/>
        				</jsp:include>
					</c:forEach>
				</div>
					<button class="shop-now"><a href="${pageContext.request.contextPath}/products">Shop Now</a></button>
			</div>
		</div>
	</body>
</html>
