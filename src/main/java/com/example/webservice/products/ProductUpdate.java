package com.example.webservice.products;

/**
 * Entity class for updating products, extending Product class
 * 
 * @author Domenico Anastasio - 2018 ©
 */
public class ProductUpdate extends Product{
	
	private static final long serialVersionUID = 1L;
	private String newName;
	
	public ProductUpdate(String oldName, String newName, Double price) {
		
		super(oldName, price);
		this.newName = newName;
	}

	public String getNewName() {
		return newName;
	}
}
