package com.example.webservice.orders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservice.Init;
import com.example.webservice.products.Product;

/**
 * Class for RestController about orders
 * 
 * @author Domenico Anastasio - 2018 ©
 */
@RestController
@RequestMapping("/orders")
public class OrdersOperation {

	/**
	 * Placing and order
	 * @author Domenico Anastasio - 2018 ©
	 * @param o - Order to be placed
	 * @return JSON of the new order
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Order> placeOrder(@RequestBody Order o)
			throws SQLException, ClassNotFoundException {
		
		Connection c =Init.startConnection();
		
		if(o.getEmail() == null)
			return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
		
		int count = 0;
		for(int i=0; i<o.getProducts().size(); i++) {
			
			PreparedStatement select = c.prepareStatement("SELECT * FROM products"
					+ " WHERE id = " + o.getProducts().get(i).getId());
			ResultSet result = select.executeQuery();
			
			if(result.next()) count++;
		}

		if(count == 0)
			return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");    
		Date date = new Date(System.currentTimeMillis());
		String timeNow = format.format(date);

		String query2 = "INSERT INTO orders(email, time) VALUES(?, ?);";
		PreparedStatement insert2 = c.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
		insert2.setString(1, o.getEmail());
		insert2.setString(2, timeNow);
		insert2.executeUpdate();
		ResultSet result2 = insert2.getGeneratedKeys();
		
		for(int i=0; i<o.getProducts().size(); i++) {
			
			String query1 = "INSERT INTO ordered_products(id_order, id_product, name, price) VALUES(?, ?, ?, ?);";
			PreparedStatement insert1 = c.prepareStatement(query1);
			insert1.setInt(1, (int) result2.getLong(1));
			insert1.setInt(2, o.getProducts().get(i).getId());
			insert1.setString(3, o.getProducts().get(i).getName());
			insert1.setDouble(4, o.getProducts().get(i).getPrice());
			insert1.executeUpdate();
			
		}
		
		return new ResponseEntity<Order>(new Order(
				(int) result2.getLong(1),
				o.getEmail(),
				o.getProducts(),
				timeNow),
				HttpStatus.OK);
	}
	
	/**
	 * Calculate the amount of an order
	 * @param id - Id of the order to be calculated
	 * @return JSON of the amount
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="/amount/{id}", method=RequestMethod.GET)
	public ResponseEntity<Double> calculateAmount(@PathVariable Integer id)
			throws SQLException, ClassNotFoundException {
		
		Connection c =Init.startConnection();
		
		PreparedStatement select = c.prepareStatement("SELECT * FROM orders"
				+ " WHERE id = " + id);
		ResultSet result = select.executeQuery();
		
		if(!result.next())
			return new ResponseEntity<Double>(HttpStatus.BAD_REQUEST);
		
		PreparedStatement select2 = c.prepareStatement("SELECT * FROM ordered_products"
				+ " WHERE id_order = " + id);
		ResultSet result2 = select2.executeQuery();

		Double sum = 0.0;
		
		while(result2.next()) {sum = sum + result2.getDouble("price");}
		
		return new ResponseEntity<Double>(sum, HttpStatus.OK);
	}
	
	/**
	 * Retrieve all orders within a given time period
	 * @param days - Numbers of days ago (e.g. 2 days = retrieve orders from 2 days ago to now)
	 * @return List of all orders from a specific day to now
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value="{days}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Order>> retrieveOrders(@PathVariable Integer days)
			throws SQLException, ClassNotFoundException {
		
		Connection c =Init.startConnection();
		
		if(days < 1)
			return new ResponseEntity<ArrayList<Order>>(HttpStatus.BAD_REQUEST);
		
		long time = 86400000 * days, ms = 0;
		ArrayList<Order> list = new ArrayList<>();
		
		PreparedStatement select = c.prepareStatement("SELECT * FROM orders");
		ResultSet result = select.executeQuery();
		
		while(result.next()) {
			
			ArrayList<Product> list2 = new ArrayList<>();
			
			PreparedStatement select2 = c.prepareStatement("SELECT * FROM ordered_products"
					+ " WHERE id_order = " + result.getInt("id"));
			ResultSet result2 = select2.executeQuery();
			
			while(result2.next()) {

				list2.add(new Product(
						result2.getInt("id_product"),
						result2.getString("name"),
						result2.getDouble("price")));
			}
			
			String stringDate = result.getString("time");
			SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
			try {
			    Date d = f.parse(stringDate);
			    ms = d.getTime();
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			
			if(System.currentTimeMillis()-ms <= time)
				list.add(new Order(
						result.getInt("id"),
						result.getString("email"),
						list2,
						result.getString("time")));
		}
		
		return new ResponseEntity<ArrayList<Order>>(list, HttpStatus.OK);
	}
}
