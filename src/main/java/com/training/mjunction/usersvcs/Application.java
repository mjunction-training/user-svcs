package com.training.mjunction.usersvcs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableHystrix
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
	
	@RefreshScope
	@Component
	public class Refreshed{
		@Value("${test.refresh.msg:}")
		private String mgs;
	}
	
	@Component
	public class NotRefreshed {
		@Value("${test.refresh.msg:}")
		private String mgs;
	}
	
	@Service
	@Path("/v1/refresh")
	public class RefreshTest {
		
		@Autowired
		private Refreshed refreshed;
		@Autowired
		private NotRefreshed notRefreshed;
		
		@GET
		@Path("/test")
		@Produces({ "application/json" })
		public Response refreshTest() {
			return Response.ok().entity("{\"refreshed\":\""+ refreshed.mgs+"\", \"not-refreshed\":\""+notRefreshed.mgs+"\"}").build();
		}
	}


}
