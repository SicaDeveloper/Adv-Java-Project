<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
	<jsp:include page="../homePage/navbar.jsp"></jsp:include>
    <main>
        <section id="cart-items">
            <h2>Your Items</h2>
            <table>
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterate through cart items using c:forEach -->
                    <c:forEach var="cartItems" items="${cartItems}">
                        <tr>
                            <td>${cartItems.name}</td>
                            <td>${cartItems.quantity}</td>
                            <td>${cartItems.price}</td>
                            <td>total</td>
                            <td>
                                <button class="remove-button" data-id="">Remove</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
        
        
        <section id="cart-summary">
            <h2>Summary</h2>
            <p>Subtotal: <span id="subtotal">${subtotal}</span></p>
            <p>Tax: <span id="tax">${tax}</span></p>
            <br>
            <p>Total: <span id="total">${total}</span></p>
            <a href="${pageContext.request.contextPath}/home"><button id="checkout-button">Proceed to Checkout</button></a>
        </section>
    </main>
    <script src="cart.js"></script>
</body>
</html>