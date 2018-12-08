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
	private Integer id;
	private String email;
	private ArrayList<Product> products;
	private String time;
	
	/**
	 * @param id - Order ID
	 * @param email - Buyer's email
	 * @param products - List of products related to the order
	 * @param time - Time when the order was placed
	 */
	public Order(Integer id, String email, ArrayList<Product> products, String time) {
		this.id = id;
		this.email = email;
		this.products = products;
		this.time = time;
	}

	public Integer getId() {
		return id;
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
