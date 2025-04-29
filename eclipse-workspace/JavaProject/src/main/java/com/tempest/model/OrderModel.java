package com.tempest.model;

import java.util.Date;
import java.util.List;

public class OrderModel {
    private int id;
    private String name;
    private String status;
    private double price;
    private Date date;
    private List<ProductModel> products;
    
    public OrderModel(int id, String name, String status, double price, Date date) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.price = price;
        this.date = date;
    }

    public OrderModel(int id, String name, String status, double price, Date date, List<ProductModel> products) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.price = price;
        this.date = date;
        this.products = products; 
    }

    // Getters and setters for all the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", status='" + status + '\'' +
               ", price=" + price +
               ", date=" + date +
               ", products=" + products +
               '}';
    }
}