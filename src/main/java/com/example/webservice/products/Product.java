package com.example.webservice.products;

import java.io.Serializable;

/**
 * Enity class for products
 * 
 * @author Domenico Anastasio - 2018 ©
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Double price;

	public Product(String name, Double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "{Name: " + name +
				" | Price: " + price + "}";
	}
}
