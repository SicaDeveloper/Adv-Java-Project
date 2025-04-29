<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-history.css">
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Order Name</th>
                <th>Quantity</th>
                <th>Order Date</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>Sample Order</td>
                <td>10</td>
                <td>2025-04-22</td>
            </tr>
        </tbody>
    </table>
</body>
</html>