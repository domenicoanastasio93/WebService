package com.example.webservice.products;

import java.io.Serializable;

/**
 * Enity class for products
 * 
 * @author Domenico Anastasio - 2018 ©
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Double price;

	/**
	 * @param id - Product ID
	 * @param name - Product name
	 * @param price - Product price
	 */
	public Product(Integer id, String name, Double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public Integer getId() {
		return id;
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
}
