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
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <c:if test="${empty cartItems}">
                <p class="empty-cart">Your cart is empty</p>
            </c:if>
            <c:if test="${not empty cartItems}">
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
                        <c:forEach var="item" items="${cartItems}">
                            <tr>
                                <td>
                                    <div class="product-info">
                                        <img src="${pageContext.request.contextPath}${item.imageUrl}" alt="${item.name}" class="product-thumbnail">
                                        <span>${item.name}</span>
                                    </div>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/update" method="post" class="quantity-form">
                                        <input type="hidden" name="productId" value="${item.id}">
                                        <input type="number" name="quantity" value="${item.quantity}" min="1" 
                                               onchange="this.form.submit()" class="quantity-input">
                                    </form>
                                </td>
                                <td>$${item.price}</td>
                                <td>$${item.price * item.quantity}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/remove" method="post" class="remove-form">
                                        <input type="hidden" name="productId" value="${item.id}">
                                        <button type="submit" class="remove-button">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </section>
        
        <section id="cart-summary">
            <h2>Summary</h2>
            <c:if test="${not empty cartItems}">
                <p>Subtotal: <span id="subtotal">$${subtotal}</span></p>
                <p>Tax (13%): <span id="tax">$${tax}</span></p>
                <br>
                <p>Total: <span id="total">$${total}</span></p>
                <a href="${pageContext.request.contextPath}/checkout">
                    <button id="checkout-button">Proceed to Checkout</button>
                </a>
            </c:if>
        </section>
    </main>
    <script src="cart.js"></script>
</body>
</html>