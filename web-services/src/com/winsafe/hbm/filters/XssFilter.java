package com.winsafe.hbm.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain; 
import javax.servlet.FilterConfig; 
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter {
    @Override 
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	HttpServletRequest hreq = (HttpServletRequest) request;
		String value = hreq.getServletPath();
		if(value.indexOf("download") != -1 && value.endsWith(".jsp")) {
			 chain.doFilter(request, response);
		} else {
			 chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		}
       
    }
}