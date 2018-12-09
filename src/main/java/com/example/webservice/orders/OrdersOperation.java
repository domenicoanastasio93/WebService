package com.example.webservice.orders;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
		
		boolean check;
		for(int i=0; i<o.getProducts().size(); i++) {
			
			check = false;
			for(int j=0; j<Init.products.size(); j++) {
				
				if(o.getProducts().get(i).getId() == (Init.products.get(i).getId())){			
					check = true;
				}
			}
			
			if(!check) return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");    
		Date date = new Date(System.currentTimeMillis());
		
		int newID = Init.orders.size();
		Order newOrder = new Order(newID, o.getEmail(), o.getProducts(), format.format(date));
		Init.orders.add(newOrder);
		Init.writeOrders();
		
		return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
	}
	
	/**
	 * Calculate the amount of an order
	 * @param ID - ID of the order to be calculated
	 * @return
	 */
	@RequestMapping(value="/amount/{id}", method=RequestMethod.GET)
	public ResponseEntity<Double> calculateAmount(@PathVariable Integer id) {
		
		if(id < 0 || id >= Init.orders.size())
			return new ResponseEntity<Double>(HttpStatus.BAD_REQUEST);
		
		ArrayList<Product> list = Init.orders.get(id).getProducts();
		Double sum = 0.0;
		
		for(int i=0; i<list.size(); i++) {
			sum = sum + list.get(i).getPrice();
		}
		
		return new ResponseEntity<Double>(sum, HttpStatus.OK);
	}
	
	/**
	 * Retrieve all orders within a given time period
	 * @param days - numbers of days ago (e.g. 2 days = retrieve orders from 2 days ago to now)
	 * @return list of all orders from a specific day to now
	 */
	@RequestMapping(value="{days}", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Order>> retrieveOrders(@PathVariable Integer days) {
		
		if(days < 1)
			return new ResponseEntity<ArrayList<Order>>(HttpStatus.BAD_REQUEST);
		
		long time = 86400000 * days, ms = 0;
		ArrayList<Order> list = new ArrayList<>();
		
		for(int i=0; i<Init.orders.size(); i++) {
			
			String stringDate = Init.orders.get(i).getTime();

			SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
			try {
			    Date d = f.parse(stringDate);
			    ms = d.getTime();
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			
			if(System.currentTimeMillis()-ms <= time)
				list.add(Init.orders.get(i));
		}
		
		return new ResponseEntity<ArrayList<Order>>(list, HttpStatus.OK);
	}
}
