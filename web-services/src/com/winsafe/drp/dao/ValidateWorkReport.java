package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class ValidateWorkReport
    extends ValidatorForm {

	/** identifier field */
    private Integer id;

    /** persistent field */
    private Integer createid;

    /** persistent field */
    private String reportcontent;

    /** persistent field */
    private Integer reportsort;

    /** nullable persistent field */
    private Date referdate;

    /** nullable persistent field */
    private Integer isrefer;

    /** nullable persistent field */
    private Integer approvestatus;

    /** nullable persistent field */
    private Date approvedate;

    /** nullable persistent field */
    private String remark;
    
    private FormFile affix;

	public Date getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public Integer getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	public Integer getCreateid() {
		return createid;
	}

	public void setCreateid(Integer createid) {
		this.createid = createid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsrefer() {
		return isrefer;
	}

	public void setIsrefer(Integer isrefer) {
		this.isrefer = isrefer;
	}

	public FormFile getAffix() {
		return affix;
	}

	public void setAffix(FormFile affix) {
		this.affix = affix;
	}

	public Date getReferdate() {
		return referdate;
	}

	public void setReferdate(Date referdate) {
		this.referdate = referdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReportcontent() {
		return reportcontent;
	}

	public void setReportcontent(String reportcontent) {
		this.reportcontent = reportcontent;
	}

	public Integer getReportsort() {
		return reportsort;
	}

	public void setReportsort(Integer reportsort) {
		this.reportsort = reportsort;
	}

	

	

}
