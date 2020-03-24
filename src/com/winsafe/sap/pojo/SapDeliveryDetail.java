package com.winsafe.sap.pojo;

import java.io.Serializable;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.util.Constants;

public class SapDeliveryDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//发货单明细序号
	private String	dlvLineItemNo;
	//物料编号
	private String	materialCode;
	//批次号
	private String	batchNo;
	//发货数量
	private String	dlvQuantity;
	//托盘码
	private String	palletCode;
	//箱码
	private String	cartonCode;
	private Long stockAlterMoveIdcodeId;
	private Long takeTicketIdcode;
	//生产日期
	private String produceDate;
	public String getDlvLineItemNo() {
		return dlvLineItemNo;
	}
	public void setDlvLineItemNo(String dlvLineItemNo) {
		this.dlvLineItemNo = dlvLineItemNo;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getDlvQuantity() {
		return dlvQuantity;
	}
	public void setDlvQuantity(String dlvQuantity) {
		this.dlvQuantity = dlvQuantity;
	}
	public String getPalletCode() {
		return palletCode;
	}
	public void setPalletCode(String palletCode) {
		this.palletCode = palletCode;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public Long getStockAlterMoveIdcodeId() {
		return stockAlterMoveIdcodeId;
	}
	public void setStockAlterMoveIdcodeId(Long stockAlterMoveIdcodeId) {
		this.stockAlterMoveIdcodeId = stockAlterMoveIdcodeId;
	}
	public Long getTakeTicketIdcode() {
		return takeTicketIdcode;
	}
	public void setTakeTicketIdcode(Long takeTicketIdcode) {
		this.takeTicketIdcode = takeTicketIdcode;
	}
	
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	
	public void setBatchLengthToTen() {
		if(!StringUtil.isEmpty(this.batchNo) && this.batchNo.length() < 10) {
			this.batchNo = (Constants.ZERO_PREFIX[10 - this.batchNo.length()] + this.batchNo);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SapDeliveryDetail castOther = (SapDeliveryDetail) obj;
		if (this.materialCode.equals(castOther.getMaterialCode())) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public int hashCode() {
		return new StringBuffer().append(materialCode).toString().hashCode();
	}
	
	
}
