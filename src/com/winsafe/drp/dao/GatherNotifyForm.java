package com.winsafe.drp.dao;


public class GatherNotifyForm {

	 /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String clinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Long saledept;
    
    private String saledeptname;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Integer isendcase;
    
    private String isendcasename;

    /** nullable persistent field */
    private Long endcaseid;
    
    private String endcaseidname;

    /** nullable persistent field */
    private String endcasedate;
    
    /** nullable persistent field */
    private Long saleid;
    
    private String saleidname;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private String bankaccount;

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getClinkman() {
		return clinkman;
	}

	public void setClinkman(String clinkman) {
		this.clinkman = clinkman;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEndcasedate() {
		return endcasedate;
	}

	public void setEndcasedate(String endcasedate) {
		this.endcasedate = endcasedate;
	}

	public Long getEndcaseid() {
		return endcaseid;
	}

	public void setEndcaseid(Long endcaseid) {
		this.endcaseid = endcaseid;
	}

	public String getEndcaseidname() {
		return endcaseidname;
	}

	public void setEndcaseidname(String endcaseidname) {
		this.endcaseidname = endcaseidname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsendcase() {
		return isendcase;
	}

	public void setIsendcase(Integer isendcase) {
		this.isendcase = isendcase;
	}

	public String getIsendcasename() {
		return isendcasename;
	}

	public void setIsendcasename(String isendcasename) {
		this.isendcasename = isendcasename;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getSaledept() {
		return saledept;
	}

	public void setSaledept(Long saledept) {
		this.saledept = saledept;
	}

	public String getSaledeptname() {
		return saledeptname;
	}

	public void setSaledeptname(String saledeptname) {
		this.saledeptname = saledeptname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
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

	public Long getSaleid() {
		return saleid;
	}

	public void setSaleid(Long saleid) {
		this.saleid = saleid;
	}

	public String getSaleidname() {
		return saleidname;
	}

	public void setSaleidname(String saleidname) {
		this.saleidname = saleidname;
	}
    
    
}
