package com.training.mjunction.usersvcs.config;

import static java.util.Arrays.asList;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class AppConfig {

	@Bean
	public Docket userApi() {

		return new Docket(SWAGGER_2).select().apis(basePackage("com.training.mjunction.usersvcs.webservice")).paths(regex("/user.*")).build()
				.apiInfo(metaData());
	}

	private ApiInfo metaData() {

		return new ApiInfo("Mjunction Training API", "Spring Boot REST API for user", "1.0", "Traning Purpose",
				new Contact("Sanjib Talukdar", "https://expogrow.org", "expogrow.org@gmail.com"), "Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0", asList(new VendorExtension<String>() {

					@Override
					public String getName() {
						return "ExpoGrow.org";
					}

					@Override
					public String getValue() {
						return "mjunction-traning";
					}

				}));

	}

}
