package com.hexaware.app.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hexaware.app.Enums.UserRole;
import com.hexaware.app.Service.UserService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
	 @Autowired
	    private  JwtAuthenticationFilter jwtAuthenticationFilter;
	    @Autowired
	    private  UserService userService;
	    
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity.csrf(AbstractHttpConfigurer::disable)
	                .authorizeHttpRequests(req -> req
	                        .requestMatchers("/api/auth/**").permitAll()
	                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
	                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
	                        .requestMatchers("/api/user/**").hasAnyAuthority(UserRole.USER.name())
	                        .requestMatchers(HttpMethod.POST, "/api/food/add").hasAuthority("ADMIN")
	                        .requestMatchers(HttpMethod.PUT, "/api/food/update").hasAuthority("ADMIN") 
	                        .requestMatchers(HttpMethod.DELETE, "/api/food/delete").hasAuthority("ADMIN")
	                        .requestMatchers(HttpMethod.POST, "/api/order/place").hasAuthority("USER")
	                        .requestMatchers(HttpMethod.GET, "/api/order/all").hasAuthority("ADMIN")
	                        .requestMatchers(HttpMethod.PUT, "/api/order/update-status").hasAuthority("ADMIN")
	                        .requestMatchers(HttpMethod.DELETE, "/api/order/delete").hasAuthority("ADMIN")
	                        .anyRequest().authenticated()).sessionManagement
	                        (management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .authenticationProvider(authenticationProvider())
	                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        return httpSecurity.build();
	    }
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	        daoAuthenticationProvider.setUserDetailsService(userService.userDetailsService());
	        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	        return daoAuthenticationProvider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
}
