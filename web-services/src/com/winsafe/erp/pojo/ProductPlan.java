package com.winsafe.erp.pojo;

import java.util.Date;

public class ProductPlan {
	private long id;
	//PO编号
	private String PONO;
	//工厂代码
	private String  organId;
	//userid>printjobid
	private Integer  makeId;
	//产品编码
	private String productId;
	//物料批次
	private String mbatch;
	//产品批次
	private String pbatch;
	//生产日期
    private Date proDate;
	//包装日期
    private Date packDate;
	//生产箱数
	private Integer  boxnum;
	//标签份数
	private Integer  copys;
	//审批标志
	private Integer  approvalFlag;
	//审批人id
	private Integer approvalMan;
	//关闭人id
	private Integer  closeMan;
	//关闭标志
	private Integer  closeFlag;
	//temp
	private String temp;
	
	//错误信息
	private String msg;
	//2016-3-18 是否上传标识位
	private Integer isUpload;
	
	private String codeFrom;
	private String codeTo;
	private Integer version; 
	
	private Integer printJobId;

	private String tdCode;//辅助字段
	
	private Integer cartonSeqFlag; 
	
	public long getId() {
		return id; 
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPONO() {
		return PONO;
	}

	public void setPONO(String pONO) {
		PONO = pONO;
	}
	
	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMbatch() {
		return mbatch;
	}

	public void setMbatch(String mbatch) {
		this.mbatch = mbatch;
	}

	public String getPbatch() {
		return pbatch;
	}

	public void setPbatch(String pbatch) {
		this.pbatch = pbatch;
	}

	public Date getProDate() {
		return proDate;
	}

	public void setProDate(Date proDate) {
		this.proDate = proDate;
	}

	public Date getPackDate() {
		return packDate;
	}

	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}

	public Integer getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(Integer boxnum) {
		this.boxnum = boxnum;
	}

	public Integer getCopys() {
		return copys;
	}

	public void setCopys(Integer copys) {
		this.copys = copys;
	}

	public Integer getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(Integer approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public Integer getApprovalMan() {
		return approvalMan;
	}

	public void setApprovalMan(Integer approvalMan) {
		this.approvalMan = approvalMan;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCloseMan() {
		return closeMan;
	}

	public void setCloseMan(Integer closeMan) {
		this.closeMan = closeMan;
	}

	public Integer getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(Integer closeFlag) {
		this.closeFlag = closeFlag;
	}

	public Integer getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}

	public String getCodeFrom() {
		return codeFrom;
	}

	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	public String getCodeTo() {
		return codeTo;
	}

	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getPrintJobId() {
		return printJobId;
	}

	public void setPrintJobId(Integer printJobId) {
		this.printJobId = printJobId;
	}

	public String getTdCode() {
		return tdCode;
	}

	public void setTdCode(String tdCode) {
		this.tdCode = tdCode;
	}

	public Integer getCartonSeqFlag() {
		return cartonSeqFlag;
	}

	public void setCartonSeqFlag(Integer cartonSeqFlag) {
		this.cartonSeqFlag = cartonSeqFlag;
	}
}
