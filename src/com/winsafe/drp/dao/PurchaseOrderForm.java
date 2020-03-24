package com.winsafe.drp.dao;


public class PurchaseOrderForm {

	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private String ppid;

    /** nullable persistent field */
    private String pid;
    
    private String providename;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private Long purchasedept;
    
    private String purchasedeptname;
    
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private Long purchaseid;
    
    private String purchaseidname;


    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String receivedate;

    /** nullable persistent field */
    private String receiveaddr;

    /** nullable persistent field */
    private Integer isrefer;
    
    private String isrefername;

    /** nullable persistent field */
    private Integer approvestatus;
    
    private String approvestatusname;

    /** nullable persistent field */
    private String approvedate;

    /** nullable persistent field */
    private Long makeid;

    private String makeidname;
    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String batch;
    
    private Integer actid;
    
    private String actidname;
    
    private Long pbaid;
    
    /** nullable persistent field */
    private Integer isendcase;
    
    private String isendcasename;

    /** nullable persistent field */
    private Long endcaseid;
    
    private String endcaseidname;

    /** nullable persistent field */
    private String endcasedate;

    /** nullable persistent field */
    private Integer isblankout;
    
    private String isblankoutname;

    /** nullable persistent field */
    private Long blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private String blankoutdate;

	public Long getPbaid() {
		return pbaid;
	}

	public void setPbaid(Long pbaid) {
		this.pbaid = pbaid;
	}

	public Integer getActid() {
		return actid;
	}

	public void setActid(Integer actid) {
		this.actid = actid;
	}

	public String getActidname() {
		return actidname;
	}

	public void setActidname(String actidname) {
		this.actidname = actidname;
	}

	public String getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(String approvedate) {
		this.approvedate = approvedate;
	}

	public Integer getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	public String getApprovestatusname() {
		return approvestatusname;
	}

	public void setApprovestatusname(String approvestatusname) {
		this.approvestatusname = approvestatusname;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsrefer() {
		return isrefer;
	}

	public void setIsrefer(Integer isrefer) {
		this.isrefer = isrefer;
	}

	public String getIsrefername() {
		return isrefername;
	}

	public void setIsrefername(String isrefername) {
		this.isrefername = isrefername;
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


	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPlinkman() {
		return plinkman;
	}

	public void setPlinkman(String plinkman) {
		this.plinkman = plinkman;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public Long getPurchasedept() {
		return purchasedept;
	}

	public void setPurchasedept(Long purchasedept) {
		this.purchasedept = purchasedept;
	}

	public String getPurchasedeptname() {
		return purchasedeptname;
	}

	public void setPurchasedeptname(String purchasedeptname) {
		this.purchasedeptname = purchasedeptname;
	}

	public Long getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(Long purchaseid) {
		this.purchaseid = purchaseid;
	}

	public String getPurchaseidname() {
		return purchaseidname;
	}

	public void setPurchaseidname(String purchaseidname) {
		this.purchaseidname = purchaseidname;
	}

	public String getReceiveaddr() {
		return receiveaddr;
	}

	public void setReceiveaddr(String receiveaddr) {
		this.receiveaddr = receiveaddr;
	}

	public String getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getProvidename() {
		return providename;
	}

	public void setProvidename(String providename) {
		this.providename = providename;
	}

	public String getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(String blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public Long getBlankoutid() {
		return blankoutid;
	}

	public void setBlankoutid(Long blankoutid) {
		this.blankoutid = blankoutid;
	}

	public String getBlankoutidname() {
		return blankoutidname;
	}

	public void setBlankoutidname(String blankoutidname) {
		this.blankoutidname = blankoutidname;
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

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	public String getIsblankoutname() {
		return isblankoutname;
	}

	public void setIsblankoutname(String isblankoutname) {
		this.isblankoutname = isblankoutname;
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
    
    

}
