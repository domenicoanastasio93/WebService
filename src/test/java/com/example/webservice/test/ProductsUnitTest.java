package com.example.webservice.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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

import org.hamcrest.Matchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductsUnitTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mock;
	
	@Before
	public void setUp() throws Exception{
		new Init();
		this.mock = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void ARetrieveProductsEmpty() throws Exception {
		
		mock.perform(get("/products"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(0)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void BCreateProductFalse() throws Exception {
		
		String json = "{\"name\": \"Item\",\"price\": 0}";
		mock.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
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
	
	@Test
	public void DRetrieveProductsNotEmpty() throws Exception {
		
		mock.perform(get("/products"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(1)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void EUpdateProductFalse() throws Exception {
		
		String json = "{\"name\": \"New Item\",\"price\": 0}";
		mock.perform(put("/products/0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void FUpdateProductTrue() throws Exception {
		
		String json = "{\"name\": \"New Item\",\"price\": 10}";
		mock.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(jsonPath("$.name", Matchers.is("New Item")))
		.andExpect(jsonPath("$.price", Matchers.is(10.0)))
		.andExpect(jsonPath("$.*", Matchers.is(Matchers.hasSize(3))))
		.andExpect(status().isOk());
	}
}