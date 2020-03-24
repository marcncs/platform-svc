package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CustomerMatchOrder implements Serializable {

    /** identifier field */
    private Long id;
    private String siname;
    private String productline;
    private String organname;
    private Integer customerlevel;
    private Integer matchorder;
    private String outorder;
    private String remark;
    private String organid;
   

    /** default constructor */
    public CustomerMatchOrder() {
    }

    /** minimal constructor */
    public CustomerMatchOrder(Long id) {
        this.id = id;
    }

	public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
	
	public String getSiname() {
		return siname;
	}

	public void setSiname(String siname) {
		this.siname = siname;
	}

	public String getProductline() {
		return productline;
	}

	public void setProductline(String productline) {
		this.productline = productline;
	}

	public String getOrganname() {
		return organname;
	}

	public void setOrganname(String organname) {
		this.organname = organname;
	}

	public Integer getCustomerlevel() {
		return customerlevel;
	}

	public void setCustomerlevel(Integer customerlevel) {
		this.customerlevel = customerlevel;
	}

	public Integer getMatchorder() {
		return matchorder;
	}

	public void setMatchorder(Integer matchorder) {
		this.matchorder = matchorder;
	}

	public String getOutorder() {
		return outorder;
	}

	public void setOutorder(String outorder) {
		this.outorder = outorder;
	}

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CustomerMatchOrder) ) return false;
        CustomerMatchOrder castOther = (CustomerMatchOrder) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }


}
