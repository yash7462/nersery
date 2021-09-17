package com.nursery.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class MultiHttpSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());

	}

	@Configuration
	@Order(1)
	public static class customLoginConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		private JwtAuthEntryPoint unauthorizedHandler;

		@Bean
		public JwtAuthTokenFilter authenticationJwtTokenFilter() {
			return new JwtAuthTokenFilter();
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
//		@Override
//        protected void configure(HttpSecurity http) throws Exception {
//			http.cors().and().csrf().disable().authorizeRequests().
//			antMatchers("/ogoshops/**","/api/auth/**", "/api/ecommerce/auth/**", "/api/mpos/auth/**")
//					.permitAll().antMatchers("/pos/pdf/**").permitAll().and().antMatcher("/api/**").authorizeRequests()
//					.anyRequest()
//					.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_corporate') or hasRole('ROLE_company') or hasRole('ROLE_branch') or hasRole('ROLE_user') ")
//                    .and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
//            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        }

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
					// .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests().antMatchers("/resources/**", "/swagger-resources/**", "/v2/api-docs")
					.permitAll()
					.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
							"/configuration/security", "/swagger-ui.html", "/webjars/**")
					.permitAll().antMatchers("/login", "/signup", "/admin/**").permitAll().antMatchers("/api/auth/**")
					.permitAll().antMatchers("/api/test/**").permitAll().anyRequest()
					.access("hasRole('ROLE_ADMIN') or hasRole('ROLE_corporate') or hasRole('ROLE_company') or hasRole('ROLE_branch') or hasRole('ROLE_USER') ")
					.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
			http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
			// http.addFilterBefore(authenticationJwtTokenFilter(),
			// UsernamePasswordAuthenticationFilter.class);

		}

	}

}
