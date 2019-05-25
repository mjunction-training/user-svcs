package com.training.mjunction.usersvcs.config;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Optional;

import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.logging.log4j.ThreadContext;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
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

	@Bean
	public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return () -> {
			final String user = ThreadContext.get("user");
			if (null == user) {
				return Optional.of("UNKNOWN");
			}
			return Optional.ofNullable(user);
		};
	}

	@Primary
	@Profile({ "!test" })
	@Bean(destroyMethod = "close", name = "dataSource")
	public DataSource batchDataSource(@Value("${mjunction.training.db.url:jdbc:mariadb://localhost:3306/users}") final String jdbcUrl,
			@Value("${mjunction.training.db.usr:root}") final String userName, @Value("${mjunction.training.db.pwd:root}") final String password,
			@Value("${mjunction.training.db.driver:org.mariadb.jdbc.Driver}") final String driverClass,
			@Value("${mjunction.training.db.validationSql:select 1}") final String valdiationQuery) {

		if (isBlank(jdbcUrl) || isBlank(userName)) {
			throw new IllegalArgumentException("All the properties ['mjunction.training.db.url' , 'mjunction.training.db.usr' "
					+ "and 'mjunction.training.db.pwd'] are expected to be non-blank");
		}

		final org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		ds.setDriverClassName(driverClass);
		ds.setUrl(jdbcUrl);
		ds.setUsername(userName);
		ds.setPassword(password);
		ds.setValidationQuery(valdiationQuery);
		ds.setValidationInterval(60000);
		ds.setTestOnBorrow(true);
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(5400);
		ds.setJdbcInterceptors("ResetAbandonedTimer");
		ds.setLogAbandoned(true);
		ds.setInitialSize(5);
		ds.setMaxActive(10);
		ds.setMaxIdle(5);
		ds.setMinIdle(3);
		ds.setMaxAge(0);
		ds.setTestWhileIdle(true);
		ds.setJmxEnabled(true);

		return ds;
	}

}
