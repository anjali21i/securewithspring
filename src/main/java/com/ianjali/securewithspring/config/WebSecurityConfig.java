package com.ianjali.securewithspring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    UserDetailsService userDetailsService;

    @Autowired
    JWTFilter jwtFilter;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                "/users/register",
                                        "/users/login"
                                )

                                .permitAll()
//                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/user/**").hasAnyAuthority("USER", "ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        try {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
//            authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // For plain text passwords
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        } catch (Exception e) {
           e.printStackTrace();
        }
       return null;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

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
}
