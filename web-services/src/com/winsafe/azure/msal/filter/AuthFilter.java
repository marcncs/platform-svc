// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.winsafe.azure.msal.filter;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.aad.msal4j.*;
import com.winsafe.azure.msal.util.AuthHelper;
import com.winsafe.azure.msal.util.SessionManagementHelper;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;


/**
 * Processes incoming requests based on auth status
 */
public class AuthFilter implements Filter {

    private AuthHelper authHelper = new AuthHelper();
    private AppUsers appUsers = new AppUsers();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            WfLogger.error("-----------"+httpRequest.getRequestURL()+"-------------------");
            try {
            	String path = httpRequest.getServletPath();
            	 // exclude home page 
                if(!path.contains("/secure")){
                    chain.doFilter(request, response);
                    return;
                } 
                
                
                /*if(isUserExistsInRTCI(httpRequest, "EOWFC")) {
                	httpResponse.sendRedirect("../sys/initDefaultAction.do");
                	return;
                } else {
                	httpRequest.getRequestDispatcher("/sys/indexAction.do").forward(request, response);
                	return;
                }*/
                
                //String currentUri = SysConfig.getSysConfig().getProperty("aad.redirectUriSignin");
                WfLogger.error("httpRequest.getRequestURL():"+httpRequest.getRequestURL());
                String currentUri = httpRequest.getRequestURL().toString();
                currentUri = currentUri.replaceAll("http","https");
                //currentUri = currentUri.replaceAll("http://10.87.200.40:8804/","https://henkelrfid-qa.winsafe.cn/");
                String queryStr = httpRequest.getQueryString();
                String fullUrl = currentUri + (queryStr != null ? "?" + queryStr : "");
                
                if(containsAuthenticationCode(httpRequest)){
                    // response should have authentication code, which will be used to acquire access token
                    WfLogger.error("response should have authentication code, which will be used to acquire access token:");
                    WfLogger.error("currentUri:"+currentUri);
                    WfLogger.error("fullUrl:"+fullUrl);
                    authHelper.processAuthenticationCodeRedirect(httpRequest, currentUri, fullUrl);
                    chain.doFilter(request, response);
                    return;
                }

                // check if user has a AuthData in the session
                if (!isAuthenticated(httpRequest)) {
                    WfLogger.error("not authenticated, redirecting to login.microsoft.com so user can authenticate...");
                        // not authenticated, redirecting to login.microsoft.com so user can authenticate
                        authHelper.sendAuthRedirect(
                                httpRequest,
                                httpResponse,
                                null,
                                authHelper.getRedirectUriSignIn());
                        return;
                }
                
                //判断RTCI中是否有登录的CWID用户信息
                if(isUserExistsInRTCI(httpRequest)) {
                	httpResponse.sendRedirect("../sys/initMainPageAction.do");
                	//httpResponse.sendRedirect("../sys/initDefaultAction.do");
                	return;
                	//httpResponse.sendRedirect("../sys/initMainPageAction.do");
                } else {
                	httpRequest.getRequestDispatcher("/sys/indexAction.do").forward(request, response);
                	return;
                }

                /*if (isAccessTokenExpired(httpRequest)) {
                    updateAuthDataUsingSilentFlow(httpRequest, httpResponse);
                }*/
            } catch (MsalException authException) {
            	WfLogger.error("",authException);
                // something went wrong (like expiration or revocation of token)
                // we should invalidate AuthData stored in session and redirect to Authorization server
                SessionManagementHelper.removePrincipalFromSession(httpRequest);
                authHelper.sendAuthRedirect(
                        httpRequest,
                        httpResponse,
                        null,
                        authHelper.getRedirectUriSignIn());
                return;
            } catch (Exception exc) {
                httpResponse.setStatus(500);
                WfLogger.error("",exc);
                request.setAttribute("result", exc.getMessage());
                //request.getRequestDispatcher("/error").forward(request, response);
                httpRequest.getRequestDispatcher("/sys/indexAction.do").forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isUserExistsInRTCI(HttpServletRequest request) throws Exception {
    	IAuthenticationResult result = SessionManagementHelper.getAuthSessionObject(request);
        String cwid = result.account().username().replaceAll("@bayer.com", "");
        UsersBean userBean = appUsers.getUsersBeanByLoginname(cwid);
        if (userBean == null) {
        	request.setAttribute("result", "RTCI中不存在用户"+cwid);
        	return false;
        }
        //获取用户分类
		Set<Integer> ucSet = appUsers.getUserCategarySet(userBean.getUserid());
		//检查用户类型是否正确
		if(!ucSet.contains(UserCategary.RTCI.getValue())) {
			request.setAttribute("result", "对不起，用户类型错误！");
			return false;
		}
		// 判断用户是否可用
		if(userBean.getStatus() != 1){
			request.setAttribute("result", "对不起，您的账号不可用！");
			return false;
		}
		// 设置用户在线
		appUsers.setOnline(userBean.getUserid());
		request.getSession().setAttribute("users", userBean);
		DBUserLog.addUserLog(userBean.getUserid(), "系统管理", "登陆[IP:"+request.getRemoteHost()+"]");
    	//request.getSession().setAttribute("account", result.account());
		return true;
	}
    
    private boolean isUserExistsInRTCI(HttpServletRequest request, String cwid) throws Exception {
        /*UsersBean userBean = appUsers.getUsersBeanByLoginname(cwid);
        if (userBean == null) {
        	request.setAttribute("result", "RTCI中不存在用户"+cwid);
        	return false;
        }
        //获取用户分类
		Set<Integer> ucSet = appUsers.getUserCategarySet(userBean.getUserid());
		//检查用户类型是否正确
		if(!ucSet.contains(UserCategary.RTCI.getValue())) {
			request.setAttribute("result", "对不起，用户类型错误！");
			return false;
		}
		// 判断用户是否可用
		if(userBean.getStatus() != 1){
			request.setAttribute("result", "对不起，您的账号不可用！");
			return false;
		}
		// 设置用户在线
		appUsers.setOnline(userBean.getUserid());
		request.getSession().setAttribute("users", userBean);
		DBUserLog.addUserLog(userBean.getUserid(), "系统管理", "登陆[IP:"+request.getRemoteHost()+"]");
		return true;*/
    	IAccount account = new IAccount() {
			
			@Override
			public String username() {
				// TODO Auto-generated method stub
				return "EOWFC";
			}
			
			@Override
			public String homeAccountId() {
				// TODO Auto-generated method stub
				return "98a99906-8929-4d3f-893d-952bbd1be0da.fcb2b37b-5da0-466b-9b83-0014b67a7c78";
			}
			
			@Override
			public String environment() {
				// TODO Auto-generated method stub
				return "login.microsoftonline.com";
			}
		};
		//request.setAttribute("account", account);
		request.getSession().setAttribute("account", account);
    	return true;
	}

	@Override
    public void destroy() {

    }

    private boolean containsAuthenticationCode(HttpServletRequest httpRequest) {
        Map<String, String[]> httpParameters = httpRequest.getParameterMap();

        boolean isPostRequest = httpRequest.getMethod().equalsIgnoreCase("POST");
        boolean containsErrorData = httpParameters.containsKey("error");
        boolean containIdToken = httpParameters.containsKey("id_token");
        boolean containsCode = httpParameters.containsKey("code");

        for(String key:  httpParameters.keySet()) {
            WfLogger.error("key:"+key);
            if(httpParameters.get(key)!= null) {
                for(String value : httpParameters.get(key)) {
                    WfLogger.error("value:"+value);
                }
            }
        }
        if(containsErrorData) {
        	httpRequest.setAttribute("error", httpRequest.getParameter("error"));
        	httpRequest.setAttribute("error_description", httpRequest.getParameter("error_description"));
        }
        WfLogger.error("isPostRequest:"+isPostRequest);
        WfLogger.error("containsErrorData:"+containsErrorData);
        WfLogger.error("containIdToken:"+containIdToken);
        WfLogger.error("containsCode:"+containsCode);

        return isPostRequest && containsErrorData || containsCode || containIdToken;
    }

    private boolean isAccessTokenExpired(HttpServletRequest httpRequest) {
        IAuthenticationResult result = SessionManagementHelper.getAuthSessionObject(httpRequest);
        return result.expiresOnDate().before(new Date());
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME) != null;
    }

    private void updateAuthDataUsingSilentFlow(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws Throwable {
        IAuthenticationResult authResult = authHelper.getAuthResultBySilentFlow(httpRequest, httpResponse);
        SessionManagementHelper.setSessionPrincipal(httpRequest, authResult);
    }
}
