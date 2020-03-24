package com.winsafe.drp.metadata;

public enum ServiceExpType { 
	UnKnownError("WZ110001", "未知错误"),
	OtherError("WZ110002", "其他错误"),
	InvalidParam("WZ110003", "参数不合法"),
	FormatError("WZ110004", "格式错误"),
	AccountExisted("WZ120001", "帐号已存在"),
	AccountNotExisted("WZ120002", "帐号不存在"),
	AccountPasswordError("WZ120003", "密码错误"),
	IllegalUser("WZ130001", "非法用户"),
	ResourceNotExisted("WZ140001", "资源不存在"),
	ResourceExisted("WZ140002", "资源已存在"),
	ResourceExpired("WZ140003", "资源已过期"),
	PermissionError("WZ150001", "权限错误"),
	NetworkError("WZ160001", "网络错误"),
	WXSendNumLimit("WZ300001", "微信用户领取红包个数超过限制"),
	WXNotEnough("WZ300002", "微信账户余额不足"),
	;
	
	private String code;
	private String desc;
	
	private ServiceExpType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public String toString() {
		return "ServiceExpType{" 
				+ "code='" + getCode() + "'"
				+ "desc='" + getDesc() + "'"
				+ "}";
	}
}
