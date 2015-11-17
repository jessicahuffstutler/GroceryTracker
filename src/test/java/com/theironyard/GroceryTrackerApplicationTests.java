package com.theironyard;

import com.theironyard.services.GroceryRepository;
import com.theironyard.services.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GroceryTrackerApplication.class)
@WebAppConfiguration
public class GroceryTrackerApplicationTests {

	@Autowired
	GroceryRepository groceries;

	@Autowired
	UserRepository users;

	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before //runs before each test and therefore would make it difficult to test edit and delete functions.
	public void before() {
		groceries.deleteAll();
		users.deleteAll();
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
					.param("username", "testu")
					.param("password", "testp")
		);
		assertTrue(users.count() == 1);
	}


	@Test
	public void testAddGrocery() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/add-grocery")
					.param("name", "TestBeer")
					.param("brand", "TestBrand")
					.param("quantity", "10")
					.param("quantityType", "Pound(s)")
					.param("category", "Red Meat")
					.sessionAttr("username", "testu")
		);

		assertTrue(groceries.count() == 1);
	}

	//test edit

	//test delete

}
