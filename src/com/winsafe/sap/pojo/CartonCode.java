package com.winsafe.sap.pojo;

import java.io.Serializable;
import java.util.Date;
/*******************************************************************************************  
 * 类描述：  
 * 箱码表实体类
 * @author: ryan.xi	  
 * @date：2014-10-15  
 * @version 1.0  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-10-15   ryan.xi                               
 *******************************************************************************************  
 */  
public class CartonCode implements Serializable, Comparable<CartonCode> {
	
	private static final long serialVersionUID = 9203787346520945994L;
	//箱码
	private String cartonCode;
	//托盘码
	private String palletCode;
	//外部pin码
	private String outPinCode;
	//内部pin码	
	private String innerPinCode;
	//创建日期
	private Date createDate;
	//物料号
	private String materialCode;
	//IS系统的产品编码
	private String productID;
	//对应的打印任务（箱号都对应打印任务）
	private Integer printJobId;
	//是否已生成小包装码(不是分批处理，不需要使用）
	private Integer PrimaryCodeStatus;
	//打印的顺序号
	private Integer printSeq;
	//托中箱数量
	private Integer countInPallet;
	//托中箱顺序
	private Integer cartonSeq;
	public CartonCode(){
		
	}
	
	public CartonCode(String cartonCode, String palletCode, String outPinCode, Integer printSeq) {
		super();
		this.cartonCode = cartonCode;
		this.palletCode = palletCode;
		this.outPinCode = outPinCode;
		this.printSeq = printSeq;
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
	public String getOutPinCode() {
		return outPinCode;
	}
	public void setOutPinCode(String outPinCode) {
		this.outPinCode = outPinCode;
	}
	public String getInnerPinCode() {
		return innerPinCode;
	}
	public void setInnerPinCode(String innerPinCode) {
		this.innerPinCode = innerPinCode;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public Integer getPrintJobId() {
		return printJobId;
	}
	public void setPrintJobId(Integer printJobId) {
		this.printJobId = printJobId;
	}
	public Integer getPrimaryCodeStatus() {
		return PrimaryCodeStatus;
	}
	public void setPrimaryCodeStatus(Integer primaryCodeStatus) {
		PrimaryCodeStatus = primaryCodeStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getPrintSeq() {
		return printSeq;
	}
	public void setPrintSeq(Integer printSeq) {
		this.printSeq = printSeq;
	}

	public Integer getCountInPallet() {
		return countInPallet;
	}

	public void setCountInPallet(Integer countInPallet) {
		this.countInPallet = countInPallet;
	}

	public Integer getCartonSeq() {
		return cartonSeq;
	}

	public void setCartonSeq(Integer cartonSeq) {
		this.cartonSeq = cartonSeq;
	}

	@Override
	public int compareTo(CartonCode o) {
		if(this.palletCode == null || o.getPalletCode() == null || this.palletCode.equals(o.getPalletCode())) {
			return this.cartonCode.compareTo(o.getCartonCode());
		} else {
			return this.palletCode.compareTo(o.getPalletCode());
		}
	}
}
