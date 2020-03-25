package com.winsafe.drp.util.fileListener.UFIDA;

public class ResXmlBean {
	//交易单号即NCCODE
	private String cgeneralhid;
	//交易的状态
	private String state;
	//交易的明细
	private String detail;
	
	public ResXmlBean(String cgeneralhid,String state,String detail) {
		this.cgeneralhid=cgeneralhid;
		this.state=state;
		this.detail=detail;
	}

	public ResXmlBean() {
		// TODO Auto-generated constructor stub
	}

	public String getCgeneralhid() {
		return cgeneralhid;
	}

	public void setCgeneralhid(String cgeneralhid) {
		this.cgeneralhid = cgeneralhid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
}
