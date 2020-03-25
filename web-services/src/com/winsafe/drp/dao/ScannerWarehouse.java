package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ScannerWarehouse implements Serializable {

    /** identifier field */
    private String id;

    /**
     * 仓库编号
     */
    private String warehouseid;
    /**
     * 采集器编号
     */
    private String scannerid;

    private String wareHouseName;
    
    private String orgName;

	/** default constructor */
    public ScannerWarehouse() {
    }

  	public boolean equals(Object other) {
        if ( !(other instanceof ScannerWarehouse) ) return false;
        ScannerWarehouse castOther = (ScannerWarehouse) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getScannerid() {
		return scannerid;
	}

	public void setScannerid(String scannerid) {
		this.scannerid = scannerid;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return orgName;
	}

}
