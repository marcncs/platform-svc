package com.winsafe.mail.smtp.base;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class DNETrustManager implements X509TrustManager  {
    public void checkClientTrusted(X509Certificate[] cert, String authType) {
    	// everything is trusted
        }

        public void checkServerTrusted(X509Certificate[] cert, String authType) {
    	// everything is trusted
        }

        public X509Certificate[] getAcceptedIssuers() {
    	return null;
        }
}
