package com.winsafe.drp.dao;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WorkReportExecuteForm {

  private String approve;
  private String approveuser;
  private String approvecontent;
  public String getApprovecontent() {
    return approvecontent;
  }

  public void setApprovecontent(String approvecontent) {
    this.approvecontent = approvecontent;
  }

  public void setApproveuser(String approveuser) {
    this.approveuser = approveuser;
  }

  public void setApprove(String approve) {
    this.approve = approve;
  }

  public String getApproveuser() {
    return approveuser;
  }

  public String getApprove() {
    return approve;
  }



}
