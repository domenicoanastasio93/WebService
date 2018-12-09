package com.example.webservice.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.webservice.Init;

import java.io.File;
import org.hamcrest.Matchers;

/**
 * Unit tests class
 * 
 * @author Domenico Anastasio - 2018 ©
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mock;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		
		File file = new File("database/database.db");
		file.delete();
		
		new Init();
	}
	
	@Before
	public void setUpBefore() {
		this.mock = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	/**
	 * Test A: Retrieve all products
	 * List is empty
	 * Expect: Result has size 0 && Http status is "Ok" (200)
	 * @throws Exception
	 */
	@Test
	public void ARetrieveProductsEmpty() throws Exception {
		
		mock.perform(get("/products"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(0)))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test B: Create a new product
	 * Post: {"name": "Item", "price": 0}
	 * Error: price has value 0
	 * Expect: Http status is "Bad Request" (400)
	 * @throws Exception
	 */
	@Test
	public void BCreateProductFalse() throws Exception {
		
		String json = "{\"name\": \"Item\",\"price\": 0}";
		mock.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test C: Create e new product
	 * Post: {"name": "Item", "price": 5}
	 * Expect: name is "Item" && price is 5.0 && Result has size 3 (id+name+price) && Http status is "Ok" (200)
	 * @throws Exception
	 */
	@Test
	public void CCreateProductTrue() throws Exception {
		
		String json = "{\"name\": \"Item\",\"price\": 5}";
		mock.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(jsonPath("$.name", Matchers.is("Item")))
		.andExpect(jsonPath("$.price", Matchers.is(5.0)))
		.andExpect(jsonPath("$.*", Matchers.is(Matchers.hasSize(3))))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test D: Retrieve all products
	 * List has 1 product
	 * Expect: Result has size 1 && Http status is "Ok" (200)
	 * @throws Exception
	 */
	@Test
	public void DRetrieveProductsNotEmpty() throws Exception {
		
		mock.perform(get("/products"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(1)))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test E: Update a product
	 * Product id: 0
	 * Put: {"name": "New Item", "price": 0}
	 * Error: price value is 0
	 * Expect: Http status is "Bad Request" (400)
	 * @throws Exception
	 */
	@Test
	public void EUpdateProductFalse() throws Exception {
		
		String json = "{\"name\": \"New Item\",\"price\": 0}";
		mock.perform(put("/products/0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test F: Update a product
	 * Product id: 0
	 * Put: {"name": "New Item", "price": 10}
	 * Expect: name is "New Item" && price is 10.0 && Result has size 3 (id+name+price) && Http status is "Ok" (200)
	 * @throws Exception
	 */
	@Test
	public void FUpdateProductTrue() throws Exception {
		
		String json = "{\"id\": 1,\"name\": \"New Item\",\"price\": 10}";
		mock.perform(put("/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(jsonPath("$.id", Matchers.is(1)))
		.andExpect(jsonPath("$.name", Matchers.is("New Item")))
		.andExpect(jsonPath("$.price", Matchers.is(10.0)))
		.andExpect(jsonPath("$.*", Matchers.is(Matchers.hasSize(3))))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test G: Retrieve all orders from yesterday
	 * List is empty
	 * Expect: Result has size 1 && Http status is "Ok" (200)
	 * @throws Exception
	 */
	@Test
	public void GRetrieveOrdersEmpty() throws Exception {
		
		mock.perform(get("/orders/1"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(0)))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test H: Place an order
	 * Post: {"email": "a@b", "products": []}
	 * Error: products list is empty
	 * Expect: Http status is "Bad Request" (400)
	 * @throws Exception
	 */
	@Test
	public void HPlaceOrderFalse() throws Exception {
		
		String json = "{\"email\": \"a@b.com\",\"products\": []}";
		mock.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
}