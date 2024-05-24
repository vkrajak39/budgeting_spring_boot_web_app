package com.exavalu.budgetbakersb.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable())
		
			.authorizeHttpRequests(authorizeHttpRequests->authorizeHttpRequests
				.requestMatchers("/","/register","/userRegistration","/error","/error2","/forgetPassword","/forgetPasswordCreateNew","/forgotPasswordUpdate")			
				.permitAll()
                .requestMatchers("/dummy").hasRole("User")
				.anyRequest().authenticated())
			
			.formLogin(formLogin->formLogin.loginPage("/signin")
						.loginProcessingUrl("/userlogin")
						.defaultSuccessUrl("/Dashboard")
						.failureUrl("/login-error") 
						.permitAll()
						)
				
			.logout(logout->logout.logoutSuccessUrl("/userlogout")
						.permitAll());

		return http.build();
	}

}