package com.example.webservice.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdersUnitTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mock;
	
	@Before
	public void setUp() throws Exception{
		new Init();
		this.mock = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void ARetrieveOrdersEmpty() throws Exception {
		
		mock.perform(get("/orders/1"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(0)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void BPlaceOrderFalse() throws Exception {
		
		String json = "{\"email\": \"a@b.com\",\"products\": []}";
		mock.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void CPlaceOrderTrue() throws Exception {
		
		String json = "{\"email\": \"a@b.com\",\"products\": [{\"id\": 0,\"name\": \"New Item\",\"price\": 10}]}";
		mock.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(jsonPath("$.email", Matchers.is("a@b.com")))
		//.andExpect(jsonPath("$.*", Matchers.hasSize(4)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void DRetrieveOrdersNotEmpty() throws Exception{
		
		mock.perform(get("/orders/1"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(1)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void ECalculateAmountFalse() throws Exception{
		
		mock.perform(get("/orders/amount/1"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void FCalculateAmountTrue() throws Exception{
		
		mock.perform(get("/orders/amount/0"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(1)))
		.andExpect(status().isOk());
	}
}
