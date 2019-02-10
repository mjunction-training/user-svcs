package com.training.mjunction.usersvcs;

import static org.hamcrest.Matchers.containsString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void infoTest() {
		RestAssured.given().when().contentType("application/json").get("/actuator/info").then().statusCode(200).body("app.name",
				containsString("user-svcs"));
	}

	@Test
	public void healthTest() {
		RestAssured.given().when().contentType("application/json").get("/actuator/health").then().statusCode(200).body("status",
				containsString("UP"));
	}

}