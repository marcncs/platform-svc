package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.struts.action.ActionForm;

/**
 * DrawShipmentBillIdcode entity. @author MyEclipse Persistence Tools
 */
//@Entity
//@Table(name = "draw_shipment_bill_idcode", schema = "dbo", catalog = "ChemturaLogisticDB")
public class CopyOfDrawShipmentBillIdcode extends ActionForm implements Serializable  {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4766739190203859519L;
	private long id;
	private String dsid;
	private String productId;
	private Integer isIdcode;
	private String warehouseBit;
	private String batch;
	private String produceDate;
	private String valiDate;
	private Integer unitId;
	private double quantity;
	private double packQuantity;
	private String lcode;
	private String startNo;
	private String endNo;
	private String idcode;
	private Date makeDate;

	// Constructors

	/** default constructor */
	public CopyOfDrawShipmentBillIdcode() {
	}

	/** minimal constructor */
	public CopyOfDrawShipmentBillIdcode(long id, String dsid, String productId) {
		this.id = id;
		this.dsid = dsid;
		this.productId = productId;
	}

	/** full constructor */
	public CopyOfDrawShipmentBillIdcode(long id, String dsid, String productId,
			Integer isIdcode, String warehouseBit, String batch,
			String produceDate, String valiDate, Integer unitId,
			double quantity, double packQuantity, String lcode, String startNo,
			String endNo, String idcode, Date makeDate) {
		this.id = id;
		this.dsid = dsid;
		this.productId = productId;
		this.isIdcode = isIdcode;
		this.warehouseBit = warehouseBit;
		this.batch = batch;
		this.produceDate = produceDate;
		this.valiDate = valiDate;
		this.unitId = unitId;
		this.quantity = quantity;
		this.packQuantity = packQuantity;
		this.lcode = lcode;
		this.startNo = startNo;
		this.endNo = endNo;
		this.idcode = idcode;
		this.makeDate = makeDate;
	}

	// Property accessors
	//@Id
	//@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	//@Column(name = "dsid", nullable = false, length = 32)
	public String getdsid() {
		return this.dsid;
	}

	public void setdsid(String dsid) {
		this.dsid = dsid;
	}

	//@Column(name = "ProductID", nullable = false, length = 32)
	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	//@Column(name = "IsIDCode")
	public Integer getIsIdcode() {
		return this.isIdcode;
	}

	public void setIsIdcode(Integer isIdcode) {
		this.isIdcode = isIdcode;
	}

	//@Column(name = "WarehouseBit", length = 12)
	public String getWarehouseBit() {
		return this.warehouseBit;
	}

	public void setWarehouseBit(String warehouseBit) {
		this.warehouseBit = warehouseBit;
	}

	//@Column(name = "Batch", length = 32)
	public String getBatch() {
		return this.batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	//@Column(name = "ProduceDate", length = 32)
	public String getProduceDate() {
		return this.produceDate;
	}

	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}

	//@Column(name = "ValiDate", length = 32)
	public String getValiDate() {
		return this.valiDate;
	}

	public void setValiDate(String valiDate) {
		this.valiDate = valiDate;
	}

	//@Column(name = "UnitID")
	public Integer getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	//@Column(name = "Quantity", precision = 18)
	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	//@Column(name = "PackQuantity", precision = 18)
	public double getPackQuantity() {
		return this.packQuantity;
	}

	public void setPackQuantity(double packQuantity) {
		this.packQuantity = packQuantity;
	}

	//@Column(name = "LCode", length = 4)
	public String getLcode() {
		return this.lcode;
	}

	public void setLcode(String lcode) {
		this.lcode = lcode;
	}

	//@Column(name = "StartNo", length = 32)
	public String getStartNo() {
		return this.startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	//@Column(name = "EndNo", length = 32)
	public String getEndNo() {
		return this.endNo;
	}

	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	//@Column(name = "IDCode", length = 64)
	public String getIdcode() {
		return this.idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	//@Column(name = "MakeDate", length = 23)
	public Date getMakeDate() {
		return this.makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

}