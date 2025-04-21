<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Order Management</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-order-management.css" />
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
					<th class="table-header-items">Order ID</th>
					<th class="table-header-items">Customer Name</th>
                    <th class="table-header-items">Status</th>
					<th class="table-header-items">Total Price</th>
					<th class="table-header-items">Date</th>
					<th class="table-header-items"></th>
				</tr>
			</thead>
			<tbody>
                <c:forEach var="order" items="${yourListOfItems}" varStatus="loop">
				    <tr class="table-body">
				        <td class="table-body-items"><input type="checkbox" name="" id=""></td>
				        <td class="table-body-items">${order.id}</td>
				        <td class="table-body-items">${order.name}</td> 
				        <td class="table-body-items">${order.status}</td> 
				        <td class="table-body-items">${order.price}</td> 
				        <td class="table-body-items">${order.date}</td> 
				        <td class="table-body-items">
				           <form action="editOrder" method="post" style="display:inline;">
					                <input type="hidden" name="id" value="${order.id}">
					                <button type="submit" class="edit-button">
					                    <img class="icon-button" src="${pageContext.request.contextPath}/resource/edit-text.png" alt="Edit">
					                </button>
            				</form>
				            <form action="deleteOrder" method="post" style="display:inline;">
				                <input type="hidden" name="id" value="${order.id}">
				                <button type="submit" class="delete-button" onclick="return confirm('Are you sure you want to delete this order?')">
				                    <img class="icon-button" src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Delete">
				                </button>
				            </form>
        				</td>
				    </tr>
				</c:forEach>
            </tbody>
		</table>
	</body>
</html>

