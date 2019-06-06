package com.training.mjunction.usersvcs.config;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.training.mjunction.usersvcs.service.OAuth2ClientDetailsService;
import com.training.mjunction.usersvcs.service.UserDetailsService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
@SessionAttributes("authorizationRequest")
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private OAuth2ClientDetailsService oAuth2ClientDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
		final FilterRegistrationBean<ForwardedHeaderFilter> filterRegBean = new FilterRegistrationBean<>();
		filterRegBean.setFilter(new ForwardedHeaderFilter());
		filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filterRegBean;
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(
			@Value("${keystore.file:cert/selfsigned.jks}") final String keyStoreFile,
			@Value("${keystore.password:foobar}") final String keyStorePassword,
			@Value("${keystore.alies:test}") final String keyStoreAlies) {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		final KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(keyStoreFile),
				keyStorePassword.toCharArray()).getKeyPair(keyStoreAlies);
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/hystrix.stream", "/actuator/**", "/js/**", "/css/**", "/*.html", "/*.htm",
				"/*.jsp", "/swagger-ui.html", "/v2/api-docs");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class).csrf().disable().formLogin()
				.loginPage("/login").permitAll().and().requestMatchers()
				.antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access").and().authorizeRequests()
				.antMatchers("/actuator/**", "/js/**", "/css/**", "/*.html", "/*.htm", "/*.jsp").permitAll()
				.anyRequest().authenticated().and().logout().permitAll().and().httpBasic();
	}

	public static final class CORSFilter implements Filter {

		@Override
		public void init(final FilterConfig filterConfig) throws ServletException {
		}

		@Override
		public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
				throws IOException, ServletException {

			log.info("Adding CORS Headers.....................");

			final HttpServletResponse response = (HttpServletResponse) res;

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,"
					+ "Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
			response.setHeader("Access-Control-Expose-Headers", "xsrf-token");

			chain.doFilter(req, res);
		}

		@Override
		public void destroy() {
		}
	}

	@Configuration
	@EnableResourceServer
	public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		private static final String RESOURCE_ID = "user-svcs-rest-api";
		private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
		private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
		private static final String SECURED_PATTERN = "/api/v1/**";

		@Override
		public void configure(final ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(final HttpSecurity http) throws Exception {
			http.requestMatchers().antMatchers(SECURED_PATTERN).and().authorizeRequests()
					.antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
					.antMatchers(HttpMethod.PUT, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
					.antMatchers(HttpMethod.DELETE, SECURED_PATTERN).access(SECURED_WRITE_SCOPE).anyRequest()
					.access(SECURED_READ_SCOPE);
		}
	}

	@Configuration
	@EnableAuthorizationServer
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private JwtAccessTokenConverter jwtAccessTokenConverter;

		@Bean
		public TokenStore tokenStore() {
			return new JdbcTokenStore(dataSource);
		}

		@Bean
		public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
			return new OAuth2AccessDeniedHandler();
		}

		@Override
		public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
					.passwordEncoder(passwordEncoder);
		}

		@Override
		public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(oAuth2ClientDetailsService);
		}

		@Override
		public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
			endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
					.userDetailsService(userDetailsService).accessTokenConverter(jwtAccessTokenConverter);
		}
	}

	@Controller
	@Configuration
	@Order(-19)
	protected static class AuthserverConfiguration implements WebMvcConfigurer {

		@RequestMapping("/user")
		@ResponseBody
		public Principal user(final Principal user) {
			return user;
		}

		@Override
		public void addViewControllers(final ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
			registry.addViewController("/oauth/confirm_access").setViewName("authorize");
		}

	}
}