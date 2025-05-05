<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Product Management</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-product-management.css" />
		<link
			href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
			rel="stylesheet"
		/>
	</head>
	<body>
		<div class="navigation-bar">
			
		</div>
		<table class="table-container">
			<thead>
				<tr class="table-header">
                    <th class="table-header-items"><input type="checkbox" name="" id=""></th>
					<th class="table-header-items">Product ID</th>
					<th class="table-header-items">Product Name</th>
                    <th class="table-header-items">Category</th>
					<th class="table-header-items">Price</th>
					<th class="table-header-items">Quantity</th>
					<th class="table-header-items">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="products" items="${products}">
	                <tr class="table-body">
	                    <td class="table-body-items">
	                        <input type="checkbox" name="" id="">
	                    </td>
	                    <td class="table-body-items">${products.id}</td>
	                    <td class="table-body-items">${products.name}</td>
	                    <td class="table-body-items">
	                        <c:set var="category" value="${productService.getProductCategory(products.id)}" />
	                        ${category != null ? category.name : 'No Category'}
	                    </td>
	                    <td class="table-body-items">${products.price}</td>
	                    <td class="table-body-items">${products.quantity}</td>
	                    <td class="table-body-items">
	                        <a href="${pageContext.request.contextPath}/admin/product/edit?productId=${products.id}" class="edit-button">
	                            <img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt="Edit">
	                        </a>
	                        <a href="${pageContext.request.contextPath}/admin/product/delete?productId=${products.id}" class="delete-button">
	                            <img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Delete">
	                        </a>
	                    </td>
	                </tr>
	               </c:forEach>
            </tbody>
		</table>
		<a href="${pageContext.request.contextPath}/admin/product/add" class="add-product-button">Add New Product</a>
	</body>
</html>
