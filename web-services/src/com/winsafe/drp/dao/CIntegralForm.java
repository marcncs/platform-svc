package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class CIntegralForm extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;
    
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
    private Double cintegral;

    /** full constructor */
    public CIntegralForm(Long id, String organid, String cid, Integer isort, Double cintegral) {
        this.id = id;
        this.organid = organid;
        this.cid = cid;
        this.isort = isort;
        this.cintegral = cintegral;
    }

    /** default constructor */
    public CIntegralForm() {
    }

    /** minimal constructor */
    public CIntegralForm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getCintegral() {
        return this.cintegral;
    }

    public void setCintegral(Double cintegral) {
        this.cintegral = cintegral;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CIntegralForm) ) return false;
        CIntegralForm castOther = (CIntegralForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
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

	public String getOrganidname() {
		return organidname;
	}

	public void setOrganidname(String organidname) {
		this.organidname = organidname;
	}

}
