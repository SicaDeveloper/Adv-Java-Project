<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-sidebar.css" />
</head>
<body>
	<div class="dashboard-sidebar">
		<div class="sidebar-items">
            <button class="sidebar-logo">TEMPEST</button>
			<a href="${pageContext.request.contextPath}/admin/product/add" class="sidebar-item">Products</a>
			<a href="${pageContext.request.contextPath}/admin/dashboard" class="sidebar-item">Orders</a>
			<a href="${pageContext.request.contextPath}/admin/dashboard" class="sidebar-item">Customers</a>
			<a href="${pageContext.request.contextPath}/admin/dashboard" class="sidebar-item">Settings</a>
		</div>
	</div>
</body>
</html>