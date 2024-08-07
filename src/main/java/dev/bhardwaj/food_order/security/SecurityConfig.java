package dev.bhardwaj.food_order.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/api/customer/create", "api/customer/login").permitAll()

				.requestMatchers("/api/dish/get-dish-details/**", "api/dish/get-dishes-from-restaurant-by-cuisine/**",
						"api/dish/get-dishes-by-cuisine/**", "api/dish/get-dishes-based-on-rating")
				.permitAll()

				.requestMatchers("/api/restaurant/get-restaurant-details/**").permitAll()

				.requestMatchers("/api/customer/update", "/api/customer/delete/**",
						"/api/customer/get-customer-details/**")
				.hasAuthority("NORMAL_USER")

				.requestMatchers("/api/order/place-order", "/api/order/get-orders-for-customer/**",
						"/api/order/get-bill")
				.hasAnyAuthority("NORMAL_USER")

				.requestMatchers("/api/rating/add-rating", "/api/rating/get-ratings-given-by-customer/**")
				.hasAuthority("NORMAL_USER")

				.requestMatchers("/api/review/add-review", "/api/review/get-reviews-given-by-customer/**")
				.hasAuthority("NORMAL_USER")

				.requestMatchers("/api/dish/add-dish", "/api/dish/update-dish", "api/dish/delete-dish/**")
				.hasAuthority("ADMIN_USER")

				.requestMatchers("/api/order/get-orders-for-restaurant/**", "api/dish/delete-dish/**",
						"api/order/update-order-delivery-status/**")
				.hasAuthority("ADMIN_USER")

				.requestMatchers("/api/restaurant/create", "/api/restaurant/delete/**", "/api/restaurant/open/**",
						"/api/restaurant/close/**")
				.hasAuthority("ADMIN_USER")

				.requestMatchers("/api/order/get-order-details/**").hasAnyAuthority("NORMAL_USER", "ADMIN_USER")

				.anyRequest().authenticated())
		
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				
				.httpBasic(Customizer.withDefaults());

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
