package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class PurchaseSalesReport extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String warehouseid;
    
    private String warehousebit;

    /** nullable persistent field */
    private String billcode;
    
    private String memo;

    /** nullable persistent field */
    private Double cyclefirstquantity;

    /** nullable persistent field */
    private Double cycleinquantity;
    
    private Double cycleoutquantity;

    /** nullable persistent field */
    private Double cyclebalancequantity;

    /** nullable persistent field */
    private Date recorddate;
    
    private Integer isDelete;

    /** full constructor */
    public PurchaseSalesReport(Long id, String productid, String batch, String warehouseid, String billcode, Double cyclefirstquantity, Double cycleinquantity,Double cycleoutquantity, Double cyclebalancequantity, Date recorddate) {
        this.id = id;
        this.productid = productid;
        this.batch = batch;
        this.warehouseid = warehouseid;
        this.billcode = billcode;
        this.cyclefirstquantity = cyclefirstquantity;
        this.cycleinquantity = cycleinquantity;
        this.cycleoutquantity = cycleoutquantity;
        this.cyclebalancequantity = cyclebalancequantity;
        this.recorddate = recorddate;
    }

    /** default constructor */
    public PurchaseSalesReport() {
    }

    /** minimal constructor */
    public PurchaseSalesReport(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }
    
    

    public String getWarehousebit() {
		return warehousebit;
	}

	public void setWarehousebit(String warehousebit) {
		this.warehousebit = warehousebit;
	}

	public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

   
    public Date getRecorddate() {
        return this.recorddate;
    }

    public void setRecorddate(Date recorddate) {
        this.recorddate = recorddate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchaseSalesReport) ) return false;
        PurchaseSalesReport castOther = (PurchaseSalesReport) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getCycleoutquantity() {
		return cycleoutquantity;
	}

	public void setCycleoutquantity(Double cycleoutquantity) {
		this.cycleoutquantity = cycleoutquantity;
	}

	public void setCyclebalancequantity(Double cyclebalancequantity) {
		this.cyclebalancequantity = cyclebalancequantity;
	}

	public void setCyclefirstquantity(Double cyclefirstquantity) {
		this.cyclefirstquantity = cyclefirstquantity;
	}

	public void setCycleinquantity(Double cycleinquantity) {
		this.cycleinquantity = cycleinquantity;
	}

	public Double getCyclebalancequantity() {
		return cyclebalancequantity;
	}

	public Double getCyclefirstquantity() {
		return cyclefirstquantity;
	}

	public Double getCycleinquantity() {
		return cycleinquantity;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
