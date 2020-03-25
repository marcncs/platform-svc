package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class CIntegralDealForm extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String organid;
    
    private String organidname;

    /** nullable persistent field */
    private String cid;
    
    private String cidname;

    /** nullable persistent field */
    private Integer isort;
    
    private String isortname;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double completeintegral;

    /** nullable persistent field */
    private Integer issettlement;
    
    private String issettlementname;
    
    private String makedate;
    
    private String mobile;
    
    /** full constructor */
    public CIntegralDealForm(Long id, String billno, String organid, String cid, Integer isort, Double dealintegral, Double completeintegral, Integer issettlement) {
        this.id = id;
        this.billno = billno;
        this.organid = organid;
        this.cid = cid;
        this.isort = isort;
        this.dealintegral = dealintegral;
        this.completeintegral = completeintegral;
        this.issettlement = issettlement;
    }

    /** default constructor */
    public CIntegralDealForm() {
    }

    /** minimal constructor */
    public CIntegralDealForm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getIsort() {
        return this.isort;
    }

    public void setIsort(Integer isort) {
        this.isort = isort;
    }

    public Double getDealintegral() {
        return this.dealintegral;
    }

    public void setDealintegral(Double dealintegral) {
        this.dealintegral = dealintegral;
    }

    public Double getCompleteintegral() {
        return this.completeintegral;
    }

    public void setCompleteintegral(Double completeintegral) {
        this.completeintegral = completeintegral;
    }

    public Integer getIssettlement() {
        return this.issettlement;
    }

    public void setIssettlement(Integer issettlement) {
        this.issettlement = issettlement;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CIntegralDealForm) ) return false;
        CIntegralDealForm castOther = (CIntegralDealForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getCidname() {
		return cidname;
	}

	public void setCidname(String cidname) {
		this.cidname = cidname;
	}

	public String getIsortname() {
		return isortname;
	}

	public void setIsortname(String isortname) {
		this.isortname = isortname;
	}

	public String getIssettlementname() {
		return issettlementname;
	}

	public void setIssettlementname(String issettlementname) {
		this.issettlementname = issettlementname;
	}

	public String getOrganidname() {
		return organidname;
	}

	public void setOrganidname(String organidname) {
		this.organidname = organidname;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}
