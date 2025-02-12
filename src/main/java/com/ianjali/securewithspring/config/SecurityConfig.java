package com.ianjali.securewithspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
//	@Bean
//	SecurityFilterChain getSecurityFilterUsingInterface(HttpSecurity httpSecurity) throws Exception {
//		
//		Customizer<CsrfConfigurer<HttpSecurity>> csrfObj = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//			
//			@Override
//			public void customize(CsrfConfigurer<HttpSecurity> config) {
//				config.disable();
//			}
//			
//		};
//		
//		Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementCustomizer = new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
//			
//			@Override
//			public void customize(SessionManagementConfigurer<HttpSecurity> sessionConfig) {
//				sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			}
//		};
//		
//		httpSecurity.csrf(csrfObj);
//		httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
//		httpSecurity.formLogin(Customizer.withDefaults());
//		httpSecurity.httpBasic(Customizer.withDefaults());
//		httpSecurity.sessionManagement(sessionManagementCustomizer);
//		return httpSecurity.build();
//	}
	

//	@Bean
//	SecurityFilterChain getSecurityFilterUsingLambda(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf(customiser -> customiser.disable());
//		httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
//		httpSecurity.formLogin(Customizer.withDefaults());
//		httpSecurity.httpBasic(Customizer.withDefaults());
//		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		return httpSecurity.build();
//	}
//	
	@Bean
	SecurityFilterChain getSecurityFilterUsingBuilder(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(customiser -> customiser.disable())
		.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.build();
	}
	
	
}
