package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ProductIncomeDetail extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String piid;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double quantity;
    
    private Double factquantity;
    
    private Double costprice;
    
    private Double costsum;
    
    private Date makedate;
    private String nccode;
    private String batch;
    /**
     * 实际数量
     */
    private Double confirmQuantity;
    /**
     * 到最小单位的转换率
     */
    private Double convertQuantity;
    
    private Double boxQuantity;

    public Double getCostprice() {
		return costprice;
	}

	public void setCostprice(Double costprice) {
		this.costprice = costprice;
	}

	public Double getCostsum() {
		return costsum;
	}

	public void setCostsum(Double costsum) {
		this.costsum = costsum;
	}

	/** full constructor */
    public ProductIncomeDetail(Integer id, String piid, String productid, String productname, String specmode, Integer unitid, Double quantity,String nccode) {
        this.id = id;
        this.piid = piid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.quantity = quantity;
        this.nccode=nccode;
    }

    /** default constructor */
    public ProductIncomeDetail() {
    }

    /** minimal constructor */
    public ProductIncomeDetail(Integer id, String piid, String productid, Integer unitid) {
        this.id = id;
        this.piid = piid;
        this.productid = productid;
        this.unitid = unitid;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPiid() {
        return this.piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }
    

    public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Double getConfirmQuantity() {
		return confirmQuantity;
	}

	public void setConfirmQuantity(Double confirmQuantity) {
		this.confirmQuantity = confirmQuantity;
	}

	public Double getConvertQuantity() {
		return convertQuantity;
	}

	public void setConvertQuantity(Double convertQuantity) {
		this.convertQuantity = convertQuantity;
	}

	
	public Double getBoxQuantity() {
		return boxQuantity;
	}

	public void setBoxQuantity(Double boxQuantity) {
		this.boxQuantity = boxQuantity;
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

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductIncomeDetail) ) return false;
        ProductIncomeDetail castOther = (ProductIncomeDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getFactquantity() {
		return factquantity;
	}

	public void setFactquantity(Double factquantity) {
		this.factquantity = factquantity;
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

}
