package com.training.mjunction.usersvcs.config;

import javax.validation.Validation;
import javax.validation.Validator;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}

	@Bean
	public ObjectMapper objetMapper() {
		return new ObjectMapper();
	}

	@Bean
	public Validator validator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

}
