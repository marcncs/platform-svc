package com.winsafe.drp.dao;

import java.util.Date;

public class ReportForm {
	//地区
	 private String area;
	 //省份
	 private String province;
	 //机构编号
	 private String organid;
	 //机构名
	 private String organname;
	 //仓库编号
	 private String warehouseid;
	 //产品类别
	 private String productsort;
	 //产品名
	 private String productname;
	 //物料号
	 private String mcode;
	 //物料号中文名
	 private String mcodechinesename;
	 //物料号英文名
	 private String mcodeenglishname;
	 //产品编号
	 private String productid;

	 //规格
	 private String specmode;
	 //报告日期
	 private Date reportdate;
	 /** 库存数量 **/
	 private String stockpile;
	 //批次
	 private String batch;
	 //生产日期
	 private String productdate;
	 //过期日期
	 private String expirydate;
	 //有效期
	 private String validate;
	 // 盘点数量 
	 private String inventorypile;
	 // 差异数量
	 private String differpile;
	 //单位
	 private Integer countunit;
	 //单据编号
	 private String billno;
	 //开始时间
	 private String beginDate;
	 //结束日期
	 private String endDate;
	 //备注
	 private String remark;
	//机构内部编码
	 private String oecode;
	 //单据日期
	 private String billdate;
	 //产品英文
	 private String productNameen;
	 //规格英文
	 private String packSizeNameEn;
	 

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getOrganid() {
		return organid;
	}
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	public String getOrganname() {
		return organname;
	}
	public void setOrganname(String organname) {
		this.organname = organname;
	}
	public String getProductsort() {
		return productsort;
	}
	public void setProductsort(String productsort) {
		this.productsort = productsort;
	}
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getSpecmode() {
		return specmode;
	}
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public String getStockpile() {
		return stockpile;
	}
	public void setStockpile(String stockpile) {
		this.stockpile = stockpile;
	}
	public String getMcodechinesename() {
		return mcodechinesename;
	}
	public void setMcodechinesename(String mcodechinesename) {
		this.mcodechinesename = mcodechinesename;
	}
	public String getMcodeenglishname() {
		return mcodeenglishname;
	}
	public void setMcodeenglishname(String mcodeenglishname) {
		this.mcodeenglishname = mcodeenglishname;
	}
	public Date getReportdate() {
		return reportdate;
	}
	public void setReportdate(Date reportdate) {
		this.reportdate = reportdate;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	
	public String getBilldate() {
		return billdate;
	}
	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getInventorypile() {
		return inventorypile;
	}
	public void setInventorypile(String inventorypile) {
		this.inventorypile = inventorypile;
	}
	public String getDifferpile() {
		return differpile;
	}
	public void setDifferpile(String differpile) {
		this.differpile = differpile;
	}
	public Integer getCountunit() {
		return countunit;
	}
	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getProductdate() {
		return productdate;
	}
	public void setProductdate(String productdate) {
		this.productdate = productdate;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	public String getWarehouseid() {
		return warehouseid;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public String getOecode() {
		return oecode;
	}
	public void setOecode(String oecode) {
		this.oecode = oecode;
	}
	public String getProductNameen() {
		return productNameen;
	}
	public void setProductNameen(String productNameen) {
		this.productNameen = productNameen;
	}
	public String getPackSizeNameEn() {
		return packSizeNameEn;
	}
	public void setPackSizeNameEn(String packSizeNameEn) {
		this.packSizeNameEn = packSizeNameEn;
	}
	 
}
