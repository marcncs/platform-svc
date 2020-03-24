package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewCIntegralDuihuanTotal implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1821553415142937135L;

	/** nullable persistent field */
    private Long id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String mobile;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Double duihuan;

    /** nullable persistent field */
    private Double tiaozheng;

    /** full constructor */
    public ViewCIntegralDuihuanTotal(Long id, String cid, String organid, Date makedate, String cname, String mobile, String makeorganid, Double duihuan, Double tiaozheng) {
        this.id = id;
        this.cid = cid;
        this.organid = organid;
        this.makedate = makedate;
        this.cname = cname;
        this.mobile = mobile;
        this.makeorganid = makeorganid;
        this.duihuan = duihuan;
        this.tiaozheng = tiaozheng;
    }

    /** default constructor */
    public ViewCIntegralDuihuanTotal() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
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

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

}
