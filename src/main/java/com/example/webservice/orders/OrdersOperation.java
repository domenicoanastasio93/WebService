package com.example.webservice.orders;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
	
	private static final long serialVersionUID = 1L;
	private ObjectOutputStream out;

	/**
	 * Placing and order
	 * @author Domenico Anastasio - 2018 ©
	 * @param o - order to be placed
	 * @return JSON of the new order
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Order> placeOrder(@RequestBody Order o)
			throws FileNotFoundException, IOException {
		
		if(o.getEmail() == null || o.getProducts() == null || o.getProducts().size() == 0)
			return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
		
		Order newOrder = new Order(Init.orders.size(), o.getEmail(), o.getProducts(), System.currentTimeMillis());
		Init.orders.add(newOrder);
		
		out = new ObjectOutputStream(new FileOutputStream(Init.ordersFile));
		out.writeObject(Init.orders);
		
		return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
	}
	
	/**
	 * Calculate the amount of an order
	 * @param ID - ID of the order to be calculated
	 * @return
	 */
	@RequestMapping(value="/amount/{ID}", method=RequestMethod.GET)
	public ResponseEntity<Double> calculateAmount(@PathVariable Integer ID) {
		
		if(ID < 0 || ID >= Init.orders.size())
			return new ResponseEntity<Double>(HttpStatus.BAD_REQUEST);
		
		ArrayList<Product> list = Init.orders.get(ID).getProducts();
		Double sum = 0.0;
		
		for(int i=0; i<list.size(); i++) {
			sum = sum + list.get(i).getPrice();
		}
		
		return new ResponseEntity<Double>(sum, HttpStatus.OK);
	}
	
	/**
	 * Retrieve all orders within a given time period
	 * @param days - numbers of days start from (e.g. 2 days = from 2 days ago to now)
	 * @return list of all orders from a specific day to now
	 */
	@RequestMapping(value="{days}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Order>> retrieveOrders(@PathVariable Integer days) {
		
		if(days < 1)
			return new ResponseEntity<ArrayList<Order>>(HttpStatus.BAD_REQUEST);
		
		long time = 86400000 * days;
		ArrayList<Order> list = new ArrayList<>();
		
		for(int i=0; i<Init.orders.size(); i++) {
			
			if(System.currentTimeMillis()-Init.orders.get(i).getTime() <= time)
				list.add(Init.orders.get(i));
		}
		
		return new ResponseEntity<ArrayList<Order>>(list, HttpStatus.OK);
	}
}
