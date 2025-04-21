<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="../../../css/cart.css">
</head>
<body>
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
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.quantity}</td>
                            <td>${item.price}</td>
                            <td>${item.total}</td>
                            <td>
                                <button class="remove-button" data-id="${item.id}">Remove</button>
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
            <button id="checkout-button">Proceed to Checkout</button>
        </section>
    </main>
    <script src="cart.js"></script>
</body>
</html>