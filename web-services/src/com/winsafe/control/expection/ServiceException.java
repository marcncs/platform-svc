package com.winsafe.control.expection;

import org.apache.commons.lang.StringUtils;

import com.winsafe.drp.metadata.ServiceExpType; 

public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -6376223551947390530L;
	
	private ServiceExpType servExpType;
	
	public ServiceException(ServiceExpType serviceExpType) {
		this.servExpType = serviceExpType;
	}

	public ServiceException(String msg, ServiceExpType serviceExpType) {
		super(msg);
		this.servExpType = serviceExpType;
	}
	
	public ServiceException(Throwable cause, ServiceExpType serviceExpType) {
		super(cause);
		this.servExpType = serviceExpType;
	}
	
	public ServiceException(String msg, Throwable cause, ServiceExpType serviceExpType) {
		super(msg, cause);
		this.servExpType = serviceExpType;
	}
	
	public ServiceExpType getServExpType() {
		return servExpType;
	}
	
	public String getMessage() {
		return StringUtils.isNotBlank(super.getMessage()) ? super.getMessage() : this.servExpType.getDesc();
	}
	
	public String getCode() {
		return servExpType.getCode();
	}
	
	public String getDesc() {
		return servExpType.getDesc();
	}
}
