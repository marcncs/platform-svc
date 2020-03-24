package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class StockMoveDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String smid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double cost;
    
    private Date makedate;

    private String nccode;
    
    private Integer boxnum;
    
    private Double scatternum;
    
    // 计量单位(用于页面显示)
    private Integer countunit;
    // 数量(计量单位)(用于页面显示)
    private Double cUnitQuantity;

    
    //用于页面显示的箱数
    private Double xnum;
    /** full constructor */
    public StockMoveDetail(Integer id, String smid, String productid, String productname, String specmode, Integer unitid, String batch, Double quantity, Double takequantity, Double cost,String nccode) {
        this.id = id;
        this.smid = smid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.quantity = quantity;
        this.takequantity = takequantity;
        this.cost = cost;
        this.nccode=nccode;
    }

    /** default constructor */
    public StockMoveDetail() {
    }

    /** minimal constructor */
    public StockMoveDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmid() {
        return this.smid;
    }

    public void setSmid(String smid) {
        this.smid = smid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
    

    public Integer getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(Integer boxnum) {
		this.boxnum = boxnum;
	}

	public Double getScatternum() {
		return scatternum;
	}

	public void setScatternum(Double scatternum) {
		this.scatternum = scatternum;
	}

	public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    
    public Double getXnum() {
		return xnum;
	}

	public void setXnum(Double xnum) {
		this.xnum = xnum;
	}

	public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTakequantity() {
        return this.takequantity;
    }

    public void setTakequantity(Double takequantity) {
        this.takequantity = takequantity;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StockMoveDetail) ) return false;
        StockMoveDetail castOther = (StockMoveDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public Integer getCountunit() {
		return countunit;
	}

	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}

	public Double getcUnitQuantity() {
		return cUnitQuantity;
	}

	public void setcUnitQuantity(Double cUnitQuantity) {
		this.cUnitQuantity = cUnitQuantity;
	}
	
	

}
