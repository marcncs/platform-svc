package com.winsafe.webservice.controller;

import com.winsafe.webservice.service.WSService;

public class RTCIWebService {
	
	private WSService wsService = new WSService();
	
	public String queryFWIdcode(String fwIdcode, int queryMode, String remoteAddress) {
		return wsService.queryFWIdcode(fwIdcode, remoteAddress, queryMode);
	}
}
