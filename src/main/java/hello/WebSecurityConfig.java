package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @// @formatter:off
 
		auth.
		inMemoryAuthentication()
		.withUser("user").password("{noop}pass1").
		roles("USER")
		.authorities("ADMIN");

		
	}	// @formatter:on
	
	
	/**
	 * Lesson 3, URL Authorization: We set up Authorization by using an extension point in the configuration
	 * All the possible options available for URL authorization
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception { //@formatter:off
		
		// This order is important, this says that anything with /greeting should have the authority of ADMIN
		http
		.authorizeRequests()
		.antMatchers("/greeting").hasAnyAuthority("ADMIN","ADMIN2")
			.anyRequest().authenticated()			
			.and()
			/**
			 * loginpage("/login") specifies which page is our login, and the assosciated controller will redirect it to the page
			 * loginProcessingUrl - this says where the login processing happens, in the post method we call it using @{/doLogin}
			 * 
			 */
		.formLogin().loginPage("/login").permitAll()
		.loginProcessingUrl("/doLogin");
	} //@formatter:on
	
	
}