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
					<c:forEach var="featuredProducts" items="${featuredProducts}">
						<jsp:include page="../productPage/product-card.jsp">
							<jsp:param name="id" value="${featuredProducts.id}"/>
							<jsp:param name="name" value="${featuredProducts.name}"/>
							<jsp:param name="price" value="${featuredProducts.price}"/>
							<jsp:param name="imageUrl" value="${featuredProducts.imageUrl}"/>
        				</jsp:include>
					</c:forEach>
				</div>
				<div class="shop-now">
					<a href="${pageContext.request.contextPath}/products">
					<button class="shop-now-button">Shop Now</button>
					</a>
				</div>
			</div>
		</div>
	</body>
</html>
