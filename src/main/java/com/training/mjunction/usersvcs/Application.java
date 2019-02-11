package com.training.mjunction.usersvcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.training.mjunction.usersvcs")
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		log.info("Starting application user-svcs");
		return application.sources(Application.class);
	}

	public static void main(final String[] args) {
		log.info("Starting application user-svcs");
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public WebMvcConfigurer CORSConfig() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry.addMapping("/api/v1/**");
			}
		};
	}

}
