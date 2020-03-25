package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IsupplySaleIdcode implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ssmid;

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

    /** nullable persistent field */
    private Double standquantity;

    /** nullable persistent field */
    private String lcode;

    /** nullable persistent field */
    private String startno;

    /** nullable persistent field */
    private String endno;

    /** nullable persistent field */
    private String idcode;

    /** full constructor */
    public IsupplySaleIdcode(Integer id, String ssmid, String productid, Integer isidcode, String warehousebit, String batch, String producedate, String validate, Integer unitid, Double quantity, Double standquantity, String lcode, String startno, String endno, String idcode) {
        this.id = id;
        this.ssmid = ssmid;
        this.productid = productid;
        this.isidcode = isidcode;
        this.warehousebit = warehousebit;
        this.batch = batch;
        this.producedate = producedate;
        this.validate = validate;
        this.unitid = unitid;
        this.quantity = quantity;
        this.standquantity = standquantity;
        this.lcode = lcode;
        this.startno = startno;
        this.endno = endno;
        this.idcode = idcode;
    }

    /** default constructor */
    public IsupplySaleIdcode() {
    }

    /** minimal constructor */
    public IsupplySaleIdcode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsmid() {
        return this.ssmid;
    }

    public void setSsmid(String ssmid) {
        this.ssmid = ssmid;
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

    public Double getStandquantity() {
        return this.standquantity;
    }

    public void setStandquantity(Double standquantity) {
        this.standquantity = standquantity;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IsupplySaleIdcode) ) return false;
        IsupplySaleIdcode castOther = (IsupplySaleIdcode) other;
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
