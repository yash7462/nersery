package com.nursery.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class MultiHttpSecurityConfig {
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

	@Configuration
	@Order(1)
	public static class customLoginConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
					// .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests()
					.antMatchers("/resources/**","/swagger-resources/**","/v2/api-docs").permitAll()
		            .antMatchers("/v2/api-docs",
		                    "/configuration/ui",
		                    "/swagger-resources/**",
		                    "/configuration/security",
		                    "/swagger-ui.html",
		                    "/webjars/**").permitAll()
					.antMatchers("/login", "/signup", "/admin/**").permitAll()
					.antMatchers("/api/auth/**").permitAll().antMatchers("/api/test/**").permitAll().anyRequest()
					.authenticated();

			// http.addFilterBefore(authenticationJwtTokenFilter(),
			// UsernamePasswordAuthenticationFilter.class);

		}

	}

}
