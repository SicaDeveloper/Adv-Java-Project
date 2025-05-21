<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tempest.model.ProductModel" %>
<%@ page import="java.util.Arrays" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Products - Tempest</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-listing-page.css" />
	</head>
	<body>
		<div class="product-listing-container">
			<div class="product-categories">
				<div class="product-category-header">Shop by categories</div>
				<form id="categoryFilterForm" action="${pageContext.request.contextPath}/products" method="get">
					<div class="product-category-container">
						<div class="category-list">
							<c:forEach var="category" items="${categories}">
								<div class="category-item">
									<input type="checkbox" id="category_${category.id}" name="categoryIds" value="${category.id}" 
										<c:forEach var="selectedId" items="${paramValues.categoryIds}">
											<c:if test="${selectedId == category.id}">checked</c:if>
										</c:forEach> />
									<label for="category_${category.id}">${category.name}</label>
								</div>
							</c:forEach>
						</div>
						<button type="submit" class="filter-button">Apply Filters</button>
					</div>
				</form>
			</div>
			<div class="product-content">
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
		<script>
			document.getElementById('categoryFilterForm').addEventListener('submit', function(e) {
				e.preventDefault();
				const selectedCategories = Array.from(document.querySelectorAll('input[name="categoryIds"]:checked'))
					.map(checkbox => checkbox.value);
				
				if (selectedCategories.length === 0) {
					window.location.href = '${pageContext.request.contextPath}/products';
					return;
				}
				
				const formData = new FormData();
				selectedCategories.forEach(categoryId => {
					formData.append('categoryIds', categoryId);
				});
				
				const queryString = new URLSearchParams(formData).toString();
				window.location.href = '${pageContext.request.contextPath}/products?' + queryString;
			});
		</script>
	</body>
</html>
