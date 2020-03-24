package com.winsafe.hbm.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.entity.HibernateUtil;


public class HibernateSessionFilter implements Filter {
	private static Logger logger = Logger.getLogger(HibernateSessionFilter.class);
	
	private static ServletContext servletContext = null;
	
    public void destroy() {       
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {

    	try{
    		chain.doFilter(request, response);
    		HibernateUtil.commitTransaction();
    	}catch( Exception e ){
    		WfLogger.error("", e);
    		logger.error("hibernate rollback error: "+e.getMessage(),e);
    		HibernateUtil.rollbackTransaction();
    		//转发的输入页面
    		HttpServletRequest httpRequest = (HttpServletRequest) request;
    		String inputUri = httpRequest.getRequestURI() + "?" + httpRequest.getQueryString();
    		request.getRequestDispatcher(inputUri).forward(request, response);
    		
    	}finally{
    		HibernateUtil.closeSession();
    	}

    }

    public void init(FilterConfig filterConfig) throws ServletException {
    	servletContext = filterConfig.getServletContext();
    }

}
