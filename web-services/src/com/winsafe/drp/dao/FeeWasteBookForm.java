package com.winsafe.drp.dao;


public class FeeWasteBookForm {
	 /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private Long feedept;
    
    private String feedeptname;

    /** nullable persistent field */
    private Long feeid;
    
    private String feeidname;

    /** nullable persistent field */
    private String porject;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Double cycleinsum;

    /** nullable persistent field */
    private Double cycleoutsum;

    /** nullable persistent field */
    private String recorddate;

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

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Double getCycleinsum() {
		return cycleinsum;
	}

	public void setCycleinsum(Double cycleinsum) {
		this.cycleinsum = cycleinsum;
	}

	public Double getCycleoutsum() {
		return cycleoutsum;
	}

	public void setCycleoutsum(Double cycleoutsum) {
		this.cycleoutsum = cycleoutsum;
	}

	public Long getFeedept() {
		return feedept;
	}

	public void setFeedept(Long feedept) {
		this.feedept = feedept;
	}

	public String getFeedeptname() {
		return feedeptname;
	}

	public void setFeedeptname(String feedeptname) {
		this.feedeptname = feedeptname;
	}

	public Long getFeeid() {
		return feeid;
	}

	public void setFeeid(Long feeid) {
		this.feeid = feeid;
	}

	public String getFeeidname() {
		return feeidname;
	}

	public void setFeeidname(String feeidname) {
		this.feeidname = feeidname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPorject() {
		return porject;
	}

	public void setPorject(String porject) {
		this.porject = porject;
	}

	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}
}
