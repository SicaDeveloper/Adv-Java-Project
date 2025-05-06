package com.tempest.model;

import java.sql.Date;

public class OrderModel {
    private int id;
    private int userId;
    private String status;
    private Date date;
    private double amount;

    public OrderModel() {
    }

    public OrderModel(int id, String status, Date date, double amount) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.amount = amount;
    }

    public OrderModel(int id, int userId, String status, Date date, double amount) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Alias methods for compatibility
    public Date getOrderDate() {
        return getDate();
    }

    public void setOrderDate(Date date) {
        setDate(date);
    }

    public double getTotalAmount() {
        return getAmount();
    }

    public void setTotalAmount(double amount) {
        setAmount(amount);
    }
}