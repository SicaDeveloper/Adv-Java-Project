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
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="sidebar-logo">TEMPEST</a>
			<a href="${pageContext.request.contextPath}/admin/product" class="sidebar-item">Products</a>
			<a href="${pageContext.request.contextPath}/admin/order" class="sidebar-item">Orders</a>
			<a href="${pageContext.request.contextPath}/admin/customer" class="sidebar-item">Customers</a>
			<a href="${pageContext.request.contextPath}/admin/setting" class="sidebar-item">Settings</a>
		</div>
	</div>
</body>
</html>