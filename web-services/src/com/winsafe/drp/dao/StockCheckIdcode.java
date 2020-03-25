package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class StockCheckIdcode implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String scid;

    /** nullable persistent field */
    private String productid;

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

    /** nullable persistent field */
    private Double cquantity;

    /** nullable persistent field */
    private Double fquantity;

    /** nullable persistent field */
    private Double packquantity;

    /** nullable persistent field */
    private String lcode;

    /** nullable persistent field */
    private String startno;

    /** nullable persistent field */
    private String endno;

    /** nullable persistent field */
    private String idcode;

    /** nullable persistent field */
    private String cidcode;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public StockCheckIdcode(Long id, String scid, String productid, String warehousebit, String batch, String producedate, String validate, Integer unitid, Double quantity, Double cquantity, Double fquantity, Double packquantity, String lcode, String startno, String endno, String idcode, String cidcode, Date makedate) {
        this.id = id;
        this.scid = scid;
        this.productid = productid;
        this.warehousebit = warehousebit;
        this.batch = batch;
        this.producedate = producedate;
        this.validate = validate;
        this.unitid = unitid;
        this.quantity = quantity;
        this.cquantity = cquantity;
        this.fquantity = fquantity;
        this.packquantity = packquantity;
        this.lcode = lcode;
        this.startno = startno;
        this.endno = endno;
        this.idcode = idcode;
        this.cidcode = cidcode;
        this.makedate = makedate;
    }

    /** default constructor */
    public StockCheckIdcode() {
    }

    /** minimal constructor */
    public StockCheckIdcode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScid() {
        return this.scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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

    public Double getCquantity() {
        return this.cquantity;
    }

    public void setCquantity(Double cquantity) {
        this.cquantity = cquantity;
    }

    public Double getFquantity() {
        return this.fquantity;
    }

    public void setFquantity(Double fquantity) {
        this.fquantity = fquantity;
    }

    public Double getPackquantity() {
        return this.packquantity;
    }

    public void setPackquantity(Double packquantity) {
        this.packquantity = packquantity;
    }

    public String getLcode() {
        return this.lcode;
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

    public String getCidcode() {
        return this.cidcode;
    }

    public void setCidcode(String cidcode) {
        this.cidcode = cidcode;
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
        if ( !(other instanceof StockCheckIdcode) ) return false;
        StockCheckIdcode castOther = (StockCheckIdcode) other;
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
