package com.tempest.model;

import java.sql.Date;

public class CartModel{
	
	private int id;
	private Date date;
	
	public int getId() {
		return id;	
		}
    // Setter methods

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