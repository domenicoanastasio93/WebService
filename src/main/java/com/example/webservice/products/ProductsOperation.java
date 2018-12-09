package com.example.webservice.products;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	private static final long serialVersionUID = 1L;
	private ObjectOutputStream out;

	/**
	 * Create a new product
	 * @author Domenico Anastasio - 2018 ©
	 * @param p - new product
	 * @return JSON of the new product
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Product> createProduct(@RequestBody Product p)
			throws FileNotFoundException, IOException {
		
		if(p.getName() == null || p.getPrice() <= 0)
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		
		int newID = Init.products.size();
		Product newP = new Product(newID, p.getName(), p.getPrice());
		Init.products.add(newP);
		Init.writeProducts();
		
		return new ResponseEntity<Product>(newP, HttpStatus.OK);
	}
	
	/**
	 * Update a product
	 * @param p - product to be updated
	 * @return JSON of the updated product
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product p)
			throws FileNotFoundException, IOException {
		
		if(id < 0 || id >= Init.products.size() || p.getName() == null || p.getPrice() <= 0)
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		
		for(int i=0; i<Init.products.size(); i++) {

			if(Init.products.get(i).getId().equals(p.getId())) {
				
				Init.products.get(i).setName(p.getName());
				Init.products.get(i).setPrice(p.getPrice());
				Init.writeProducts();
				
				return new ResponseEntity<Product>(Init.products.get(i), HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Retrieve all products
	 * @return JSON of the list of products
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<ArrayList<Product>> retrieveProducts() {
		
		return new ResponseEntity<ArrayList<Product>>(Init.products, HttpStatus.OK);
	}

}
