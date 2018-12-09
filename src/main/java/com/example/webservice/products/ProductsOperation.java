package com.example.webservice.products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservice.Init;

/**
 * Class for RestController about products
 * 
 * @author Domenico Anastasio - 2018 ©
 */
@RestController
@RequestMapping("/products")
public class ProductsOperation {

	/**
	 * Create a new product
	 * @author Domenico Anastasio - 2018 ©
	 * @param p - New product
	 * @return JSON of the new product
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Product> createProduct(@RequestBody Product p)
			throws SQLException, ClassNotFoundException {
		
		Connection c = Init.startConnection();
		
		if(p.getName() == null || p.getPrice() <= 0)
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		
		String query = "INSERT INTO products(name, price) VALUES(?, ?);";
		PreparedStatement insert = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		insert.setString(1, p.getName());
		insert.setDouble(2, p.getPrice());
		insert.executeUpdate();
		ResultSet result = insert.getGeneratedKeys();
		
		int key = (int) result.getLong(1);
		return new ResponseEntity<Product>(new Product(
				key,
				p.getName(),
				p.getPrice()),
				HttpStatus.OK);
	}
	
	/**
	 * Update a product
	 * @param p - Product to be updated
	 * @return JSON of the updated product
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product p)
			throws SQLException, ClassNotFoundException {
		
		Connection c =Init.startConnection();
		
		if(p.getName() == null || p.getPrice() <= 0)
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		
		PreparedStatement update = c.prepareStatement("UPDATE products"
				+ " SET name = \"" + p.getName() + "\", price = " + p.getPrice()
				+ " WHERE id = " + id);
		int check = update.executeUpdate();
		
		if(check == 0)
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<Product>(new Product(
				id,
				p.getName(),
				p.getPrice()),
				HttpStatus.OK);
	}
	
	/**
	 * Retrieve all products
	 * @return JSON of the list of products
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Product>> retrieveProducts()
			throws SQLException, ClassNotFoundException {
		
		Connection c = Init.startConnection();
		
		PreparedStatement select = c.prepareStatement("SELECT * FROM products");
		ResultSet result = select.executeQuery();
		
		ArrayList<Product> list = new ArrayList<>();
		while(result.next()) {
			
			list.add(new Product(result.getInt("id"),
					result.getString("name"),
					result.getDouble("price")));
		}
		
		return new ResponseEntity<ArrayList<Product>>(list, HttpStatus.OK);
	}
}
