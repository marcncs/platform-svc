package com.winsafe.sap.pojo;

import java.io.Serializable;
import java.util.Date;

import com.winsafe.sap.metadata.PrimaryCodeStatus;
import com.winsafe.sap.metadata.PrintStatus;

public class PrintJob implements Serializable {

	private static final long serialVersionUID = 1L;
	// 打印任务号
	private Integer printJobId;
	// SAP TO号
	private String transOrder;
	// TO单序号
	private String transOrderItem;
	// 工厂编号
	private String plantCode;
	// 工厂IS系统编号
	private String organId;
	// 工厂名称
	private String plantName;
	// 物料号
	private String materialCode;
	// 物料名
	private String materialName;
	// 数量（指kg或升）与产品物料有关的信息,一般为一箱多少数量
	private String shipperQuantity;
	// 包装规格（如：5克）
	private String packSize;
	// PO单号
	private String processOrderNumber;
	// TO日期
	private Date transOrderDate;
	// 批次号
	private String batchNumber;
	// GTIN码，Global Trade Item Number
	private String GTINNumber;
	// 该打印任务总的箱码数量
	private Integer numberOfCases;
	// 该打印任务总的打印数量
	private Integer totalNumber;
	// 生产日期
	private String productionDate;
	// 包装日期(打印时可修改，补打时不允许修改）
	private String packagingDate;
	// 到期日期
	private String expiryDate;
	// 打印状态(0,未打印，1已打印）
	private Integer printingStatus;
	// 打印日期
	private Date printingDate;
	// 创建日期
	private Date createDate;
	// 创建用户号
	private Integer createUser;
	// 上传日志序号
	private Integer uploadId;
	// 是否已生成小包装码
	private Integer primaryCodeStatus;
	// 包装码文件保存路径
	private String codeFilePath;
	// 是否已删除(0,未删除 1,已删除)
	private Integer isDelete;

	private String palletSSCC;

	private String cartonSSCC;
	//产品编号
	private String productId;
	//生成小包装码时的错误信息
	private String codeErrorMsg;
	//产线
	private String productionLine;
	//确认标志
	private Integer confirmFlag;

	// 解析手工上传杭州工厂订单文件时用到的index
	private int plantIndex = 0;
	// 解析手工上传分装厂订单文件时用到的index
	private int tollerIndex = 0;
	
	//增加容量
	private String shipperSize;
	
	//增加物料批次后两位
	private String materialBatchNo;
	
	//标签类型
	private String labelType;
	
	//标签二维码（辅助字段）
	private String tdCode;
	//同步状态0未同步1已上传2已回传
	private Integer syncStatus;
	//条码类型1.DM 2.QR
	private Integer codeType;
	//关联模式1.前关联2.后关联
	private Integer linkMode;
	
	private String syncFilePath; 
	//箱序号状态0未关联1已关联-1无需关联
	private Integer cartonSeqStatus; 
	
	public String getTdCode() {
		return tdCode;
	}

	public void setTdCode(String tdCode) {
		this.tdCode = tdCode;
	}

	public Integer getPrintJobId() {
		return printJobId;
	}

	public void setPrintJobId(Integer printJobId) {
		this.printJobId = printJobId;
	}
	
	public String getMaterialBatchNo() {
		return materialBatchNo;
	}

	public void setMaterialBatchNo(String materialBatchNo) {
		this.materialBatchNo = materialBatchNo;
	}

	public String getTransOrder() {
		return transOrder;
	}

	public void setTransOrder(String transOrder) {
		this.transOrder = transOrder;
	}

	public String getTransOrderItem() {
		return transOrderItem;
	}

	public void setTransOrderItem(String transOrderItem) {
		this.transOrderItem = transOrderItem;
	}

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getShipperQuantity() {
		return shipperQuantity;
	}

	public void setShipperQuantity(String shipperQuantity) {
		this.shipperQuantity = shipperQuantity;
	}

	public String getPackSize() {
		return packSize;
	}

	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	public String getProcessOrderNumber() {
		return processOrderNumber;
	}

	public void setProcessOrderNumber(String processOrderNumber) {
		this.processOrderNumber = processOrderNumber;
	}

	public Date getTransOrderDate() {
		return transOrderDate;
	}

	public void setTransOrderDate(Date transOrderDate) {
		this.transOrderDate = transOrderDate;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getGTINNumber() {
		return GTINNumber;
	}

	public void setGTINNumber(String gTINNumber) {
		GTINNumber = gTINNumber;
	}
	

	public String getShipperSize() {
		return shipperSize;
	}

	public void setShipperSize(String shipperSize) {
		this.shipperSize = shipperSize;
	}

	public Integer getNumberOfCases() {
		return numberOfCases;
	}

	public void setNumberOfCases(Integer numberOfCases) {
		this.numberOfCases = numberOfCases;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getPackagingDate() {
		return packagingDate;
	}

	public void setPackagingDate(String packagingDate) {
		this.packagingDate = packagingDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getPrintingStatus() {
		return printingStatus;
	}

	public String getPrintingStatusDisplay() {
		PrintStatus printStatus = PrintStatus.parse(printingStatus);
		if (printStatus != null) {
			return printStatus.getDisplayName();
		}
		return "";
	}

	public void setPrintingStatus(Integer printingStatus) {
		this.printingStatus = printingStatus;
	}

	public Date getPrintingDate() {
		return printingDate;
	}

	public void setPrintingDate(Date printingDate) {
		this.printingDate = printingDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getUploadId() {
		return uploadId;
	}

	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}

	public Integer getPrimaryCodeStatus() {
		return primaryCodeStatus;
	}

	public String getPrimaryCodeStatusDisplay() {
		PrimaryCodeStatus primaryCodeStatus = PrimaryCodeStatus
				.parse(printingStatus);
		if (primaryCodeStatus != null) {
			return primaryCodeStatus.getDisplayName();
		}
		return "";
	}

	public void setPrimaryCodeStatus(Integer primaryCodeStatus) {
		this.primaryCodeStatus = primaryCodeStatus;
	}

	public String getPalletSSCC() {
		return palletSSCC;
	}

	public void setPalletSSCC(String palletSSCC) {
		this.palletSSCC = palletSSCC;
	}

	public String getCartonSSCC() {
		return cartonSSCC;
	}

	public void setCartonSSCC(String cartonSSCC) {
		this.cartonSSCC = cartonSSCC;
	}

	public String getCodeFilePath() {
		return codeFilePath;
	}

	public void setCodeFilePath(String codeFilePath) {
		this.codeFilePath = codeFilePath;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getCodeErrorMsg() {
		return codeErrorMsg;
	}

	public void setCodeErrorMsg(String codeErrorMsg) {
		this.codeErrorMsg = codeErrorMsg;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public int getPlantIndex() {
		return plantIndex;
	}

	public int getTollerIndex() {
		return tollerIndex;
	}

	public String getProductionLine() {
		return productionLine;
	}

	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}

	public Integer getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(Integer confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public void addPlantIndex() {
		plantIndex++;
	}
	
	public void addTollerIndex() {
		tollerIndex++;
	}
	
	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public void setPlantProperty(String value) {
		switch (plantIndex) {
		case 0:
			setProcessOrderNumber(value);
			plantIndex++;
			break;
		case 1:
			setPalletSSCC(value);
			plantIndex++;
			break;
		case 2:
			setCartonSSCC(value);
			plantIndex++;
			break;
		case 3:
			setMaterialCode(value);
			plantIndex++;
			break;
		case 4:
			setMaterialName(value);
			plantIndex++;
			break;
		case 5:
			setBatchNumber(value);
			plantIndex++;
			break;
		case 6:
			setShipperQuantity(value);
			plantIndex++;
			break;
		case 7:
			setPackSize(value);
			plantIndex++;
			break;
		case 8:
			setProductionDate(value);
			plantIndex++;
			break;
		case 9:
			setPackagingDate(value);
			plantIndex++;
			break;
		default:
			break;
		}
	}
	
	public void setTollerProperty(String value) {
		switch (tollerIndex) {
		case 0:
			setCartonSSCC(value);
			tollerIndex++;
			break;
		case 1:
			setMaterialCode(value);
			tollerIndex++;
			break;
		case 2:
			setMaterialName(value);
			tollerIndex++;
			break;
		case 3:
			setBatchNumber(value);
			tollerIndex++;
			break;
		case 4:
			setProductionDate(value);
			tollerIndex++;
			break;
		case 5:
			setPackagingDate(value);
			tollerIndex++;
			break;
		case 6:
			setGTINNumber(value);
			tollerIndex++;
			break;
		default:
			break;
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
		PrintJob castOther = (PrintJob) obj;
		if ((this.transOrder == null ? true : this.transOrder.equals(castOther
				.getTransOrder()))
				&& (this.transOrderDate == null ? true : this.transOrderDate
						.equals(castOther.getTransOrderDate()))
//				&& (this.transOrderItem == null ? true : this.transOrderItem
//						.equals(castOther.getTransOrderItem()))
				&& (this.processOrderNumber == null ? true
						: this.processOrderNumber.equals(castOther
								.getProcessOrderNumber()))
				&& (this.plantCode == null ? true : this.plantCode
						.equals(castOther.getPlantCode()))
				&& (this.materialCode == null ? true : this.materialCode
						.equals(castOther.getMaterialCode()))
				&& (this.batchNumber == null ? true : this.batchNumber
						.equals(castOther.getBatchNumber()))
				&& (this.shipperQuantity == null ? true : this.shipperQuantity
						.equals(castOther.getShipperQuantity()))
				&& (this.productionDate == null ? true : this.productionDate
						.equals(castOther.getProductionDate()))
				&& (this.expiryDate == null ? true : this.expiryDate
						.equals(castOther.getExpiryDate()))
				&& (this.packagingDate == null ? true : this.packagingDate
						.equals(castOther.getPackagingDate()))
				&& (this.GTINNumber == null ? true : this.GTINNumber
						.equals(castOther.getGTINNumber()))) {
			return true;
		} else {
			return false;
		}
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public Integer getLinkMode() {
		return linkMode;
	}

	public void setLinkMode(Integer linkMode) {
		this.linkMode = linkMode;
	}

	public String getSyncFilePath() {
		return syncFilePath;
	}

	public void setSyncFilePath(String syncFilePath) {
		this.syncFilePath = syncFilePath;
	}

	@Override
	public int hashCode() {
		return new StringBuffer().append(transOrder).append(transOrderDate)
//				.append(transOrderItem)
				.append(processOrderNumber).append(plantCode).append(
						materialCode).append(batchNumber).append(
						shipperQuantity).append(productionDate).append(expiryDate)
				.append(packagingDate).append(GTINNumber).toString().hashCode();
	}

	public Integer getCartonSeqStatus() {
		return cartonSeqStatus;
	}

	public void setCartonSeqStatus(Integer cartonSeqStatus) {
		this.cartonSeqStatus = cartonSeqStatus;
	}

}
