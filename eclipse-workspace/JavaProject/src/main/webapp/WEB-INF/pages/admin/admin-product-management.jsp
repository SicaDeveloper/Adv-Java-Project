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
		<table class="table-container">
			<thead>
				<tr class="table-header">
                    <th class="table-header-items"><input type="checkbox" name="" id=""></th>
					<th class="table-header-items">Product ID</th>
					<th class="table-header-items">Product Name</th>
                    <th class="table-header-items">Category</th>
					<th class="table-header-items">Price</th>
					<th class="table-header-items">Quantity</th>
					<th class="table-header-items"></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="product" items="${products}">
                <tr class="table-body">
                    <td class="table-body-items"><input type="checkbox" name="" id=""></td>
                    <td class="table-body-items">${product.id}</td>
                    <td class="table-body-items">${product.name}</td>
                    <td class="table-body-items">${product.category}</td>
                    <td class="table-body-items">${product.price}</td>
                    <td class="table-body-items">
                        <button class="edit-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt=""></button>
                        <button class="delete-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt=""></button>
                    </td>
                </tr>
               </c:forEach>
            </tbody>
		</table>
	</body>
</html>
