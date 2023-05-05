package com.ism.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.ism.entity.UserEntity;
import com.ism.repository.UserRepository;

//@Component
public class AuthTokenFilter implements Filter{

	@Autowired
	UserRepository userReprository;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)(req);
		HttpServletResponse response = (HttpServletResponse)(resp);
		
		String url = request.getRequestURL().toString();
		
		System.out.println(url);
		String token = request.getHeader("token");
		 
		if(token == null || token.trim().length() != 16)
		{
			String userJsonString = new Gson().toJson(token);
			
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(userJsonString);
			response.setStatus(HttpStatus.FORBIDDEN.value());
			out.flush();
		}
		else
		{
			UserEntity tokenexist = userReprository.findByToken(token).orElse(null);
			
			if(tokenexist == null)
			{
				String userJsonString = new Gson().toJson("Invalid Access");
				
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(userJsonString);
				response.setStatus(HttpStatus.FORBIDDEN.value()); 
				out.flush();
				  
			}
			
					chain.doFilter(request, resp);
		}
	}

	
}
