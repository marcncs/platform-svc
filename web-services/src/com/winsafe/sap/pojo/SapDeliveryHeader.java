package com.winsafe.sap.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SapDeliveryHeader implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//发货单编号 
	private String dlvDocNo;
	//发货类型(J = Sales  T = Returns) 
	private String dlvDocType;
	//发货日期
	private Date dlvDate;
	//真实发货日期
	private Date tdlvDate;
	//发货工厂
	private String DistributionPlant;
	//收货机构代码
	private String soldToPartyCode;
	//收货机构名称
	private String soldToPartyName;
	//收货机构省份
	private String	soldToPartyProvince;
	//收货机构城市
	private String	soldToPartyCity;
	//收货机构区县
	private String	soldToPartyCountry;
	//送货地点机构代码
	private String	shipToPartyCode;
	//送货地点省份
	private String	shipToPartyProvince;
	//送货地点城市
	private String	shipToPartyCity ;
	//送货地点国家
	private String	shipToPartyCountry;
	//发货单IS系统ID号
	private String stockAlterMoveId; 
	
	private List<SapDeliveryDetail> dlvDetails = new ArrayList<SapDeliveryDetail>();

	public String getDlvDocNo() {
		return dlvDocNo;
	}
	public void setDlvDocNo(String dlvDocNo) {
		this.dlvDocNo = dlvDocNo;
	}
	public String getDlvDocType() {
		return dlvDocType;
	}
	public void setDlvDocType(String dlvDocType) {
		this.dlvDocType = dlvDocType;
	}
	public Date getDlvDate() {
		return dlvDate;
	}
	public void setDlvDate(Date dlvDate) {
		this.dlvDate = dlvDate;
	}
	public String getDistributionPlant() {
		return DistributionPlant;
	}
	public void setDistributionPlant(String distributionPlant) {
		DistributionPlant = distributionPlant;
	}
	public String getSoldToPartyCode() {
		return soldToPartyCode;
	}
	public void setSoldToPartyCode(String soldToPartyCode) {
		this.soldToPartyCode = soldToPartyCode;
	}
	public String getSoldToPartyName() {
		return soldToPartyName;
	}
	public void setSoldToPartyName(String soldToPartyName) {
		this.soldToPartyName = soldToPartyName;
	}
	public String getSoldToPartyProvince() {
		return soldToPartyProvince;
	}
	public void setSoldToPartyProvince(String soldToPartyProvince) {
		this.soldToPartyProvince = soldToPartyProvince;
	}
	public String getSoldToPartyCity() {
		return soldToPartyCity;
	}
	public void setSoldToPartyCity(String soldToPartyCity) {
		this.soldToPartyCity = soldToPartyCity;
	}
	public String getSoldToPartyCountry() {
		return soldToPartyCountry;
	}
	public void setSoldToPartyCountry(String soldToPartyCountry) {
		this.soldToPartyCountry = soldToPartyCountry;
	}
	public String getShipToPartyCode() {
		return shipToPartyCode;
	}
	public void setShipToPartyCode(String shipToPartycode) {
		this.shipToPartyCode = shipToPartycode;
	}
	public String getShipToPartyProvince() {
		return shipToPartyProvince;
	}
	public void setShipToPartyProvince(String shipToPartyProvince) {
		this.shipToPartyProvince = shipToPartyProvince;
	}
	public String getShipToPartyCity() {
		return shipToPartyCity;
	}
	public void setShipToPartyCity(String shipToPartyCity) {
		this.shipToPartyCity = shipToPartyCity;
	}
	public String getShipToPartyCountry() {
		return shipToPartyCountry;
	}
	public void setShipToPartyCountry(String shipToPartyCountry) {
		this.shipToPartyCountry = shipToPartyCountry;
	}
	
	public void addDeliveryDetails(SapDeliveryDetail dlvDetail) throws Exception {
		dlvDetail.setBatchLengthToTen();
		dlvDetails.add(dlvDetail);
	}
	public List<SapDeliveryDetail> getDlvDetails() {
		return dlvDetails;
	}
	public String getStockAlterMoveId() {
		return stockAlterMoveId;
	}
	public void setStockAlterMoveId(String stockAlterMoveId) {
		this.stockAlterMoveId = stockAlterMoveId;
	}
	public void setDlvDetails(List<SapDeliveryDetail> dlvDetails) {
		this.dlvDetails = dlvDetails;
	}
	public Date getTdlvDate() {
		return tdlvDate;
	}
	public void setTdlvDate(Date tdlvDate) {
		this.tdlvDate = tdlvDate;
	}
	

}
