<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orders.css">
</head>
<body>
    <h1>Orders</h1>
    <c:forEach var="order" items="${orders}">
        <section class="order">
            <h2>Order ID: ${order.id}</h2>
            <p>Name: ${order.name}</p>
            <p>Status: ${order.status}</p>
            <p>Total Price: ${order.price}</p>
            <p>Date: ${order.date}</p>

            <h3>Products</h3>
            <table>
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${order.products}">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </c:forEach>
</body>
</html>