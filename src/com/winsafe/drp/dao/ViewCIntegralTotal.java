package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewCIntegralTotal implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8979904992753551017L;
	private Long id;
	/** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double completeintegral;

    /** nullable persistent field */
    private Double duihuan;

    /** nullable persistent field */
    private Double tiaozheng;

    /** nullable persistent field */
    private Double jieyu;
    
    /** full constructor */
    public ViewCIntegralTotal(String cid, Date makedate, String cname, String mobile, String makeorganid, Double dealintegral, Double completeintegral, Double duihuan, Double tiaozheng, Double jieyu) {
        this.cid = cid;
        this.makedate = makedate;
        this.cname = cname;
        this.mobile = mobile;
        this.makeorganid = makeorganid;
        this.dealintegral = dealintegral;
        this.completeintegral = completeintegral;
        this.duihuan = duihuan;
        this.tiaozheng = tiaozheng;
        this.jieyu = jieyu;
    }

    /** default constructor */
    public ViewCIntegralTotal() {
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Double getDealintegral() {
        return this.dealintegral;
    }

    public void setDealintegral(Double dealintegral) {
        this.dealintegral = dealintegral;
    }

    public Double getCompleteintegral() {
        return this.completeintegral;
    }

    public void setCompleteintegral(Double completeintegral) {
        this.completeintegral = completeintegral;
    }

    public Double getDuihuan() {
        return this.duihuan;
    }

    public void setDuihuan(Double duihuan) {
        this.duihuan = duihuan;
    }

    public Double getTiaozheng() {
        return this.tiaozheng;
    }

    public void setTiaozheng(Double tiaozheng) {
        this.tiaozheng = tiaozheng;
    }

    public Double getJieyu() {
        return this.jieyu;
    }

    public void setJieyu(Double jieyu) {
        this.jieyu = jieyu;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
    
    
    
    

}
