package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PayableDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double goodsfund;

    /** nullable persistent field */
    private Double scot;

    /** nullable persistent field */
    private Double alreadyquantity;

    /** nullable persistent field */
    private Double alreadysum;

    /** nullable persistent field */
    private Integer isclose;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private Date makedate;
    
    private Date closedate;

    /** full constructor */
    public PayableDetail(Integer id, String pid, String productid, String productname, String specmode, String batch, String billno, Integer unitid, Double quantity, Double goodsfund, Double scot, Double alreadyquantity, Double alreadysum, Integer isclose, String poid, Date makedate) {

        this.id = id;
        this.pid = pid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.batch = batch;
        this.billno = billno;
        this.unitid = unitid;
        this.quantity = quantity;
        this.goodsfund = goodsfund;
        this.scot = scot;
        this.alreadyquantity = alreadyquantity;
        this.alreadysum = alreadysum;
        this.isclose = isclose;
        this.poid = poid;
        this.makedate = makedate;
    }

    /** default constructor */
    public PayableDetail() {
    }

    /** minimal constructor */
    public PayableDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
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

    public Double getGoodsfund() {
        return this.goodsfund;
    }

    public void setGoodsfund(Double goodsfund) {
        this.goodsfund = goodsfund;
    }

    public Double getScot() {
        return this.scot;
    }

    public void setScot(Double scot) {
        this.scot = scot;
    }

    public Double getAlreadyquantity() {
        return this.alreadyquantity;
    }

    public void setAlreadyquantity(Double alreadyquantity) {
        this.alreadyquantity = alreadyquantity;
    }

    public Double getAlreadysum() {
        return this.alreadysum;
    }

    public void setAlreadysum(Double alreadysum) {
        this.alreadysum = alreadysum;
    }

    public Integer getIsclose() {
        return this.isclose;
    }

    public void setIsclose(Integer isclose) {
        this.isclose = isclose;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
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
        if ( !(other instanceof PayableDetail) ) return false;
        PayableDetail castOther = (PayableDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Date getClosedate() {
		return closedate;
	}

	public void setClosedate(Date closedate) {
		this.closedate = closedate;
	}

}
