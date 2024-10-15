package com.expenses;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity.authorizeHttpRequests((requests) -> requests
	            .requestMatchers("/login", "/login2").hasAnyRole("admin") // Allow login pages
	            .requestMatchers("/save", "/addExpense", "/delete", "/pdf").authenticated() // Require authentication for these
	            .requestMatchers("/expenses", "/ByUser").authenticated()// Require authentication for these
	            // All other requests require authentication
	            .anyRequest().authenticated() // Ensure any other requests require authentication
	    )
	    .formLogin(form -> form
	    		 
	              .permitAll() 
	          )
	          
	          .logout(logout -> logout
	              .permitAll() 
	          );
	        
	    return httpSecurity.build();
	}
//	 @Bean
//	    public UserDetailsService userDetailsService() {
//	        // Create an in-memory user
//	        UserDetails user1 = User.withUsername("vamsi2")
//	                .password(passwordEncoder().encode("sai111")) // Encode password
//	                .roles("ADMIN")
//	                .build();
//
//	        UserDetails user2=User.withUsername("vamsi")
//	        		.password(passwordEncoder().encode("125"))"661"
//	        		.roles("ADMIN")
//	        		.build();
//	        return new InMemoryUserDetailsManager(user1,user2);
//	    }

	   @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }


	
}
