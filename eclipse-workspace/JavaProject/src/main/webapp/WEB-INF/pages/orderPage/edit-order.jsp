<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Order</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-order-management.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="form-container">
        <h2>Edit Order</h2>
        <form action="${pageContext.request.contextPath}/admin/order/edit" method="post">
            <input type="hidden" name="orderId" value="${order.id}">
            <div class="form-group">
                <label for="userId">User ID:</label>
                <input type="number" id="userId" name="userId" value="${order.userId}" required>
            </div>
            <div class="form-group">
                <label for="totalAmount">Total Amount:</label>
                <input type="number" id="totalAmount" name="totalAmount" value="${order.totalAmount}" step="0.01" required>
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <select id="status" name="status" required>
                    <option value="pending" ${order.status == 'pending' ? 'selected' : ''}>Pending</option>
                    <option value="processing" ${order.status == 'processing' ? 'selected' : ''}>Processing</option>
                    <option value="shipped" ${order.status == 'shipped' ? 'selected' : ''}>Shipped</option>
                    <option value="delivered" ${order.status == 'delivered' ? 'selected' : ''}>Delivered</option>
                    <option value="cancelled" ${order.status == 'cancelled' ? 'selected' : ''}>Cancelled</option>
                </select>
            </div>
            <div class="form-actions">
                <button type="submit" class="submit-button">Update Order</button>
                <a href="${pageContext.request.contextPath}/admin/order" class="cancel-button">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html> 