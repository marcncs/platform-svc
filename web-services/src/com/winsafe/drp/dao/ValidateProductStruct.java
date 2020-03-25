package com.winsafe.drp.dao;

import org.apache.struts.validator.ValidatorForm;

public class ValidateProductStruct extends ValidatorForm{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5319616874746378016L;

	/** identifier field */
    private Long id;

    /** persistent field */
    private String sortname;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Long parentid;

    /** nullable persistent field */
    private Integer useflag;
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUseflag(Integer useflag) {
    this.useflag = useflag;
  }

  public void setSortname(String sortname) {
    this.sortname = sortname;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public void setParentid(Long parentid) {
    this.parentid = parentid;
  }

  public Long getParentid() {
    return parentid;
  }

  public String getRemark() {
    return remark;
  }

  public String getSortname() {
    return sortname;
  }

  public Integer getUseflag() {
    return useflag;
  }

}
