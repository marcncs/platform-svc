package com.winsafe.drp.dao;


public class CashWasteBookForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer cbid;
    
    private String cbidname;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Double cyclefirstsum;

    /** nullable persistent field */
    private Double cycleinsum;

    /** nullable persistent field */
    private Double cycleoutsum;

    /** nullable persistent field */
    private Double cyclebalancesum;

    /** nullable persistent field */
    private String recorddate;

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Integer getCbid() {
		return cbid;
	}

	public void setCbid(Integer cbid) {
		this.cbid = cbid;
	}

	public String getCbidname() {
		return cbidname;
	}

	public void setCbidname(String cbidname) {
		this.cbidname = cbidname;
	}

	public Double getCyclebalancesum() {
		return cyclebalancesum;
	}

	public void setCyclebalancesum(Double cyclebalancesum) {
		this.cyclebalancesum = cyclebalancesum;
	}

	public Double getCyclefirstsum() {
		return cyclefirstsum;
	}

	public void setCyclefirstsum(Double cyclefirstsum) {
		this.cyclefirstsum = cyclefirstsum;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}

}
