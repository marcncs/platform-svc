package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class BarcodeInventoryIdcode implements Serializable{

	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String osid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer isidcode;

    /** nullable persistent field */
    private String warehousebit;

    /** nullable persistent field */
    private String batch_;

    /** nullable persistent field */
    private String producedate;

    /** nullable persistent field */
    private String validate_;

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

	public BarcodeInventoryIdcode() {
	}
	
	public BarcodeInventoryIdcode(Long id, String osid, String productid, Integer isidcode, String warehousebit, String batch, String producedate, String validate, Integer unitid, Double quantity, Double packquantity, String lcode, String startno, String endno, String idcode, Date makedate) {
        this.id = id;
        this.osid = osid;
        this.productid = productid;
        this.isidcode = isidcode;
        this.warehousebit = warehousebit;
        this.setBatch_(batch);
        this.producedate = producedate;
        this.setValidate_(validate);
        this.unitid = unitid;
        this.quantity = quantity;
        this.packquantity = packquantity;
        this.lcode = lcode;
        this.startno = startno;
        this.endno = endno;
        this.idcode = idcode;
        this.makedate = makedate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOsid() {
		return osid;
	}

	public void setOsid(String osid) {
		this.osid = osid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public Integer getIsidcode() {
		return isidcode;
	}

	public void setIsidcode(Integer isidcode) {
		this.isidcode = isidcode;
	}

	public String getWarehousebit() {
		return warehousebit;
	}

	public void setWarehousebit(String warehousebit) {
		this.warehousebit = warehousebit;
	}

	

	public String getProducedate() {
		return producedate;
	}

	public void setProducedate(String producedate) {
		this.producedate = producedate;
	}

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPackquantity() {
		return packquantity;
	}

	public void setPackquantity(Double packquantity) {
		this.packquantity = packquantity;
	}

	public String getLcode() {
		return lcode;
	}

	public void setLcode(String lcode) {
		this.lcode = lcode;
	}

	public String getStartno() {
		return startno;
	}

	public void setStartno(String startno) {
		this.startno = startno;
	}

	public String getEndno() {
		return endno;
	}

	public void setEndno(String endno) {
		this.endno = endno;
	}

	public String getIdcode() {
		return idcode;
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
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public void setBatch_(String batch_) {
		this.batch_ = batch_;
	}

	public String getBatch_() {
		return batch_;
	}

	public void setValidate_(String validate_) {
		this.validate_ = validate_;
	}

	public String getValidate_() {
		return validate_;
	}

}
