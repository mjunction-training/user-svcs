package com.training.mjunction.usersvcs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.mjunction.usersvcs.webservice.resources.UserDetailsRequestResource;

import io.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper mappee;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void infoTest() {
		given().when().contentType("application/json").get("/actuator/info").then().statusCode(200).body("app.name",
				containsString("user-svcs"));
	}

	@Test
	public void healthTest() {
		given().when().contentType("application/json").get("/actuator/health").then().statusCode(200).body("status",
				containsString("UP"));
	}

	@Test
	public void swaggerTest() {
		given().when().contentType("application/json").get("/api/swagger.json").then().statusCode(200)
				.body("info.contact.name", containsString("Sanjib Talukdar"));
	}

	@Test
	public void createUserrTest() throws JsonParseException, JsonMappingException, IOException {
		final String json = "{" + "	\"username\": \"sanjeeb\"," + "	\"password\": \"password\","
				+ "	\"firstName\": \"Sanjib\"," + "	\"lastName\": \"Talukdar\","
				+ "	\"email\": \"expogrow.org@gmail.com\"," + "	\"phone\": \"9833375042\"," + "	\"authorities\": [{"
				+ "		\"authority\": \"ADMIN\"" + "	}]" + "}";
		final UserDetailsRequestResource request = mappee.readValue(json, UserDetailsRequestResource.class);
		given().when().contentType("application/json").auth().basic("user", "user").accept("application/json")
				.basePath("/api/v1/users/").body(request).then().statusCode(200)
				.body("username", containsString("sanjeeb"));
	}

}