package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class StockAlterMoveIdcode implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String samid;

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
    
    private Double packquantity;
    
    private String lcode;

    /** nullable persistent field */
    private String startno;

    /** nullable persistent field */
    private String endno;

    /** nullable persistent field */
    private String idcode;
    
    private Date makedate;
    
    private String nccode;

    private String cartonCode;
    private String palletCode;
    
    /** full constructor */
    public StockAlterMoveIdcode(Long id, String samid, String productid, Integer isidcode, String warehousebit, String batch, String producedate, String validate, Integer unitid, Double quantity, String startno, String endno, String idcode,String nccode) {
        this.id = id;
        this.samid = samid;
        this.productid = productid;
        this.isidcode = isidcode;
        this.warehousebit = warehousebit;
        this.batch = batch;
        this.producedate = producedate;
        this.validate = validate;
        this.unitid = unitid;
        this.quantity = quantity;
        this.startno = startno;
        this.endno = endno;
        this.idcode = idcode;
        this.nccode=nccode;
    }

    /** default constructor */
    public StockAlterMoveIdcode() {
    }

    /** minimal constructor */
    public StockAlterMoveIdcode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSamid() {
        return this.samid;
    }

    public void setSamid(String samid) {
        this.samid = samid;
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
    

    public String getLcode() {
		return lcode;
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
    
    

    public Double getPackquantity() {
		return packquantity;
	}

	public void setPackquantity(Double packquantity) {
		this.packquantity = packquantity;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
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
        if ( !(other instanceof StockAlterMoveIdcode) ) return false;
        StockAlterMoveIdcode castOther = (StockAlterMoveIdcode) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}
	
	public String getDetail() {
		return "id = " + id +
			   " , samid=" + samid +
			   " , productid ="  + productid +
			   " , isidcode= "  + isidcode +
			   " , warehousebit"  + warehousebit +
			   " , batch"  + batch +
			   " , producedate"  + producedate +
			   " , validate"  + validate +
			   " , unitid"  + unitid +
			   " , quantity"  + quantity +
			   " , packquantity"  + packquantity +
			   " , lcode"  + lcode +
			   " , startno"  + startno +
			   " , endno"  + endno +
			   " , idcode"  + idcode +
			   " , makedate"  + makedate +
			   " , nccode"  + nccode +
			   " , cartonCode"  + cartonCode +
			   " , palletCode"  + palletCode;
	}

}
