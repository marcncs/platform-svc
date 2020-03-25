package com.winsafe.drp.dao;

import java.util.Date;


public class WorkReportForm {

  private Integer id;
  private String createuser;
  private String reportcontent;
  private String reportsort;
  private String referdate;
  private String isrefer;
  private String approvestatus;
  private String remark;
  private String approveuser;
  private String approvecontent;
  private String approvedate;
  private Date makedate;
  private String affix;
  public String getAffix() {
	return affix;
}

public void setAffix(String affix) {
	this.affix = affix;
}

public Date getMakedate() {
	return makedate;
}

public void setMakedate(Date makedate) {
	this.makedate = makedate;
}

public String getApprovecontent() {
    return approvecontent;
  }

  public void setApprovecontent(String approvecontent) {
    this.approvecontent = approvecontent;
  }

  public void setReportsort(String reportsort) {
    this.reportsort = reportsort;
  }

  public void setReportcontent(String reportcontent) {
    this.reportcontent = reportcontent;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public void setReferdate(String referdate) {
    this.referdate = referdate;
  }

  public void setCreateuser(String createuser) {
    this.createuser = createuser;
  }

  public void setApproveuser(String approveuser) {
    this.approveuser = approveuser;
  }

  public void setApprovedate(String approvedate) {
    this.approvedate = approvedate;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getApproveuser() {
    return approveuser;
  }

  public String getCreateuser() {
    return createuser;
  }

  public String getApprovestatus() {
	return approvestatus;
}

public void setApprovestatus(String approvestatus) {
	this.approvestatus = approvestatus;
}

public String getReferdate() {
    return referdate;
  }

  public String getRemark() {
    return remark;
  }

  public String getReportcontent() {
    return reportcontent;
  }

  public String getReportsort() {
    return reportsort;
  }

  public String getApprovedate() {
    return approvedate;
  }

  public Integer getId() {
    return id;
  }

public String getIsrefer() {
	return isrefer;
}

public void setIsrefer(String isrefer) {
	this.isrefer = isrefer;
}
}
