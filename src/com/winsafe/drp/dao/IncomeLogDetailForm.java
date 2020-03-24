package com.winsafe.drp.dao;

public class IncomeLogDetailForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ilid;

    /** nullable persistent field */
    private String rid;

    /** nullable persistent field */
    private Double receivablesum;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Double thisreceivablesum;

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

	public String getIlid() {
		return ilid;
	}

	public void setIlid(String ilid) {
		this.ilid = ilid;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public Double getReceivablesum() {
		return receivablesum;
	}

	public void setReceivablesum(Double receivablesum) {
		this.receivablesum = receivablesum;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Double getThisreceivablesum() {
		return thisreceivablesum;
	}

	public void setThisreceivablesum(Double thisreceivablesum) {
		this.thisreceivablesum = thisreceivablesum;
	}
    
    
}
