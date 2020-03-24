package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewOIntegralTotal implements Serializable {

    /** nullable persistent field */
    private Long id;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String organname;

    /** nullable persistent field */
    private Double dealintegral;

    /** nullable persistent field */
    private Double completeintegral;

    /** nullable persistent field */
    private Double tiaozheng;

    /** nullable persistent field */
    private Double leiji;

    /** full constructor */
    public ViewOIntegralTotal(Long id, String oid, Date makedate, String organname, Double dealintegral, Double completeintegral, Double tiaozheng, Double leiji) {
        this.id = id;
        this.oid = oid;
        this.makedate = makedate;
        this.organname = organname;
        this.dealintegral = dealintegral;
        this.completeintegral = completeintegral;
        this.tiaozheng = tiaozheng;
        this.leiji = leiji;
    }

    /** default constructor */
    public ViewOIntegralTotal() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getOrganname() {
        return this.organname;
    }

    public void setOrganname(String organname) {
        this.organname = organname;
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

    public Double getTiaozheng() {
        return this.tiaozheng;
    }

    public void setTiaozheng(Double tiaozheng) {
        this.tiaozheng = tiaozheng;
    }

    public Double getLeiji() {
        return this.leiji;
    }

    public void setLeiji(Double leiji) {
        this.leiji = leiji;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

}
