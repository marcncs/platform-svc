package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganWithdrawDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String owid;

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
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;
    
    private Double ratifyquantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double subsum;
    
    //用于页面显示的箱数
    private Double xnum;
    private String nccode;
    
    private Integer boxnum;
    
    private Double scatternum;
    
    // 计量单位(用于页面显示)
    private Integer countunit;
    // 数量(计量单位)(用于页面显示)
    private Double cUnitQuantity;
    // 计量单位名称(用于页面显示)
    private String countUnitName;
    
    
    private String unitList;

    /** full constructor */
    public OrganWithdrawDetail(Integer id, String owid, String productid, String productname, String specmode, Integer unitid, String batch, Double unitprice, Double quantity, Double takequantity, Double subsum) {
        this.id = id;
        this.owid = owid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.takequantity = takequantity;
        this.subsum = subsum;
    }

    /** default constructor */
    public OrganWithdrawDetail() {
    }

    /** minimal constructor */
    public OrganWithdrawDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public Double getXnum() {
		return xnum;
	}

	public void setXnum(Double xnum) {
		this.xnum = xnum;
	}
	

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getOwid() {
        return this.owid;
    }

    public void setOwid(String owid) {
        this.owid = owid;
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

    public Double getUnitprice() {
        return this.unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    
    

    public Double getRatifyquantity() {
		return ratifyquantity;
	}

	public void setRatifyquantity(Double ratifyquantity) {
		this.ratifyquantity = ratifyquantity;
	}

	public Double getTakequantity() {
        return this.takequantity;
    }

    public void setTakequantity(Double takequantity) {
        this.takequantity = takequantity;
    }

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
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

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganWithdrawDetail) ) return false;
        OrganWithdrawDetail castOther = (OrganWithdrawDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
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

	public String getUnitList() {
		return unitList;
	}

	public void setUnitList(String unitList) {
		this.unitList = unitList;
	}

	public String getCountUnitName() {
		return countUnitName;
	}

	public void setCountUnitName(String countUnitName) {
		this.countUnitName = countUnitName;
	}

}
