package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TakeTicketDetail implements Serializable {

	private String remark;
	
    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ttid;

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
    
    /**确认数量*/
    private Double realQuantity;
    
    private Double cost;
    
    private Integer isread;
    
    private Integer isPicked = 0;
    
    //用于页面显示的箱数
    private Double xnum;
    
    private String nccode;
    
    private Integer boxnum;
    
    private Double scatternum;
    
    private Double stockQuantity;
    

    public Integer getIsPicked() {
		return isPicked;
	}

	public void setIsPicked(Integer isPicked) {
		this.isPicked = isPicked;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isRead) {
		this.isread = isRead;
	}

    public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	/** full constructor */
    public TakeTicketDetail(Integer id, String ttid, String productid, String productname, String specmode, Integer unitid, String batch, Double unitprice, Double quantity) {
        this.id = id;
        this.ttid = ttid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.unitprice = unitprice;
        this.quantity = quantity;
    }

    /** default constructor */
    public TakeTicketDetail() {
    }

    /** minimal constructor */
    public TakeTicketDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTtid() {
        return this.ttid;
    }

    public void setTtid(String ttid) {
        this.ttid = ttid;
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

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TakeTicketDetail) ) return false;
        TakeTicketDetail castOther = (TakeTicketDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getRealQuantity() {
		return realQuantity;
	}

	public void setRealQuantity(Double realQuantity) {
		this.realQuantity = realQuantity;
	}

	public Double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

}
