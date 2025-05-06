<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Order</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/add-order.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <script>
        function addProduct(productId) {
            fetch('${pageContext.request.contextPath}/admin/order/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'action=addProduct&selectedProductId=' + productId
            })
            .then(response => {
                if (response.ok) {
                    location.reload();
                }
            });
        }

        function removeProduct(productId) {
            fetch('${pageContext.request.contextPath}/admin/order/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'action=removeProduct&productId=' + productId
            })
            .then(response => {
                if (response.ok) {
                    location.reload();
                }
            });
        }

        function updateQuantity(productId, quantity) {
            fetch('${pageContext.request.contextPath}/admin/order/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'action=updateQuantity&productId=' + productId + '&quantity=' + quantity
            })
            .then(response => {
                if (response.ok) {
                    location.reload();
                }
            });
        }

        function validateForm() {
            const customerId = document.getElementById('customerId').value;
            if (!customerId) {
                alert('Please select a customer');
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <div class="main-container">
        <div class="order-header">
            <h2>ADD ORDER</h2>
            <p>Create a new order by selecting products and quantities</p>
        </div>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <div class="content-wrapper">
            <div class="left-column">
                <div class="customer-selector">
                    <div class="selector-header">
                        <h3>Select Customer</h3>
                    </div>
                    <div class="selector-content">
                        <select id="customerId" name="customerId" class="customer-dropdown" required>
                            <option value="">Select a customer...</option>
                            <c:forEach var="customer" items="${customers}">
                                <option value="${customer.user_id}">
                                    ${customer.first_name} ${customer.last_name} (${customer.email})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="product-selector">
                    <div class="selector-header">
                        <h3>Add Products</h3>
                    </div>
                    <div class="selector-content">
                        <select id="productSelect" class="product-dropdown">
                            <option value="">Select a product...</option>
                            <c:forEach var="availableProduct" items="${availableProducts}">
                                <option value="${availableProduct.id}">
                                    ${availableProduct.name} - $${availableProduct.price}
                                </option>
                            </c:forEach>
                        </select>
                        <button type="button" class="add-product-btn" onclick="addProduct(document.getElementById('productSelect').value)">
                            <span>+</span> Add Product
                        </button>
                    </div>
                </div>
            </div>

            <div class="order-items" id="orderItems">
                <c:forEach var="selectedProduct" items="${selectedProducts}">
                    <div class="product-card" data-product-id="${selectedProduct.id}">
                        <div class="product-image">
                            <img src="${pageContext.request.contextPath}${selectedProduct.imageUrl}" alt="${selectedProduct.name}">
                        </div>
                        <div class="product-details">
                            <div class="product-info">
                                <h3>${selectedProduct.name}</h3>
                                <p class="product-id">Product ID: ${selectedProduct.id}</p>
                                <p class="product-price">$${selectedProduct.price}</p>
                            </div>
                            <div class="product-actions">
                                <div class="quantity-selector">
                                    <label>Quantity:</label>
                                    <input type="number" name="quantity_${selectedProduct.id}" 
                                           min="1" max="${selectedProduct.quantity}" 
                                           value="1"
                                           onchange="updateQuantity(${selectedProduct.id}, this.value)">
                                </div>
                                <button type="button" class="remove-btn" onclick="removeProduct(${selectedProduct.id})">
                                    <img src="${pageContext.request.contextPath}/resource/trash-bin.png" alt="Remove">
                                </button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="order-summary">
                <h3>SUMMARY</h3>
                <div class="summary-details">
                    <div class="summary-row">
                        <span>Subtotal</span>
                        <span class="amount">$<span id="subtotal">${subtotal}</span></span>
                    </div>
                    <div class="summary-row">
                        <span>Shipping</span>
                        <span class="amount">$<span id="shipping">8.99</span></span>
                    </div>
                    <div class="summary-row total">
                        <span>Total</span>
                        <span class="amount">USD $<span id="total">${total}</span></span>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/admin/order/add" method="post" id="orderForm" onsubmit="return validateForm()">
                    <input type="hidden" name="action" value="createOrder">
                    <input type="hidden" name="totalAmount" value="${total}">
                    <input type="hidden" name="status" value="pending">
                    <input type="hidden" name="customerId" id="customerIdHidden">
                    <button type="submit" class="checkout-btn">CREATE ORDER</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Update the hidden customerId field when the dropdown changes
        document.getElementById('customerId').addEventListener('change', function() {
            document.getElementById('customerIdHidden').value = this.value;
        });
    </script>
</body>
</html> 