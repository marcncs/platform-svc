package com.winsafe.drp.dao;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author : jerry
 * @version : 2009-11-6 上午10:41:39 www.winsafe.cn
 */
public class DocSortVisit {

	private Integer id;
	private Integer dsid;
	private Integer userid;

	
	
	
	public DocSortVisit() {
		super();
	}

	public DocSortVisit(Integer id, Integer dsid, Integer userid) {
		super();
		this.id = id;
		this.dsid = dsid;
		this.userid = userid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDsid() {
		return dsid;
	}

	public void setDsid(Integer dsid) {
		this.dsid = dsid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	 public String toString() {
	        return new ToStringBuilder(this)
	            .append("id", getId())
	            .toString();
	    }

	    public boolean equals(Object other) {
	        if ( !(other instanceof WarehouseVisit) ) return false;
	        WarehouseVisit castOther = (WarehouseVisit) other;
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
