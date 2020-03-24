package com.winsafe.drp.dao;

public class OutlayDetailForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private Integer outlayprojectid;
    
    private String outlayprojectidname;

    /** nullable persistent field */
    private Double outlaysum;
    
    private String voucher;

    /** nullable persistent field */
    private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Integer getOutlayprojectid() {
		return outlayprojectid;
	}

	public void setOutlayprojectid(Integer outlayprojectid) {
		this.outlayprojectid = outlayprojectid;
	}

	public String getOutlayprojectidname() {
		return outlayprojectidname;
	}

	public void setOutlayprojectidname(String outlayprojectidname) {
		this.outlayprojectidname = outlayprojectidname;
	}

	public Double getOutlaysum() {
		return outlaysum;
	}

	public void setOutlaysum(Double outlaysum) {
		this.outlaysum = outlaysum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
}
