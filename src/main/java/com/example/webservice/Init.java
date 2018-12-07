package com.example.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.example.webservice.orders.Order;
import com.example.webservice.products.Product;

/**
 * Class used to initialize files and arraylists for products and orders
 * 
 * @author Domenico Anastasio - 2018 ©
 */
public class Init {
	
	private static final long serialVersionUID = 1L;
	public static ArrayList<Product> products;
	public static ArrayList<Order> orders;
	public static File productsFile, ordersFile;
	private ObjectInputStream in;
	
	public Init() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		productsFile = new File("products.dat");
		if(!productsFile.exists()) productsFile.createNewFile();
		
		products = new ArrayList<>();
		if(productsFile.length() > 0) {
			in = new ObjectInputStream(new FileInputStream(productsFile));
			products.addAll((ArrayList<Product>) in.readObject());
		}
		
		ordersFile = new File("orders.dat");
		if(!ordersFile.exists()) ordersFile.createNewFile();
		
		orders = new ArrayList<>();
		if(ordersFile.length() > 0) {
			in = new ObjectInputStream(new FileInputStream(ordersFile));
			orders.addAll((ArrayList<Order>) in.readObject());
		}
	}
}