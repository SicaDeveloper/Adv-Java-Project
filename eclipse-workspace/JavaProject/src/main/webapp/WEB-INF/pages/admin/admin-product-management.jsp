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
                <tr class="table-body">
                    <td class="table-body-items"><input type="checkbox" name="" id=""></td>
                    <td class="table-body-items">1</td>
                    <td class="table-body-items">Product 1</td>
                    <td class="table-body-items">Shoes</td>
                    <td class="table-body-items">$100</td>
                    <td class="table-body-items">10</td>
                    <td class="table-body-items">
                        <button class="edit-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt=""></button>
                        <button class="delete-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt=""></button>
                    </td>
                </tr>
                <tr class="table-body">
    <td class="table-body-items"><input type="checkbox" name="" id=""></td>
    <td class="table-body-items">2</td>
    <td class="table-body-items">Awesome Gadget X</td>
    <td class="table-body-items">Electronics</td>
    <td class="table-body-items">$49.99</td>
    <td class="table-body-items">5</td>
    <td class="table-body-items">
        <button class="edit-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt="Edit"></button>
        <button class="delete-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Delete"></button>
    </td>
</tr>
<tr class="table-body">
    <td class="table-body-items"><input type="checkbox" name="" id=""></td>
    <td class="table-body-items">3</td>
    <td class="table-body-items">Cozy Knit Sweater</td>
    <td class="table-body-items">Apparel</td>
    <td class="table-body-items">$75.00</td>
    <td class="table-body-items">15</td>
    <td class="table-body-items">
        <button class="edit-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt="Edit"></button>
        <button class="delete-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Delete"></button>
    </td>
</tr>
<tr class="table-body">
    <td class="table-body-items"><input type="checkbox" name="" id=""></td>
    <td class="table-body-items">4</td>
    <td class="table-body-items">Gourmet Coffee Beans</td>
    <td class="table-body-items">Grocery</td>
    <td class="table-body-items">$12.50</td>
    <td class="table-body-items">20</td>
    <td class="table-body-items">
        <button class="edit-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt="Edit"></button>
        <button class="delete-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Delete"></button>
    </td>
</tr>
<tr class="table-body">
    <td class="table-body-items"><input type="checkbox" name="" id=""></td>
    <td class="table-body-items">5</td>
    <td class="table-body-items">Ergonomic Office Chair</td>
    <td class="table-body-items">Furniture</td>
    <td class="table-body-items">$299.99</td>
    <td class="table-body-items">3</td>
    <td class="table-body-items">
        <button class="edit-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt="Edit"></button>
        <button class="delete-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Delete"></button>
    </td>
</tr>
<tr class="table-body">
    <td class="table-body-items"><input type="checkbox" name="" id=""></td>
    <td class="table-body-items">6</td>
    <td class="table-body-items">Organic Green Tea</td>
    <td class="table-body-items">Beverages</td>
    <td class="table-body-items">$8.75</td>
    <td class="table-body-items">30</td>
    <td class="table-body-items">
        <button class="edit-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt="Edit"></button>
        <button class="delete-button"><img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Delete"></button>
    </td>
</tr>
            </tbody>
		</table>
	</body>
</html>
