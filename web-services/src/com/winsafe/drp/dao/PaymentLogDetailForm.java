package com.winsafe.drp.dao;

public class PaymentLogDetailForm {
	  /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String plid;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private Double payablesum;

    /** nullable persistent field */
    private Integer paymode;
    
    private String paymodename;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Double thispayablesum;

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPayablesum() {
		return payablesum;
	}

	public void setPayablesum(Double payablesum) {
		this.payablesum = payablesum;
	}

	public Integer getPaymode() {
		return paymode;
	}

	public void setPaymode(Integer paymode) {
		this.paymode = paymode;
	}

	public String getPaymodename() {
		return paymodename;
	}

	public void setPaymodename(String paymodename) {
		this.paymodename = paymodename;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPlid() {
		return plid;
	}

	public void setPlid(String plid) {
		this.plid = plid;
	}

	public Double getThispayablesum() {
		return thispayablesum;
	}

	public void setThispayablesum(Double thispayablesum) {
		this.thispayablesum = thispayablesum;
	}


	
}
