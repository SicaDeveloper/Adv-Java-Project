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
                            <tr data-product-id="${item.id}">
                                <td>
                                    <div class="product-info">
                                        <img src="${pageContext.request.contextPath}/resource/images/products/${item.imageUrl}" alt="${item.name}" class="product-thumbnail">
                                        <span>${item.name}</span>
                                    </div>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/update" method="post" class="quantity-form" onsubmit="return validateQuantity(this)">
                                        <input type="hidden" name="productId" value="${item.id}">
                                        <input type="number" name="quantity" value="${item.cartQuantity}" min="1" max="${item.quantity}"
                                               onchange="this.form.submit()" class="quantity-input">
                                        <span class="error" id="quantityError_${item.id}"></span>
                                    </form>
                                </td>
                                <td>$${item.price}</td>
                                <td>$${item.price * item.cartQuantity}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/cart/delete" method="post" class="remove-form" onsubmit="return confirmRemove(this)">
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
                <c:set var="subtotal" value="0" />
                <c:forEach var="item" items="${cartItems}">
                    <c:set var="subtotal" value="${subtotal + (item.price * item.cartQuantity)}" />
                </c:forEach>
                <p>Subtotal: <span id="subtotal">$${subtotal}</span></p>
                <c:set var="tax" value="${subtotal * 0.13}" />
                <p>Tax (13%): <span id="tax">$${tax}</span></p>
                <br>
                <c:set var="total" value="${subtotal + tax}" />
                <p>Total: <span id="total">$${total}</span></p>
                <a href="${pageContext.request.contextPath}/checkout">
                    <button id="checkout-button">Proceed to Checkout</button>
                </a>
            </c:if>
        </section>
    </main>
    <script src="cart.js"></script>
    <script>
    function validateQuantityInput(input) {
        const quantity = parseInt(input.value);
        const maxQuantity = parseInt(input.max);
        const errorElement = document.getElementById('quantityError_' + input.form.querySelector('[name="productId"]').value);
        
        if (isNaN(quantity) || quantity < 1) {
            errorElement.textContent = 'Quantity must be at least 1';
            input.value = 1;
            return false;
        } else if (quantity > maxQuantity) {
            errorElement.textContent = `Only ${maxQuantity} items available`;
            input.value = maxQuantity;
            return false;
        } else {
            errorElement.textContent = '';
            return true;
        }
    }

    function validateQuantity(form) {
        const quantity = parseInt(form.querySelector('[name="quantity"]').value);
        const maxQuantity = parseInt(form.querySelector('[name="quantity"]').max);
        
        if (isNaN(quantity) || quantity < 1 || quantity > maxQuantity) {
            const productId = form.querySelector('[name="productId"]').value;
            document.getElementById('quantityError_' + productId).textContent = 'Please enter a valid quantity';
            return false;
        }
        
        return true;
    }

    function confirmRemove(form) {
        return confirm('Are you sure you want to remove this item from your cart?');
    }
    </script>
</body>
</html>