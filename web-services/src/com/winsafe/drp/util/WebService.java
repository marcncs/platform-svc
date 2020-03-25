package com.winsafe.drp.util;

import java.net.URL;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;

public class WebService { 
	
	public static Object[] call(String wsdlUrl,String method,Object in[]) throws Exception {
		Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory)new EasySSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", easyhttps);
		Client client = new Client(new URL(wsdlUrl));
		return client.invoke(method, in);
	}
	
}
