<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-customer-management.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Customer Management</h1>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <table class="table-container">
            <thead>
                <tr class="table-header">
                    <th class="table-header-items">ID</th>
                    <th class="table-header-items">Name</th>
                    <th class="table-header-items">Email</th>
                    <th class="table-header-items">Phone</th>
                    <th class="table-header-items">Address</th>
                    <th class="table-header-items">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${customers}" var="customer">
                    <tr class="table-body">
                        <td class="table-body-items">${customer.user_id}</td>
                        <td class="table-body-items">${customer.first_name} ${customer.last_name}</td>
                        <td class="table-body-items">${customer.email}</td>
                        <td class="table-body-items">${customer.phone}</td>
                        <td class="table-body-items">${customer.address}</td>
                        <td class="table-body-items">
                            <a href="${pageContext.request.contextPath}/admin/customer/edit?id=${customer.user_id}" 
                               class="btn-edit">Edit</a>
                            <form action="${pageContext.request.contextPath}/admin/customer/delete" 
                                  method="post" style="display: inline;">
                                <input type="hidden" name="id" value="${customer.user_id}">
                                <button type="submit" class="btn-delete" 
                                        onclick="return confirm('Are you sure you want to delete this customer?')">
                                    Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <div class="navigation">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn">Back to Dashboard</a>
        </div>
    </div>
</body>
</html> 