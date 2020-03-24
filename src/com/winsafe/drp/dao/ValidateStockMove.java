package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.validator.ValidatorForm;


public class ValidateStockMove extends ValidatorForm{

    private Long id;
    private Date movedate;
    private Long handleid;
    private Long pid;
    private Long outwarehouseid;
    private Long inwarehouseid;
    private Integer movequantity;
    private String movecause;
    private String remark;
  public Long getHandleid() {
    return handleid;
  }

  public void setHandleid(Long handleid) {
    this.handleid = handleid;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public void setOutwarehouseid(Long outwarehouseid) {
    this.outwarehouseid = outwarehouseid;
  }

  public void setMovequantity(Integer movequantity) {
    this.movequantity = movequantity;
  }

  public void setMovedate(Date movedate) {
    this.movedate = movedate;
  }

  public void setMovecause(String movecause) {
    this.movecause = movecause;
  }

  public void setInwarehouseid(Long inwarehouseid) {
    this.inwarehouseid = inwarehouseid;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Long getInwarehouseid() {
    return inwarehouseid;
  }

  public String getMovecause() {
    return movecause;
  }

  public Date getMovedate() {
    return movedate;
  }

  public Integer getMovequantity() {
    return movequantity;
  }

  public Long getOutwarehouseid() {
    return outwarehouseid;
  }

  public Long getPid() {
    return pid;
  }

  public String getRemark() {
    return remark;
  }
}
