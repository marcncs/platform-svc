package com.winsafe.log4j.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

/**
 * Project:winsafepdp->Class:ResFilter.java
 * <p style="font-size:16px;">Description：设置Log4j日志的用户名信息过滤器</p>
 */
public class ResFilter implements Filter
{

	private final static double DEFAULT_USERNAME = Math.random() * 10000;
	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		if(null == session)
		{
			MDC.put("userName", DEFAULT_USERNAME);
		}
		else
		{
			UsersBean user = null;
			try {
				user = UserManager.getUser(request);
			} catch (Exception e) {
			}
			if(null == user)
			{
				MDC.put("userName", DEFAULT_USERNAME);
			}
			else
			{
				MDC.put("userName", user.getLoginname());
			}
		}
		chain.doFilter(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		// TODO Auto-generated method stub

	}

}
