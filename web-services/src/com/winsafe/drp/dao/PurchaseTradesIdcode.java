package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseTradesIdcode implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String ptid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer isidcode;

    /** nullable persistent field */
    private String warehousebit;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String producedate;

    /** nullable persistent field */
    private String validate;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double quantity;
    
    private Double packquantity;
    
    private String lcode;

    /** nullable persistent field */
    private String startno;

    /** nullable persistent field */
    private String endno;

    /** nullable persistent field */
    private String idcode;
    
    private Date makedate;

    /** full constructor */
    public PurchaseTradesIdcode(Long id, String ptid, String productid, Integer isidcode, String warehousebit, String batch, String producedate, String validate, Integer unitid, Double quantity, String startno, String endno, String idcode) {
        this.id = id;
        this.ptid = ptid;
        this.productid = productid;
        this.isidcode = isidcode;
        this.warehousebit = warehousebit;
        this.batch = batch;
        this.producedate = producedate;
        this.validate = validate;
        this.unitid = unitid;
        this.quantity = quantity;
        this.startno = startno;
        this.endno = endno;
        this.idcode = idcode;
    }

    /** default constructor */
    public PurchaseTradesIdcode() {
    }

    /** minimal constructor */
    public PurchaseTradesIdcode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPtid() {
        return this.ptid;
    }

    public void setPtid(String ptid) {
        this.ptid = ptid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getIsidcode() {
        return this.isidcode;
    }

    public void setIsidcode(Integer isidcode) {
        this.isidcode = isidcode;
    }

    public String getWarehousebit() {
        return this.warehousebit;
    }

    public void setWarehousebit(String warehousebit) {
        this.warehousebit = warehousebit;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getProducedate() {
        return this.producedate;
    }

    public void setProducedate(String producedate) {
        this.producedate = producedate;
    }

    public String getValidate() {
        return this.validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    

    public String getLcode() {
		return lcode;
	}

	public void setLcode(String lcode) {
		this.lcode = lcode;
	}

	public String getStartno() {
        return this.startno;
    }

    public void setStartno(String startno) {
        this.startno = startno;
    }

    public String getEndno() {
        return this.endno;
    }

    public void setEndno(String endno) {
        this.endno = endno;
    }

    public String getIdcode() {
        return this.idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    
    

    public Double getPackquantity() {
		return packquantity;
	}

	public void setPackquantity(Double packquantity) {
		this.packquantity = packquantity;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchaseTradesIdcode) ) return false;
        PurchaseTradesIdcode castOther = (PurchaseTradesIdcode) other;
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
