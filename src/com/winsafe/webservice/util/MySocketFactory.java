package com.winsafe.webservice.util;

import java.util.Hashtable;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.axis.components.net.JSSESocketFactory;

public class MySocketFactory extends JSSESocketFactory {

	public MySocketFactory(Hashtable attributes) {
		super(attributes);
	}

	@Override
	protected void initFactory() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[] {
			        new MyX509TrustManager()};
			  
			    // Install the all-trusting trust manager
			    SSLContext sc = SSLContext.getInstance("SSL");
			    // Create empty HostnameVerifier
			    HostnameVerifier hv = new HostnameVerifier() {
			                public boolean verify(String arg0, SSLSession arg1) {
			                        return true;
			                }
			    };
			 
			    sc.init(null, trustAllCerts, new java.security.SecureRandom());
			    sslFactory = sc.getSocketFactory();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
