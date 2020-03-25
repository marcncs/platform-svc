package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewWlmIdcode implements Serializable {

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String syncode;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String billname;

    /** nullable persistent field */
    private String billtype;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String producedate;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String startno;

    /** nullable persistent field */
    private String endno;

    /** nullable persistent field */
    private Double packquantity;

    /** nullable persistent field */
    private String organid;
    
    private String email;
    
    private String nccode;
    
    private Date auditdate;
    
    /** full constructor */
    public ViewWlmIdcode(String id, String warehouseid, String cid, String syncode, String cname, Integer province, Integer city, Integer areas, String productid, String productname, String specmode, String billname, String billtype, Date makedate, String producedate, String batch, String startno, String endno, Double packquantity, String organid, String nccode) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.cid = cid;
        this.syncode = syncode;
        this.cname = cname;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.billname = billname;
        this.billtype = billtype;
        this.makedate = makedate;
        this.producedate = producedate;
        this.batch = batch;
        this.startno = startno;
        this.endno = endno;
        this.packquantity = packquantity;
        this.organid = organid;
        this.nccode = nccode;
    }

    /** default constructor */
    public ViewWlmIdcode() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSyncode() {
        return this.syncode;
    }

    public void setSyncode(String syncode) {
        this.syncode = syncode;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getProvince() {
        return this.province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getAreas() {
        return this.areas;
    }

    public void setAreas(Integer areas) {
        this.areas = areas;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public String getBillname() {
        return this.billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getBilltype() {
        return this.billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getProducedate() {
        return this.producedate;
    }

    public void setProducedate(String producedate) {
        this.producedate = producedate;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getStartno() {
        return this.startno;
    }

    public void setStartno(String startno) {
        this.startno = startno;
    }

    public String getEndno() {
        return this.endno;
    }

    public void setEndno(String endno) {
        this.endno = endno;
    }

    public Double getPackquantity() {
        return this.packquantity;
    }

    public void setPackquantity(Double packquantity) {
        this.packquantity = packquantity;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }
    
    

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

}
