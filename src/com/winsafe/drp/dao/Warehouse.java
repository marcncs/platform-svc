package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Warehouse implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String warehousename;

    /** nullable persistent field */
    private Integer dept;

    /** nullable persistent field */
    private Integer userid;
    private String username;

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/** nullable persistent field */
    private String warehousetel;

    /** nullable persistent field */
    private Integer warehouseproperty;

    /** nullable persistent field */
    private String warehouseaddr;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer useflag;

    /** nullable persistent field */
    private String remark;
    
    private Integer province;
    
    private Integer city;
    
    private Integer areas;
    
    private Integer isautoreceive;
    
    /*是否允许负库存*/
    private Integer isMinusStock;

    private String nccode;
    /**
     * 仓库简称
     */
    private String shortname;
    
    //上限
    private String highNumber;
    
    //下限 
    private String belowNumber;
    
    //显示库存
    private Double stockpile;
    
    //显示库存状态(0安全，1不足，2超出)
    private Integer stockpilestruts;
    
    private Date creationTime;
    private Date modificationTime;
    
	/** full constructor */
    public Warehouse(String id, String warehousename, Integer dept, Integer userid, String warehousetel, Integer warehouseproperty, String warehouseaddr, String makeorganid, Integer makedeptid, Integer useflag, String remark,String nccode) {
        this.id = id;
        this.warehousename = warehousename;
        this.dept = dept;
        this.userid = userid;
        this.warehousetel = warehousetel;
        this.warehouseproperty = warehouseproperty;
        this.warehouseaddr = warehouseaddr;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.useflag = useflag;
        this.remark = remark;
        this.nccode = nccode;
    }

    /** default constructor */
    public Warehouse() {
    }

    /** minimal constructor */
    public Warehouse(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarehousename() {
        return this.warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public Integer getDept() {
        return this.dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getWarehousetel() {
        return this.warehousetel;
    }

    public void setWarehousetel(String warehousetel) {
        this.warehousetel = warehousetel;
    }

    public Integer getWarehouseproperty() {
        return this.warehouseproperty;
    }

    public void setWarehouseproperty(Integer warehouseproperty) {
        this.warehouseproperty = warehouseproperty;
    }

    public String getWarehouseaddr() {
        return this.warehouseaddr;
    }

    public void setWarehouseaddr(String warehouseaddr) {
        this.warehouseaddr = warehouseaddr;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Integer getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Integer makedeptid) {
        this.makedeptid = makedeptid;
    }

    public Integer getUseflag() {
        return this.useflag;
    }

    public void setUseflag(Integer useflag) {
        this.useflag = useflag;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    

    public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}
	

    public Integer getIsautoreceive() {
		return isautoreceive;
	}

	public void setIsautoreceive(Integer isautoreceive) {
		this.isautoreceive = isautoreceive;
	}


	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }
	

    public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public boolean equals(Object other) {
        if ( !(other instanceof Warehouse) ) return false;
        Warehouse castOther = (Warehouse) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getHighNumber() {
		return highNumber;
	}

	public void setHighNumber(String highNumber) {
		this.highNumber = highNumber;
	}

	public String getBelowNumber() {
		return belowNumber;
	}

	public void setBelowNumber(String belowNumber) {
		this.belowNumber = belowNumber;
	}

	public Double getStockpile() {
		return stockpile;
	}

	public void setStockpile(Double stockpile) {
		this.stockpile = stockpile;
	}

	public Integer getStockpilestruts() {
		return stockpilestruts;
	}

	public void setStockpilestruts(Integer stockpilestruts) {
		this.stockpilestruts = stockpilestruts;
	}

	public Integer getIsMinusStock() {
		return isMinusStock;
	}

	public void setIsMinusStock(Integer isMinusStock) {
		this.isMinusStock = isMinusStock;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModificationTime() {
		return modificationTime; 
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

}
