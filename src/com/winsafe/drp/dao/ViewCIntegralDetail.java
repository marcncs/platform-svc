package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewCIntegralDetail implements Serializable {

    /** nullable persistent field */
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
    private String organid;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double xiaofei;

    /** nullable persistent field */
    private Double jibei;

    /** nullable persistent field */
    private Double tuijian;

    /** nullable persistent field */
    private Double dinghua;

    /** nullable persistent field */
    private Double fukuan;

    /** nullable persistent field */
    private Double songhua;

    /** full constructor */
    public ViewCIntegralDetail(Long id, String cid, Date makedate, String cname, String mobile, String organid, Double dealintegral, Double xiaofei, Double jibei, Double tuijian, Double dinghua, Double fukuan, Double songhua) {
        this.id = id;
        this.cid = cid;
        this.makedate = makedate;
        this.cname = cname;
        this.mobile = mobile;
        this.organid = organid;
        this.dealintegral = dealintegral;
        this.xiaofei = xiaofei;
        this.jibei = jibei;
        this.tuijian = tuijian;
        this.dinghua = dinghua;
        this.fukuan = fukuan;
        this.songhua = songhua;
    }

    /** default constructor */
    public ViewCIntegralDetail() {
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

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public Double getDealintegral() {
        return this.dealintegral;
    }

    public void setDealintegral(Double dealintegral) {
        this.dealintegral = dealintegral;
    }

    public Double getXiaofei() {
        return this.xiaofei;
    }

    public void setXiaofei(Double xiaofei) {
        this.xiaofei = xiaofei;
    }

    public Double getJibei() {
        return this.jibei;
    }

    public void setJibei(Double jibei) {
        this.jibei = jibei;
    }

    public Double getTuijian() {
        return this.tuijian;
    }

    public void setTuijian(Double tuijian) {
        this.tuijian = tuijian;
    }

    public Double getDinghua() {
        return this.dinghua;
    }

    public void setDinghua(Double dinghua) {
        this.dinghua = dinghua;
    }

    public Double getFukuan() {
        return this.fukuan;
    }

    public void setFukuan(Double fukuan) {
        this.fukuan = fukuan;
    }

    public Double getSonghua() {
        return this.songhua;
    }

    public void setSonghua(Double songhua) {
        this.songhua = songhua;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

}
