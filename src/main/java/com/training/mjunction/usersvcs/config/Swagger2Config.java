package com.training.mjunction.usersvcs.config;

import static java.util.Arrays.asList;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.training.mjunction.usersvcs.webservice.impl.UserDetailsRESTServiceImpl;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Primary
@Component
@EnableSwagger2
@Configuration
@ApplicationPath("/api")
public class Swagger2Config extends ResourceConfig implements SwaggerResourcesProvider {

	@Value("${spring.jersey.application-path:/api}")
	private String apiPath;

	@Resource
	private InMemorySwaggerResourcesProvider inMemorySwaggerResourcesProvider;

	public Swagger2Config() {
		final BeanConfig swaggerConfig = new BeanConfig();
		swaggerConfig.setBasePath("/api");
		SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfig);
		packages(getClass().getPackage().getName(), ApiListingResource.class.getPackage().getName());
		this.register(ApiListingResource.class);
		this.register(SwaggerSerializers.class);
		this.register(WadlResource.class);
		this.register(WadlResource.class);
		this.register(UserDetailsRESTServiceImpl.class);
		final BeanConfig config = new BeanConfig();
		config.setTitle("Mjunction Training API");
		config.setVersion("v1");
		config.setContact("Sanjib Talukdar");
		config.setSchemes(new String[] { "http", "https" });
		config.setBasePath(apiPath);
		config.setResourcePackage("com.training.mjunction.usersvcs.webservice");
		config.setPrettyPrint(true);
		config.setScan(true);
	}

	@Override
	public List<SwaggerResource> get() {
		final SwaggerResource jerseySwaggerResource = new SwaggerResource();
		jerseySwaggerResource.setLocation("/api/swagger.json");
		jerseySwaggerResource.setSwaggerVersion("2.0");
		jerseySwaggerResource.setName("user-service");
		return Stream.concat(Stream.of(jerseySwaggerResource), inMemorySwaggerResourcesProvider.get().stream()).collect(Collectors.toList());
	}

	@Bean
	public Docket userApi() {
		return new Docket(SWAGGER_2).select().apis(basePackage("com.training.mjunction.usersvcs.webservice")).paths(PathSelectors.any()).build()
				.enable(true)
				.apiInfo(new ApiInfo("Mjunction Training API", "Spring Boot REST API for user", "1.0", "Traning Purpose",
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

						})));
	}

}
