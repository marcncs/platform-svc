package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Idcode implements Serializable {

    /** identifier field */
    private String idcode;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String producedate;

    /** nullable persistent field */
    private String vad;

    /** nullable persistent field */
    private String lcode;

    /** nullable persistent field */
    private String startno;

    /** nullable persistent field */
    private String endno;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double quantity;
    
    private Double fquantity;

    /** nullable persistent field */
    private Double packquantity;

    /** nullable persistent field */
    private Integer isuse;
    
    private Integer isout;

    /** nullable persistent field */
    private String billid;

    /** nullable persistent field */
    private Integer idbilltype;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private String warehousebit;

    /** nullable persistent field */
    private String provideid;

    /** nullable persistent field */
    private String providename;

    /** nullable persistent field */
    private Date makedate;
    
    private Integer pcolumn;
    
    private String dealerreceive;
    
    //检验状态
    private Integer verifyStatus;
    //检验日期
    private Date verifydate;
    private String boxCode;
    private String cartonCode;
    private String palletCode;
    private String ncLotNo;
    //是否可积分
    private Integer isIntegral;
    
    public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Date getVerifydate() {
		return verifydate;
	}

	public void setVerifydate(Date verifydate) {
		this.verifydate = verifydate;
	}

	/** full constructor */
    public Idcode(String idcode, String productid, String productname, String batch, String producedate, String validate, String lcode, String startno, String endno, Integer unitid, Double quantity, Double packquantity, Integer isuse, String billid, Integer idbilltype, String makeorganid, String warehouseid, String warehousebit, String provideid, String providename, Date makedate) {
        this.idcode = idcode;
        this.productid = productid;
        this.productname = productname;
        this.batch = batch;
        this.producedate = producedate;
        this.vad = validate;
        this.lcode = lcode;
        this.startno = startno;
        this.endno = endno;
        this.unitid = unitid;
        this.quantity = quantity;
        this.packquantity = packquantity;
        this.isuse = isuse;
        this.billid = billid;
        this.idbilltype = idbilltype;
        this.makeorganid = makeorganid;
        this.warehouseid = warehouseid;
        this.warehousebit = warehousebit;
        this.provideid = provideid;
        this.providename = providename;
        this.makedate = makedate;
    }

    /** default constructor */
    public Idcode() {
    }

    
    public String getNcLotNo() {
		return ncLotNo;
	}

	public void setNcLotNo(String ncLotNo) {
		this.ncLotNo = ncLotNo;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getCartonCode() {
		return cartonCode;
	}

	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}

	public String getPalletCode() {
		return palletCode;
	}

	public void setPalletCode(String palletCode) {
		this.palletCode = palletCode;
	}

	/** minimal constructor */
    public Idcode(String idcode) {
        this.idcode = idcode;
    }

    public String getIdcode() {
        return this.idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
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

    public Double getPackquantity() {
        return this.packquantity;
    }

    public void setPackquantity(Double packquantity) {
        this.packquantity = packquantity;
    }

    public Integer getIsuse() {
        return this.isuse;
    }

    public void setIsuse(Integer isuse) {
        this.isuse = isuse;
    }

    public String getBillid() {
        return this.billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public Integer getIdbilltype() {
        return this.idbilltype;
    }

    public void setIdbilltype(Integer idbilltype) {
        this.idbilltype = idbilltype;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getWarehousebit() {
        return this.warehousebit;
    }

    public void setWarehousebit(String warehousebit) {
        this.warehousebit = warehousebit;
    }

    public String getProvideid() {
        return this.provideid;
    }

    public void setProvideid(String provideid) {
        this.provideid = provideid;
    }

    public String getProvidename() {
        return this.providename;
    }

    public void setProvidename(String providename) {
        this.providename = providename;
    }

    public Date getMakedate() {
        return this.makedate;
    }
    
    

    public Double getFquantity() {
		return fquantity;
	}

	public void setFquantity(Double fquantity) {
		this.fquantity = fquantity;
	}

	public Integer getIsout() {
		return isout;
	}

	public void setIsout(Integer isout) {
		this.isout = isout;
	}

	public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }
	
	

    public Integer getPcolumn() {
		return pcolumn;
	}

	public void setPcolumn(Integer pcolumn) {
		this.pcolumn = pcolumn;
	}
	
	public String getDealerreceive() {
		return dealerreceive;
	}

	public void setDealerreceive(String dealerreceive) {
		this.dealerreceive = dealerreceive;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("idcode", getIdcode())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Idcode) ) return false;
        Idcode castOther = (Idcode) other;
        return new EqualsBuilder()
            .append(this.getIdcode(), castOther.getIdcode())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getIdcode())
            .toHashCode();
    }

	public Integer getIsIntegral() {
		return isIntegral;
	}

	public void setIsIntegral(Integer isIntegral) {
		this.isIntegral = isIntegral;
	}

}
