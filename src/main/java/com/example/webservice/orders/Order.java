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
	private Long time;
	
	public Order(Integer ID, String email, ArrayList<Product> products, Long time) {
		this.ID = ID;
		this.email = email;
		this.products = products;
		this.time = time;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");    
		Date date = new Date(time);
		
		return "{ID: " + ID +
				" | Buyer's e-mail: " + email +
				" | Products list: " + products +
				" | Placed time: " + format.format(date) + "}";
	}

}
