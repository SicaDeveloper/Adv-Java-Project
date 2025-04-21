<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Admin Dashboard</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-dashboard.css" />
		<link rel="preconnect" href="https://fonts.googleapis.com" />
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
		<link
			href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
			rel="stylesheet"
		/>
	</head>
	<body>
		<div class="dashboard-container">
			<div class="dashboard-sidebar">
				<div class="sidebar-items">
                    <button class="sidebar-logo">TEMPEST</button>
					<button class="sidebar-item"><img class="sidebar-item-icon" src="${pageContext.request.contextPath}/resource/shopping_cart.svg" alt="" />Products</button>
					<button class="sidebar-item"><img class="sidebar-item-icon" src="${pageContext.request.contextPath}/resource/shopping_cart.svg" alt="" />Orders</button>
					<button class="sidebar-item"><img class="sidebar-item-icon" src="${pageContext.request.contextPath}/resource/person.svg" alt="" />Customers</button>
					<button class="sidebar-item"><img class="sidebar-item-icon" src="${pageContext.request.contextPath}/resource/" alt="" />Settings</button>
				</div>
			</div>
			<div class="dashboard-main">
			<jsp:include page="admin-order-management.jsp">
			</jsp:include>
			</div>
		</div>
	</body>
</html>
