package com.training.mjunction.usersvcs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApplicationTests {

	@LocalServerPort
	private int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void infoTest() {
		given().when().contentType("application/json").get("/actuator/info").then().statusCode(200).body("app.name", containsString("user-svcs"));
	}

	@Test
	public void healthTest() {
		given().when().contentType("application/json").get("/actuator/health").then().statusCode(200).body("status", containsString("UP"));
	}

	@Test
	public void swaggerTest() {
		given().when().contentType("application/json").auth().basic("user", "user").get("/api/swagger.json").then().statusCode(200)
				.body("info.contact.name", containsString("Sanjib Talukdar"));
	}

}