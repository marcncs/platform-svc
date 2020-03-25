package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TakeTicketIdcode implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String ttid;

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
    private String vad;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double quantity;

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
    
    private Integer issplit;

    /** nullable persistent field */
    private Date makedate;

    private String warehousefromid;
    private String organtoid;
    private String warehousetoid;
    private String organfromid;
    
    private String cartonCode;
    private String palletCode;
    
    private Double bonuspoint;

    
    /** full constructor */
    public TakeTicketIdcode(Long id, String ttid, String productid, Integer isidcode, String warehousebit, String batch, String producedate, String validate, Integer unitid, Double quantity, Double packquantity, String lcode, String startno, String endno, String idcode, Date makedate) {
        this.id = id;
        this.ttid = ttid;
        this.productid = productid;
        this.isidcode = isidcode;
        this.warehousebit = warehousebit;
        this.batch = batch;
        this.producedate = producedate;
        this.vad = validate;
        this.unitid = unitid;
        this.quantity = quantity;
        this.packquantity = packquantity;
        this.lcode = lcode;
        this.startno = startno;
        this.endno = endno;
        this.idcode = idcode;
        this.makedate = makedate;
    }

    /** default constructor */
    public TakeTicketIdcode() {
    }

    /** minimal constructor */
    public TakeTicketIdcode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

   
    public String getVad() {
		return vad;
	}

	public void setVad(String vad) {
		this.vad = vad;
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
    
    

    public Integer getIssplit() {
		return issplit;
	}

	public void setIssplit(Integer issplit) {
		this.issplit = issplit;
	}

	public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    
    public String getWarehousefromid() {
		return warehousefromid;
	}

	public void setWarehousefromid(String warehousefromid) {
		this.warehousefromid = warehousefromid;
	}

	public String getOrgantoid() {
		return organtoid;
	}

	public void setOrgantoid(String organtoid) {
		this.organtoid = organtoid;
	}

	public String getWarehousetoid() {
		return warehousetoid;
	}

	public void setWarehousetoid(String warehousetoid) {
		this.warehousetoid = warehousetoid;
	}

	public String getOrganfromid() {
		return organfromid;
	}

	public void setOrganfromid(String organfromid) {
		this.organfromid = organfromid;
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

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TakeTicketIdcode) ) return false;
        TakeTicketIdcode castOther = (TakeTicketIdcode) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getBonuspoint() {
		return bonuspoint;
	}

	public void setBonuspoint(Double bonuspoint) {
		this.bonuspoint = bonuspoint;
	}

    
}
