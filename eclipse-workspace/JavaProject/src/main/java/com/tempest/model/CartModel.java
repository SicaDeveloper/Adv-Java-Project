package com.tempest.model;

import java.util.Date;

public class CartModel {
	private int id;
	private Date date;

	public CartModel() {
	}

	public CartModel(int id, Date date) {
		this.id = id;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}