package com.winsafe.drp.dao;

import java.util.Date;

/**
 * @author : jerry
 * @version : 2009-9-15 下午03:37:49
 * www.winsafe.cn
 */
public class SupplySaleMoveDetailForm {

	 private Integer id;

	    /** nullable persistent field */
	    private String ssmid;

	    /** nullable persistent field */
	    private String productid;

	    /** nullable persistent field */
	    private String productname;

	    /** nullable persistent field */
	    private String specmode;

	    /** nullable persistent field */
	    private String unitidname;
	    
	    private Double cost;
	    
	    private Date makedate;

	    public Double getCost() {
			return cost;
		}

		public void setCost(Double cost) {
			this.cost = cost;
		}

		public Date getMakedate() {
			return makedate;
		}

		public void setMakedate(Date makedate) {
			this.makedate = makedate;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getSsmid() {
			return ssmid;
		}

		public void setSsmid(String ssmid) {
			this.ssmid = ssmid;
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

		public String getUnitidname() {
			return unitidname;
		}

		public void setUnitidname(String unitidname) {
			this.unitidname = unitidname;
		}

		public String getBatch() {
			return batch;
		}

		public void setBatch(String batch) {
			this.batch = batch;
		}

		public Double getPunitprice() {
			return punitprice;
		}

		public void setPunitprice(Double punitprice) {
			this.punitprice = punitprice;
		}

		public Double getSunitprice() {
			return sunitprice;
		}

		public void setSunitprice(Double sunitprice) {
			this.sunitprice = sunitprice;
		}

		public Double getQuantity() {
			return quantity;
		}

		public void setQuantity(Double quantity) {
			this.quantity = quantity;
		}

		public Double getTakequantity() {
			return takequantity;
		}

		public void setTakequantity(Double takequantity) {
			this.takequantity = takequantity;
		}

		public Double getPsubsum() {
			return psubsum;
		}

		public void setPsubsum(Double psubsum) {
			this.psubsum = psubsum;
		}

		public Double getSsubsum() {
			return ssubsum;
		}

		public void setSsubsum(Double ssubsum) {
			this.ssubsum = ssubsum;
		}

		/** nullable persistent field */
	    private String batch;

	    /** nullable persistent field */
	    private Double punitprice;

	    /** nullable persistent field */
	    private Double sunitprice;

	    /** nullable persistent field */
	    private Double quantity;

	    /** nullable persistent field */
	    private Double takequantity;

	    /** nullable persistent field */
	    private Double psubsum;

	    /** nullable persistent field */
	    private Double ssubsum;
}
