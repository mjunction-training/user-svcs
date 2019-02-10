package com.training.mjunction.usersvcs.config;

import static org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest.toAnyEndpoint;
import static org.springframework.http.HttpMethod.OPTIONS;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/actuator/**", "/js/**", "/css/**", "/*.html", "/*.htm", "/*.jsp");
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class).csrf().disable().authorizeRequests().requestMatchers(toAnyEndpoint())
				.permitAll().antMatchers(OPTIONS, "/**").permitAll().antMatchers("/actuator/**", "/js/**", "/css/**", "/*.html", "/*.htm", "/*.jsp")
				.permitAll().anyRequest().authenticated().and().logout().permitAll().and().httpBasic();
	}
}