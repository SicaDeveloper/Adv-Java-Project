<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section>
    <h2>Create New Order</h2>
    <form id="orderForm">
        <label for="orderName">Order Name:</label>
        <input type="text" id="orderName" name="orderName" required>
        
        <label for="orderQuantity">Quantity:</label>
        <input type="number" id="orderQuantity" name="orderQuantity" required>
        
        <label for="orderDate">Order Date:</label>
        <input type="date" id="orderDate" name="orderDate" required>
        
        <button type="submit">Create Order</button>
    </form>
</section>