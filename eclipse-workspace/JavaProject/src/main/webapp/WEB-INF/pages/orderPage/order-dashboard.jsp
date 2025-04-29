<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-dashboard.css">
</head>
<body>
    <!-- Include Navbar -->
    <jsp:include page="../homePage/navbar.jsp" />

    <main>
        <!-- Include Create Order Section -->
        <jsp:include page="create-order.jsp" />

        <!-- Include Order History Section -->
        <jsp:include page="order-history.jsp" />

        <!-- Link to Orders Page -->
        <section>
            <h2>View All Orders</h2>
            <a href="${pageContext.request.contextPath}/WEB-INF/pages/orderPage/orders.jsp" class="view-orders-btn">Go to Orders</a>
        </section>
    </main>

    <script>
        // Example JavaScript to handle form submission
        document.getElementById('orderForm').addEventListener('submit', function(event) {
            event.preventDefault();
            alert('Order created successfully!');
            document.getElementById('orderForm').reset();
        });
    </script>
</body>
</html>