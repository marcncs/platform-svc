package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class CustomerUser extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1398760130182720163L;

	/** identifier field */
    private String cid;

    /** nullable persistent field */
    private String cname;
    
    private Integer vocation;
    
    private String vocationname;

    /** nullable persistent field */
    private String idcard;
    
    /** nullable persistent field */
    private String birthday;

    /** nullable persistent field */
    private String cphoto;

    /** nullable persistent field */
    private Date signdate;

    /** nullable persistent field */
    private Integer customertype;

    /** nullable persistent field */
    private Integer customerstatus;
    
    private Integer yauld;
    
    private String yauldname;

    /** nullable persistent field */
    private Long regieid;

    /** nullable persistent field */
    private Integer province;

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

    /** nullable persistent field */
    private String detailaddr;

    /** nullable persistent field */
    private String postcode;

    /** nullable persistent field */
    private String hometel;

    /** nullable persistent field */
    private String officetel;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String movetel;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private Date registdate;

    /** nullable persistent field */
    private Long recordid;

    /** nullable persistent field */
    private String remark;
    
    private Double waitreceivablesum;
    
    private Long specializedept;
    
    private Long specializeid;
    
    private String provincename;
    
    private String cityname;
    
    private String areasname;
    
    private Integer paymentmode;
    
    private Integer transportmode;

	public Integer getTransportmode() {
		return transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public Long getSpecializeid() {
		return specializeid;
	}

	public void setSpecializeid(Long specializeid) {
		this.specializeid = specializeid;
	}

	public Double getWaitreceivablesum() {
		return waitreceivablesum;
	}

	public void setWaitreceivablesum(Double waitreceivablesum) {
		this.waitreceivablesum = waitreceivablesum;
	}

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getCustomerstatus() {
		return customerstatus;
	}

	public void setCustomerstatus(Integer customerstatus) {
		this.customerstatus = customerstatus;
	}

	public Integer getCustomertype() {
		return customertype;
	}

	public void setCustomertype(Integer customertype) {
		this.customertype = customertype;
	}

	public String getDetailaddr() {
		return detailaddr;
	}

	public void setDetailaddr(String detailaddr) {
		this.detailaddr = detailaddr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHometel() {
		return hometel;
	}

	public void setHometel(String hometel) {
		this.hometel = hometel;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMovetel() {
		return movetel;
	}

	public void setMovetel(String movetel) {
		this.movetel = movetel;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Long getRecordid() {
		return recordid;
	}

	public void setRecordid(Long recordid) {
		this.recordid = recordid;
	}

	public Long getRegieid() {
		return regieid;
	}

	public void setRegieid(Long regieid) {
		this.regieid = regieid;
	}

	public Date getRegistdate() {
		return registdate;
	}

	public void setRegistdate(Date registdate) {
		this.registdate = registdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCphoto() {
		return cphoto;
	}

	public void setCphoto(String cphoto) {
		this.cphoto = cphoto;
	}

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public Integer getYauld() {
		return yauld;
	}

	public void setYauld(Integer yauld) {
		this.yauld = yauld;
	}

	public String getYauldname() {
		return yauldname;
	}

	public void setYauldname(String yauldname) {
		this.yauldname = yauldname;
	}

	public Integer getVocation() {
		return vocation;
	}

	public void setVocation(Integer vocation) {
		this.vocation = vocation;
	}

	public String getVocationname() {
		return vocationname;
	}

	public void setVocationname(String vocationname) {
		this.vocationname = vocationname;
	}

	public Long getSpecializedept() {
		return specializedept;
	}

	public void setSpecializedept(Long specializedept) {
		this.specializedept = specializedept;
	}

	public String getAreasname() {
		return areasname;
	}

	public void setAreasname(String areasname) {
		this.areasname = areasname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

}
