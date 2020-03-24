package com.winsafe.erp.pojo;

import java.util.Date;

public class ProductPlanForm {
	private long id;
	//PO编号
	private String PONO;
	//工厂代码
	private String  organId;
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
	private Integer closeMan;
	private Integer closeFlag;
	
	//temp
	private String temp;
	
	private String organname;
	
	private String productname;
	//物料号
	private String mcode;
	
	private String packsize;
	
	private String totalweight;
	private String totalnum;
	
	//总量
	private String countNum;
	
	//托数
	private Integer tnum;
	//已释放箱数
	private Integer reaLnum;
	//实际生产箱数
	private Integer sjnum;
	//规格
	private String specmode;
	
	//生产日期
    private String proDateString;
	//包装日期
    private String packDateString;
    //是否上传
    private Integer isUpload;
    
    private String codeFrom;
    private String codeTo;

    //关联模式
    private String linkMode;

	public String getLinkMode() {
		return linkMode;
	}

	public void setLinkMode(String linkMode) {
		this.linkMode = linkMode;
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

	public String getOrganname() {
		return organname;
	}

	public void setOrganname(String organname) {
		this.organname = organname;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getPacksize() {
		return packsize;
	}

	public void setPacksize(String packsize) {
		this.packsize = packsize;
	}

	public String getTotalweight() {
		return totalweight;
	}

	public void setTotalweight(String totalweight) {
		this.totalweight = totalweight;
	}

	public String getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}

	public String getProDateString() {
		return proDateString;
	}

	public void setProDateString(String proDateString) {
		this.proDateString = proDateString;
	}

	public String getPackDateString() {
		return packDateString;
	}

	public void setPackDateString(String packDateString) {
		this.packDateString = packDateString;
	}

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	public Integer getTnum() {
		return tnum;
	}

	public void setTnum(Integer tnum) {
		this.tnum = tnum;
	}

	public Integer getReaLnum() {
		return reaLnum;
	}

	public void setReaLnum(Integer reaLnum) {
		this.reaLnum = reaLnum;
	}

	public Integer getSjnum() {
		return sjnum;
	}

	public void setSjnum(Integer sjnum) {
		this.sjnum = sjnum;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public Integer getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
}
