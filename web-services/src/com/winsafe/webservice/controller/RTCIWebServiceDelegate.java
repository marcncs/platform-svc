package com.winsafe.webservice.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.winsafe.drp.dao.AppQuery;
import com.winsafe.webservice.service.WSService;

@javax.jws.WebService(targetNamespace = "http://controller.webservice.winsafe.com/", serviceName = "RTCIWebServiceService", portName = "RTCIWebServicePort", wsdlLocation = "WEB-INF/wsdl/RTCIWebServiceService.wsdl")
public class RTCIWebServiceDelegate {

	com.winsafe.webservice.controller.RTCIWebService rTCIWebService = new com.winsafe.webservice.controller.RTCIWebService();

	@Resource
	private WebServiceContext wsContext;

	private String getClientInfo() {
		AppQuery aq = new AppQuery();
		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest request = (HttpServletRequest) (mc
				.get(MessageContext.SERVLET_REQUEST));
		String remortAddress = aq.getIpAddr(request);
//		String remortAddress = request.getRemoteAddr();
		return (remortAddress);
	}

	public String queryFWIdcode(String fwIdcode, int queryMode) {
		return rTCIWebService.queryFWIdcode(fwIdcode, queryMode,getClientInfo());
	}

}