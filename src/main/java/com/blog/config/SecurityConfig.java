package com.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.cglib.proxy.NoOp;
import com.blog.security.CustomeUserDetailService;
import com.blog.security.JWTAuthenticationEntryPoint;
import com.blog.security.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
//public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};

	@Autowired
	private CustomeUserDetailService customeUserDetailService;
	
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception {
	 * 
	 * http .csrf() .disable() .authorizeHttpRequests()
	 * .antMatchers("/api/v1/auth/login") .permitAll() .anyRequest()
	 * .authenticated() .and() .exceptionHandling()
	 * .authenticationEntryPoint(this.jwtAuthenticationEntryPoint) .and()
	 * .sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	 * 
	 * 
	 * 
	 * http.addFilterBefore(this.jwtAuthenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class);
	 * 
	 * http.authenticationProvider(daoAuthenticationProvider());
	 * 
	 * DefaultSecurityFilterChain defaultSecurityFilterChain =http.build();
	 * 
	 * return defaultSecurityFilterChain;
	 * 
	 * }
	 */
	
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	  
	  
	  http .csrf() .disable() .authorizeHttpRequests()
	  .antMatchers(PUBLIC_URLS).permitAll() 
	  .antMatchers(HttpMethod.GET).permitAll()
	  .anyRequest()
	  .authenticated() .and() .exceptionHandling()
	  .authenticationEntryPoint(this.jwtAuthenticationEntryPoint) .and()
	  .sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  
	  
	  
	  http.addFilterBefore(this.jwtAuthenticationFilter,
	  UsernamePasswordAuthenticationFilter.class);
	  
	  }
	 
	
	
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 auth.userDetailsService(this.customeUserDetailService).passwordEncoder(
	  passwordEncoder()); }
	
	
	
	
	
	/*
	 * @Bean public DaoAuthenticationProvider daoAuthenticationProvider() {
	 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	 * provider.setUserDetailsService(customeUserDetailService);
	 * provider.setPasswordEncoder(passwordEncoder()); return provider;
	 * 
	 * }
	 */
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
		
		  @Bean
		  @Override
		  public AuthenticationManager authenticationManagerBean() throws Exception { 
			  return super.authenticationManagerBean();
		  }
		 
	 
	 
		/*
		 * @Bean public AuthenticationManager
		 * authenticationManagerBean(AuthenticationConfiguration configuration) throws
		 * Exception { return configuration.getAuthenticationManager(); }
		 */
     
	 

}
