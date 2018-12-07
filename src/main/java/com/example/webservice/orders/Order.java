package com.example.webservice.orders;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.webservice.products.Product;

/**
 * Entity class for orders
 * 
 * @author Domenico Anastasio - 2018 ©
 */
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer ID;
	private String email;
	private ArrayList<Product> products;
	private String time;
	
	public Order(Integer ID, String email, ArrayList<Product> products, String time) {
		this.ID = ID;
		this.email = email;
		this.products = products;
		this.time = time;
	}

	public Integer getID() {
		return ID;
	}

	public String getEmail() {
		return email;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
