package com.blog.security;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get JWT from Request
		String requestToken=request.getHeader("Authorization");
		
		//Bearer gtgjaxcasbcsnhvjeshfhe
		System.out.println(requestToken);
		String username=null;
		
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			 token = requestToken.substring(7);
			
			 try {
				 username = this.jwtTokenHelper.getUsernameFromToken(token);
			 }
			 catch(IllegalArgumentException e) {
				 System.out.println("Unable to get JWT Token");
			 }
			 catch(ExpiredJwtException e) {
				 System.out.println("JWT Token is Expired");
				 
			 }
			 catch(MalformedJwtException e) {
				 System.out.println("JWT Token is invalid");
				 
			 }
		
		}
		else {
			System.out.println("JWT Token does not start with Bearer ");
		}
		
		//once token is recieved,now validate token
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
		UserDetails userDetails =	this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token,userDetails )) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("invalid Token");
			}
		}
		else {
			System.out.println("Username is null or contex is not null");
		}
		filterChain.doFilter(request, response);
		
	}


	
	
}
