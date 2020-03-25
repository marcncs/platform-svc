package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ProductStockpileAll implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer countunit;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String producedate;

    /** nullable persistent field */
    private String vad;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private Double stockpile;

    /** nullable persistent field */
    private Double prepareout;

    /** nullable persistent field */
    private Integer islock;

    /** nullable persistent field */
    private Date makedate;
    //检验状态
    private Integer verifyStatus;
    //备注
    private String remark;
    //检验日期
    private Date verifydate;

    public Date getVerifydate() {
		return verifydate;
	}

	public void setVerifydate(Date verifydate) {
		this.verifydate = verifydate;
	}

	/** full constructor */
    public ProductStockpileAll(Long id, String productid, Integer countunit, String batch, String producedate, String validate, Double cost, String warehouseid, Double stockpile, Double prepareout, Integer islock, Date makedate) {
        this.id = id;
        this.productid = productid;
        this.countunit = countunit;
        this.batch = batch;
        this.producedate = producedate;
        this.vad = validate;
        this.cost = cost;
        this.warehouseid = warehouseid;
        this.stockpile = stockpile;
        this.prepareout = prepareout;
        this.islock = islock;
        this.makedate = makedate;
    }

    /** default constructor */
    public ProductStockpileAll() {
    }

    /** minimal constructor */
    public ProductStockpileAll(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductid() {
        return this.productid;
    }

    public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getCountunit() {
        return this.countunit;
    }

    public void setCountunit(Integer countunit) {
        this.countunit = countunit;
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


    public String getVad() {
		return vad;
	}

	public void setVad(String vad) {
		this.vad = vad;
	}

	public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public Double getStockpile() {
        return this.stockpile;
    }

    public void setStockpile(Double stockpile) {
        this.stockpile = stockpile;
    }

    public Double getPrepareout() {
        return this.prepareout;
    }

    public void setPrepareout(Double prepareout) {
        this.prepareout = prepareout;
    }

    public Integer getIslock() {
        return this.islock;
    }

    public void setIslock(Integer islock) {
        this.islock = islock;
    }

    public Date getMakedate() {
        return this.makedate;
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
        if ( !(other instanceof ProductStockpileAll) ) return false;
        ProductStockpileAll castOther = (ProductStockpileAll) other;
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
